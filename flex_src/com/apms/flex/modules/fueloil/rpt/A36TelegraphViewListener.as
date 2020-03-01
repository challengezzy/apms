package com.apms.flex.modules.fueloil.rpt
{
	import com.apms.flex.modules.fueloil.rpt.A36TelegraphViewWindow;
	import com.apms.flex.vo.ApmsUIConst;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.OperatingTipUtil;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.GlobalConst;
	import smartx.flex.components.vo.SimpleHashVO;
	
	public class A36TelegraphViewListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a36ViewWindow:A36TelegraphViewWindow;

		private var teleContent:String;
		private var thedmu:String

		public function A36TelegraphViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA49ListHashVoHandler);
			formService.getSimpleHashVoArrayByDS.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			if(listPanel.getSelectedRowValues().length != 1){
				SmartXMessage.show("请选择一条报文！");
				return;
			}
			var selectObj:Object = listPanel.getSelectedRowValue();
			var msgno:String = selectObj["MSG_NO"];
			teleContent = selectObj["TEL_CONTENT"];

			var sql2:String = "SELECT * FROM V_A36LIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql2);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
		}
		
		private function getA49ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a36Arr:Array = event.result as Array;
			var a36vo:SimpleHashVO = a36Arr[0];
			
			a36ViewWindow = new A36TelegraphViewWindow();
			a36ViewWindow.telegraphContent = teleContent;
			a36ViewWindow.a36vo = a36vo;
			
			PopUpManager.addPopUp(a36ViewWindow,desktop,true);
			PopUpManager.centerPopUp(a36ViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文查询失败!",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}