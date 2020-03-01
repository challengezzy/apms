package com.apms.flex.modules.workorder
{
	import com.apms.flex.modules.workorder.AmsWoSelectedViewWindow;
	import com.apms.flex.vo.ApmsUIConst;
	
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
	import smartx.flex.components.vo.GlobalConst;
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	/**
	 * @author zzy
	 * @date Jun 14, 2011
	 * AMS维修工单查询
	 */
	public class AmsWoSelectViewListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var selWindow:AmsWoSelectedViewWindow;
		
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			billCardPanel = cardPanel;
			
			selWindow = new AmsWoSelectedViewWindow();
			selWindow.addEventListener(CloseEvent.CLOSE,function(event:CloseEvent):void{
				PopUpManager.removePopUp(selWindow);
			});
			selWindow.selectedFunc = selectedFun;
			PopUpManager.addPopUp(selWindow,desktop,true);
			PopUpManager.centerPopUp(selWindow);
		}
		
		private function selectedFun():void{
			var selObj:Object = selWindow.selectObj;
			cardData["ACNUM"]= selObj["ACNO"];
			cardData["PLANSN"] = selObj["PLANSN"];
			cardData["WORKORDERSN"] = selObj["WORKORDERSN"];
			cardData["CONTEXTCN"] = selObj["CONTEXTCN"];
			cardData["OPDY"] = selObj["OPDY"];
			cardData["ENGSN"] = selObj["PARTSN"];
			
			billCardPanel.setDataValue(cardData);
			
			PopUpManager.removePopUp(selWindow);
			selWindow.selectedFunc = null;
			selWindow = null;
			MemoryUtil.forceGC();
		}
		
	}
}