/*==============================================================*/
/* Index: IDX_A38LIST_MSGNO                                     */
/*==============================================================*/
CREATE INDEX IDX_A38LIST_MSGNO ON A_DFD_A38_LIST (
   MSG_NO ASC
);

/*==============================================================*/
/* Index: IDX_A38LIST_ACNUM                                     */
/*==============================================================*/
CREATE INDEX IDX_A38LIST_ACNUM ON A_DFD_A38_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_A38LIST_RPTDATE                                   */
/*==============================================================*/
CREATE INDEX IDX_A38LIST_RPTDATE ON A_DFD_A38_LIST (
   RPTDATE ASC
);

/*==============================================================*/
/* Index: IDX_A38LIST_ASN                                       */
/*==============================================================*/
CREATE INDEX IDX_A38LIST_ASN ON A_DFD_A38_LIST (
   ASN_E1 ASC
);


/*==============================================================*/
/* Index: IDX_ACARS_DFD_PARSETIME                               */
/*==============================================================*/
CREATE INDEX IDX_ACARS_DFD_PARSETIME ON A_ACARS_TELEGRAPH_DFD (
   PARSETIME ASC
);

/*==============================================================*/
/* Index: IDX_ACARS_CFD_PARSETIME                               */
/*==============================================================*/
CREATE INDEX IDX_ACARS_CFD_PARSETIME ON A_ACARS_TELEGRAPH_CFD (
   PARSETIME ASC
);


/*==============================================================*/
/* Index: IDX_MSGTEMPLATE_CODE                                  */
/*==============================================================*/
CREATE UNIQUE INDEX IDX_MSGTEMPLATE_CODE ON ALARM_MSGTEMPLATE (
   CODE ASC
);

/*==============================================================*/
/* Index: IDX_A23LIST_ACNUM                                     */
/*==============================================================*/
CREATE INDEX IDX_A23LIST_ACNUM ON A_DFD_A23_LIST (
   ACNUM ASC
);

/*==============================================================*/
/* Index: IDX_A23LIST_DATEUTC                                   */
/*==============================================================*/
CREATE INDEX IDX_A23LIST_DATEUTC ON A_DFD_A23_LIST (
   DATE_UTC ASC
);

/*==============================================================*/
/* Index: IDX_A14_LIST_ASN                                      */
/*==============================================================*/
CREATE INDEX IDX_A14_LIST_ASN ON A_DFD_A14_LIST (
   ASN ASC
);

/*==============================================================*/
/* Index: IDX_A14LIST_RPTDATE                                   */
/*==============================================================*/
CREATE INDEX IDX_A14LIST_RPTDATE ON A_DFD_A14_LIST (
   RPTDATE ASC
);
/*==============================================================*/
/* Index: IDX_A19LIST_RPTDATE                                   */
/*==============================================================*/
CREATE INDEX IDX_A19LIST_RPTDATE ON A_DFD_A19_LIST (
   RPTDATE ASC
);
/*==============================================================*/
/* Index: IDX_A21LIST_RPTDATE                                   */
/*==============================================================*/
CREATE INDEX IDX_A21LIST_RPTDATE ON A_DFD_A21_LIST (
   RPTDATE ASC
);
/*==============================================================*/
/* Index: IDX_A24LIST_RPTDATE                                   */
/*==============================================================*/
CREATE INDEX IDX_A24LIST_RPTDATE ON A_DFD_A24_LIST (
   RPTDATE ASC
);

/*==============================================================*/
/* Index: IDX_DATATASK_BEGINTIME                                   */
/*==============================================================*/
CREATE INDEX IDX_DATATASK_BEGINTIME ON PUB_DATATASK (
   BEGINTIME ASC
);
