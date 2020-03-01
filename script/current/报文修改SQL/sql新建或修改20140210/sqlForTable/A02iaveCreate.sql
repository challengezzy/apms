CREATE TABLE A_DFD_A02IAEV25_LIST  (
   CIVV_2               NUMBER(8,3),
   FD_2                 NUMBER(8,3),
   FD_1                 NUMBER(8,3),
   FT_2                 NUMBER(8,3),
   FT_1                 NUMBER(8,3),
   WD_2                 NUMBER(8,3),
   WD_1                 NUMBER(8,3),
   WS_2                 NUMBER(8,3),
   WS_1                 NUMBER(8,3),
   LATP_2               VARCHAR2(16),
   LATP_1               VARCHAR2(16),
   LONP_2               VARCHAR2(16),
   LONP_1               VARCHAR2(16),
   THDG_2               NUMBER(8,3),
   THDG_1               NUMBER(8,3),
   SLAT_2               VARCHAR2(24),
   SLAT_1               VARCHAR2(24),
   FLAP_2               VARCHAR2(16),
   FLAP_1               VARCHAR2(16),
   RSP5_2               NUMBER(8,3),
   RSP5_1               NUMBER(8,3),
   RSP4_2               NUMBER(8,3),
   RSP4_1               NUMBER(8,3),
   RSP3_2               NUMBER(8,3),
   RSP3_1               NUMBER(8,3),
   RSP2_2               NUMBER(8,3),
   RSP2_1               NUMBER(8,3),
   STAB                 VARCHAR2(16),
   ROLL                 VARCHAR2(16),
   AILR                 VARCHAR2(16),
   RUDD                 VARCHAR2(16),
   CIVV_1               NUMBER(8,3),
   CFPG_2               NUMBER(8,3),
   CFPG_1               NUMBER(8,3),
   SLP_2                NUMBER(8,3),
   SLP_1                NUMBER(8,3),
   AOA_2                NUMBER(8,3),
   AOA_1                NUMBER(8,3),
   ELEV_2               NUMBER(8,3),
   ELEV_1               NUMBER(8,3),
   WFQ_2                NUMBER(8,3),
   CIVV_3               NUMBER(8,3),
   CIVV_4               NUMBER(8,3),
   CFPG_3               NUMBER(8,3),
   CFPG_4               NUMBER(8,3),
   SLP_3                NUMBER(8,3),
   SLP_4                NUMBER(8,3),
   AOA_3                NUMBER(8,3),
   AOA_4                NUMBER(8,3),
   ELEV_3               NUMBER(8,3),
   ELEV_4               NUMBER(8,3),
   WFQ_3                NUMBER(8,3),
   WFQ_4                NUMBER(8,3),
   YAW                  VARCHAR2(16),
   AILL                 VARCHAR2(16),
   RUDT                 VARCHAR2(16),
   WFQ_1                NUMBER(8,3),
   ID                   NUMBER(18),
   MSG_NO               NUMBER(18),
   ACNUM                VARCHAR2(16),
   RPTDATE              DATE,
   CODE                 VARCHAR2(16),
   ESN_1                VARCHAR2(16),
   EHRS_1               NUMBER(24),
   ERT_1                NUMBER(24),
   ECYC_1               NUMBER(24),
   AP_1                 VARCHAR2(16),
   QA_EC                NUMBER(10),
   QE_EC                NUMBER(10),
   ESN_2                VARCHAR2(16),
   EHRS_2               NUMBER(24),
   ERT_2                NUMBER(24),
   ECYC_2               NUMBER(24),
   AP_2                 VARCHAR2(16),
   EPR_1                NUMBER(8,3),
   EPRC_1               NUMBER(8,3),
   EGT_1                NUMBER(8,3),
   N1_1                 NUMBER(8,3),
   N2_1                 NUMBER(8,3),
   FF_1                 NUMBER(8,3),
   P125_1               NUMBER(8,3),
   EPR_2                NUMBER(8,3),
   EPRC_2               NUMBER(8,3),
   EGT_2                NUMBER(8,3),
   N1_2                 NUMBER(8,3),
   N2_2                 NUMBER(8,3),
   FF_2                 NUMBER(8,3),
   P125_2               NUMBER(8,3),
   P25_1                NUMBER(8,3),
   T25_1                NUMBER(8,3),
   P3_1                 NUMBER(8,3),
   T3_1                 NUMBER(8,3),
   P25_2                NUMBER(8,3),
   T25_2                NUMBER(8,3),
   P3_2                 NUMBER(8,3),
   T3_2                 NUMBER(8,3),
   P49_1                NUMBER(8,3),
   SVA_1                NUMBER(8,3),
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
   BAF_2                NUMBER(8,3),
   ACC_2                NUMBER(8,3),
   LP_2                 NUMBER(8,3),
   GLE_2                NUMBER(8,3),
   PD_2                 NUMBER(8,3),
   TN_2                 NUMBER(8,3),
   P2_2                 NUMBER(8,3),
   T2_2                 NUMBER(8,3),
   ECW1_1               VARCHAR2(16),
   ECW2_1               VARCHAR2(16),
   EVM_1                VARCHAR2(16),
   OIP_1                NUMBER(8,3),
   OIT_1                NUMBER(8,3),
   OIQH_1               NUMBER(8,3),
   ECW1_2               VARCHAR2(16),
   ECW2_2               VARCHAR2(16),
   EVM_2                VARCHAR2(16),
   OIP_2                NUMBER(8,3),
   OIT_2                NUMBER(8,3),
   OIQH_2               NUMBER(8,3),
   VB1_1                NUMBER(8,3),
   VB2_1                NUMBER(8,3),
   PHA_1                NUMBER(8,3),
   VB1_2                NUMBER(8,3),
   VB2_2                NUMBER(8,3),
   PHA_2                NUMBER(8,3),
   UPDATE_DATE          DATE
);

