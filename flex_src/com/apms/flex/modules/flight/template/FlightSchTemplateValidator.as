package com.apms.flex.modules.flight.template
{
	import com.apms.flex.util.DateUtil;
	
	import mx.controls.Alert;
	import mx.formatters.DateFormatter;
	
	import smartx.flex.components.basic.ClientEnviorment;
	import smartx.flex.components.core.BillCardPanel;
	import smartx.flex.components.core.ext.DataValidator;
	import smartx.flex.components.util.SmartXMessage;
	import smartx.flex.components.util.TempletDataUtil;
	import smartx.flex.components.vo.SimpleRefItemVO;

	public class FlightSchTemplateValidator implements DataValidator 
	{
		private var templateObj:Object;
		
		public function validateData(cardPanel:BillCardPanel):Boolean{
			templateObj = cardPanel.getDataValue();
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			templateObj["UPDATEUSER"] = user;
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			templateObj["UPDATETIME"] = nowData;
			
			var acnumRef:SimpleRefItemVO = templateObj["ACNUM"] as SimpleRefItemVO;
			
			if(acnumRef != null){
				var acmodel:String = acnumRef.code;
				templateObj["ACMODEL"] = acmodel;
			}
			
			//验证起飞和落地时间数据格式是否正确
			var std:String = templateObj["STD"];
			var sta:String = templateObj["STA"];
			
			var isStdOk:Boolean = DateUtil.isHourMinute(std);
			var isStaOk:Boolean = DateUtil.isHourMinute(sta);
			
			if(isStdOk == false){
				SmartXMessage.show("起飞时间数据格式不正确，应为HH24:MM60");
				return false;
			}
			
			if(isStaOk == false){
				SmartXMessage.show("落地时间数据格式不正确，应为HH24:MM60");
				return false;
			}
			
			return true;
			
		}
		
	}//end of class
}