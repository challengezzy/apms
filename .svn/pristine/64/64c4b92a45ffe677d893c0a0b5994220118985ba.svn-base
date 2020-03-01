DROP SEQUENCE S_L_APU_TRAINDATA;

CREATE SEQUENCE S_L_APU_TRAINDATA;
DROP INDEX IDX_APUTRAINDATA_ACNUM;

DROP INDEX IDX_APUTRAINDATA_RPTDATE;

DROP INDEX IDX_APUTRAINDATA_ASN;

ALTER TABLE L_APU_TRAINDATA
   DROP PRIMARY KEY CASCADE;

/*==============================================================*/
/* Table: L_APU_TRAINDATA                                       */
/*==============================================================*/
CREATE TABLE L_APU_TRAINDATA  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18),
   ASN                  VARCHAR2(255),
   ACNUM                VARCHAR2(255),
   RPTDATE              DATE,
   AMID                 NUMBER(18),
   ISVALID              NUMBER(2),
   DATATYPE             NUMBER(2),
   COMMENTS             VARCHAR2(511),
   UPDATETIME           DATE,
   UPDATEUSER           NUMBER(10)
);

COMMENT ON TABLE L_APU_TRAINDATA IS
'APU神经网络训练数据';

COMMENT ON COLUMN L_APU_TRAINDATA.ID IS
'ID';

COMMENT ON COLUMN L_APU_TRAINDATA.MSG_NO IS
'报文ID';

COMMENT ON COLUMN L_APU_TRAINDATA.ASN IS
'APU序号';

COMMENT ON COLUMN L_APU_TRAINDATA.ACNUM IS
'机号';

COMMENT ON COLUMN L_APU_TRAINDATA.RPTDATE IS
'报文时间';

COMMENT ON COLUMN L_APU_TRAINDATA.AMID IS
'APU型号';

COMMENT ON COLUMN L_APU_TRAINDATA.ISVALID IS
'是否有效0-无效,1-有效';

COMMENT ON COLUMN L_APU_TRAINDATA.DATATYPE IS
'数据分类';

COMMENT ON COLUMN L_APU_TRAINDATA.COMMENTS IS
'分类说明';

COMMENT ON COLUMN L_APU_TRAINDATA.UPDATETIME IS
'更新时间';

COMMENT ON COLUMN L_APU_TRAINDATA.UPDATEUSER IS
'更新人';

ALTER TABLE L_APU_TRAINDATA
   ADD CONSTRAINT PK_L_APU_TRAINDATA PRIMARY KEY (ID);

/*==============================================================*/
/* Index: IDX_APUTRAINDATA_ASN                                  */
/*==============================================================*/
CREATE INDEX IDX_APUTRAINDATA_ASN ON L_APU_TRAINDATA (
   ASN ASC
);

/*==============================================================*/
/* Index: IDX_APUTRAINDATA_RPTDATE                              */
/*==============================================================*/
CREATE INDEX IDX_APUTRAINDATA_RPTDATE ON L_APU_TRAINDATA (
   RPTDATE ASC
);

/*==============================================================*/
/* Index: IDX_APUTRAINDATA_ACNUM                                */
/*==============================================================*/
CREATE INDEX IDX_APUTRAINDATA_ACNUM ON L_APU_TRAINDATA (
   ACNUM ASC
);


DROP INDEX IDX_APUTRAINDATA_MSGNO;

/*==============================================================*/
/* Index: IDX_APUTRAINDATA_MSGNO                                */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_APUTRAINDATA_MSGNO ON L_APU_TRAINDATA (
   MSG_NO ASC
);


DROP SEQUENCE S_L_APU_DIAGNOSERESULT;

CREATE SEQUENCE S_L_APU_DIAGNOSERESULT;

DROP INDEX IDX_APUDIAGNOSE_MSGNO;

DROP INDEX IDX_APUDIAGNOSE_ACNUM;

DROP INDEX IDX_APUDIAGNOSE_RPTDATE;

DROP INDEX IDX_APUDIAGNOSE_ASN;

ALTER TABLE L_APU_DIAGNOSERESULT
   DROP PRIMARY KEY CASCADE;

