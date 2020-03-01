package com.sep.flex.modules.buttonListener
{
	import mx.managers.PopUpManager;
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import com.sep.flex.modules.butttonWindowMoudle.ChemiacalWindow;
	
	
	public class ChemiacalListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			var chemiacalWindow:ChemiacalWindow = new ChemiacalWindow();			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条工卡！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条工卡！");
					return;
				}
				chemiacalWindow.selObj=listPanel.getSelectedRowValue();
				PopUpManager.addPopUp(chemiacalWindow,desktop,true);
				PopUpManager.centerPopUp(chemiacalWindow);
				
			}
		}
	}
}