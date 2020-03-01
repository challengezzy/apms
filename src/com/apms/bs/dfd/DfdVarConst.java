package com.apms.bs.dfd;

public class DfdVarConst {
	
	//告警中参数对象的key
	public final static String KEY_HEADVO = "HEADVO";//解析后Head对象
	public final static String KEY_BODYVO = "BODYVO";//解析后Body对象
	public final static String KEY_COMPUTEDVO = "COMPUTEDVO";//计算后对象
	
	public final static int CHANGEPOINT_YES = 1;//是新起点
	public final static int CHANGEPOINT_NO = 0;//不是新起点

	//报文计算点类型
	public final static int POINTTYPE_REAL = 0;//实点
	public final static int POINTTYPE_FLOAT = 1;//飘点
	public final static int POINTTYPE_BAD = 2;//不良数据
	
	
	//当前S13标态压力与前一报文标态S46压力差状态
	public final static int DETAPRESSTATE_NOMARL = 0;//正常泄压
	public final static int DETAPRESSTATE_CHANGE = 1;//换瓶
	public final static int DETAPRESSTATE_LOWER = -1;//泄压门限
	
	
	//点事件类型
	public final static int POINTEVENTTYPE_INIT = 0;//0未标记 、 
	public final static int POINTEVENTTYPE_FAULT = 1;//1=故障数据
	public final static int POINTEVENTTYPE_MAINT = 2;//2=维修工作
	public final static int POINTEVENTTYPE_BAD = 3;//3-不良数据
	
	//独立样本T检验，告警类型
	public final static int TSMP_ALART_NOMARL = 0;//检查无差异
	public final static int TSMP_ALART_BAD = 1;//情况变坏
	public final static int TSMP_ALART_GOOD = -1;//情况变好
	
	//告警类型
	public final static int ALARMTULE_TYPE_CUSTOM = 0;//自定义处理
	public final static int ALARMTULE_TYPE_LIMIT=1;//门限告警
	public final static int ALARMTULE_TYPE_FLOATPOINT=2;//飘点告警
	public final static int ALARMTULE_TYPE_TOGGLE=3;//开关告警
	//告警规则激活状态
	public final static int ALARMTULE_STATUS_IGNORE = 0;//忽略
	public final static int ALARMTULE_STATUS_RAISED = 1;//激活
	
	public final static int ALARMTULEPARAM_TYPE_CONST = 0;//常量
	public final static int ALARMTULEPARAM_TYPE_TARGETVO = 1;//取自对象
	public final static int ALARMTULEPARAM_TYPE_FUN = 2;//函数
	
	
}
