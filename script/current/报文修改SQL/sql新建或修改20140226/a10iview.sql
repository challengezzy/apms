CREATE OR REPLACE VIEW V_A10ILIST_VIEW AS
SELECT A.TEL_CONTENT,
       H.RPTNO,
       H.ACMODEL,
       H.FLY_FROM,
       H.FLY_TO,
       H.FLT,
       H.PH,
       H.CNT,
       H.CODE,
       H.BLEED_STATUS,
       H.APU,
       H.TAT,
       H.ALT,
       H.CAS,
       H.MN,
       H.GW,
       H.CG,
       H.DMU,
       H.STATUS,
       H.DATE_UTC,
       L.ID,
       L.MSG_NO,
       L.ACNUM,
       L.RPTDATE,
       L.ESN_1,
       L.EHRS_1,
       L.ECYC_1,
       L.AP_1,
       L.Y1_EC,
       L.Y2_EC,
       L.ESN_2,
       L.EHRS_2,
       L.ECYC_2,
       L.AP_2,
       L.E,
       L.MAX,
       L.LIM,
       L.TOL,
       L.TTP,
       L.TTF,
       L.FF,
       L.PD,
       L.SM,
       L.N1_S1,
       L.N2_S1,
       L.EGT_S1,
       L.FF_S1,
       L.P2_S1,
       L.T25_S1,
       L.N1_S2,
       L.N1_S3,
       L.N2_S3,
       L.EGT_S3,
       L.FF_S3,
       L.P2_S3,
       L.T25_S3,
       L.PD_S3,
       L.N1_S4,
       L.N2_S4,
       L.EGT_S4,
       L.FF_S4,
       L.P2_S4,
       L.T25_S4,
       L.PD_S4,
       L.N1_S5,
       L.N2_S5,
       L.EGT_S5,
       L.FF_S5,
       L.P2_S5,
       L.T25_S5,
       L.PD_S5,
       L.N1_S6,
       L.N2_S6,
       L.EGT_S6,
       L.FF_S6,
       L.P2_S6,
       L.T25_S6,
       L.PD_S6,
       L.N1_S7,
       L.N2_S7,
       L.EGT_S7,
       L.FF_S7,
       L.P2_S7,
       L.T25_S7,
       L.PD_S7,
       L.P3_T1,
       L.T3_T1,
       L.SVA_T1,
       L.BAF_T1,
       L.T2_T1,
       L.OIT_T1,
       L.ECW5_T1,
       L.P3_T2,
       L.T3_T2,
       L.SVA_T2,
       L.BAF_T2,
       L.T2_T2,
       L.OIT_T2,
       L.ECW5_T2,
       L.P3_T3,
       L.T3_T3,
       L.SVA_T3,
       L.BAF_T3,
       L.T2_T3,
       L.OIT_T3,
       L.ECW5_T3,
       L.P3_T4,
       L.T3_T4,
       L.SVA_T4,
       L.BAF_T4,
       L.T2_T4,
       L.OIT_T4,
       L.ECW5_T4,
       L.P3_T5,
       L.T3_T5,
       L.SVA_T5,
       L.BAF_T5,
       L.T2_T5,
       L.OIT_T5,
       L.ECW5_T5,
       L.P3_T6,
       L.T3_T6,
       L.SVA_T6,
       L.BAF_T6,
       L.T2_T6,
       L.OIT_T6,
       L.ECW5_T6,
       L.P3_T7,
       L.T3_T7,
       L.SVA_T7,
       L.BAF_T7,
       L.T2_T7,
       L.OIT_T7,
       L.ECW5_T7,
       L.N2_S2,
       L.EGT_S2,
       L.FF_S2,
       L.P2_S2,
       L.T25_S2,
       L.PD_S2,
       L.PD_S1,
       L.UPDATE_DATE
  FROM A_DFD_A10IAVE25_LIST L, A_DFD_HEAD H,A_ACARS_TELEGRAPH A
 WHERE H.MSG_NO = L.MSG_NO AND H.MSG_NO=A.MSG_NO;
