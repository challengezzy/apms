CREATE TABLE A_DFD_A07CFM56_LIST  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18),
   ACNUM                VARCHAR2(16),
   RPTDATE              DATE,
   CODE                 VARCHAR2(16),
   ESN_1                VARCHAR2(16),
   EHRS_1               NUMBER(9,3),
   ERT_1                NUMBER(9,3),
   ECYC_1               NUMBER(9,3),
   AP_1                 VARCHAR2(16),
   ECW1_1               VARCHAR2(16),
   ESN_2                VARCHAR2(16),
   EHRS_2               NUMBER(9,3),
   ERT_2                NUMBER(9,3),
   ECYC_2               NUMBER(9,3),
   AP_2                 VARCHAR2(16),
   ECW1_2               VARCHAR2(16),
   E                    NUMBER(9,3),
   MAX                  NUMBER(9,3),
   LIM                  NUMBER(9,3),
   REF                  NUMBER(9,3),
   TOL                  NUMBER(9,3),
   TTP                  NUMBER(9,3),
   EVM                  VARCHAR2(16),
   N1_S1                NUMBER(9,3),
   N2_S1                NUMBER(9,3),
   VN_S1                NUMBER(9,3),
   VC_S1                NUMBER(9,3),
   VH_S1                NUMBER(9,3),
   VL_S1                NUMBER(9,3),
   PHA_S1               NUMBER(9,3),
   PHT_S1               NUMBER(9,3),
   OIT_S1               NUMBER(9,3),
   OIP_S1               NUMBER(9,3),
   UPDATE_DATE          DATE,
   N1_S2                NUMBER(9,3),
   N2_S2                NUMBER(9,3),
   VN_S2                NUMBER(9,3),
   VC_S2                NUMBER(9,3),
   VH_S2                NUMBER(9,3),
   VL_S2                NUMBER(9,3),
   PHA_S2               NUMBER(9,3),
   PHT_S2               NUMBER(9,3),
   OIT_S2               NUMBER(9,3),
   OIP_S2               NUMBER(9,3),
   N1_S3                NUMBER(9,3),
   N2_S3                NUMBER(9,3),
   VN_S3                NUMBER(9,3),
   VC_S3                NUMBER(9,3),
   VH_S3                NUMBER(9,3),
   VL_S3                NUMBER(9,3),
   PHA_S3               NUMBER(9,3),
   PHT_S3               NUMBER(9,3),
   OIT_S3               NUMBER(9,3),
   OIP_S3               NUMBER(9,3),
   N1_S4                NUMBER(9,3),
   N2_S4                NUMBER(9,3),
   VN_S4                NUMBER(9,3),
   VC_S4                NUMBER(9,3),
   VH_S4                NUMBER(9,3),
   VL_S4                NUMBER(9,3),
   PHA_S4               NUMBER(9,3),
   PHT_S4               NUMBER(9,3),
   OIT_S4               NUMBER(9,3),
   OIP_S4               NUMBER(9,3),
   N1_S5                NUMBER(9,3),
   N2_S5                NUMBER(9,3),
   VN_S5                NUMBER(9,3),
   VC_S5                NUMBER(9,3),
   VH_S5                NUMBER(9,3),
   VL_S5                NUMBER(9,3),
   PHA_S5               NUMBER(9,3),
   PHT_S5               NUMBER(9,3),
   OIT_S5               NUMBER(9,3),
   OIP_S5               NUMBER(9,3),
   N1_S6                NUMBER(9,3),
   N2_S6                NUMBER(9,3),
   VN_S6                NUMBER(9,3),
   VC_S6                NUMBER(9,3),
   VH_S6                NUMBER(9,3),
   VL_S6                NUMBER(9,3),
   PHA_S6               NUMBER(9,3),
   PHT_S6               NUMBER(9,3),
   OIT_S6               NUMBER(9,3),
   OIP_S6               NUMBER(9,3),
   N1_S7                NUMBER(9,3),
   N2_S7                NUMBER(9,3),
   VN_S7                NUMBER(9,3),
   VC_S7                NUMBER(9,3),
   VH_S7                NUMBER(9,3),
   VL_S7                NUMBER(9,3),
   PHA_S7               NUMBER(9,3),
   PHT_S7               NUMBER(9,3),
   OIT_S7               NUMBER(9,3),
   OIP_S7               NUMBER(9,3),
   N1_S8                NUMBER(9,3),
   N2_S8                NUMBER(9,3),
   VN_S8                NUMBER(9,3),
   VC_S8                NUMBER(9,3),
   VH_S8                NUMBER(9,3),
   VL_S8                NUMBER(9,3),
   PHA_S8               NUMBER(9,3),
   PHT_S8               NUMBER(9,3),
   OIT_S8               NUMBER(9,3),
   OIP_S8               NUMBER(9,3),
   N1_S9                NUMBER(9,3),
   N2_S9                NUMBER(9,3),
   VN_S9                NUMBER(9,3),
   VC_S9                NUMBER(9,3),
   VH_S9                NUMBER(9,3),
   VL_S9                NUMBER(9,3),
   PHA_S9               NUMBER(9,3),
   PHT_S9               NUMBER(9,3),
   OIT_S9               NUMBER(9,3),
   OIP_S9               NUMBER(9,3),
   N1_S0                NUMBER(9,3),
   N2_S0                NUMBER(9,3),
   VN_S0                NUMBER(9,3),
   VC_S0                NUMBER(9,3),
   VH_S0                NUMBER(9,3),
   VL_S0                NUMBER(9,3),
   PHA_S0               NUMBER(9,3),
   PHT_S0               NUMBER(9,3),
   OIT_S0               NUMBER(9,3),
   OIP_S0               NUMBER(9,3)
);

COMMENT ON COLUMN A_DFD_A07CFM56_LIST.ID IS
'系统编号';

COMMENT ON COLUMN A_DFD_A07CFM56_LIST.MSG_NO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A07CFM56_LIST.ACNUM IS
'飞机号';

COMMENT ON COLUMN A_DFD_A07CFM56_LIST.RPTDATE IS
'报文时间
2012-1-1 00:00:00';

COMMENT ON COLUMN A_DFD_A07CFM56_LIST.CODE IS
'发送编码';

/*==============================================================*/
/* Index: "INDEX_200"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_200" ON A_DFD_A07CFM56_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: "INDEX_201"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_201" ON A_DFD_A07CFM56_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: "INDEX_202"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_202" ON A_DFD_A07CFM56_LIST (
   RPTDATE ASC
);
