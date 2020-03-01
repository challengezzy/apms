package com.apms.flex.modules.basic.interceptor
{
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	
	public class InsertAndUpdateUnFltRptInterceptor implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var cardPanel:BillCardPanel;
		
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			var cardValue:Object = cardPanel.getDataValue();
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			cardValue["UPDATEUSER"] = user;//更新人
			
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			cardValue["UPDATETIME"] = nowData;//更新时间
			if(cardValue["ACID"]!=null){
				cardValue["ACNUM"] = cardValue["ACID"].name;//飞机号
			}
			
			if(cardValue["AP_LASTAP"]!=null){
				cardValue["AP_LASTAP"] = cardValue["AP_LASTAP"].name;
			}
		}		
	}
}