package com.thinkgem.jeesite.modules.live.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 直播系统相关图片文件类型枚举
 */
public enum LiveFileSubjectTypeEnum {

    SYS_INDEX(1000, "sys/index", 0, "首页焦点图片"),
    ROOM_COVER(20000, "room/cover", 0, "直播房间封面图"),
    ROOM_REVIEW(21000, "room/review", 0, "直播房间回顾图");

    private final int subjectType;
    private final String physicsDir;
    private final int fileType;// 文件类型 0:图片,1:文件
    private final String meaning;

    LiveFileSubjectTypeEnum(int subjectType, String physicsDir, int fileType, String meaning) {
        this.subjectType = subjectType;
        this.physicsDir = physicsDir;
        this.fileType = fileType;
        this.meaning = meaning;
    }

    private static final Map<Integer, LiveFileSubjectTypeEnum> CODE2ENUMMAP = new HashMap<Integer, LiveFileSubjectTypeEnum>();

    static {
        for (LiveFileSubjectTypeEnum statusEnum : LiveFileSubjectTypeEnum.values()) {
            CODE2ENUMMAP.put(statusEnum.getSubjectType(), statusEnum);
        }
    }

    public String getMeaning() {
        return meaning;
    }

    public int getSubjectType() {
        return subjectType;
    }

    public String getPhysicsDir() {
        return physicsDir;
    }

    public int getFileType() {
        return fileType;
    }

    /**
     * 根据subjectType获取枚举对象
     *
     * @param subjectType
     * @return
     */
    public static LiveFileSubjectTypeEnum getEnum(Integer subjectType) {
        return CODE2ENUMMAP.get(subjectType);
    }
}
