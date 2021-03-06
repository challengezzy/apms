package com.apms.flex.modules.map.feature
{
	import com.apms.flex.assets.AssetsFeature;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.text.TextField;
	import flash.text.TextFormat;
	
	import org.bytearray.gif.player.GIFPlayer;
	import org.openscales.core.feature.Feature;
	import org.openscales.core.style.Rule;
	import org.openscales.core.style.Style;
	import org.openscales.core.style.fill.SolidFill;
	import org.openscales.core.style.font.Font;
	import org.openscales.core.style.marker.DisplayObjectMarker;
	import org.openscales.core.style.marker.WellKnownMarker;
	import org.openscales.core.style.stroke.Stroke;
	import org.openscales.core.style.symbolizer.PointSymbolizer;
	import org.openscales.core.style.symbolizer.Symbolizer;
	import org.openscales.core.style.symbolizer.TextSymbolizer;
	import org.openscales.geometry.Geometry;
	import org.openscales.geometry.ICollection;
	import org.openscales.geometry.LineString;
	import org.openscales.geometry.Point;
	import org.openscales.geometry.basetypes.Location;
	import org.openscales.geometry.basetypes.Pixel;
	
	/**
	 * 飞机点视图
	 * */
	public class AcFlightFeature extends Feature
	{
		private var _graphic:DisplayObjectMarker;
		
		private var _displayClass:Class;
		
		public var gifUrl:String;
		
		/**
		 * the geometry of the parent when the feature is an edition feature
		 **/
		private var _editionFeatureParentGeometry:ICollection = null;
		
		public function AcFlightFeature(geom:Geometry=null, data:Object=null, style:Style=null)
		{
			super(geom, data, style, false);
			
			_displayClass = AcFlightComp;
			this._graphic = new DisplayObjectMarker(_displayClass, -11, -25, 48);;
			//添加默认符号
			var rule:Rule = new Rule();
			var symbolizer:PointSymbolizer = new PointSymbolizer(this._graphic);
			rule.symbolizers.push(symbolizer);
			this.style = new Style();
			this.style.rules.push(rule);
		}
		
		public static function createPointFeature(loc:Location, data:Object=null, style:Style=null ):AcFlightFeature {
			var pt:Point = new Point(loc.lon,loc.lat);
			pt.projection = loc.projection;
			return new AcFlightFeature(pt, data, style);
		}
		
		override public function get lonlat():Location {
			var value:Location = null;
			if (this.point != null) {
				value = new Location(this.point.x, this.point.y, this.point.projection);
			}
			return value;
		}
		
		override public function destroy():void {
			this._editionFeatureParentGeometry = null;
			super.destroy();
		}
		
		public function get point():Point {
			return this.geometry as Point;
		}
		
		override public function draw():void {
			var i:int = this.numChildren;
			for (i; i > 0; i--) {
				this.removeChildAt(0);
			}
			super.draw();
		}
		
		/**
		 * @inheritdoc
		 */
		override protected function acceptSymbolizer(symbolizer:Symbolizer):Boolean
		{
			if (symbolizer is PointSymbolizer)
				return true;
			else
				return false;
		}
		
		/**
		 * @inheritdoc
		 */
		override protected function executeDrawing(symbolizer:Symbolizer):void {
			var x:Number;
			var y:Number;
			if(!this.layer || !this.layer.map)
				return;
			var resolution:Number = this.layer.map.resolution.value;
			this.x = 0;
			this.y = 0;
			var px:Pixel = this.layer.getLayerPxForLastReloadedStateFromLocation(new Location(this.point.x, this.point.y, this.projection));
			x = px.x;
			y = px.y;
			//this.graphics.beginBitmapFill();
			this.graphics.drawRect(x, y, 50, 50);
			this.graphics.endFill();
			
			
			/* 展现不出来，难道必须显示后，才能展现出来？
			var comp:AcFlightComp = new AcFlightComp();
			comp.width = 100;
			comp.height = 100;
			comp.x = x;
			comp.y = y;
			var bmpData:BitmapData = new BitmapData(comp.width,comp.height,true,0x0000000);  
			bmpData.draw(comp);
			var bmp:Bitmap = new Bitmap(bmpData);
			bmp.x += x;
			bmp.y += y;
			this.addChild(bmp);
			*/
			//点元素的显示渲染
			
			/*
			动画展示
			var gifPlayer:GIFPlayer = new GIFPlayer(true);
			var url:URLRequest = new URLRequest("./com/apms/flex/assets/feature/planegif.gif");
			gifPlayer.load(url);
			gifPlayer.visible=true;
			gifPlayer.x += x;
			gifPlayer.y += y;
			this.addChild(gifPlayer);
			*/
			
//			if (symbolizer is PointSymbolizer) {
//				var pointSymbolizer:PointSymbolizer = (symbolizer as PointSymbolizer);
//				if (pointSymbolizer.graphic) {
//					var render:DisplayObject = pointSymbolizer.graphic.getDisplayObject(this);
//					render.x += x;
//					render.y += y;
//					this.addChild(render);
//				}
//			} else if (symbolizer is TextSymbolizer) {
//				(symbolizer as TextSymbolizer).drawTextField(this);
//			}
		}
		
		/**
		 * To obtain feature clone
		 * */
		override public function clone():Feature {
			var geometryClone:Geometry = this.geometry.clone();
			var PointFeatureClone:AcFlightFeature = new AcFlightFeature(geometryClone as Point, null, this.style);
			PointFeatureClone._originGeometry = this._originGeometry;
			PointFeatureClone.layer = this.layer;
			return PointFeatureClone;
		}
		
		
		/**
		 * To know the segment of the Collection the edition point belongs to
		 * @param point:geometry point to test
		 * @param detectionTolerance:Number
		 * @return the segment number
		 * */
		public function getSegmentsIntersection(collection:ICollection, detectionTolerance:Number=8):int {
			
			var arrayResult:Array = new Array();
			var LineString1:LineString = null;
			var intersect:Boolean = false;
			var distanceArray:Array = new Array();
			var i:int;
			var j:int;
			
			if (collection != null) {
				j = collection.componentsLength - 1;
				for (i = 0; i < j; ++i) {
					var point1:Point = collection.componentByIndex(i) as Point;
					var point2:Point = collection.componentByIndex(i + 1) as Point;
					
					if (point1 != null && point2 != null) {
						var top:Number = Math.max(point1.y, point2.y);
						var right:Number = Math.max(point1.x, point2.x);
						var bottom:Number = Math.min(point1.y, point2.y);
						var left:Number = Math.min(point1.x, point2.x);
						if (point.y <= top && point.y >= bottom && point.x >= left && point.x <= right) {
							intersect = true;
						}
						if (intersect)
							arrayResult.push(new Array(new LineString(new <Number>[point1.x,point1.y, point2.x,point2.y]), i + 1));
						
					}
					intersect = false;
				}
				
				//The last segment
				point1 = collection.componentByIndex(0) as Point;
				point2 = collection.componentByIndex(collection.componentsLength - 1) as Point;
				if (point1.x != point2.x || point1.y != point2.y) {
					if (point1 != null && point2 != null) {
						top = Math.max(point1.y, point2.y);
						right = Math.max(point1.x, point2.x);
						bottom = Math.min(point1.y, point2.y);
						left = Math.min(point1.x, point2.x);
						if (point.y <= top && point.y >= bottom && point.x >= left && point.x <= right) {
							intersect = true;
						}
						if (intersect)
							arrayResult.push(new Array(new LineString(new <Number>[point1.x,point1.y, point2.x,point2.y]), collection.componentsLength));
						
					}
				}
				distanceArray = new Array();
				j = arrayResult.length;
				for (i = 0; i < j; i++) {
					//Scalar product to find the closest point with tolerance
					var pointA:Point = (arrayResult[i][0] as LineString).componentByIndex(0) as Point;
					var pointB:Point = (arrayResult[i][0] as LineString).componentByIndex(1) as Point;
					
					var pointPx:Pixel = this.layer.map.getMapPxFromLocation(new Location(point.x, point.y));
					
					var pointPxA:Pixel = this.layer.map.getMapPxFromLocation(new Location(pointA.x, pointA.y));
					
					var pointPxB:Pixel = this.layer.map.getMapPxFromLocation(new Location(pointB.x, pointB.y));
					
					var scalarPointAPointBPower:Number = Math.pow((pointPxA.x - pointPxB.x), 2) + Math.pow((pointPxA.y - pointPxB.y), 2);
					
					var scalarPointPointAPower:Number = Math.pow((pointPx.x - pointPxA.x), 2) + Math.pow((pointPxA.y - pointPx.y), 2);
					
					var scalarAHAB:Number = Math.pow((pointPx.x - pointPxA.x) * (pointPxB.x - pointPxA.x) + (pointPx.y - pointPxA.y) * (pointPxB.y - pointPxA.y), 2);
					
					var scalarPointPointBPower:Number = Math.pow((pointPx.x - pointPxB.x), 2) + Math.pow((pointPxB.y - pointPx.y), 2);
					
					var distance:Number = Math.pow((scalarPointPointAPower - scalarAHAB / scalarPointAPointBPower), 1 / 2);
					
					if (distance < detectionTolerance) {
						distanceArray.push(new Array(distance, arrayResult[i][1]));
					}
				}
				if (distanceArray.length > 1) {
					distanceArray.sort(sortOnValue);
					return distanceArray[0][1];
				} else if (distanceArray.length == 1)
					return distanceArray[0][1];
			}
			return -1;
		}
		
		private  function sortOnValue(a:Number,b:Number):Number{
			if(a>b) return 1;
			else if(a<b) return -1;
			else return 0;
		}
	}
}