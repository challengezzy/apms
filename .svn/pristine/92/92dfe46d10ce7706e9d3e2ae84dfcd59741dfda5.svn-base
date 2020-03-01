CREATE OR REPLACE VIEW V_DEFECTREMIND AS
SELECT --提醒单编辑查询视图
       ID,
       FLT_NO,
       ACNUM,
       ACMODEL,
       ATA_NO,
       RISKLEVEL,
       TIPTYPE,
       STARTTIME,
       ENDTIME,
       STATUS,
       REMINDDESC,
       REMINDDESC RDMINDDESC_OLD,
       FEEDBACK,
       SOURCE,
       CREATOR,
       CREATOR_DEPT,
       ISNEEDSUPPORT,
       LOOPTYPE,
       LOOPINTERVAL,
       WEEKDAY_0,WEEKDAY_1,WEEKDAY_2,WEEKDAY_3,WEEKDAY_4,WEEKDAY_5,WEEKDAY_6,
       COMMENTS,
       TO_CHAR(starttime,'YYYY-MM-DD') starttime_str,TO_CHAR(endtime,'YYYY-MM-DD') endtime_str
       ,(select d.valuecn from b_dictionary d where d.classname='W_DEFECTREMIND' AND d.attributename='STATUS'
                and d.value = t.status ) status_name
       ,(select d.valuecn from b_dictionary d where d.classname='W_DEFECTREMIND' AND d.attributename='TIPTYPE'
                and d.value = t.tiptype ) tiptype_name
       ,(select d.valuecn from b_dictionary d where d.classname='W_DEFECTREMIND' AND d.attributename='RISKLEVEL'
                and d.value = t.risklevel ) risklevel_name
       ,(select d.valuecn from b_dictionary d where d.classname='W_DEFECTREMIND' AND d.attributename='SOURCE'
                and d.value = t.source ) source_name,
       UPDATETIME,
       UPDATEUSER
  FROM W_DEFECTREMIND T;
 
 