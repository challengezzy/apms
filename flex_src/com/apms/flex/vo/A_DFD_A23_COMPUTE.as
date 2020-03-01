package com.apms.flex.vo
{
	[Bindable]
	[RemoteClass(alias="com.apms.bs.oxygen.vo.A_DFD_A23_COMPUTE")]
	public class A_DFD_A23_COMPUTE
	{
		public function A_DFD_A23_COMPUTE()
		{
		}
		
		public var ID:String;
		public var MSG_NO:String;
		public var DATE_UTC:String;
		
		public var PRES_ST_C15_S13:Number;//S13标态压力
		public var PRES_ST_C15_S13_ROLL5:Number;
		public var DATETIME_MID_S13:String;
		public var DATETIME_MID_S13_MIS:Number; //S13 时间 毫秒数
		
		public var PRES_ST_C15_S46:Number;//S46标态压力
		public var PRES_ST_C15_S46_ROLL5:Number;
		public var DATETIME_MID_S46:String;
		public var DATETIME_MID_S46_MIS:Number;
		
		public var PRES_ST_S46_K:Number;//S46基于时间N点斜率
		public var PRES_ST_S46_K_OUT:Number;
		public var PRES_ST_S46_K_TSMP_ALTER:String;
		
		public var DETA_PRES_FWD:Number;//与前一报文掉压率
		public var DETA_PRES_FWD_ROLL5:Number;
		
		
		public var DETA_PRESRATE_ST:Number; //本报文掉压率
		public var DETA_PRESRATE_ST_ROLL5:Number;
		public var DETA_PRESRATE_ST_OUT:Number;
		public var DETA_PRESRATE_REMARK:String;
		public var DETA_PRESRATE_ST_TSMP_ALTER:String;
		
		public var DETA_PRESRATE_STS46H24:Number;//24小时掉压率
		public var DETA_PRESRATE_STS46H24_ROLL5:Number;
		public var DETA_PRES_REMARKS46H24:String;
		public var DETA_PRESRATE_H24_TSMP_ALTER:String;
		
		public var MARKER:String;
		public var MARKTIME:String;
		public var DETA_PRESRATE_ST_POINTTYPE:Number;
		
		//标识斜率的点，使用k和lineValue 展示
		public var k:Number; //斜率，原数据库中保存的是 psi/ms,应该转换为psi/24h(day) 
		public var lineValue:Number;	//一元回归线上的起点或终点
		
	}
}