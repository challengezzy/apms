CREATE OR REPLACE VIEW V_A11ILIST_VIEW AS
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
       L.EHRS_1 ,
       L.ECYC_1,
       L.AP_1,
       L.ESN_2,
       L.EHRS_2,
       L.ECYC_2,
       L.AP_2,
       L.EPR_1,
       L.EGT_1,
       L.N1_1,
       L.N2_1,
       L.FF_1,
       L.P125_1,
       L.P25_1,
       L.T25_1,
       L.P3_1,
       L.T3_1,
       L.P49_1,
       L.SVA_1,
       L.EPR_2,
       L.EGT_2,
       L.N1_2,
       L.N2_2,
       L.FF_2,
       L.P125_2,
       L.P25_2,
       L.T25_2,
       L.P3_2,
       L.T3_2,
       L.P49_2,
       L.SVA_2,
       L.BAF_1,
       L.ACC_1,
       L.LP_1,
       L.GLE_1,
       L.PD_1,
       L.TN_1,
       L.P2_1,
       L.T2_1,
       L.ECW1_1,
       L.ECW2_1,
       L.EVM_1,
       L.VB1_1,
       L.VB2_1,
       L.PHA_1,
       L.OIP_1,
       L.OIT_1,
       L.EGTK_1,
       L.N1K_1,
       L.N2K_1,
       L.FFK_1,
       L.BAF_2,
       L.ACC_2,
       L.LP_2,
       L.GLE_2,
       L.PD_2,
       L.TN_2,
       L.P2_2,
       L.T2_2,
       L.ECW1_2,
       L.ECW2_2,
       L.EVM_2,
       L.VB1_2,
       L.VB2_2,
       L.PHA_2,
       L.OIP_2,
       L.OIT_2,
       L.EGTK_2,
       L.N1K_2,
       L.N2K_2,
       L.FFK_2,
       L.UPDATE_DATE
  FROM A_DFD_A11IAVE25_LIST L, A_DFD_HEAD H,A_ACARS_TELEGRAPH A
 WHERE H.MSG_NO = L.MSG_NO AND H.MSG_NO=A.MSG_NO;
