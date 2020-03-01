package com.apms.flex.modules.engine.listeners
{
	import com.apms.flex.modules.engine.EngineReportViewFactory;
	import com.apms.flex.modules.engine.telegraph.A06CTelegraphViewWindow;
	import com.apms.flex.modules.engine.telegraph.A06ITelegraphViewWindow;
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
	
	public class A06TelegraphViewIAEListener implements ListButtonListener
	{  
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a06cViewWindow:A06CTelegraphViewWindow;
		private var a06iViewWindow:A06ITelegraphViewWindow;
		//private var teleContent:String;
		private var thedmu:String
		
		public function A06TelegraphViewIAEListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA06ListHashVoHandler);
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
			
			thedmu = engFac.checkDMU(dmu);
			
			var sqlA06I:String = "SELECT * FROM V_A06ILIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sqlA06I);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
			
		}
		
		private function getA06ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a06Arr:Array = event.result as Array;
			var a06vo:SimpleHashVO = a06Arr[0];
			
			a06iViewWindow = new A06ITelegraphViewWindow();
			//a02iViewWindow.telegraphContent = teleContent;
			a06iViewWindow.a06ivo = a06vo;
			
			PopUpManager.addPopUp(a06iViewWindow,desktop,true);
			PopUpManager.centerPopUp(a06iViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文查询失败!",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}