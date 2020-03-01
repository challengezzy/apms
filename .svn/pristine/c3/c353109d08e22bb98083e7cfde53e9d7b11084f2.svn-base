CREATE OR REPLACE PROCEDURE P_A21_LIMIT_STATISTIC_1(IN_MSG_NO IN NUMBER,VAR_COUNT OUT NUMBER) IS
vtmp NUMBER := 0;
--VAR_COUNT  NUMBER := 0;
VAR_COUNT_LIMIT NUMBER := 0;
VAR_COUNT_DIV NUMBER := 0;
--左边超限
IS_PF1 NUMBER := 0 ;
IS_COT1 NUMBER := 0 ;
IS_RI1 NUMBER := 0 ;
IS_PBV1 NUMBER := 0 ;
IS_TW1 NUMBER := 0 ;
IS_TP1 NUMBER := 0 ;
IS_D11 NUMBER := 0 ;
IS_PRV1 NUMBER := 0 ;
IS_PD1 NUMBER := 0 ;
--右边超限
IS_PF2 NUMBER := 0 ;
IS_COT2 NUMBER := 0 ;
IS_RI2 NUMBER := 0 ;
IS_PBV2 NUMBER := 0 ;
IS_TW2 NUMBER := 0 ;
IS_TP2 NUMBER := 0 ;
IS_D12 NUMBER := 0 ;
IS_PRV2 NUMBER := 0 ;
IS_PD2 NUMBER := 0 ;

--混合总管温度
IS_MIXCAB NUMBER := 0;
IS_DIV_CK NUMBER := 0;
IS_DIV_FWD NUMBER := 0;
IS_DIV_AFT NUMBER := 0;

