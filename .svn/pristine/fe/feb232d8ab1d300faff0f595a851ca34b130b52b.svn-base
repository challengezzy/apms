
alter table A_DFD_A14_LIST_DATA drop column NA;

alter table A_DFD_A14_LIST_DATA add(NA number(8,3));

create or replace view v_a_dfd_a14_list_data as 
select id, msg_no, acnum, row_num, row_title, egta, wf, ota, igv, wb, lcit, pt, p2a, lcot, scv, hot, gla, recdatetime, 
DECODE(na,NULL,'',CONCAT(TO_CHAR(na*100,'990.9'),'%')) na_percent from a_dfd_a14_list_data;