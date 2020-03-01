package com.apms.flex.modules.basic.interceptor
{
	import com.apms.flex.modules.basic.AirPositionTipViewWindow;
	import com.apms.flex.vo.ApmsUIConst;
	
	import mx.collections.ArrayCollection;
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
	import smartx.flex.components.vo.SimpleHashVO;

	public class AirPositionTipViewListener implements ListButtonListener
	{
		private var listPanel:BillListPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var airPositionTipViewWindow:AirPositionTipViewWindow;
		public function AirPositionTipViewListener(){
		}
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条机位信息！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条机位信息！");
					return;
				}
				var selectObj:Object = listPanel.getSelectedRowValue();
				var flightPositionId:String = selectObj["ID"];
				airPositionTipViewWindow = new AirPositionTipViewWindow();
				airPositionTipViewWindow.ap_positionid=flightPositionId;
				airPositionTipViewWindow.airport_name=selectObj["AIRPORTID"].name;
				airPositionTipViewWindow.ap_name=selectObj["CODE"];
				PopUpManager.addPopUp(airPositionTipViewWindow,desktop,true);
				PopUpManager.centerPopUp(airPositionTipViewWindow);
			}
			
		}
	}
}