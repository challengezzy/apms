ALTER TABLE B_AIRCRAFT ADD ISFLYLOG NUMBER(2) DEFAULT 0;
COMMENT ON COLUMN B_AIRCRAFT.ISFLYLOG IS '是否计算飞行日志,0-否,1-是';
CREATE INDEX IDX_AC_ISFLYLOG ON B_AIRCRAFT (ISFLYLOG ASC);

ALTER TABLE B_AIRCRAFT ADD ISDFDPARSE NUMBER(2) DEFAULT 0;
COMMENT ON COLUMN B_AIRCRAFT.ISDFDPARSE IS '是否处理DFD报文,0-否,1-是';
CREATE INDEX IDX_AC_ISDFDPARSE ON B_AIRCRAFT (ISDFDPARSE ASC);

ALTER TABLE B_AIRCRAFT ADD ISCFDPARSE NUMBER(2) DEFAULT 0;
COMMENT ON COLUMN B_AIRCRAFT.ISCFDPARSE IS '是否处理CFD报文,0-否,1-是';
CREATE INDEX IDX_AC_ISCFDPARSE ON B_AIRCRAFT (ISCFDPARSE ASC);

--索引名重建
DROP INDEX INDEX_87;
DROP INDEX INDEX_86;
DROP INDEX INDEX_85;
DROP INDEX INDEX_84;

/*==============================================================*/
/* Index: IDX_DFDHEAD_MSGNO                                     */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_DFDHEAD_MSGNO ON A_DFD_HEAD (
   MSG_NO ASC
);
/*==============================================================*/
/* Index: IDX_DFDHEAD_ACNUM                                     */
/*==============================================================*/
CREATE INDEX IDX_DFDHEAD_ACNUM ON A_DFD_HEAD (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_DFDHEAD_ACMODEL                                   */
/*==============================================================*/
CREATE INDEX IDX_DFDHEAD_ACMODEL ON A_DFD_HEAD (
   ACMODEL ASC
);

/*==============================================================*/
/* Index: IDX_DFDHEAD_RPTNO                                     */
/*==============================================================*/
CREATE INDEX IDX_DFDHEAD_RPTNO ON A_DFD_HEAD (
   RPTNO ASC
);

CREATE INDEX IDX_DFDHEAD_PH ON A_DFD_HEAD (
   PH ASC
);


DROP INDEX INDEX_200;

DROP INDEX INDEX_150;

DROP INDEX INDEX_149;

DROP INDEX INDEX_148;

DROP INDEX INDEX_141;

DROP INDEX INDEX_38;

DROP INDEX INDEX_37;


/*==============================================================*/
/* Index: IDX_ALARMMSG_ORGID                                    */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_ORGID ON ALARM_MESSAGE (
   ORGID ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_DISPATCHSTAT                             */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_DISPATCHSTAT ON ALARM_MESSAGE (
   DISPATHSTATUS ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_DEALSTATUS                               */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_DEALSTATUS ON ALARM_MESSAGE (
   DEALSTATUS ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_ACNUM                                    */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_ACNUM ON ALARM_MESSAGE (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_RPTNO                                    */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_RPTNO ON ALARM_MESSAGE (
   RPTNO ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_DEVICESN                                 */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_DEVICESN ON ALARM_MESSAGE (
   DEVICESN ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_PKVALUE                                  */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_PKVALUE ON ALARM_MESSAGE (
   DATAVIEWPK_VALUE ASC
);

/*==============================================================*/
/* Index: IDX_ALARMMSG_RPTDATE                                  */
/*==============================================================*/
CREATE INDEX IDX_ALARMMSG_RPTDATE ON ALARM_MESSAGE (
   RPTDATE ASC
);