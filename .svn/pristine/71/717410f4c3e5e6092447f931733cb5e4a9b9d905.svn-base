package com.apms.flex.modules.apu.swap
{
	import mx.managers.PopUpManager;
	import com.apms.flex.modules.apu.swap.ApuAttachmentManagementWindow;
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;

	
	public class ApuAttachManagementListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			var apuAttachmentManagementWindow:ApuAttachmentManagementWindow = new ApuAttachmentManagementWindow();			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条报文！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条报文！");
					return;
				}
				apuAttachmentManagementWindow.selObj=listPanel.getSelectedRowValue();
				PopUpManager.addPopUp(apuAttachmentManagementWindow,desktop,true);
				PopUpManager.centerPopUp(apuAttachmentManagementWindow);
			
	    	}
		}
	}
}