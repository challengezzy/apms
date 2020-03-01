package com.sep.flex.modules.a3
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
	
	import myreport.ReportEngine;
	import myreport.ReportViewer;
	import myreport.ReportViewer2;
	import myreport.export.ExportEvent;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.basic.DeskTopFrame;
	import smartx.flex.components.event.BasicEvent;
	import smartx.flex.components.util.MemoryUtil;
	import smartx.flex.components.util.OperatingTipUtil;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.vo.GlobalConst;
	
	public class A3CardPrintUtil
	{
		
		private var formService:RemoteObject;

		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var a3PromptVo:Object;
		private var suitlist:String;
		private var path:String;
		private var riskpointlist:ArrayCollection = new ArrayCollection();
		private var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
		private var toolsListts:ArrayCollection=new ArrayCollection();
		private var toolsListyb:ArrayCollection=new ArrayCollection();
		private var chemiacalList:ArrayCollection=new ArrayCollection();
		private var airmaterialList:ArrayCollection=new ArrayCollection();
		
		public function printA3Card(jobcardId:String):void{
						
			formService = new RemoteObject("sepService");
			formService.endpoint = endpoint;
			myreport.ReportViewer2.Instance.addEventListener(ExportEvent.EXPORT, OnExport);
			formService.getA3CardData.addEventListener(ResultEvent.RESULT,getA3CardDataHandler);					
			formService.getA3CardData.addEventListener(FaultEvent.FAULT,function(event:FaultEvent):void{
				OperatingTipUtil.endOperat();
				SmartXMessage.show("获取A3提示卡数据失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
			});
			
			formService.getA3CardData(jobcardId);
			OperatingTipUtil.startOperat("数据生成中..");
		}
		
		
		private function OnExport(event:ExportEvent):void
		{
			var file:FileReference;
			if(event.FileType == ExportEvent.FILE_TYPE_PDF)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "A3提示卡.pdf");
			}
			else if(event.FileType == ExportEvent.FILE_TYPE_XLS)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "A3提示卡.xls");
			}
		}
		
		private function PreviewReport():void
		{
			//source支持url:String，xml:XML，null参数类型
			myreport.ReportViewer2.Instance.Show(
				[
					"A3提示卡",
					"航材工具列表" 
				],
				[
					"com/sep/flex/modules/reportxml/XmlA3PromptCard.xml",
					"com/sep/flex/modules/reportxml/XmlAviationMaterials.xml"
				],
				[
					getKeypointTableData(),
					getSubReportTableDataToolSpecial()
				],
				[
					getParameterDataJobcard(),
					getParameterDataDevices()
				]);
			
			myreport.ReportViewer.Instance.width = 900;
			PopUpManager.centerPopUp(myreport.ReportViewer.Instance);
		}
		

		private function getKeypointTableData():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			for (var i:int = 0; i < riskpointlist.length; i++)
			{
				
				var keyPointMap:Object = riskpointlist[i];
				var checkType:String = keyPointMap["CHECKTYPE"];
				
				var img1:String = keyPointMap["IMAGEPATH1"]==null?"":keyPointMap["IMAGEPATH1"];
				var img2:String = keyPointMap["IMAGEPATH2"]==null?"":keyPointMap["IMAGEPATH2"];
				var img3:String = keyPointMap["IMAGEPATH3"]==null?"":keyPointMap["IMAGEPATH3"];

				var image1:String = "";
				var image2:String = "";
				var image3:String = "";
				if(img1!=""){
					image1 = keyPointMap["IMAGEPATH1"].substring(path.length ,keyPointMap["IMAGEPATH1"].length);
				}
				if(img2!=""){
					image2 = keyPointMap["IMAGEPATH2"].substring(path.length,keyPointMap["IMAGEPATH2"].length);
				}
				if(img3!=""){
					image3 = keyPointMap["IMAGEPATH3"].substring(path.length,keyPointMap["IMAGEPATH3"].length);
				}
				var hujian:Number=0;
				var bijian:Number=0;
				if(checkType == "20"){
					hujian =1;
				} else{
					bijian =1;
				}				
				var suit:String = keyPointMap["ADAPTORDESC"]==null?"":keyPointMap["ADAPTORDESC"];
				var keypointname:String ="";
				if(suit!=null && suit!=""){
					keypointname = "风险点"+(i+1)+":"+keyPointMap["DETAILDESC"] +"      适用描述："+keyPointMap["ADAPTORDESC"];
				}else{
					keypointname = "风险点"+(i+1)+":"+keyPointMap["DETAILDESC"];
				}
				var errorhis:String = "";
				if(keyPointMap["ERRHISTEXT1"]!=null){
					errorhis = "差错历史1："+keyPointMap["ERRHISTEXT1"];
				}
				if(keyPointMap["ERRHISTEXT2"]!=null){
					errorhis = errorhis +"\n"+ "差错历史2："+keyPointMap["ERRHISTEXT2"];
				}
				
				if(errorhis == ""){
					errorhis = "无差错历史。";
				}
				list.addItem({风险点名称: keypointname, 
					           	 必检:  bijian,
								 互检:  hujian,
							       风险点图1: image1, 
						                    风险点图2: image2,
							      风险点图3: image3,
							      差错历史: errorhis});
			}
			return list;
		}
		
		
		private function getA3CardDataHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var res:Object = event.result;
			
			a3PromptVo = res["jobcardbasic"];
			suitlist = res["suitlist"];
			riskpointlist = res["keypointList"] ;
			toolsListts = res["toolsListts"];
			toolsListyb = res["toolsListyb"] ;
			chemiacalList  = res["chemiacalList"];
			airmaterialList = res["airmaterialList"];
			path  = res["path"]; 
			PreviewReport();

			trace("............");
		}
		
		private function getParameterDataJobcard():Dictionary
		{
			var dict:Dictionary = new Dictionary();
			if(a3PromptVo["VERSIONINFO"]==null || a3PromptVo["VERSIONINFO"]==""){
				dict["工卡名称"]="工卡:" + a3PromptVo["AMM_NO"]+" " +a3PromptVo["NAME"] +"     D值："+a3PromptVo["DVALUE"];
			}else{
				dict["工卡名称"]="工卡:" + a3PromptVo["AMM_NO"]+" " +a3PromptVo["NAME"] + "     文件版次："+ a3PromptVo["VERSIONINFO"]+"     D值："+a3PromptVo["DVALUE"];
			}
			
			dict["工卡号"]=a3PromptVo["AMM_NO"]==null?"":a3PromptVo["AMM_NO"];
			dict["文件版次"]=a3PromptVo["VERSIONINFO"]==null?"":a3PromptVo["VERSIONINFO"];
			dict["D值"]=a3PromptVo["DVALUE"]==null?"":a3PromptVo["DVALUE"];
			dict["必检"]=a3PromptVo["MUSTCHECK"]==null?"":a3PromptVo["MUSTCHECK"];
			dict["互检"]=a3PromptVo["CROSSCHECK"]==null?"":a3PromptVo["CROSSCHECK"];
			dict["适用性"]="适用："+suitlist;
			return  dict;
		}
		
		private function getParameterDataDevices():Dictionary
		{
			var dict:Dictionary = new Dictionary();
			dict["主标题"] = "航材工具列表";
			dict["打印时间"] = new Date();
			dict["打印人"] = user;
			dict["工卡号"] = a3PromptVo["AMM_NO"];
			
			dict["通用工具表格"] = getSubReportTableDataTools();
			dict["化工件表格"] = getSubReportTableDataChemiacal();
			dict["航材表格"] = getSubReportTableDataAirmaterial();
			
			return dict;
		}
		
		/** 准备特殊工具表格数据 */ 
		private function getSubReportTableDataToolSpecial():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			if( toolsListts!=null){
				for(var i:int=0;i<toolsListts.length;i++){  
					var obj:Object = toolsListts.getItemAt(i);  
					list.addItem({
						名称: obj.NAME!=null?obj.NAME:"",
						规格: obj.SPEC!=null?obj.SPEC:"",
						数量: obj.COUNT!=null?obj.COUNT:"",
						件号: obj.PARTNO!=null?obj.PARTNO:"",
						备注:obj.REMARK!=null?obj.REMARK:""
						
					});
				}  
			}
			if(list.length<=0){
				list.addItem({
					名称: "",
					规格:"",
					数量:"",
					件号:"",
					备注:""
				});
			}
			return list;
		}
		
		/** 准备通用工具表格数据 */ 
		private function getSubReportTableDataTools():ArrayCollection
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
					名称:"",
					规格:"",
					数量:"",
					备注:""
				});
			}
			return list;
		}
		
		/** 准备化工件表格数据 */ 
		private function getSubReportTableDataChemiacal():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			if( chemiacalList!=null){
				for(var i:int=0;i<chemiacalList.length;i++){  
					var obj:Object = chemiacalList.getItemAt(i);  
					list.addItem({
						名称: obj.NAME!=null?obj.NAME:"",
						编号: obj.NUM!=null?obj.NUM:"",
						物料号: obj.SAPNUM!=null?obj.SAPNUM:"",
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
		
		/** 准备航材表格数据 */ 
		private function getSubReportTableDataAirmaterial():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			if( chemiacalList!=null){
				for(var i:int=0; i< airmaterialList.length; i++){  
					var obj:Object = airmaterialList.getItemAt(i);  
					list.addItem({
						名称: obj.NAME!=null?obj.NAME:"",
						件号: obj.PARTNO!=null?obj.PARTNO:"",
						物料号: obj.SAPNUM!=null?obj.SAPNUM:"",
						数量: obj.AMOUNT!=null?obj.AMOUNT:"",
						手册名称: obj.IPC!=null?obj.IPC:""
					});
				}  
			}
			if(list.length<=0){
				list.addItem({
					名称:"",
					件号:"",
					物料号:"",
					数量:"",
					手册名称:""
				});
			}
			return list;
		}
		
	}//end class
}