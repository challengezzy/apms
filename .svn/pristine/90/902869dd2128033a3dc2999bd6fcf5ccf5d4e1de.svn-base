ALTER TABLE A_ACARS_TELEGRAPH_DFD
   DROP PRIMARY KEY CASCADE;

DROP TABLE A_ACARS_TELEGRAPH_DFD CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: A_ACARS_TELEGRAPH_DFD                                 */
/*==============================================================*/
CREATE TABLE A_ACARS_TELEGRAPH_DFD  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18)                      NOT NULL,
   TEL_CONTENT          VARCHAR2(4000),
   RPTNO                VARCHAR2(32),
   AC_ID                VARCHAR2(15),
   MODELCODE            VARCHAR2(64),
   MODELSERIES          VARCHAR2(64),
   ERRINT               NUMBER(2),
   ERRMESSAGE           VARCHAR2(4000),
   FDIMUVERSION         VARCHAR2(64),
   PREFIX               VARCHAR2(32),
   TRANS_TIME           DATE,
   RECORD_TIME          DATE,
   PARSETIME            DATE
);

COMMENT ON TABLE A_ACARS_TELEGRAPH_DFD IS
'DFD报文数据';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.ID IS
'ID';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.MSG_NO IS
'消息编号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.TEL_CONTENT IS
'报文内容';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.RPTNO IS
'报文编号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.AC_ID IS
'飞机号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.MODELCODE IS
'机型';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.MODELSERIES IS
'机型系列';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.ERRINT IS
'解析结果标识';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.ERRMESSAGE IS
'错误消息';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.FDIMUVERSION IS
'FDIMU版本号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.PREFIX IS
'报文前缀，代表报文类型';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.TRANS_TIME IS
'传送时间';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.RECORD_TIME IS
'记录时间';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_DFD.PARSETIME IS
'解析时间';

ALTER TABLE A_ACARS_TELEGRAPH_DFD
   ADD CONSTRAINT PK_A_ACARS_TELEGRAPH_DFD PRIMARY KEY (ID);

/*==============================================================*/
/* Index: IDX_ACARS_DFD_ERRINT                                  */
/*==============================================================*/
CREATE INDEX IDX_ACARS_DFD_ERRINT ON A_ACARS_TELEGRAPH_DFD (
   ERRINT ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_DFD_MSGNO                                   */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_ACARS_DFD_MSGNO ON A_ACARS_TELEGRAPH_DFD (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_DFD_ACID                                    */
/*==============================================================*/
CREATE INDEX IDX_ACARS_DFD_ACID ON A_ACARS_TELEGRAPH_DFD (
   AC_ID ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_DFD_RPTNO                                   */
/*==============================================================*/
CREATE INDEX IDX_ACARS_DFD_RPTNO ON A_ACARS_TELEGRAPH_DFD (
   RPTNO ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_DFD_TRANSTIME                               */
/*==============================================================*/
CREATE INDEX IDX_ACARS_DFD_TRANSTIME ON A_ACARS_TELEGRAPH_DFD (
   TRANS_TIME ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_DFD_MODELCODE                               */
/*==============================================================*/
CREATE INDEX IDX_ACARS_DFD_MODELCODE ON A_ACARS_TELEGRAPH_DFD (
   MODELCODE ASC
);



ALTER TABLE A_ACARS_TELEGRAPH_CFD
   DROP PRIMARY KEY CASCADE;

DROP TABLE A_ACARS_TELEGRAPH_CFD CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: A_ACARS_TELEGRAPH_CFD                                 */
/*==============================================================*/
CREATE TABLE A_ACARS_TELEGRAPH_CFD  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18)                      NOT NULL,
   TEL_CONTENT          VARCHAR2(4000),
   RPTNO                VARCHAR2(32),
   AC_ID                VARCHAR2(7),
   FLT_ID               VARCHAR2(6),
   MODELCODE            VARCHAR2(64),
   MODELSERIES          VARCHAR2(64),
   ERRINT               NUMBER(2),
   ERRMESSAGE           VARCHAR2(4000),
   PREFIX               VARCHAR2(32),
   TRANS_TIME           DATE,
   RECORD_TIME          DATE,
   PARSETIME            DATE
);

COMMENT ON TABLE A_ACARS_TELEGRAPH_CFD IS
'CFD报文数据';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.ID IS
'ID';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.MSG_NO IS
'消息编号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.TEL_CONTENT IS
'报文内容';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.RPTNO IS
'报文编号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.AC_ID IS
'飞机号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.FLT_ID IS
'航班号';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.MODELCODE IS
'机型';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.MODELSERIES IS
'机型系列';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.ERRINT IS
'解析结果标识';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.ERRMESSAGE IS
'错误消息';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.PREFIX IS
'报文前缀，代表报文类型';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.TRANS_TIME IS
'传送时间';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.RECORD_TIME IS
'记录时间';

COMMENT ON COLUMN A_ACARS_TELEGRAPH_CFD.PARSETIME IS
'解析时间';

ALTER TABLE A_ACARS_TELEGRAPH_CFD
   ADD CONSTRAINT PK_A_ACARS_TELEGRAPH_CFD PRIMARY KEY (ID);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_ERRINT                                  */
/*==============================================================*/
CREATE INDEX IDX_ACARS_CFD_ERRINT ON A_ACARS_TELEGRAPH_CFD (
   ERRINT ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_MSGNO                                   */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_ACARS_CFD_MSGNO ON A_ACARS_TELEGRAPH_CFD (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_ACID                                    */
/*==============================================================*/
CREATE INDEX IDX_ACARS_CFD_ACID ON A_ACARS_TELEGRAPH_CFD (
   AC_ID ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_RPTNO                                   */
/*==============================================================*/
CREATE INDEX IDX_ACARS_CFD_RPTNO ON A_ACARS_TELEGRAPH_CFD (
   RPTNO ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_TRANSTIME                               */
/*==============================================================*/
CREATE INDEX IDX_ACARS_CFD_TRANSTIME ON A_ACARS_TELEGRAPH_CFD (
   TRANS_TIME ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_MODELCODE                               */
/*==============================================================*/
CREATE INDEX IDX_ACARS_CFD_MODELCODE ON A_ACARS_TELEGRAPH_CFD (
   MODELCODE ASC
);


--发动机型号添加父子约束
ALTER TABLE B_ENGINE_MODEL
   DROP CONSTRAINT FK_ENGINE_MO_R_B_ENGINE_PARENT;

ALTER TABLE B_ENGINE_MODEL
   ADD CONSTRAINT FK_ENGINE_MO_R_B_ENGINE_PARENT FOREIGN KEY (PARENTID)
      REFERENCES B_ENGINE_MODEL (ID);