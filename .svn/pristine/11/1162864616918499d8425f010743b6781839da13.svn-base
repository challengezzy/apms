--联合时预达时间计算错误
 CREATE OR REPLACE VIEW V_FLIGHTSCH_JOIN AS
SELECT --航前、航后、过站航班信息联合查询
       0 DATATYPE,FD.FLT_DATE,FD.FLT_DATE_STR,FD.IATA_C,FD.IATA_C_NAME,FD.CO_SEQ,FD.CO_SEQ_NAME
       ,FD.ACNUM,FD.ACMODEL,FD.ACMODELID,FD.DEP_APT APT,FD.FLTTYPE_DEP MAINTAINTYPE
       ,NULL AC_STATE_A,NULL AC_STATE_NAME_A,FD.AC_STATE AC_STATE_D,FD.AC_STATE_NAME AC_STATE_NAME_D
       --飞机状态判断
       ,case when fd.ac_state<20 then '待命' when fd.ac_state=20 then '关舱' when fd.ac_state=30 then '推出'
             when (fd.ac_state=any(40,50) ) then '空中' when fd.AC_STATE>=60 then '下站到港'
        end ACPORTSTATE --飞机在港状态
       ,FD.AC_STATE ACSTATEFILTER,FD.DATASOURCE,FD.ISDELAY,FD.FRAMETYPE --飞机状态过滤字段
       ,DUTY_USER,MAINTAIN_USER,RELEASE_USER,GUARDIAN_USER,HANDOVER_USER --相关人员
       ,FD.WORKFORCE_STATE,FD.WORKFORCE_STATE_NAME,FD.CANCEL_FLAG
       ,FD.AC_STOP,FD.ISCONFIRMED,FD.MEMO,FD.FLIGHTDESC
       ,FD.ISCONFIRMED_NAME,FD.ISLOCKEDIN,FD.ISLOCKEDIN_NAME
       ,NULL FLT_PK_A,FD.FLT_PK FLT_PK_D
       ,FD.FLIGHTNO FLIGHTNO_UNION,NULL FLIGHTNO_A,FD.FLIGHTNO FLIGHTNO_D
       ,FD.DEP_APT_NAME||'-'||FD.ARR_APT_NAME APT_UNION
       ,FD.T_STD STDA,FD.CTD TIMEFILTER --时间过滤字段
       ,FD.REMAIN_MIN_DEP, NULL REMAIN_MIN_ARR --剩余起飞、落地时间
       ,DECODE( SIGN((SELECT COUNT(1) C FROM W_DD_INFO D WHERE D.AC_ID=FD.ACNUM AND  finish_sign = any(0, 2)
       and ((TARGET_DATE is null) or (trunc(sysdate) <= TARGET_DATE and trunc(sysdate) > ISSUE_DATE)) )),1,'D',NULL) DD_FLAG
       ,DECODE( SIGN((SELECT COUNT(1) C FROM W_DD_INFO D WHERE D.AC_ID=FD.ACNUM AND D.FINISH_SIGN=0 AND M_FLAG='1' ))
               ,1,'M',NULL) M_FLAG
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=0 and t.flightid=fd.FLT_PK ))
               ,1, 'P', null) S_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=1 and t.flightid=fd.FLT_PK))
               ,1, 'T', null) J_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=2 and t.flightid=fd.FLT_PK))
               ,1, 'G', null) G_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=3 and t.flightid=fd.FLT_PK))
               ,1, 'C', null) C_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=4 and t.flightid=fd.FLT_PK))
                ,1, 'V', null) V_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=5 and t.flightid=fd.FLT_PK))
                ,1, 'L', null) L_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=3  AND T.STATUS=0 and t.flightid=fd.FLT_PK))
               ,1, 'UC', null) UC_TIP --未关闭的提醒确认单
       ,-( (SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FD.FLT_PK AND C.ISBOARDCAST=0 AND C.NEEDBOARDCAST=1
                   AND C.CHANGETYPE=ANY(200,10,20,40,50,300,400) )
        ) CHGALTERNUM --只关心的变动
        ,-((SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FD.FLT_PK AND C.ISBOARDCAST=0 )
        ) CHGLOGNUM
       ,decode((select count(1) from a_cfd_head ach,a_cfd_fault acf where ach.msg_no=acf.msg_no
            and FD.FLT_PK =ach.flightid ),0,'','是') ishavefault
       ,decode((select count(1) from a_cfd_head ach,a_cfd_warning acw where ach.msg_no=acw.msg_no
            and FD.FLT_PK =ach.flightid ),0,'','是') ishavewarning
       ,(select name from b_organization org,b_aircraft air where org.id=air.baseorgid and air.aircraftsn=FD.ACNUM) baseorg
       ,NULL STA,FD.STD,NULL ETA,FD.ETD,NULL CTA,FD.CTD,NULL T_ETA,FD.T_ETD
  FROM V_FLIGHTSCH_SHORT FD
 WHERE FD.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31') AND FD.FLTTYPE_DEP = 'PF' --航前
