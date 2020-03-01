package com.apms.flex.modules.basic.interceptor
{
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	
	public class UpdateManInfoBfInterceptor implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var cardPanel:BillCardPanel;
		
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			var cardValue:Object = cardPanel.getDataValue();
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			
			cardValue["UPDATE_MAN"] = user;
			cardValue["UPDATEUSER"] = user;
			
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			
			cardValue["UPDATE_DATE"] = nowData;
			cardValue["UPDATETIME"] = nowData;
		}		
	}
}