package com.ofweek.live.core.modules.speaker.service;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.utils.ZipUtils;
import com.ofweek.live.core.modules.room.dao.RoomSpeechDao;
import com.ofweek.live.core.modules.room.dao.RoomSpeechPublicDao;
import com.ofweek.live.core.modules.room.service.RoomSpeechService;
import com.ofweek.live.core.modules.speaker.dao.SpeakerSpeechDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerSpeechRecycleDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.enums.AuditStatusEnum;
import com.ofweek.live.core.modules.sys.enums.FileSuffixTypeEnum;
import com.ofweek.live.core.modules.sys.enums.FileTypeEnum;
import com.ofweek.live.core.modules.sys.service.SysFileService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional
public class SpeakerSpeechService extends CrudService<SpeakerSpeechDao, SpeakerSpeech> {

    @Autowired
    private SpeakerSpeechRecycleDao speakerSpeechRecycleDao;

    @Autowired
    private RoomSpeechDao roomSpeechDao;

    @Autowired
    private RoomSpeechPublicDao roomSpeechPublicDao;

    @Autowired
    private RoomSpeechService roomSpeechService;

    @Autowired
    private SysFileService sysFileService;

    @Override
    @Transactional(readOnly = false)
    public void save(SpeakerSpeech entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveSpeakerSpeech"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void saveSpeech(SpeakerSpeech entity) throws Exception {
        SysFile sysFile = sysFileService.save(entity.getFileId(), SysFileUtils.TypeEnum.SPEAKER_SPEECH);
        entity.setFileId(sysFile.getId());
        entity.setName(sysFile.getOriginalName());
        entity.setStatus(AuditStatusEnum.AUDITING.getCode());

        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("SpeakerSpeech"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }

        unpackSpeakerSpeech(entity, sysFile);
    }

    public void delSpeakerSpeechForIds(String speechIds) {
        dao.delSpeakerSpeechForIds(speechIds.split(","));
    }

