package com.sep.flex.modules.nrcard
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
	
	public class NrCardPrintUtil
	{
		
		private var formService:RemoteObject;

		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
		
		private var nrCardVo:Object;
		private var actionlist:ArrayCollection = new ArrayCollection();//工作步骤信息
		private var toolList:ArrayCollection = new ArrayCollection();
		private var partList:ArrayCollection = new ArrayCollection();
		private var materialList:ArrayCollection = new ArrayCollection();
		
		public function printA3Card(jobcardId:String):void{
						
			formService = new RemoteObject("sepService");
			formService.endpoint = endpoint;
			myreport.ReportViewer2.Instance.addEventListener(ExportEvent.EXPORT, OnExport);
			formService.getNrCardData.addEventListener(ResultEvent.RESULT,getNrCardDataHandler);					
			formService.getNrCardData.addEventListener(FaultEvent.FAULT,function(event:FaultEvent):void{
				OperatingTipUtil.endOperat();
				SmartXMessage.show("获取非例行工单数据失败",SmartXMessage.MESSAGE_ERROR,event.fault.faultString);
			});
			
			formService.getNrCardData(jobcardId);
			OperatingTipUtil.startOperat("数据生成中..");
		}
		
		
		private function OnExport(event:ExportEvent):void
		{
			var file:FileReference;
			if(event.FileType == ExportEvent.FILE_TYPE_PDF)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "非例行工单.pdf");
			}
			else if(event.FileType == ExportEvent.FILE_TYPE_XLS)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "非例行工单.xls");
			}
		}
		
		private function getNrCardDataHandler(event:ResultEvent):void{
			OperatingTipUtil.endOperat();
			var res:Object = event.result;
			nrCardVo = res["nrcard"];
			actionlist = res["actionList"];
			
			toolList = res["toolList"];
			partList = res["partList"];
			materialList = res["materialList"];
			
			PreviewReport();
			
			trace("............");
		}
		
		private function PreviewReport():void
		{
			//source支持url:String，xml:XML，null参数类型
			myreport.ReportViewer2.Instance.Show(
				["非例行工单" ],
				["com/sep/flex/modules/reportxml/XmlNonRoutineJobCard.xml" ],
				[ getActionStepTableData() ],
				[ getParameterDataNrcard() ]  );
			
			myreport.ReportViewer.Instance.width = 900;
			PopUpManager.centerPopUp(myreport.ReportViewer.Instance);
		}
		
		private function getParameterDataNrcard():Dictionary
		{
			var dict:Dictionary = new Dictionary();
			
			//dict[""] = null2Blank(nrCardVo[""]);
			dict["CARDNO"] = null2Blank(nrCardVo["CARDNO"]);
			dict["ATA"] = null2Blank(nrCardVo["ATA"]);
			dict["ACNUM"] = null2Blank(nrCardVo["ACNUM"]);
			dict["ACTYPE"] = null2Blank(nrCardVo["ACTYPE"]);
			dict["CHECKTYPE"] = null2Blank(nrCardVo["CHECKTYPE"]);
			dict["SKILL"] = null2Blank(nrCardVo["SKILL"]);
			dict["WORKORDERNO"] = null2Blank(nrCardVo["WORKORDERNO"]);
			dict["ORIGINATINGDOC"] = null2Blank(nrCardVo["ORIGINATINGDOC"]);
			dict["FAULTMARKNO"] = null2Blank(nrCardVo["FAULTMARKNO"]);
			dict["FAULTMARKZONE"] = null2Blank(nrCardVo["FAULTMARKZONE"]);
			dict["FAULTDESC"] = null2Blank(nrCardVo["FAULTDESC"]);
			dict["REFDOC_REV"] = null2Blank(nrCardVo["REFDOC_REV"]);
			dict["GROUNDTIME"] = null2Blank(nrCardVo["GROUNDTIME"]);
			
			if(nrCardVo["PLAN_MH"] == null || nrCardVo["PLAN_MH"]==""){
				dict["PLAN_MH"] = "";
			}else{
				dict["PLAN_MH"] = nrCardVo["PLAN_MH"]+"H";
			}
				
			dict["ACT_MH"] = "";
			
			//是否超出手册限制
			dict["NEEDSUPPORT"] = 0;
			dict["NOTNEEDSUPORT"] = 0;
			if( nrCardVo["ISEXCEEDLIMIT"] == "1"){
				dict["NEEDSUPPORT"] = 1;
			}else if(nrCardVo["ISEXCEEDLIMIT"] == "0"){
				dict["NOTNEEDSUPORT"] = 1;
			}
			
			//是否需要安全检查单
			dict["NEEDSECURITY"] = 0;
			dict["NOTNEEDSECURITY"] = 0;
			if( nrCardVo["ISSECURITYCHECK"] == "1"){
				dict["NEEDSECURITY"] = 1;
			}else if(nrCardVo["ISSECURITYCHECK"] == "0"){
				dict["NOTNEEDSECURITY"] = 1;
			}
			
			//维修类别
			if(nrCardVo["REPCLASS"] == "1"){
				dict["ISRII"] = 1;
			}else if(nrCardVo["REPCLASS"] == "2"){
				dict["ISCDCCL"] = 1;
			}else if(nrCardVo["REPCLASS"] == "3"){
				dict["ISMAJORREP"] = 1;
			}else if(nrCardVo["REPCLASS"] == "4"){
				dict["ISETOPS"] = 1;
			}else if(nrCardVo["REPCLASS"] == "5"){
				dict["ISDM"] = 1;
			}else if(nrCardVo["REPCLASS"] == "6"){
				dict["ISGGY"] = 1;
			}else if(nrCardVo["REPCLASS"] == "7"){
				dict["ISADCAD"] = 1;
			}
			
			dict["ISSECURITYCHECK"] = null2Blank(nrCardVo["ISSECURITYCHECK"]);
			dict["CHECKCONTENT"] = null2Blank(nrCardVo["CHECKCONTENT"]);
			dict["PREPARED_USER"] = null2Blank(nrCardVo["PREPARED_USER"]);
			dict["APPROVED_USER"] = null2Blank(nrCardVo["APPROVED_USER"]);
			dict["APPROVED_DATE"] = null2Blank(nrCardVo["APPROVEDDATE_STR"]);
			dict["STATION"] = "";

			addParts(dict);
			addMaterials(dict);
			addTools(dict);
			
			return  dict;
		}
		
		private function addParts(dict:Dictionary):void{
			for(var i:int=1; i<=4;i++){
				//ITEM, PARTNAME, QTY, PARTNO_NO, BATCHNO_NO, PARTNO_OFF, BATCHNO_OFF
				if( partList.length >= i ){
					var obj:Object = partList.getItemAt(i-1);
					dict["P_ITEM_"		 +i] = null2Blank( obj["ITEM"] );
					dict["P_PARTNAME_"   +i] = null2Blank( obj["PARTNAME"] );
					dict["P_QTY_"		 +i] = null2Blank( obj["QTY"] );
					dict["P_PARTNO_NO_"  +i] = null2Blank( obj["PARTNO_NO"] );
					dict["P_BATCHNO_NO_" +i] = null2Blank( obj["BATCHNO_NO"] );
					dict["P_PARTNO_OFF_" +i] = null2Blank( obj["PARTNO_OFF"] );
					dict["P_BATCHNO_OFF_"+i] = null2Blank( obj["BATCHNO_OFF"] );
				}else{
					dict["P_ITEM_"		 +i] = "";
					dict["P_PARTNAME_"   +i] = "";
					dict["P_QTY_"		 +i] = "";
					dict["P_PARTNO_NO_"  +i] = "";
					dict["P_BATCHNO_NO_" +i] = "";
					dict["P_PARTNO_OFF_" +i] = "";
					dict["P_BATCHNO_OFF_"+i] = "";
				}
			}
		}
		
		private function addTools(dict:Dictionary):void{
			for(var i:int=1; i<=4;i++){
				//ITEM, NAME, PARTNO, QTY, REMARK
				if( toolList.length >= i ){
					var obj:Object = toolList.getItemAt(i-1);
					dict["T_ITEM_"	 +i] = null2Blank( obj["ITEM"] );
					dict["T_NAME_"   +i] = null2Blank( obj["NAME"] );
					dict["T_PARTNO_" +i] = null2Blank( obj["PARTNO"] );
					dict["T_QTY_"	 +i] = null2Blank( obj["QTY"] );
					dict["T_REMARK_" +i] = null2Blank( obj["REMARK"] );
				}else{
					dict["T_ITEM_"	 +i] = "";
					dict["T_NAME_"   +i] = "";
					dict["T_PARTNO_" +i] = "";
					dict["T_QTY_"    +i] = "";
					dict["T_REMARK_" +i] = "";
				}
			}
		}
		
		private function addMaterials(dict:Dictionary):void{
			for(var i:int=1; i<=4;i++){
				//ITEM, NAME, PARTNO, IPC, QTY, REMARK
				if( materialList.length >= i ){
					var obj:Object = materialList.getItemAt(i-1);
					dict["M_ITEM_"	 +i] = null2Blank( obj["ITEM"] );
					dict["M_NAME_"   +i] = null2Blank( obj["NAME"] );
					dict["M_PARTNO_" +i] = null2Blank( obj["PARTNO"] );
					dict["M_IPC_"	 +i] = null2Blank( obj["IPC"] );
					dict["M_QTY_"    +i] = null2Blank( obj["QTY"] );
					dict["M_REMARK_" +i] = null2Blank( obj["REMARK"] );
				}else{
					dict["M_ITEM_"	 +i] = "";
					dict["M_NAME_"   +i] = "";
					dict["M_PARTNO_" +i] = "";
					dict["M_IPC_"    +i] = "";
					dict["M_QTY_"    +i] = "";
					dict["M_REMARK_" +i] = "";
				}
			}
		}
		
		
		private function null2Blank(obj:Object):String{
			if(obj == null)
				return "";
			else
				return obj as String;
		}

		private function getActionStepTableData():ArrayCollection
		{
			var list:ArrayCollection = new ArrayCollection();
			for (var i:int = 0; i < actionlist.length; i++)
			{
				var stepMap:Object = actionlist[i];
				var ITEM:String = null2Blank(  stepMap["ITEM"]);
				var ACTION:String = null2Blank( stepMap["RECTIFY_ACTION"]);
				var MECH:String = null2Blank( stepMap["MECH"]);
				var INSP:String = null2Blank( stepMap["INSP"]);
				
				list.addItem({ITEM   : ITEM, 
							  ACTION : ACTION,
							  MECH   : MECH,
							  INSP   : INSP
					         });
			}
			
			if(list.length<=0){
				list.addItem({
					ITEM   : "", 
					ACTION : "",
					MECH   : "",
					INSP   : ""
				});
			}
			
			return list;
		}
		
		
	}//end class
}