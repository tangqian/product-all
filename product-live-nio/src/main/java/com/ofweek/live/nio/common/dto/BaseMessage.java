package com.ofweek.live.nio.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author tangqian
 *
 */
public class BaseMessage implements Serializable {

	private static final long serialVersionUID = -5984872972282890337L;

	@JSONField(ordinal = 1)
	private String msgId;

	@JSONField(serialize = false)
	private Integer msgType;

	@JSONField(serialize = false)
	private Integer msgNo;

	private Date timestamp;

	private StatusCodeEnum statusCode;

	@JSONField(ordinal = 3)
	private String message;

	@JSONField(ordinal = 2)
	private Object body;

	public static enum StatusCodeEnum {
		OK(200, "正常"), BAD_REQUEST(400, "请求异常"), SERVER_ERROR(500, "服务器错误");

		private static final Map<Integer, StatusCodeEnum> CODE_MAP = new HashMap<>();

		static {
			for (StatusCodeEnum typeEnum : StatusCodeEnum.values()) {
				CODE_MAP.put(typeEnum.getCode(), typeEnum);
			}
		}

		StatusCodeEnum(int code, String meaning) {
			this.code = code;
			this.meaning = meaning;
		}

		public int getCode() {
			return code;
		}

		public String getMeaning() {
			return meaning;
		}

		public static StatusCodeEnum getEnum(Integer code) {
			return CODE_MAP.get(code);
		}

		private final int code;
		private final String meaning;
	}
	
	public static BaseMessage getResponseException(BaseMessage request){
		return makeInstance(request, StatusCodeEnum.SERVER_ERROR);
	}
	
	public static BaseMessage getResponseOk(BaseMessage request){
		return makeInstance(request, StatusCodeEnum.OK);
	}
	
	public static BaseMessage getNotification(){
		BaseMessage response = new BaseMessage();
		response.setTimestamp(new Date());
		response.setMsgId("");
		response.setMsgType(2);
		response.setStatusCode(StatusCodeEnum.OK);
		return response;	
	}
	
	private static BaseMessage makeInstance(BaseMessage request, StatusCodeEnum code){
		BaseMessage response = new BaseMessage();
		response.setTimestamp(new Date());
		response.setMsgId(request.getMsgId());
		response.setMsgNo(request.getMsgNo());
		response.setMsgType(2);
		response.setStatusCode(code);
		return response;		
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Integer getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(Integer msgNo) {
		this.msgNo = msgNo;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@JSONField(name = "status", ordinal = 4)
	public int getStatus() {
		return statusCode.getCode();
	}

	@JSONField(serialize = false)
	public StatusCodeEnum getStatusCode() {
		return statusCode;
	}

	@JSONField(deserialize = false)
	public void setStatusCode(StatusCodeEnum statusCode) {
		this.statusCode = statusCode;
	}

	public Object getBody() {
		return body;
	}

	@JSONField(deserialize = false)
	public void setBody(Object body) {
		this.body = body;
	}
	
	@JSONField(name = "body")
	public void setBody(String body){
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
