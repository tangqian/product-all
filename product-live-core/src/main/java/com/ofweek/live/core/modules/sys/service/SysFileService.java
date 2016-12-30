package com.ofweek.live.core.modules.sys.service;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.CacheUtils;
import com.ofweek.live.core.common.utils.DateUtils;
import com.ofweek.live.core.common.utils.FileUtils;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.utils.image.CompressCenter;
import com.ofweek.live.core.common.utils.image.PictureCompressionUtils;
import com.ofweek.live.core.modules.sys.dao.SysFileDao;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.utils.FileServiceUtils;
import com.ofweek.live.core.modules.sys.utils.RandomUtils;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils.TypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SysFileService extends CrudService<SysFileDao, SysFile> {

    public static final String FILE_CACHE = "fileCache";

    public static final String FILE_CACHE_ID_ = "id_";

    @Override
    public SysFile get(String id) {
        SysFile file = (SysFile) CacheUtils.get(FILE_CACHE, FILE_CACHE_ID_ + id);
        if (file == null) {
            file = super.get(id);
            if (file != null && TypeEnum.isCacheable(file.getSubjectType())) {
                CacheUtils.put(FILE_CACHE, FILE_CACHE_ID_ + id, file);
            }
        }
        return file;
    }

    @Override
    @Transactional(readOnly = false)
    public void save(SysFile entity) {
        throw new UnsupportedOperationException();
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void saveFile(SysFile entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("SysFile"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    @Transactional(readOnly = false)
    public SysFile save(String tempId, TypeEnum typeEnum) throws IOException {
        if (StringUtils.isBlank(tempId)) {
            return null;
        }
        SysFile tempFile = get(tempId);
        if (SysFileUtils.TempEnum.isNotTemp(tempFile.getIsTemp())) {//非临时文件，直接返回
            return tempFile;
        }

        String targetId = SequenceUtils.getNextString("SysFile");
        String targetUri = generatePath(typeEnum) + generateNewName(targetId, tempFile.getExt());
        if (typeEnum == TypeEnum.SPEAKER_RICH_TEXT) {
            PictureCompressionUtils.equalRatioSize(getAbsolutePath(tempFile.getUri()), getAbsolutePath(targetUri), 900, 900);
            CompressCenter.doCompressImage(targetId, targetUri, 640, 640, CompressCenter.CompressTypeEnum.SMALL_IMAGE);
        } else {
            FileUtils.copyFile(getAbsolutePath(tempFile.getUri()), getAbsolutePath(targetUri));
        }

        SysFile entity = new SysFile();
        entity.setId(targetId);
        entity.setUri(targetUri);
        entity.setOriginalName(tempFile.getOriginalName());
        entity.setSize(tempFile.getSize());
        entity.setExt(tempFile.getExt());
        entity.setIsTemp(SysFileUtils.TempEnum.NO.getCode());
        entity.setType(typeEnum.getFileType());
        entity.setSubjectType(typeEnum.getCode());

        entity.preInsert();
        dao.insert(entity);
        return entity;
    }

    @Transactional(readOnly = false)
    public SysFile saveTemp(MultipartFile file) throws IOException {
        if (file == null || file.getSize() == 0)
            return null;

        String fileName = file.getOriginalFilename();
        String ext = FileServiceUtils.getExt(fileName);

        String relativePath = "temp/" + DateUtils.formatDate(new Date(), "yyyy-MM/dd/");
        String id = SequenceUtils.getNextString("SysFile");
        String newFileName = generateNewName(id, ext);

        File targetFile = new File(getAbsolutePath(relativePath), newFileName);
        targetFile.mkdirs();
        file.transferTo(targetFile);

        SysFile entity = new SysFile();
        entity.setId(id);
        entity.setUri(relativePath + newFileName);
        entity.setOriginalName(fileName);
        entity.setSize((int) file.getSize());
        entity.setExt(ext);
        entity.setIsTemp(SysFileUtils.TempEnum.YES.getCode());
        entity.setType(0);
        entity.setSubjectType(-1);

        entity.preInsert();
        dao.insert(entity);
        return entity;
    }

    public String generateNewName(String id, String ext) {
        String timeIdStr = id + DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        return StringUtils.isEmpty(ext) ? timeIdStr : timeIdStr + "." + ext;
    }

    public String getAbsolutePath(String relativePath) {
        return LiveEnv.getUploadRoot() + relativePath;
    }

    /**
     * 生成存放路径，格式=分类名/yyyy-MM/ ,例audience/logo/2016-08
     *
     * @param typeEnum
     * @return
     */
    public String generatePath(TypeEnum typeEnum) {
        StringBuilder sb = new StringBuilder(typeEnum.getClassifyDir()).append('/').append(DateUtils.formatDate(new Date(), "yyyy-MM/"));
        return sb.toString();
    }

    private String generateRandomName(String ext) {
        return StringUtils.isEmpty(ext) ? RandomUtils.getRandomString(10) : RandomUtils.getRandomString(10) + "." + ext;
    }

    public List<SysFile> findByParentId(String parentId) {
        SysFile file = new SysFile();
        file.setParentId(parentId);
        return dao.findList(file);
    }

    /**
     * 删除三天前的临时文件
     */
    @Transactional(readOnly = false)
    public void clearTempFile() {
        Date endDay = DateUtils.addDays(new Date(), -3);
        logger.error(">>>>>>start clear temp file before date={}", DateUtils.formatDate(endDay, "yyyy-MM-dd HH:mm:ss"));
        List<SysFile> files = dao.findTempList(endDay);
        files.forEach(f -> {
            try {
                new File(getAbsolutePath(f.getUri())).delete();
            } catch (Exception e) {
            }
            dao.delete(f);
        });
        logger.error(">>>>>>end clear temp file");
    }

    @Transactional(readOnly = false)
    public SysFile saveTempAndFormal(MultipartFile file, String operationType) throws IOException {
        return save(saveTemp(file).getId(), operationType.equals("speakerVideo") ? SysFileUtils.TypeEnum.SPEAKER_VIDEO : SysFileUtils.TypeEnum.ROOM_REVIEW_VIDEO);
    }
}