--PACK1#和PACK2# 差值
IS_PF_DIV NUMBER := 0 ;
IS_COT_DIV NUMBER := 0 ;
IS_RI_DIV NUMBER := 0 ;
IS_PBV_DIV NUMBER := 0 ;
IS_TW_DIV NUMBER := 0 ;
IS_TP_DIV NUMBER := 0 ;
IS_D1_DIV NUMBER := 0 ;
IS_PRV_DIV NUMBER := 0 ;
IS_PD_DIV NUMBER := 0 ;
IS_D1_TW1 NUMBER := 0;
IS_D1_TW2 NUMBER := 0;
BEGIN
  /**
    AUTHOR:ZHANGZY
    DATE:2014/12/12
    DESCRIPTION: 计算A21左右数据差值有多少个点超限
  */
  --遍历每个数据
  --pf,cot,ri,pbv,tw,tp,d1,pin_prv, pd, d1_tw1, d1_tw2
  FOR CC IN (SELECT AC.BASEORGID,AM.MODELCODE,AM.MODELSERIES,L.MSG_NO,L.ACNUM,L.RPTDATE
                    ,L.AP_2,L.Y1_2,L.LIM_2
                    ,L.N1_E1,L.N2_E1,L.PF_E1,L.COT_E1,L.RI_E1,L.RO_E1,L.PBV_E1,L.FCV_E1
                    ,L.N1_N1,L.N2_N1,L.PF_N1,L.COT_N1,L.RI_N1,L.RO_N1,L.PBV_N1,L.FCV_N1
                    ,L.P3_S1,L.T3_S1,L.TW_S1,L.TP_S1,L.TPO_S1,L.PD_S1,L.ALT_S1,L.PS_S1
                    ,L.P3_T1,L.T3_T1,L.TW_T1,L.TP_T1,L.TPO_T1,L.PD_T1,L.ALT_T1,L.PS_T1
                    ,L.TAT_V1,L.SAT_V1,L.ZCB_V1,L.ZLD_V1,L.SC1_V1,L.SC2_V1,L.SC3_V1,L.RV_V1
                    ,L.PCSW_X1,L.VSCB_X1,L.PDC_X1,L.VF_X1,L.VW_X1,L.VA_X1,L.OVP_X1,L.CPC_X1
                    ,L.PB_WAI_W1,L.PB_PRV1_W1,L.PB_PRV2_W1,L.SW_XFR_W1
                    ,L.PIN_HPV_M1,L.PIN_PRV_M1,L.OPV1_M1,L.PIN_HPV_M2,L.PIN_PRV_M2,L.OPV2_M1
                    ,L.FAV1_Z1,L.FAV2_Z1,L.HPV1_Z1,L.HPV2_Z1,L.PRV1_Z1,L.PRV2_Z1
                    ,L.FAV1_R1,L.FAV2_R1,L.HPV1_R1,L.HPV2_R1,L.PRV1_R1,L.PRV2_R1
                    ,L.PDMT_L_D1,L.PDMT_R_D1,L.CKT_F1,L.FWDT_F1,L.AFTT_F1,L.HOTAIRPB_F1
                    ,L.CKDUCT_G1,L.FWDUCT_G1,L.AFTDUCT_G1,L.MIXF_G1,L.MIXCAB_G1
                    ,L.TAPRV_H1,L.TAV_H1,L.MAINCTL_H1,L.SECDCTL_H1 
                FROM A_DFD_A21_LIST L,B_AIRCRAFT AC,B_AIRCRAFT_MODEL AM
               WHERE L.ACNUM= AC.AIRCRAFTSN AND AM.ID=AC.ACMODELID --AND AC.BASEORGID=VAR_BASEORGID
                AND L.MSG_NO = IN_MSG_NO --123329476
                AND NOT EXISTS (SELECT 1 FROM A_DFD_A21_COMPUTE_STAT ST WHERE ST.MSG_NO=L.MSG_NO)
              )
  LOOP
   
    IS_PF1  := 0;
    IS_COT1 := 0;
    IS_RI1  := 0;
    IS_PBV1 := 0;
    IS_TW1  := 0;
    IS_TP1  := 0;
    IS_D11  := 0;
    IS_PRV1 := 0;
    IS_PD1  := 0;
    IS_PF2  := 0;
    IS_COT2 := 0;
    IS_RI2  := 0;
    IS_PBV2 := 0;
    IS_TW2  := 0;
    IS_TP2 := 0;
    IS_D12  := 0;
    IS_PRV2 := 0;
    IS_PD2  := 0;
    -- IF( CC. >  OR CC. <  ) THEN IS_ := 1;  END IF;
    --区分机型判断参数, PACK1# BEGIN
    IF( CC.MODELSERIES = '321' ) THEN 
        IF( CC.PF_E1 > 0.55 OR CC.PF_E1 < 0.3) THEN IS_PF1 := 1;  END IF;
        IF( CC.COT_E1 > 190  OR CC.COT_E1 < 90 ) THEN IS_COT1 := 1;  END IF;
        IF( CC.PBV_E1 > 50 OR CC.PBV_E1 < 17 ) THEN IS_PBV1 := 1;  END IF;
    ELSIF ( CC.MODELSERIES = '320' ) THEN
        IF( CC.PF_E1 > 0.6 OR CC.PF_E1 < 0.35 ) THEN IS_PF1 := 1;  END IF;
        IF( CC.COT_E1 > 195  OR CC.COT_E1 < 100 ) THEN IS_COT1 := 1;  END IF;
        IF( CC.PBV_E1 > 55 OR CC.PBV_E1 < 18 ) THEN IS_PBV1 := 1;  END IF;
    ELSIF ( CC.MODELSERIES = '319' ) THEN
        IF( CC.PF_E1 > 0.7 OR CC.PF_E1 < 0.45 ) THEN IS_PF1 := 1;  END IF;
        IF( CC.COT_E1 > 200  OR CC.COT_E1 < 110 ) THEN IS_COT1 := 1;  END IF;
        IF( CC.PBV_E1 > 60 OR CC.PBV_E1 < 20 ) THEN IS_PBV1 := 1;  END IF;
    END IF;
    
    IF( CC.RI_E1 >30  OR CC.RI_E1 < 5 ) THEN IS_RI1 := 1;  END IF;
    IF( CC.TW_S1 >42 OR CC.TW_S1 <4  ) THEN IS_TW1 := 1;  END IF;
    IF( CC.TP_S1 >40  OR CC.TP_S1 <-20  ) THEN IS_TP1 := 1;  END IF;
    IF( CC.PDMT_L_D1 > 30  OR (CC.PDMT_L_D1 < 4 AND CC.PDMT_L_D1>0) ) THEN IS_D11 := 1;  END IF;
    IF( CC.PIN_PRV_M1 > 70  OR CC.PIN_PRV_M1 < 37 ) THEN IS_PRV1 := 1;  END IF;
    IF( CC.PD_S1 > 49 OR CC.PD_S1 < 39 ) THEN IS_PD1 := 1;  END IF;
    --参数超限 PACK1# END
    
    --区分机型判断参数, PACK2# BEGIN
    IF( CC.MODELSERIES = '321' ) THEN 
        IF( CC.PF_N1 > 0.55 OR CC.PF_N1 < 0.3) THEN IS_PF2 := 1;  END IF;
        IF( CC.COT_N1 > 190  OR CC.COT_N1 < 90 ) THEN IS_COT2 := 1;  END IF;
        IF( CC.PBV_N1 > 50 OR CC.PBV_N1 < 17 ) THEN IS_PBV2 := 1;  END IF;
    ELSIF ( CC.MODELSERIES = '320' ) THEN
        IF( CC.PF_N1 > 0.6 OR CC.PF_N1 < 0.35 ) THEN IS_PF2 := 1;  END IF;
        IF( CC.COT_N1 > 195  OR CC.COT_N1 < 100 ) THEN IS_COT2 := 1;  END IF;
        IF( CC.PBV_N1 > 55 OR CC.PBV_N1 < 18 ) THEN IS_PBV2 := 1;  END IF;
    ELSIF ( CC.MODELSERIES = '319' ) THEN
        IF( CC.PF_N1 > 0.7 OR CC.PF_N1 < 0.45 ) THEN IS_PF2 := 1;  END IF;
        IF( CC.COT_N1 > 200  OR CC.COT_N1 < 110 ) THEN IS_COT2 := 1;  END IF;
        IF( CC.PBV_N1 > 60 OR CC.PBV_N1 < 20 ) THEN IS_PBV2 := 1;  END IF;
    END IF;
    
    IF( CC.RI_N1 >30  OR CC.RI_N1 < 5 ) THEN IS_RI2 := 1;  END IF;
    IF( CC.TW_T1 >42 OR CC.TW_T1 < 4  ) THEN IS_TW2 := 1;  END IF;
    IF( CC.TP_T1 >40  OR CC.TP_T1 <-20  ) THEN IS_TP2 := 1;  END IF;
    IF( CC.PDMT_R_D1 > 30  OR (CC.PDMT_R_D1 < 4 AND CC.PDMT_L_D1>0) ) THEN IS_D12 := 1;  END IF;
    IF( CC.PIN_PRV_M2 > 70  OR CC.PIN_PRV_M2 < 37 ) THEN IS_PRV2 := 1;  END IF;
    IF( CC.PD_T1 > 49 OR CC.PD_T1 < 39 ) THEN IS_PD2 := 1;  END IF;
    --参数超限 PACK2# END

    IS_PF_DIV  := 0;
    IS_COT_DIV := 0;
    IS_RI_DIV  := 0;
    IS_PBV_DIV := 0;
    IS_TW_DIV  := 0;
    IS_TP_DIV  := 0;
    IS_D1_DIV  := 0;
    IS_PRV_DIV := 0;
    IS_PD_DIV  := 0;
    IS_D1_TW1 :=0;
    IS_D1_TW2 :=0;
    --左右差值超限 begin
    IF( ABS(CC.PF_E1-CC.PF_N1) > 0.06 ) THEN IS_PF_DIV := 1;    END IF;

    IF( ABS(CC.Cot_E1-cc.cot_n1) > 45 ) THEN IS_COT_DIV := 1;    END IF;

    IF( ABS(CC.Ri_E1-cc.ri_n1) > 10 ) THEN IS_RI_DIV := 1;    END IF;

    IF( ABS(CC.Pbv_E1-cc.pbv_n1) > 13 ) THEN IS_PBV_DIV := 1;    END IF;

    IF( ABS(CC.Tw_S1-cc.tw_t1) > 8 ) THEN IS_TW_DIV := 1;    END IF;

    IF( ABS(CC.Tp_S1-cc.tp_t1) > 15 ) THEN  IS_TP_DIV := 1;    END IF;
    /*
    IF( ABS(CC.D1_DIV) > 1 ) THEN IS_D1_DIV := 1;  END IF;
    IF( ABS(CC.PDMT_L_D1 - CC.TW_S1) > 5) THEN IS_D1_TW1 := 1; END IF;
    IF( ABS(CC.PDMT_R_D1 - CC.TW_T1) > 5) THEN IS_D1_TW2 := 1; END IF;
    */
        
    IF( ABS(CC.Pin_Prv_M1 - cc.pin_prv_m2) > 5 ) THEN IS_PRV_DIV := 1;    END IF;

    IF( ABS(CC.Pd_S1-cc.pd_t1) > 4 ) THEN IS_PD_DIV := 1;    END IF;
    --左右差值超限 end
    IS_MIXCAB := 0;
    IS_DIV_CK := 0;
    IS_DIV_FWD := 0;
    IS_DIV_AFT := 0;
    --温度上下限
    IF(CC.MIXCAB_G1 >30 OR (CC.MIXCAB_G1 < 3 AND CC.MIXCAB_G1>0) ) THEN IS_MIXCAB :=1; END IF;
    IF( CC.CKT_F1>0 and (CC.SC1_V1-CC.CKT_F1 >5 OR CC.SC1_V1-CC.CKT_F1 < -5) ) THEN 
        IS_DIV_CK :=1; END IF; --驾驶舱
    IF( cc.fwdt_f1>0 and (CC.SC1_V1-CC.FWDT_F1 >5 OR CC.SC1_V1-CC.FWDT_F1 < -5) ) THEN 
        IS_DIV_FWD :=1; END IF; --前客舱
        
    IF( CC.AFTT_F1>0 and (CC.SC1_V1-CC.AFTT_F1 >5 OR CC.SC1_V1-CC.AFTT_F1 < -5) ) THEN 
        IS_DIV_AFT :=1; END IF; --后客舱
    
    --单值超限数
    VAR_COUNT_LIMIT := IS_PF1 + IS_COT1 + IS_RI1 + IS_PBV1 + IS_TW1 + IS_TP1 + IS_D11 + IS_PRV1 + IS_PD1
                    + IS_PF2 + IS_COT2 + IS_RI2 + IS_PBV2 + IS_TW2 + IS_TP2 + IS_D12 + IS_PRV2 + IS_PD2;
    
    VAR_COUNT_DIV := IS_PF_DIV + IS_COT_DIV + IS_RI_DIV + IS_PBV_DIV + IS_TW_DIV
                   + IS_TP_DIV + IS_D1_DIV + IS_PRV_DIV + IS_PD_DIV
                   + IS_DIV_CK + IS_DIV_FWD + IS_DIV_AFT ;

    VAR_COUNT := VAR_COUNT_LIMIT + VAR_COUNT_DIV;

    INSERT INTO A_DFD_A21_COMPUTE_STAT(MSG_NO,BASEORGID,ACNUM,RPTDATE
    ,EX_DIV_PF,EX_DIV_COT,EX_DIV_RI,EX_DIV_PBV,EX_DIV_TW,EX_DIV_TP,EX_DIV_D1
    ,EX_DIV_PRV,EX_DIV_PD,EX_DIV_D1_TW1,EX_DIV_D1_TW2
    ,EX_DIV_CK,EX_DIV_FWD,EX_DIV_AFT,EX_MIXCAB
    ,EX_PF1,EX_COT1,EX_RI1,EX_PBV1,EX_TW1,EX_TP1,EX_D11,EX_PRV1,EX_PD1
    ,EX_PF2,EX_COT2,EX_RI2,EX_PBV2,EX_TW2,EX_TP2,EX_D12,EX_PRV2,EX_PD2
    ,EX_COUNT,EX_COUNT_LIMIT,EX_COUNT_DIV,UPDATE_DATE)
    VALUES( CC.MSG_NO,CC.BASEORGID,CC.ACNUM,CC.RPTDATE
     ,IS_PF_DIV,IS_COT_DIV,IS_RI_DIV,IS_PBV_DIV,IS_TW_DIV,IS_TP_DIV,IS_D1_DIV
     ,IS_PRV_DIV,IS_PD_DIV,IS_D1_TW1, IS_D1_TW2
     ,IS_DIV_CK, IS_DIV_FWD,IS_DIV_AFT,IS_MIXCAB
     ,IS_PF1,IS_COT1,IS_RI1,IS_PBV1,IS_TW1,IS_TP1,IS_D11,IS_PRV1,IS_PD1
     ,IS_PF2,IS_COT2,IS_RI2,IS_PBV2,IS_TW2,IS_TP2,IS_D12,IS_PRV2,IS_PD2
     , VAR_COUNT,VAR_COUNT_LIMIT,VAR_COUNT_DIV, SYSDATE);
     
    COMMIT;--数据提交
    
    /*
    select count(1) c, sum(ex_count),sum(Ex_Count_Limit),sum(Ex_Count_Div)
       ,sum(ex_div_pf),sum(ex_div_cot),sum(ex_div_ri),sum(ex_div_pbv),sum(ex_div_tw),sum(ex_div_tp)
       ,sum(ex_div_d1),sum(ex_div_prv),sum(ex_div_pd),sum(ex_div_d1_tw1),sum(ex_div_d1_tw2),sum(ex_div_ck)
       ,sum(ex_div_fwd),sum(ex_div_aft),sum(ex_mixcab),sum(Ex_Pf1),sum(Ex_Cot1),sum(ex_ri1),sum(ex_pbv1)
       ,sum(ex_tw1),sum(Ex_Tp1),sum(Ex_D11),sum(Ex_Prv1),sum(ex_pd1),sum(Ex_Pf2),sum(Ex_Cot2),sum(ex_ri2)
       ,sum(ex_pbv2),sum(ex_tw2),sum(Ex_Tp2),sum(Ex_D12),sum(Ex_Prv2),sum(ex_pd2)
       from a_dfd_a21_compute_stat t 
       where t.ex_count >= 3
    */
    
  END LOOP; --结束遍历

