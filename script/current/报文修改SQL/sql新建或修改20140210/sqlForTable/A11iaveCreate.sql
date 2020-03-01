CREATE TABLE A_DFD_A11IAVE25_LIST  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18),
   ACNUM                VARCHAR2(16),
   RPTDATE              DATE,
   CODE                 VARCHAR2(16),
   ESN_1                VARCHAR2(12),
   EHRS_1               NUMBER(8,3),
   ECYC_1               NUMBER(8,3),
   AP_1                 VARCHAR2(12),
   ESN_2                VARCHAR2(12),
   EHRS_2               NUMBER(8,3),
   ECYC_2               NUMBER(8,3),
   AP_2                 VARCHAR2(12),
   EPR_1                NUMBER(8,3),
   EGT_1                NUMBER(8,3),
   N1_1                 NUMBER(8,3),
   N2_1                 NUMBER(8,3),
   FF_1                 NUMBER(8,3),
   P125_1               NUMBER(8,3),
   P25_1                NUMBER(8,3),
   T25_1                NUMBER(8,3),
   P3_1                 NUMBER(8,3),
   T3_1                 NUMBER(8,3),
   P49_1                NUMBER(8,3),
   SVA_1                NUMBER(8,3),
   EPR_2                NUMBER(8,3),
   EGT_2                NUMBER(8,3),
   N1_2                 NUMBER(8,3),
   N2_2                 NUMBER(8,3),
   FF_2                 NUMBER(8,3),
   P125_2               NUMBER(8,3),
   P25_2                NUMBER(8,3),
   T25_2                NUMBER(8,3),
   P3_2                 NUMBER(8,3),
   T3_2                 NUMBER(8,3),
   P49_2                NUMBER(8,3),
   SVA_2                NUMBER(8,3),
   BAF_1                NUMBER(8,3),
   ACC_1                NUMBER(8,3),
   LP_1                 NUMBER(8,3),
   GLE_1                NUMBER(8,3),
   PD_1                 NUMBER(8,3),
   TN_1                 NUMBER(8,3),
   P2_1                 NUMBER(8,3),
   T2_1                 NUMBER(8,3),
   ECW1_1               VARCHAR2(12),
   ECW2_1               VARCHAR2(12),
   EVM_1                VARCHAR2(12),
   VB1_1                NUMBER(8,3),
   VB2_1                NUMBER(8,3),
   PHA_1                NUMBER(8,3),
   OIP_1                NUMBER(8,3),
   OIT_1                NUMBER(8,3),
   EGTK_1               NUMBER(8,3),
   N1K_1                NUMBER(8,3),
   N2K_1                NUMBER(8,3),
   FFK_1                NUMBER(8,3),
   BAF_2                NUMBER(8,3),
   ACC_2                NUMBER(8,3),
   LP_2                 NUMBER(8,3),
   GLE_2                NUMBER(8,3),
   PD_2                 NUMBER(8,3),
   TN_2                 NUMBER(8,3),
   P2_2                 NUMBER(8,3),
   T2_2                 NUMBER(8,3),
   ECW1_2               VARCHAR2(12),
   ECW2_2               VARCHAR2(12),
   EVM_2                VARCHAR2(12),
   VB1_2                NUMBER(8,3),
   VB2_2                NUMBER(8,3),
   PHA_2                NUMBER(8,3),
   OIP_2                NUMBER(8,3),
   OIT_2                NUMBER(8,3),
   EGTK_2               NUMBER(8,3),
   N1K_2                NUMBER(8,3),
   N2K_2                NUMBER(8,3),
   FFK_2                NUMBER(8,3),
   UPDATE_DATE          DATE
);

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.ID IS
'系统编号';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.MSG_NO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.ACNUM IS
'飞机号';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.RPTDATE IS
'报文时间
2012-1-1 00:00:00';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.CODE IS
'发送编码';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.ESN_1 IS
'左发序号';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.EHRS_1 IS
'左发飞行小时';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.ESN_2 IS
'左发序号';

COMMENT ON COLUMN A_DFD_A11IAVE25_LIST.EHRS_2 IS
'左发飞行小时';

/*==============================================================*/
/* Index: "INDEX_218"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_218" ON A_DFD_A11IAVE25_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: "INDEX_219"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_219" ON A_DFD_A11IAVE25_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: "INDEX_220"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_220" ON A_DFD_A11IAVE25_LIST (
   RPTDATE ASC
);
