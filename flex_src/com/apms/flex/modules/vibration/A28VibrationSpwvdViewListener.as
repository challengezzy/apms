package com.apms.flex.modules.vibration
{
	import com.adobe.net.URI;
	import com.apms.flex.vo.ApmsUIConst;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
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
	
	public class A28VibrationSpwvdViewListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a28a32ViewWindow:A28A32TelegraphViewWindow;
		private var teleContent:String;
		
		public function A28VibrationSpwvdViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
//			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA28A32ListHashVoHandler);
//			formService.getSimpleHashVoArrayByDS.addEventListener(FaultEvent.FAULT,faultHandler);
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
			var groupid:String = selectObj["GROUPID"];
			var dim:String = "Z";
			var acnum:String = selectObj["AC_ID"];
			var date_utc:String = selectObj["DATE_UTC"];
			
			var paramStr:String = "?acnum="+acnum+"&groupid=" + groupid +  "&dim="+dim + "&date_utc="+date_utc;
			
			var urlReq:URLRequest = new URLRequest();
			
			var parObj:Object = FlexGlobals.topLevelApplication.parameters;
			var uri:URI = new URI(FlexGlobals.topLevelApplication.url);
			
//			var url:String = "http://".concat(uri.authority).concat(":").concat(uri.port)
//					.concat("/").concat("apms/webfigure/VibrationFigureChart.jsp").concat(paramStr);
				
			var url:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_WEBROOT)
				 + "webfigure/VibrationFigureChart.jsp" + paramStr;
			urlReq.url = encodeURI(url);
			navigateToURL(urlReq, "_blank");
			
//			var sql:String = "SELECT * FROM V_A28A32LIST_VIEW WHERE MSG_NO='"+msgno+"'";
//			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
			
		}
		
//		private function getA28A32ListHashVoHandler(event:ResultEvent):void{
//			var a28a32Arr:Array = event.result as Array;
//			var a28a32vo:SimpleHashVO = a28a32Arr[0];
//			
//			a28a32ViewWindow = new A28A32TelegraphViewWindow();
//			a28a32ViewWindow.telegraphContent = teleContent;
//			a28a32ViewWindow.a28a32vo = a28a32vo;
//			
//			PopUpManager.addPopUp(a28a32ViewWindow,desktop,true);
//			PopUpManager.centerPopUp(a28a32ViewWindow);
//		}
//		
//		private function faultHandler(event:FaultEvent):void{
//			OperatingTipUtil.endOperat();
//			SmartXMessage.show("报文数据查询异常",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
//		}
		
	}
}