UNION ALL
SELECT 1 DATATYPE,FD.FLT_DATE,FD.FLT_DATE_STR,FD.IATA_C,FD.IATA_C_NAME,FD.CO_SEQ,FD.CO_SEQ_NAME
       ,FD.ACNUM,FD.ACMODEL,FD.ACMODELID,FD.DEP_APT APT,FD.FLTTYPE_DEP MAINTAINTYPE
       ,FA.AC_STATE AC_STATE_A,FA.AC_STATE_NAME AC_STATE_NAME_A,FD.AC_STATE AC_STATE_D,FD.AC_STATE_NAME AC_STATE_NAME_D
       --未起飞（0）、空中（1）、落地（2）、靠桥（3）、开舱（4） (进港航段),待命
       --关舱（5）、推出（6）、空中（7）、下站到港（8） （出发航段）
       ,case when fd.ac_state<20 then  (
            case when fa.ac_state<40 then '上站未起' when fa.ac_state=any(40,50) then '上站空中'
                 when fa.ac_state=60 then '落地' when fa.ac_state=70 then '靠桥' when fa.AC_STATE>=80 then '开舱'
             end ) --上一段判断结束
         when fd.ac_state=20 then '关舱' when fd.ac_state=30 then '推出'
         when fd.ac_state=any(40,50) then '下站空中' when fd.AC_STATE>=60 then '下站到港'
        end ACPORTSTATE --飞机在港状态
       ,FD.AC_STATE ACSTATEFILTER,FD.DATASOURCE,FD.ISDELAY,FD.FRAMETYPE --飞机状态过滤字段
       ,FD.DUTY_USER,FD.MAINTAIN_USER,FD.RELEASE_USER,FD.GUARDIAN_USER,FD.HANDOVER_USER --相关人员
       ,FD.WORKFORCE_STATE,FD.WORKFORCE_STATE_NAME,FD.CANCEL_FLAG
       ,FD.AC_STOP,FD.ISCONFIRMED,FA.MEMO||' / '||FD.MEMO,FD.FLIGHTDESC
       ,FD.ISCONFIRMED_NAME,FD.ISLOCKEDIN,FD.ISLOCKEDIN_NAME
       ,FA.FLT_PK FLT_PK_A,FD.FLT_PK FLT_PK_D
       ,FA.FLIGHTNO||'/'||FD.FLIGHTNO FLIGHTNO_UNION,FA.FLIGHTNO FLIGHTNO_A,FD.FLIGHTNO FLIGHTNO_D
       ,FA.DEP_APT_NAME||'-'||FD.DEP_APT_NAME||'-'||FD.ARR_APT_NAME APT_UNION
       ,TO_CHAR(FA.STA,'HH24:MI')||DECODE(SIGN(TRUNC(FA.STA)-FD.FLT_DATE),-1,' -'||(FD.FLT_DATE-TRUNC(FA.STA)),'' )||' / '||FD.T_STD STDA --到达航班做-1处理
       ,FD.CTD TIMEFILTER --时间过滤字段
       ,FD.REMAIN_MIN_DEP, FA.REMAIN_MIN_ARR REMAIN_MIN_ARR --剩余起飞、落地时间
       ,DECODE( SIGN((SELECT COUNT(1) C FROM W_DD_INFO D WHERE D.AC_ID=FD.ACNUM AND finish_sign = any(0, 2)
       and ((TARGET_DATE is null) or (trunc(sysdate) <= TARGET_DATE and trunc(sysdate) > ISSUE_DATE)) )),1,'D',NULL) DD_FLAG
       ,DECODE( SIGN((SELECT COUNT(1) C FROM W_DD_INFO D WHERE D.AC_ID=FD.ACNUM AND D.FINISH_SIGN=0 AND M_FLAG='1' ))
               ,1,'M',NULL) M_FLAG
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=0 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
               ,1, 'P', null) S_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=1 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
               ,1, 'T', null) J_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=2 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
               ,1, 'G', null) G_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=3 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
               ,1, 'C', null) C_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=4 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
               ,1, 'V', null) V_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=5 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
                ,1, 'L', null) L_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=3  AND T.STATUS=0 and t.flightid=ANY(FA.FLT_PK,FD.FLT_PK) ))
               ,1, 'UC', null) UC_TIP --未关闭的提醒确认单
       ,-( (SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FA.FLT_PK AND C.ISBOARDCAST=0 AND C.NEEDBOARDCAST=1
                   AND C.CHANGETYPE=ANY(200,10,25,30,45,55,300,450) )
          +(SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FD.FLT_PK AND C.ISBOARDCAST=0 AND C.NEEDBOARDCAST=1
                   AND C.CHANGETYPE=ANY(200,10,20,40,50,300,400) )
        ) CHGALTERNUM --只关心的变动
        ,-( (SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FA.FLT_PK AND C.ISBOARDCAST=0 )
          +(SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FD.FLT_PK AND C.ISBOARDCAST=0 )
        ) CHGLOGNUM
       ,decode((select count(1) from a_cfd_head ach,a_cfd_fault acf where ach.msg_no=acf.msg_no
            and (FD.FLT_PK=ach.flightid or FA.FLT_PK=ach.flightid) ),0,'','是') ishavefault
       ,decode((select count(1) from a_cfd_head ach,a_cfd_warning acw where ach.msg_no=acw.msg_no
       and (FD.FLT_PK=ach.flightid or FA.FLT_PK=ach.flightid) ),0,'','是') ishavewarning
       ,(select name from b_organization org,b_aircraft air where org.id=air.baseorgid and air.aircraftsn=FD.ACNUM) baseorg
       ,FA.STA,FD.STD,FA.ETA,FD.ETD,FA.CTA,FD.CTD
       ,TO_CHAR(FA.ETA,'HH24:MI')||DECODE(SIGN(TRUNC(FA.ETA)-FD.FLT_DATE),-1,'-1','') T_ETA,FD.T_ETD
  FROM V_FLIGHTSCH_SHORT FD,V_FLIGHTSCH_SHORT FA
 WHERE FD.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31') AND FD.FLTTYPE_DEP =ANY ('TR','AF+PF') --过站
    AND FA.ACNUM=FD.ACNUM AND FA.ARR_APT=FD.DEP_APT
    AND  FA.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31')
   AND FA.CTD =(SELECT MAX(FT.CTD) FROM F_FLIGHT_SCHEDULE FT
                   WHERE FT.ACNUM=FD.ACNUM AND FT.FLT_DATE<=FD.FLT_DATE AND FT.FLT_DATE>=FD.FLT_DATE-3
                   And FT.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31')
                    AND FT.ARR_APT=FD.DEP_APT AND FT.CTD<FD.CTD )