/*==============================================================*/
/* Table: L_APU_DIAGNOSERESULT                                  */
/*==============================================================*/
CREATE TABLE L_APU_DIAGNOSERESULT  (
   ID                   NUMBER(18)                      NOT NULL,
   MSG_NO               NUMBER(18),
   ASN                  VARCHAR2(255),
   ACNUM                VARCHAR2(255),
   RPTDATE              DATE,
   STATUS               NUMBER(3),
   DATATYPE             NUMBER(2),
   COMMENTS             VARCHAR2(511),
   UPDATETIME           DATE,
   UPDATEUSER           NUMBER(10)
);

COMMENT ON TABLE L_APU_DIAGNOSERESULT IS
'APU神经网络训练诊断结果表';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.ID IS
'ID';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.MSG_NO IS
'报文ID';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.ASN IS
'APU序号';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.ACNUM IS
'机号';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.RPTDATE IS
'报文时间';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.STATUS IS
'状态0-已诊断,1-已处理';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.DATATYPE IS
'数据分类';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.COMMENTS IS
'分类说明';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.UPDATETIME IS
'更新时间';

COMMENT ON COLUMN L_APU_DIAGNOSERESULT.UPDATEUSER IS
'更新人';

ALTER TABLE L_APU_DIAGNOSERESULT
   ADD CONSTRAINT PK_L_APU_DIAGNOSERESULT PRIMARY KEY (ID);

/*==============================================================*/
/* Index: IDX_APUDIAGNOSE_ASN                                   */
/*==============================================================*/
CREATE INDEX IDX_APUDIAGNOSE_ASN ON L_APU_DIAGNOSERESULT (
   ASN ASC
);

/*==============================================================*/
/* Index: IDX_APUDIAGNOSE_RPTDATE                               */
/*==============================================================*/
CREATE INDEX IDX_APUDIAGNOSE_RPTDATE ON L_APU_DIAGNOSERESULT (
   RPTDATE ASC
);

/*==============================================================*/
/* Index: IDX_APUDIAGNOSE_ACNUM                                 */
/*==============================================================*/
CREATE INDEX IDX_APUDIAGNOSE_ACNUM ON L_APU_DIAGNOSERESULT (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_APUDIAGNOSE_MSGNO                                 */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_APUDIAGNOSE_MSGNO ON L_APU_DIAGNOSERESULT (
   MSG_NO ASC
);

DROP INDEX INDEX_133;

DROP INDEX INDEX_104;

DROP INDEX INDEX_103;

DROP INDEX INDEX_102;
/*==============================================================*/
/* Index: IDX_A13COMPUTE_MSGNO                                  */
/*==============================================================*/
CREATE INDEX IDX_A13COMPUTE_MSGNO ON A_DFD_A13_COMPUTE (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: IDX_A13COMPUTE_ACNUM                                  */
/*==============================================================*/
CREATE INDEX IDX_A13COMPUTE_ACNUM ON A_DFD_A13_COMPUTE (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_A13COMPUTE_UTCDATE                                */
/*==============================================================*/
CREATE INDEX IDX_A13COMPUTE_UTCDATE ON A_DFD_A13_COMPUTE (
   UTCDATE ASC
);

/*==============================================================*/
/* Index: IDX_A13COMPUTE_ISCHANGEPOINT                          */
/*==============================================================*/
CREATE INDEX IDX_A13COMPUTE_ISCHANGEPOINT ON A_DFD_A13_COMPUTE (
   ISCHANGEPOINT ASC
);

/*==============================================================*/
/* Index: IDX_A13COMPUTE_ASN                                    */
/*==============================================================*/
CREATE INDEX IDX_A13COMPUTE_ASN ON A_DFD_A13_COMPUTE (
   ASN ASC
);

ALTER TABLE A_DFD_A13_COMPUTE ADD NETFLAG NUMBER(2) DEFAULT 0;
COMMENT ON COLUMN A_DFD_A13_COMPUTE.NETFLAG IS '网络诊断标识0-未诊断 1-已诊断';
CREATE INDEX IDX_A13COMPUTE_NETFLAG ON A_DFD_A13_COMPUTE (
   NETFLAG ASC
);


