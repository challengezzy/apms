package com.sep.flex.modules.buttonListener
{
	import com.apms.flex.vo.ApmsUIConst;
	import com.cool.components.print.CoolPrintJob;
	import com.cool.components.print.PrintPreviewPanel;
	
	import flash.net.FileReference;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import myreport.ReportViewer;
	import myreport.export.ExportEvent;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.core.BillListPanel;
	import smartx.flex.components.event.BasicEvent;
	import smartx.flex.components.event.BillCardPanelEvent;
	import smartx.flex.components.styletemplate.ifc.ListButtonListener;
	import smartx.flex.components.util.MemoryUtil;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.GlobalConst;
	import smartx.flex.components.vo.SimpleComboxItemVO;
	import smartx.flex.components.vo.SimpleHashVO;
	import smartx.flex.components.vo.SimpleRefItemVO;
	
	/**
	 * @author zzy
	 * @date Jun 14, 2011
	 * 编辑公告信息
	 */
	public class AviationMaterialsListener implements ListButtonListener
	{
		
		private var listData:Object;
		private var listPanel:BillListPanel;
		private var formService:RemoteObject;

		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var a3PromptVo:Object;
		private var suitlist:String;
		private var path:String;
		private var riskpointlist:ArrayCollection = new ArrayCollection();
		
		
		[Bindable]
		private var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
		private var toolsListts:ArrayCollection=new ArrayCollection();
		private var toolsListyb:ArrayCollection=new ArrayCollection();
		private var chemiacalList:ArrayCollection=new ArrayCollection();
		
		public function buttonClick(listPanel:BillListPanel):void{
						
			if(listPanel.getSelectedRowValues()==null){
				SmartXMessage.show("请选择一条工卡！");
				return;
			}
			else{
				if(listPanel.getSelectedRowValues().length!=1){
					SmartXMessage.show("请选择一条工卡！");
					return;
				}
				listData = listPanel.getSelectedRowValue();
				this.a3PromptVo = listData;
				formService = new RemoteObject("sepService");
				formService.endpoint = endpoint;
				ReportViewer.Instance.addEventListener(ExportEvent.EXPORT, OnExport);
				formService.getAviationMaterials.addEventListener(ResultEvent.RESULT,getAviationMaterialsHandler);					
				formService.getAviationMaterials.addEventListener(FaultEvent.FAULT,function(event:FaultEvent):void{
					SmartXMessage.show("获取航材列表数据失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
				});
				formService.getAviationMaterials(listData["ID"].toString());	
				
				
//				PreviewReport();

				
			}	
		}
		
		
		private function OnExport(event:ExportEvent):void
		{
			var file:FileReference;
			if(event.FileType == ExportEvent.FILE_TYPE_PDF)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "航材报表.pdf");
			}
			else if(event.FileType == ExportEvent.FILE_TYPE_XLS)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "航材报表.xls");
			}
		}
		
		
		
		private function getAviationMaterialsHandler(event:ResultEvent):void{
			var res:Object = event.result;
			toolsListts = res["toolsListts"];
			toolsListyb = res["toolsListyb"] ;
			chemiacalList  = res["chemiacalList"];
			
			HandleReport(1);
			myreport.ReportViewer.Instance.width = 750;
			PopUpManager.centerPopUp(myreport.ReportViewer.Instance);
		}
		
		/**
		 * @param cmd: 0编辑, 1预览, 2直接打印
		 */
		private function HandleReport(mode:int):void
		{
			var url:String = "com/sep/flex/modules/reportxml/XmlAviationMaterials.xml";
			var table:* = GetSubReportTableData1();
			var params:* = GetParameterData();
			
			
			switch(mode)
			{
				case 0://编辑
					//source支持url:String，xml:XML，null参数类型
					myreport.ReportDesigner.Instance.Show(url, table, params);
					break;
				case 1://预览
					//source支持url:String，xml:XML，null参数类型
					myreport.ReportViewer.Instance.Show(url, table, params);
					break;
				case 2://直接打印
					myreport.ReportEngine.LoadAndPrintAsync(url, table, params);
					break;
			}
			
		}
		
		
		
		private function GetParameterData():Dictionary
		{
			var dict:Dictionary = new Dictionary();
			dict["主标题"] = "航材工具列表";
			dict["打印时间"] = new Date();
			dict["打印人"] = user;
		
			
			
			dict["子报表2表格"] = GetSubReportTableData2();
			
			dict["子报表3表格"] = GetSubReportTableData3();
			
			
			return dict;
		}
		/** 准备子报表1表格数据 */ 
		private function GetSubReportTableData1():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			if( toolsListts!=null){
				for(var i:int=0;i<toolsListts.length;i++){  
					var obj:Object = toolsListts.getItemAt(i);  
					list.addItem({
						名称: obj.NAME!=null?obj.NAME:"",
						规格: obj.SPEC!=null?obj.SPEC:"",
						数量: obj.COUNT!=null?obj.COUNT:"",
						备注:obj.REMARK!=null?obj.REMARK:""
						
					});
				}  
			}
			if(list.length<=0){
				list.addItem({
					名称: "",
					规格:"",
					数量:"",
					备注:""
				});
			}
			return list;
		}
		
		/** 准备子报表2表格数据 */ 
		private function GetSubReportTableData2():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			if( toolsListyb!=null){
				for(var i:int=0;i<toolsListyb.length;i++){  
					var obj:Object = toolsListyb.getItemAt(i);  
					list.addItem({
						名称: obj.NAME!=null?obj.NAME:"",
						规格: obj.SPEC!=null?obj.SPEC:"",
						数量: obj.COUNT!=null?obj.COUNT:"",
						备注:obj.REMARK!=null?obj.REMARK:""
						
					});
				}  
			}
			if(list.length<=0){
				list.addItem({
					名称: "",
					规格:"",
					数量:"",
					备注:""
				});
			}
			return list;
		}
		/** 准备子报表3表格数据 */ 
		private function GetSubReportTableData3():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			if( chemiacalList!=null){
				for(var i:int=0;i<chemiacalList.length;i++){  
					var obj:Object = chemiacalList.getItemAt(i);  
					list.addItem({
						名称: obj.NAME!=null?obj.NAME:"",
						编号:obj.NUM!=null?obj.NUM:"",
						物料号:obj.SAPNUM!=null?obj.SAPNUM:"",
						替代号: obj.REPNUM!=null?obj.REPNUM:"",
						备注: obj.REMARK!=null?obj.REMARK:""
					});
				}  
			}
			if(list.length<=0){
				list.addItem({
					名称:"",
					编号:"",
					物料号:"",
					替代号:"",
					备注:""
				});
			}
			return list;
		}
	}
}