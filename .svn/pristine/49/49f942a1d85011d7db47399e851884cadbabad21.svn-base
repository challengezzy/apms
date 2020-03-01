package com.apms.flex.modules.engine
{
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.CardButtonListener;
	
	
	public class EngineHourCycRecordListener implements CardButtonListener
	{
		
		public function buttonClick(cardPanel:BillCardPanel):void{
			
			var recordWindow:EngineHourCycRecordWindow = new EngineHourCycRecordWindow();
			var engData:Object = cardPanel.getDataValue();
			
			recordWindow.esn = engData["ENGSN"];
			PopUpManager.addPopUp(recordWindow,cardPanel,true);
			PopUpManager.centerPopUp(recordWindow);
		}
		
	}
}