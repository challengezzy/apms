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
	
	public class A13TelegraphViewListener implements ListButtonListener
	{
		private var apmsDest:String = ApmsUIConst.APMS_SERVICE;

		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var apmsService:RemoteObject = new RemoteObject;

		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var a13ViewWindow:A13TelegraphViewWindow;
		private var r13ViewWindow:R13TelegraphViewWindow;
		private var teleContent:String;
		
		private var rpt_no:String;
		
		public function A13TelegraphViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			apmsService.endpoint = endpoint;
			apmsService.destination = apmsDest;
			apmsService.getReportMsg.addEventListener(ResultEvent.RESULT,getRptListHashVoHandler);
			apmsService.getReportMsg.addEventListener(FaultEvent.FAULT,faultHandler);
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA13ListHashVoHandler);
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

			if(teleContent == null){
				var queryForReportsql:String = "SELECT a.*,f.TEL_CONTENT,f.rptno FROM V_A13LIST_VIEW a,A_ACARS_TELEGRAPH_DFD f WHERE a.MSG_NO=f.MSG_NO AND a.MSG_NO='"+msgno+"'";
				apmsService.getReportMsg(ApmsUIConst.DATASOURCE_APMS,queryForReportsql);
				OperatingTipUtil.startOperat("正在查询....",listPanel);

			}else{
				var sql:String = "SELECT * FROM V_A13LIST_VIEW WHERE MSG_NO='"+msgno+"'";
				formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql);
				OperatingTipUtil.startOperat("正在查询....",listPanel);
				
			}
			
		}
		
		private function getA13ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a13Arr:Array = event.result as Array;
			var a13vo:SimpleHashVO = a13Arr[0];
			
			if (rpt_no == "A13"){
				a13ViewWindow = new A13TelegraphViewWindow();
				a13ViewWindow.telegraphContent = teleContent;
				a13ViewWindow.a13vo = a13vo;
				
				PopUpManager.addPopUp(a13ViewWindow,desktop,true);
				PopUpManager.centerPopUp(a13ViewWindow);
			} else if (rpt_no == "R13"){
				r13ViewWindow = new R13TelegraphViewWindow();
				r13ViewWindow.telegraphContent = teleContent;
				r13ViewWindow.r13vo = a13vo;
				
				PopUpManager.addPopUp(r13ViewWindow,desktop,true);
				PopUpManager.centerPopUp(r13ViewWindow);
			}
			
		}
		private function getRptListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();

			var rptArr:Array = event.result as Array;
			var rptvo:SimpleHashVO = rptArr[0];
			if(rptvo==null){
				SmartXMessage.show("未查到原始报文内容");	
				return;
			}
				//13号报文需要区分是A13 还是R13
			var rptno:String = rptvo.dataMap.rptno;
				
			if(rptno == "A13"){
				var a13ViewWindow:A13TelegraphViewWindow = new A13TelegraphViewWindow();
				a13ViewWindow.a13vo = rptvo;
				var a13Data:Object = rptvo.dataMap;
				a13ViewWindow.telegraphContent = a13Data.tel_content;
				
				PopUpManager.addPopUp(a13ViewWindow,desktop,true);
				PopUpManager.centerPopUp(a13ViewWindow);
			}else if(rptno == "R13"){
				var r13ViewWindow:R13TelegraphViewWindow = new R13TelegraphViewWindow();
				r13ViewWindow.r13vo = rptvo;
				var r13Data:Object = rptvo.dataMap;
				r13ViewWindow.telegraphContent = r13Data.tel_content;
				
				PopUpManager.addPopUp(r13ViewWindow,desktop,true);
				PopUpManager.centerPopUp(r13ViewWindow);
			}
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文数据查询异常",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}