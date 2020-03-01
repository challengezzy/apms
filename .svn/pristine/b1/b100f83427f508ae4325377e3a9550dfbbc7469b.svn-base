package com.apms.flex.modules.flight
{
	import com.apms.flex.vo.ApmsUIConst;
	import com.cool.components.print.CoolPrintJob;
	import com.cool.components.print.PrintPreviewPanel;
	
	import flash.net.FileReference;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import myreport.ReportViewer;
	import myreport.export.ExportEvent;
	
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
	public class PrintUnusualReportViewListener implements CardButtonListener
	{
		
		private var cardData:Object;
		private var billCardPanel:BillCardPanel;
		private var desktop:DeskTopFrame = ClientEnviorment.getInstance().getVar(ClientEnviorment.DESKTOP_FRAME) as DeskTopFrame;
		private var previewWindow:UnusualReportPrintWindow;
		private var destination:String = ApmsUIConst.APMS_SERVICE;
		private var endpoint:String  = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_ENDPOINT) as String;
		private var unusualReportVo:Object;
		public function buttonClick(cardPanel:BillCardPanel):void{
			cardData = cardPanel.getDataValue();
			previewWindow = new UnusualReportPrintWindow();
			this.unusualReportVo=cardData;
			
			ReportViewer.Instance.addEventListener(ExportEvent.EXPORT, OnExport);
			PreviewReport();
		}
		
		private function OnExport(event:ExportEvent):void
		{
			var file:FileReference;
			if(event.FileType == ExportEvent.FILE_TYPE_PDF)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "国航工程技术分公司维修基地航班不正常快报.pdf");
			}
			else if(event.FileType == ExportEvent.FILE_TYPE_XLS)
			{
				file = new FileReference();
				//保存到本地，该方法要Flash player 10以上
				file.save(event.Bytes, "国航工程技术分公司维修基地航班不正常快报.xls");
			}
		}
		
		private function PreviewReport():void
		{
			//source支持url:String，xml:XML，null参数类型
			myreport.ReportViewer.Instance.Show("com/apms/flex/reportxml/XmlUnusualFlightReport.xml", GetTableData(), GetParameterData());
			myreport.ReportViewer.Instance.width = 900;
			PopUpManager.centerPopUp(myreport.ReportViewer.Instance);
		}
		private function GetTableData():ArrayCollection
		{
			var list:ArrayCollection=new ArrayCollection();
			
			return list;
		}
		private function GetParameterData():Dictionary
		{
			var dict:Dictionary = new Dictionary();
			dict["主标题"] = "国航工程技术分公司维修基地航班不正常快报";
			dict["打印时间"] = new Date();
			
			var solution:String=unusualReportVo["SOLUTION"];//处理措施
			var solutionandcauseanalysis:String="";
			if(solution==null){
				solutionandcauseanalysis="";
			}else{
				solutionandcauseanalysis="处理措施："+solution;
			}
			var causeanalysis:String=unusualReportVo["CAUSEANALYSIS"];//原因分析
			if(causeanalysis==null){
				solutionandcauseanalysis=solutionandcauseanalysis+"";
			}else{
				solutionandcauseanalysis=solutionandcauseanalysis+"\n"+"原因分析："+causeanalysis;
			}
			
			//类型
			var type:Number=-1;
			var typeObj:SimpleComboxItemVO=unusualReportVo["TYPE"] as  SimpleComboxItemVO;
			type=Number(typeObj["id"]);//类别
			var fanhang:Number=0;//类别
			var gaihang:Number=0;
			var huahui:Number=0;
			var huanji:Number=0;
			var huojing:Number=0;
			var dimiantingche:Number=0;
			var kongzhongtingche:Number=0;
			var yanwu:Number=0;
			var quxiao:Number=0;
			var chongchupaodao:Number=0;
			var zhongzhiqifei:Number=0;
			var zhongduanqifei:Number=0;
			var qita_type:Number=0;
			switch(type){
				case 0:fanhang=1;break;
				case 1:gaihang=1;break;
				case 2:huahui=1;break;
				case 3:huanji=1;break;
				case 4:huojing=1;break;
				case 5:dimiantingche=1;break;
				case 6:kongzhongtingche=1;break;
				case 7:yanwu=1;break;
				case 8:quxiao=1;break;
				case 9:chongchupaodao=1;break;
				case 10:zhongzhiqifei=1;break;
				case 11:zhongduanqifei=1;break;
				case 12:qita_type=1;break;
			}
			
			//发生阶段
			var occurphase:Number=-1;
			occurphase=Number(unusualReportVo["OCCURPHASE"]["id"]);
			var dimian:Number=0;
			var qifei:Number=0;
			var xunhang:Number=0;
			var zhuolu:Number=0;
			var huaxing:Number=0;
			var pasheng:Number=0;
			var xiajiang:Number=0;
			var qita_fashengjieduan:Number=0;
			switch(occurphase){
				case 0:dimian=1;break;
				case 1:qifei=1;break;
				case 2:xunhang=1;break;
				case 3:zhuolu=1;break;
				case 4:huaxing=1;break;
				case 5:pasheng=1;break;
				case 6:xiajiang=1;break;
				case 7:qita_fashengjieduan=1;break;
			}
			
			var hangqian:Number=0;
			var guozhan:Number=0;
			var hanghou:Number=0;
			var dingjian:Number=0;
			var zhiling:Number=0;
			var qita_weixiujibie:Number=0;
			var maintenancelevel:Number=-1;
			maintenancelevel=Number(unusualReportVo["MAINTENANCELEVEL"]["id"]);//维修级别
			switch(maintenancelevel){
				case 0:hangqian=1;break;
				case 1:guozhan=1;break;
				case 2:hanghou=1;break;
				case 3:dingjian=1;break;
				case 4:zhiling=1;break;
				case 5:qita_weixiujibie=1;break;
			}
			
			//是否属于三次故障
			var isthirdfault:Number=-1;
			isthirdfault=Number(unusualReportVo["ISTHIRDFAULT"]["id"]);
			var shi:Number=0;
			var fou:Number=0;
			var busheji:Number=0;
			switch(isthirdfault){
				case 0:shi=1;break;
				case 1:fou=1;break;
				case 2:busheji=1;break;
			}
			//机务分类
			var maintenancereasontype:Number=-1;
			maintenancereasontype=Number(unusualReportVo["MAINTENANCEREASONTYPE"]["id"]);//工程机务分类
			var jixieguzhang:Number=0;
			var weixiubudang:Number=0;
			var shengchanjihua:Number=0;
			var shebeishishi:Number=0;
			var renli:Number=0;
			var hangcai:Number=0;
			var gongcheng:Number=0;
			var zhiliang:Number=0;
			switch(maintenancereasontype){
				case 0:jixieguzhang=1;break;
				case 1:weixiubudang=1;break;
				case 2:shengchanjihua=1;break;
				case 3:shebeishishi=1;break;
				case 4:renli=1;break;
				case 5:hangcai=1;break;
				case 6:gongcheng=1;break;
				case 7:zhiliang=1;break;
			}
			
			dict["空白"]="";
			dict["编号"]=unusualReportVo["CODE"]==null?"":unusualReportVo["CODE"];//编号
			dict["航班号"]=unusualReportVo["FLT_NO"]==null?"":unusualReportVo["FLT_NO"];
			dict["日期"]=unusualReportVo["STD"]==null?"":unusualReportVo["STD"].substr(0,10);
			dict["实际开门时间"]=unusualReportVo["OPENTIME"]==null?"":unusualReportVo["OPENTIME"];
			dict["实际关门时间"]=unusualReportVo["CLOSETIME"]==null?"":unusualReportVo["CLOSETIME"];
			dict["计划起飞时间"]=unusualReportVo["STD"]==null?"":unusualReportVo["STD"];
			dict["机号"]=unusualReportVo["ACNUM"]==null?"":unusualReportVo["ACNUM"];
			dict["机型"]=unusualReportVo["ACMODEL"]==null?"":unusualReportVo["ACMODEL"];
			dict["延误时间"]=unusualReportVo["DALAYTIME"]==null?"":unusualReportVo["DALAYTIME"];
			dict["拆下件号"]=unusualReportVo["REMOVENUM"]==null?"":unusualReportVo["REMOVENUM"];
			dict["装上件号"]=unusualReportVo["FITONNUM"]==null?"":unusualReportVo["FITONNUM"];
			dict["拆下序号"]=unusualReportVo["REMOVENO"]==null?"":unusualReportVo["REMOVENO"];
			dict["装上序号"]=unusualReportVo["FITONNO"]==null?"":unusualReportVo["FITONNO"];
			dict["发生航站"]=unusualReportVo["AIRPORTNAME"]==null?"":unusualReportVo["AIRPORTNAME"];
			
			//类型
			dict["返航"]=fanhang;
			dict["改航"]=gaihang;
			dict["滑回"]=huahui;
			dict["换机"]=huanji;
			dict["火警"]=huojing;
			dict["地面停车"]=dimiantingche;
			dict["空中停车"]=kongzhongtingche;
			dict["延误"]=yanwu;
			dict["取消"]=quxiao;
			dict["冲出跑道"]=chongchupaodao;
			dict["中止起飞"]=zhongzhiqifei;
			dict["中断起飞"]=zhongduanqifei;
			dict["其它_类型"]=qita_type;
			
			//维修级别
			dict["航前"]=hangqian;
			dict["过站"]=guozhan;
			dict["航后"]=hanghou;
			dict["定检"]=dingjian;
			dict["指令"]=zhiling;
			dict["其它_维修级别"]=qita_weixiujibie;
			
			dict["上次航后完成基地"]=unusualReportVo["AP_LASTAP"]==null?"":unusualReportVo["AP_LASTAP"];
			
			//发生阶段
			dict["地面"]=dimian;
			dict["起飞"]=qifei;
			dict["巡航"]=xunhang;
			dict["着陆"]=zhuolu;
			dict["滑行"]=huaxing;
			dict["爬升"]=pasheng;
			dict["下降"]=xiajiang;
			dict["其它_发生阶段"]=qita_fashengjieduan;
			
			//是否为三次故障
			dict["是"]=shi;
			dict["否"]=fou;
			dict["不涉及"]=busheji;
			
			//机务工程分类
			dict["机械故障"]=jixieguzhang;
			dict["维修不当"]=weixiubudang;
			dict["生产计划"]=shengchanjihua;
			dict["设备实施"]=shebeishishi;
			dict["人力"]=renli;
			dict["航材"]=hangcai;
			dict["工程"]=gongcheng;
			
			dict["事件描述"]=unusualReportVo["EVENTDESC"]==null?"":unusualReportVo["EVENTDESC"];
			dict["事件处理及原因分析"]=solutionandcauseanalysis;
			dict["报告单位"]=unusualReportVo["REPORTER_DEPT"]==null?"":unusualReportVo["REPORTER_DEPT"];
			dict["MCC填报人"]=unusualReportVo["REPORTER"]==null?"":unusualReportVo["REPORTER"];
			dict["报告时间"]=unusualReportVo["UPDATETIME"]==null?"":unusualReportVo["UPDATETIME"];
					
			return dict;
		}
	}
}