COMMENT ON TABLE A_DFD_A02IAEV25_LIST IS
'IAEV25发动机 巡航报文  A01，只针对只针对V25型号';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ID IS
'系统编号';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.MSG_NO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ACNUM IS
'飞机号';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.RPTDATE IS
'报文时间
2012-1-1 00:00:00';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.CODE IS
'发送编码';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ESN_1 IS
'左发序号';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EHRS_1 IS
'左发飞行小时';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ERT_1 IS
'左发运行小时';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ECYC_1 IS
'左发循环数';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.AP_1 IS
'左发自动驾驶状态';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.QA_EC IS
'发动机稳态数据量';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.QE_EC IS
'发动机稳态数据量';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ESN_2 IS
'右发序号';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EHRS_2 IS
'右发飞行小时';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ERT_2 IS
'右发运行小时';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ECYC_2 IS
'右发循环数';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.AP_2 IS
'右发自动驾驶状态';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EPR_1 IS
'左发推力比';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EPRC_1 IS
'左发发动机推力指令';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EGT_1 IS
'左发排气温度';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.N1_1 IS
'左发N1转速';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.N2_1 IS
'左发N2转速';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.FF_1 IS
'左发燃油流量';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P125_1 IS
'左发P12.5压力';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EPR_2 IS
'右发推力比';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EPRC_2 IS
'右发推力指令';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EGT_2 IS
'右发排气温度';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.N1_2 IS
'右发N1转速';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.N2_2 IS
'右发N2转速';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.FF_2 IS
'右发燃油流量';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P125_2 IS
'右发P12.5压力';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P25_1 IS
'左发2.5位置的静态压力 XX.XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.T25_1 IS
'左发2.5位置的温度 XXX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P3_1 IS
'左发3 位置的静态压力 XXX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.T3_1 IS
'左发3 位置的温度 XXX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P25_2 IS
'右发2.5位置的静态压力 XX.XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.T25_2 IS
'右发2.5位置的温度 XXX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P3_2 IS
'右发3 位置的静态压力 XXX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.T3_2 IS
'右发3 位置的温度 XXX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P49_1 IS
'左发 P49的压力 XX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.SVA_1 IS
'左发静止叶片开度 XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P49_2 IS
'右发P49的压力 XX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.SVA_2 IS
'右发静止叶片开度 XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.BAF_1 IS
'左发2.5级放气活门开度 XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ACC_1 IS
'左发ACC开度 XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.LP_1 IS
'左发低压涡轮间隙控制状态 1=CLOSE';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.GLE_1 IS
'左发发电机负载XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.PD_1 IS
'左发预冷器进口压力XX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.TN_1 IS
'左发吊架温度 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P2_1 IS
'左发P2 总压 XX.XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.T2_1 IS
'左发T2 温度 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.BAF_2 IS
'右发2.5级放气活门开度 XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ACC_2 IS
'右发ACC开度 XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.LP_2 IS
'右发低压涡轮间隙控制状态 1=CLOSE';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.GLE_2 IS
'右发发电机负载XXX%';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.PD_2 IS
'右发预冷器进口压力XX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.TN_2 IS
'右发吊架温度 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.P2_2 IS
'右发P2 总压 XX.XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.T2_2 IS
'右发T2 温度 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ECW1_1 IS
'左发告警控制字 XXXXX 需要解码1';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ECW2_1 IS
'左发告警控制字 XXXXX 需要解码2';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EVM_1 IS
'左发EVMU控制字，需要解码';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.OIP_1 IS
'左发滑油压力XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.OIT_1 IS
'左发滑油温度XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.OIQH_1 IS
'左发上段滑油消耗量 XX.XX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ECW1_2 IS
'右发告警控制字 XXXXX 需要解码1';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.ECW2_2 IS
'右发告警控制字 XXXXX 需要解码2';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.EVM_2 IS
'右EVMU控制字，需要解码';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.OIP_2 IS
'右发滑油压力 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.OIT_2 IS
'右发滑油温度 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.OIQH_2 IS
'右发上段滑油消耗量 XX.XX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.VB1_1 IS
'左发N1抖动值 XX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.VB2_1 IS
'左发N2抖动值 XX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.PHA_1 IS
'左发抖动相位角 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.VB1_2 IS
'右发N1抖动值 XX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.VB2_2 IS
'右发N2抖动值 XX.X';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.PHA_2 IS
'右发抖动相位角 XXX';

COMMENT ON COLUMN A_DFD_A02IAEV25_LIST.UPDATE_DATE IS
'更新时间';

/*==============================================================*/
/* Index: "INDEX_194"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_194" ON A_DFD_A02IAEV25_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: "INDEX_195"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_195" ON A_DFD_A02IAEV25_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: "INDEX_196"                                           */
/*==============================================================*/
CREATE INDEX "INDEX_196" ON A_DFD_A02IAEV25_LIST (
   RPTDATE ASC
);