package com.ofweek.live.core.modules.rpc.qcloud.dto;

/**
 * @author tangqian
 *
 */
public class QcloudVodPlayInfo {

	private String fileId;
	
	/**
	 * 原始播放地址
	 */
	private String originalPlayUrl;
	
	private String mp4PlayUrl;
	
	public QcloudVodPlayInfo(String fileId, String originalPlayUrl, String mp4PlayUrl) {
		this.fileId = fileId;
		this.originalPlayUrl = originalPlayUrl;
		this.mp4PlayUrl = mp4PlayUrl;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getOriginalPlayUrl() {
		return originalPlayUrl;
	}

	public void setOriginalPlayUrl(String originalPlayUrl) {
		this.originalPlayUrl = originalPlayUrl;
	}

	public String getMp4PlayUrl() {
		return mp4PlayUrl;
	}

	public void setMp4PlayUrl(String mp4PlayUrl) {
		this.mp4PlayUrl = mp4PlayUrl;
	}
	
}
