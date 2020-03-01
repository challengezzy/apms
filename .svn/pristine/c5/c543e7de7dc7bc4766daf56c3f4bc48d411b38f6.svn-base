package com.apms.flex.modules.apu
{
	import com.apms.flex.vo.ApmsUIConst;
	
	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import smartx.flex.components.event.BillCardPanelEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.event.BasicEvent;
	import smartx.flex.components.styletemplate.ifc.CardButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.util.StringUtils;
	import smartx.flex.components.util.TextAreaWindow;
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	import com.apms.flex.util.DateUtil;


	public class ApuDiagnoseresultAddTraindataListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var taskId:String;
		private var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
		private var refItemVo:SimpleRefItemVO;
		private var comBoxItemVO:SimpleComboxItemVO;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var apmsDest:String = ApmsUIConst.NEURALNETSERVICE;
		private var apmsService:RemoteObject = new RemoteObject;
		public function ApuDiagnoseresultAddTraindataListener(){
			apmsService.endpoint = endpoint;
			apmsService.destination = apmsDest;
			apmsService.apuDiagnoseresultAddTraindata.addEventListener(ResultEvent.RESULT,addOkHandler);
			apmsService.apuDiagnoseresultAddTraindata.addEventListener(FaultEvent.FAULT,faultHandler);
		
		} 

		public function buttonClick(cardPanel:BillCardPanel):void{
			
			cardData = cardPanel.getDataValue();
//			if(cardData["FORMSERVICE_MODIFYFLAG"] == "update"){
//				SmartXMessage.show("请先修改完保存后 再添加！");
//				return;
//			}
			billCardPanel = cardPanel;
			billCardPanel.addEventListener(BillCardPanelEvent.SAVESUCCESSFUL,saveOkHandler);
			billCardPanel.addEventListener(BillCardPanelEvent.OPERATION_FAILED,saveFaultHandler);
			var msg_no:String = cardData["MSG_NO"];
			var datatype_oldVo:SimpleComboxItemVO = cardData["DATATYPE_OLD"] as SimpleComboxItemVO;
			var datatype_old:String = datatype_oldVo.id;
			var datetype_newVo:SimpleComboxItemVO = cardData["DATATYPE"] as SimpleComboxItemVO;
			var datetype_new:String = datetype_newVo.id;

			var acnum:String = cardData["ACNUM"];
			var asn:String = cardData["ASN"];
			var apumodelidVo:SimpleRefItemVO = cardData["APUMODELID"] as SimpleRefItemVO;
			var apumodelid:String = apumodelidVo.id;
			var comments:String = cardData["COMMENTS"];
		  
			var utcDate:String = cardData["UTCDATE"];

			apmsService.apuDiagnoseresultAddTraindata(msg_no,datatype_old,datetype_new,
				acnum,utcDate,asn,apumodelid,comments,user);

		}
		

		private function addOkHandler(event:ResultEvent):void{
			var res:Object = event.result;
			if(res == "0"){
				SmartXMessage.show("数据分类未修改 无需添加！");

			}else if(res == "1"){
				billCardPanel.isShowAlert=false;
				billCardPanel.save();
				SmartXMessage.show("已存在样本管理 更新成功！");

			}else if(res == "2"){
				billCardPanel.isShowAlert=false;
				billCardPanel.save();
				SmartXMessage.show("添加成功！"); 
			}
		}
		
		private function saveOkHandler(event:BillCardPanelEvent):void{
			billCardPanel.isShowAlert=true;
		}
		private function saveFaultHandler(event:BillCardPanelEvent):void{
			billCardPanel.isShowAlert=true;
		}
		private function faultHandler(event:FaultEvent):void{
			SmartXMessage.show("添加失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
	}
}