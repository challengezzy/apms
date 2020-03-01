package com.apms.flex.modules.MaintenanceManage
{
	import com.apms.flex.modules.MaintenanceManage.DdinfoViewWindow;
	
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.SimpleHashVO;
	
	public class DDinfoListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条DD单数据！");
				return;
			}else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条DD单数据！");
					return;
				}
				var ddinfoViewWindow:DdinfoViewWindow=new DdinfoViewWindow();
				var selectObj:Object = listPanel.getSelectedRowValue();
				ddinfoViewWindow.ddInfoData = selectObj;
				PopUpManager.addPopUp(ddinfoViewWindow,desktop,true);
				PopUpManager.centerPopUp(ddinfoViewWindow);
			}
			
		}
	}
}