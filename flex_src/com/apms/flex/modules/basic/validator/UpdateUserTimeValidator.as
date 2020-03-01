package com.apms.flex.modules.basic.validator
{
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.core.ext.DataValidator;
	import smartx.flex.components.util.TempletDataUtil;

	public class UpdateUserTimeValidator implements DataValidator 
	{
		
		public function validateData(cardPanel:BillCardPanel):Boolean{
			var cardValue:Object = cardPanel.getDataValue();
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			
			cardValue["UPDATEUSER"] = user;
			
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			
			cardValue["UPDATETIME"] = nowData;
			
			
			return true;
			
		}
		
	}//end of class
}