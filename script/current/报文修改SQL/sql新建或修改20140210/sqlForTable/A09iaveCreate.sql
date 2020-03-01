CREATE TABLE A_DFD_A09IAVE25_LIST  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18),
   ACNUM                VARCHAR2(16),
   RPTDATE              DATE,
   CODE                 VARCHAR2(16),
   ESN_1                VARCHAR2(16),
   EHRS_1               NUMBER(24),
   ECYC_1               NUMBER(24),
   AP_1                 VARCHAR2(16),
   SVA_1                NUMBER(10),
   BAF_1                VARCHAR2(16),
   ESN_2                VARCHAR2(16),
   EHRS_2               NUMBER(24),
   ECYC_2               NUMBER(24),
   AP_2                 VARCHAR2(16),
   SVA_2                NUMBER(10),
   BAF_2                VARCHAR2(16),
   E                    NUMBER(24),
   DIV                  NUMBER(24),
   REF                  NUMBER(24),
   K                    NUMBER(24),
   ECW3                 VARCHAR2(16),
   ECW4                 VARCHAR2(16),
   PARA                 NUMBER(24),
   EPR_S1               NUMBER(24),
   TN_S1                CHAR(10),
   EPRC_S1              CHAR(10),
   EGT_S1               NUMBER(24),
   N1_S1                NUMBER(24),
   N2_S1                NUMBER(24),
   FF_S1                NUMBER(24),
   UPDATE_DATE          DATE,
   EPR_S2               NUMBER(24),
   EGT_S2               NUMBER(24),
   N1_S2                NUMBER(24),
   N2_S2                NUMBER(24),
   FF_S2                NUMBER(24),
   TN_S2                CHAR(10),
   EPRC_S2              CHAR(10),
   EPR_S3               NUMBER(24),
   EGT_S3               NUMBER(24),
   N1_S3                NUMBER(24),
   N2_S3                NUMBER(24),
   FF_S3                NUMBER(24),
   TN_S3                CHAR(10),
   EPRC_S3              CHAR(10),
   EPR_S4               NUMBER(24),
   EGT_S4               NUMBER(24),
   N1_S4                NUMBER(24),
   N2_S4                NUMBER(24),
   FF_S4                NUMBER(24),
   TN_S4                CHAR(10),
   EPRC_S4              CHAR(10),
   EPR_S5               NUMBER(24),
   EGT_S5               NUMBER(24),
   N1_S5                NUMBER(24),
   N2_S5                NUMBER(24),
   FF_S5                NUMBER(24),
   TN_S5                CHAR(10),
   EPRC_S5              CHAR(10),
   EPR_S6               NUMBER(24),
   EGT_S6               NUMBER(24),
   N1_S6                NUMBER(24),
   N2_S6                NUMBER(24),
   FF_S6                NUMBER(24),
   TN_S6                CHAR(10),
   EPRC_S6              CHAR(10),
   EPR_S7               NUMBER(24),
   EGT_S7               NUMBER(24),
   N1_S7                NUMBER(24),
   N2_S7                NUMBER(24),
   FF_S7                NUMBER(24),
   TN_S7                CHAR(10),
   EPRC_S7              CHAR(10),
   EPR_T1               NUMBER(24),
   EGT_T1               NUMBER(24),
   N1_T1                NUMBER(24),
   N2_T1                NUMBER(24),
   FF_T1                NUMBER(24),
   TN_T1                CHAR(10),
   EPRC_T1              CHAR(10),
   EPR_T2               NUMBER(24),
   EGT_T2               NUMBER(24),
   N1_T2                NUMBER(24),
   N2_T2                NUMBER(24),
   FF_T2                NUMBER(24),
   TN_T2                CHAR(10),
   EPRC_T2              CHAR(10),
   EPR_T3               NUMBER(24),
   EGT_T3               NUMBER(24),
   N1_T3                NUMBER(24),
   N2_T3                NUMBER(24),
   FF_T3                NUMBER(24),
   TN_T3                CHAR(10),
   EPRC_T3              CHAR(10),
   EPR_T4               NUMBER(24),
   EGT_T4               NUMBER(24),
   N1_T4                NUMBER(24),
   N2_T4                NUMBER(24),
   FF_T4                NUMBER(24),
   TN_T4                CHAR(10),
   EPRC_T4              CHAR(10),
   EPR_T5               NUMBER(24),
   EGT_T5               NUMBER(24),
   N1_T5                NUMBER(24),
   N2_T5                NUMBER(24),
   FF_T5                NUMBER(24),
   TN_T5                CHAR(10),
   EPRC_T5              CHAR(10),
   EPR_T6               NUMBER(24),
   EGT_T6               NUMBER(24),
   N1_T6                NUMBER(24),
   N2_T6                NUMBER(24),
   FF_T6                NUMBER(24),
   TN_T6                CHAR(10),
   EPRC_T6              CHAR(10),
   EPR_T7               NUMBER(24),
   EGT_T7               NUMBER(24),
   N1_T7                NUMBER(24),
   N2_T7                NUMBER(24),
   FF_T7                NUMBER(24),
   TN_T7                CHAR(10),
   EPRC_T7              CHAR(10)
);

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.ID IS
'系统编号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.MSG_NO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.ACNUM IS
'飞机号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.RPTDATE IS
'报文时间
2012-1-1 00:00:00';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.CODE IS
'发送编码';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.ESN_1 IS
'左发序号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.EHRS_1 IS
'左发飞行小时';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.ECYC_1 IS
'左发循环数';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.AP_1 IS
'左发自动驾驶状态';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.SVA_1 IS
'发动机稳态数据量';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.BAF_1 IS
'右发序号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.ESN_2 IS
'左发序号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.EHRS_2 IS
'左发飞行小时';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.ECYC_2 IS
'左发循环数';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.AP_2 IS
'左发自动驾驶状态';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.SVA_2 IS
'发动机稳态数据量';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.BAF_2 IS
'右发序号';

COMMENT ON COLUMN A_DFD_A09IAVE25_LIST.UPDATE_DATE IS
'更新时间';

/*==============================================================*/
/* Index: "INDEX_206"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_206" ON A_DFD_A09IAVE25_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: "INDEX_207"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_207" ON A_DFD_A09IAVE25_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: "INDEX_208"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_208" ON A_DFD_A09IAVE25_LIST (
   RPTDATE ASC
);