package com.apms.flex.modules.engine.listeners
{
	import com.apms.flex.modules.engine.EngineReportViewFactory;
	import com.apms.flex.modules.engine.telegraph.A11ITelegraphViewWindow;
	import com.apms.flex.modules.engine.telegraph.A40TelegraphViewWindow;
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
	
	public class A40TelegraphViewListener implements ListButtonListener
	{  
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
//		private var a12cViewWindow:A12CTelegraphViewWindow;
		private var a39ViewWindow:A40TelegraphViewWindow;
		//private var teleContent:String;
		private var thedmu:String
		
		private var msgno:String;
		public function A40TelegraphViewListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.getSimpleHashVoArrayByDS.addEventListener(ResultEvent.RESULT,getA39ListHashVoHandler);
			formService.getSimpleHashVoArrayByDS.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues().length!=1){
				SmartXMessage.show("请选择一条报文！");
				return;
			}
			var selectObj:Object = listPanel.getSelectedRowValue();
			msgno= selectObj["MSG_NO"];
			//teleContent = selectObj["TEL_CONTENT"];
			var dmu:String = selectObj["DMU"];
			var rptno:String = selectObj["RPTNO"];
			var engFac:EngineReportViewFactory = new EngineReportViewFactory();
			var sql0:String; 
			var sql1:String; 
			
			sql0 = "SELECT TEL_CONTENT,RPTNO,ACMODEL,FLY_FROM,FLY_TO,FLT,PH,CNT,CODE,BLEED_STATUS," +
				"APU,TAT,ALT,CAS,MN,GW,CG,DMU,STATUS,DATE_UTC,ID,MSG_NO,ACNUM,RPTDATE,ESN_EC,EHRS_EC,ECYC_EC," +
				" FF1_AVG,FF1_STD,FF2_AVG,FF2_STD,N21_AVG,N21_STD,N22_AVG,N22_STD,PD1_AVG,PD1_STD,PD2_AVG,PD2_STD," +
				"ESN_EE,EHRS_EE,ECYC_EE,to_char(STARTTIME_S1,'yyyymmdd hh24:mi:ss') STARTTIME_S1," +
				"to_char(ENDTIME_S1,'yyyymmdd hh24:mi:ss') ENDTIME_S1,INTERVAL_TIME FROM V_A40LIST_VIEW WHERE MSG_NO='"+msgno+"'";
			formService.getSimpleHashVoArrayByDS(ApmsUIConst.DATASOURCE_APMS,sql0);
			OperatingTipUtil.startOperat("正在查询....",listPanel);
		}
		
		private function getA39ListHashVoHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var a39Arr:Array = event.result as Array;
			var a39vo:SimpleHashVO = a39Arr[0];
			a39ViewWindow=new　A40TelegraphViewWindow();
			a39ViewWindow.a39vo = a39vo;
			
			a39ViewWindow.msg_no=msgno;
			PopUpManager.addPopUp(a39ViewWindow,desktop,true);
			PopUpManager.centerPopUp(a39ViewWindow);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("报文查询失败!",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}