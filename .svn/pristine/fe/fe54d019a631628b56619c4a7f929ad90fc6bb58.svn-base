package org.openscales.core.style.marker {
	import flash.display.DisplayObject;
	import flash.geom.ColorTransform;
	
	import org.openscales.core.feature.Feature;

	/**
	 * 从DisplayObjectMarker类复制而来，增加了宽度和高度的设置 
	 */
	public class ImageMarker extends Marker {
		private var _c:Class;

		private var _xOffset:Number;

		private var _yOffset:Number;
		
		private var _width:Number;
		
		private var _height:Number;
		
		private var _color:uint = undefined; //滤镜颜色

		public function ImageMarker(c:Class, xOffset:Number=0, yOffset:Number=0 ,width:Number=6,height:Number=6, 
									size:Number=6, opacity:Number=1, rotation:Number=0 ,color:uint=undefined) {

			super(size, opacity, rotation);

			this._c = c;
			this._xOffset = xOffset;
			this._yOffset = yOffset;
			this._width = width;
			this._height = height;
			this._color = color;
		}

		override protected function generateGraphic(feature:Feature):DisplayObject {

			var result:DisplayObject = (new _c() as DisplayObject);
			result.x = this._xOffset;
			result.y = this._yOffset;
			
			//add by zhangzy 指定图片或资源的大小
			result.width = _width;
			result.height = _height;
			
			if( _color > 0){
				var colorInfo:ColorTransform = result.transform.colorTransform;
				colorInfo.color = _color ;
				result.transform.colorTransform = colorInfo;
			}
			
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
			return new ImageMarker(_c,_xOffset,this._yOffset, this._width, this._height, this._size as Number,this._opacity,this._rotation);
			
		}

	}
}