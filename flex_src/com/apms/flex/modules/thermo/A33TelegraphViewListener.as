package com.apms.flex.modules.thermo
{
	import com.apms.flex.vo.ApmsUIConst;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
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
	
	public class A33TelegraphViewListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a33ViewWindow:A33TelegraphViewWindow;
		private var teleContent:String;
		
		public function A33TelegraphViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA33ListHashVoHandler);
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
			
			var sql:String = "SELECT * FROM V_A33LIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
		}
		
		private function getA33ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a33Arr:Array = event.result as Array;
			var a33vo:SimpleHashVO = a33Arr[0];
			
			a33ViewWindow = new A33TelegraphViewWindow();
			a33ViewWindow.telegraphContent = teleContent;
			a33ViewWindow.a33vo = a33vo;
			
			PopUpManager.addPopUp(a33ViewWindow,desktop,true);
			PopUpManager.centerPopUp(a33ViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文数据查询异常",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}