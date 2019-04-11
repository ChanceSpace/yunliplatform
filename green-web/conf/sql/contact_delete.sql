删除提车
delete from zp_contact_execute_finished where contact_uuid='20171227_1433_2510222225793142';
delete from zp_contact_execute_change where contact_uuid='20171227_1433_2510222225793142';
delete from zp_contact_rentfee_his where contact_uuid='20171227_1433_2510222225793142';
delete from zp_contact_execute where contact_uuid='20171227_1433_2510222225793142';

//删除合同
delete from zp_contact_vehicle_module where contact_uuid='20171201_1722_5391410361790201';
delete from zp_contact_file where contact_uuid='20171201_1722_5391410361790201';
delete from zp_contact_log where contact_uuid='20180109_1522_2095921436254301';
delete from zp_contact_info where uuid='20171201_1722_5391410361790201';

//还原车辆
update yj_carrier_vehicle set user_uuid = owner_uuid,v_status = 'S_BEIYONG',contact_number = '',happen_date = NULL where v_license_num like '%A61R3T';

//撤销执行
update zp_contact_info set contact_status = 'S_CREATED' where contact_number ='LKA201712689011';