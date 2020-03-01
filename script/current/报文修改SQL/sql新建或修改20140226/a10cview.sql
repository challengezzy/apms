CREATE OR REPLACE VIEW V_A10CLIST_VIEW AS
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
       L.ERT_1,
       L.ECYC_1,
       L.AP_1,
       L.Y1_EC,
       L.Y2_EC,
       L.ESN_2,
       L.ERT_2,
       L.EHRS_2,
       L.ECYC_2,
       L.AP_2,
       L.E,
       L.MAX,
       L.LIM,
       L.TOL,
       L.TTP,
       L.FF,
       L.PD,
       L.SM,
       L.N1_S1,
       L.N2_S1,
       L.EGT_S1,
       L.FF_S1,
       L.FMV_S1,
       L.T25_S1,
       L.N1_S2,
       L.N1_S3,
       L.N2_S3,
       L.EGT_S3,
       L.FF_S3,
       L.FMV_S3,
       L.T25_S3,
       L.PD_S3,
       L.N1_S4,
       L.N2_S4,
       L.EGT_S4,
       L.FF_S4,
       L.FMV_S4,
       L.T25_S4,
       L.PD_S4,
       L.N1_S5,
       L.N2_S5,
       L.EGT_S5,
       L.FF_S5,
       L.FMV_S5,
       L.T25_S5,
       L.PD_S5,
       L.N1_S6,
       L.N2_S6,
       L.EGT_S6,
       L.FF_S6,
       L.FMV_S6,
       L.T25_S6,
       L.PD_S6,
       L.N1_S7,
       L.N2_S7,
       L.EGT_S7,
       L.FF_S7,
       L.FMV_S7,
       L.T25_S7,
       L.PD_S7,
       L.P3_T1,
       L.T3_T1,
       L.VSV_T1,
       L.VBV_T1,
       L.T5_T1,
       L.OIT_T1,
       L.ECW5_T1,
       L.P3_T2,
       L.T3_T2,
       L.VSV_T2,
       L.VBV_T2,
       L.T5_T2,
       L.OIT_T2,
       L.ECW5_T2,
       L.P3_T3,
       L.T3_T3,
       L.VSV_T3,
       L.VBV_T3,
       L.T5_T3,
       L.OIT_T3,
       L.ECW5_T3,
       L.P3_T4,
       L.T3_T4,
       L.VSV_T4,
       L.VBV_T4,
       L.T5_T4,
       L.OIT_T4,
       L.ECW5_T4,
       L.P3_T5,
       L.T3_T5,
       L.VSV_T5,
       L.VBV_T5,
       L.T5_T5,
       L.OIT_T5,
       L.ECW5_T5,
       L.P3_T6,
       L.T3_T6,
       L.VSV_T6,
       L.VBV_T6,
       L.T5_T6,
       L.OIT_T6,
       L.ECW5_T6,
       L.P3_T7,
       L.T3_T7,
       L.VSV_T7,
       L.VBV_T7,
       L.T5_T7,
       L.OIT_T7,
       L.ECW5_T7,
       L.N2_S2,
       L.EGT_S2,
       L.FF_S2,
       L.FMV_S2,
       L.T25_S2,
       L.PD_S2,
       L.PD_S1,
       L.UPDATE_DATE
  FROM A_DFD_A10CFM56_LIST L, A_DFD_HEAD H,A_ACARS_TELEGRAPH A
 WHERE H.MSG_NO = L.MSG_NO AND H.MSG_NO=A.MSG_NO;
