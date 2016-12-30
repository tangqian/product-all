package com.thinkgem.jeesite.common.i18n.code;

public class OnlineConstants {
    public final static int NULL_REFERENCE_ID = -1;

    public static class Bulletin {

        public static class ErrorCode {
            public final static String BULLETIN_REQUIRED = "error.bulletin.required";
            public static final String NAME_EXISTS = "error.bulletin.name.exists";
            public static final String CONTENT_REQUIRED = "error.bulletin.content.required";
            public static final String REMOVE_RELATION_EXITS = "error.bulletin.move.relation.exists";
            public static final String BULLETIN_INVALID = "error.bulletin.invalid";

            public static final String PUBLISH_TIME_ERROR = "error.bulletin.publishTime.error";
            public static final String PUBLISH_TIME_REQUIRED = "error.bulletin.publishTime.required";
            public static final String PUBLISH_TIME_BEFORE_NOW_ERROR = "error.bulletin.time_before_now.error";
            public static final String END_TIME_ERROR = "error.bulletin.endTime.error";
            public static final String END_TIME_REQUIRED = "error.bulletin.endTime.required";
            public static final String TIME_AREA_ERROR = "error.bulletin.timeArea.error";
            public static final String EXHIBITION_REQUIRED = "error.bulletin.exhibition.required";
            public static final String PUBLISH_AREA_REQUIRED = "error.bulletin.publishArea.required";
            public static final String PUBLISHING_STATUS = "error.bulletin.publishing_status.error";
        }
    }

    public static class ActiveSchedule {

        public static class ErrorCode {
            public final static String SCHEDULE_REQUIRED = "error.schedule.required";
            public static final String NAME_EXISTS = "error.schedule.name.exists";
            public static final String CONTENT_REQUIRED = "error.schedule.content.required";
            public static final String REMOVE_RELATION_EXITS = "error.schedule.move.relation.exists";
            public static final String BULLETIN_INVALID = "error.schedule.invalid";

            public static final String PUBLISH_TIME_ERROR = "error.schedule.publishTime.error";
            public static final String PUBLISH_TIME_REQUIRED = "error.schedule.publishTime.required";
            public static final String PUBLISH_TIME_BEFORE_NOW_ERROR = "error.schedule.time_before_now.error";
            public static final String END_TIME_ERROR = "error.schedule.endTime.error";
            public static final String END_TIME_REQUIRED = "error.schedule.endTime.required";
            public static final String TIME_AREA_ERROR = "error.schedule.timeArea.error";
            public static final String EXHIBITION_REQUIRED = "error.schedule.exhibition.required";
            public static final String PUBLISH_AREA_REQUIRED = "error.schedule.publishArea.required";
            public static final String PUBLISHING_STATUS = "error.schedule.publishing_status.error";
        }
    }

    public static class Exhibition {

