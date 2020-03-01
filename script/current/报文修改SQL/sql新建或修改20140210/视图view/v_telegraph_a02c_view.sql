create or replace view v_telegraph_a02c_view as
select  a.msg_no,a.rptno,a.ac_id,h.date_utc,a.iata_c,a.flt_id,a.nmdps_code,a.rgs_code,a.record_time,a.errint,h.dmu
from a_acars_telegraph a,a_dfd_head h,a_dfd_a02cfm56_list t
where a.msg_no=h.msg_no and t.msg_no=h.msg_no;