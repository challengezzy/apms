CREATE INDEX IDX_FLTREPORT_REPORTTIME ON F_FLIGHTREPORT (
   REPORTTIME ASC
);

/*==============================================================*/
/* Index: IDX_USER_ORGIDLINE                                    */
/*==============================================================*/
CREATE INDEX IDX_USER_ORGIDLINE ON B_USER (
   ORGID_LINE ASC
);

/*==============================================================*/
/* Index: IDX_USER_ORGIDGROUP                                   */
/*==============================================================*/
CREATE INDEX IDX_USER_ORGIDGROUP ON B_USER (
   ORGID_GROUP ASC
);

