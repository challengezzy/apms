package com.apms.flex.modules.flight
{
	/*import com.apms.flex.vo.ApmsUIConst;

	
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
	
	
	public class PrintDDReportViewListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var previewWindow:PrintDDReportWindow;
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		
		public function buttonClick(cardPanel:BillCardPanel):void{
			
			cardData = cardPanel.getDataValue();
			previewWindow = new PrintDDReportWindow();
			previewWindow.ddReportVo=cardData;
			PopUpManager.addPopUp(previewWindow,desktop,true);
			PopUpManager.centerPopUp(previewWindow);
		}
	}*/
	import com.apms.flex.modules.flight.PrintDDReportWindow;
	import com.cool.components.print.CoolPrintJob;
	import com.cool.components.print.PrintPreviewPanel;
	import mx.managers.PopUpManager;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.SimpleHashVO;
	
	public class PrintDDReportViewListener implements ListButtonListener
	{  
		private var listPanel:BillListPanel;
		private var printDDviewWindow:PrintDDReportWindow;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		public function buttonClick(listPanel:BillListPanel):void{
			this.listPanel = listPanel;
			
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条DD单数据！");
				return;
			}else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条DD单数据！");
					return;
				}
				printDDviewWindow=new PrintDDReportWindow();
				var selectObj:Object = listPanel.getSelectedRowValue();
				printDDviewWindow.ddReportVo=selectObj;
				PopUpManager.addPopUp(printDDviewWindow,desktop,true);
				PopUpManager.centerPopUp(printDDviewWindow);
			}
			
		}
	}
}