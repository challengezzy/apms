package com.sep.flex.modules.nrcard
{
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	
	public class PrintNrCardListener implements ListButtonListener
	{
		
		private var listData:Object;
		private var listPanel:BillListPanel;
		
		public function buttonClick(listPanel:BillListPanel):void{
						
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条工卡！");
				return;
			}
			
			if(listPanel.getSelectedRowValues().length!=1){
				SmartXMessage.show("请选择一条工卡！");
				return;
			}
			listData = listPanel.getSelectedRowValue();
			var nrcardId:String = listData["ID"].toString();
			var util:NrCardPrintUtil = new NrCardPrintUtil();
			util.printA3Card(nrcardId);
		}
		
		
	}//end class
}