package com.sep.flex.modules.buttonListener
{
	import mx.managers.PopUpManager;
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import com.sep.flex.modules.butttonWindowMoudle.ErrorHistoryWindow;
	
	
	public class ErrorHistoryListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			var errorHistoryWindow:ErrorHistoryWindow = new ErrorHistoryWindow();			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条工卡！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条工卡！");
					return;
				}
				errorHistoryWindow.selObj=listPanel.getSelectedRowValue();
				PopUpManager.addPopUp(errorHistoryWindow,desktop,true);
				PopUpManager.centerPopUp(errorHistoryWindow);
				
			}
		}
	}
}