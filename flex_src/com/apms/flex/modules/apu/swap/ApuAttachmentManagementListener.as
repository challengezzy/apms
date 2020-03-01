package com.apms.flex.modules.apu.swap
{
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;

	
	public class ApuAttachmentManagementListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues().length != 1){
				SmartXMessage.show("请选择一条报文");
			}else{
				var apuFileWindow:ApuFileWindow = new ApuFileWindow();
				apuFileWindow.selObj = listPanel.getSelectedRowValue();
				PopUpManager.addPopUp(apuFileWindow,desktop,true);
				PopUpManager.centerPopUp(apuFileWindow);
			}
		}
	}
}