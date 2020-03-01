package com.apms.flex.modules.fdimu
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
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.OperatingTipUtil;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.GlobalConst;
	
	/**
	 * @author zzy
	 * @date Aug 10, 2011
	 */
	public class FdimuFileDownloadListener implements ListButtonListener
	{
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		private var selectObj:Object;
		
		public function FdimuFileDownloadListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
			formService.downloadFdimuFile.addEventListener(ResultEvent.RESULT,fileDownloadHandler);
			formService.downloadFdimuFile.addEventListener(FaultEvent.FAULT,faultHandler);
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			if(listPanel.getSelectedRowValues().length != 1){
				SmartXMessage.show("请选择一个文件记录下载！");
				return;
			}
			selectObj = listPanel.getSelectedRowValue();
			
			SmartXMessage.show("确认要下载该文件？",SmartXMessage.MESSAGE_CONFIRM,null,listPanel,confirmHandler);
		}
		
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("文件下载失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
		
		private function fileDownloadHandler(event:ResultEvent):void{
			var url:String = event.result as String;
			navigateToURL(new URLRequest(encodeURI(url)), "_blank");
			OperatingTipUtil.endOperat();
			listPanel.query();
		}
		
		private function confirmHandler(event:CloseEvent):void{
			if(event.detail == Alert.YES){
				OperatingTipUtil.startOperat("正在下载......",listPanel);
				
				formService.downloadFdimuFile(selectObj["ID"],selectObj["FILENAME"]);
			}
		}
		
	}
}