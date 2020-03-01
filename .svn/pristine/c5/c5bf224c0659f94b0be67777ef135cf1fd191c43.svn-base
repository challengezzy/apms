package com.apms.flex.modules.apu.correct
{
	import com.apms.flex.vo.ApmsUIConst;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.event.BasicEvent;
	import smartx.flex.components.event.BillCardPanelEvent;
	import smartx.flex.components.styletemplate.ifc.CardButtonListener;
	import smartx.flex.components.util.MemoryUtil;
	import smartx.flex.components.util.OperatingTipUtil;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.GlobalConst;
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	/**
	 * 对APU报文应用小时循环修正，并设置为重新计算
	 * @author zzy
	 * @date Jun 14, 2011
	 */
	public class ApuReportReparseListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;

		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		
		private var asn:String;
		
		private var beginTime:String;
		private var endTime:String;
		
		private var corHour:Number;
		private var corCycle:Number;
		private var corFlag:String = "1";//修正
		
		private var corObject:Object = new Object();
		
		public function ApuReportReparseListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.correctApuHourCyc.addEventListener(ResultEvent.RESULT,correctOkHandler);
			formService.correctApuHourCyc.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(cardPanel:BillCardPanel):void{
			
			cardData = cardPanel.getDataValue();
			billCardPanel = cardPanel;
			
			var asnComb:SimpleRefItemVO = cardData["APU_ID"] as SimpleRefItemVO;
			if(asnComb == null || asnComb.name == null){
				SmartXMessage.show("请先选择APU！");
				return;
			}
			//对数据进行保存
			//cardPanel.save();
			
			asn =asnComb.name;			
			beginTime = cardData["BEGIN_TIME"];
			endTime = cardData["END_TIME"];
			corHour = cardData["CORHOUR"];//正确 的小时、循环
			corCycle = cardData["CORCYCLE"];
			
			corObject["BEGIN_TIME"] = beginTime;
			corObject["END_TIME"] = endTime;
			corObject["CORHOUR"] = corHour;
			corObject["CORCYCLE"] = corCycle;
			corObject["CORFLAG"] = corFlag;
			
			if(corHour ==0 || corCycle==0 || isNaN(corHour) || isNaN(corCycle) ){
				SmartXMessage.show("请先设置正确的小时循环数据！");
				return;
			}
			
			if(beginTime == null || beginTime == ""){
				SmartXMessage.show("请先设置修正开始时间！");
				return;
			}
			
			if(endTime == null || endTime == ""){
				SmartXMessage.show("未设置修正结束时间，确认小时循环从开始时间修正到现在？",SmartXMessage.MESSAGE_CONFIRM,null,billCardPanel,confirmHandler);
			}else{
				OperatingTipUtil.startOperat("执行小时循环修正...",billCardPanel);
				
				formService.correctApuHourCyc(asn,corObject);
			}
			
		}
		
		private function confirmHandler(event:CloseEvent):void{
			if(event.detail == Alert.YES){
				OperatingTipUtil.startOperat("执行小时循环修正...",billCardPanel);
				
				formService.correctApuHourCyc(asn,corObject);
			}
		}
		
		private function correctOkHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("当前APU时间区间的小时循环数据修正成功！");			
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("APU小时循环命令执行异常",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
	}
}