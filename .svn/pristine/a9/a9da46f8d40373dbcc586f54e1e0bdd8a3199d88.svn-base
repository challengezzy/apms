package org.openscales.core.layer
{
	import org.openscales.core.Map;
	import org.openscales.core.events.MapEvent;
	import org.openscales.core.handler.IHandler;
	import org.openscales.core.tile.ImageTile;
	import org.openscales.core.tile.Tile;
	import org.openscales.fx.control.PanZoom;
	import org.openscales.fx.control.Zoom;
	import org.openscales.geometry.basetypes.Bounds;
	import org.openscales.geometry.basetypes.Location;
	import org.openscales.geometry.basetypes.Pixel;
	import org.openscales.geometry.basetypes.Size;
	
	/**
	 * The Tiled Web Service provides access to resources, in particular, to rendered
	 * cartographic tiles at fixed scales.
	 *
	 * More informations on [url]http://wiki.osgeo.org/wiki/Tile_Map_Service_Specification[/url]
	 *
	 */
	
	public class ImageTMS extends Grid
	{
		private var _serviceVersion:String = "1.0.0";
		
		private var _format:String = "png";
		
		private var _layerName:String;
		
		/**
		 * A list of all resolutions available on the server.
		 * Only set this property if the map resolutions differs from the server
		 */
		private var _serverResolutions:Array = null;
		
		public function ImageTMS(name:String,url:String, layerName:String="") {
			super(name, url);
			this._layerName = layerName;
			
		}
		
		/**
		 * 从TMS中COPY的计划ZOOM的方法
		 */
		public function getZoomForResolution(resolution:Number):Number {
			if(resolution > this.resolutions[0]) {
				return 0;
			}
			if(resolution < this.resolutions[this.resolutions.length - 1]) {
				return this.resolutions.length - 1;
			}
			var i:int = 1;
			var j:int = this.resolutions.length;
			for (i; i < j; ++i) {
				if ((this.resolutions[i] < resolution) && (Math.abs(this.resolutions[i] - resolution) > RESOLUTION_TOLERANCE)) {
					break;
				}
			}
			return i - 1;
		}
		
		override public function addTile(bounds:Bounds, position:Pixel):ImageTile {
			// return new ImageTile(this, position, bounds, this.getURL(bounds), new Size(this.tileWidth-3, this.tileHeight-3));
			return new ImageTile(this, position, bounds, this.getURL(bounds), new Size(this.tileWidth, this.tileHeight));
		}
		
		override public function getURL(bounds:Bounds):String {
			var res:Number = this.getSupportedResolution(this.map.resolution).value;
			if(this._tileOrigin==null) {
				this._tileOrigin = new Location(this.maxExtent.left,this.maxExtent.bottom);
			}
			
			//计算列号           
			var tileX:Number = Math.round((bounds.left - this.map.maxExtent.left) / (res * this.tileWidth));
			//计算行号
			var tileY:Number = Math.round((bounds.bottom - this.map.maxExtent.bottom) / (res * this.tileHeight));
			
			var tileZ:Number = this.getZoomForResolution(this.map.resolution.reprojectTo(this.projection).value);
			
			if(tileZ == 0){
				trace("debug");
			}
			
			if (tileX < 0) 
				tileX = tileX + Math.round(this.map.maxExtent.width / bounds.width);
			
			if (tileY < 0)
				tileY = tileY + Math.round(this.map.maxExtent.height / bounds.height);
			
//			trace("mapext.height="+map.maxExtent.height+", mapext.width="+map.maxExtent.width
//				//+", mapext.top="+map.maxExtent.top+",mapext.left="+map.maxExtent.left
//				//+", mapext.bottom="+map.maxExtent.bottom+", mapext.right="+map.maxExtent.right
//				//+", bounds.top="+bounds.top+",bounds.left="+bounds.left
//				//+", bounds.bottom="+bounds.bottom+",bounds.right="+bounds.right
//				+",z= "+tileZ+"  , x= "+tileX+"  , y="+ tileY + "  , resolution=" +this.map.resolution.value );
			
			//trace(" z= "+tileZ+"  , x= "+tileX+"  , y="+ tileY + "  , resolution=" +this.map.resolution.value );
			
//			if(tileX >= Math.pow(2,tileZ+1) || tileY>=Math.pow(2,tileZ)){
//				return  this.url +"/" + "none.jpg";
//			}
			var url:String = this.url  +"/" + this.layerName + "/" + tileZ + "/" + tileX + "/" + tileY+"."+this._format;
			
			return url ;
		}
		
		override public function set map(map:Map):void {
			super.map = map;
			if (! this._tileOrigin) {
				this._tileOrigin = new Location(this.map.maxExtent.left, this.map.maxExtent.bottom);
			}
		}
		
		/**
		 * setter for tile image format
		 * 
		 * @param value:String the tile image extention
		 */
		public function set format(value:String):void {
			if(value.length==0)
				return;
			else if(value.charAt(0)=='.')
				this._format = value.substr(1,value.length-1);
			else
				this._format = value;
		}
		
		/**
		 * getter for tile image format
		 * 
		 * @return String the tile image format
		 */
		public function get format():String {
			return this._format;
		}
		
		/**
		 * setter and getter of the TMS grid origin
		 */
		public function set origin(value:Location):void {
			this._tileOrigin = value;
		}
		
		public function get origin():Location {
			return this._tileOrigin.clone();
		}
		
		/**
		 * setter and getter of the TMS layer name
		 */
		public function set layerName(value:String):void {
			this._layerName = value;
		}
		
		public function get layerName():String {
			return this._layerName;
		}
		
		public function getOldURL(bounds:Bounds):String {
			//			var res:Number = this.getSupportedResolution(this.map.resolution).value;
			//			if(this._tileOrigin==null) {
			//				this._tileOrigin = new Location(this.maxExtent.left,this.maxExtent.bottom, this.maxExtent.projection);
			//			}
			//			
			//			//当前的等级 
			//			//var zoom:Number = this.getZoomForResolution(this.map.resolution.reprojectTo(this.projection).value); 
			//			var zoom:Number = getZoomValue();
			//			
			//			var xValue:Number = Math.round((bounds.left + this.maxExtent.width/2 - this.maxExtent.left) / (res * this.tileWidth));
			//			var yValue:Number = Math.round((bounds.bottom + this.maxExtent.height/2 - this.maxExtent.bottom) / (res * this.tileHeight));
			//			
			////			var xValue:Number = Math.round((bounds.left - this._tileOrigin.lon) / (res * this.tileWidth));
			////			var yValue:Number = Math.round((bounds.bottom - this._tileOrigin.lat) / ( res* this.tileHeight));
			//			
			//			var imgUrl:String = this.url +"/" + "none.jpg";
			//			if (xValue >= 0 && yValue >= 0) {
			//				imgUrl =  this.url +"/" +   this.layerName + "/" + zoom + "/" + xValue + "/" + yValue + "." + this._format;
			//				return imgUrl;
			//				
			//			} else {
			//				return imgUrl;
			//			}
			var res:Number = this.getSupportedResolution(this.map.resolution).value;
			if(this._tileOrigin==null) {
				this._tileOrigin = new Location(this.maxExtent.left,this.maxExtent.bottom, this.maxExtent.projection);
			}
			
			var x:Number = Math.round((bounds.left - this._tileOrigin.lon ) / (res * this.tileWidth));
			var y:Number = Math.round((bounds.bottom - this._tileOrigin.lat) / ( res* this.tileHeight));
			var z:Number = this.getZoomForResolution(this.map.resolution.reprojectTo(this.projection).value);
			
			var url:String = this.url + "/" + this.layerName + "/" + z + "/" + x + "/" + y+"."+this._format;
			
			if(x < 0 || y<0){
				url = this.url +"/" + "none.jpg";
			}
			
			trace("x= "+x+" , y= "+y+" , z="+ z + ", resolution=" +this.map.resolution );
			
			return url ;
		}
		
		/** 找出当前地图的缩放级别 , 经测试不准，暂时弃用*/
		private function getZoomValue():Number{
			var zvalue:Number = 0;
			
			var element:IHandler = null;
			var controls:Vector.<IHandler> =  this.map.controls;
			for(var i:int =0; i<controls.length; i++) {
				element = controls[i];
				if(element is PanZoom){
					zvalue = (element as PanZoom).zoomSlider.currentSliderValue;
					break;
				}else if(element is Zoom){
					zvalue = (element as Zoom).currentSliderValue;
					break;
				}
			}
			
			return zvalue;
		}
		
	}
	
}