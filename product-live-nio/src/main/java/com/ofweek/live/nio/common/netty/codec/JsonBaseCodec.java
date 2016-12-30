package com.ofweek.live.nio.common.netty.codec;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.BaseMessage.StatusCodeEnum;
import com.ofweek.live.nio.common.utils.NioFastJsonUtils;

/**
 * @author tangqian
 */
public class JsonBaseCodec extends MessageToMessageCodec<String, BaseMessage> {

    private final static Logger logger = LoggerFactory.getLogger(JsonBaseCodec.class);

    private static final Object PLACEHOLDER_OBJ = new Object();// 占位对象

    private static final int MSG_TYPE_LENGTH = 1;

    private static final int MSG_NO_LENGTH = 4;

    @SuppressWarnings("unused")
    private static final int REQUEST = 1;

    private static final int RESPONSE = 2;

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        if (msg.length() < MSG_TYPE_LENGTH + MSG_NO_LENGTH) {
            logger.error("消息不符号规范");
            sendExceptionResponse(ctx);
            return;
        }

        BaseMessage receivedMsg = null;
        try {
            String jsonMsg = msg.substring(5);
            logger.info("服务端收到:{}", msg);
            receivedMsg = NioFastJsonUtils.parseObject(jsonMsg, BaseMessage.class);
            receivedMsg.setMsgType(Integer.valueOf(msg.substring(0, 1)));
            receivedMsg.setMsgNo(Integer.valueOf(msg.substring(1, 5)));
            out.add(receivedMsg);
        } catch (Exception e) {
            logger.error("消息解析出错, receive msg = {}", msg);
            logger.error("异常信息为：", e);
            sendExceptionResponse(ctx);
        }
    }

    private void sendExceptionResponse(ChannelHandlerContext ctx) {
        BaseMessage response = new BaseMessage();
        response.setMsgType(2);
        response.setMsgNo(MsgNoEnum.SYS_BAD_REQUEST.code());
        response.setStatusCode(StatusCodeEnum.BAD_REQUEST);
        response.setMessage("请求格式不对");
        ctx.channel().writeAndFlush(response);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage msg, List<Object> out) throws Exception {
        try {
            initResponse(msg);
            StringBuilder sb = new StringBuilder();
            sb.append(msg.getMsgType()).append(StringUtils.leftPad(Integer.toString(msg.getMsgNo()), 4, '0'))
                    .append(NioFastJsonUtils.toJSONString(msg));
            logger.info("服务端发送:{}", sb.toString());
            out.add(sb.toString());
        } catch (Exception e) {
            logger.error("消息序列化为json串出错，异常信息为：", e);
        }
    }

    private void initResponse(BaseMessage response) {
        response.setMsgType(RESPONSE);
        if (response.getStatusCode() == null)
            response.setStatusCode(StatusCodeEnum.OK);

        Object body = response.getBody();
        if (body == null || (body instanceof String && StringUtils.isBlank((String) body))) {
            response.setBody(PLACEHOLDER_OBJ);
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String msg = "20003{\"msgId\":\"40\",\"body\":{},\"message\": null,\"status\":400}";
        JsonBaseCodec encoder = new JsonBaseCodec();
        encoder.decode(null, msg, null);
        encoder.encode(null, null, null);
    }
}
