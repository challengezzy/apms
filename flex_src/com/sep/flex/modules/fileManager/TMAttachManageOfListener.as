package com.sep.flex.modules.fileManager
{
	import com.apms.flex.modules.basic.AttachUploadWindow;
	
	import mx.controls.Alert;
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
	import smartx.flex.components.util.TextAreaWindow;
	import smartx.flex.components.vo.GlobalConst;
	
	/**
	 * @author zzy
	 * @date Jun 14, 2011
	 * 添加公告的附件信息
	 */
	public class TMAttachManageOfListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var taskId:String;
		private var isWriteStatus:String;
		private var isWriteStatusYes:String = "是";

		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			isWriteStatus = cardData["ISWRITEFILE"];
			if(isWriteStatus==isWriteStatusYes){
				SmartXMessage.show("已有附件,请勿重复上传！");
				
				return;
			}
			billCardPanel = cardPanel;
			var uploadWindow:TMAttachUploadWindow = new TMAttachUploadWindow();
			
			uploadWindow.addEventListener(BasicEvent.LOADDATA_SUCCESSFUL,uploadSuccessfulHandler);
		
			uploadWindow.keyValue = cardData["ID"];
			
			PopUpManager.addPopUp(uploadWindow,cardPanel,true);
			PopUpManager.centerPopUp(uploadWindow);
		}
		
		private function uploadSuccessfulHandler(event:BasicEvent):void{
			
			SmartXMessage.show("附件上传成功,附件列表该刷新啦！！");
			billCardPanel.dispatchEvent(new BillCardPanelEvent(BillCardPanelEvent.REFRESHCHILDLIST));
		}
		
		
	}
}