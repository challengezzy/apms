package com.apms.flex.modules.engine
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
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleHashVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	
	public class EngDismountingListener implements ListButtonListener
	{		
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var engWindow:EngineSwapWindow;
		
		//private var a23ViewWindow:A23TelegraphViewWindow;
		private var teleContent:String;
		
		public function EngDismountingListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA23ListHashVoHandler);
			formService.getSimpleHashVoArrayByDS.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			if(listPanel.getSelectedRowValues().length != 1){
				SmartXMessage.show("请选择您要更换的发动机！");
				return;
			}
			var selectObj:Object = listPanel.getSelectedRowValue();
			var positionVo:SimpleComboxItemVO = selectObj["ENGLOC"] as SimpleComboxItemVO;
			var position:String = positionVo.id;
//			var statusVo:SimpleComboxItemVO = selectObj["STATUS"] as SimpleComboxItemVO;
//			var status:String = statusVo.id;
			if(position != "1"){//如果不是 在翼状态，不进行拆换
				SmartXMessage.show("APU非在翼状态，不需要进行拆换！");
				return;
			}
			engWindow = new EngineSwapWindow();
			//engWindow.curEngObj = selectObj;
			engWindow.curEngSn = selectObj["ENGSN"];
			engWindow.infoDate = selectObj["INFODATE"];
			var modelRefVo:SimpleComboxItemVO = selectObj["ENGMODELID"] as SimpleComboxItemVO;
			engWindow.engmodelId = modelRefVo.id;
			engWindow.listPanel=listPanel;
			PopUpManager.addPopUp(engWindow,desktop,true);
		    PopUpManager.centerPopUp(engWindow);
			
			//var msgno:String = selectObj["MSG_NO"];
			//teleContent = selectObj["TEL_CONTENT"];
			
			//var sql:String = "SELECT * FROM V_A23LIST_VIEW WHERE MSG_NO='"+msgno+"'";
			//formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
			
		}
		
		private function getA23ListHashVoHandler(event:ResultEvent):void{
			var a23Arr:Array = event.result as Array;
			var a23vo:SimpleHashVO = a23Arr[0];
			
//			a23ViewWindow = new A23TelegraphViewWindow();
//			a23ViewWindow.telegraphContent = teleContent;
//			a23ViewWindow.a23vo = a23vo;
			
//			PopUpManager.addPopUp(a23ViewWindow,desktop,true);
//			PopUpManager.centerPopUp(a23ViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("更换发动机失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}