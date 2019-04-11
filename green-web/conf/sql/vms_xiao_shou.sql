SET FOREIGN_KEY_CHECKS=0;

#合同部分

DROP TABLE IF EXISTS `xs_contact_info`;
CREATE TABLE `xs_contact_info` (
  `uuid` varchar(30) NOT NULL,
  `xiao_shou_type` varchar(20) DEFAULT NULL,
  `bao_dian_type` varchar(20) DEFAULT NULL,
  `contact_status` varchar(20) DEFAULT NULL,
  `yifang_type` varchar(20) DEFAULT NULL,
  `sale_man_uuid` varchar(30) DEFAULT NULL,
  `sale_man_name` varchar(20) NOT NULL,
  `creator_uuid` varchar(30) NOT NULL,
  `creator_name` varchar(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
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
  `begin_execute` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `end_execute` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `qian_fei` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `qian_fei_time` datetime DEFAULT NULL,
  `zl_charge_nubmer` int(5) DEFAULT 0,
  `jl_charge_number` int(5) DEFAULT 0,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `xs_contact_info` ADD INDEX contact_index_number(`contact_number`);
ALTER TABLE `xs_contact_info` ADD INDEX contact_index_jiafang_uuid(`jiafang_uuid`);
ALTER TABLE `xs_contact_info` ADD INDEX contact_index_yifang_uuid(`yifang_uuid`);

DROP TABLE IF EXISTS `xs_contact_file`;
CREATE TABLE `xs_contact_file` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  `upload_filename` varchar(100) default '',
  `actual_filename` varchar(100) default '',
  `contact_uuid` varchar(30) default NULL,
  PRIMARY KEY  (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES xs_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `xs_contact_log`;
CREATE TABLE `xs_contact_log` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  `description` varchar(100) default '',
  `contact_uuid` varchar(30) default NULL,
  PRIMARY KEY  (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES xs_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `xs_contact_vehicle_module`;
CREATE TABLE `xs_contact_vehicle_module` (
  `uuid` varchar(30) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `module_type` varchar(20) DEFAULT NULL,
  `module_brand` varchar(100) DEFAULT NULL,
  `module_color` varchar(128) DEFAULT NULL,
  `xiao_shou_type` varchar(20) DEFAULT 0,
  `ding_jin` decimal(11,2) DEFAULT NULL,
  `shou_fu` decimal(11,2) DEFAULT NULL,
  `saleprice` decimal(11,2) DEFAULT NULL,
  `wei_kuan` decimal(11,2) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  `sale_number` int(5) DEFAULT 0,
  `max_aj_year` int(5) DEFAULT 0,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES xs_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `xs_contact_execute`;
CREATE TABLE `xs_contact_execute` (
  `uuid` varchar(30) NOT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `module_type` varchar(20) DEFAULT NULL,
  `module_brand` varchar(100) DEFAULT NULL,
  `module_color` varchar(128) DEFAULT NULL,
  `vehicle_num` varchar(8) DEFAULT NULL,
  `tiche_pici` int(5) DEFAULT 0,
  `tiche_date` datetime DEFAULT NULL,
  `ding_jin` decimal(11,2) DEFAULT NULL,
  `shou_fu` decimal(11,2) DEFAULT NULL,
  `saleprice` decimal(11,2) DEFAULT NULL,
  `wei_kuan` decimal(11,2) DEFAULT NULL,
  `xiao_shou_type` varchar(20) DEFAULT 0,
  `execute_status` varchar(20) DEFAULT 0,
  `contact_uuid` varchar(30) DEFAULT NULL,
  `comment` text,
  `max_aj_year` int(5) DEFAULT 0,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES xs_contact_info (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `xs_contact_rentfee_his`;
CREATE TABLE `xs_contact_rentfee_his` (
  `uuid` varchar(30) NOT NULL,
  `vehicle_number` varchar(8) DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `jiaoyi_ren` varchar(100) DEFAULT 0,
  `jiaoyi_ren_uuid` varchar(30) DEFAULT 0,
  `tiche_pici` int(5) DEFAULT 0,
  `history_type` varchar(20) DEFAULT 0,
  `pay_status` varchar(20) DEFAULT 0,
  `actual_fee_date` datetime DEFAULT NULL,
  `actual_fee_date_end` datetime DEFAULT NULL,
  `happend_date` datetime DEFAULT NULL,
  `fee_total` decimal(11, 2) DEFAULT 0,
  `pay_order_number` varchar(48) DEFAULT 0,
  `pay_order_uuid` varchar(30) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES xs_contact_info (`uuid`),
  FOREIGN KEY (`pay_order_uuid`) REFERENCES yj_pay_order (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#补充协议部分

DROP TABLE IF EXISTS `xs_contact_supply`;
CREATE TABLE `xs_contact_supply` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `caozuo_ren` varchar(100) DEFAULT NULL,
  `caozuo_ren_uuid` varchar(30) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES `xs_contact_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `xs_contact_supply_content`;
CREATE TABLE `xs_contact_supply_content` (
  `uuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `supply_uuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`supply_uuid`) REFERENCES `xs_contact_supply` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `xs_contact_charging`;
CREATE TABLE `xs_contact_charging` (
  `uuid` varchar(30) NOT NULL,
  `c_status` varchar(20) DEFAULT NULL,
  `c_address` varchar(30) DEFAULT NULL,
  `contact_uuid` varchar(30) DEFAULT NULL,
  `c_create_time` datetime DEFAULT NULL,
  `c_finish_time` datetime DEFAULT NULL,
  `c_number` varchar(30) DEFAULT NULL,
  `c_types` varchar(20) DEFAULT NULL,
  `actor_man_uuid`  varchar(30) NOT NULL,
  `actor_man_name` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`contact_uuid`) REFERENCES `xs_contact_info` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#租赁合同相关的字段
ALTER TABLE `zp_contact_vehicle_module` ADD COLUMN `module_dianliang` varchar(20) DEFAULT NULL;
ALTER TABLE `zp_contact_execute` ADD COLUMN `module_dianliang` varchar(20) DEFAULT NULL;

update zp_contact_info set start_date = '2017-11-30 00:00:00' where contact_number = 'LKA201711050795';
update zp_contact_info set start_date = '2017-12-04 00:00:00' where contact_number = 'LKA201711711776';
update zp_contact_info set start_date = '2017-11-29 00:00:00' where contact_number = 'LKA201711419920';
update zp_contact_info set start_date = '2017-11-29 00:00:00' where contact_number = 'LKA201711636480';
update zp_contact_info set start_date = '2017-12-15 00:00:00' where contact_number = 'LKA201711296208';
update zp_contact_info set start_date = '2017-12-08 00:00:00' where contact_number = 'LKA201712628089';
update zp_contact_info set start_date = '2017-12-07 00:00:00' where contact_number = 'LKA201712839273';
update zp_contact_info set start_date = '2017-12-12 00:00:00' where contact_number = 'LKA201711772492';
update zp_contact_info set start_date = '2017-12-13 00:00:00' where contact_number = 'LKA201712748640';

update zp_contact_info set start_date = '2018-01-05 00:00:00' where contact_number = 'CDLKA201801528954';
update zp_contact_info set start_date = '2018-01-03 00:00:00' where contact_number = 'CDLKA201801029230';
update zp_contact_info set start_date = '2018-01-03 00:00:00' where contact_number = 'CDLKA201801004240';
update zp_contact_info set start_date = '2018-01-02 00:00:00' where contact_number = 'LKA201711092669';
update zp_contact_info set start_date = '2018-01-07 00:00:00' where contact_number = 'CDLKA201801414523';
update zp_contact_info set start_date = '2018-01-08 00:00:00' where contact_number = 'LKA201712385156';
update zp_contact_info set start_date = '2018-01-08 00:00:00' where contact_number = 'LKA201712452289';
update zp_contact_info set start_date = '2018-01-06 00:00:00' where contact_number = 'LKA201712919086';
update zp_contact_info set start_date = '2018-01-03 00:00:00' where contact_number = 'SZTTA201801727289';
update zp_contact_info set start_date = '2018-01-03 00:00:00' where contact_number = 'SZTTA201801328338';
update zp_contact_info set start_date = '2018-01-01 00:00:00' where contact_number = 'SZTTA201801205355';
update zp_contact_info set start_date = '2018-01-01 00:00:00' where contact_number = 'LKA201712664650';
update zp_contact_info set start_date = '2018-01-10 00:00:00' where contact_number = 'SZTTA201801763584';
update zp_contact_info set start_date = '2018-01-11 00:00:00' where contact_number = 'LKA201712144593';
update zp_contact_info set start_date = '2018-01-02 00:00:00' where contact_number = 'CDLKA201801042143';
update zp_contact_info set start_date = '2018-01-07 00:00:00' where contact_number = 'CDLKA201801002713';
update zp_contact_info set start_date = '2018-01-01 00:00:00' where contact_number = 'LKA201712225630';
update zp_contact_info set start_date = '2018-01-10 00:00:00' where contact_number = 'CDLKA201801643570';
update zp_contact_info set start_date = '2018-01-11 00:00:00' where contact_number = 'CDLKB201712071106';
update zp_contact_info set start_date = '2018-01-08 00:00:00' where contact_number = 'CDLKA201712911638';
update zp_contact_info set start_date = '2018-01-09 00:00:00' where contact_number = 'LKA201712808801';
update zp_contact_info set start_date = '2018-01-08 00:00:00' where contact_number = 'CDLKB201712154104';
update zp_contact_info set start_date = '2018-01-01 00:00:00' where contact_number = 'LKA201712879582';

select z.uuid, z.contact_number, z.start_date, p.tiche_date from zp_contact_info z right join zp_contact_execute p on z.uuid = p.contact_uuid where z.start_date != p.tiche_date;

SET FOREIGN_KEY_CHECKS=1;
