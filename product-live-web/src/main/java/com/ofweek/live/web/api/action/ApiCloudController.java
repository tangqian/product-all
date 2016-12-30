package com.ofweek.live.web.api.action;

import com.baidubce.services.vod.model.GenerateMediaDeliveryInfoResponse;
import com.ofweek.live.core.modules.base.entity.MethodInvokeResult;
import com.ofweek.live.core.modules.room.service.RoomReviewDataService;
import com.ofweek.live.core.modules.rpc.baidu.BaiduCloudService;
import com.ofweek.live.core.modules.rpc.qcloud.QcloudService;
import com.ofweek.live.web.api.action.dto.MediaInfoDto;
import com.ofweek.live.web.api.common.CodeEnum;
import com.ofweek.live.web.api.common.HttpCommonResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/cloud")
public class ApiCloudController extends ApiBaseController {

    @Resource
    private QcloudService qcloudService;

    @Resource
    private BaiduCloudService baiduCloudService;

    @Resource
    private RoomReviewDataService roomReviewDataService;

    @RequestMapping(value = {"videoSync"})
    public HttpCommonResponse<Object> videoSync() {
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();

        MethodInvokeResult<T> methodResult = qcloudService.syncVideo();
        if (!methodResult.isSuccess()) {
            result.setCode(CodeEnum.SERVER_ERROR);
        }
        result.setMessage(methodResult.getMsg());
        return result;
    }

    /**
     * 从百度云上同步获取回放视频
     * @return
     */
    @RequestMapping(value = {"reviewVideoSync"})
    public HttpCommonResponse<Object> reviewVideoSync() {
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();

        MethodInvokeResult<T> methodResult = qcloudService.syncVideo();
        if (!methodResult.isSuccess()) {
            result.setCode(CodeEnum.SERVER_ERROR);
        }
        result.setMessage(methodResult.getMsg());
        return result;
    }

    /**
     * 查询媒资分发信息
     * @return
     */
    @RequestMapping(value = {"getMediaInfo"})
    public HttpCommonResponse<Object> getMediaInfo(@RequestParam(value = "mediaId", required = true) String mediaId) {
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();
        GenerateMediaDeliveryInfoResponse response = baiduCloudService.generateMediaDeliveryInfo(mediaId);
        MediaInfoDto dto = new MediaInfoDto();
        if(response != null){
            dto.setSourceUrl(response.getFile());
            dto.setCoverPage(response.getCover());
        }
        result.setData(dto);
        return result;
    }

    /**
     * 从百度云上同步获取回放视频
     * @return
     */
    @RequestMapping(value = {"syncBCloud"})
    public HttpCommonResponse<Object> syncBCloud() {
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();

        boolean isSuccess = roomReviewDataService.syncBCloud();
        if (!isSuccess) {
            result.setCode(CodeEnum.SERVER_ERROR);
        }
        result.setMessage(CodeEnum.SUCCESS.getMeaning());
        return result;
    }

}
