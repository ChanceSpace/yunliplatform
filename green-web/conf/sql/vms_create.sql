SET FOREIGN_KEY_CHECKS=0;

#系统人管相关----------------------------------

DROP TABLE IF EXISTS `yj_user_info`;
CREATE TABLE `yj_user_info` (
  `uuid` varchar(30) NOT NULL,
  `u_name` varchar(120) default NULL,
  `u_register_time` datetime default NULL,
  `u_num` varchar(48) default '',
  `u_mobile` varchar(12) default '',
  `u_faren` varchar(48) default '',
  `u_postcode` varchar(48) default '',
  `u_address` varchar(1024) default '',
  `u_note` text,
  `u_user_type` varchar(20) default 'U_CARRIER',
  `u_service_type` varchar(20) default 'T_COMPANY',
  `parent_uuid` varchar(30) default NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`parent_uuid`) REFERENCES `yj_user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `yj_user_account`;
CREATE TABLE `yj_user_account` (
  `uuid` varchar(30) NOT NULL,
  `u_name` varchar(120) default NULL,
  `u_username` varchar(48) default '',
  `u_password` varchar(48) default '',
  `u_enabled` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  `u_register_time` datetime default NULL,
  `u_mobile` varchar(12) default '',
  `user_uuid` varchar(30) default NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`user_uuid`) REFERENCES `yj_user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `yj_user_account` ADD INDEX user_index_username(`u_username`);

DROP TABLE IF EXISTS `yj_role_type`;
CREATE TABLE `yj_role_type` (
  `uuid` varchar(30) NOT NULL,
  `u_role_name` varchar(40) default NULL,
  `u_role_type` varchar(30) default NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `yj_user_role_link`;
CREATE TABLE `yj_user_role_link` (
  `account_uuid` varchar(30) DEFAULT NULL,
  `role_uuid` varchar(30) DEFAULT NULL,
  FOREIGN KEY (`account_uuid`) REFERENCES `yj_user_account` (`uuid`),
  FOREIGN KEY (`role_uuid`) REFERENCES `yj_role_type` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#承运商宣传部分
DROP TABLE IF EXISTS `yj_poster_template`;
CREATE TABLE `yj_poster_template` (
  `uuid` varchar(30) NOT NULL,
  `p_title` varchar(120) default NULL,
  `p_content` text,
  `o_status` varchar(20) default 'CLOSE',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `yj_poster_carrier`;
CREATE TABLE `yj_poster_carrier` (
  `uuid` varchar(30) NOT NULL,
  `p_description` text,
  `p_contact_person` varchar(48) default '',
  `p_contact_phone` varchar(48) default '',
  `p_contact_address` varchar(1024) default '',
  `u_published` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  `user_uuid` varchar(30) DEFAULT NULL,
  `template_uuid` varchar(30) DEFAULT NULL,
  `poster_picture_uuid` varchar(30) default NULL,
  FOREIGN KEY (`user_uuid`) REFERENCES `yj_user_info` (`uuid`),
  FOREIGN KEY (`template_uuid`) REFERENCES `yj_poster_template` (`uuid`),
  FOREIGN KEY (`poster_picture_uuid`) REFERENCES `yj_poster_carrier_picture` (`uuid`),
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `yj_poster_carrier_picture`;
CREATE TABLE `yj_poster_carrier_picture` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `upload_filename` varchar(100) default '',
  `actual_filename` varchar(100) default '',
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#车辆相关---------------------------------

DROP TABLE IF EXISTS `yj_carrier_vehicle`;
CREATE TABLE `yj_carrier_vehicle` (
  `uuid` varchar(30) NOT NULL,
  `v_license_num` varchar(8) DEFAULT NULL,
  `v_vin` varchar(17) DEFAULT NULL,
  `v_status` varchar(20) DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `module_brand` varchar(100) DEFAULT NULL,
  `owner_uuid` varchar(30) DEFAULT NULL,
  `user_uuid` varchar(30) DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`owner_uuid`) REFERENCES `yj_user_info` (`uuid`),
  FOREIGN KEY (`user_uuid`) REFERENCES `yj_user_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `yj_carrier_vehicle` ADD INDEX vehicle_index_num(`v_license_num`);

#订单部分---------------------------------

DROP TABLE IF EXISTS `yj_order_service`;
CREATE TABLE `yj_order_service` (
  `uuid` varchar(30) NOT NULL,
  `o_create_time` datetime default NULL,
  `o_finish_time` datetime default NULL,
  `o_license_num` varchar(20) default NULL,
  `o_carrier_name` varchar(120) default NULL,
  `o_carrier_uuid` varchar(30) default '',
  `o_contact_name` varchar(120) default NULL,
  `o_contact_phone` varchar(30) default '',
  `o_status` varchar(20) default 'M_CREATED',
  `o_service` varchar(20) default 'MAINTENANCE',
  `o_note` text,
  `o_number` varchar(20) default NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `yj_order_service` ADD INDEX vehicle_index_carrier_uuid (`o_carrier_uuid`);

DROP TABLE IF EXISTS `yj_order_service_log`;
CREATE TABLE `yj_order_service_log` (
  `uuid` varchar(30) NOT NULL,
  `o_create_time` datetime default NULL,
  `o_actor_name` varchar(120) default NULL,
  `o_actor_uuid` varchar(30) default '',
  `o_note` text,
  `order_service_uuid` varchar(30) default NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#销售意向部分---------------------------------

DROP TABLE IF EXISTS `yj_order_sale`;
CREATE TABLE `yj_order_sale` (
  `uuid` varchar(30) NOT NULL,
  `o_create_time` datetime default NULL,
  `o_finish_time` datetime default NULL,
  `o_license_num` varchar(20) default NULL,
  `o_carrier_name` varchar(120) default NULL,
  `o_carrier_uuid` varchar(30) default '',
  `o_contact_name` varchar(120) default NULL,
  `o_contact_phone` varchar(30) default '',
  `o_status` varchar(20) default 'M_PROCESS',
  `o_sale` varchar(20) default 'RENT',
  `o_note` text,
  `o_number` varchar(20) default NULL,
  `module_brand` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `yj_order_sale` ADD INDEX vehicle_index_carrier_uuid (`o_carrier_uuid`);

DROP TABLE IF EXISTS `yj_order_sale_log`;
CREATE TABLE `yj_order_sale_log` (
  `uuid` varchar(30) NOT NULL,
  `o_create_time` datetime default NULL,
  `o_actor_name` varchar(120) default NULL,
  `o_actor_uuid` varchar(30) default '',
  `o_note` text,
  `order_sale_uuid` varchar(30) default NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS=1;