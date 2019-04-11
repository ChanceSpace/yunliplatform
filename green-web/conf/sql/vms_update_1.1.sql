SET FOREIGN_KEY_CHECKS=0;

#订单部分
DROP TABLE IF EXISTS `yj_pay_order`;
CREATE TABLE `yj_pay_order` (
  `uuid` varchar(30) NOT NULL,
  `o_source` varchar(48) default 'O_CONTACT',
  `o_dingdan_number` varchar(26) default NULL,
  `o_jiaoyi_number` varchar(48) default NULL,
  `o_belone_name` varchar(120) default '',
  `o_belone_uuid` varchar(30) default '',
  `o_chengyun_name` varchar(120) default '',
  `o_chengyun_uuid` varchar(30) default '',
  `o_create_time` datetime default NULL,
  `o_finish_time` datetime default NULL,
  `o_status` varchar(20) default 'O_CREATE',
  `o_pay_way` varchar(20) default 'O_OFFLINE',
  `o_actor_name` varchar(48) default NULL,
  `o_actor_uuid` varchar(30) default NULL,
  `o_total_fee` decimal(11, 2) DEFAULT 0,
  `o_total_fee_belone` decimal(11, 2) DEFAULT 0,
  `o_total_fee_chengyun` decimal(11, 2) DEFAULT 0,
  `o_note` text,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `yj_pay_order` ADD INDEX pay_order_index_dingdan(`o_dingdan_number`);
ALTER TABLE `yj_pay_order` ADD INDEX pay_order_index_jiaoyi(`o_jiaoyi_number`);
ALTER TABLE `yj_pay_order` ADD INDEX pay_order_index_cy_name(`o_chengyun_name`);
ALTER TABLE `yj_pay_order` ADD INDEX pay_order_index_cy_uuid(`o_chengyun_uuid`);

DROP TABLE IF EXISTS `yj_pay_order_file`;
CREATE TABLE `yj_pay_order_file` (
  `uuid` varchar(30) NOT NULL,
  `timestamp` datetime DEFAULT NULL,
  `actor_man_uuid` varchar(30) DEFAULT NULL,
  `actor_man_name` varchar(20) DEFAULT NULL,
  `upload_filename` varchar(100) DEFAULT NULL,
  `actual_filename` varchar(100) DEFAULT NULL,
  `pay_order_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`pay_order_uuid`) REFERENCES `yj_pay_order` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#其他部分
ALTER TABLE `yj_user_info` ADD COLUMN `u_current_balance` decimal(11, 2) DEFAULT 0;
ALTER TABLE `yj_user_info` ADD COLUMN `u_qian_fei_begin_time` datetime DEFAULT NULL;
ALTER TABLE `yj_user_info` ADD COLUMN `u_qian_fei_balance` decimal(11, 2) DEFAULT 0;

UPDATE yj_user_info set u_current_balance = 0;
ALTER TABLE `zp_contact_rentfee_his` ADD COLUMN `pay_status` varchar(20) DEFAULT NULL;
ALTER TABLE `zp_contact_rentfee_his` ADD COLUMN `pay_order_number`  varchar(48) DEFAULT NULL;
ALTER TABLE `zp_contact_rentfee_his` ADD COLUMN `pay_order_uuid` varchar(30) DEFAULT NULL;
ALTER TABLE `zp_contact_rentfee_his` ADD FOREIGN KEY (`pay_order_uuid`) REFERENCES `yj_pay_order` (`uuid`);

#承运商账户管理部分

DROP TABLE IF EXISTS `yj_user_rentfee_his`;
CREATE TABLE `yj_user_rentfee_his` (
  `uuid` varchar(30)  NOT NULL,
  `happen_date` datetime DEFAULT NULL,
  `fee_total` decimal(11,2) DEFAULT 0,
  `pay_order_uuid` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  FOREIGN KEY (`pay_order_uuid`) REFERENCES `yj_pay_order` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS=1;
