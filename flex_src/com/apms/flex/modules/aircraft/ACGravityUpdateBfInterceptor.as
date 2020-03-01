package com.apms.flex.modules.aircraft
{
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	import smartx.flex.components.util.SmartXMessage;
	
	public class ACGravityUpdateBfInterceptor implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var cardPanel:BillCardPanel;
		
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			var cardValue:Object = cardPanel.getDataValue();
			
			var moment_base:Number = Number(String(cardValue["MOMENT_BASE"]));
			var weight_base:Number = Number(String(cardValue["WEIGHT_BASE"]));
			var k_value:Number = Number(String(cardValue["K_VALUE"]));
			var modify_postion:Number = Number(String(cardValue["MODIFY_POSTION"]));
			
			if(weight_base == 0 || k_value ==0 ){
				
				SmartXMessage.show("基准力矩不能为0！",SmartXMessage.MESSAGE_WARN);
				return;
			}else{
				var rc_base:Number = moment_base/weight_base;
				var datum_gravity:Number = (rc_base-modify_postion)/k_value;
				cardValue["DATUM_GRAVITY"] = datum_gravity;
				
			}
			
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			cardValue["UPDATE_DATE"] = nowData;
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			cardValue["UPDATE_MAN"] = user;
			
		}		
	}
}