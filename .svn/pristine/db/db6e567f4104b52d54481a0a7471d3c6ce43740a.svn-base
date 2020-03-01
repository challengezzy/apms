package com.apms.flex.modules.dfd
{
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;

	/**
	 * 进行修改dfd报文解析状态
	 * */
	public class DfdParseStateResetListener implements ListButtonListener
	{

		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		public function DfdParseStateResetListener()
		{
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			var selWindow:DfdParseStateResetWindow = new DfdParseStateResetWindow();
			
			PopUpManager.addPopUp(selWindow,desktop,true);
			PopUpManager.centerPopUp(selWindow);
		}
		
	}
}