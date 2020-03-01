CREATE OR REPLACE VIEW V_A07ILIST_VIEW AS
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
       L.ECW4_1,
       L.Y1_EC,
       L.Y2_EC,
       L.ESN_2,
       L.EHRS_2,
       L.ECYC_2,
       L.AP_2,
       L.ECW4_2,
       L.E,
       L.MAX,
       L.LIM,
       L.REF,
       L.TOL,
       L.TTP,
       L.EVM,
       L.PARA,
       L.N1_S1,
       L.N2_S1,
       L.VB1_S1,
       L.VB2_S1,
       L.PHA_S1,
       L.OIT_S1,
       L.OIP_S1,
       L.PSB_S1,
       L.BVP_S1,
       L.N1_S2,
       L.N2_S2,
       L.VB1_S2,
       L.VB2_S2,
       L.PHA_S2,
       L.OIT_S2,
       L.OIP_S2,
       L.PSB_S2,
       L.BVP_S2,
       L.N1_S3,
       L.N2_S3,
       L.VB1_S3,
       L.VB2_S3,
       L.PHA_S3,
       L.OIT_S3,
       L.OIP_S3,
       L.PSB_S3,
       L.BVP_S3,
       L.N1_S4,
       L.N2_S4,
       L.VB1_S4,
       L.VB2_S4,
       L.PHA_S4,
       L.OIT_S4,
       L.OIP_S4,
       L.PSB_S4,
       L.BVP_S4,
       L.N1_S5,
       L.N2_S5,
       L.VB1_S5,
       L.VB2_S5,
       L.PHA_S5,
       L.OIT_S5,
       L.OIP_S5,
       L.PSB_S5,
       L.BVP_S5,
       L.N1_S6,
       L.N2_S6,
       L.VB1_S6,
       L.VB2_S6,
       L.PHA_S6,
       L.OIT_S6,
       L.OIP_S6,
       L.PSB_S6,
       L.BVP_S6,
       L.N1_S7,
       L.N2_S7,
       L.VB1_S7,
       L.VB2_S7,
       L.PHA_S7,
       L.OIT_S7,
       L.OIP_S7,
       L.PSB_S7,
       L.BVP_S7,
       L.N1_S8,
       L.N2_S8,
       L.VB1_S8,
       L.VB2_S8,
       L.PHA_S8,
       L.OIT_S8,
       L.OIP_S8,
       L.PSB_S8,
       L.BVP_S8,
       L.N1_S9,
       L.N2_S9,
       L.VB1_S9,
       L.VB2_S9,
       L.PHA_S9,
       L.OIT_S9,
       L.OIP_S9,
       L.PSB_S9,
       L.BVP_S9,
       L.N1_S0,
       L.N2_S0,
       L.VB1_S0,
       L.VB2_S0,
       L.PHA_S0,
       L.OIT_S0,
       L.OIP_S0,
       L.PSB_S0,
       L.BVP_S0,
       L.UPDATE_DATE
  FROM A_DFD_A07IAVE25_LIST L, A_DFD_HEAD H,A_ACARS_TELEGRAPH A
 WHERE H.MSG_NO = L.MSG_NO AND H.MSG_NO=A.MSG_NO;
