--删除表
DROP TABLE A_DFD_A15A16_LIST;

ALTER TABLE A_DFD_A15A16_LIST
   DROP PRIMARY KEY CASCADE;

DROP TABLE A_DFD_A15A16_LIST CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: A_DFD_A15A16_LIST                                     */
/*==============================================================*/
CREATE TABLE A_DFD_A15A16_LIST  (
   ID                   NUMBER(18)                      NOT NULL,
   RPTNO                VARCHAR2(63),
   MSG_NO               NUMBER(18),
   ACNUM                VARCHAR2(32),
   DATE_UTC             DATE,
   COUNT                NUMBER(18,2),
   GW                   NUMBER(18,2),
   ESN_1                VARCHAR2(20),
   EHRS_1               NUMBER(18),
   AP_1                 VARCHAR2(20),
   FLAP_1               NUMBER(18,3),
   SLAT_1               NUMBER(18,3),
   ESN_2                VARCHAR2(20),
   EHRS_2               NUMBER(18),
   AP_2                 VARCHAR2(20),
   FLAP_2               NUMBER(18,3),
   SLAT_2               NUMBER(18,3),
   MAX_E1               NUMBER(18,2),
   LIM_E1               NUMBER(18,2),
   RALT_S1              NUMBER(18,2),
   RALR_S1              NUMBER(18,2),
   PTCH_S1              NUMBER(18,2),
   PTCR_S1              NUMBER(18,2),
   ROLL_S1              NUMBER(18,2),
   ROLR_S1              NUMBER(18,2),
   YAW_S1               NUMBER(18,2),
   RALT_S2              NUMBER(18,2),
   RALR_S2              NUMBER(18,2),
   PTCH_S2              NUMBER(18,2),
   PTCR_S2              NUMBER(18,2),
   ROLL_S2              NUMBER(18,2),
   ROLR_S2              NUMBER(18,2),
   YAW_S2               NUMBER(18,2),
   VRTA_S3              NUMBER(18,2),
   LONA_S3              NUMBER(18,2),
   LATA_S3              NUMBER(18,2),
   RALR_S3              NUMBER(18,2),
   VRTA_S4              NUMBER(18,2),
   LONA_S4              NUMBER(18,2),
   LATA_S4              NUMBER(18,2),
   RALR_S4              NUMBER(18,2),
   RALT_T1              NUMBER(18,2),
   RALR_T1              NUMBER(18,2),
   PTCH_T1              NUMBER(18,2),
   PTCR_T1              NUMBER(18,2),
   ROLL_T1              NUMBER(18,2),
   ROLR_T1              NUMBER(18,2),
   YAW_T1               NUMBER(18,2),
   RALT_T2              NUMBER(18,2),
   RALR_T2              NUMBER(18,2),
   PTCH_T2              NUMBER(18,2),
   PTCR_T2              NUMBER(18,2),
   ROLL_T2              NUMBER(18,2),
   ROLR_T2              NUMBER(18,2),
   YAW_T2               NUMBER(18,2),
   VRTA_T3              NUMBER(18,2),
   LONA_T3              NUMBER(18,2),
   LATA_T3              NUMBER(18,2),
   VRTA_T4              NUMBER(18,2),
   LONA_T4              NUMBER(18,2),
   LATA_T4              NUMBER(18,2),
   UPDATE_DATE          DATE
);

COMMENT ON TABLE A_DFD_A15A16_LIST IS
'飞机重着落';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ID IS
'系统编号';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RPTNO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A15A16_LIST.MSG_NO IS
'报文编号';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ACNUM IS
'飞机号';

COMMENT ON COLUMN A_DFD_A15A16_LIST.DATE_UTC IS
'报文时间';

COMMENT ON COLUMN A_DFD_A15A16_LIST.COUNT IS
'LAF探测空中颠簸>999认为空中颠簸';

COMMENT ON COLUMN A_DFD_A15A16_LIST.GW IS
'总重';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ESN_1 IS
'EC_ESN 左发动机序号';

COMMENT ON COLUMN A_DFD_A15A16_LIST.EHRS_1 IS
'EC_ECRS左发发动机小时 99999';

COMMENT ON COLUMN A_DFD_A15A16_LIST.AP_1 IS
'EC_AP  AP1自动驾驶模式 9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.FLAP_1 IS
'EC_FLAP 左襟翼位置 角度 ';