END P_A21_LIMIT_STATISTIC_1;
/

CREATE OR REPLACE PROCEDURE P_A21_LIMIT_STATISTIC IS
VAR_COUNT  NUMBER := 0;
VAR_COUNT_LIMIT NUMBER := 0;
VAR_COUNT_DIV NUMBER := 0;

BEGIN
  /**
    AUTHOR:ZHANGZY
    DATE:2014/12/12
    DESCRIPTION: 计算A21左右数据差值有多少个点超限
  */
  --遍历每个数据
  --pf,cot,ri,pbv,tw,tp,d1,pin_prv, pd, d1_tw1, d1_tw2
  FOR CC IN (SELECT AC.BASEORGID,AM.MODELCODE,AM.MODELSERIES,L.MSG_NO,L.ACNUM,L.RPTDATE
                    ,L.AP_2,L.Y1_2,L.LIM_2
                    ,L.N1_E1,L.N2_E1,L.PF_E1,L.COT_E1,L.RI_E1,L.RO_E1,L.PBV_E1,L.FCV_E1
                    ,L.N1_N1,L.N2_N1,L.PF_N1,L.COT_N1,L.RI_N1,L.RO_N1,L.PBV_N1,L.FCV_N1
                    ,L.P3_S1,L.T3_S1,L.TW_S1,L.TP_S1,L.TPO_S1,L.PD_S1,L.ALT_S1,L.PS_S1
                    ,L.P3_T1,L.T3_T1,L.TW_T1,L.TP_T1,L.TPO_T1,L.PD_T1,L.ALT_T1,L.PS_T1
                    ,L.TAT_V1,L.SAT_V1,L.ZCB_V1,L.ZLD_V1,L.SC1_V1,L.SC2_V1,L.SC3_V1,L.RV_V1
                    ,L.PCSW_X1,L.VSCB_X1,L.PDC_X1,L.VF_X1,L.VW_X1,L.VA_X1,L.OVP_X1,L.CPC_X1
                    ,L.PB_WAI_W1,L.PB_PRV1_W1,L.PB_PRV2_W1,L.SW_XFR_W1
                    ,L.PIN_HPV_M1,L.PIN_PRV_M1,L.OPV1_M1,L.PIN_HPV_M2,L.PIN_PRV_M2,L.OPV2_M1
                    ,L.FAV1_Z1,L.FAV2_Z1,L.HPV1_Z1,L.HPV2_Z1,L.PRV1_Z1,L.PRV2_Z1
                    ,L.FAV1_R1,L.FAV2_R1,L.HPV1_R1,L.HPV2_R1,L.PRV1_R1,L.PRV2_R1
                    ,L.PDMT_L_D1,L.PDMT_R_D1,L.CKT_F1,L.FWDT_F1,L.AFTT_F1,L.HOTAIRPB_F1
                    ,L.CKDUCT_G1,L.FWDUCT_G1,L.AFTDUCT_G1,L.MIXF_G1,L.MIXCAB_G1
                    ,L.TAPRV_H1,L.TAV_H1,L.MAINCTL_H1,L.SECDCTL_H1 
                FROM A_DFD_A21_LIST L,B_AIRCRAFT AC,B_AIRCRAFT_MODEL AM
               WHERE L.ACNUM= AC.AIRCRAFTSN AND AM.ID=AC.ACMODELID --AND AC.BASEORGID=VAR_BASEORGID
                --AND L.MSG_NO = 123329476
                AND NOT EXISTS (SELECT 1 FROM A_DFD_A21_COMPUTE_STAT ST WHERE ST.MSG_NO=L.MSG_NO)
              )
  LOOP
   --单条报文计算
   P_A21_LIMIT_STATISTIC_1(CC.MSG_NO,VAR_COUNT);
   DBMS_OUTPUT.put_line('本次共计算['|| VAR_COUNT ||']条');
  END LOOP; --结束遍历

