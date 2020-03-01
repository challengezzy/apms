package com.apms.flex.modules.apu.struct
{
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.styletemplate.ifc.ClientInterceptorIFC;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	public class ApuStructUpdateBefore implements ClientInterceptorIFC
	{
		private var dataValue:Object;
		
		private var structListPanel:BillListPanel;
		
		public function handler(obj:Object):void{
			structListPanel = obj as BillListPanel;
			var cardValue:Object = structListPanel.getSaveDataValueList();
			
			if(structListPanel.getSelectedRowValues().length != 1){
				SmartXMessage.show("请选择您要更换的APU！");
				return;
			}
			
		}		
	}
}