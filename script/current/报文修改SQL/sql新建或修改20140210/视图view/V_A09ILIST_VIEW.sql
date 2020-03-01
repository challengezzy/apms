CREATE OR REPLACE VIEW V_A09ILIST_VIEW AS
SELECT A.TEL_CONTENT,
       H.RPTNO,
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
       L.SVA_1,
       L.BAF_1,
       L.ESN_2,
       L.EHRS_2,
       L.ECYC_2,
       L.AP_2,
       L.SVA_2,
       L.BAF_2,
       L.E,
       L.DIV,
       L.REF,
       L.K,
       L.ECW3,
       L.ECW4,
       L.PARA,
       L.EPR_S1,
       L.EGT_S1,
       L.N1_S1,
       L.N2_S1,
       L.FF_S1,
       L.TN_S1,
       L.EPRC_S1,
       L.EPR_S2,
       L.EGT_S2,
       L.N1_S2,
       L.N2_S2,
       L.FF_S2,
       L.TN_S2,
       L.EPRC_S2,
       L.EPR_S3,
       L.EGT_S3,
       L.N1_S3,
       L.N2_S3,
       L.FF_S3,
       L.TN_S3,
       L.EPRC_S3,
       L.EPR_S4,
       L.EGT_S4,
       L.N1_S4,
       L.N2_S4,
       L.FF_S4,
       L.TN_S4,
       L.EPRC_S4,
       L.EPR_S5,
       L.EGT_S5,
       L.N1_S5,
       L.N2_S5,
       L.FF_S5,
       L.TN_S5,
       L.EPRC_S5,
       L.EPR_S6,
       L.EGT_S6,
       L.N1_S6,
       L.N2_S6,
       L.FF_S6,
       L.TN_S6,
       L.EPRC_S6,
       L.EPR_S7,
       L.EGT_S7,
       L.N1_S7,
       L.N2_S7,
       L.FF_S7,
       L.TN_S7,
       L.EPRC_S7,
       L.EPR_T1,
       L.EGT_T1,
       L.N1_T1,
       L.N2_T1,
       L.FF_T1,
       L.TN_T1,
       L.EPRC_T1,
       L.EPR_T2,
       L.EGT_T2,
       L.N1_T2,
       L.N2_T2,
       L.FF_T2,
       L.TN_T2,
       L.EPRC_T2,
       L.EPR_T3,
       L.EGT_T3,
       L.N1_T3,
       L.N2_T3,
       L.FF_T3,
       L.TN_T3,
       L.EPRC_T3,
       L.EPR_T4,
       L.EGT_T4,
       L.N1_T4,
       L.N2_T4,
       L.FF_T4,
       L.TN_T4,
       L.EPRC_T4,
       L.EPR_T5,
       L.EGT_T5,
       L.N1_T5,
       L.N2_T5,
       L.FF_T5,
       L.TN_T5,
       L.EPRC_T5,
       L.EPR_T6,
       L.EGT_T6,
       L.N1_T6,
       L.N2_T6,
       L.FF_T6,
       L.TN_T6,
       L.EPRC_T6,
       L.EPR_T7,
       L.EGT_T7,
       L.N1_T7,
       L.N2_T7,
       L.FF_T7,
       L.TN_T7,
       L.EPRC_T7,
       L.UPDATE_DATE
  FROM A_DFD_A09IAVE25_LIST L, A_DFD_HEAD H,A_ACARS_TELEGRAPH A
 WHERE H.MSG_NO = L.MSG_NO AND H.MSG_NO=A.MSG_NO;
