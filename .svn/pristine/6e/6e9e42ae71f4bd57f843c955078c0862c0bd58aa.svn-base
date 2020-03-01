package com.apms.flex.modules.fdimu
{
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.event.BasicEvent;
	import smartx.flex.components.event.BillCardPanelEvent;
	import smartx.flex.components.styletemplate.ifc.CardButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.util.StringUtils;
	import smartx.flex.components.vo.GlobalConst;
	
	/**
	 * 文件上传监听器
	 * */
	public class FdimuFileUploadListener implements CardButtonListener
	{
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
	
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			billCardPanel = cardPanel;
			var uploadWindow:FdimuFileUpload = new FdimuFileUpload();
			
			uploadWindow.addEventListener(BasicEvent.LOADDATA_SUCCESSFUL,uploadSuccessfulHandler);
			uploadWindow.tableName = "B_FDIMU_SWVERSION";
			uploadWindow.uploadUser = user;
			uploadWindow.fdimuId = cardData["ID"];
			
			PopUpManager.addPopUp(uploadWindow,cardPanel,true);
			PopUpManager.centerPopUp(uploadWindow);
		}
		
		private function uploadSuccessfulHandler(event:BasicEvent):void{
			
			SmartXMessage.show("附件上传成功！");
			billCardPanel.dispatchEvent(new BillCardPanelEvent(BillCardPanelEvent.REFRESHCHILDLIST));
		}

		
	}
}