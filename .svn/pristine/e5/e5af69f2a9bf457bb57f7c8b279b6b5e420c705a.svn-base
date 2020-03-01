package com.apms.flex.modules.flight.template
{
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.GlobalConst;

	public class FlightSchCreateListener implements ListButtonListener
	{
		private var listPanel:BillListPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var createWindow:FlightSchCreateWindow;
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条航班计划模板！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条航班计划模板！");
					return;
				}
				var selectObj:Object = listPanel.getSelectedRowValue();
				createWindow = new  FlightSchCreateWindow();
				createWindow.templateObj = selectObj;
				
				
				PopUpManager.addPopUp(createWindow,desktop,true);
				PopUpManager.centerPopUp(createWindow);
			}
			
		}
	}
}