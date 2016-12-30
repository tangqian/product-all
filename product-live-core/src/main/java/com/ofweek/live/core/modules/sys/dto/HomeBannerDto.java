package com.ofweek.live.core.modules.sys.dto;

import java.io.Serializable;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.modules.sys.entity.HomeBanner;

public class HomeBannerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String bannerImgUrl;

	private String href;

	public HomeBannerDto() {

	}

	public HomeBannerDto(HomeBanner banner, String bannerImgUrl) {
		id = banner.getId();
		href = banner.getHref();
		this.bannerImgUrl = bannerImgUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBannerImgUrl() {
		return bannerImgUrl;
	}

	public void setBannerImgUrl(String bannerImgUrl) {
		this.bannerImgUrl = bannerImgUrl;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
