package com.apms.flex.modules.engine.llp
{
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.event.BasicEvent;
	import smartx.flex.components.event.BillCardPanelEvent;
	import smartx.flex.components.styletemplate.ifc.CardButtonListener;
	import smartx.flex.components.util.MemoryUtil;
	import smartx.flex.components.util.SmartXMessage;
	
	/**
	 * @author zzy
	 * @date Jun 14, 2011
	 * 编辑公告信息
	 */
	public class EngLlpPartnumSetListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
	
		private var llpWindow:EngLlpSelectWindow;
		
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			billCardPanel = cardPanel;
			
			llpWindow = new EngLlpSelectWindow();
			llpWindow.structId = cardData["STRUCTID"];
			
			llpWindow.addEventListener(CloseEvent.CLOSE,function(event:CloseEvent):void{
				PopUpManager.removePopUp(llpWindow);
			});
			llpWindow.selectedFunc = llpWindowSelectedFun;
			
			PopUpManager.addPopUp(llpWindow,desktop,true);
			PopUpManager.centerPopUp(llpWindow);
		}
		
		private function llpWindowSelectedFun():void{
			var partObj:Object = llpWindow.selectObj;
			
			cardData["PART_NO"] = partObj["PN"];
			cardData["LIMIT_TIME"] = partObj["LIMIT_TIME"];
			cardData["LIMIT_CYC"] = partObj["LIMIT_CYC"];
			cardData["CYC_ALTER1"] = partObj["CYC_ALTER1"];
			cardData["CYC_ALTER2"] = partObj["CYC_ALTER2"];
			
			cardData["PARTNUMID"] = partObj["ID"];
			
			billCardPanel.setDataValue(cardData);
			
			PopUpManager.removePopUp(llpWindow);
			llpWindow.selectedFunc = null;
			llpWindow = null;
			MemoryUtil.forceGC();
		}

		
	}
}