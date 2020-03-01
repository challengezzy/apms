package com.apms.flex.modules.apu.swap
{
	import com.apms.flex.modules.apu.swap.ApuSwapWindow;
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
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleHashVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	
	public class ApuSwapListListener implements ListButtonListener
	{		
		private var destination:String = GlobalConst.SERVICE_FORM;
		private var formService:RemoteObject;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var listPanel:BillListPanel;
		
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var swapWindow:ApuSwapWindow;
		
		public function ApuSwapListListener()
		{
			formService = new RemoteObject(destination);
			if(endpoint != null)
				formService.endpoint = endpoint;
			
		}
		
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			if(listPanel.getSelectedRowValues().length != 1){
				SmartXMessage.show("请选择您要更换的APU！");
				return;
			}
			var selectObj:Object = listPanel.getSelectedRowValue();
			var positionVo:SimpleComboxItemVO = selectObj["POSITION"] as SimpleComboxItemVO;
			var position:String = positionVo.id;
			if(position != "1"){//如果不是 在翼状态，不进行拆换
				SmartXMessage.show("APU非在翼状态，不需要进行拆换！");
				return;
			}
			
			swapWindow = new ApuSwapWindow;
			swapWindow.curApuSn = selectObj["APUSN"];
			swapWindow.infoDate = selectObj["DATATIME"];
			var modelRefVo:SimpleRefItemVO = selectObj["APUMODELID"] as SimpleRefItemVO;
			swapWindow.apumodelId = modelRefVo.id;
			
			PopUpManager.addPopUp(swapWindow,desktop,true);
		    PopUpManager.centerPopUp(swapWindow);
			
		}
		
	}
}