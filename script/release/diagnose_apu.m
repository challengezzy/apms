%调用神经网络数据进行诊断新的APU报文是否有异常
clear,clc
close all
%从数据库中读取数据，最好每次不超过500条
conn = database('ORCL','apms151010','apms151010','oracle.jdbc.driver.OracleDriver','jdbc:oracle:thin:@121.197.15.177:1521:');
sql_q1 = 'SELECT C.EGTA_MAX_COR EGT,F1.V_STD EGT_STD, F1.V_K EGT_K,C.PT_MAX_COR,F2.V_STD PT_STD, F2.V_K PT_K,C.IGV_MAX' ;
sql_q2 = ',F3.V_STD IGV_STD,C.STA_V1,F4.V_STD STA_STD, F4.V_K STA_K,C.OT_V1,F5.V_STD OT_STD, F5.V_K OT_K,C.NPA_V1_COR,C.EGTP_V1_COR';
sql_q3 = '';
sql_q4 =',L.MSG_NO,L.DATE_UTC,L.ACNUM,L.ASN,C.PDI_OLD,C.PDI_NEW';
sql_q5 = ' FROM A_DFD_A13_COMPUTE C,A_DFD_A13_LIST L,B_APU A,B_APU_MODEL AM,A_DFD_FIELD_COMPUTE F1';
sql_q6 = ' ,A_DFD_FIELD_COMPUTE F2,A_DFD_FIELD_COMPUTE F3,A_DFD_FIELD_COMPUTE F4,A_DFD_FIELD_COMPUTE F5';
sql_q7 = ' WHERE C.MSG_NO = L.MSG_NO AND A.APUSN=L.ASN AND AM.ID=A.APUMODELID AND F1.MSG_NO=L.MSG_NO AND F1.F_NAME=''EGTA_MAX_COR'' AND F2.MSG_NO=L.MSG_NO ';
sql_q8 = ' AND F2.F_NAME=''PT_MAX_COR'' AND F3.MSG_NO=L.MSG_NO AND F3.F_NAME=''IGV_MAX'' AND F4.MSG_NO=L.MSG_NO AND F4.F_NAME=''STA_V1'' ';
sql_q9 = ' AND F5.MSG_NO=L.MSG_NO AND F5.F_NAME=''OT_MAX''';
%sql_q10 = ' AND L.MSG_NO=325909959 ';
sql_q10= ' AND L.ACNUM = ''B6032'' AND L.DATE_UTC>to_date(''20150726'',''YYYYMMDD'') AND L.DATE_UTC<to_date(''20150729'',''YYYYMMDD'')';
sql_q  = [sql_q1,sql_q2,sql_q3,sql_q4,sql_q5,sql_q6,sql_q7,sql_q8,sql_q9 ,sql_q10 ];

%setdbprefs('DataReturnFormat', 'numeric') %设置数据格式
curs = fetch(conn, sql_q);

msgnoData = cell2mat(curs(:,17));
msgnoData = msgnoData';

dbData = curs(:,1:16); 
toJudgeData = cell2mat(dbData); %从cell类型转换成矩阵类型
toJudgeData = toJudgeData';
%从文件中读取从神经网络数据
filepath = 'd:\apudata\apu_neuralnetwork';
load(filepath);
tic;
net=network(net);

%aa = net.numInputs
xx = mapminmax('apply',toJudgeData,s); %将测试输入模型
s = sim(net,xx); %对数据进行诊断测试

%显示结果
res=vec2ind(s); %将向量形式的分类结果表示为标量
res(1,:);%res表示的就是预测出属于那种类型的样本

%更新到数据库中
[rows,cols] = size(res);

tablename = 'L_APU_DIAGNOSERESULT';
colnames = {'MSG_NO','STATUS','DATATYPE','UPDATETIME'};

for n=1:cols
    msgno_n = msgnoData(1,n);
    type_n = res(1,n);
    expdata = {msgno_n,0,type_n, datestr(now,31) };
    fastinsert(conn, tablename, colnames, expdata); %更新到数据库中
    n = n+1;
end
close(conn);
%exit;












