package com.apms.flex.modules.map
{
	import com.apms.flex.assets.AssetsFeature;
	import com.apms.flex.modules.map.vo.AcstopVo;
	
	import org.openscales.core.feature.CustomPointFeature;
	import org.openscales.core.feature.Feature;
	import org.openscales.core.feature.PointFeature;
	import org.openscales.core.layer.ImageLayer;
	import org.openscales.core.style.Rule;
	import org.openscales.core.style.Style;
	import org.openscales.core.style.font.Font;
	import org.openscales.core.style.marker.CustomImageMarker;
	import org.openscales.core.style.marker.DisplayObjectMarker;
	import org.openscales.core.style.marker.ImageMarker;
	import org.openscales.core.style.symbolizer.PointSymbolizer;
	import org.openscales.core.style.symbolizer.TextFieldSymbolizer;
	import org.openscales.core.style.symbolizer.TextSymbolizer;
	import org.openscales.geometry.Point;
	
	import smartx.flex.components.basic.ClientEnviorment;
	
	/**
	 * 飞机地图元素加载工具类
	 * */
	public class PlaneFeatureUtil
	{
		private var webroot:String = ClientEnviorment.getInstance().getVar(ClientEnviorment.KEY_SERVICE_WEBROOT) as String;
		
		private var color_blue:uint = 0x005CA7;//黄色
		private var color_yellow:uint = 0xFFCC33;//土黄 
		private var color_red:uint = 0xFF0033;//绛红 CC3333,FF5555
		private var color_orange:uint = 0xFF8204;//橙色FF9900,
		private var color_green:uint = 0x00923F;//淡绿色00DC47
		private var color_gray:uint = 0x999999;//灰色
		
		public function getBuildingFeature(buildingData:Object,x:Number, y:Number,zoomCur:Number,rotation:Number=0):Feature{
			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
			
			//构建渲染效果
			var markerStyle:Style = new Style();
			
			var fontSize:Number = 18;
			if(zoomCur <=2){
				fontSize = 8;
			}else if(zoomCur ==3 ){
				fontSize = 10;
			}else if(zoomCur ==4 ){
				fontSize = 12;
			}else if(zoomCur == 5){
				fontSize = 14;
			}else if(zoomCur >= 6){
				fontSize = 20;
			}
			
			var offset:Number = getPlaneOffset(rotation,zoomCur);
			var height:Number = Math.abs(offset)* 2;
			var width:Number = height;
			
			//缩放情况
			var ws:Number = new Number(buildingData["widthscale"]);
			var hs:Number = new Number(buildingData["heightscale"]);
			height = height * hs;
			width  = width  * ws;
			
			//设置标示标签
			var bname:String = buildingData["name"];
			buildingData["buiding_name"] = buildingData["name"];
			
			//文本的偏移
			var xLabelOffset:Number = 0;// -fontSize*(bname.length/2) + width/2;
			var yLabelOffset:Number = height/2 + fontSize/2;
			
			var imgUrl:String = webroot + buildingData["imageurl"]; // "com/apms/flex/assets/building/hangar_door.png";
			var disMarker:CustomImageMarker = new CustomImageMarker(imgUrl,width,height);
			var rule:Rule = new Rule();
			rule.symbolizers.push( new PointSymbolizer(disMarker) );
			
			if(zoomCur >= 3 ){ //在3级以上才显示文本
				var font:Font = new Font(fontSize,0xffffff,1,null,null,"normal");
				rule.symbolizers.push(new TextFieldSymbolizer("buiding_name", font, true, color_blue, xLabelOffset, yLabelOffset ));
			}
			
			markerStyle.rules.push(rule);
			
			var buidingFeature:CustomPointFeature = new CustomPointFeature(point,buildingData,markerStyle);
			buidingFeature.doubleClickEnabled = true;
			
			return buidingFeature;
		}
		
		/**
		 * 获取飞机图形元素
		 * */
		public function getFlightFeature(featureData:Object,x:Number, y:Number,rotation:Number,zoomCur:Number,isConflict:Boolean=false): Feature{
			
			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
			
			var flightStyle:Style = getPlaneMark(featureData, rotation,zoomCur,isConflict);
			
			//var flightFeature:PointFeature = new PointFeature(point,featureData,flightStyle);
			var flightFeature:CustomPointFeature = new CustomPointFeature(point,featureData,flightStyle);
			flightFeature.doubleClickEnabled = true;
			
			return flightFeature;
		}
		
		public function getPlaneMark(flightData:Object ,_rotation:Number, zoomCur:Number,isConflict:Boolean=false): Style{
			var rule:Rule = new Rule();
			var markerStyle:Style = new Style();
			
			var fontSize:Number = 10;
			var offset:Number = 0; //偏移量，偏移到图像的一半大小 ，以保证显示在中央
			var rotation:Number = 0; //根据不同的rotation 获取不同旋转角度的图片,但是经测试旋转不是以中心点为中心，还是不要旋转了
			
			var opacity:Number = 1;//图标透明度
			var colorPlane:uint = color_blue;
			var planeIcon:Class = AssetsFeature.plane_128;
			
			offset = getPlaneOffset(rotation,zoomCur);
			var imgOffset:Number = offset;
			//设置图片大小
			var height:Number = Math.abs(imgOffset)* 2;
			var width:Number = height;
			
			if(zoomCur <=2){
				fontSize = 6;
			}else if(zoomCur ==3 ){
				fontSize = 8;
			}else if(zoomCur ==4 ){
				fontSize = 10;
			}else if(zoomCur == 5){
				fontSize = 14;
			}else if(zoomCur >= 6){
				fontSize = 17;
			}
			
			var minArr:Number = flightData["remain_min_arr"];
			var minDep:Number = flightData["remain_min_dep"];
			var maintainType:String = flightData["maintaintype"];
			var workstate:Number = flightData["workforce_state"];
			
			var ac_state_a:Number = 0;
			var ac_state_d:Number = 0;
			if( flightData["ac_state_a"] == null || flightData["ac_state_a"] == ""){
				ac_state_a = 70; //如果没有到达航班状态，则为航前，前一航班飞机设定为已到港
			}else{
				ac_state_a = flightData["ac_state_a"];
			}
			
			if( flightData["ac_state_d"] == null || flightData["ac_state_d"] == ""){
				ac_state_d = 0;//如果后一航班状态为空，为航后，后一航班状态设置为靠桥，判断时不考虑
			}else{
				ac_state_d = flightData["ac_state_d"];
			}
			
			var isMinArrSymbol:Boolean = false;//是否显示剩余分钟信息
			var isMinDepSymbol:Boolean = false;
			
			if( ac_state_a < 60){ //飞机还未落地,设置半透明
				opacity = 1;
				//改用灰度
				colorPlane = 0xffffff;//白色
				//20160319未落地时，判断即将落地时间如果小于30分钟，则加背景颜色提示
				if(minArr < 35){
					var acbgcolor:uint = color_blue;
					if(minArr < 16 && minArr >=1 ){
						acbgcolor = color_orange;
					}else if(minArr < 1){
						acbgcolor = color_red; //晚点啦
					}
					var bgfont:Font = new Font(width,0xffffff,0.3,null,null,"normal");
					flightData["acbackground"] = "	";
					isMinArrSymbol = true;					
					rule.symbolizers.push(new TextFieldSymbolizer("acbackground", bgfont, true, acbgcolor,0,0));
				}
			}
			
			
			if(maintainType == "AF" && workstate >= 40){ //航后只要工作完成即可
				colorPlane = color_green;
			}else{
				if(ac_state_d >= 10){ //已放行
					colorPlane = color_green;
				}else if(minDep < 30){ //未放行的判断时间还剩余多少
					isMinDepSymbol = true;
					
					if(minDep < 15 && minDep > 0){
						colorPlane = color_orange;
					}else if(minDep < 0){
						colorPlane = color_red;
					}
				}
			}
			
			//旋转的图标要取大一点
//			if( _rotation%90 > 0){
//				imgOffset = imgOffset*1.414;
//				xLabelOffset = xLabelOffset/1.414;
//				yLabelOffset = yLabelOffset/1.414;
//			}
			
			offset = offset; //文件偏移，再加上文字大小
			var xLabelOffset:Number = -offset;  //文字偏移量
			var yLabelOffset:Number = 0;  //文字偏移量
			
			//根据不同的rotation 获取不同旋转角度的图片
			
			if(_rotation == 45){
				planeIcon = AssetsFeature.plane_128_45;
				xLabelOffset = offset;
				yLabelOffset = -offset-fontSize;
			}else if(_rotation == 90){
				planeIcon = AssetsFeature.plane_128_90;
				xLabelOffset = offset-fontSize;
				yLabelOffset = 0;
				
			}else if(_rotation == 135){
				planeIcon = AssetsFeature.plane_128_135;
				xLabelOffset = offset;
				yLabelOffset = offset+fontSize;
				
			}else if(_rotation == 180){
				planeIcon = AssetsFeature.plane_128_180;
				xLabelOffset = 0;
				yLabelOffset = offset-fontSize;
				
			}else if(_rotation == 225){
				planeIcon = AssetsFeature.plane_128_225;
				xLabelOffset = -offset;
				yLabelOffset = offset+fontSize;
			}else if(_rotation == 270){
				planeIcon = AssetsFeature.plane_128_270;
				xLabelOffset = -offset+fontSize;
				yLabelOffset = 0;
				
			}else if(_rotation == 315){
				planeIcon = AssetsFeature.plane_128_315;
				xLabelOffset = -offset;
				yLabelOffset = -offset-fontSize;
			}else{
				planeIcon = AssetsFeature.plane_128;
				xLabelOffset = 0;
				yLabelOffset = -offset+fontSize;
			}
			
			//如果已落地，则使用靠桥动画
			if(ac_state_a == 60){
				colorPlane = undefined;
				//width = height/2;
				
				planeIcon = AssetsFeature.planeInSwf_000;
				if(_rotation == 45){
					planeIcon = AssetsFeature.planeInSwf_045;
				}else if(_rotation == 90){
					planeIcon = AssetsFeature.planeInSwf_090;
					
				}else if(_rotation == 135){
					planeIcon = AssetsFeature.planeInSwf_135;
					
				}else if(_rotation == 180){
					planeIcon = AssetsFeature.planeInSwf_180;
					
				}else if(_rotation == 225){
					planeIcon = AssetsFeature.planeInSwf_225;
				}else if(_rotation == 270){
					planeIcon = AssetsFeature.planeInSwf_270;
					
				}else if(_rotation == 315){
					planeIcon = AssetsFeature.planeInSwf_315;
				}
			}
			
			//推出动画
			if(ac_state_d == 30){
				colorPlane = undefined;
				//width = height/2;
				
				planeIcon = AssetsFeature.planeOutSwf_000;
				if(_rotation == 45){
					planeIcon = AssetsFeature.planeOutSwf_045;
				}else if(_rotation == 90){
					planeIcon = AssetsFeature.planeOutSwf_090;
					
				}else if(_rotation == 135){
					planeIcon = AssetsFeature.planeOutSwf_135;
					
				}else if(_rotation == 180){
					planeIcon = AssetsFeature.planeOutSwf_180;
					
				}else if(_rotation == 225){
					planeIcon = AssetsFeature.planeOutSwf_225;
				}else if(_rotation == 270){
					planeIcon = AssetsFeature.planeOutSwf_270;
					
				}else if(_rotation == 315){
					planeIcon = AssetsFeature.planeOutSwf_315;
				}
			}
			
			if(isConflict){ //冲突不透明
				opacity = 1;
				colorPlane = undefined;
				planeIcon = AssetsFeature.crosstSwf;
			}
			
			//此处可以设置旋转,但是displayobjcet的旋转不是以中心点为中心，因此不可直接使用
			var disMarker:ImageMarker = new ImageMarker(planeIcon, imgOffset , imgOffset, width, height , height , opacity, rotation ,colorPlane );
			
			rule.symbolizers.push( new PointSymbolizer(disMarker) );
			
			//机尾文字内容
			var actextType:String = ClientEnviorment.getInstance().getVar("ACTEXTTYPE") as String;
			var actextAttr:String = "acmodel";
			if( actextType == "ACMODEL" ){
				actextAttr = "acmodel";
			}else if( actextType == "ACNUM" ){
				actextAttr = "acnum";
			}else if( actextType == "FLIGHTNO" ){
				actextAttr = "flightno_union";
			}
			
			if(zoomCur >= 4 ){ //在3级以上才显示文本
				//rule.symbolizers.push(new TextSymbolizer("flight_mapdesc", font));
				//rule.symbolizers.push(new TextFieldSymbolizer("flight_mapdesc", font, true, 0xffffff, 0, -offset+fontSize));
				var font:Font = new Font(fontSize,0xffffff,1,null,null,"normal");
				rule.symbolizers.push(new TextFieldSymbolizer(actextAttr, font, true, 0x5E8FEC, xLabelOffset, yLabelOffset));
			}
			
			if(zoomCur >=2 ){
				//如果是VIP,添加一个V标志,从OMIS航班备注和提醒单信息中获取
				var memo:String = flightData["memo"]==null ?"":flightData["memo"];
				var flagtip:String = flightData["flag_tip"]==null ?"":flightData["flag_tip"];
				if( flagtip.indexOf("V")>-1 || memo.indexOf("VIP")>-1 || memo.indexOf("vip")>-1 ){
					flightData["V"] = "V";
					var vfont:Font = new Font(fontSize*3,0xEE00EE,1,"Arial",null,"bold");//VIP字条颜色
					rule.symbolizers.push(new TextFieldSymbolizer("V", vfont,false,0,-width/2,0) );
				}
			}
			
			if(isMinArrSymbol){
				var arrfont:Font = new Font(width,0xffffff,0.95,null,null,"normal");
				rule.symbolizers.push(new TextFieldSymbolizer("remain_min_arr", arrfont, false, 0,width/2,0));
			}
			if(isMinDepSymbol){
				var depfont:Font = new Font(width,0xffffff,0.95,null,null,"normal");
				rule.symbolizers.push(new TextFieldSymbolizer("remain_min_dep", depfont, false, 0,width/2,0));
			}
			
			markerStyle.rules.push(rule);
			
			return markerStyle;
		}
		
		/**
		 * 获取飞机的位置偏移量
		 * */
		private function getPlaneOffset(_rotation:Number, zoomCur:Number):Number{
			var offset:Number = 0; //偏移量，偏移到图像的一半大小 ，以保证显示在中央
			if(zoomCur <=2){
				offset = -6;
			}else if(zoomCur ==3 ){
				offset = -16;
			}else if(zoomCur ==4 ){
				offset = -24;
			}else if(zoomCur == 5){
				offset = -48;
			}else if(zoomCur >= 6){
				offset = -64;
			}
			return offset;
		}
		
		/**
		 * 获取机位号图标
		 * */
		public function getAcstopFeature(acstop:AcstopVo,x:Number, y:Number,zoomCur:Number): Feature{
			
			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
			var markerStyle:Style = new Style();
			var rule:Rule = new Rule();
			
			var fontSize:Number = 10;
			var opacity:Number = 0.9;//图标透明度
			var rotation:Number = 0;
			if(zoomCur <=2){
				fontSize = 6;
			}else if(zoomCur ==3 ){
				fontSize = 10;
			}else if(zoomCur ==4 ){
				fontSize = 16;
			}else if(zoomCur == 5){
				fontSize = 19;
			}else if(zoomCur >= 6){
				fontSize = 24;
			}
			
			var data:Object = new Object();
			data["acstop_vo"] = acstop;
			data["code"] = acstop.code;
			data["key"] = acstop.airportid + "_" + acstop.code ;
			data["feature_name"] = "acstop";
			
			var offset:Number = getPlaneOffset(acstop.rotation,zoomCur);
			var xoffset:Number = 0;
			var yoffset:Number = 0;
			
			offset = offset - fontSize/2;
			
			var _rotation:Number = acstop.rotation;
			var xLabelOffset:Number = -offset;  //机位文字X偏移量
			var yLabelOffset:Number = 0;  //机位文字Y偏移量
			if(_rotation == 45){
				xLabelOffset = offset;
				yLabelOffset = -offset;
			}else if(_rotation == 90){
				xLabelOffset = offset;
				yLabelOffset = 0;
				
			}else if(_rotation == 135){
				xLabelOffset = offset;
				yLabelOffset = offset;
				
			}else if(_rotation == 180){
				xLabelOffset = 0;
				yLabelOffset = offset;
				
			}else if(_rotation == 225){
				xLabelOffset = -offset;
				yLabelOffset = offset;
			}else if(_rotation == 270){
				xLabelOffset = -offset;
				yLabelOffset = 0;
				
			}else if(_rotation == 315){
				xLabelOffset = -offset;
				yLabelOffset = -offset;
			}else{
				xLabelOffset = 0;
				yLabelOffset = -offset;
			}
			
			if( _rotation%90 > 0){
				offset = offset*1.414;
				xLabelOffset = xLabelOffset/1.414;
				yLabelOffset = yLabelOffset/1.414;
			}
			
			var txtcolor:uint = 0xEEEE00; //上海为白字，黄色背景; 杭州为无背景，黄字
			var bgcolor:uint = 0xF8C946;
			var font:Font = new Font(fontSize,txtcolor,1,null,null,"bold");
			rule.symbolizers.push(new TextFieldSymbolizer("code", font, false, bgcolor, -xLabelOffset, -yLabelOffset));
			
			markerStyle.rules.push(rule);
			
			var acstopFeature:CustomPointFeature = new CustomPointFeature(point,data,markerStyle);
			acstopFeature.doubleClickEnabled = true;
			return acstopFeature;
		}
		
		/**
		 * 获取人员图形元素
		 * */
		public function getWorkerFeature(featureData:Object,x:Number, y:Number,rotation:Number,zoomCur:Number): PointFeature{
			
			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
			var markerStyle:Style = new Style();
			var rule:Rule = new Rule();
			
			var workerIcon:Class = AssetsFeature.workman_64;
			
			var rotation:Number = 0;
			var offset:Number = getPlaneOffset(rotation,zoomCur);
			var width:Number = Math.abs(offset) *  3 / 4;
			
			var opacity:Number = 1;//图标透明度
			var workstate:Number = featureData["workforce_state"];
			var colorWorker:uint = undefined;;
			if(workstate == 10){
				//opacity = 0.55;
				//用灰度代替透明
				colorWorker = color_gray;
			}else if(workstate == 23) {
				colorWorker = color_orange;
			}else if(workstate == 40) { //工作完成
				workerIcon = AssetsFeature.checkoff_64;
			}else if(workstate >= 30 && workstate < 40){ //工作中状态
				workerIcon = AssetsFeature.tool_64;
			}
			
			
			var disMarker:ImageMarker = new ImageMarker(workerIcon, offset , offset , width, width, width , opacity, rotation ,colorWorker );
			
			rule.symbolizers.push( new PointSymbolizer(disMarker) );
			markerStyle.rules.push(rule);
			
			var workerFeature:PointFeature = new PointFeature(point,featureData,markerStyle);
			return workerFeature;
		}
		/**
		 * 获取故障图形元素
		 * */
		public function getFaultFeature(featureData:Object,x:Number, y:Number,rotation:Number,zoomCur:Number): PointFeature{
			
			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
			var markerStyle:Style = new Style();
			var rule:Rule = new Rule();
			
			var faultIcon:Class = AssetsFeature.fault_64;
			
			var rotation:Number = 0;
			var offsetY:Number = getPlaneOffset(rotation,zoomCur);
			var offsetX:Number = 0-getPlaneOffset(rotation,zoomCur); 
			var width:Number = Math.abs(offsetX) *  3 / 4;
			
			var opacity:Number = 1;//图标透明度
			var colorWorker:uint = undefined;
			
			var disMarker:ImageMarker = new ImageMarker(faultIcon, offsetX , offsetY , width, width, width , opacity, rotation ,colorWorker );
			
			rule.symbolizers.push( new PointSymbolizer(disMarker) );
			markerStyle.rules.push(rule);
			
			var faultFeature:PointFeature = new PointFeature(point,featureData,markerStyle);
			return faultFeature;
		}
//		/**
//		 * 获取告警图形元素
//		 * */
//		public function getWarningFeature(featureData:Object,x:Number, y:Number,rotation:Number,zoomCur:Number): PointFeature{
//			
//			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
//			var markerStyle:Style = new Style();
//			var rule:Rule = new Rule();
//			
//			var warningIcon:Class = AssetsFeature.warning_64;
//			 
//			var rotation:Number = 0;
//			var offset:Number = 0-getPlaneOffset(rotation,zoomCur);
//			var width:Number = Math.abs(offset) *  3 / 4;
//			
//			var opacity:Number = 1;//图标透明度
//			var colorWorker:uint = undefined;;
//			
//			var disMarker:ImageMarker = new ImageMarker(warningIcon, offset , offset , width, width, width , opacity, rotation ,colorWorker );
//			
//			rule.symbolizers.push( new PointSymbolizer(disMarker) );
//			markerStyle.rules.push(rule);
//			
//			var faultFeature:PointFeature = new PointFeature(point,featureData,markerStyle);
//			return faultFeature;
//		}
		/**
		 * 获取工作梯图形元素
		 * */
		public function getWorkladderFeature(featureData:Object,x:Number, y:Number,_rotation:Number,zoomCur:Number): PointFeature{
			
			var point:org.openscales.geometry.Point = new org.openscales.geometry.Point(x,y);
			var markerStyle:Style = new Style();
			var rule:Rule = new Rule();
			
			var workladderIcon:Class = AssetsFeature.workladder_64;
			
			
			var rotation:Number = 0;
//			var offsetX:Number = -6;
//			var offsetY:Number = 0;
//			
//			var width:Number = Math.abs(offsetY-offsetX) *  3 / 4;
			
			var fontSize:Number = 10;
			if(zoomCur <=2){
				fontSize = 6;
			}else if(zoomCur ==3 ){
				fontSize = 10;
			}else if(zoomCur ==4 ){
				fontSize = 16;
			}else if(zoomCur == 5){
				fontSize = 19;
			}else if(zoomCur >= 6){
				fontSize = 24;
			}
			
			var offset:Number = getPlaneOffset(_rotation,zoomCur);
			var xoffset:Number = 0;
			var yoffset:Number = 0;
			
			offset = offset - fontSize/2;
			var xLabelOffset:Number = -offset;  //机位文字X偏移量
			var yLabelOffset:Number = 0;  //机位文字Y偏移量
			if(_rotation == 45){
				xLabelOffset = offset-fontSize/2;
				yLabelOffset = -offset+fontSize/2;
			}else if(_rotation == 90){
				xLabelOffset = offset-fontSize/2;
				yLabelOffset = 0-fontSize/2;
				
			}else if(_rotation == 135){
				xLabelOffset = offset-fontSize/2;
				yLabelOffset = offset+fontSize/2;
				
			}else if(_rotation == 180){
				xLabelOffset = 0-fontSize/2;
				yLabelOffset = offset+fontSize/2;
				
			}else if(_rotation == 225){
				xLabelOffset = -offset-fontSize/2;
				yLabelOffset = offset+fontSize/2;
			}else if(_rotation == 270){
				xLabelOffset = -offset-fontSize/2;
				yLabelOffset = 0+fontSize/2;
				
			}else if(_rotation == 315){
				xLabelOffset = -offset-fontSize/2;
				yLabelOffset = -offset+fontSize/2;
			}else{
				xLabelOffset = 0-fontSize/2;
				yLabelOffset = -offset+fontSize/2;
			}
			
			var width:Number = 6;
			if(zoomCur <=2){
				width = 8;
			}else if(zoomCur ==3 ){
				width = 16;
			}else if(zoomCur ==4 ){
				width = 24;
			}else if(zoomCur == 5){
				width = 32;
			}else if(zoomCur >= 6){
				width = 40;
			}
			
			var opacity:Number = 1;//图标透明度
			var colorWorker:uint = undefined;
			var disMarker:ImageMarker = new ImageMarker(workladderIcon, -xLabelOffset , -yLabelOffset , width, width, width , opacity, rotation ,colorWorker );
			
			rule.symbolizers.push( new PointSymbolizer(disMarker) );
			markerStyle.rules.push(rule);
			
			var workladderFeature:PointFeature = new PointFeature(point,featureData,markerStyle);
			return workladderFeature;
		}
		
	}
}