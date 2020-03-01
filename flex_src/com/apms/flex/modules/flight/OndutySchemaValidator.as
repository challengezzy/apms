package com.apms.flex.modules.flight
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

	public class OndutySchemaValidator implements DataValidator 
	{
		private var ondutyObj:Object;
		
		public function validateData(cardPanel:BillCardPanel):Boolean{
			ondutyObj = cardPanel.getDataValue();
			
			var user:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_LOGIN_USER_NAME) as String;
			ondutyObj["UPDATEUSER"] = user;
			var dateFormatter:DateFormatter = new DateFormatter();
			dateFormatter.formatString = "YYYY-MM-DD JJ:NN:SS";
			var nowData:String= dateFormatter.format(new Date());
			ondutyObj["UPDATETIME"] = nowData;
			
			//验证上班和下班时间数据格式是否正确
			var std:String = ondutyObj["STARTTIME"];
			var sta:String = ondutyObj["ENDTIME"];
			
			var isStdOk:Boolean = DateUtil.isHourMinute(std);
			var isStaOk:Boolean = DateUtil.isHourMinute(sta);
			
			if(isStdOk == false){
				SmartXMessage.show("上班数据格式不正确，应为HH24:MM60");
				return false;
			}
			
			if(isStaOk == false){
				SmartXMessage.show("下班时间数据格式不正确，应为HH24:MM60");
				return false;
			}
			
			return true;
			
		}
		
	}
}