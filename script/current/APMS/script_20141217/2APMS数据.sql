insert into alarm_msgtemplate (ID, CODE, NAME, SUBJECT, CONTENT, TYPE, SMCONTENT, UPDATE_DATE, UPDATE_MAN, APPMODULE, ATA)
values (267, '空调巡航参数池超限消息', '空调巡航参数池超限消息', '空调参数池超限', '{ACNUM}在{TIME},空调巡航报文有{EX_COUNT}个参数超限, {EXDETAIL}', 3, '{ACNUM}在{TIME},空调巡航报文有{EX_COUNT}个超限,分别为{EXDETAIL}', to_date('17-04-2013 09:52:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'EX_LIMIT_ALARM', '00');

insert into alarm_rule (ID, NAME, CODE, MONITOROBJECTID, RULETYPE, RULEIMPCLASS, CONDITIONTYPE, CONDITIONCLAUSE, ACTIONTYPE, ACTIONALERTID, MSGTEMPLATEID, STATUS, COMMENTS, ATA, SEVERITY, VAL_COLUMN, VAL_COLUMN_NAME)
values (384, '空调巡航参数池超限告警', '空调巡航参数池超限告警', 184, 0, 'com.apms.bs.alarm.rule.impl.AlarmImplA21_ExtendLimit', null, '', 0, null, 267, 1, '', '00', 2, '', '');

commit;