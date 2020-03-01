package com.apms.flex.modules.flight
{
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.core.ext.DataValidator;
	import smartx.flex.components.util.SmartXMessage;
	

	public class FaultInfoUpdateValidator implements DataValidator 
	{
		
		public function FaultInfoUpdateValidator()
		{
		}
		
		private var cardValue:Object;
		
		public function validateData(cardPanel:BillCardPanel):Boolean{
			cardValue = cardPanel.getDataValue();
			if(cardValue["ACNUM"]==null&&cardValue["FLT_NO"]==null){
				SmartXMessage.show("航班号和飞机号不能同时为空！");
				return false;
			}
			return true;
			
		}
		
	}
}