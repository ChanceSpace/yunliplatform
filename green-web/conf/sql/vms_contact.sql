
SET FOREIGN_KEY_CHECKS=0;

#合同部分

DROP TABLE IF EXISTS `zp_contact_info`;
CREATE TABLE `zp_contact_info` (
  `uuid` varchar(30) NOT NULL,
  `contact_type` varchar(20) DEFAULT NULL,
  `bao_dian_type` varchar(20) DEFAULT NULL,
  `sale_man_uuid` varchar(30) DEFAULT NULL,
  `sale_man_name` varchar(20) NOT NULL,
  `creator_uuid` varchar(30) NOT NULL,
  `creator_name` varchar(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `contact_status` varchar(20) DEFAULT NULL,
  `jiafang_uuid` varchar(30) DEFAULT NULL,
  `jiafang_name` varchar(100) DEFAULT NULL,
  `jiafang_faren` varchar(20) DEFAULT NULL,
  `jiafang_address` varchar(255) DEFAULT NULL,
  `jiafang_postcode` varchar(10) DEFAULT NULL,
  `yifang_uuid` varchar(30) DEFAULT NULL,
  `yifang_name` varchar(100) DEFAULT NULL,
  `yifang_faren` varchar(100) DEFAULT NULL,
  `yifang_address` varchar(255) DEFAULT NULL,
  `yifang_postcode` varchar(10) DEFAULT NULL,
  `yifang_contact_name` varchar(10) DEFAULT NULL,
  `yifang_contact_phone` varchar(30) DEFAULT NULL,
  `yifang_type` varchar(20) DEFAULT 0,
  `orig_yajin_fee` decimal(11, 2) DEFAULT 0,
  `zujin_haspay` decimal(11, 2) DEFAULT 0,
  `yajin_haspay` decimal(11, 2) DEFAULT 0,
  `begin_execute` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `end_execute` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `qian_fei` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `qian_fei_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `zp_contact_info` ADD INDEX contact_index_number(`contact_number`);
ALTER TABLE `zp_contact_info` ADD INDEX contact_index_jiafang_uuid(`jiafang_uuid`);
ALTER TABLE `zp_contact_info` ADD INDEX contact_index_yifang_uuid(`yifang_uuid`);

DROP TABLE IF EXISTS `zp_contact_file`;
CREATE TABLE `zp_contact_file` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  `upload_filename` varchar(100) default '',
  `actual_filename` varchar(100) default '',
  `contact_uuid` varchar(30) default NULL,
  PRIMARY KEY  (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES zp_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_log`;
CREATE TABLE `zp_contact_log` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  `description` varchar(100) default '',
  `contact_uuid` varchar(30) default NULL,
  PRIMARY KEY  (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES zp_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_vehicle_module`;
CREATE TABLE `zp_contact_vehicle_module` (
  `uuid` varchar(30) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `module_type` varchar(20) DEFAULT NULL,
  `module_brand` varchar(100) DEFAULT NULL,
  `module_color` varchar(128) DEFAULT NULL,
  `rent_number` int(11) DEFAULT 0,
  `rent_month` int(11) DEFAULT 0,
  `delay_month` int(4) DEFAULT 0,
  `actual_rent_fee` decimal(11, 2) DEFAULT 0,
  `single_ya_jin` decimal(11, 2) DEFAULT 0,
  `yajin_type` varchar(20) DEFAULT 0,
  `yajin_fenqishu` varchar(20) DEFAULT 0,
  `yajin_shoufu` decimal(11,2) DEFAULT NULL,
  `yajin_yuegong` decimal(11,2) DEFAULT NULL,
  `repay_type` varchar(20) DEFAULT 0,
  `contact_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES zp_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_execute`;
CREATE TABLE `zp_contact_execute` (
  `uuid` varchar(30) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `module_type` varchar(20) DEFAULT NULL,
  `module_brand` varchar(100) DEFAULT NULL,
  `module_color` varchar(128) DEFAULT NULL,
  `vehicle_num` varchar(8) DEFAULT NULL,
  `rent_month` int(4) DEFAULT 0,
  `delay_month` int(4) DEFAULT 0,
  `actual_rent_fee` decimal(11, 2) DEFAULT 0,
  `single_ya_jin` decimal(11, 2) DEFAULT 0,
  `tiche_pici` int(5) DEFAULT 0,
  `yajin_haspay_cishu` int(5) DEFAULT 0,
  `tiche_date` datetime DEFAULT NULL,
  `next_fee_date` datetime DEFAULT NULL,
  `next_message_date` datetime DEFAULT NULL,
  `actual_zj_month` datetime DEFAULT NULL,
  `actual_yj_month` datetime DEFAULT NULL,
  `jie_shu_date` datetime DEFAULT NULL,
  `next_yajin_date` datetime DEFAULT NULL,
  `is_over` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `pici_over` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `over_not_revert` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  `yajin_type` varchar(20) DEFAULT 0,
  `yajin_fenqishu` varchar(20) DEFAULT 0,
  `yajin_shoufu` decimal(11,2) DEFAULT NULL,
  `yajin_yuegong` decimal(11,2) DEFAULT NULL,
  `repay_type` varchar(20) DEFAULT 0,
  `contact_uuid` varchar(30) DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES zp_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_rentfee_his`;
CREATE TABLE `zp_contact_rentfee_his` (
  `uuid` varchar(30) NOT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `vehicle_number` varchar(8) DEFAULT NULL,
  `happend_date` datetime DEFAULT NULL,
  `actual_fee_date` datetime DEFAULT NULL,
  `fee_total` decimal(11, 2) DEFAULT 0,
  `tiche_pici` int(5) DEFAULT 0,
  `jiaoyi_ren` varchar(100) DEFAULT 0,
  `jiaoyi_ren_uuid` varchar(30) DEFAULT 0,
  `tian_jia` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `repay_location` varchar(20) DEFAULT 0,
  `contact_uuid` varchar(30) DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES zp_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `zp_contact_execute_change`;
CREATE TABLE `zp_contact_execute_change` (
  `uuid` varchar(30) NOT NULL,
  `change_date` datetime DEFAULT NULL,
  `caozuo_ren` varchar(100) DEFAULT NULL,
  `caozuo_ren_uuid` varchar(30) DEFAULT NULL,
  `vehicle_num_before` varchar(8) DEFAULT NULL,
  `vehicle_num_now` varchar(8) DEFAULT NULL,
  `execute_uuid` varchar(30) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES `zp_contact_info` (`uuid`),
  FOREIGN KEY (`execute_uuid`) REFERENCES zp_contact_execute (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_execute_finished`;
CREATE TABLE `zp_contact_execute_finished` (
  `uuid` varchar(30) NOT NULL,
  `finish_date` datetime DEFAULT NULL,
  `finish_type` varchar(20) DEFAULT NULL,
  `charge_type` varchar(20) DEFAULT NULL,
  `finish_num` varchar(20) DEFAULT NULL,
  `finish_fee` decimal(11, 2) DEFAULT 0,
  `finish_note` text,
  `actor_uuid` varchar(30) DEFAULT NULL,
  `actor_name` varchar(100) DEFAULT NULL,
  `execute_uuid` varchar(30) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES `zp_contact_info` (`uuid`),
  FOREIGN KEY (`execute_uuid`) REFERENCES zp_contact_execute (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#补充协议部分

DROP TABLE IF EXISTS `zp_contact_supply`;
CREATE TABLE `zp_contact_supply` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `caozuo_ren` varchar(100) DEFAULT NULL,
  `caozuo_ren_uuid` varchar(30) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES `zp_contact_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_supply_content`;
CREATE TABLE `zp_contact_supply_content` (
  `uuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `supply_uuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`supply_uuid`) REFERENCES `zp_contact_supply` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#充电桩部分

DROP TABLE IF EXISTS `zp_contact_charging`;
CREATE TABLE `zp_contact_charging` (
  `uuid` varchar(30) NOT NULL,
  `c_status` varchar(20) DEFAULT NULL,
  `c_address` varchar(30) DEFAULT NULL,
  `user_uuid` varchar(30) DEFAULT NULL,
  `c_create_time` datetime DEFAULT NULL,
  `c_finish_time` datetime DEFAULT NULL,
  `c_number` varchar(30) DEFAULT NULL,
  `c_types` varchar(20) DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`user_uuid`) REFERENCES `yj_user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

