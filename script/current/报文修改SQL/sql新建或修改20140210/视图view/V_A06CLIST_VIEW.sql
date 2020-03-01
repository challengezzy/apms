CREATE OR REPLACE VIEW V_A06CLIST_VIEW AS
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
       L.ERT_1,
       L.ECYC_1,
       L.AP_1,
       L.TR_1,
       L.TR_2,
       L.ESN_2,
       L.EHRS_2,
       L.ERT_2,
       L.ECYC_2,
       L.AP_2,
       L.N1_S1,
       L.N1C_S1,
       L.N2_S1,
       L.EGT_S1,
       L.FF_S1,
       L.P3_S1,
       L.T3_S1,
       L.N1_S2,
       L.N1C_S2,
       L.N2_S2,
       L.EGT_S2,
       L.FF_S2,
       L.P3_S2,
       L.T3_S2,
       L.N1_S3,
       L.N1C_S3,
       L.N2_S3,
       L.EGT_S3,
       L.FF_S3,
       L.P3_S3,
       L.T3_S3,
       L.N1_S4,
       L.N1C_S4,
       L.N2_S4,
       L.EGT_S4,
       L.FF_S4,
       L.P3_S4,
       L.T3_S4,
       L.N1_S5,
       L.N1C_S5,
       L.N2_S5,
       L.EGT_S5,
       L.FF_S5,
       L.P3_S5,
       L.T3_S5,
       L.N1_S6,
       L.N1C_S6,
       L.N2_S6,
       L.EGT_S6,
       L.FF_S6,
       L.P3_S6,
       L.T3_S6,
       L.N1_S7,
       L.N1C_S7,
       L.N2_S7,
       L.EGT_S7,
       L.FF_S7,
       L.P3_S7,
       L.T3_S7,
       L.N1_S8,
       L.N1C_S8,
       L.N2_S8,
       L.EGT_S8,
       L.FF_S8,
       L.P3_S8,
       L.T3_S8,
       L.N1_S9,
       L.N1C_S9,
       L.N2_S9,
       L.EGT_S9,
       L.FF_S9,
       L.P3_S9,
       L.T3_S9,
       L.T5_V1,
       L.P25_V1,
       L.T25_V1,
       L.PT2_V1,
       L.ECW1_V1,
       L.VBV_V1,
       L.VSV_V1,
       L.T5_V2,
       L.P25_V2,
       L.T25_V2,
       L.PT2_V2,
       L.ECW1_V2,
       L.VBV_V2,
       L.VSV_V2,
       L.T5_V3,
       L.P25_V3,
       L.T25_V3,
       L.PT2_V3,
       L.ECW1_V3,
       L.VBV_V3,
       L.VSV_V3,
       L.T5_V4,
       L.P25_V4,
       L.T25_V4,
       L.PT2_V4,
       L.ECW1_V4,
       L.VBV_V4,
       L.VSV_V4,
       L.T5_V5,
       L.P25_V5,
       L.T25_V5,
       L.PT2_V5,
       L.ECW1_V5,
       L.VBV_V5,
       L.VSV_V5,
       L.T5_V6,
       L.P25_V6,
       L.T25_V6,
       L.PT2_V6,
       L.ECW1_V6,
       L.VBV_V6,
       L.VSV_V6,
       L.T5_V7,
       L.P25_V7,
       L.T25_V7,
       L.PT2_V7,
       L.ECW1_V7,
       L.VBV_V7,
       L.VSV_V7,
       L.T5_V8,
       L.P25_V8,
       L.T25_V8,
       L.PT2_V8,
       L.ECW1_V8,
       L.VBV_V8,
       L.VSV_V8,
       L.T5_V9,
       L.P25_V9,
       L.T25_V9,
       L.PT2_V9,
       L.ECW1_V9,
       L.VBV_V9,
       L.VSV_V9,
       L.T5_X1,
       L.P25_X1,
       L.T25_X1,
       L.PT2_X1,
       L.ECW1_X1,
       L.VBV_X1,
       L.VSV_X1,
       L.T5_X2,
       L.P25_X2,
       L.T25_X2,
       L.PT2_X2,
       L.ECW1_X2,
       L.VBV_X2,
       L.VSV_X2,
       L.T5_X3,
       L.P25_X3,
       L.T25_X3,
       L.PT2_X3,
       L.ECW1_X3,
       L.VBV_X3,
       L.VSV_X3,
       L.T5_X4,
       L.P25_X4,
       L.T25_X4,
       L.PT2_X4,
       L.ECW1_X4,
       L.VBV_X4,
       L.VSV_X4,
       L.T5_X5,
       L.P25_X5,
       L.T25_X5,
       L.PT2_X5,
       L.ECW1_X5,
       L.VBV_X5,
       L.VSV_X5,
       L.T5_X6,
       L.P25_X6,
       L.T25_X6,
       L.PT2_X6,
       L.ECW1_X6,
       L.VBV_X6,
       L.VSV_X6,
       L.T5_X7,
       L.P25_X7,
       L.T25_X7,
       L.PT2_X7,
       L.ECW1_X7,
       L.VBV_X7,
       L.VSV_X7,
       L.T5_X8,
       L.P25_X8,
       L.T25_X8,
       L.PT2_X8,
       L.ECW1_X8,
       L.VBV_X8,
       L.VSV_X8,
       L.T5_X9,
       L.P25_X9,
       L.T25_X9,
       L.PT2_X9,
       L.ECW1_X9,
       L.VBV_X9,
       L.VSV_X9,
       L.N1_T1,
       L.N1C_T1,
       L.N2_T1,
       L.EGT_T1,
       L.FF_T1,
       L.P3_T1,
       L.T3_T1,
       L.N1_T2,
       L.N1C_T2,
       L.N2_T2,
       L.EGT_T2,
       L.FF_T2,
       L.P3_T2,
       L.T3_T2,
       L.N1_T3,
       L.N1C_T3,
       L.N2_T3,
       L.EGT_T3,
       L.FF_T3,
       L.P3_T3,
       L.T3_T3,
       L.N1_T4,
       L.N1C_T4,
       L.N2_T4,
       L.EGT_T4,
       L.FF_T4,
       L.P3_T4,
       L.T3_T4,
       L.N1_T5,
       L.N1C_T5,
       L.N2_T5,
       L.EGT_T5,
       L.FF_T5,
       L.P3_T5,
       L.T3_T5,
       L.N1_T6,
       L.N1C_T6,
       L.N2_T6,
       L.EGT_T6,
       L.FF_T6,
       L.P3_T6,
       L.T3_T6,
       L.N1_T7,
       L.N1C_T7,
       L.N2_T7,
       L.EGT_T7,
       L.FF_T7,
       L.P3_T7,
       L.T3_T7,
       L.N1_T8,
       L.N1C_T8,
       L.N2_T8,
       L.EGT_T8,
       L.FF_T8,
       L.P3_T8,
       L.T3_T8,
       L.N1_T9,
       L.N1C_T9,
       L.N2_T9,
       L.EGT_T9,
       L.FF_T9,
       L.P3_T9,
       L.T3_T9,
       L.E,
       L.MAX,
       L.LIM,
       L.REF,
       L.TOL,
       L.TTP,
       L.Y1,
       L.Y2,
       L.PSEL,
       L.UPDATE_DATE
  FROM A_DFD_A06CFM56_LIST L, A_DFD_HEAD H,A_ACARS_TELEGRAPH A
 WHERE H.MSG_NO = L.MSG_NO AND H.MSG_NO=A.MSG_NO;
