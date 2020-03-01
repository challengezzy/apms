package com.apms.flex.modules.user
{
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.core.ext.DataValidator;
	import smartx.flex.components.util.TempletDataUtil;

	public class UserSaveValidator implements DataValidator 
	{
		public function UserSaveValidator()
		{
		}
		
		private var userObj:Object;
		
		public function validateData(cardPanel:BillCardPanel):Boolean{
			userObj = cardPanel.getDataValue();
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			userObj["UPDATEUSER"] = user;
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			userObj["UPDATETIME"] = nowData;
			//检验密码和确认密码是否一致
			if(cardPanel.insertMode){
				var loginName:String=userObj["LOGINNAME"];
				var pwd:String=userObj["PWD"];
				var pwd_confirm:String=userObj["PWD_CONFIRM"];
				if(pwd==null){
					Alert.show("密码不能为空!","错误");
					return false;
				}else if(pwd.localeCompare(pwd_confirm)!=0){
					Alert.show("两次输入的密码不一致!","错误");
					return false;
				}
				userObj["PWD"] = TempletDataUtil.generatePassword(loginName,pwd);
			}
			return true;
			
		}
		
	}//end of class
}