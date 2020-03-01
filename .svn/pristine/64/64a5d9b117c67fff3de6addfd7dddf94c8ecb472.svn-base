package com.apms.flex.modules.basic.interceptor
{
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	
	public class UserUpdateBfInterceptor implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var cardPanel:BillCardPanel;
		
		public function handler(obj:Object):void{
			cardPanel = obj as BillCardPanel;
			var cardValue:Object = cardPanel.getDataValue();
			
			cardValue["PWDCONFIRM"] = cardValue["PWD"];
			cardValue["ADMINPWDCONFIRM"] = cardValue["ADMINPWD"];
		}		
	}
}