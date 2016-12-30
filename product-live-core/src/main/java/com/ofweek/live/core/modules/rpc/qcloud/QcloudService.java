package com.ofweek.live.core.modules.rpc.qcloud;

import com.ofweek.live.core.modules.base.entity.MethodInvokeResult;
import com.ofweek.live.core.modules.rpc.qcloud.dto.QcloudVodPlayInfo;
import com.qcloud.api.common.CloudBaseResponse;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse;
import com.qcloud.api.modules.live.dto.DescribeLVBChannelListResponse;
import com.qcloud.api.modules.live.dto.DescribeLVBChannelResponse;
import org.apache.poi.ss.formula.functions.T;

public interface QcloudService {
	
	QcloudVodPlayInfo getPlayInfo(String fileName);

	public CreateLVBChannelResponse releaseLive(Integer boothId);

	public CloudBaseResponse deleteLive(String channelId);
	
	DescribeLVBChannelListResponse getChannels(Integer status);
	
	DescribeLVBChannelResponse getChannel(String channelId);

	/**
	 * 同步直播房间主播上传的视频到云上
	 * @return
	 */
	MethodInvokeResult<T> syncVideo();

	/**
	 * 同步直播房间回放视频到云上
	 * @return
	 */
	MethodInvokeResult<T> syncReviewVideo();

}