package org.openscales.core.style.marker {
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.geom.ColorTransform;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;
	
	import org.bytearray.gif.events.GIFPlayerEvent;
	import org.bytearray.gif.player.GIFPlayer;
	import org.openscales.core.feature.Feature;

	/**
	 * 从DisplayObjectMarker类复制而来，增加了宽度和高度的设置 ,显示GIS动画
	 */
	public class ImageGifMarker extends Marker {
		private var _c:Class; //显示对象

		private var _xOffset:Number;

		private var _yOffset:Number;
		
		private var _width:Number;
		
		private var _height:Number;
		
		//通过bytearray加载
		[Embed(source='/com/apms/flex/assets/feature/move.gif', mimeType="application/octet-stream")]
		private var _movegif:Class;

		public function ImageGifMarker(c:Class, xOffset:Number=0, yOffset:Number=0 ,width:Number=6,height:Number=6, 
									   size:Number=6, opacity:Number=1, rotation:Number=0 ) {

			super(size, opacity, rotation);

			this._c = c;
			this._xOffset = xOffset;
			this._yOffset = yOffset;
			this._width = width;
			this._height = height;
		}

		override protected function generateGraphic(feature:Feature):DisplayObject {

			var result:Sprite = new Sprite();
//			result.width = _width;
//			result.height = _height;
			
			//GIF动画展示,但是经测试不能指定大小
			var gifPlayer:GIFPlayer = new GIFPlayer(true);
			
			gifPlayer.visible=true;
			gifPlayer.addEventListener ( GIFPlayerEvent.COMPLETE, function():void
				{  
					gifPlayer.resize(40, 40);
				});
			
			//两种不同的加载方式
//			var url:URLRequest = new URLRequest("com/apms/flex/assets/feature/planegif.gif");
//			gifPlayer.load(url);
			var gifBytes:ByteArray = (new _movegif() as ByteArray);
			
			try
			{
				gifPlayer.loadBytes(gifBytes);
			}
			catch (e:Error)
			{
				trace("GIF error: " + e.message);
			}

			
			
			//add by zhangzy 指定图片或资源的大小
			
//			var colorInfo:ColorTransform = gifPlayer.transform.colorTransform;
//			colorInfo.color = 0xFF0000 ;
//			gifPlayer.transform.colorTransform = colorInfo;
			
			result.addChild(gifPlayer);
			
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
			return new ImageGifMarker(_c,_xOffset,this._yOffset, this._width, this._height, this._size as Number,this._opacity,this._rotation);
			
		}

	}
}