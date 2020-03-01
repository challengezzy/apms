package com.apms.flex.modules.apu.correct
{
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	public class ApuHourCorUpdateBefore implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var cardPanel:BillCardPanel;
		
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			var cardValue:Object = cardPanel.getDataValue();
			
			var asnComb:SimpleRefItemVO = cardValue["APU_ID"] as SimpleRefItemVO;
			if(asnComb == null || asnComb.name == null){
				SmartXMessage.show("请先选择APU！");
				return;
			}
			
			cardValue["APUSN"] = asnComb.name;
			
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			cardValue["UPDATE_DATE"] = nowData;
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			cardValue["UPDATE_MAN"] = user;
			
		}		
	}
}