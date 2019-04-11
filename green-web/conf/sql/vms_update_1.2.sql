alter table yj_user_info modify `u_mobile` varchar(15) NOT NULL;
alter table yj_user_account modify `u_mobile` varchar(15) NOT NULL;
ALTER TABLE `zp_contact_rentfee_his` ADD COLUMN `actual_fee_date_end` datetime DEFAULT NULL;