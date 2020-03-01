package com.apms.flex.modules.apu
{
	import com.apms.flex.modules.apu.management.ApuAttachManageofApuManageWindow;
	import com.apms.flex.vo.ApmsUIConst;
	import com.flexmonster.pivot.model.report.DestinationType;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.OperatingTipUtil;
	import smartx.flex.components.util.SmartXMessage;

	
	public class AttachQueryOfApuLoadListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var formService:RemoteObject=new RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条附件信息！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条附件信息！");
					return;
				}
				SmartXMessage.show("确认要下载该文件？",SmartXMessage.MESSAGE_CONFIRM,null,listPanel,confirmHandler);
	    	}
		}
		private function confirmHandler(event:CloseEvent):void{
			var obj:Object=listPanel.getSelectedRowValue();
			if(event.detail == Alert.YES){
				formService = new RemoteObject(destination);
				if(endpoint != null)
				formService.endpoint = endpoint;
				formService.downloadFdimuFile.addEventListener(ResultEvent.RESULT,fileDownloadHandler);
				formService.downloadFdimuFile.addEventListener(FaultEvent.FAULT,faultHandler);
				
				OperatingTipUtil.startOperat("正在下载..	....",listPanel);
				formService.downloadFdimuFile(obj["ID"],obj["FILENAME"]);
			}
		}
		
		private function fileDownloadHandler(event:ResultEvent):void{
			var url:String = event.result as String;
			navigateToURL(new URLRequest(encodeURI(url)), "_blank");
			OperatingTipUtil.endOperat();
			//listPanel.query();
		}
		private function faultHandler(event:FaultEvent):void{
			OperatingTipUtil.endOperat();
			SmartXMessage.show("文件下载失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
		}
	}
}