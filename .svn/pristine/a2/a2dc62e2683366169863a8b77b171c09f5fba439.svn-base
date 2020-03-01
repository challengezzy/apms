create or replace view v_dd_info_app as
select dd_no,--未关闭DD单查询
       AC_ID,
       eng_sn,
       '未关闭' finish_sign,
       decode(m_flag, 1, 'M', null) m_flag,
       m_desc,
       target_date,
       defect_rpt,
       rect
  from w_dd_info
 where FINISH_SIGN = 0;
 

 
 create or replace view v_device_workladder_app as
select a.name, --工作梯查询视图
       air.code_3 code_3,
       (select ap.linkcode
          from b_airport_position ap
         where ap.id = a.positionid) airposition,
       (select bd.valuecn
          from b_dictionary bd
         where bd.classname = 'AIRPORT_DEVICE'
           AND bd.attributename = 'TYPE'
           and bd.value = a.type) type,
       a.positiondesc,
       (select bd.valuecn
          from b_dictionary bd
         where bd.classname = 'AIRPORT_DEVICE'
           AND bd.attributename = 'UPSTATE'
           and bd.value = a.upstate) upstate,
       b.platform_height,
       b.heightdesc,
       b.platform_area,
       (select bd.valuecn
          from b_dictionary bd
         where bd.classname = 'GLOBAL'
           AND bd.attributename = 'BOOLEAN'
           and bd.value = b.isguardbar) isguardbar,
       a.comments
  from b_airport_device a, b_workladder b, b_airport air
 where a.id = b.id
   and air.id = a.airportid;
 
 
create or replace view v_aircraft_app as
select id, --app中飞机信息查询
       (select name from b_organization org where org.id = air.baseorgid) baseorg,
       (select name from b_airline t where t.id = air.airlineid) airline,
       aircraftsn,
       (select modelcode from B_AIRCRAFT_MODEL am where am.id = acmodelid) acmodel,
       (select t.model from b_engine_model t where t.id=air.engine_mode ) engine_mode,
       decode(air.stats, 0, '待用', 1, '启用', 2, '停用', null) status,
       msn
  from b_aircraft air;
 
 
 create or replace view v_acstop_tip as
select pt.id --机位提醒信息
       ,pt.ap_positionid
       ,pt.type
       ,(select d.valuecn from b_dictionary d where d.classname='AIRPORT_POSITIONTIP'
                AND d.attributename='TYPE' and d.value=pt.type ) type_name
       ,pt.tipinfo
       ,p.airportid,p.terminalno,p.code
       ,p.isbridge,decode(p.isbridge,1,'靠桥','不靠桥') isbridge_name
       ,p.linkcode,p.positiondesc
from b_airport_positiontip pt,b_airport_position p
where p.id=pt.ap_positionid;