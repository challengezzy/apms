package com.apms.flex.modules.user
{
	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	
	public class UserPwdModifyBtnListener implements ListButtonListener
	{
		
		public function buttonClick(listPanel:BillListPanel):void
		{
			if(listPanel.getSelectedRowValue())
			{
				var loginInfo:Object = listPanel.getSelectedRowValue();
				var ump:UserModifyPwdWindow = new UserModifyPwdWindow();
				ump.endpoint = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
				ump.setState(false);
				ump.setLoginProperty(loginInfo["LOGINNAME"],loginInfo["ID"]);
				PopUpManager.addPopUp(ump,listPanel,true);
				PopUpManager.centerPopUp(ump);
			}else
			{
				Alert.show("请选择一个用户!","error");
			}
		}
	}
}