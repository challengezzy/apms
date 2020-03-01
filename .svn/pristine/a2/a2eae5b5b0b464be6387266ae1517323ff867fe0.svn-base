package com.apms.flex.modules.engine.fileUploadWindow
{
	import com.apms.flex.modules.engine.AttachManageOfEngineManageWindow;
	import com.apms.flex.modules.engine.fileUploadWindow.EngineOpenItemUploadWindow;
	
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	
	public class EngineOpenItemUploadWindowListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			var engineOpenItemUploadWindow:EngineOpenItemUploadWindow = new EngineOpenItemUploadWindow();			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条报文！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条报文！");
					return;
				}
				engineOpenItemUploadWindow.selObj=listPanel.getSelectedRowValue();
				PopUpManager.addPopUp(engineOpenItemUploadWindow,desktop,true);
				PopUpManager.centerPopUp(engineOpenItemUploadWindow);
				
			}
		}
	}
}