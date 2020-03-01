package com.apms.flex.modules.basic.interceptor
{
	import flash.events.Event;
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.formatters.DateFormatter;
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	import smartx.flex.components.util.SmartXMessage;
	
	/**
	 *新增机场设备时设置新增的工作梯数据
	 */ 
	public class InsertWorkladderBfInterceptor implements ClientInterceptorIFC
	{
		private var cardValue:Object;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var cardPanel:BillCardPanel;
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			cardValue= cardPanel.getDataValue();
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			cardValue["UPDATEUSER"] = user;
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			cardValue["UPDATETIME"] = nowData;
		}	
	}
}