        public static class ErrorCode {
            public static final String EXHIBITION_REQUIRED = "error.exhibition.required";
            public static final String NAME_REQUIRED = "error.exhibition.name.required";
            public static final String NAME_EXISTS = "error.exhibition.name.exists";
            public static final String PICTURE_REQUIRED = "error.exhibition.picture.required";
            public static final String START_TIME_REQUIRED = "error.exhibition.startTime.required";
            public static final String ADMISSION_TIME_REQUIRED = "error.exhibition.admissionTime.required";
            public static final String END_TIME_REQUIRED = "error.exhibition.endTime.required";
            public static final String REMARK_REQUIRED = "error.exhibition.remark.required";
            public static final String VEDIO_REQUIRED = "error.exhibition.vedio.required";
            public static final String REMOVE_FAILURE_USERD = "error.exhibition.removeFailiure.used";
            public static final String BRIEF_REQUIRED = "error.exhibition.brief.required";
            /** 开始时间格式有问题 **/
            public static final String START_TIME_ERROR = "error.exhibition.startTime.error";
            public static final String END_TIME_ERROR = "error.exhibition.endTime.error";
            public static final String ORG_INVALID = "error.exhibition.org.invalid";
            public static final String INDUSTRY_INVALID = "error.exhibition.industry.invalid";
            public static final String VEDIO_INVALID = "error.exhibition.vedio.invalid";
            public static final String ADMISSION_TIME_INVALID = "error.exhibition.admissionTime.invalid";
            public static final String EXHIBITION_INVALID = "error.exhibition.invalid";
            /** 正在举行中 */
            public static final String EXHIBITION_IN_HOLDING = "error.exhibition.holding";
            public static final String EXHIBITION_IN_FEATURE = "error.exhibition.feature";
            public static final String EXHIBITION_IN_END = "error.exhibition.end";
            public static final String EXHIBITION_START_ERROR = "error.exhibition.start.error";
            public static final String EXHIBITION_ERROR_HOLDING_STATUS = "error.exhibition.holding.status";
            public static final String EXHIBITION_ERROR_END_STATUS = "error.exhibition.end.status";
            public static final String EXHIBITION_MODE_INVALID = "error.exhibition.mode.invalid";
            public static final String INVALID_EXIHIBITOR_REGISTER_STATUS = "error.exhibition.exhibtor.register.status.invalid";
            /** 展商进入展台需要先登记 */
            public static final String EXHIBITOR_REGISTER_FIRST = "error.exhibition.exhibitor.register.first";
            public static final String INVALID_WAITER = "error.exhibition.waiter.invalid";
            public static final String INVALID_AUDIENCE = "error.exhibition.audience.invalid";
            public static final String INVALID_USER = "error.exhibition.user.invalid";
            public static final String USER_NOT_AUTH_IN_EXHIBITION = "error.user.notAllInExhibition";
            public static final String INVALID_AUDIENCE_INVITATION = "error.exhibition.audience.invitationTest.invalid";
            public static final String ENTER_EXHIBITION_FIRST = "error.exhibition.enterExhibition.first";
            public static final String USER_IN_EXHIBITION_BLACK_LIST = "error.exhibition.userInExhibitionBlackList";
            public static final String USER_IN_BOOTH_BLACK_LIST = "error.exhibition.userInBoothBlackList";
            public static final String BOOTH_INVALID = "error.exhibition.booth.invalid";
            public static final String BOOTH_NOT_ALLOWED_PREVIEW = "error.exhibition.booth.notAllowPreview";
            public static final String INVALID_BOOTH = "error.exhibition.booth.invalid";
            public static final String NOT_LOGIN_EXHIBITION = "error.exhibition.notLoginExhibition";
            public static final String NOT_IN_BOOTH = "error.exhibition.notInBooth";
            public static final String OPERATION_AUDIENCE_ONLY = "error.exhibition.operation.audience.only";
            public static final String INVALID_EXHIBITION = "error.exhibition.invalid";
            public static final String OPERATION_EXHIBITOR_ONLY = "error.exhibition.operation.exhibitor.only";
            public static final String INVALID_BULLETIN = "error.exhibition.bulletin.invalid";
            public static final String INVALID_SPONSOR = "error.exhibition.sponsor.invalid";
            public static final String PICTURE_INVALID = "error.exhibition.picture.invalid";

            public static final String LOGIN_FIRST_BEFORE_ENTER_EXHIBITION = "error.exhibition.loginFirst.beforeEnterExhibition";
        }

    }

    public static class Hall {

        public static class ErrorCode {
            public final static String NAME_REQUIRED = "error.hall.name.required";
            public static final String NAME_EXISTS = "error.hall.name.exists";
            public static final String VEDIO_REQUIRED = "error.hall.vedio.required";
            public static final String BACKGROUND_IMAGE_REQUIRED = "error.hall.backgroundImage.required";
            public static final String DATA_INVALID = "error.hall.data.invalid";
            public static final String HALL_SHOULD_NOT_REMOVE = "error.hall.shouldNotRemove";
            public static final String HALL_REQUIRED = "error.hall.required";
        }

    }

    public static class Sponsor {
        public static class ErrorCode {
            public final static String SPONSOR_REQUIRED = "error.sponsor.required";
            public static final String SPONSOR_INVALID = "error.sponsor.invalid";
        }
    }

    public static class Organizer {
        public static class ErrorCode {
            public final static String ORGANIZER_REQUIRED = "error.organizer.required";
            public static final String ORGANIZER_INVALID = "error.organizer.invalid";
        }
    }

    public static class BlackList {
        public static class ErrorCode {
            public final static String BLACKLIST_REQUIRED = "error.blacklist.required";
            public final static String BLACKLIST_INVALID = "error.blacklist.invalid";
        }
    }

    private OnlineConstants() {
    }

    public static class Activity {

