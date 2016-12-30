package com.ofweek.live.web.sys.action;

import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.report.entity.ReportDataDownload;
import com.ofweek.live.core.modules.report.service.ReportDataDownloadService;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDataDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by tangqian on 2016/9/6.
 */
@Controller
@RequestMapping(value = "/download")
public class DownloadController extends BaseController {

    @Resource
    private SpeakerDataDao speakerDataDao;

    @Resource
    private UserService userService;

    @Resource
    private RoomDao roomDao;

    @Resource
    private ReportDataDownloadService reportDataDownloadService;

    @RequestMapping({"room/data"})
    public void roomData(@RequestParam(required = true) String id,
                         @RequestParam(required = true) String roomId,
                         @RequestParam(required = true) Integer mode,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            SpeakerData data = speakerDataDao.get(id);
            if (!RoomModeEnum.isPreview(mode)) {
                if (toSave(roomId)) {
                    ReportDataDownload download = new ReportDataDownload();
                    download.setRoomId(roomId);
                    download.setType(1);
                    download.setUserId(UserUtils.getUser().getId());
                    download.setDataId(id);
                    download.setName(data.getName());
                    reportDataDownloadService.save(download);
                }
            }

            SysFile sysFile = sysFileService.get(data.getFileId());
            File file = new File(sysFileService.getAbsolutePath(sysFile.getUri()));
            response.setHeader("content-length", Long.toString(file.length()));
            String filename = getFileName(request, data.getName());
            response.setHeader("Content-Disposition", "attachment;" + filename);
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            logger.error("直播房间资料下载失败", e);
        }
    }

    @RequestMapping({"speaker/data"})
    public void speakerData(@RequestParam(required = true) String id, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            SpeakerData data = speakerDataDao.get(id);
            SysFile sysFile = sysFileService.get(data.getFileId());
            File file = new File(sysFileService.getAbsolutePath(sysFile.getUri()));
            response.setHeader("content-length", Long.toString(file.length()));
            String filename = getFileName(request, data.getName());
            response.setHeader("Content-Disposition", "attachment;" + filename);
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            logger.error("直播公司资料下载失败", e);
        }
    }

    /**
     * 获取文件名，主要是解决不同浏览器兼容性的问题
     *
     * @param request
     * @param originalFileName 原始名称
     * @return
     */
    private String getFileName(HttpServletRequest request, String originalFileName) throws UnsupportedEncodingException {
        String name = "filename=";
        originalFileName = originalFileName.replace(" ", "");//去掉文件名中的空格
        if (isMSIE(request)) {
            name += java.net.URLEncoder.encode(originalFileName, "UTF-8");
        } else {
            name += new String(originalFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        return name;
    }

    private static boolean isMSIE(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        boolean isMSIE = (agent.contains("MSIE") || agent.contains("Trident"));
        return isMSIE;
    }

    /**
     * 是否保存下载记录到数据库中
     *
     * @param roomId
     * @return
     */
    private boolean toSave(String roomId) {
        boolean toSave = true;
        GeneralUser user = userService.getGeneralUser(UserUtils.getUser().getId());
        // 用户所在公司名称中能含有ofweek字样时，则toSave为false,即不入库
        if (user.getCompany() != null && user.getCompany().toLowerCase().contains("ofweek")) {
            toSave = false;
        } else {
            Room room = roomDao.get(roomId);
            if (userService.isRoomEmployee(user.getId(), user.getType(), room.getSpeakerId())) {
                toSave = false;
            }
        }
        return toSave;
    }
}
