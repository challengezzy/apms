CREATE OR REPLACE VIEW V_FLT_CHANGELOG_MONITOR AS
SELECT --航班动态监控视图
       C.ID,
       C.FLIGHTID,
       C.FLIGHTNO,
       S.ACNUM,
       S.ACMODEL,
       S.DEP_APT,
       S.ARR_APT,
       S.CTD,
       S.CTA,
       C.BOARDCASTTIME,
        TO_CHAR(C.BOARDCASTTIME,'YYYY-MM-DD HH24:MI:SS') BOARDCASTTIME_STR,
       C.FLIGHTDATE,
       TO_CHAR(C.FLIGHTDATE,'MM-DD') FLIGHTDATE_SHORT,
       (select d.valuecn from b_dictionary d where d.classname='FLIGHTSCHEDULE'
               AND d.attributename='CHANGETYPE' and d.value=c.changetype) changetype_name,
       C.CHANGETYPE,
       C.CHANGETIME,
       TO_CHAR(C.CHANGETIME,'MM-DD HH24:MI') CHANGETIME_STR,
       C.NEEDBOARDCAST,
       C.ISBOARDCAST,
       DECODE(C.NEEDBOARDCAST,0,'否',1,'是') NEEDBOARDCAST_NAME,
       DECODE(C.ISBOARDCAST,0,'否',1,'是') ISBOARDCAST_NAME,
       C.CHANGECONTENT,
       C.COMMENTS,
       C.RECIPIENT,
       S.MEMO,S.CANCEL_FLAG,S.CANCEL_TYPE,S.CANCEL_TIME,S.CANCEL_REASON,S.CANCEL_SRC,--取消相关
       C.UPDATETIME,
       C.UPDATEUSER
  FROM F_FLIGHT_CHANGELOG C,F_FLIGHT_SCHEDULE S
  WHERE S.FLT_PK=C.FLIGHTID;