        public static class ErrorCode {
            public final static String ACTIVITY_REQUIRED = "error.activity.required";
            public final static String NAME_REQUIRED = "error.activity.name.required";
            public final static String NAME_EXISTS = "error.activity.name.exists";
            public final static String BRIEF_REQUIRED = "error.activity.brief.required";
            public static final String START_TIME_REQUIRED = "error.activity.startTime.required";
            public static final String END_TIME_REQUIRED = "error.activity.endTime.required";
            public static final String START_TIME_ERROR = "error.activity.startTime.error";
            public static final String END_TIME_ERROR = "error.activity.endTime.error";
            public static final String END_BEFORE_START_ERROR = "error.activity.end_before_start.error";
            public static final String TYPE_REQUIRED = "error.activity.type.required";
            public static final String EXHIBITOR_REQUIRED = "error.activity.exhibitor.required";
            public static final String ACTIVITY_IN_HOLDING = "error.activity.holding";
            // public static final String ACTIVITY_IN_FEATURE = "error.activity.feature";
        }
    }

    public static class BroadcastMessage {
        public static class ErrorCode {
            public final static String MESSAGE_REQUIRED = "error.broadcast_message.required";
            public final static String EXHIBITION_REQUIRED = "error.broadcast_message.exhibition.required";
            public final static String SUBJECT_REQUIRED = "error.broadcast_message.subject.required";
            public final static String CONTENT_REQUIRED = "error.broadcast_message.content.required";
            public static final String TYPE_REQUIRED = "error.broadcast_message.type.required";
            public static final String MESSAGE_SEND = "error.broadcast_message.send";
            public static final String LINK_FORMAT_ERROR = "error.broadcast_message.link_format";
        }
    }

    public static class IndexImage {
        public final static String LINK_INVALID = "error.indexImage.linkTo.invalid";
        public final static String URI_INVALID = "error.indexImage.uri.invalid";
        public final static String ALIAS_INVALID = "error.indexImage.alias.invalid";
    }

    public static class Audience {
        public static final String DATA_INVALID = "error.request.data.invalid";
        public static final String Audience_INVALID = "error.audience.register.audience.invalid";
    }

    public static class Security {
        public static final String ACCESS_KEY_REQUIRED = "error.exhibition.accessKey.required";
        public static final String ACCESS_KEY_INVALID = "error.exhibition.accessKey.invalid";
        public static final String ENCODE_AK_FAILURE = "error.exhibition.ak.failure";
        
        public final static String ERROR_CODE_TOKEN_CREATE_FAILURE = "error.security.token.create.failure";
        public final static String ERROR_CODE_TOKEN_QUERY_FAILURE = "error.security.token.query.failure";
        public static final String ERROR_CODE_TOKEN_UPDATE_FAILURE = "error.security.token.update.failure";
        public static final String ERROR_CODE_TOKEN_REMOVE_FAILURE = "error.security.token.remove.failure";
        
        public static final String ERROR_CODE_TOKEN_CREDENTIAL_NOT_MATCH = "error.security.token.credential.not.match";
    }

    public static class User {

        public static class ErrorCode {

            public static final String ACCOUNT_ASSIGNED = "error.user.account.assigned";
            /** 带参数account */
            public final static String ACCOUNT_INVALID = "error.user.account.invalid";
            public static final String EMAIL_REQUIRED = "error.user.email.requried";
            public static final String ACCOUNT_EMPTY = "error.user.account.empty";
            /** 账号已经被使用，带accont参数 */
            public static final String ACCOUNT_EXISTS = "error.user.account.exists";
            /** 邮箱已经被使用 */
            public static final String EMAIL_EXISTS = "error.user.email.exists";
            /** 规则不正确，带参数长度范围 */
            public static final String ACCOUNT_NOT_MATCH_RULE = "error.user.account.not.match.rule";
            public static final String EMAIL_NOT_MATCH_RULE = "error.user.email.not.match.rule";
            public static final String PASSWORD_REQUIRED = "error.user.password.required";
            public static final String PASSWORD_NOT_MATCH_RULE = "error.user.password.notMatchRule";
            public static final String ACCOUNT_REQUIRED = "error.user.account.required";
            public static final String LOGIN_FAILURE = "error.user.login.failure";
            public static final String ACCOUNT_PASSWORD_NOT_MATCH = "error.user.login.accountPassword.notMatch";
            public static final String USER_NOT_FOUND = "error.user.notFound";
            public static final String ACTIVATION_URL_INVALID = "error.user.activationUrl.invalid";
            public static final String VERIFY_CODE_INVALID = "error.user.verifyCode.invalid";
            public static final String OLD_PASSWORD_INVALID = "error.user.oldPassword.invalid";
            public static final String NEW_PASSWORD_INVALID = "error.user.newPassword.invalid";
        }
    }
}