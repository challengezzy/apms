CREATE TABLE A_DFD_A10CFM56_LIST  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18),
   ACNUM                VARCHAR2(16),
   RPTDATE              DATE,
   CODE                 VARCHAR2(16),
   ESN_1                NUMBER(24),
   EHRS_1               NUMBER(24),
   ERT_1                NUMBER(24),
   ECYC_1               NUMBER(24),
   AP_1                 VARCHAR2(16),
   Y1_EC                NUMBER(8,3),
   Y2_EC                NUMBER(24),
   ESN_2                NUMBER(24),
   ERT_2                NUMBER(24),
   EHRS_2               NUMBER(24),
   ECYC_2               NUMBER(24),
   AP_2                 VARCHAR2(16),
   E                    NUMBER(8,3),
   MAX                  NUMBER(8,3),
   LIM                  NUMBER(8,3),
   TOL                  NUMBER(24),
   TTP                  NUMBER(24),
   TTF                  NUMBER(24),
   FF                   NUMBER(24),
   PD                   NUMBER(24),
   SM                   NUMBER(24),
   N1_S1                NUMBER(8,3),
   N2_S1                NUMBER(8,3),
   EGT_S1               NUMBER(8,3),
   FF_S1                NUMBER(24),
   FMV_S1               NUMBER(24),
   T25_S1               NUMBER(8,3),
   N1_S2                NUMBER(8,3),
   N1_S3                NUMBER(8,3),
   N2_S3                NUMBER(8,3),
   EGT_S3               NUMBER(8,3),
   FF_S3                NUMBER(24),
   FMV_S3               NUMBER(24),
   T25_S3               NUMBER(8,3),
   PD_S3                NUMBER(24),
   N1_S4                NUMBER(8,3),
   N2_S4                NUMBER(8,3),
   EGT_S4               NUMBER(8,3),
   FF_S4                NUMBER(24),
   FMV_S4               NUMBER(24),
   T25_S4               NUMBER(8,3),
   PD_S4                NUMBER(24),
   N1_S5                NUMBER(8,3),
   N2_S5                NUMBER(8,3),
   EGT_S5               NUMBER(8,3),
   FF_S5                NUMBER(24),
   FMV_S5               NUMBER(24),
   T25_S5               NUMBER(8,3),
   PD_S5                NUMBER(24),
   N1_S6                NUMBER(8,3),
   N2_S6                NUMBER(8,3),
   EGT_S6               NUMBER(8,3),
   FF_S6                NUMBER(24),
   FMV_S6               NUMBER(24),
   T25_S6               NUMBER(8,3),
   PD_S6                NUMBER(24),
   N1_S7                NUMBER(8,3),
   N2_S7                NUMBER(8,3),
   EGT_S7               NUMBER(8,3),
   FF_S7                NUMBER(24),
   FMV_S7               NUMBER(24),
   T25_S7               NUMBER(8,3),
   PD_S7                NUMBER(24),
   P3_T1                NUMBER(8,3),
   T3_T1                NUMBER(8,3),
   VSV_T1               NUMBER(8,3),
   VBV_T1               NUMBER(8,3),
   T5_T1                NUMBER(8,3),
   OIT_T1               NUMBER(24),
   ECW5_T1              VARCHAR2(16),
   P3_T2                NUMBER(8,3),
   T3_T2                NUMBER(8,3),
   VSV_T2               NUMBER(8,3),
   VBV_T2               NUMBER(8,3),
   T5_T2                NUMBER(8,3),
   OIT_T2               NUMBER(24),
   ECW5_T2              VARCHAR2(16),
   P3_T3                NUMBER(8,3),
   T3_T3                NUMBER(8,3),
   VSV_T3               NUMBER(8,3),
   VBV_T3               NUMBER(8,3),
   T5_T3                NUMBER(8,3),
   OIT_T3               NUMBER(24),
   ECW5_T3              VARCHAR2(16),
   P3_T4                NUMBER(8,3),
   T3_T4                NUMBER(8,3),
   VSV_T4               NUMBER(8,3),
   VBV_T4               NUMBER(8,3),
   T5_T4                NUMBER(8,3),
   OIT_T4               NUMBER(24),
   ECW5_T4              VARCHAR2(16),
   P3_T5                NUMBER(8,3),
   T3_T5                NUMBER(8,3),
   VSV_T5               NUMBER(8,3),
   VBV_T5               NUMBER(8,3),
   T5_T5                NUMBER(8,3),
   OIT_T5               NUMBER(24),
   ECW5_T5              VARCHAR2(16),
   P3_T6                NUMBER(8,3),
   T3_T6                NUMBER(8,3),
   VSV_T6               NUMBER(8,3),
   VBV_T6               NUMBER(8,3),
   T5_T6                NUMBER(8,3),
   OIT_T6               NUMBER(24),
   ECW5_T6              VARCHAR2(16),
   P3_T7                NUMBER(8,3),
   T3_T7                NUMBER(8,3),
   VSV_T7               NUMBER(8,3),
   VBV_T7               NUMBER(8,3),
   T5_T7                NUMBER(8,3),
   OIT_T7               NUMBER(24),
   ECW5_T7              VARCHAR2(16),
   N2_S2                NUMBER(8,3),
   EGT_S2               NUMBER(8,3),
   FF_S2                NUMBER(24),
   FMV_S2               NUMBER(24),
   T25_S2               NUMBER(8,3),
   PD_S2                NUMBER(24),
   PD_S1                NUMBER(24),
   UPDATE_DATE          DATE
);

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.ID IS
'系统编号';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.MSG_NO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.ACNUM IS
'飞机号';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.RPTDATE IS
'报文时间
2012-1-1 00:00:00';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.CODE IS
'发送编码';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.ESN_1 IS
'左发序号';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.EHRS_1 IS
'左发飞行小时';

COMMENT ON COLUMN A_DFD_A10CFM56_LIST.UPDATE_DATE IS
'时间';

/*==============================================================*/
/* Index: "INDEX_209"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_209" ON A_DFD_A10CFM56_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: "INDEX_210"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_210" ON A_DFD_A10CFM56_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: "INDEX_211"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_211" ON A_DFD_A10CFM56_LIST (
   RPTDATE ASC
);
