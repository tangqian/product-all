package com.ofweek.live.core.modules.rpc.common;

import com.ofweek.live.core.common.config.Global;
import com.ofweek.live.core.common.security.Digests;
import com.ofweek.live.core.common.security.shiro.session.SessionDAO;
import com.ofweek.live.core.common.service.BaseService;
import com.ofweek.live.core.common.utils.DateUtils;
import com.ofweek.live.core.common.utils.Encodes;
import com.ofweek.live.core.modules.rpc.InvokeExpo;
import com.ofweek.live.core.modules.rpc.InvokeWebinar;
import com.ofweek.live.core.modules.rpc.common.dto.LiveVo;
import com.ofweek.live.core.modules.rpc.dto.ExhibitionDto;
import com.ofweek.live.core.modules.rpc.dto.ExpoExhibitionsDto;
import com.ofweek.live.core.modules.rpc.dto.WebinarMeetingsDto;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.security.SystemAuthorizingRealm;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 远程过程调用服务类
 * @author tangqian
 * 
 */
@Service
public class RpcService extends BaseService {

	@Resource
	private ExecutorService executorService;

	/**
	 * 已结束直播显示数量限制(只针对在线展和研讨会)
	 */
	private static final int overLiveShowLimit = 5;

	/**
	 * 展会网直播，后台定时调用接口存入数据，下标0,1分别存入未结束，已结束展会。
	 */
	private static LiveVo[][] expoLives = new LiveVo[2][];

	/**
	 * 研讨会直播，后台定时调用接口存入数据，下标0,1分别存入未结束，已结束研讨会。
	 */
	private static LiveVo[][] webinarLives = new LiveVo[2][];

	static {
		LiveVo[] emptyLive = new LiveVo[0];
		for (int i = 0; i < expoLives.length; i++) {
			webinarLives[i] = expoLives[i] = emptyLive;
		}
	}

	/**
	 * 用quartz定时器调用此方法，来更新首页数据
	 */
	public void updateHomePageData() {
		logger.error(">>>>>>start update homePage data");
		doExpoInvoke();
		doWebinarInvoke();
		logger.error(">>>>>>end update homePage data");
	}

	private void doExpoInvoke() {
		executorService.submit(() -> {
			try {
				ExpoExhibitionsDto dtos = InvokeExpo.getNotOverExhibitions();
				if (dtos != null) {
					expoLives[0] = fromExpo(dtos.getData(), false);

					dtos = InvokeExpo.getOverExhibitions();
					expoLives[1] = fromExpo(dtos.getData(), true);
				}
			} catch (Exception e) {
				logger.error("首页调用展会接口失败!", e);
			}
		});
	}

	private void doWebinarInvoke() {
		executorService.submit(() -> {
			try {
				WebinarMeetingsDto dtos = InvokeWebinar.getMeetings(1);
				if (dtos != null) {
					List<WebinarMeetingsDto.WebinarActivity> datas = dtos.getActivity();
					dtos = InvokeWebinar.getMeetings(0);
					datas.addAll(dtos.getActivity());
					webinarLives[0] = fromWebinar(datas, false);

					dtos = InvokeWebinar.getMeetings(2);
					if (dtos != null)
						webinarLives[1] = fromWebinar(dtos.getActivity(), true);
				}
			} catch (Exception e) {
				logger.error("首页调用研讨会接口失败!", e);
			}
		});
	}

	private LiveVo[] fromWebinar(List<WebinarMeetingsDto.WebinarActivity> data, boolean isOver) {
		int size = isOver ? Math.min(data.size(), overLiveShowLimit) : data.size();
		LiveVo[] liveVos = new LiveVo[size];
		for (int i = 0; i < size; i++) {
			liveVos[i] = new LiveVo(data.get(i));
		}
		return liveVos;
	}

	private LiveVo[] fromExpo(List<ExhibitionDto> data, boolean isOver) {
		int size = isOver ? Math.min(data.size(), overLiveShowLimit) : data.size();
		LiveVo[] liveVos = new LiveVo[size];
		for (int i = 0; i < size; i++) {
			liveVos[i] = new LiveVo(data.get(i));
		}
		return liveVos;
	}
}
