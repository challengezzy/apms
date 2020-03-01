package com.apms.flex.modules.flight
{
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
	 * 编辑公告信息
	 */
	public class FlightSelectViewListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		
		private var selWindow:FlightSelectedViewWindow;
		
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			billCardPanel = cardPanel;
			
			selWindow = new FlightSelectedViewWindow();
			selWindow.addEventListener(CloseEvent.CLOSE,function(event:CloseEvent):void{
				PopUpManager.removePopUp(selWindow);
			});
			selWindow.selectedFunc = selectedFun;
			PopUpManager.addPopUp(selWindow,desktop,true);
			PopUpManager.centerPopUp(selWindow);
		}
		
		private function selectedFun():void{
			var selObj:Object = selWindow.selectObj;
			//航班号
			cardData["FLIGHTID"]= selObj["FLT_PK"];
			cardData["FLT_NO"] = selObj["FLIGHTNO"];
			//飞机
			cardData["ACID"]=new SimpleRefItemVO();
			cardData["ACID"].id=selObj["ACID"];
			cardData["ACID"].code=selObj["ACMODEL"];
			cardData["ACID"].name=selObj["ACNUM"];
			cardData["ACNUM"] = selObj["ACNUM"];
			cardData["ACMODEL"] = selObj["ACMODEL"];
			//机场
			cardData["AIRPORTID"]=new SimpleRefItemVO();
			cardData["AIRPORTID"].id=selObj["AIRPORTID"].id;
			cardData["AIRPORTID"].code=selObj["DEP_APT"];
			cardData["AIRPORTID"].name=selObj["AIRPORTNAME"];
			//计划起飞和到达时间
			cardData["STD"] = selObj["STD"];
			cardData["STA"] = selObj["STA"];
			//预计完成时间
			cardData["ESTIMATEFISHTIME"] = selObj["ETA"];
			//延误时间
			cardData["DALAYTIME"] = selObj["DELAY_TIME"];
			
			billCardPanel.setDataValue(cardData);
			
			PopUpManager.removePopUp(selWindow);
			selWindow.selectedFunc = null;
			selWindow = null;
			MemoryUtil.forceGC();
		}
		
	}
}