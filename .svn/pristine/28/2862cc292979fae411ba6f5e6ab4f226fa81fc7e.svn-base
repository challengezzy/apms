package com.apms.flex.modules.apu
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
	
	public class A14TelegraphViewListener implements ListButtonListener
	{
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a14ViewWindow:A14TelegraphViewWindow;
		private var r14ViewWindow:R14TelegraphViewWindow;
		private var teleContent:String;
		private var rpt_no:String;
		
		public function A14TelegraphViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA14ListHashVoHandler);
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
			rpt_no = selectObj["RPTNO"];
			
			var sql:String = "SELECT * FROM V_A14LIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
		}
		
		private function getA14ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a14Arr:Array = event.result as Array;
			var a14vo:SimpleHashVO = a14Arr[0];
			
			if (rpt_no == "A14"){
				a14ViewWindow = new A14TelegraphViewWindow();
				a14ViewWindow.telegraphContent = teleContent;
				a14ViewWindow.a14vo = a14vo;
				
				PopUpManager.addPopUp(a14ViewWindow,desktop,true);
				PopUpManager.centerPopUp(a14ViewWindow);
			} else if (rpt_no == "R14"){
				r14ViewWindow = new R14TelegraphViewWindow();
				r14ViewWindow.telegraphContent = teleContent;
				r14ViewWindow.r14vo = a14vo;
				
				PopUpManager.addPopUp(r14ViewWindow,desktop,true);
				PopUpManager.centerPopUp(r14ViewWindow);
			}
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文数据查询异常",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}