insert into alarm_msgtemplate (ID, CODE, NAME, SUBJECT, CONTENT, TYPE, SMCONTENT, UPDATE_DATE, UPDATE_MAN, APPMODULE, ATA)
values (267, '�յ�Ѳ�������س�����Ϣ', '�յ�Ѳ�������س�����Ϣ', '�յ������س���', '{ACNUM}��{TIME},�յ�Ѳ��������{EX_COUNT}����������, {EXDETAIL}', 3, '{ACNUM}��{TIME},�յ�Ѳ��������{EX_COUNT}������,�ֱ�Ϊ{EXDETAIL}', to_date('17-04-2013 09:52:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'EX_LIMIT_ALARM', '00');

insert into alarm_rule (ID, NAME, CODE, MONITOROBJECTID, RULETYPE, RULEIMPCLASS, CONDITIONTYPE, CONDITIONCLAUSE, ACTIONTYPE, ACTIONALERTID, MSGTEMPLATEID, STATUS, COMMENTS, ATA, SEVERITY, VAL_COLUMN, VAL_COLUMN_NAME)
values (384, '�յ�Ѳ�������س��޸澯', '�յ�Ѳ�������س��޸澯', 184, 0, 'com.apms.bs.alarm.rule.impl.AlarmImplA21_ExtendLimit', null, '', 0, null, 267, 1, '', '00', 2, '', '');

commit;