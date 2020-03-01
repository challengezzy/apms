package com.apms.flex.modules.flight
{
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

	public class DefectRemindInsListener implements ListButtonListener
	{
		private var listPanel:BillListPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var defectremindInsInfoViewWindow:DefectremindInsInfoViewWindow;
		public function DefectRemindInsListener(){
		}
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条故障信息！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条故障信息！");
					return;
				}
				var selectObj:Object = listPanel.getSelectedRowValue();
				var defectremindid:String = selectObj["ID"];
				defectremindInsInfoViewWindow = new DefectremindInsInfoViewWindow();
				defectremindInsInfoViewWindow.defectremindid=defectremindid;
				PopUpManager.addPopUp(defectremindInsInfoViewWindow,desktop,true);
				PopUpManager.centerPopUp(defectremindInsInfoViewWindow);
			}
			
		}
	}
}