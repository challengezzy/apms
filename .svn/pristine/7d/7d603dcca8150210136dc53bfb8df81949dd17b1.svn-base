package com.apms.flex.modules.alartmessage
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
	
	public class AlartMessageDealListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		public function AlartMessageDealListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
//			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA13ListHashVoHandler);
//			formService.getSimpleHashVoArrayByDS.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			if(listPanel.getSelectedRowValue() == null){
				SmartXMessage.show("请选择一条告警消息！");
				return;
			}
			var messageObj:Object = listPanel.getSelectedRowValue();
			
			var dealWindow:AlartMessageDealWindow = new AlartMessageDealWindow;
			dealWindow.alartMsgObj = messageObj;			
			
			PopUpManager.addPopUp(dealWindow,desktop,true);
			PopUpManager.centerPopUp(dealWindow);
			
			//formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
			
		}
		
		private function getA13ListHashVoHandler(event:ResultEvent):void{
			var a13Arr:Array = event.result as Array;
			var a13vo:SimpleHashVO = a13Arr[0];
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("数据查询异常",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}