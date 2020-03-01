package com.apms.flex.modules
{
	import com.apms.flex.modules.MaintenanceManage.DDinfoListener;
	import com.apms.flex.modules.aircond.A19TelegraphViewListener;
	import com.apms.flex.modules.aircond.A21TelegraphViewListener;
	import com.apms.flex.modules.aircond.A24TelegraphViewListener;
	import com.apms.flex.modules.aircraft.ACGravityUpdateBfInterceptor;
	import com.apms.flex.modules.aircraft.ACWeightWorkPalnCardAfInterceptor;
	import com.apms.flex.modules.aircraft.AirStatusControlViewListener;
	import com.apms.flex.modules.alartmessage.AlartMessageDealListener;
	import com.apms.flex.modules.apu.A13TelegraphViewListener;
	import com.apms.flex.modules.apu.A14TelegraphViewListener;
	import com.apms.flex.modules.apu.ApuDiagnoseresultAddTraindataListener;
	import com.apms.flex.modules.apu.AttachQueryOfApuLoadListener;
	import com.apms.flex.modules.apu.correct.ApuHourCorEndListener;
	import com.apms.flex.modules.apu.correct.ApuHourCorStartListener;
	import com.apms.flex.modules.apu.correct.ApuHourCorUpdateBefore;
	import com.apms.flex.modules.apu.correct.ApuReportHourCycRecoverListener;
	import com.apms.flex.modules.apu.correct.ApuReportReparseListener;
	import com.apms.flex.modules.apu.llp.ApuLlpPartnumSetListener;
	import com.apms.flex.modules.apu.management.AttachManageOfAircraftWeightListener;
	import com.apms.flex.modules.apu.management.AttachManageOfApuManageListener;
	import com.apms.flex.modules.apu.query.AttachLoadingOfApuManageListener;
	import com.apms.flex.modules.apu.struct.ApuStructUpdateBefore;
	import com.apms.flex.modules.apu.swap.ApuAttachManagementListener;
	import com.apms.flex.modules.apu.swap.ApuSwapListListener;
	import com.apms.flex.modules.basic.interceptor.AirPositionTipViewListener;
	import com.apms.flex.modules.basic.interceptor.InsertAirPositionBfInterceptor;
	import com.apms.flex.modules.basic.interceptor.InsertAndUpdateUnFltRptInterceptor;
	import com.apms.flex.modules.basic.interceptor.InsertFaultInfoBfInterceptor;
	import com.apms.flex.modules.basic.interceptor.InsertWorkladderBfInterceptor;
	import com.apms.flex.modules.basic.interceptor.UpdateManInfoBfInterceptor;
	import com.apms.flex.modules.basic.interceptor.UpdateUserTimeBfInterceptor;
	import com.apms.flex.modules.basic.interceptor.UserUpdateBfInterceptor;
	import com.apms.flex.modules.basic.validator.UpdateUserTimeValidator;
	import com.apms.flex.modules.dfd.DfdParseStateResetListener;
	import com.apms.flex.modules.engine.AttachManageOfEngineManageListener;
	import com.apms.flex.modules.engine.AttachManageOfEngineSwapListener;
	import com.apms.flex.modules.engine.EngDismountingListener;
	import com.apms.flex.modules.engine.EngineHourCycRecordListener;
	import com.apms.flex.modules.engine.fileUploadWindow.EngineOpenItemUploadWindowListener;
	import com.apms.flex.modules.engine.listeners.A01TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A01TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A02TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A02TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A04TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A04TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A05TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A05TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A06TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A06TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A07TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A07TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A09TelegraphViewListener;
	import com.apms.flex.modules.engine.listeners.A10TelegraphViewCFMListener;
	import com.apms.flex.modules.engine.listeners.A10TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A11TelegraphViewListener;
	import com.apms.flex.modules.engine.listeners.A38TelegraphViewListener;
	import com.apms.flex.modules.engine.listeners.A40TelegraphViewListener;
	import com.apms.flex.modules.engine.listeners.A46TelegraphViewListener;
	import com.apms.flex.modules.engine.listeners.A48TelegraphViewIPrefListener;
	import com.apms.flex.modules.engine.listeners.A48TelegraphViewIWarnListener;
	import com.apms.flex.modules.engine.listeners.A49TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.listeners.A50TelegraphViewIAEListener;
	import com.apms.flex.modules.engine.llp.EngLlpPartnumSetListener;
	import com.apms.flex.modules.fdimu.FdimuFileDownloadListener;
	import com.apms.flex.modules.fdimu.FdimuFileUploadListener;
	import com.apms.flex.modules.flight.DefectRemindInsListener;
	import com.apms.flex.modules.flight.FaultInfoUpdateValidator;
	import com.apms.flex.modules.flight.FaultVersionViewListener;
	import com.apms.flex.modules.flight.FlightSelectViewListener;
	import com.apms.flex.modules.flight.OndutySchemaValidator;
	import com.apms.flex.modules.flight.PrintDDReportViewListener;
	import com.apms.flex.modules.flight.PrintUnusualReportViewListener;
	import com.apms.flex.modules.flight.template.FlightSchCreateListener;
	import com.apms.flex.modules.flight.template.FlightSchTemplateValidator;
	import com.apms.flex.modules.fueloil.rpt.A36TelegraphViewListener;
	import com.apms.flex.modules.landing.A15TelegraphViewListener;
	import com.apms.flex.modules.oil.A25TelegraphViewListener;
	import com.apms.flex.modules.oil.A26TelegraphViewListener;
	import com.apms.flex.modules.oil.A27TelegraphViewListener;
	import com.apms.flex.modules.oxygen.A23TelegraphViewListener;
	import com.apms.flex.modules.thermo.A33TelegraphViewListener;
	import com.apms.flex.modules.user.UserPwdModifyBtnListener;
	import com.apms.flex.modules.user.UserSaveValidator;
	import com.apms.flex.modules.vibration.A28A32TelegraphViewListener;
	import com.apms.flex.modules.vibration.A28VibrationSpwvdViewListener;
	import com.apms.flex.modules.wavhealth.A34TelegraphViewListener;
	import com.apms.flex.modules.workorder.AmsWoSelectViewListener;
	import com.apms.flex.vo.ApmsUIConst;
	
	import flash.display.Sprite;
	
	/**
	 * 所有自定义监听器、拦截器类的引用 
	 **/
	public class ApmsCustomLibrary extends Sprite
	{
		
		private var apms_ds:String = ApmsUIConst.DATASOURCE_DEFAULT;
		
		private var updatebf1:UpdateManInfoBfInterceptor;
		
		//vsms
		private var dDinfoListener:DDinfoListener;
		
		private var updateUserTimeValidator:UpdateUserTimeValidator;
		
		private var attachManageOfAircraftWeightListener:AttachManageOfAircraftWeightListener;
		private var fdimuFileDownloadListener:FdimuFileDownloadListener;
		private var fdimuFileUploadListener:FdimuFileUploadListener;
		private var userUpdateBfInterceptor:UserUpdateBfInterceptor;
		private var updateUserTimeBfInterceptor:UpdateUserTimeBfInterceptor;
		private var acGravityUpdateBfInterceptor:ACGravityUpdateBfInterceptor;
		private var acWeightWorkPalnCardAfInterceptor:ACWeightWorkPalnCardAfInterceptor;
		private var apuStructUpdateBefore:ApuStructUpdateBefore;
		
		//private var a15TelegraphViewListener:A15TelegraphViewListener;
		
		private var a01TelegraphViewCFMListener:A01TelegraphViewCFMListener;
		private var a01TelegraphViewIAEListener:A01TelegraphViewIAEListener;
		private var a02TelegraphViewCFMListener:A02TelegraphViewCFMListener;
		private var a02TelegraphViewIAEListener:A02TelegraphViewIAEListener;
		private var a04TelegraphViewCFMListener:A04TelegraphViewCFMListener;
		private var a04TelegraphViewIAEListener:A04TelegraphViewIAEListener;
		private var a05TelegraphViewCFMListener:A05TelegraphViewCFMListener;
		private var a05TelegraphViewIAEListener:A05TelegraphViewIAEListener;
		private var a06TelegraphViewCFMListener:A06TelegraphViewCFMListener;
		private var a06TelegraphViewIAEListener:A06TelegraphViewIAEListener;
		private var a07TelegraphViewCFMListener:A07TelegraphViewCFMListener;
		private var a07TelegraphViewIAEListener:A07TelegraphViewIAEListener;
		private var a09TelegraphViewListener:A09TelegraphViewListener;
		private var a10TelegraphViewCFMListener:A10TelegraphViewCFMListener;
		private var a10TelegraphViewIAEListener:A10TelegraphViewIAEListener;
		private var a11TelegraphViewListener:A11TelegraphViewListener;
		private var a13TelegraphViewListener:A13TelegraphViewListener;
		private var a14TelegraphViewListener:A14TelegraphViewListener;
		private var a15TelegraphViewListener:A15TelegraphViewListener;
		private var a19TelegraphViewListener:A19TelegraphViewListener;
		private var a21TelegraphViewListener:A21TelegraphViewListener;
		private var a23TelegraphViewListener:A23TelegraphViewListener;
		private var a24TelegraphViewListener:A24TelegraphViewListener;
		private var a25TelegraphViewListener:A25TelegraphViewListener; 
		private var a26TelegraphViewListener:A26TelegraphViewListener; 
		private var a27TelegraphViewListener:A27TelegraphViewListener; 
		private var a28a32TelegraphViewListener:A28A32TelegraphViewListener;
		private var a33TelegraphViewListener:A33TelegraphViewListener;
		private var a34TelegraphViewListener:A34TelegraphViewListener;
		private var a49TelegraphViewIAEListener:A49TelegraphViewIAEListener;
		private var a50TelegraphViewIAEListener:A50TelegraphViewIAEListener;
		private var a48TelegraphViewIPrefListener:A48TelegraphViewIPrefListener;
		private var a48TelegraphViewIWarnListener:A48TelegraphViewIWarnListener;
		
		private var engDismountingViewListener:EngDismountingListener;
		private var engLlpPartnumSetListener:EngLlpPartnumSetListener;
		private var attachManageOfEngineManageListener:AttachManageOfEngineManageListener;
		private var apuSwapListListener:ApuSwapListListener;
		private var apuLlpPartnumSetListener:ApuLlpPartnumSetListener;
		private var apuHourCorStartListener:ApuHourCorStartListener;
		private var apuHourCorEndListener:ApuHourCorEndListener;
		private var apuHourCorUpdateBefore:ApuHourCorUpdateBefore;
		private var apuAttachManagementListener:ApuAttachManagementListener;
		private var attachManageOfApuManageListener:AttachManageOfApuManageListener;
		private var attachManageOfEngineSwapListener:AttachManageOfEngineSwapListener;
		private var a28VibrationSpwvdViewListener:A28VibrationSpwvdViewListener;
		private var apuReportReparseListener:ApuReportReparseListener;
		private var apuReportHourCycRecoverListener:ApuReportHourCycRecoverListener;
		private var insertAndUpdateUnFltRptInterceptor:InsertAndUpdateUnFltRptInterceptor;
		private var faultVersionViewListener:FaultVersionViewListener;
		private var insertFaultInfoBfInterceptor:InsertFaultInfoBfInterceptor;
		private var insertWorkladderBfInterceptor:InsertWorkladderBfInterceptor;
		private var flightSelectViewListener:FlightSelectViewListener;
		private var a38TelegraphViewListener:A38TelegraphViewListener;
		private var a40TelegraphViewListener:A40TelegraphViewListener;
		private var a46TelegraphViewListener:A46TelegraphViewListener;
		
		//dfd报文
		private var dfdParseStateResetListener:DfdParseStateResetListener;
		
		private var airPositionTipViewListener:AirPositionTipViewListener;
		private var airStatusControlViewListener:AirStatusControlViewListener;
		//告警消息处理
		private var alartMessageDealListener:AlartMessageDealListener;
		private var insertAirPositionBfInterceptor:InsertAirPositionBfInterceptor;
		private var userSaveValidator:UserSaveValidator;
		private var userpwdmodify:UserPwdModifyBtnListener;
		private var attachQueryOfApuLoadListener:AttachQueryOfApuLoadListener;
		private var faultInfoUpdateValidator:FaultInfoUpdateValidator;
		//轮班模式校验器
		private var ondutySchemaValidator:OndutySchemaValidator;
		//航班相关  begin
		private var flighttemplateValidator:FlightSchTemplateValidator;
		private var flightschCreateListener:FlightSchCreateListener;
		private var printUnusualReportViewListener:PrintUnusualReportViewListener;
		//DD单打印相关
		private var printDDReportViewListener:PrintDDReportViewListener;
		//航班相关  end
		private var attachLoadingOfApuManageListener:AttachLoadingOfApuManageListener;
		//故障处理信息查看
		private var defectRemindInsListener:DefectRemindInsListener;
		//发动机开项控制附件管理
		private var engineOpenItemUploadWindowListener:EngineOpenItemUploadWindowListener;
		
		private var engineHourCycRecordListener : EngineHourCycRecordListener;
		
		private var apuDiagnoseresultAddTraindataListener:ApuDiagnoseresultAddTraindataListener
		
		private var amsWoSelectViewListener:AmsWoSelectViewListener;
		
		//APU 燃油
		private var a36TelegraphViewListener:A36TelegraphViewListener;

	}
}