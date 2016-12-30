CREATE TABLE `live_audience` (
  `id` int(9) NOT NULL,
  `company` varchar(255) DEFAULT NULL COMMENT '公司名',
  `country` varchar(64) DEFAULT NULL COMMENT '国家编码',
  `province` varchar(64) DEFAULT NULL COMMENT '省份编码',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `job` varchar(64) DEFAULT NULL COMMENT '职位',
  `logo_id` int(9) DEFAULT NULL COMMENT '头像id',
  `sex` tinyint(3) DEFAULT '0' COMMENT '性别，0:保密,1:男,2:女',
  `department` varchar(64) DEFAULT NULL COMMENT '部门',
  `mobile_phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `telephone` varchar(64) DEFAULT NULL COMMENT '座机',
  `fax` varchar(64) DEFAULT NULL COMMENT '传真',
  `biz_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '1自注册,2会员,3非会员,4推广',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='观众表';

CREATE TABLE `live_audience_register` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `audience_id` int(9) NOT NULL COMMENT '观众id',
  `company` varchar(255) DEFAULT NULL COMMENT '公司名',
  `country` varchar(64) DEFAULT NULL COMMENT '国家编码',
  `province` varchar(64) DEFAULT NULL COMMENT '省份编码',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `job` varchar(64) DEFAULT NULL COMMENT '职位',
  `logo_id` int(9) DEFAULT NULL COMMENT '头像id',
  `sex` tinyint(3) DEFAULT '0' COMMENT '性别，0:保密,1:男,2:女',
  `department` varchar(64) DEFAULT NULL COMMENT '部门',
  `mobile_phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `telephone` varchar(64) DEFAULT NULL COMMENT '座机',
  `fax` varchar(64) DEFAULT NULL COMMENT '传真',
  `is_sent_email` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否已发送通知邮件,0:否',  
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_room_audience_id` (`room_id`,`audience_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='观众登记表';

CREATE TABLE `live_home_banner` (
  `id` int(9) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `href` varchar(255) NOT NULL COMMENT '链接地址',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页banner图表';

CREATE TABLE `live_report_data_download` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',  
  `type` tinyint(3) NOT NULL COMMENT '资料类型(1:资料,2:演讲稿)',
  `data_id` int(9) NOT NULL COMMENT '主播资料id',
  `user_id` int(9) NOT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_report_room_data_user_id` (`room_id`,`type`,`data_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间资料下载记录表';

CREATE TABLE `live_report_user_present` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `user_id` int(9) NOT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_report_room_user_id` (`room_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户访问记录表';

CREATE TABLE `live_report_visitor_present` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `visitor_id` int(9) NOT NULL COMMENT '游客id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_report_room_visitor_id` (`room_id`,`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游客访问记录表';

CREATE TABLE `live_room` (
  `id` int(9) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '直播主题名称',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键字',
  `speaker_id` int(9) NOT NULL COMMENT '主播id(举办公司)',
  `publish_mode` tinyint(3) NOT NULL DEFAULT '0' COMMENT '直播模式,0:视频/ppt',
  `watch_mode` tinyint(3) NOT NULL DEFAULT '0' COMMENT '观看模式,0:游客',
  `is_show` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否显示,0:是',
  `is_contact_show` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否显示联络方式,0:是',
  `is_top` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否置顶：0否：1是',
  `industry` varchar(255) NOT NULL COMMENT '行业,用,id1,id2,格式保存',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `bg_end_time` datetime NOT NULL COMMENT '直播实际结束时间',
  `review_id` int(9) DEFAULT NULL COMMENT '回顾图id',
  `cover_id` int(9) DEFAULT NULL COMMENT '封面图id',
  `summary` varchar(1000) DEFAULT NULL COMMENT '摘要',
  `detail` text COMMENT '详细介绍',
  `top_sort` int(9) NOT NULL DEFAULT '100' COMMENT '置顶房间排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '直播房间状态。0:新建,1:保留,2:进行中,3:保留,4:结束',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `pv` int(9) NOT NULL DEFAULT '0' COMMENT '人气值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播房间';

CREATE TABLE `live_room_blacklist` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `user_id` int(9) NOT NULL COMMENT '用户id',
  `reason` varchar(1000) DEFAULT NULL COMMENT '原因',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_blacklist_room_user_id` (`room_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户黑名单表';

CREATE TABLE `live_room_chat` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `send_by` int(9) NOT NULL COMMENT '发送者id',
  `send_to` int(9) DEFAULT NULL COMMENT '群聊时被@的人',
  `content` varchar(1000) NOT NULL COMMENT '聊天内容',
  `type` tinyint(3) NOT NULL COMMENT '聊天类型(1:私聊,2:群聊)',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间聊天记录表';

CREATE TABLE `live_room_chat_receiver` (
  `id` int(9) NOT NULL,
  `chat_id` int(9) NOT NULL COMMENT '聊天记录id',
  `receive_by` int(9) NOT NULL COMMENT '接收者id',
  `read_date` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天接收记录表';

CREATE TABLE `live_room_data` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `source_id` int(9) NOT NULL COMMENT '数据源id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间资料表';

CREATE TABLE `live_room_data_public` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `source_id` int(9) NOT NULL COMMENT '数据源id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间资料表(审核通过)';

CREATE TABLE `live_room_review_data` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `name` varchar(255) NOT NULL COMMENT '资料名称',
  `file_id` int(9) NOT NULL COMMENT '资料id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `mediaId` varchar(255) DEFAULT NULL COMMENT '媒资ID(百度云VOD查询用)',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态(0:禁用,1:启用)',
  `type` tinyint(3) NOT NULL COMMENT '资料类型(1:视频)',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间回顾资料表';

CREATE TABLE `live_room_rich_text` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `content` varchar(2000) NOT NULL COMMENT '图文内容',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建',
  `create_by` int(9) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(9) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播图文表';

CREATE TABLE `live_room_speech` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `source_id` int(9) NOT NULL COMMENT '数据源id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间演讲稿表';

CREATE TABLE `live_room_speech_public` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `source_id` int(9) NOT NULL COMMENT '数据源id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间演讲稿表(审核通过)';

CREATE TABLE `live_room_video` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `source_id` int(9) NOT NULL COMMENT '数据源id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间视频表';

CREATE TABLE `live_room_video_public` (
  `id` int(9) NOT NULL,
  `room_id` int(9) NOT NULL COMMENT '直播房间id',
  `source_id` int(9) NOT NULL COMMENT '数据源id',
  `name` varchar(255) NOT NULL COMMENT '视频名',
  `detail` varchar(1000) DEFAULT NULL COMMENT '详细介绍',
  `file_id` int(9) NOT NULL COMMENT '视频文件id',
  `cover_id` int(9) DEFAULT NULL COMMENT '视频封面图id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房间视频表(审核通过)';

CREATE TABLE `live_speaker` (
  `id` int(9) NOT NULL,
  `company` varchar(255) NOT NULL COMMENT '公司名',
  `address` varchar(255) DEFAULT NULL COMMENT '公司详细地址',
  `industry` varchar(255) NOT NULL COMMENT '行业,用,id1,id2,格式保存',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `job` varchar(64) DEFAULT NULL COMMENT '职位',
  `logo_id` int(9) DEFAULT NULL COMMENT '头像id',
  `sex` tinyint(3) DEFAULT '0' COMMENT '性别，0:保密,1:男,2:女',
  `department` varchar(64) DEFAULT NULL COMMENT '部门',
  `mobile_phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `telephone` varchar(64) DEFAULT NULL COMMENT '座机',
  `fax` varchar(64) DEFAULT NULL COMMENT '传真',
  `url` varchar(64) DEFAULT NULL,
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播表';

CREATE TABLE `live_speaker_data` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建(即待审核),1、2:保留,3:审核通过,4:审核不通过',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播资料表';

CREATE TABLE `live_speaker_data_recycle` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建(即待审核),1、2:保留,3:审核通过,4:审核不通过',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播资料回收表';

CREATE TABLE `live_speaker_recycle_inventory` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `subject_name` varchar(255) NOT NULL COMMENT '关联名称',
  `subject_id` int(9) NOT NULL COMMENT '关联实体id',
  `subject_type` int(9) NOT NULL COMMENT '关联实体类型,如视频、演讲稿',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播回收站清单表';

CREATE TABLE `live_speaker_speech` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建(即待审核),1、2:保留,3:审核通过,4:审核不通过',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播演讲稿表';

CREATE TABLE `live_speaker_speech_recycle` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(255) NOT NULL COMMENT '文件名',
  `file_id` int(9) NOT NULL COMMENT '文件id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建(即待审核),1、2:保留,3:审核通过,4:审核不通过',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播演讲稿回收表';

CREATE TABLE `live_speaker_video` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(255) NOT NULL COMMENT '视频名',
  `detail` varchar(1000) DEFAULT NULL COMMENT '详细介绍',
  `file_id` int(9) NOT NULL COMMENT '视频文件id',
  `cover_id` int(9) DEFAULT NULL COMMENT '视频封面图id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建(即待审核),1、2:保留,3:审核通过,4:审核不通过',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播视频表';

CREATE TABLE `live_speaker_video_recycle` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(255) NOT NULL COMMENT '视频名',
  `detail` varchar(1000) DEFAULT NULL COMMENT '详细介绍',
  `file_id` int(9) NOT NULL COMMENT '视频文件id',
  `cover_id` int(9) DEFAULT NULL COMMENT '视频封面图id',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:新建(即待审核),1、2:保留,3:审核通过,4:审核不通过',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播视频回收表';

CREATE TABLE `live_speaker_waiter` (
  `id` int(9) NOT NULL,
  `speaker_id` int(9) NOT NULL COMMENT '主播id',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `job` varchar(64) DEFAULT NULL COMMENT '职位',
  `logo_id` int(9) DEFAULT NULL COMMENT '头像id',
  `sex` tinyint(3) DEFAULT '0' COMMENT '性别，0:保密,1:男,2:女',
  `department` varchar(64) DEFAULT NULL COMMENT '部门',
  `mobile_phone` varchar(64) DEFAULT NULL COMMENT '手机',
  `telephone` varchar(64) DEFAULT NULL COMMENT '座机',
  `fax` varchar(64) DEFAULT NULL COMMENT '传真',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主播客服表';

CREATE TABLE `live_user` (
  `id` int(9) NOT NULL,
  `account` varchar(64) NOT NULL COMMENT '用户名',
  `email` varchar(255) NOT NULL COMMENT '邮箱',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `type` tinyint(3) NOT NULL COMMENT '用户类型(1:观众,2:管理员,3:主播,4:客服)',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '用户状态。0:正常,1禁用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_account_type` (`account`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `live_visitor` (
  `id` int(9) NOT NULL,
  `ip` varchar(255) NOT NULL COMMENT '访问ip',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游客表';

CREATE TABLE `sys_async_task` (
  `id` int(9) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '任务名称',
  `type` tinyint(3) NOT NULL COMMENT '类型',
  `param` varchar(200) DEFAULT NULL COMMENT '任务参数',
  `status` tinyint(3) NOT NULL COMMENT '状态,0待执行，1执行中，2执行成功，3执行失败',
  `execute_time` datetime DEFAULT NULL COMMENT '任务最后执行完成时间',
  `result_message` text,
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异步任务表';

CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

CREATE TABLE `sys_email` (
  `id` int(9) NOT NULL,
  `subject` varchar(255) NOT NULL COMMENT '主题',
  `content` text NOT NULL,
  `receiver` varchar(255) NOT NULL,
  `cc` varchar(255) DEFAULT NULL,
  `bcc` varchar(255) DEFAULT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态,0:待发送,1:发送成功,2:发送失败',
  `subject_id` int(9) NOT NULL COMMENT '关联实体id',
  `subject_type` int(9) NOT NULL COMMENT '关联实体类型',
  `create_date` datetime NOT NULL,
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件记录表';

CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL,
  `uri` varchar(225) NOT NULL COMMENT '文件物理相对路径',
  `original_name` varchar(225) NOT NULL COMMENT '原始文件名称',
  `size` int(11) NOT NULL COMMENT '文件大小(字节数)',
  `ext` varchar(64) NOT NULL COMMENT '文件扩展名',
  `is_temp` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否临时文件,0:不是',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '类型,0:图片,1:文件',
  `subject_type` int(9) NOT NULL COMMENT '关联业务类型',
  `parent_id` int(9) DEFAULT NULL COMMENT '父级编号',
  `sort` int(9) DEFAULT NULL COMMENT '排序',
  `create_date` datetime NOT NULL,
  `create_by` int(9) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';

CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `is_sys` varchar(64) DEFAULT NULL COMMENT '是否系统数据',
  `useable` varchar(64) DEFAULT NULL COMMENT '是否可用',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`),
  KEY `sys_role_enname` (`enname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

CREATE TABLE `sys_sequence` (
  `name` varchar(128) NOT NULL COMMENT '序列key',
  `next_id` int(9) NOT NULL DEFAULT '1' COMMENT '下一个可用值',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='序列表';

CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

INSERT INTO `live_user` (`id`, `account`, `email`, `password`, `type`, `status`, `create_date`, `update_date`) VALUES ('1', 'admin', '123456@qq.com', '0f4f6729665fb527debcc93bdb78c097af8aa4714998d27ef496274e', '2', '0', '2016-08-09 17:28:44', '2016-08-09 17:28:47');

INSERT INTO `sys_sequence` VALUES ('sysUser', '138', '2016-10-15 16:29:11');
INSERT INTO `sys_sequence` VALUES ('LiveUser', '2', '2016-10-14 16:22:37');

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '0', '正常', 'del_flag', '删除标记', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('10', 'yellow', '黄色', 'color', '颜色值', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('100', 'zh_CN', '中国', 'country_code', '国家', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('101', '-1', '中国以外地区', 'country_code', '国家', '15', '0', '1', '2016-02-26 10:41:00', '1', '2016-02-26 10:41:00', '', '0');
INSERT INTO `sys_dict` VALUES ('102', 'CNANH', '安徽', 'province_code', '省份', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('103', 'CNBEI', '北京 ', 'province_code', '省份', '11', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('104', 'CNFUJ', '福建', 'province_code', '省份', '12', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('105', 'CNGAN', '甘肃', 'province_code', '省份', '13', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('106', 'CNGUA', '广东', 'province_code', '省份', '14', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('107', 'CNGUN', '广西', 'province_code', '省份', '15', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('108', 'CNGUI', '贵州', 'province_code', '省份', '16', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('109', 'CNHAI', '海南', 'province_code', '省份', '17', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('11', 'orange', '橙色', 'color', '颜色值', '50', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('110', 'CNHEB', '河北', 'province_code', '省份', '18', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('111', 'CNHEN', '河南', 'province_code', '省份', '19', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('112', 'CNHEI', '黑龙江', 'province_code', '省份', '20', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('113', 'CNHUB', '湖北', 'province_code', '省份', '21', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('114', 'CNHUN', '湖南', 'province_code', '省份', '22', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('115', 'CNJIL', '吉林', 'province_code', '省份', '23', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('116', 'CNJIA', '江苏', 'province_code', '省份', '24', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('117', 'CNJIN', '江西', 'province_code', '省份', '25', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('118', 'CNLIA', '辽宁', 'province_code', '省份', '26', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('119', 'CNNEI', '内蒙', 'province_code', '省份', '27', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('12', 'default', '默认主题', 'theme', '主题方案', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('120', 'CNNIN', '宁夏', 'province_code', '省份', '28', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('121', 'CNQIN', '青海', 'province_code', '省份', '29', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('122', 'CNSHN', '山东', 'province_code', '省份', '30', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('123', 'CNSHX', '山西', 'province_code', '省份', '31', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('124', 'CNSHA', '陕西', 'province_code', '省份', '32', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('125', 'CNSHG', '上海', 'province_code', '省份', '33', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('126', 'CNSIC', '四川', 'province_code', '省份', '34', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('127', 'CNTIA', '天津', 'province_code', '省份', '35', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('128', 'CNXIZ', '西藏', 'province_code', '省份', '36', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('129', 'CNXIN', '新疆', 'province_code', '省份', '37', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('13', 'cerulean', '天蓝主题', 'theme', '主题方案', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('130', 'CNYUN', '云南', 'province_code', '省份', '38', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('131', 'CNZHE', '浙江', 'province_code', '省份', '39', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('132', 'CNCHO', '重庆', 'province_code', '省份', '40', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('133', 'CNTAW', '台湾', 'province_code', '省份', '41', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('134', 'CNHNK', '香港', 'province_code', '省份', '42', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('135', 'CNMAC', '澳门', 'province_code', '省份', '43', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('14', 'readable', '橙色主题', 'theme', '主题方案', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('15', 'united', '红色主题', 'theme', '主题方案', '40', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('16', 'flat', 'Flat主题', 'theme', '主题方案', '60', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '1', '删除', 'del_flag', '删除标记', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('21', '10', '10分钟', 'admission_option', '提前入场时间', '10', '0', '1', '2016-02-26 10:39:55', '1', '2016-02-26 10:39:55', '', '0');
INSERT INTO `sys_dict` VALUES ('22', '15', '15分钟', 'admission_option', '提前入场时间', '15', '0', '1', '2016-02-26 10:41:40', '1', '2016-02-26 10:41:40', '', '0');
INSERT INTO `sys_dict` VALUES ('23', '20', '20分钟', 'admission_option', '提前入场时间', '20', '0', '1', '2016-02-26 10:42:00', '1', '2016-02-26 10:42:00', '', '0');
INSERT INTO `sys_dict` VALUES ('24', '30', '30分钟', 'admission_option', '提前入场时间', '30', '0', '1', '2016-02-26 10:42:41', '1', '2016-02-26 10:42:41', '', '0');
INSERT INTO `sys_dict` VALUES ('25', '60', '60分钟', 'admission_option', '提前入场时间', '60', '0', '1', '2016-02-26 10:43:06', '1', '2016-02-26 10:43:06', '', '0');
INSERT INTO `sys_dict` VALUES ('26', '1', '直播', 'activity_type', '活动类型', '60', '0', '1', '2016-02-26 10:42:41', '1', '2016-02-26 10:42:41', '', '0');
INSERT INTO `sys_dict` VALUES ('27', '2', '有奖活动', 'activity_type', '活动类型', '30', '0', '1', '2016-02-26 10:43:06', '1', '2016-02-26 10:43:06', '', '0');
INSERT INTO `sys_dict` VALUES ('3', '1', '显示', 'show_hide', '显示/隐藏', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('39', '1', '系统管理', 'sys_user_type', '用户类型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('4', '0', '隐藏', 'show_hide', '显示/隐藏', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('40', '2', '部门经理', 'sys_user_type', '用户类型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('41', '3', '普通用户', 'sys_user_type', '用户类型', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('5', '1', '是', 'yes_no', '是/否', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('6', '0', '否', 'yes_no', '是/否', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('67', '1', '接入日志', 'sys_log_type', '日志类型', '30', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('68', '2', '异常日志', 'sys_log_type', '日志类型', '40', '0', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('7', 'red', '红色', 'color', '颜色值', '10', '0', '1', '2013-05-27 08:00:00', '1', '2016-02-23 09:54:32', '', '0');
INSERT INTO `sys_dict` VALUES ('71', '103100', '医疗', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('72', '11000', '物联网', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('73', '11500', '3D打印', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('74', '14000', '照明', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('75', '15000', '电力', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('76', '16000', '锂电', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('77', '2000', '光通讯', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('78', '3000', '光学', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('79', '4000', '激光', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('8', 'green', '绿色', 'color', '颜色值', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('80', '5000', '可穿戴设备', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('81', '51000', '安防', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('82', '6000', '太阳能光伏', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('83', '692007', '半导体照明', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('84', '692028', '显示', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('85', '71100', '汽车网', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('86', '77000', '新能源汽车', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('87', '879012', '电子工程', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('88', '879083', '智能电网', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('89', '879100', '工控', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('9', 'blue', '蓝色', 'color', '颜色值', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('90', '88000', '传感器', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('91', '897251', '仪器仪表', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('92', '897512', '风电', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('93', '898000', '通信', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('94', '898500', '电源', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('95', '898800', '机器人', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('96', '1', '男', 'sex', '性别', '10', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('97', '2', '女', 'sex', '性别', '20', '0', '1', '2013-10-28 08:00:00', '1', '2013-10-28 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('98', '911000', '智能家居', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');
INSERT INTO `sys_dict` VALUES ('99', '987000', '节能环保', 'industry_option', '行业类型', '10', '0', '1', '2016-02-26 10:39:00', '1', '2016-02-26 10:39:00', '', '0');


-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('04c61e3615224a5ba9585ea303973aa1', '测试管理员', null, null, '8', '1', '1', '1', '2016-04-16 13:37:08', '1', '2016-10-19 10:37:45', '测试人员专用，请勿修改、删除，谢谢！', '0');
INSERT INTO `sys_role` VALUES ('1', '系统管理员', null, null, '8', '1', '1', '1', '2016-02-22 16:47:16', '1', '2016-08-09 16:05:31', '', '0');
INSERT INTO `sys_role` VALUES ('2', '运营人员', null, null, '1', '1', '1', '1', '2016-02-22 16:37:23', '1', '2016-07-28 10:43:07', '', '0');
INSERT INTO `sys_role` VALUES ('35b3c732fce74a69b6d24f83385f2c57', '市场主任', null, null, '8', '1', '1', '1', '2016-05-07 11:30:08', '1', '2016-05-20 09:41:11', '', '0');
INSERT INTO `sys_role` VALUES ('425a012a1aae44be9e2566015ca10204', '市场经理', null, null, '8', '1', '1', '1', '2016-04-16 15:21:11', '1', '2016-05-20 09:50:53', '', '0');
INSERT INTO `sys_role` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '产品经理', null, null, '8', '1', '1', '1', '2016-04-16 15:11:53', '1', '2016-04-29 17:27:14', '', '0');
INSERT INTO `sys_role` VALUES ('7745fab47242415491f305bf5002b92c', '销售经理', null, null, '8', '1', '1', '1', '2016-04-16 15:24:28', '1', '2016-04-19 17:47:26', '', '0');
INSERT INTO `sys_role` VALUES ('8bc4495ea9704f56a3c18bb06549d565', '市场资料', null, null, '8', '1', '1', '1', '2016-05-13 14:16:58', '1', '2016-05-13 14:36:40', '', '1');
INSERT INTO `sys_role` VALUES ('c9b4386d3ab34b93a2ab0c25184c2adb', '运营经理', null, null, '8', '1', '1', '1', '2016-04-16 15:23:20', '1', '2016-05-20 09:51:06', '', '0');
INSERT INTO `sys_role` VALUES ('d75e85db5e074fc485ca04ebf865a586', '技术', null, null, '8', '1', '1', '1', '2016-04-16 15:28:27', '1', '2016-04-16 15:28:27', '', '0');
INSERT INTO `sys_role` VALUES ('df38acf552a0443793446c2bcbd47d9a', '产品服务', null, null, '8', '1', '1', '1', '2016-04-16 15:18:06', '1', '2016-04-29 17:26:53', '', '0');
INSERT INTO `sys_role` VALUES ('ec3c9981df8a4e4d9d52663e0421ad19', '客服经理', null, null, '8', '1', '1', '1', '2016-05-20 09:46:39', '1', '2016-05-20 09:46:39', '', '0');
INSERT INTO `sys_role` VALUES ('f266ce77531b4a40a08548bbee9ed976', '推广部门', null, null, '8', '1', '1', '1', '2016-04-16 15:21:28', '1', '2016-04-27 11:32:47', '', '0');
INSERT INTO `sys_role` VALUES ('f34a591c206c4995951a9116fa050f35', '客服', null, null, '8', '1', '1', '1', '2016-04-16 15:20:06', '1', '2016-05-20 10:15:49', '', '0');

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0a8f5ed704604503a13974d823e5150b', 'f3ad92d258ef462a8ff6da4c6c1610dc', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,f3ad92d258ef462a8ff6da4c6c1610dc,', '删除', '60', '', '', '', '0', 'live:speaker:delete', '1', '2016-08-05 14:14:23', '1', '2016-08-05 14:14:55', '', '0');
INSERT INTO `sys_menu` VALUES ('1', '0', '0,', '功能菜单', '0', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '0,1,2,3,', '字典管理', '60', '/sys/dict/', '', 'th-list', '1', 'sys:dict:view', '1', '2013-05-27 08:00:00', '1', '2016-02-23 14:59:56', '', '0');
INSERT INTO `sys_menu` VALUES ('12', '10', '0,1,2,3,10,', '修改', '40', '', '', '', '0', 'sys:dict:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('13', '2', '0,1,2,', '系统用户', '970', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2016-02-23 10:36:07', '', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '0,1,', '系统设置', '900', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('20', '13', '0,1,2,13,', '用户管理', '20', '/sys/user/list', '', 'user', '1', 'sys:user:view', '1', '2013-05-27 08:00:00', '1', '2016-02-23 12:08:21', '', '0');
INSERT INTO `sys_menu` VALUES ('22', '20', '0,1,2,13,20,', '修改', '40', '', '', '', '0', 'sys:user:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('22fcb71f7b0548e489e54e30cf2d0ddb', 'b83a3e8de12145dcae00fa0e2c8f0488', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,', '资料审核', '120', '/live/data/list', '', '', '1', 'live:data:view', '1', '2016-08-08 14:51:47', '1', '2016-08-08 14:53:00', '', '0');
INSERT INTO `sys_menu` VALUES ('26abc8b1ec764d93a2219bc791c10ae4', '9e72a8769d174d648e8aa675cee35b7e', '0,1,4d9730668e634e00b81c7ca00db5097c,9e72a8769d174d648e8aa675cee35b7e,', '异步任务管理', '30', '/sys/asyncTask/list', '', '', '1', 'sys:asyncTask:view', '1', '2016-09-09 09:43:52', '1', '2016-11-08 14:11:59', '', '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '0,1,', '我的面板', '200', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('28', '27', '0,1,27,', '个人信息', '30', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('28b8ae99d0e248dfb0e186b6f09c39fb', 'b83a3e8de12145dcae00fa0e2c8f0488', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,', '直播房间', '30', '/live/room/list', '', '', '1', 'live:room:view', '1', '2016-08-03 10:37:04', '1', '2016-08-03 10:45:31', '', '0');
INSERT INTO `sys_menu` VALUES ('29', '28', '0,1,27,28,', '个人信息', '30', '/sys/user/info', '', 'user', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('29d429cc87a040288b7f9b0bdcb749e5', 'c241d463d36741f5a52e5a84821c2262', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,c241d463d36741f5a52e5a84821c2262,', '修改', '30', '', '', '', '0', 'live:homeBanner:edit', '1', '2016-08-18 15:48:17', '1', '2016-08-18 15:48:17', '', '0');
INSERT INTO `sys_menu` VALUES ('2ec897110add46949309f9ec45917338', 'f3ad92d258ef462a8ff6da4c6c1610dc', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,f3ad92d258ef462a8ff6da4c6c1610dc,', '修改', '30', '', '', '', '0', 'live:speaker:edit', '1', '2016-08-05 14:13:54', '1', '2016-08-05 14:14:47', '', '0');
INSERT INTO `sys_menu` VALUES ('2feb629ee13a4c4598b5c4c9578bb0e8', '84f9be930e1b45c0933f6e015ab76e5e', '0,1,4d9730668e634e00b81c7ca00db5097c,43a47ad0d3c4499ba637499caca6320b,84f9be930e1b45c0933f6e015ab76e5e,', '移除黑名单', '60', '', '', '', '0', 'live:roomBlacklist:delete', '1', '2016-08-11 16:20:47', '1', '2016-11-08 15:27:33', '', '0');
INSERT INTO `sys_menu` VALUES ('3', '2', '0,1,2,', '系统设置', '980', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('30', '28', '0,1,27,28,', '修改密码', '40', '/sys/user/modifyPwd', '', 'lock', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('4', '3', '0,1,2,3,', '菜单管理', '40', '/sys/menu/', '', 'list-alt', '1', 'sys:menu:view', '1', '2013-05-27 08:00:00', '1', '2016-02-23 14:59:26', '', '0');
INSERT INTO `sys_menu` VALUES ('43a47ad0d3c4499ba637499caca6320b', '4d9730668e634e00b81c7ca00db5097c', '0,1,4d9730668e634e00b81c7ca00db5097c,', '观众管理', '60', '', '', '', '1', '', '1', '2016-08-11 15:31:19', '1', '2016-08-11 15:31:19', '', '0');
INSERT INTO `sys_menu` VALUES ('451bf4b6c341447b89d78ee19edbfd5f', '9e72a8769d174d648e8aa675cee35b7e', '0,1,4d9730668e634e00b81c7ca00db5097c,9e72a8769d174d648e8aa675cee35b7e,', '重置主播密码', '60', '/live/speaker/resetPassForm', '', '', '1', 'system:manage:view', '1', '2016-10-14 16:34:53', '1', '2016-10-14 17:58:49', '', '0');
INSERT INTO `sys_menu` VALUES ('4d9730668e634e00b81c7ca00db5097c', '1', '0,1,', '直播系统', '100', '', '', 'icon-bar-chart', '1', '', '1', '2016-08-01 14:40:56', '1', '2016-08-03 10:34:17', '', '0');
INSERT INTO `sys_menu` VALUES ('56', '71', '0,1,27,71,', '文件管理', '90', '/../static/ckfinder/ckfinder.html', '', 'folder-open', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('57', '56', '0,1,27,40,56,', '查看', '30', '', '', '', '0', 'cms:ckfinder:view', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('58', '56', '0,1,27,40,56,', '上传', '40', '', '', '', '0', 'cms:ckfinder:upload', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('59', '56', '0,1,27,40,56,', '修改', '50', '', '', '', '0', 'cms:ckfinder:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('6', '4', '0,1,2,3,4,', '修改', '40', '', '', '', '0', 'sys:menu:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('67', '2', '0,1,2,', '日志查询', '985', '', '', '', '1', '', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('68', '67', '0,1,2,67,', '日志查询', '30', '/sys/log', '', 'pencil', '1', 'sys:log:view', '1', '2013-06-03 08:00:00', '1', '2013-06-03 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('6bfedca09fa0416ea70a756cd0b05de8', 'fb4f0610c30b45508e8e92a5443bdea0', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,fb4f0610c30b45508e8e92a5443bdea0,', '导出', '30', '', '', '', '0', 'live:dataDownload:export', '1', '2016-08-12 15:09:36', '1', '2016-08-12 15:46:56', '', '0');
INSERT INTO `sys_menu` VALUES ('7', '3', '0,1,2,3,', '角色管理', '30', '/sys/role/', '', 'lock', '1', 'sys:role:view', '1', '2013-05-27 08:00:00', '1', '2016-02-23 14:58:48', '', '0');
INSERT INTO `sys_menu` VALUES ('71', '27', '0,1,27,', '文件管理', '90', '', '', '', '1', '', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('80c13a82a15b490f9efed00b097d5e73', 'ce5b54b08d2f4c3c9b2789665d2409c4', '0,1,4d9730668e634e00b81c7ca00db5097c,43a47ad0d3c4499ba637499caca6320b,ce5b54b08d2f4c3c9b2789665d2409c4,', '加入黑名单', '90', '', '', '', '0', 'live:audienceRegister:blacklist', '1', '2016-08-11 16:22:55', '1', '2016-11-08 15:21:59', '', '0');
INSERT INTO `sys_menu` VALUES ('836be71abdaf4f56b014a19601def3e6', 'd0e72fe52a3045babd4a8e23f0be87bb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,d0e72fe52a3045babd4a8e23f0be87bb,', '删除', '60', '', '', '', '0', 'live:roomReviewData:delete', '1', '2016-09-24 13:04:48', '1', '2016-09-24 13:04:48', '', '0');
INSERT INTO `sys_menu` VALUES ('84', '67', '0,1,2,67,', '连接池监视', '40', '/../druid', '', '', '1', '', '1', '2013-10-18 08:00:00', '1', '2013-10-18 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('84f9be930e1b45c0933f6e015ab76e5e', '43a47ad0d3c4499ba637499caca6320b', '0,1,4d9730668e634e00b81c7ca00db5097c,43a47ad0d3c4499ba637499caca6320b,', '观众黑名单', '90', '/live/roomBlacklist/list', '', '', '1', 'live:roomBlacklist:view', '1', '2016-08-11 16:18:37', '1', '2016-11-08 15:27:17', '', '0');
INSERT INTO `sys_menu` VALUES ('8befb28b8681428f8ae33e060a3f194f', '22fcb71f7b0548e489e54e30cf2d0ddb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,22fcb71f7b0548e489e54e30cf2d0ddb,', '视频审核', '30', '', '', '', '0', 'live:video:audit', '1', '2016-08-08 14:54:20', '1', '2016-08-09 14:58:59', '', '0');
INSERT INTO `sys_menu` VALUES ('9', '7', '0,1,2,3,7,', '修改', '40', '', '', '', '0', 'sys:role:edit', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', '', '0');
INSERT INTO `sys_menu` VALUES ('939d2289195e42c5a701d70ca8e6ae9d', 'd0e72fe52a3045babd4a8e23f0be87bb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,d0e72fe52a3045babd4a8e23f0be87bb,', '修改', '30', '', '', '', '0', 'live:roomReviewData:edit', '1', '2016-09-24 12:20:22', '1', '2016-09-24 13:04:34', '', '0');
INSERT INTO `sys_menu` VALUES ('9e72a8769d174d648e8aa675cee35b7e', '4d9730668e634e00b81c7ca00db5097c', '0,1,4d9730668e634e00b81c7ca00db5097c,', '系统管理', '90', '', '', '', '1', '', '1', '2016-09-09 09:41:11', '1', '2016-09-09 09:41:11', '', '0');
INSERT INTO `sys_menu` VALUES ('a4975a92e7404f659f858cd5471c7b19', '28b8ae99d0e248dfb0e186b6f09c39fb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,28b8ae99d0e248dfb0e186b6f09c39fb,', '删除', '60', '', '', '', '0', 'live:room:delete', '1', '2016-08-03 10:38:03', '1', '2016-08-03 10:38:10', '', '0');
INSERT INTO `sys_menu` VALUES ('b0b63b5da531433d957c3520e51dc773', '28b8ae99d0e248dfb0e186b6f09c39fb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,28b8ae99d0e248dfb0e186b6f09c39fb,', '修改', '30', '', '', '', '0', 'live:room:edit', '1', '2016-08-03 10:37:48', '1', '2016-08-03 10:37:48', '', '0');
INSERT INTO `sys_menu` VALUES ('b5959611cce848359be266b4347dde2c', '1', '0,1,', '查看', '930', '', '', '', '1', 'exhibition:activity:view', '1', '2016-07-04 10:54:09', '1', '2016-07-04 10:54:09', '', '1');
INSERT INTO `sys_menu` VALUES ('b83a3e8de12145dcae00fa0e2c8f0488', '4d9730668e634e00b81c7ca00db5097c', '0,1,4d9730668e634e00b81c7ca00db5097c,', '直播管理', '30', '', '', '', '1', '', '1', '2016-08-03 10:34:48', '1', '2016-08-03 10:34:48', '', '0');
INSERT INTO `sys_menu` VALUES ('c241d463d36741f5a52e5a84821c2262', 'b83a3e8de12145dcae00fa0e2c8f0488', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,', 'Banner管理', '210', '/live/homeBanner/list', '', '', '1', 'live:homeBanner:view', '1', '2016-08-18 15:47:33', '1', '2016-08-18 15:47:33', '', '0');
INSERT INTO `sys_menu` VALUES ('c835ca2eb0c04eaabecd94602e795eb8', '22fcb71f7b0548e489e54e30cf2d0ddb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,22fcb71f7b0548e489e54e30cf2d0ddb,', '资料审核', '60', '', '', '', '0', 'live:data:audit', '1', '2016-08-08 14:55:22', '1', '2016-08-09 14:59:06', '', '0');
INSERT INTO `sys_menu` VALUES ('cd146fed256d4282a118f36c5d6bf6bf', 'c241d463d36741f5a52e5a84821c2262', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,c241d463d36741f5a52e5a84821c2262,', '删除', '60', '', '', '', '0', 'live:homeBanner:delete', '1', '2016-08-18 15:48:42', '1', '2016-08-18 15:48:42', '', '0');
INSERT INTO `sys_menu` VALUES ('ce5b54b08d2f4c3c9b2789665d2409c4', '43a47ad0d3c4499ba637499caca6320b', '0,1,4d9730668e634e00b81c7ca00db5097c,43a47ad0d3c4499ba637499caca6320b,', '观众登记', '60', '/live/audienceRegister/list', '', '', '1', 'live:audienceRegister:view', '1', '2016-08-05 09:23:57', '1', '2016-11-08 15:19:52', '', '0');
INSERT INTO `sys_menu` VALUES ('d0e72fe52a3045babd4a8e23f0be87bb', 'b83a3e8de12145dcae00fa0e2c8f0488', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,', '直播回顾', '180', '/live/review/list', '', '', '1', 'live:review:view', '1', '2016-08-13 17:29:43', '1', '2016-08-13 17:29:43', '', '0');
INSERT INTO `sys_menu` VALUES ('d45cc10209bb4a89bec0d19e03132b38', 'ce5b54b08d2f4c3c9b2789665d2409c4', '0,1,4d9730668e634e00b81c7ca00db5097c,43a47ad0d3c4499ba637499caca6320b,ce5b54b08d2f4c3c9b2789665d2409c4,', '导出', '60', '', '', '', '0', 'live:audienceRegister:export', '1', '2016-08-05 16:14:08', '1', '2016-11-08 15:21:30', '', '0');
INSERT INTO `sys_menu` VALUES ('df514c755492440dacb6802a6277a2b1', '28b8ae99d0e248dfb0e186b6f09c39fb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,28b8ae99d0e248dfb0e186b6f09c39fb,', '房间管理员权限', '90', '', '', '', '0', 'live:room:check', '1', '2016-10-11 14:42:46', '1', '2016-10-11 14:42:46', '调用该权限的用户可在直播房间列表页面点击直播主题进入到房间,此时是以管理员身份进入房间,除了具有主播的全部功能外,还额外具有结束房间的功能. 此权限功能强大,请谨慎分配', '0');
INSERT INTO `sys_menu` VALUES ('f1f06c12e3ca42ce94b90c8f273b65b6', '22fcb71f7b0548e489e54e30cf2d0ddb', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,22fcb71f7b0548e489e54e30cf2d0ddb,', '演讲稿审核', '90', '', '', '', '0', 'live:speech:audit', '1', '2016-08-08 14:57:02', '1', '2016-08-09 14:59:21', '', '0');
INSERT INTO `sys_menu` VALUES ('f24ff408828049e0acea869e81794f9c', 'ce5b54b08d2f4c3c9b2789665d2409c4', '0,1,4d9730668e634e00b81c7ca00db5097c,43a47ad0d3c4499ba637499caca6320b,ce5b54b08d2f4c3c9b2789665d2409c4,', '查看', '30', '', '', '', '0', 'live:liveAudienceRegister:view', '1', '2016-08-05 09:27:40', '1', '2016-08-05 15:26:47', '', '1');
INSERT INTO `sys_menu` VALUES ('f3ad92d258ef462a8ff6da4c6c1610dc', 'b83a3e8de12145dcae00fa0e2c8f0488', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,', '直播公司', '90', '/live/speaker/list', '', '', '1', 'live:speaker:view', '1', '2016-08-05 14:13:19', '1', '2016-08-05 14:13:34', '', '0');
INSERT INTO `sys_menu` VALUES ('fb4f0610c30b45508e8e92a5443bdea0', 'b83a3e8de12145dcae00fa0e2c8f0488', '0,1,4d9730668e634e00b81c7ca00db5097c,b83a3e8de12145dcae00fa0e2c8f0488,', '资料下载统计', '150', '/live/dataDownload/list', '', '', '1', 'live:dataDownload:view', '1', '2016-08-11 09:05:04', '1', '2016-08-11 09:05:04', '', '0');


-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '0f4f6729665fb527debcc93bdb78c097af8aa4714998d27ef496274e', null, '超级管理员', '', '8675', '8675', '', '/userfiles/1/images/2016/08/s02.jpg', '0:0:0:0:0:0:0:1', '2016-11-07 16:11:00', '1', '1', '2013-05-27 08:00:00', '1', '2016-08-18 16:05:10', '最高管理员', '0');
INSERT INTO `sys_user` VALUES ('100', 'TestGroup', '8a0c872d92236d2acdd3dd1505c9002dea5c82ff9622950ddcd20acd', null, '测试专用', 'luzhong@netwaymedia.com', '0755-88880405', '15989886405', '1', '/userfiles/1/images/photo/2016/04/%E7%AE%A1%E7%90%86%E5%91%98%E5%A4%B4%E5%83%8F_009.jpg', '192.168.2.251', '2016-05-26 14:36:56', '1', '1', '2016-04-16 13:46:48', '1', '2016-04-16 13:46:48', '测试人员现网验证所用，请勿修改、删除，谢谢！', '0');
INSERT INTO `sys_user` VALUES ('101', 'chujing', 'e8f9778c5d51b88069b99c38803e0b51958fd0a801a02969934fa603', null, '楚晶', 'chujing@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-26 17:28:41', '1', '1', '2016-04-16 15:50:29', '1', '2016-04-16 15:59:41', '', '0');
INSERT INTO `sys_user` VALUES ('102', 'teresaxiong', '2d8c598beed617119569f58b9a7a4a997d634b9f0e8327705772119c', null, '熊常春', 'xiongchangchun@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-26 17:43:58', '1', '1', '2016-04-16 15:59:24', '1', '2016-04-16 15:59:24', '', '0');
INSERT INTO `sys_user` VALUES ('103', 'jamielu', 'c36386e16496d6a5123450153ec4e09f6500cd8cca0bd4d75153e728', null, '芦志红', 'luzhihong@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-26 19:48:08', '1', '1', '2016-04-16 16:07:01', '1', '2016-05-20 09:47:26', '', '0');
INSERT INTO `sys_user` VALUES ('104', 'stacywang', '3d25f36b7446faed4fbcddba796c7f22531a45a66c8f339189ee5762', null, '王愈阳', 'wangyuyang@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-25 11:30:09', '1', '1', '2016-04-16 16:09:18', '1', '2016-04-16 16:09:18', '', '0');
INSERT INTO `sys_user` VALUES ('105', 'luoyuqing', '368b74488ad771f11149d6085570a2c4719a6dd4ad093ecf85efaa8e', null, '罗雨晴', 'luoyuqing@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-25 15:15:41', '1', '1', '2016-04-18 11:18:28', '1', '2016-04-18 11:18:28', '', '0');
INSERT INTO `sys_user` VALUES ('106', 'xielina', 'e5fe2e61bcba12f5612a8e05f99868e4f9a654484ca4eded3a5186b6', null, '谢丽娜', 'xielina@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-25 14:09:26', '1', '1', '2016-04-18 11:18:58', '1', '2016-04-18 11:18:58', '', '0');
INSERT INTO `sys_user` VALUES ('107', 'yangwei', '527fa7479d0a54c149a718063deb0577ab6cbb0751968a58cb6362f4', null, '杨维', 'yangwei@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-25 14:16:12', '1', '1', '2016-04-18 11:19:24', '1', '2016-04-18 11:19:24', '', '0');
INSERT INTO `sys_user` VALUES ('108', 'tianqi', 'd3afb5a5fea6a511c587cebced26f93c7ee045a1bedb04f263d2df77', null, '田琪', 'tianqi@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-24 17:00:11', '1', '1', '2016-04-18 11:19:49', '1', '2016-04-18 11:19:49', '', '0');
INSERT INTO `sys_user` VALUES ('109', 'zhouchen', 'f6ad710759c2edb29c9437dc1dd4c37e15b0bf9825a282e0246ce71b', null, '周辰', 'zhouchen@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-04-28 10:37:55', '1', '1', '2016-04-18 11:20:45', '1', '2016-04-18 11:20:45', '', '1');
INSERT INTO `sys_user` VALUES ('110', 'taolingxiao', '753d9c8cf76d356c763ea979b2fb12fdccd7a643d1b9fd31d7750d22', null, '陶凌霄', 'taolingxiao@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-25 09:16:46', '1', '1', '2016-04-18 11:21:15', '1', '2016-04-18 11:21:15', '', '0');
INSERT INTO `sys_user` VALUES ('111', 'lucywang', 'a379b74dfb65f14de49ce9d20f90c95fd7465436b72228bae5aa90d7', null, '王小艳', 'wangxiaoyan@netwaymedia.com', '', '', '3', '', null, null, '1', '1', '2016-04-18 12:03:43', '1', '2016-04-18 12:03:43', '', '0');
INSERT INTO `sys_user` VALUES ('112', 'zhaoqiang', 'd4dcfc29e5d48d076bc626dec48555a854ef864b15f6d57eab270838', null, '李兆强', 'lizhaoqiang@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-25 14:05:51', '1', '1', '2016-04-18 12:05:12', '1', '2016-04-18 12:05:12', '', '0');
INSERT INTO `sys_user` VALUES ('113', 'autumnyang', '964e43c3adfb619979af3445cf7f7e5a0e38f8985c48a5bc1ebe33b8', null, '杨秋妮', 'yangqiuni@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-25 12:19:34', '1', '1', '2016-04-19 17:52:17', '1', '2016-04-19 17:52:17', '', '0');
INSERT INTO `sys_user` VALUES ('114', 'zhouzhou', 'efc4b3dcc6b17ea7de3eade660bc9103a3c70d14b62df9e446c9eec2', null, '周舟', 'zhouzhou@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-12 10:04:19', '1', '1', '2016-04-19 17:52:54', '1', '2016-07-21 14:26:51', '', '0');
INSERT INTO `sys_user` VALUES ('115', 'yaolingfang', 'f4c2cf63159994a8da633baf101c933737c4775428e7e9f6acd6ae29', null, '姚玲芳', 'yaolingfang@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-26 14:15:54', '1', '1', '2016-04-19 17:53:41', '1', '2016-06-15 18:59:10', '', '0');
INSERT INTO `sys_user` VALUES ('116', 'liuyinyan', '99a40701d579d00ce1555d3020405c176360e214374d1b8b74c6ad56', null, '刘尹艳', 'liuyinyan@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-24 15:33:33', '1', '1', '2016-04-19 17:54:14', '1', '2016-06-15 19:00:31', '', '0');
INSERT INTO `sys_user` VALUES ('117', 'kangxiaotao', '6d4f6088e440a7a6f2d9742276b6e2db712aa390f1785a04a25b43f9', null, '康晓陶', 'kangxiaotao@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-19 16:53:09', '1', '1', '2016-04-19 17:54:38', '1', '2016-04-19 17:54:38', '', '0');
INSERT INTO `sys_user` VALUES ('118', 'zhongmingmei', 'a7475767d07248dcab276bad28f5056691b16a27da013fadeda8ec46', null, '钟明媚', 'zhongmingmei@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-26 16:46:48', '1', '1', '2016-04-19 17:55:11', '118', '2016-05-18 10:01:58', '', '0');
INSERT INTO `sys_user` VALUES ('119', 'zhangxiaofang', '291b728c288f32d03d6b9408a029285dce76b5f94ba45a38f8ed4371', null, '张小芳', 'zhangxiaofang@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-26 19:41:02', '1', '1', '2016-04-20 11:16:53', '1', '2016-04-20 11:24:48', '', '0');
INSERT INTO `sys_user` VALUES ('120', 'wuweifeng', '9224981c7a8b3f6fc7338b771c019202bd67430c3b5131580207fbfc', null, '吴伟锋', 'wuweifeng@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-26 20:09:38', '1', '1', '2016-04-20 11:17:54', '1', '2016-06-15 18:59:07', '', '0');
INSERT INTO `sys_user` VALUES ('121', 'huangcaifei', 'bf93593e8ef7435ccf392f4245ddc29cfe6171e634b69c8cd33a3786', null, '黄彩飞', 'huangcaifei@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-26 16:37:17', '1', '1', '2016-04-20 11:18:21', '1', '2016-04-20 11:22:50', '', '0');
INSERT INTO `sys_user` VALUES ('123', 'zhangxing', '26330b7fe1a6f48988abf9b6fee56c7c6624cb162e57c0c4604372eb', null, '张星', 'zhangxing@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-25 11:27:57', '1', '1', '2016-04-22 11:28:53', '1', '2016-04-22 11:28:53', '', '0');
INSERT INTO `sys_user` VALUES ('124', 'linjunxiong', '8aacd7cee2ba5873411de4e5289acbfb3aca7fe26d95a2646d95a49b', null, '林俊雄', 'linjunxiong@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-04 16:36:51', '1', '1', '2016-05-04 15:57:21', '1', '2016-05-20 09:47:55', '', '1');
INSERT INTO `sys_user` VALUES ('125', 'zhutingting', 'c744c49fe92521e7a64972ecf14f9b9a84427486f77d41789f150ec5', null, '朱婷婷', 'zhutingting@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-16 09:14:11', '1', '1', '2016-05-09 09:59:31', '1', '2016-05-09 09:59:31', '', '1');
INSERT INTO `sys_user` VALUES ('126', 'liyunli', '1835ada984d7f4b8feeee2eff94f88de132d74be832690093577dd21', null, '李云丽', 'liyunli@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-14 12:19:31', '1', '1', '2016-05-09 10:00:08', '1', '2016-05-09 10:00:08', '', '1');
INSERT INTO `sys_user` VALUES ('127', 'jiangningjing', '13661a073b3e34d22c1e3664674055b75d8b85cdb2a0955150b2027d', null, '蒋宁静', 'jiangningjing@ofweek.com', '', '', '3', '', null, null, '1', '1', '2016-05-09 10:01:03', '1', '2016-05-20 10:12:30', '', '0');
INSERT INTO `sys_user` VALUES ('128', 'zhouyan', '31f4191956b893ecf2f3e00b3ac713c45eb67792736bd0b2ba55546a', null, '周艳', 'zhouyan@netwaymedia.com', '', '', '3', '', null, null, '1', '1', '2016-05-09 10:01:52', '1', '2016-05-09 10:01:52', '', '1');
INSERT INTO `sys_user` VALUES ('129', 'zhuohui', '9284f484e3119fcd06a8bd6a0ce159da5bc77c8289010eeaa417bd71', null, '卓晖', 'zhuohui@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-09 11:31:54', '1', '1', '2016-05-09 10:02:45', '1', '2016-05-09 10:02:45', '', '1');
INSERT INTO `sys_user` VALUES ('130', 'chenxiaojing', '811425687cbcff2ee2bdd621236d8bfe9350f6f4dcc4c2c8606c8797', null, '陈晓静', 'chenxiaojing@netwaymedia.com', '', '', '3', '', '192.168.2.251', '2016-05-25 16:32:03', '1', '1', '2016-05-12 16:38:23', '1', '2016-05-12 16:38:23', '', '0');
INSERT INTO `sys_user` VALUES ('131', 'market', '37d5664f722277f55208f150237317edbaa6ffdb6b6bfbdaf7dcdedd', null, '市场部', 'zhangxiaofang@ofweek.com', '', '', '3', '', '192.168.2.251', '2016-05-26 18:57:49', '1', '1', '2016-05-13 14:10:49', '1', '2016-05-20 09:41:27', '', '0');
INSERT INTO `sys_user` VALUES ('132', 'fengjingwei', '7b827801d085dd5a777c61d3ef13fa1001a5f1daac508fa4ed60a77a', null, 'fengjingwei', '', '', '', '', '', '127.0.0.1', '2016-10-20 10:43:04', '1', '1', '2016-07-04 11:01:39', '1', '2016-07-21 14:26:55', '', '0');
INSERT INTO `sys_user` VALUES ('136', 'More', '1d27c0da32e6b21abdeeea354348c50293b439c4b8be168ade3f4545', null, 'More', '', '', '', '', '', null, null, '1', '1', '2016-07-21 14:31:40', '1', '2016-07-21 14:35:27', '', '1');
INSERT INTO `sys_user` VALUES ('137', 'ck', '2a4679cdb2f542ec1b4ebaf8a9122c6d60e1d5da77bd7890b333dc24', null, 'ck', '', '', '', '', '', '127.0.0.1', '2016-07-21 15:41:41', '1', '1', '2016-07-21 14:39:05', '1', '2016-10-14 16:36:49', '', '1');
INSERT INTO `sys_user` VALUES ('2', 'test', '102766ce4488f771184bf2e2f880f8feab8e7768b47d52e8edc6bf20', null, '系统管理员', null, '', '', '', '', '0:0:0:0:0:0:0:1', '2016-02-23 15:26:33', '1', '1', '2016-02-23 15:15:31', '1', '2016-02-23 15:19:43', '', '0');
INSERT INTO `sys_user` VALUES ('3', 'test1', '7a6775ad95bb868579e9b1b2e67667fbb2e9f0901a545e33fec056a9', null, '运营人员', null, '', '', '', '', '192.168.2.68', '2016-07-28 15:06:59', '1', '1', '2016-02-23 15:20:46', '1', '2016-03-09 17:40:10', '', '0');


-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('100', '04c61e3615224a5ba9585ea303973aa1');
INSERT INTO `sys_user_role` VALUES ('101', 'df38acf552a0443793446c2bcbd47d9a');
INSERT INTO `sys_user_role` VALUES ('102', 'df38acf552a0443793446c2bcbd47d9a');
INSERT INTO `sys_user_role` VALUES ('103', 'ec3c9981df8a4e4d9d52663e0421ad19');
INSERT INTO `sys_user_role` VALUES ('104', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('105', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('106', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('107', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('108', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('109', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('110', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('111', '67ed35bbeb814fa8a9f130155d813bd1');
INSERT INTO `sys_user_role` VALUES ('112', '67ed35bbeb814fa8a9f130155d813bd1');
INSERT INTO `sys_user_role` VALUES ('113', 'c9b4386d3ab34b93a2ab0c25184c2adb');
INSERT INTO `sys_user_role` VALUES ('114', '7745fab47242415491f305bf5002b92c');
INSERT INTO `sys_user_role` VALUES ('115', '7745fab47242415491f305bf5002b92c');
INSERT INTO `sys_user_role` VALUES ('116', '7745fab47242415491f305bf5002b92c');
INSERT INTO `sys_user_role` VALUES ('117', '7745fab47242415491f305bf5002b92c');
INSERT INTO `sys_user_role` VALUES ('118', '7745fab47242415491f305bf5002b92c');
INSERT INTO `sys_user_role` VALUES ('119', '425a012a1aae44be9e2566015ca10204');
INSERT INTO `sys_user_role` VALUES ('120', 'f266ce77531b4a40a08548bbee9ed976');
INSERT INTO `sys_user_role` VALUES ('121', 'f266ce77531b4a40a08548bbee9ed976');
INSERT INTO `sys_user_role` VALUES ('123', 'df38acf552a0443793446c2bcbd47d9a');
INSERT INTO `sys_user_role` VALUES ('124', '35b3c732fce74a69b6d24f83385f2c57');
INSERT INTO `sys_user_role` VALUES ('125', '35b3c732fce74a69b6d24f83385f2c57');
INSERT INTO `sys_user_role` VALUES ('126', '35b3c732fce74a69b6d24f83385f2c57');
INSERT INTO `sys_user_role` VALUES ('127', '425a012a1aae44be9e2566015ca10204');
INSERT INTO `sys_user_role` VALUES ('128', '35b3c732fce74a69b6d24f83385f2c57');
INSERT INTO `sys_user_role` VALUES ('129', '35b3c732fce74a69b6d24f83385f2c57');
INSERT INTO `sys_user_role` VALUES ('130', 'f34a591c206c4995951a9116fa050f35');
INSERT INTO `sys_user_role` VALUES ('131', '35b3c732fce74a69b6d24f83385f2c57');
INSERT INTO `sys_user_role` VALUES ('132', '04c61e3615224a5ba9585ea303973aa1');
INSERT INTO `sys_user_role` VALUES ('136', '1');
INSERT INTO `sys_user_role` VALUES ('137', '1');
INSERT INTO `sys_user_role` VALUES ('2', '1');
INSERT INTO `sys_user_role` VALUES ('3', '1');
INSERT INTO `sys_user_role` VALUES ('3', '2');

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '0a8f5ed704604503a13974d823e5150b');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '1');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '10');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '12');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '13');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '2');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '20');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '22');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '22fcb71f7b0548e489e54e30cf2d0ddb');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '26abc8b1ec764d93a2219bc791c10ae4');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '27');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '28');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '28b8ae99d0e248dfb0e186b6f09c39fb');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '29');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '29d429cc87a040288b7f9b0bdcb749e5');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '2ec897110add46949309f9ec45917338');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '2feb629ee13a4c4598b5c4c9578bb0e8');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '3');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '30');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '4');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '43a47ad0d3c4499ba637499caca6320b');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '451bf4b6c341447b89d78ee19edbfd5f');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '4d9730668e634e00b81c7ca00db5097c');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '56');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '57');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '58');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '59');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '6');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '67');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '68');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '6bfedca09fa0416ea70a756cd0b05de8');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '7');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '71');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '80c13a82a15b490f9efed00b097d5e73');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '836be71abdaf4f56b014a19601def3e6');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '84');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '84f9be930e1b45c0933f6e015ab76e5e');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '8befb28b8681428f8ae33e060a3f194f');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '9');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '939d2289195e42c5a701d70ca8e6ae9d');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', '9e72a8769d174d648e8aa675cee35b7e');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'a4975a92e7404f659f858cd5471c7b19');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'b0b63b5da531433d957c3520e51dc773');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'b83a3e8de12145dcae00fa0e2c8f0488');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'c241d463d36741f5a52e5a84821c2262');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'c835ca2eb0c04eaabecd94602e795eb8');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'cd146fed256d4282a118f36c5d6bf6bf');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'ce5b54b08d2f4c3c9b2789665d2409c4');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'd0e72fe52a3045babd4a8e23f0be87bb');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'd45cc10209bb4a89bec0d19e03132b38');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'df514c755492440dacb6802a6277a2b1');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'f1f06c12e3ca42ce94b90c8f273b65b6');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'f24ff408828049e0acea869e81794f9c');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'f3ad92d258ef462a8ff6da4c6c1610dc');
INSERT INTO `sys_role_menu` VALUES ('04c61e3615224a5ba9585ea303973aa1', 'fb4f0610c30b45508e8e92a5443bdea0');
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '10');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '13');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '20');
INSERT INTO `sys_role_menu` VALUES ('1', '22');
INSERT INTO `sys_role_menu` VALUES ('1', '22fcb71f7b0548e489e54e30cf2d0ddb');
INSERT INTO `sys_role_menu` VALUES ('1', '27');
INSERT INTO `sys_role_menu` VALUES ('1', '28');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '4d9730668e634e00b81c7ca00db5097c');
INSERT INTO `sys_role_menu` VALUES ('1', '56');
INSERT INTO `sys_role_menu` VALUES ('1', '57');
INSERT INTO `sys_role_menu` VALUES ('1', '58');
INSERT INTO `sys_role_menu` VALUES ('1', '59');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '67');
INSERT INTO `sys_role_menu` VALUES ('1', '68');
INSERT INTO `sys_role_menu` VALUES ('1', '7');
INSERT INTO `sys_role_menu` VALUES ('1', '71');
INSERT INTO `sys_role_menu` VALUES ('1', '84');
INSERT INTO `sys_role_menu` VALUES ('1', '8befb28b8681428f8ae33e060a3f194f');
INSERT INTO `sys_role_menu` VALUES ('1', '9');
INSERT INTO `sys_role_menu` VALUES ('1', 'b83a3e8de12145dcae00fa0e2c8f0488');
INSERT INTO `sys_role_menu` VALUES ('2', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '27');
INSERT INTO `sys_role_menu` VALUES ('2', '28');
INSERT INTO `sys_role_menu` VALUES ('2', '29');
INSERT INTO `sys_role_menu` VALUES ('2', '30');
INSERT INTO `sys_role_menu` VALUES ('2', '56');
INSERT INTO `sys_role_menu` VALUES ('2', '57');
INSERT INTO `sys_role_menu` VALUES ('2', '58');
INSERT INTO `sys_role_menu` VALUES ('2', '59');
INSERT INTO `sys_role_menu` VALUES ('2', '71');
INSERT INTO `sys_role_menu` VALUES ('35b3c732fce74a69b6d24f83385f2c57', '1');
INSERT INTO `sys_role_menu` VALUES ('35b3c732fce74a69b6d24f83385f2c57', '27');
INSERT INTO `sys_role_menu` VALUES ('35b3c732fce74a69b6d24f83385f2c57', '28');
INSERT INTO `sys_role_menu` VALUES ('35b3c732fce74a69b6d24f83385f2c57', '30');
INSERT INTO `sys_role_menu` VALUES ('425a012a1aae44be9e2566015ca10204', '1');
INSERT INTO `sys_role_menu` VALUES ('425a012a1aae44be9e2566015ca10204', '27');
INSERT INTO `sys_role_menu` VALUES ('425a012a1aae44be9e2566015ca10204', '28');
INSERT INTO `sys_role_menu` VALUES ('425a012a1aae44be9e2566015ca10204', '29');
INSERT INTO `sys_role_menu` VALUES ('425a012a1aae44be9e2566015ca10204', '30');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '1');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '13');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '1fc6890dc1b848a68ce7080958902381');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '2');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '20');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '22');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '27');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '28');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '29');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '30');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '56');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '57');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '58');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '59');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '67');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '68');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '71');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '7c14cb1761384ee19fe680c9a1b7d404');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', '84');
INSERT INTO `sys_role_menu` VALUES ('67ed35bbeb814fa8a9f130155d813bd1', 'd1aa8a2e358a4aaa9e3dc92ae0062fa7');
INSERT INTO `sys_role_menu` VALUES ('7745fab47242415491f305bf5002b92c', '1');
INSERT INTO `sys_role_menu` VALUES ('7745fab47242415491f305bf5002b92c', '27');
INSERT INTO `sys_role_menu` VALUES ('7745fab47242415491f305bf5002b92c', '28');
INSERT INTO `sys_role_menu` VALUES ('7745fab47242415491f305bf5002b92c', '29');
INSERT INTO `sys_role_menu` VALUES ('7745fab47242415491f305bf5002b92c', '30');
INSERT INTO `sys_role_menu` VALUES ('7745fab47242415491f305bf5002b92c', 'd1aa8a2e358a4aaa9e3dc92ae0062fa7');
INSERT INTO `sys_role_menu` VALUES ('8bc4495ea9704f56a3c18bb06549d565', '1');
INSERT INTO `sys_role_menu` VALUES ('8bc4495ea9704f56a3c18bb06549d565', '27');
INSERT INTO `sys_role_menu` VALUES ('8bc4495ea9704f56a3c18bb06549d565', '28');
INSERT INTO `sys_role_menu` VALUES ('8bc4495ea9704f56a3c18bb06549d565', '30');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '0a8f5ed704604503a13974d823e5150b');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '1');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '22fcb71f7b0548e489e54e30cf2d0ddb');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '28b8ae99d0e248dfb0e186b6f09c39fb');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '29d429cc87a040288b7f9b0bdcb749e5');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '2ec897110add46949309f9ec45917338');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '4d9730668e634e00b81c7ca00db5097c');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '6bfedca09fa0416ea70a756cd0b05de8');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '836be71abdaf4f56b014a19601def3e6');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '8befb28b8681428f8ae33e060a3f194f');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', '939d2289195e42c5a701d70ca8e6ae9d');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'a4975a92e7404f659f858cd5471c7b19');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'b0b63b5da531433d957c3520e51dc773');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'b83a3e8de12145dcae00fa0e2c8f0488');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'c241d463d36741f5a52e5a84821c2262');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'c835ca2eb0c04eaabecd94602e795eb8');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'cd146fed256d4282a118f36c5d6bf6bf');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'd0e72fe52a3045babd4a8e23f0be87bb');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'df514c755492440dacb6802a6277a2b1');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'f1f06c12e3ca42ce94b90c8f273b65b6');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'f3ad92d258ef462a8ff6da4c6c1610dc');
INSERT INTO `sys_role_menu` VALUES ('a860ae882ca3416b9516226e335cd11c', 'fb4f0610c30b45508e8e92a5443bdea0');
INSERT INTO `sys_role_menu` VALUES ('c9b4386d3ab34b93a2ab0c25184c2adb', '1');
INSERT INTO `sys_role_menu` VALUES ('c9b4386d3ab34b93a2ab0c25184c2adb', '27');
INSERT INTO `sys_role_menu` VALUES ('c9b4386d3ab34b93a2ab0c25184c2adb', '28');
INSERT INTO `sys_role_menu` VALUES ('c9b4386d3ab34b93a2ab0c25184c2adb', '29');
INSERT INTO `sys_role_menu` VALUES ('c9b4386d3ab34b93a2ab0c25184c2adb', '30');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '1');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '2');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '27');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '28');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '29');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '30');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '67');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '68');
INSERT INTO `sys_role_menu` VALUES ('d75e85db5e074fc485ca04ebf865a586', '84');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '1');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '1fc6890dc1b848a68ce7080958902381');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '27');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '28');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '29');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '30');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', '7c14cb1761384ee19fe680c9a1b7d404');
INSERT INTO `sys_role_menu` VALUES ('df38acf552a0443793446c2bcbd47d9a', 'd1aa8a2e358a4aaa9e3dc92ae0062fa7');
INSERT INTO `sys_role_menu` VALUES ('ec3c9981df8a4e4d9d52663e0421ad19', '1');
INSERT INTO `sys_role_menu` VALUES ('ec3c9981df8a4e4d9d52663e0421ad19', '27');
INSERT INTO `sys_role_menu` VALUES ('ec3c9981df8a4e4d9d52663e0421ad19', '28');
INSERT INTO `sys_role_menu` VALUES ('ec3c9981df8a4e4d9d52663e0421ad19', '29');
INSERT INTO `sys_role_menu` VALUES ('ec3c9981df8a4e4d9d52663e0421ad19', '30');
INSERT INTO `sys_role_menu` VALUES ('f266ce77531b4a40a08548bbee9ed976', '1');
INSERT INTO `sys_role_menu` VALUES ('f266ce77531b4a40a08548bbee9ed976', '27');
INSERT INTO `sys_role_menu` VALUES ('f266ce77531b4a40a08548bbee9ed976', '28');
INSERT INTO `sys_role_menu` VALUES ('f266ce77531b4a40a08548bbee9ed976', '29');
INSERT INTO `sys_role_menu` VALUES ('f266ce77531b4a40a08548bbee9ed976', '30');
INSERT INTO `sys_role_menu` VALUES ('f266ce77531b4a40a08548bbee9ed976', 'd1aa8a2e358a4aaa9e3dc92ae0062fa7');
INSERT INTO `sys_role_menu` VALUES ('f34a591c206c4995951a9116fa050f35', '1');
INSERT INTO `sys_role_menu` VALUES ('f34a591c206c4995951a9116fa050f35', '27');
INSERT INTO `sys_role_menu` VALUES ('f34a591c206c4995951a9116fa050f35', '28');
INSERT INTO `sys_role_menu` VALUES ('f34a591c206c4995951a9116fa050f35', '29');
INSERT INTO `sys_role_menu` VALUES ('f34a591c206c4995951a9116fa050f35', '30');