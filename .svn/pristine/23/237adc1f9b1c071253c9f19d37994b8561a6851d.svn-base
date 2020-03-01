package com.apms.flex.modules.common
{
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.itemcomponent.ItemRefPanel;
	import smartx.flex.components.vo.GlobalConst;
	import smartx.flex.components.vo.TempletItemVO;

	public class RefConditionUtil
	{
				
		private static var smartxDest:String = GlobalConst.SERVICE_FORM;
		
		/**
		 *获取飞机查询条件的参照控件
		 **/
		public static function getAcRefPanel():ItemRefPanel{
			var endpoint:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
			//添加参照控件
			var acItemVO:TempletItemVO = new TempletItemVO();
			acItemVO.itemtype="参照";
			acItemVO.itemname="选择飞机";
			acItemVO.itemkey="ACNUM";
			acItemVO.refdesc="SELECT A.ID ID#,BASEORGID ORGID#,AIRCRAFTSN 机号,M.MODELCODE 机型 FROM B_AIRCRAFT A,B_AIRCRAFT_MODEL M WHERE M.ID=A.ACMODELID;ds=datasource_apms";
			var acRefPanel:ItemRefPanel = new ItemRefPanel(acItemVO,smartxDest,endpoint,false,false);
			
			return acRefPanel;
		}
		
		/**
		 *获取APU查询条件的参照控件
		 **/
		public static function getApuRefPanel():ItemRefPanel{
			var endpoint:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
			var apuItemVO:TempletItemVO = new TempletItemVO();
			apuItemVO.itemtype="参照";
			apuItemVO.itemname="选择APU";
			apuItemVO.itemkey="ACNUM";
			apuItemVO.refdesc="SELECT A.ID ID#,AIRCRAFTID ACID#,APUSN APU序列号,M.MODEL APU型号 FROM B_APU A,B_APU_MODEL M WHERE M.ID=A.APUMODELID;ds=datasource_apms";
			var apuRefPanel:ItemRefPanel = new ItemRefPanel(apuItemVO,smartxDest,endpoint,false,false);
			
			return apuRefPanel;
		}
		
		
	}
}