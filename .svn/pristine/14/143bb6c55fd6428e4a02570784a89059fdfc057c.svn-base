package com.sep.flex.modules.fileManager
{
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
	import com.apms.flex.vo.ApmsUIConst;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	/**
	 * @author zzy
	 * @date Jun 14, 2011
	 * 添加公告的附件信息
	 */
	public class JKAttachManageOfListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var taskId:String;
		private var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
		private var itemVo:SimpleRefItemVO;
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			if(cardData["FORMSERVICE_MODIFYFLAG"] == "insert"){
				SmartXMessage.show("请先补充完资料保存 再上传文件！");
				return;
			}
			billCardPanel = cardPanel;
			var uploadWindow:JKAttachUploadWindow = new JKAttachUploadWindow();
			
			uploadWindow.addEventListener(BasicEvent.LOADDATA_SUCCESSFUL,uploadSuccessfulHandler);
//			uploadWindow.tableName = "PUB_MESSAGES";
//			uploadWindow.keyColumn = "公告附件";
			uploadWindow.keyValue = cardData["ID"];
			
			itemVo= cardData["JOBCARDID"];
			uploadWindow.jobCardId = itemVo.id;

			uploadWindow.user =user;
			
			
			PopUpManager.addPopUp(uploadWindow,cardPanel,true);
			PopUpManager.centerPopUp(uploadWindow);
		}
		
		private function uploadSuccessfulHandler(event:BasicEvent):void{
			
			SmartXMessage.show("附件上传成功,附件列表该刷新啦！！");
			billCardPanel.dispatchEvent(new BillCardPanelEvent(BillCardPanelEvent.REFRESHCHILDLIST));
		}
	}
}