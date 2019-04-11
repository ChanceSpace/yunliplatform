alter table zp_contact_info modify `yifang_contact_name` varchar(30) NOT NULL;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `yj_carrier_info`;
DROP TABLE IF EXISTS `yj_carrier_poster`;
DROP TABLE IF EXISTS `yj_carrier_vehicle`;
DROP TABLE IF EXISTS `yj_order_sale`;
DROP TABLE IF EXISTS `yj_order_sale_log`;
DROP TABLE IF EXISTS `yj_order_service`;
DROP TABLE IF EXISTS `yj_order_service_log`;
DROP TABLE IF EXISTS `yj_post_carrier`;
DROP TABLE IF EXISTS `yj_post_template`;
DROP TABLE IF EXISTS `yj_poster_carrier`;
DROP TABLE IF EXISTS `yj_poster_carrier_picture`;
DROP TABLE IF EXISTS `yj_poster_template`;
DROP TABLE IF EXISTS `yj_user_account`;
DROP TABLE IF EXISTS `yj_user_info`;
DROP TABLE IF EXISTS `yj_user_role_link`;

SET FOREIGN_KEY_CHECKS=1;