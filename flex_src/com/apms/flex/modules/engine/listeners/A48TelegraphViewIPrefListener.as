package com.apms.flex.modules.engine.listeners
{
	import com.apms.flex.modules.engine.EngineReportViewFactory;
	import com.apms.flex.modules.engine.telegraph.A48IPrefTelegraphViewWindow;
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
	
	public class A48TelegraphViewIPrefListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a48iViewWindow:A48IPrefTelegraphViewWindow;

		private var teleContent:String;
		private var thedmu:String

		public function A48TelegraphViewIPrefListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,geta48ListHashVoHandler);
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
			var dmu:String = selectObj["DMU"];
			var rptno:String = selectObj["RPTNO"];

			var sql2:String = "SELECT * FROM V_A48ILIST_PREFVIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql2);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
		}
		
		private function geta48ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a48Arr:Array = event.result as Array;
			var a48vo:SimpleHashVO = a48Arr[0];
			
			a48iViewWindow = new A48IPrefTelegraphViewWindow();
			a48iViewWindow.telegraphContent = teleContent;
			a48iViewWindow.msg_no = a48vo.dataMap["msg_no"];
			a48iViewWindow.a48vo = a48vo;
			
			PopUpManager.addPopUp(a48iViewWindow,desktop,true);
			PopUpManager.centerPopUp(a48iViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文查询失败!",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}