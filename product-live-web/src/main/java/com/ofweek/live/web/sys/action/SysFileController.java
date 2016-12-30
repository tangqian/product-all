/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ofweek.live.web.sys.action;

import com.ofweek.live.core.common.utils.DateUtils;
import com.ofweek.live.core.common.utils.FastJsonUtils;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.utils.image.CompressCenter;
import com.ofweek.live.core.common.utils.image.PictureCompressionUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.sys.dao.SysFileDao;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.service.SysFileService;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典Controller
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "/sys/file")
public class SysFileController extends BaseController {

    @Resource
    private SysFileService sysFileService;

    @Resource
    private SysFileDao sysFileDao;

    @PostConstruct
    public void initSync() {
        Date limit = DateUtils.parseDate("2016-11-02");
        List<SysFile> files = sysFileDao.findListBySubjectType(SysFileUtils.TypeEnum.SPEAKER_RICH_TEXT.getCode(), limit);
        for (SysFile sysFile : files) {
            String uri = sysFile.getUri();
            String sourceImgPath = sysFileService.getAbsolutePath(uri);
            File srcFile = new File(sourceImgPath);
            if (srcFile.isFile()) {
                String targetUri = CompressCenter.getCompressUri(uri, CompressCenter.CompressTypeEnum.SMALL_IMAGE);
                File targetFile = new File(sysFileService.getAbsolutePath(targetUri));
                if (targetFile.exists()) {
                    CompressCenter.put(sysFile.getId(), targetUri, CompressCenter.CompressTypeEnum.SMALL_IMAGE);
                } else {
                    if (PictureCompressionUtils.equalRatioSize(srcFile, targetFile, 640, 640)) {
                        CompressCenter.put(sysFile.getId(), targetUri, CompressCenter.CompressTypeEnum.SMALL_IMAGE);
                    }
                }
            }
        }
    }

    @ModelAttribute
    public SysFile get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return sysFileService.get(id);
        } else {
            return new SysFile();
        }
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "upload")
    public Object upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        SysFile sysFile = null;
        try {
            sysFile = sysFileService.saveTemp(file);
            map.put("fileId", sysFile.getId());
            map.put("status", "0");
            map.put("uri", sysFile.getUri());
        } catch (IOException e) {
            logger.error("上传文件失败,IO异常", e);
            map.put("status", "-1");
        } catch (Exception e) {
            logger.error("上传文件失败，服务器异常", e);
            map.put("status", "-2");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(FastJsonUtils.toJSONString(map));
        return null;
    }

    @RequestMapping(value = "secretUpload")
    public Object secretUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        return upload(file, response);
    }

    /**
     * 提供给直播房间上传图片用
     *
     * @param file
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "secretRoomUpload")
    public Object secretRoomUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        SysFile sysFile = null;
        try {
            sysFile = sysFileService.saveTemp(file);
            sysFile = sysFileService.save(sysFile.getId(), SysFileUtils.TypeEnum.SPEAKER_RICH_TEXT);
            map.put("fileId", sysFile.getId());
            map.put("status", "0");
            map.put("url", sysFile.getUrl());
        } catch (IOException e) {
            logger.error("上传图文直播图片失败,IO异常", e);
            map.put("status", "-1");
        } catch (Exception e) {
            logger.error("上传图文直播图片失败，服务器异常", e);
            map.put("status", "-2");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(FastJsonUtils.toJSONString(map));
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "secretSave")
    public Map<String, String> secretSave(@RequestParam(required = true) String fileId, @RequestParam(required = true) Integer type) throws Exception {
        SysFile sysFile = sysFileService.save(fileId, SysFileUtils.TypeEnum.from(type));
        Map<String, String> map = new HashMap<>(4);
        map.put("id", sysFile.getId());
        return map;
    }

    /**
     * 提供上传接口,将上传对象保存为临时数据和正式数据
     *
     * @param file
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresPermissions("user")
    public Object upload2(@RequestParam("file") MultipartFile file, String operationType, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        SysFile sysFile = null;
        try {
            sysFile = sysFileService.saveTempAndFormal(file, operationType);
            map.put("fileId", sysFile.getId());
            map.put("status", "0");
            map.put("uri", sysFile.getUri());
        } catch (IOException e) {
            logger.error("上传文件失败,IO异常", e);
            map.put("status", "-1");
        } catch (Exception e) {
            logger.error("上传文件失败，服务器异常", e);
            map.put("status", "-2");
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(FastJsonUtils.toJSONString(map));
        return null;
    }

    @RequestMapping(value = "secretUpload2")
    public Object secretUpload2(@RequestParam("file") MultipartFile file, String operationType, HttpServletResponse response) throws Exception {
        return upload2(file, operationType, response);
    }

}
