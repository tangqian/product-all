package com.ofweek.live.web.api.action;

import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.base.entity.Result;
import com.ofweek.live.core.modules.base.enums.ResultBodyEnum;
import com.ofweek.live.core.modules.room.dto.RoomDto;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.sys.dto.HomeBannerDto;
import com.ofweek.live.core.modules.sys.service.HomeBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/index/")
public class ApiIndexController extends BaseController {

    @Autowired
    private RoomService roomService;

	@Autowired
	private HomeBannerService homeBannerService;

    @RequestMapping(value = {"getIndexLiveList"})
    public Result<Object> getIndexLiveList(@RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "offset", required = false) Integer offset, HttpServletRequest request) {
        Result<Object> result = new Result<Object>();
        try {
            List<RoomDto> room = roomService.findIndexLiveList(status, offset);
            result.setData(room);
        } catch (Exception e) {
            setError(result, RoomDto.class);
            e.printStackTrace();
        }
        return result;
    }

	@RequestMapping(value = {"getIndexBannerList"})
	public Result<Object> getIndexBannerList(HttpServletRequest request) {
		Result<Object> result = new Result<Object>();
		try {
			List<HomeBannerDto> hList = homeBannerService.findIndexBannerList();
			result.setData(hList);
		} catch (Exception e) {
			setError(result, HomeBannerDto.class);
			e.printStackTrace();
		}
		return result;
	}
	
	private static void setError(Result<Object> result, Class<?> object) {
		result.setStatus(false);
		try {
			if (object.newInstance() instanceof HomeBannerDto) {
				result.setErrorCode(ResultBodyEnum.OFWEEK_LIVE_BANNER_ERROR.getCode());
				result.setErrorMsg(ResultBodyEnum.OFWEEK_LIVE_BANNER_ERROR.getMeaning());
			} else if (object.newInstance() instanceof RoomDto) {
                result.setErrorCode(ResultBodyEnum.OFWEEK_LIVE_ERROR.getCode());
                result.setErrorMsg(ResultBodyEnum.OFWEEK_LIVE_ERROR.getMeaning());
            }
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