END P_A21_LIMIT_STATISTIC;
/


CREATE OR REPLACE VIEW V_ALARM_MESSAGE_VIEW AS
SELECT --告警消息查看
       M.ID,
       M.MSGTEMPLATEID,
       MT.ATA,
       M.MONITOBJID,
       M.MONITOBJNAME,
       M.DATAVIEWPK_VALUE,
       M.RPTNO,
       M.ACNUM,
       M.DEVICESN,
       M.RPTDATE,
       M.SUBJECT,
       M.CONTENT,
       M.ORGID,
       (select h.ph from a_dfd_head h where h.msg_no=m.dataviewpk_value) PH,--航段
       a.acmodelid,
       M.SEVERITY,
       M.DISPATHSTATUS,
       M.CREATETIME,
       M.DEALSTATUS,
       M.DEALDESC,
       M.UPDATE_DATE,
       M.UPDATE_MAN
 FROM ALARM_MESSAGE M,ALARM_MSGTEMPLATE MT,b_aircraft a
WHERE MT.ID=M.MSGTEMPLATEID and M.ACNUM=a.aircraftsn;


CREATE OR REPLACE VIEW V_AIRCRAFT_PDI_AC AS
SELECT --空调散热器性能
       AC.AIRCRAFTSN ACNUM,AC.BASEORGID, AC.ACMODELID,'PACK1#' POSITION,
       L.RPTDATE,
        AM.MODELCODE,AM.MODELSERIES,
        case when AM.MODELSERIES = 'A321'
             then ROUND( 0.1789*(RI_E1-7)/23+0.2938*(200-COT_E1)/60+0.4779*((PBV_E1-21)/27)+0.0493*(0.65-PF_E1)/0.2 ,4)
              when AM.MODELSERIES = 'A320'
             then ROUND(0.1789*(RI_E1-7)/22+0.2938*(195-COT_E1)/75+0.4779*((PBV_E1-21)/24)+0.0493*(0.60-PF_E1)/0.2 ,4)
              when AM.MODELSERIES = 'A319'
             then ROUND(0.1789*(RI_E1-7)/20+0.2938*(185-COT_E1)/85+0.4779*((PBV_E1-20)/20)+0.0493*(0.55-PF_E1)/0.15 ,4)
        end PDI_AC_COMPUTE, --左
        C.PDI_AC1 PDI_AC,C.PDI_AC1_AVG5 PDI_AC_AVG5,
       L.RI_E1 RI,L.COT_E1 COT,L.PBV_E1 PBV,L.PF_E1 PF
 FROM B_AIRCRAFT AC,B_AIRCRAFT_MODEL AM,A_DFD_A21_LIST L,A_DFD_A21_COMPUTE C