UNION ALL --航后
SELECT 2 DATATYPE,FA.FLT_DATE,FA.FLT_DATE_STR,FA.IATA_C,FA.IATA_C_NAME,FA.CO_SEQ,FA.CO_SEQ_NAME
       ,FA.ACNUM,FA.ACMODEL,FA.ACMODELID,FA.ARR_APT APT,FA.FLTTYPE_ARR MAINTAINTYPE
       ,FA.AC_STATE AC_STATE_A,FA.AC_STATE_NAME AC_STATE_NAME_A,NULL AC_STATE_D,NULL AC_STATE_NAME_D
       --飞机状态判断
       ,case when fa.ac_state<40 then '未起飞' when fa.ac_state=any(40,50) then '空中' when fa.ac_state=60 then '落地'
             when fa.ac_state=70 then '靠桥' when fa.AC_STATE>=80 then '开舱'
        end ACPORTSTATE
       ,FA.AC_STATE ACSTATEFILTER,FA.DATASOURCE,FA.ISDELAY,FA.FRAMETYPE
       ,DUTY_USER,MAINTAIN_USER,RELEASE_USER,GUARDIAN_USER,HANDOVER_USER
       ,FA.WORKFORCE_STATE,FA.WORKFORCE_STATE_NAME,FA.CANCEL_FLAG
       ,FA.AC_STOP_ARR AC_STOP,FA.ISCONFIRMED,FA.MEMO,FA.FLIGHTDESC
       ,FA.ISCONFIRMED_NAME,FA.ISLOCKEDIN,FA.ISLOCKEDIN_NAME
       ,FA.FLT_PK FLT_PK_A,null FLT_PK_D
       ,FA.FLIGHTNO FLIGHTNO_UNION,FA.FLIGHTNO FLIGHTNO_A,NULL FLIGHTNO_D
       ,FA.DEP_APT_NAME||'-'||FA.ARR_APT_NAME APT_UNION
       ,FA.T_STA STDA,FA.CTA TIMEFILTER --时间过滤字段
       ,NULL REMAIN_MIN_DEP, FA.REMAIN_MIN_ARR REMAIN_MIN_ARR --剩余起飞、落地时间
       ,DECODE( SIGN((SELECT COUNT(1) C FROM W_DD_INFO D WHERE D.AC_ID=FA.ACNUM AND finish_sign = any(0, 2)
       and ((TARGET_DATE is null) or (trunc(sysdate) <= TARGET_DATE and trunc(sysdate) > ISSUE_DATE)) )),1,'D',NULL) DD_FLAG
       ,DECODE( SIGN((SELECT COUNT(1) C FROM W_DD_INFO D WHERE D.AC_ID=FA.ACNUM AND D.FINISH_SIGN=0 AND M_FLAG='1' ))
               ,1,'M',NULL) M_FLAG
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=0 and t.flightid=fa.FLT_PK ))
               ,1, 'P', null) S_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=1 and t.flightid=fa.FLT_PK ))
               ,1, 'T', null) J_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=2 and t.flightid=fa.FLT_PK ))
               ,1, 'G', null) G_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=3 and t.flightid=fa.FLT_PK ))
               ,1, 'C', null) C_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=4 and t.flightid=fa.FLT_PK ))
               ,1, 'V', null) V_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=5 and t.flightid=fa.FLT_PK ))
                ,1, 'L', null) L_TIP
       ,DECODE( SIGN((SELECT COUNT(1) C FROM v_remindins_unconfirm T WHERE T.TIPTYPE=3 AND T.STATUS=0 and t.flightid=fa.FLT_PK))
               ,1, 'UC', null) UC_TIP --未关闭的提醒确认单
       ,-((SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FA.FLT_PK AND C.ISBOARDCAST=0 AND C.NEEDBOARDCAST=1
                   AND C.CHANGETYPE=ANY(200,10,25,30,45,55,300,450) )
        ) CHGALTERNUM --只关心的变动
        ,-( (SELECT COUNT(1) FROM F_FLIGHT_CHANGELOG C WHERE C.FLIGHTID=FA.FLT_PK AND C.ISBOARDCAST=0 )
        ) CHGLOGNUM
       ,decode((select count(1) from a_cfd_head ach,a_cfd_fault acf where ach.msg_no=acf.msg_no
            and FA.FLT_PK=ach.flightid ),0,'','是') ishavefault
       ,decode((select count(1) from a_cfd_head ach,a_cfd_warning acw where ach.msg_no=acw.msg_no
            and FA.FLT_PK=ach.flightid ),0,'','是') ishavewarning
       ,(select name from b_organization org,b_aircraft air where org.id=air.baseorgid and air.aircraftsn=FA.ACNUM) baseorg
       ,FA.STA,NULL,FA.ETA,NULL,FA.CTA,NULL,FA.T_ETA,NULL T_ETD
  FROM V_FLIGHTSCH_SHORT FA
 WHERE FA.CANCEL_FLAG=ANY('0','3','4','5','6','7','8','A','B','C','31') AND FA.FLTTYPE_ARR = 'AF';
 