    public SpeakerSpeech getSpeakerSpeechForObj(SpeakerSpeech speakerSpeech) {
        speakerSpeech.setSpeakerId(UserUtils.getUser().getId());
        return dao.getSpeakerSpeechForObj(speakerSpeech);
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void delete(SpeakerSpeech speakerSpeech) {
        setSpeakerDataRecycle(speakerSpeech);
    }

    private void setSpeakerDataRecycle(SpeakerSpeech speakerSpeech) {
        speakerSpeechRecycleDao.insertFormSpeech2SpeechRecycle(speakerSpeech.getId());// 备份到回收站表
        dao.delete(speakerSpeech);// 删除演讲稿记录
        roomSpeechDao.deleteBySourceId(speakerSpeech.getId());// 删除房间演讲稿记录
        roomSpeechPublicDao.deleteBySourceId(speakerSpeech.getId());// 删除房间演讲稿表(审核通过)记录
    }

    public String check(String speechId) {
        if (roomSpeechService.countOngoing(speechId)) {
            return "Exists";
        } else {
            return "NoExists";
        }
    }

    public void unpackSpeakerSpeech(SpeakerSpeech speakerSpeech, SysFile sysFile) {
        try {
            String filePath = LiveEnv.getUploadRoot() + sysFile.getUri();
            Resource resource = new UrlResource(ResourceUtils.FILE_URL_PREFIX + filePath);
            FileSuffixTypeEnum fileType = FileSuffixTypeEnum.getType(resource.getInputStream());
            String canonicalPath = new File(filePath).getAbsolutePath();
            String folder = null;
            if (fileType.equals(FileSuffixTypeEnum.ZIP)) {
                folder = canonicalPath.substring(0, canonicalPath.indexOf(".zip"));
                unZip(canonicalPath, folder);
            } else if (fileType.equals(FileSuffixTypeEnum.RAR)) {
                folder = canonicalPath.substring(0, canonicalPath.indexOf(".rar"));
                unRar(canonicalPath, folder);
            } else {
                throw new ServiceException("文件格式错误[" + fileType + "],只支持rar和zip格式!");
            }
            if (StringUtils.isNotEmpty(folder)) {
                String uploadFlagDir = getUploadFlagDir();
                saveChildFile(sysFile.getId(), uploadFlagDir, folder);
            }
        } catch (Exception e) {
            logger.error("unpack exception", e);
            throw new ServiceException("请检查压缩包中是否包含图片!必须将PPT的每一页都转成jpg图片并用数字排序命名,并且打包成压缩包后再上传!");
        }
    }

    private void saveChildFile(final String parentId, final String flagDir, String fullFileName) throws Exception {
        File file = new File(fullFileName);
        File[] children = file.listFiles();
        // 如果有子节点，那么递归遍历该文件下的子节点，直到找到所有的子文件
        if (ArrayUtils.isNotEmpty(children)) {
            for (File child : children) {
                saveChildFile(parentId, flagDir, child.getCanonicalPath());
            }
        } else {
            String fileName = file.getName();
            int orderIdex = parseOrderIndex(fileName);
            File newFile = new File(file.getParentFile().getCanonicalPath() + File.separator + orderIdex + fileName.substring(fileName.lastIndexOf(".")));
            file.renameTo(newFile);
            String canonicalPath = StringUtils.replace(newFile.getCanonicalPath(), "\\", "/");
            String uri = canonicalPath.substring(canonicalPath.indexOf(flagDir) + flagDir.length() + 1);

            SysFile entity = new SysFile();
            entity.setParentId(parentId);
            entity.setUri(uri);
            entity.setSort(orderIdex);
            entity.setSize((int) newFile.length());
            savePackChildFile(entity, fileName);
        }
    }

    /**
     * 获取上传根路径配置项中的最后一个目录名
     *
     * @return
     */
    private String getUploadFlagDir() {
        String uploadRoot = LiveEnv.getUploadRoot();
        File file = new File(uploadRoot);
        return file.getName();
    }

    private void savePackChildFile(SysFile entity, String srcName) {
        entity.setOriginalName(srcName);
        entity.setExt(StringUtils.substringAfterLast(srcName, "."));
        entity.setIsTemp(0);
        entity.setType(FileTypeEnum.PICTURE.getCode());
        entity.setSubjectType(SysFileUtils.TypeEnum.SPEAKER_SPEECH.getCode());
        try {
            sysFileService.saveFile(entity);
        } catch (Exception e) {
            logger.error("system exception", e);
            throw new ServiceException("系统异常,上传PPT失败!");
        }
    }

    private int parseOrderIndex(String fileName) throws Exception {
        String tempFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String intStr = "";
        for (int i = tempFileName.length() - 1; i >= 0; i--) {
            try {
                // 只是判断该字符是不是数字字符
                int value = Integer.valueOf("" + tempFileName.charAt(i));
                intStr = value + intStr;
            } catch (Exception e) {
                if (NumberUtils.isNumber(intStr)) {
                    return Integer.valueOf(intStr);
                }
                throw new ServiceException("压缩包中包含1张或多张未以数字编号命名的图片文件,请确保每张图片以数字编号有序命名!");
            }
        }
        return Integer.valueOf(intStr);
    }

    private void unZip(String fileName, String destDir) throws Exception {
        InputStream input = null;
        FileOutputStream output = null;
        ZipFile zipFile = null;
        try {
            File dstDiretory = new File(destDir);
            if (!dstDiretory.exists()) {
                dstDiretory.mkdirs();
            }
            zipFile = new ZipFile(fileName, "GBK");
            for (Enumeration<?> entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    String subFileName = entry.getName();
                    subFileName = subFileName.substring(subFileName.lastIndexOf("/") + 1);
                    String subFileCanonicalPath = destDir + File.separator + subFileName;
                    File file = new File(subFileCanonicalPath);
                    checkFileSuffix(subFileName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    input = zipFile.getInputStream(entry);
                    output = new FileOutputStream(file);
                    int readedCount = 0;
                    byte[] readedBytes = null;
                    byte[] buffer = new byte[ZipUtils.DEFAULT_BUFFER_SIZE];
                    while ((readedCount = IOUtils.read(input, buffer)) > 0) {
                        if (readedCount == buffer.length) {
                            readedBytes = buffer;
                        } else {
                            readedBytes = ArrayUtils.subarray(buffer, 0, readedCount);
                        }
                        output.write(readedBytes, 0, readedCount);
                    }
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(output);
                }
            }
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
            zipFile.close();
        }
    }

    private void unRar(String fileName, String destDir) throws Exception {
        Archive a = null;
        FileOutputStream os = null;
        try {
            File dstDiretory = new File(destDir);
            if (!dstDiretory.exists()) {
                dstDiretory.mkdirs();
            }
            a = new Archive(new File(fileName));
            FileHeader fh = a.nextFileHeader();
            while (fh != null) {
                if (!fh.isDirectory()) {
                    String subFileName = StringUtils.isNotEmpty(fh.getFileNameW()) ? fh.getFileNameW() : fh
                            .getFileNameString();
                    if (File.separator.equals("/")) {
                        subFileName = subFileName.substring(subFileName.lastIndexOf("/") + 1);
                    } else {
                        subFileName = subFileName.substring(subFileName.lastIndexOf("\\") + 1);
                    }
                    String subFileCanonicalPath = destDir + File.separator + subFileName;
                    File file = new File(subFileCanonicalPath);
                    checkFileSuffix(subFileName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    os = new FileOutputStream(file);
                    a.extractFile(fh, os);
                    IOUtils.closeQuietly(os);
                }
                fh = a.nextFileHeader();
            }
        } finally {
            try {
                if (a != null) {
                    a.close();
                }
            } catch (IOException ioe) {
                logger.error("Archive close exception", ioe);
            }
            IOUtils.closeQuietly(os);
        }
    }

    private void checkFileSuffix(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        List<String> validSuffix = Arrays.asList(".png", ".gif", ".jpg", ".jpeg", ".bmp");
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            throw new ServiceException("压缩包中包含无法识别的格式文件!");
        }
        String suffix = fileName.substring(index).toLowerCase();
        if (!validSuffix.contains(suffix)) {
            throw new ServiceException("压缩包中包含不支持的格式文件,支持的格式: .png .gif .jpg .jpeg .bmp");
        }
    }

    public List<SpeakerSpeech> findSpeakerSpeechForConditions(String speechIds) {
        return dao.findSpeakerSpeechForConditions(speechIds, UserUtils.getUser().getId());
    }

}