WHERE L.ACNUM =AC.AIRCRAFTSN AND AM.ID=AC.ACMODELID AND C.MSG_NO = L.MSG_NO
 AND L.MSG_NO=(SELECT MAX(MSG_NO) FROM A_DFD_A21_COMPUTE LT WHERE LT.ACNUM = AC.AIRCRAFTSN)
 --AND BASEORGID=3 --杭州基地
UNION ALL
SELECT --空调散热器性能
       AC.AIRCRAFTSN ACNUM,AC.BASEORGID, AC.ACMODELID,'PACK2#' POSITION,
       L.RPTDATE,
       AM.MODELCODE,AM.MODELSERIES,
       case when AM.MODELSERIES = 'A321'
             then ROUND(0.1789*(RI_N1-7)/23+0.2938*(200-COT_N1)/60+0.4779*((PBV_N1-21)/27)+0.0493*(0.65-PF_N1)/0.2 ,4)
              when AM.MODELSERIES = 'A320'
             then ROUND(0.1789*(RI_N1-7)/22+0.2938*(195-COT_N1)/75+0.4779*((PBV_N1-21)/24)+0.0493*(0.60-PF_N1)/0.2 ,4)
              when AM.MODELSERIES = 'A319'
             then ROUND(0.1789*(RI_N1-7)/20+0.2938*(185-COT_N1)/85+0.4779*((PBV_N1-20)/20)+0.0493*(0.55-PF_N1)/0.15 ,4)
        end PDI_AC_COMPUTE, --右
        C.PDI_AC2 PDI_AC,C.PDI_AC2_AVG5 PDI_AC_AVG5,
       L.RI_N1 RI,L.COT_N1 COT,L.PBV_N1 PBV,L.PF_N1 PF
 FROM B_AIRCRAFT AC,B_AIRCRAFT_MODEL AM,A_DFD_A21_LIST L,A_DFD_A21_COMPUTE C
