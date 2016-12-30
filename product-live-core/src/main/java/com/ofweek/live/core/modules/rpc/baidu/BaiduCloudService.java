package com.ofweek.live.core.modules.rpc.baidu;

import com.baidubce.services.lss.model.CreateSessionResponse;
import com.baidubce.services.lss.model.GetSessionResponse;
import com.baidubce.services.lss.model.ListSessionsResponse;
import com.baidubce.services.lss.model.RefreshSessionResponse;
import com.baidubce.services.vod.model.GenerateMediaDeliveryInfoResponse;
import com.baidubce.services.vod.model.ListMediaResourceResponse;


public interface BaiduCloudService {
	
	public CreateSessionResponse createPushSession(String description, String preset, String notification, String securityPolicy, String recording);

	public ListSessionsResponse listSessions();
	
	public GetSessionResponse getSession(String sessionId);
	
	public RefreshSessionResponse refreshSession(String sessionId);
	
	public void startRecording(String sessionId, String recording);
	
	public void stopRecording(String sessionId);

	public ListMediaResourceResponse listMediaResource(String sessionId);

	public ListMediaResourceResponse getMediaResourceResponse(String title);

	public GenerateMediaDeliveryInfoResponse generateMediaDeliveryInfo(String mediaId);

}