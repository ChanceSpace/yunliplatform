SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `yj_poster_carrier_picture`;
CREATE TABLE `yj_poster_carrier_picture` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `upload_filename` varchar(100) default '',
  `actual_filename` varchar(100) default '',
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `yj_poster_carrier`;
CREATE TABLE `yj_poster_carrier` (
  `uuid` varchar(30) NOT NULL,
  `p_description` text,
  `p_contact_person` varchar(48) default '',
  `p_contact_phone` varchar(48) default '',
  `p_contact_address` varchar(1024) default '',
  `u_published` tinyint(1) default '0' COMMENT '1 for YES or 0 for NO',
  `company_uuid` varchar(30) DEFAULT NULL,
  `company_name` varchar(120) DEFAULT NULL,
  `template_uuid` varchar(30) DEFAULT NULL,
  `poster_picture_uuid` varchar(30) default NULL,
  FOREIGN KEY (`template_uuid`) REFERENCES `yj_poster_template` (`uuid`),
  FOREIGN KEY (`poster_picture_uuid`) REFERENCES `yj_poster_carrier_picture` (`uuid`),
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `zp_contact_charging`;
CREATE TABLE `zp_contact_charging` (
  `uuid` varchar(30) NOT NULL,
  `c_status` varchar(20) DEFAULT NULL,
  `c_address` varchar(30) DEFAULT NULL,
  `c_create_time` datetime DEFAULT NULL,
  `c_finish_time` datetime DEFAULT NULL,
  `c_number` varchar(30) DEFAULT NULL,
  `c_types` varchar(20) DEFAULT NULL,
  `company_uuid` varchar(30) DEFAULT NULL,
  `company_name` varchar(120) DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS=1;