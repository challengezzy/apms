package com.apms.flex.modules.apu.correct
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
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	/**
	 * @author zzy
	 * @date Jun 14, 2011
	 * 编辑公告信息
	 */
	public class ApuHourCorEndListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
	
		private var selWindow:ApuCorPointChooser;
		
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			billCardPanel = cardPanel;
			
			selWindow = new ApuCorPointChooser();
			
			var asnComb:SimpleRefItemVO = cardData["APU_ID"] as SimpleRefItemVO;
			selWindow.asn =asnComb.name;
			
			selWindow.addEventListener(CloseEvent.CLOSE,function(event:CloseEvent):void{
				PopUpManager.removePopUp(selWindow);
			});
			selWindow.selectedFunc = selectedFun;
			
			PopUpManager.addPopUp(selWindow,desktop,true);
			PopUpManager.centerPopUp(selWindow);
		}
		
		private function selectedFun():void{
			var selObj:Object = selWindow.selectObj;
			
			cardData["END_TIME"] = selObj["DATATIME"];
			
			billCardPanel.setDataValue(cardData);
			
			PopUpManager.removePopUp(selWindow);
			selWindow.selectedFunc = null;
			selWindow = null;
			MemoryUtil.forceGC();
		}

		
	}
}