COMMENT ON COLUMN A_DFD_A15A16_LIST.SLAT_1 IS
'EC_SLAT 左缝翼位置 角度';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ESN_2 IS
'EE_ESN 右发动机序号';

COMMENT ON COLUMN A_DFD_A15A16_LIST.EHRS_2 IS
'EE_ECRS右发发动机小时';

COMMENT ON COLUMN A_DFD_A15A16_LIST.AP_2 IS
'EE_AP  AP2自动驾驶模式';

COMMENT ON COLUMN A_DFD_A15A16_LIST.FLAP_2 IS
'EE_FLAP 右襟翼位置 角度';

COMMENT ON COLUMN A_DFD_A15A16_LIST.SLAT_2 IS
'EE_SLAT 左缝翼位置 角度';

COMMENT ON COLUMN A_DFD_A15A16_LIST.MAX_E1 IS
'从飞机落地前0.5秒至落地后0.5秒内的超过限制的最大值 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LIM_E1 IS
'超过限制值的 限制门限值 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALT_S1 IS
'无线电高度 X99.9 英尺';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALR_S1 IS
'无线电高度下降率 X99.9 英尺/秒';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCH_S1 IS
'俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCR_S1 IS
'机身俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLL_S1 IS
'滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLR_S1 IS
'机身滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.YAW_S1 IS
'偏航角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALT_S2 IS
'无线电高度 X99.9 英尺';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALR_S2 IS
'无线电高度下降率 X99.9 英尺/秒';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCH_S2 IS
'俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCR_S2 IS
'机身俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLL_S2 IS
'滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLR_S2 IS
'机身滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.YAW_S2 IS
'偏航角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.VRTA_S3 IS
'落地时最大加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LONA_S3 IS
'落地时最大纵向加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LATA_S3 IS
'落地时最大横向加速度 X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALR_S3 IS
'落地时最大无线电高度下降率 X99.9 英尺/秒';

COMMENT ON COLUMN A_DFD_A15A16_LIST.VRTA_S4 IS
'落地时最小加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LONA_S4 IS
'落地时最小纵向加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LATA_S4 IS
'落地时最小横向加速度 X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALR_S4 IS
'落地时最小无线电高度下降率 X99.9 英尺/秒';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALT_T1 IS
'无线电高度 X99.9 英尺';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALR_T1 IS
'无线电高度下降率 X99.9 英尺/秒';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCH_T1 IS
'俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCR_T1 IS
'机身俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLL_T1 IS
'滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLR_T1 IS
'机身滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.YAW_T1 IS
'偏航角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALT_T2 IS
'无线电高度 X99.9 英尺';

COMMENT ON COLUMN A_DFD_A15A16_LIST.RALR_T2 IS
'无线电高度下降率 X99.9 英尺/秒';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCH_T2 IS
'俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.PTCR_T2 IS
'机身俯仰角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLL_T2 IS
'滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.ROLR_T2 IS
'机身滚转角度X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.YAW_T2 IS
'偏航角度 X99.9';

COMMENT ON COLUMN A_DFD_A15A16_LIST.VRTA_T3 IS
'落地时最大加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LONA_T3 IS
'落地时最大纵向加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LATA_T3 IS
'落地时最大横向加速度 X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.VRTA_T4 IS
'落地时最小加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LONA_T4 IS
'落地时最小纵向加速度  X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.LATA_T4 IS
'落地时最小横向加速度 X9.99';

COMMENT ON COLUMN A_DFD_A15A16_LIST.UPDATE_DATE IS
'更新时间';

ALTER TABLE A_DFD_A15A16_LIST
   ADD CONSTRAINT PK_A_DFD_A15A16_LIST PRIMARY KEY (ID);

/*==============================================================*/
/* Index: IDX_A1516LIST_MSGNO                                   */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_A1516LIST_MSGNO ON A_DFD_A15A16_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: IDX_A1516LIST_ACNUM                                   */
/*==============================================================*/
CREATE INDEX IDX_A1516LIST_ACNUM ON A_DFD_A15A16_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_A1516LIST_DATEUTC                                 */
/*==============================================================*/
CREATE INDEX IDX_A1516LIST_DATEUTC ON A_DFD_A15A16_LIST (
   DATE_UTC ASC
);

/*==============================================================*/
/* Index: IDX_A1516LIST_RPTNO                                   */
/*==============================================================*/
CREATE INDEX IDX_A1516LIST_RPTNO ON A_DFD_A15A16_LIST (
   RPTNO ASC
);
