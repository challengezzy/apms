CREATE OR REPLACE VIEW V_FLIGHTSCH_STAT AS
SELECT --航班计划视图，做数据统计使用
       F.ID,FLT_PK,
       FLT_DATE,TO_CHAR(FLT_DATE,'YYYY-MM-DD') FLT_DATE_STR,
       IATA_C,(SELECT SHORTNAME FROM B_AIRLINE T WHERE T.IATA_CODE=IATA_C ) IATA_C_NAME,
       CO_SEQ,(SELECT SHORTNAME FROM B_BRANCH_CODE T WHERE T.SEQ=CO_SEQ ) CO_SEQ_NAME,
       FLIGHTNO,FLT_ID,FLT_SEQ,FLT_TASK,
       ACNUM,ACMODEL,DEP_APT,ARR_APT,AC_STOP,AC_STOP_ARR,
       A.BASEORGID,A.ACMODELID,
       M.MODELCODE,M.MODELSERIES,M.MODELCAPACITY,M.AIRFRAMETYPE, --机型相关
       (SELECT SHORTNAME FROM B_AIRPORT WHERE CODE_3=DEP_APT) DEP_APT_NAME,
       (SELECT SHORTNAME FROM B_AIRPORT WHERE CODE_3=ARR_APT) ARR_APT_NAME,
       DUTY_USER,
       MAINTAIN_USER,
       RELEASE_USER,
       GUARDIAN_USER,
       HANDOVER_USER,
       OFF_TIME,
       ON_TIME,
       OUT_TIME,
       IN_TIME,
       CTD,CTA,
       MEMO,
       AC_STATE,
       (select d.valuecn from b_dictionary d where d.classname='FLIGHTSCHEDULE' AND d.attributename='ACSTATE'
           AND D.VALUE=AC_STATE) AC_STATE_NAME,
       WORKFORCE_STATE,
       (select d.valuecn from b_dictionary d where d.classname='FLIGHTSCHEDULE' AND d.attributename='WORKFORCESTATE'
           AND D.VALUE=WORKFORCE_STATE) WORKFORCE_STATE_NAME,
       FLTTYPE_DEP,
       FLTTYPE_ARR,
       ISCONFIRMED,
       DECODE(ISCONFIRMED,0,'否',1,'是' ) ISCONFIRMED_NAME,
       ISLOCKEDIN,
       DECODE(ISLOCKEDIN,0,'否',1,'是' ) ISLOCKEDIN_NAME,
       PRE_FLIGHTID,
       NEXT_FLIGHTID,
       NEXT_FLIGHTNO,
       UNUSAULFLT_RPTID,
       ISDELAY,
       RTRIM(TRANSLATE(DELAY_CODE,'*',' ')) DELAY_CODE,
       DELAY_TIME,
       CANCEL_FLAG,
       CANCEL_TYPE,
       CANCEL_TIME,
       CANCEL_REASON,
       VIP,
       VIP_NAME,
       PLAN_FLAG,
       FLIGHTDESC,
       DATASOURCE
  FROM F_FLIGHT_SCHEDULE F,B_AIRCRAFT A,B_AIRCRAFT_MODEL M
 WHERE A.AIRCRAFTSN=F.ACNUM AND M.ID=A.ACMODELID;
 
 
 CREATE OR REPLACE VIEW V_FLIGHTSCH_JOIN_STAT AS 
SELECT --航前、航后信息联合查询,做和工作分配等相关的数据统计用，不连接TR航班
       0 DATATYPE,FD.FLT_DATE,FD.FLT_DATE_STR,FD.IATA_C,FD.IATA_C_NAME,FD.CO_SEQ,FD.CO_SEQ_NAME
       ,FD.ACNUM,FD.ACMODEL,FD.ACMODELID,FD.DEP_APT APT,FD.FLTTYPE_DEP MAINTAINTYPE
       ,NULL AC_STATE_A,NULL AC_STATE_NAME_A,FD.AC_STATE AC_STATE_D,FD.AC_STATE_NAME AC_STATE_NAME_D
       ,FD.AC_STATE ACSTATEFILTER,FD.DATASOURCE,FD.ISDELAY,FD.AIRFRAMETYPE --飞机状态过滤字段
       ,DUTY_USER,MAINTAIN_USER,RELEASE_USER,GUARDIAN_USER,HANDOVER_USER --相关人员
       ,FD.WORKFORCE_STATE,FD.WORKFORCE_STATE_NAME,FD.CANCEL_FLAG
       ,FD.AC_STOP,FD.ISCONFIRMED,FD.MEMO,FD.FLIGHTDESC
       ,FD.ISCONFIRMED_NAME,FD.ISLOCKEDIN,FD.ISLOCKEDIN_NAME
       ,NULL FLT_PK_A,FD.FLT_PK FLT_PK_D
       ,FD.FLIGHTNO
  FROM V_FLIGHTSCH_STAT FD
 WHERE FD.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31') 
   AND FD.FLTTYPE_DEP= ANY('PF','TR','AF+PF')--航前,过站
UNION ALL --航后
SELECT 2 DATATYPE,FA.FLT_DATE,FA.FLT_DATE_STR,FA.IATA_C,FA.IATA_C_NAME,FA.CO_SEQ,FA.CO_SEQ_NAME
       ,FA.ACNUM,FA.ACMODEL,FA.ACMODELID,FA.ARR_APT APT,FA.FLTTYPE_ARR MAINTAINTYPE
       ,FA.AC_STATE AC_STATE_A,FA.AC_STATE_NAME AC_STATE_NAME_A,NULL AC_STATE_D,NULL AC_STATE_NAME_D
       ,FA.AC_STATE ACSTATEFILTER,FA.DATASOURCE,FA.ISDELAY,FA.AIRFRAMETYPE
       ,DUTY_USER,MAINTAIN_USER,RELEASE_USER,GUARDIAN_USER,HANDOVER_USER
       ,FA.WORKFORCE_STATE,FA.WORKFORCE_STATE_NAME,FA.CANCEL_FLAG
       ,FA.AC_STOP_ARR AC_STOP,FA.ISCONFIRMED,FA.MEMO,FA.FLIGHTDESC
       ,FA.ISCONFIRMED_NAME,FA.ISLOCKEDIN,FA.ISLOCKEDIN_NAME
       ,FA.FLT_PK FLT_PK_A,null FLT_PK_D
       ,FA.FLIGHTNO
  FROM V_FLIGHTSCH_STAT FA
 WHERE FA.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31') 
   AND FA.FLTTYPE_ARR = 'AF';