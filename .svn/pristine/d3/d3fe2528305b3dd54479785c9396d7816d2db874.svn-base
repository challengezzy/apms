clear,clc
close all
conn = database('ORCL','apms151010','apms151010','oracle.jdbc.driver.OracleDriver','jdbc:oracle:thin:@121.197.15.177:1521:');
sql_q1 = 'SELECT D.DATATYPE,C.EGTA_MAX_COR EGT,F1.V_STD EGT_STD, F1.V_K EGT_K,C.PT_MAX_COR,F2.V_STD PT_STD, F2.V_K PT_K,C.IGV_MAX' ;
sql_q2 = ',F3.V_STD IGV_STD,C.STA_V1,F4.V_STD STA_STD, F4.V_K STA_K,C.OT_V1,F5.V_STD OT_STD, F5.V_K OT_K,C.NPA_V1_COR,C.EGTP_V1_COR';
sql_q3 = ',(SELECT AM.MODEL fROM B_APU A,B_APU_MODEL AM WHERE A.APUMODELID=AM.ID AND A.APUSN=L.ASN) APUMODEL';% 在第一列加入故障类型编号需要修改数据结构
sql_q4 =',L.MSG_NO,L.DATE_UTC,L.ACNUM,L.ASN,C.PDI_OLD,C.PDI_NEW';
sql_q5 = ' FROM A_DFD_A13_COMPUTE C,A_DFD_A13_LIST L,L_APU_TRAINDATA D,B_APU A,B_APU_MODEL AM,A_DFD_FIELD_COMPUTE F1,';
sql_q6 = 'A_DFD_FIELD_COMPUTE F2,A_DFD_FIELD_COMPUTE F3,A_DFD_FIELD_COMPUTE F4,A_DFD_FIELD_COMPUTE F5 WHERE C.MSG_NO = L.MSG_NO AND D.MSG_NO = L.MSG_NO ';
sql_q7 = 'AND A.APUSN=L.ASN AND AM.ID=A.APUMODELID AND F1.MSG_NO=L.MSG_NO AND F1.F_NAME=''EGTA_MAX_COR'' AND F2.MSG_NO=L.MSG_NO AND ';
sql_q8 = 'F2.F_NAME=''PT_MAX_COR'' AND F3.MSG_NO=L.MSG_NO AND F3.F_NAME=''IGV_MAX'' AND F4.MSG_NO=L.MSG_NO AND F4.F_NAME=''STA_V1'' AND F5.MSG_NO=L.MSG_NO ';
sql_q9 = 'AND F5.F2_NAME=''OT_MAX''';
sql_q  = [sql_q1,sql_q2,sql_q3,sql_q4,sql_q5,sql_q6,sql_q7,sql_q8,sql_q9];
curs = fetch(conn, sql_q);
trainx=curs(:,2:17); %设置训练样本,16列字段
trainx=trainx';
% zhangzy 这里如何利用数据库中已经分类的数据
labNo=curs(:,1);
labNo=labNo'; %故障类型编号 应该是从数据库获取出来的，这里手输 1-4 是 MAP 定义的对故障类型进行设置，工程师针对故障进行人工分类 分类编号必须从1开始不能负数和0
[x0,s]=mapminmax(trainx); %归一化处理
tic; %记录计算机开始时间
spread=1;  %为参数平滑因子  可调 这里默认为1
net = newpnn(x0,ind2vec(labNo),spread); %创建径向基神经网络
save d:\apudata\apu_neuralnetwork net s labNo% 保存神经网络数据成文件

close(conn); %关闭数据库连接
toc;


