SET FOREIGN_KEY_CHECKS=0;

#执行工作相关---------------------------------

DROP TABLE IF EXISTS `yj_remote_job_2018`;
CREATE TABLE `yj_remote_job_2018` (
  `uuid` varchar(30) NOT NULL,
  `job_id` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `job_status` varchar(20) DEFAULT NULL,
  `request_host` varchar(100) DEFAULT NULL,
  `request_method` varchar(10) DEFAULT NULL,
  `request_body` varchar(4096) DEFAULT NULL,
  `response_body` text,
  `try_times` int(2) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
ALTER TABLE `yj_remote_job_2018` ADD INDEX remote_job_try_times(`try_times`);
ALTER TABLE `yj_remote_job_2018` ADD INDEX remote_job_status(`job_status`);
ALTER TABLE `yj_remote_job_2018` ADD INDEX remote_job_create_time(`create_time`);

SET FOREIGN_KEY_CHECKS=1;