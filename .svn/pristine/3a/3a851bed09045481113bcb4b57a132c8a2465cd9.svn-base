package com.apms.flex.modules.engine.listeners
{
	import com.apms.flex.modules.engine.EngineReportViewFactory;
	import com.apms.flex.modules.engine.telegraph.A10CTelegraphViewWindow;
	import com.apms.flex.modules.engine.telegraph.A10ITelegraphViewWindow;
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
	
	public class A10TelegraphViewIAEListener implements ListButtonListener
	{  
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a10cViewWindow:A10CTelegraphViewWindow;
		private var a10iViewWindow:A10ITelegraphViewWindow;
		//private var teleContent:String;
		private var thedmu:String
		
		public function A10TelegraphViewIAEListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA10ListHashVoHandler);
			formService.getSimpleHashVoArrayByDS.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues().length!=1){
				SmartXMessage.show("请选择一条报文！");
				return;
			}
			
			
			var selectObj:Object = listPanel.getSelectedRowValue();
			var msgno:String = selectObj["MSG_NO"];
			//teleContent = selectObj["TEL_CONTENT"];
			var dmu:String = selectObj["DMU"];
			var rptno:String = selectObj["RPTNO"];
			var engFac:EngineReportViewFactory = new EngineReportViewFactory();
			var sql:String; 
			
			sql = "SELECT * FROM V_A10ILIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
			
		}
		
		private function getA10ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a10Arr:Array = event.result as Array;
			var a10vo:SimpleHashVO = a10Arr[0];
			
			a10iViewWindow = new A10ITelegraphViewWindow();
			a10iViewWindow.A10ivo = a10vo;
			
			PopUpManager.addPopUp(a10iViewWindow,desktop,true);
			PopUpManager.centerPopUp(a10iViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文查询失败!",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}