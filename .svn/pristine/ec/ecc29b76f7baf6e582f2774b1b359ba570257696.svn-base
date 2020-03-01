package com.apms.flex.modules.apu.management
{
	import com.apms.flex.modules.apu.management.ApuAttachManageofApuManageWindow;	
	import mx.managers.PopUpManager;
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;

	
	public class AttachManageOfAircraftWeightListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			var aircraftWeightAttachManageWindow:AircraftWeightAttachManageWindow = new AircraftWeightAttachManageWindow();			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条飞机载重信息！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条飞机载重信息！");
					return;
				}
				aircraftWeightAttachManageWindow.selObj=listPanel.getSelectedRowValue();
				PopUpManager.addPopUp(aircraftWeightAttachManageWindow,desktop,true);
				PopUpManager.centerPopUp(aircraftWeightAttachManageWindow);
			
	    	}
		}
	}
}