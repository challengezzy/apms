package com.apms.flex.modules.aircraft
{
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	import smartx.flex.components.util.SmartXMessage;
	
	/**
	 * 载重工作计划卡片更新后置拦截器
	 * */
	public class ACWeightWorkPalnCardAfInterceptor implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var cardPanel:BillCardPanel;
		
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			var cardValue:Object = cardPanel.getDataValue();
			
			//更新载重工作计划
			var id:String = cardValue["ID"] as String;
			
			cardPanel.initQueryCondition = "ID=" + id;
			cardPanel.setDataValueByQuery();
			
			
		}		
	}
}