WHERE L.ACNUM =AC.AIRCRAFTSN AND AM.ID=AC.ACMODELID AND C.MSG_NO = L.MSG_NO
 AND L.MSG_NO=(SELECT MAX(MSG_NO) FROM A_DFD_A21_COMPUTE LT WHERE LT.ACNUM = AC.AIRCRAFTSN);


CREATE OR REPLACE VIEW V_AIRCRAFT_PDI_AC_HIS AS
SELECT --空调散热器性能历史趋势
       AC.AIRCRAFTSN ACNUM,AC.BASEORGID, AC.ACMODELID,'PACK1#' POSITION1,'PACK2#' POSITION2,
       L.MSG_NO,L.RPTDATE,L.SAT_V1,
        AM.MODELCODE,AM.MODELSERIES,
        case when AM.MODELSERIES = 'A321'
             then ROUND( 0.1789*(RI_E1-7)/23+0.2938*(200-COT_E1)/60+0.4779*((PBV_E1-21)/27)+0.0493*(0.65-PF_E1)/0.2 ,4)
              when AM.MODELSERIES = 'A320'
             then ROUND(0.1789*(RI_E1-7)/22+0.2938*(195-COT_E1)/75+0.4779*((PBV_E1-21)/24)+0.0493*(0.60-PF_E1)/0.2 ,4)
              when AM.MODELSERIES = 'A319'
             then ROUND(0.1789*(RI_E1-7)/20+0.2938*(185-COT_E1)/85+0.4779*((PBV_E1-20)/20)+0.0493*(0.55-PF_E1)/0.15 ,4)
        end PDI_AC1, --左

       case when AM.MODELSERIES = 'A321'
             then ROUND(0.1789*(RI_N1-7)/23+0.2938*(200-COT_N1)/60+0.4779*((PBV_N1-21)/27)+0.0493*(0.65-PF_N1)/0.2 ,4)
              when AM.MODELSERIES = 'A320'
             then ROUND(0.1789*(RI_N1-7)/22+0.2938*(195-COT_N1)/75+0.4779*((PBV_N1-21)/24)+0.0493*(0.60-PF_N1)/0.2 ,4)
              when AM.MODELSERIES = 'A319'
             then ROUND(0.1789*(RI_N1-7)/20+0.2938*(185-COT_N1)/85+0.4779*((PBV_N1-20)/20)+0.0493*(0.55-PF_N1)/0.15 ,4)
        end PDI_AC2, --右
       L.RI_E1,L.COT_E1,L.PBV_E1,L.PF_E1,
       L.RI_N1,L.COT_N1,L.PBV_N1,L.PF_N1,
       (select f.v_out from  a_dfd_field_compute f where L.msg_no=f.msg_no and f.f_name='PDI_AC1') PDI_AC1_OUT,
       (select f.v_out from  a_dfd_field_compute f where L.msg_no=f.msg_no and f.f_name='PDI_AC2') PDI_AC2_OUT,
       (select f.v_pointtype from  a_dfd_field_compute f where L.msg_no=f.msg_no and f.f_name='PDI_AC1') PDI_AC1_POINTTYPE,
       (select f.v_pointtype from  a_dfd_field_compute f where L.msg_no=f.msg_no and f.f_name='PDI_AC2') PDI_AC2_POINTTYPE,
       (select f.f_value_roll5 from  a_dfd_field_compute f where L.msg_no=f.msg_no and f.f_name='PDI_AC1') PDI_AC1_ROLL5,
       (select f.f_value_roll5 from  a_dfd_field_compute f where L.msg_no=f.msg_no and f.f_name='PDI_AC2') PDI_AC2_ROLL5
 FROM B_AIRCRAFT AC,B_AIRCRAFT_MODEL AM,A_DFD_A21_LIST L,A_DFD_HEAD H
WHERE L.ACNUM =AC.AIRCRAFTSN AND AM.ID=AC.ACMODELID AND H.MSG_NO = L.MSG_NO AND H.PH='06';
