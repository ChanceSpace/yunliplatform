SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `zp_contact_info` ADD COLUMN `baoyue_type` varchar(20) DEFAULT 0;
ALTER TABLE `zp_contact_vehicle_module` ADD COLUMN `baoyue_type` varchar(20) DEFAULT 0;
ALTER TABLE `zp_contact_execute` ADD COLUMN `baoyue_type` varchar(20) DEFAULT 0;

SET FOREIGN_KEY_CHECKS=1;