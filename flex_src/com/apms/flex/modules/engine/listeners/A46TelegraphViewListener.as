package com.apms.flex.modules.engine.listeners
{
	import com.apms.flex.modules.engine.EngineReportViewFactory;
	import com.apms.flex.modules.engine.telegraph.A46TelegraphViewWindow;
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
	
	public class A46TelegraphViewListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a46ViewWindow:A46TelegraphViewWindow;
		private var teleContent:String;
		private var thedmu:String
		
		public function A46TelegraphViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA46ListHashVoHandler);
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
//			var engFac:EngineReportViewFactory = new EngineReportViewFactory();
			
			
			var sqlA01C:String = "SELECT * FROM V_A46LIST_VIEW WHERE MSG='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sqlA01C);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
			//			var sqlA01I:String = "SELECT * FROM V_A01ILIST_VIEW WHERE MSG_NO='"+msgno+"'";
			//			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sqlA01I);
			
			
		}
		
		private function getA46ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a46Arr:Array = event.result as Array;
			var a46vo:SimpleHashVO = a46Arr[0];
			
			//			if (thedmu == "CFM"){
			a46ViewWindow = new A46TelegraphViewWindow();
			a46ViewWindow.telegraphContent = teleContent;
			a46ViewWindow.a46vo = a46vo;
			
			PopUpManager.addPopUp(a46ViewWindow,desktop,true);
			PopUpManager.centerPopUp(a46ViewWindow);
			//			} else if(thedmu == "IAE"){
			//				a01iViewWindow = new A01ITelegraphViewWindow();
			//				//a01iViewWindow.telegraphContent = teleContent;
			//			   	a01iViewWindow.a01ivo = a01vo;
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