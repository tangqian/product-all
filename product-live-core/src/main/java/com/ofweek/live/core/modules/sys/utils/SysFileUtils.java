package com.ofweek.live.core.modules.sys.utils;

import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.service.SysFileService;

public class SysFileUtils {

	private static SysFileService sysFileService = SpringContextHolder.getBean(SysFileService.class);

	public static SysFile get(String id) {
		return sysFileService.get(id);
	}

	public enum TempEnum {
		NO(0, "否"), YES(1, "是");

		private Integer code;

		private String meaning;

		TempEnum(Integer code, String meaning) {
			this.code = code;
			this.meaning = meaning;
		}

		/**
		 * @return 返回 code
		 */
		public Integer getCode() {
			return code;
		}

		/**
		 * @return 返回 meaning
		 */
		public String getMeaning() {
			return meaning;
		}

		public static boolean isNotTemp(Integer isTemp) {
			return isTemp != null && isTemp == TempEnum.NO.code;
		}
	}

	public enum TypeEnum {
		SYS_INDEX(1000, "sys/index", 0, "首页焦点图片"),

		AUDIENCE_LOGO(10000, "audience/logo", 0, "观众头像"),

		ROOM_COVER(20000, "room/cover", 0, "直播房间封面图"),
		ROOM_REVIEW(21000, "room/review", 0, "直播房间回顾图"),
		ROOM_REVIEW_VIDEO(50000, "room/video", 1, "直播房间回顾视频"),
		
		SPEAKER_LOGO(30000, "speaker/logo", 0, "主播头像"),
		SPEAKER_DATA(31000, "speaker/data", 1, "主播资料"),
		SPEAKER_SPEECH(32000, "speaker/speech", 1, "主播演讲稿"),
		SPEAKER_VIDEO(33000, "speaker/video", 1, "主播视频"),
		SPEAKER_VIDEO_COVER(33100, "speaker/videoCover", 0, "主播视频封面图"),
		SPEAKER_RICH_TEXT(34000, "speaker/richText", 0, "主播图片直播上传图片"),
		
		WAITER_LOGO(40000, "waiter/logo", 0, "客服头像");

		/**
		 * code值由两部分组成， 1位逻辑分类+4位业务细项分类组成。<br/>
		 * 比如直播房间相关的文件上传都为1XXXX，主播相关的文件上传都为2XXXX，以此类推。<br/>
		 * 各业务子项按照从高位到低位编码方式，如20XXX，21XXX。
		 * 
		 */
		private Integer code;

		private String classifyDir;

		private String meaning;

		/**
		 * 文件类型 0:图片,1:文件
		 */
		private  Integer fileType;

		/**
		 * <默认构造函数>
		 */
		TypeEnum(Integer code, String classifyDir, Integer fileType, String meaning) {
			this.code = code;
			this.classifyDir = classifyDir;
			this.fileType = fileType;
			this.meaning = meaning;
		}

		/**
		 * @return 返回 code
		 */
		public Integer getCode() {
			return code;
		}

		/**
		 * @return 返回 meaning
		 */
		public String getMeaning() {
			return meaning;
		}

		public String getClassifyDir() {
			return classifyDir;
		}

		/**
		 * 可理解的名称
		 * 
		 * @param code
		 * @return
		 */
		public static String readable(Integer code) {
			TypeEnum ret = getEnum(code);
			return ret == null ? "" : ret.meaning;
		}

		/**
		 * 根据code获取到对应的枚举实例
		 * @param code
		 * @return
		 */
		public static TypeEnum from(Integer code) {
			return getEnum(code);
		}

		public static boolean isCacheable(Integer code){
			return code == null || code != SPEAKER_VIDEO.code;
		}

		private  static  TypeEnum getEnum(Integer code){
			TypeEnum ret = null;
			for (TypeEnum e : TypeEnum.values()) {
				if (e.code.equals(code)) {
					ret = e;
					break;
				}
			}
			return ret;
		}

		public Integer getFileType() {
			return fileType;
		}

		public void setFileType(Integer fileType) {
			this.fileType = fileType;
		}
	}
}
