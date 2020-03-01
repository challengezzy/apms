package org.openscales.core.style.marker {
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	
	import mx.controls.Label;
	import mx.core.UIComponent;
	
	import org.openscales.core.feature.Feature;

	/**
	 * 从DisplayObjectMarker类复制而来，增加了宽度和高度的设置 
	 */
	public class FlightMarker extends Marker {
		private var _c:Class;

		private var _xOffset:Number;

		private var _yOffset:Number;
		
		private var _width:Number;
		
		private var _height:Number;

		public function FlightMarker(c:Class, xOffset:Number=0, yOffset:Number=0 ,width:Number=6,height:Number=6, size:Number=6, opacity:Number=1, rotation:Number=0) {

			super(size, opacity, rotation);

			this._c = c;
			this._xOffset = xOffset;
			this._yOffset = yOffset;
			this._width = width;
			this._height = height;
		}

		override protected function generateGraphic(feature:Feature):DisplayObject {

			var result:UIComponent = new UIComponent();
			result.x = this._xOffset;
			result.y = this._yOffset;
			
			//add by zhangzy 指定图片或资源的大小
			result.width = 100;
			result.height = 100;
			
			//失败的尝试
			var label:Label = new Label();
			label.text = "你好";
			
			
			result.addChild(label);
			
			
			return result;
		}

		/**
		 * Offset along the x axis for the position of the display object
		 */
		public function set xOffset(value:Number):void {

			this._xOffset = value;
		}

		public function get xOffset():Number {

			return this._xOffset;
		}


		/**
		 * Offset along the y axis for the position of the display object
		 */
		public function set yOffset(value:Number):void {

			this._yOffset = value;
		}

		public function get yOffset():Number {

			return this._yOffset;
		}

		public function get image():Class {
			return this._c;
		}

		public function set image(value:Class):void {
			this._c = value;
		}
		
		override public function clone():Marker{
			return new FlightMarker(_c,_xOffset,this._yOffset, this._width, this._height, this._size as Number,this._opacity,this._rotation);
			
		}

	}
}