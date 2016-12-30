package com.ofweek.live.nio.handlers.user;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.audience.service.AudienceRegisterService;
import com.ofweek.live.core.modules.base.exception.NioIllegalArgumentException;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.NioLoginEnum;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.Visitor;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.service.VisitorService;
import com.ofweek.live.core.modules.sys.utils.AccessKeyUtils;
import com.ofweek.live.core.modules.sys.utils.CallbackUtils;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.handler.HandlerListener;
import com.ofweek.live.nio.common.netty.RoomChannelContainer;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.sys.CountOnlineHandler;
import com.ofweek.live.nio.handlers.sys.CountPresentHandler;
import com.ofweek.live.nio.handlers.user.dto.LoginRequest;
import com.ofweek.live.nio.handlers.user.dto.LoginResponse;
import com.ofweek.live.nio.handlers.user.dto.NioUserDto;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoginHandler extends AbstractBaseHandler<LoginRequest> {

    @Resource
    private UserService userService;

    @Resource
    private RoomService roomService;

    @Resource
    private VisitorService visitorService;

    @Resource
    private AudienceRegisterService audienceRegisterService;

    @Resource
    private CountPresentHandler countPresentHandler;

    @Resource
    private CountOnlineHandler countOnlineHandler;

    private List<HandlerListener> handlerListeners = new ArrayList<>();

    private static final String APP_ID = LiveEnv.getAppId();

    @Override
    public int msgNo() {
        return MsgNoEnum.USER_LOGIN.code();
    }

    @Override
    protected Class<LoginRequest> getReqestBodyClass() {
        return LoginRequest.class;
    }

    @Override
    protected boolean isNeedCheckAuth() {
        return false;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, LoginRequest requestBody) {
        validate(requestBody);
        String clientIp = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
        requestBody.setClientIp(clientIp);

        LoginResponse response = new LoginResponse();
        Room room;
        LoginResponse.UserPart userPart;
        if (NioLoginEnum.isVisitorLogin(requestBody.getLoginType())) {// 匿名访问
            Visitor visitor = visitLogin(requestBody);
            room = enterRoom(visitor, requestBody, channel);

            userPart = new LoginResponse.UserPart(visitor);
            StringBuilder requestUri = new StringBuilder("/index/room/enter.xhtml?").append("roomId=").append(room.getId()).append("&mode=")
                    .append(requestBody.getMode());
            String callback = CallbackUtils.encode(requestUri.toString());
            userPart.setCallback(callback);
            userPart.setRegistered(false);
        } else {
            GeneralUser user = userLogin(requestBody);
            room = enterRoom(user, requestBody, channel);
            // 是否本房间的雇员
            boolean isEmployee = userService.isRoomEmployee(user.getId(), user.getType(), room.getSpeakerId());
            if (isEmployee) {
                NioUserUtils.setIsEmployee(channel, Boolean.TRUE);
            }
            userPart = new LoginResponse.UserPart(user);
            userPart.setEmployee(isEmployee);
            if (UserTypeEnum.isAudience(user.getType())) {
                userPart.setRegistered(audienceRegisterService.isRegistered(user.getId(), room.getId()));
            }
        }
        response.setUser(userPart);
        response.setAppId(APP_ID);

        RoomChannelContainer.addChannel(channel);
        response.setOnlineCount(countOnlineHandler.getCount(room.getId()));
        response.setPresentCount(countPresentHandler.getCount(room.getId()));
        return response;
    }

    private void validate(LoginRequest requestBody) throws NioIllegalArgumentException {
        if (requestBody == null || requestBody.getUserId() == null || requestBody.getRoomId() == null || requestBody.getMode() == null) {
            throw new NioIllegalArgumentException("参数不完整");
        }
        if (requestBody.getMode() <= 0 || requestBody.getMode() >= 5) {
            logger.error("socket用户登录模式不正确");
            throw new NioIllegalArgumentException("参数不正确");
        }

        String exceptedAk = AccessKeyUtils.encode(requestBody.getUserId(), requestBody.getNonce(), "ofweek");
        if (!StringUtils.equals(requestBody.getAk(), exceptedAk)) {
            logger.error("用户访问密钥错误，{}", requestBody);
            throw new NioIllegalArgumentException("访问密钥不正确，拒绝访问");
        }
    }

    private GeneralUser userLogin(LoginRequest requestBody) throws NioIllegalArgumentException {
        GeneralUser user = userService.getGeneralUser(requestBody.getUserId());
        if (user == null) {
            logger.error("用户不存在，{}", requestBody);
            throw new NioIllegalArgumentException("用户不存在");
        }
        return user;
    }

    private Visitor visitLogin(LoginRequest requestBody) {
        if (RoomModeEnum.isPreview(requestBody.getMode())) {
            logger.error("游客不能以预览模式进入房间");
            throw new NioIllegalArgumentException("参数不正确");
        }

        Visitor visitor = visitorService.get(requestBody.getUserId());
        if (visitor == null) {
            logger.error("游客不存在, {}", requestBody);
            throw new NioIllegalArgumentException("游客不存在");
        }
        return visitor;
    }

    private Room enterRoom(GeneralUser user, LoginRequest requestBody, Channel channel) {
        Integer mode = requestBody.getMode();
        Room room = roomService.enter(user, requestBody.getRoomId(), mode);
        NioUserUtils.setRoomId(channel, room.getId());
        NioUserUtils.setMode(channel, mode);
        NioUserUtils.setUser(channel, user);
        NioUserUtils.setCreateTime(channel, new Date());
        return room;
    }

    private Room enterRoom(Visitor visitor, LoginRequest requestBody, Channel channel) {
        Integer mode = requestBody.getMode();
        Room room = roomService.enter(visitor, requestBody.getRoomId(), mode);
        NioUserUtils.setRoomId(channel, room.getId());
        NioUserUtils.setMode(channel, mode);
        NioUserUtils.setVisitor(channel, visitor);
        NioUserUtils.setCreateTime(channel, new Date());
        return room;
    }

    @Override
    protected void sendNotifiaction(Channel channel, LoginRequest request, Object responseBody) {
        try {

            try {
                sendEnterRoomNotification(channel);
            } catch (Exception e) {
                logger.error("enter room notification failure.", e);
            }

            for (HandlerListener listener : handlerListeners) {
                try {
                    listener.notice(channel);
                } catch (Exception e) {
                    logger.error("发送观众进入房间后视频流定位通知失败", e);
                }
            }
        } catch (Exception e) {
            logger.error("发送登录成功后相关通知失败!", e);
        }
    }

    private void sendEnterRoomNotification(Channel channel) {
        NioUserDto notification;

        Visitor visitor = NioUserUtils.getVisitor(channel);
        if (visitor != null) {
            notification = new NioUserDto(visitor);
        } else {
            GeneralUser user = NioUserUtils.getUser(channel);
            notification = new NioUserDto(user);
            // 主办方账号不发通知
            if (UserTypeEnum.isAdmin(user.getType())) {
                return;
            }
            if (RoomChannelContainer.isOtherChannelInRoom(channel)) {// 已经有该用户的其它通道在线
                logger.error("该用户已经有其它端在线，不发送进入房间通知!, userId={}", user.getId());
                return;
            }
        }

        BaseMessage message = BaseMessage.getNotification();
        message.setMsgNo(MsgNoEnum.BROADCAST_USER_ENTER.code());
        message.setBody(notification);
        sendToRoomExcludeSelf(channel, message);
    }

    public void addHanlderListener(HandlerListener listener) {
        handlerListeners.add(listener);
    }

    public static final class NioUserUtils {
        private static final AttributeKey<String> ROOM_ID = AttributeKey.valueOf("roomId");
        private static final AttributeKey<Integer> MODE = AttributeKey.valueOf("mode");
        private static final AttributeKey<GeneralUser> USER = AttributeKey.valueOf("user");
        // 游客信息
        private static final AttributeKey<Visitor> VISITOR = AttributeKey.valueOf("visitor");
        // socket连接创建时间
        private static final AttributeKey<Date> CREATE_TIME = AttributeKey.valueOf("createTime");
        // 是否当前房间的雇员，不为空则表示是当前房间的雇员
        private static final AttributeKey<Boolean> IS_EMPLOYEE = AttributeKey.valueOf("isEmployee");

        private static void setRoomId(Channel channel, String roomId) {
            channel.attr(ROOM_ID).set(roomId);
        }

        public static void setUser(Channel channel, GeneralUser user) {
            channel.attr(USER).set(user);
        }

        private static void setVisitor(Channel channel, Visitor visitor) {
            channel.attr(VISITOR).set(visitor);
        }

        private static void setCreateTime(Channel channel, Date date) {
            channel.attr(CREATE_TIME).set(date);
        }

        private static void setIsEmployee(Channel channel, Boolean b) {
            channel.attr(IS_EMPLOYEE).set(b);
        }

        private static void setMode(Channel channel, Integer id) {
            channel.attr(MODE).set(id);
        }

        public static GeneralUser getUser(Channel channel) {
            return channel.attr(USER).get();
        }

        public static Visitor getVisitor(Channel channel) {
            return channel.attr(VISITOR).get();
        }

        public static Integer getMode(Channel channel) {
            return channel.attr(MODE).get();
        }

        public static Date getCreateTime(Channel channel) {
            return channel.attr(CREATE_TIME).get();
        }

        public static String getUserId(Channel channel) {
            GeneralUser user = getUser(channel);
            return user != null ? user.getId() : null;
        }

        public static String getRoomId(Channel channel) {
            return channel.attr(ROOM_ID).get();
        }

        public static Boolean getIsEmployee(Channel channel) {
            return channel.attr(IS_EMPLOYEE).get();
        }

        /**
         * 用户所在公司名称中能含有ofweek字样，或本房间的雇员时，则不需要入库，防止展商后台看到此操作用户的记录
         *
         * @param channel
         * @return
         */
        public static boolean isRecord(Channel channel) {
            boolean isRecord = true;
            if (getIsEmployee(channel) != null) {
                isRecord = false;
            }
            GeneralUser user = getUser(channel);
            /*if (user != null && user.getCompany() != null && user.getCompany().toLowerCase().contains("ofweek")) {
                isRecord = false;
			}*/
            return isRecord;
        }
    }

}
