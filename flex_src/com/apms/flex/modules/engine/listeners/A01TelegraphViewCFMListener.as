package com.apms.flex.modules.engine.listeners
{
	import com.apms.flex.modules.engine.EngineReportViewFactory;
	import com.apms.flex.modules.engine.telegraph.A01CTelegraphViewWindow;
	import com.apms.flex.modules.engine.telegraph.A01ITelegraphViewWindow;
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
	
	public class A01TelegraphViewCFMListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a01cViewWindow:A01CTelegraphViewWindow;
		private var a01iViewWindow:A01ITelegraphViewWindow;
		private var teleContent:String;
		private var thedmu:String
		
		public function A01TelegraphViewCFMListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
			formService.endpoint = endpoint;
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA01ListHashVoHandler);
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
			//teleContent = selectObj["TEL_CONTENT"];
			var dmu:String = selectObj["DMU"];
			var rptno:String = selectObj["RPTNO"];
			var engFac:EngineReportViewFactory = new EngineReportViewFactory();
			
			
			var sqlA01C:String = "SELECT * FROM V_A01CLIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sqlA01C);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
//			var sqlA01I:String = "SELECT * FROM V_A01ILIST_VIEW WHERE MSG_NO='"+msgno+"'";
//			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sqlA01I);
			
		
		}
		
		private function getA01ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a01Arr:Array = event.result as Array;
			var a01vo:SimpleHashVO = a01Arr[0];
			
//			if (thedmu == "CFM"){
				a01cViewWindow = new A01CTelegraphViewWindow();
				//a01cViewWindow.telegraphContent = teleContent;
				a01cViewWindow.a01cvo = a01vo;
		
				PopUpManager.addPopUp(a01cViewWindow,desktop,true);
				PopUpManager.centerPopUp(a01cViewWindow);
//			} else if(thedmu == "IAE"){
//				a01iViewWindow = new A01ITelegraphViewWindow();
//				//a01iViewWindow.telegraphContent = teleContent;
//				a01iViewWindow.a01ivo = a01vo;
//				
//				PopUpManager.addPopUp(a01iViewWindow,desktop,true);
//				PopUpManager.centerPopUp(a01iViewWindow);
//			}
			
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文查询失败!",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}