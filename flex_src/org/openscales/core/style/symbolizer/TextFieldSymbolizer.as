package org.openscales.core.style.symbolizer
{
	import flash.filters.BitmapFilterQuality;
	import flash.filters.GlowFilter;
	import flash.text.AntiAliasType;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFormat;
	
	import org.openscales.core.feature.Feature;
	import org.openscales.core.style.font.Font;
	import org.openscales.core.style.halo.Halo;
	import org.openscales.geometry.basetypes.Location;
	import org.openscales.geometry.basetypes.Pixel;
	
	public class TextFieldSymbolizer extends Symbolizer
	{
		
		private var _font:Font = null;
		private var _halo:Halo = null;
		private var _propertyName:String = null;
		
		//add by zhangzy 增加offset，背景色
		private var _xOffset:Number;		
		private var _yOffset:Number;
		private var _backgroud:Boolean = false;
		private var _backgroudColor:uint = undefined; //滤镜颜色
		
		public function TextFieldSymbolizer(propertyName:String=null,font:Font = null
											,backgroud:Boolean=false, backgroudColor:uint=undefined 
											,xOffset:Number=0, yOffset:Number=0, halo:Halo = null)
		{
			super();
			this._propertyName = propertyName;
			this._font = font;
			this._halo = halo;
			
			this._xOffset = xOffset;
			this._yOffset = yOffset;
			this._backgroud = backgroud;
			this._backgroudColor = backgroudColor;
		}
		
		/**
		 * font style
		 */
		public function get font():Font
		{
			return _font;
		}
		/**
		 * @private
		 */
		public function set font(value:Font):void
		{
			_font = value;
		}
		/**
		 * property to display
		 */
		public function get propertyName():String
		{
			return _propertyName;
		}
		/**
		 * @private
		 */
		public function set propertyName(value:String):void
		{
			_propertyName = value;
		}
		
		/**
		 * 
		 */
		public function drawTextField(f:Feature, text:String = null):void {

			if(this._propertyName && f.attributes && f.attributes[this._propertyName]) {
				text = f.attributes[this._propertyName];
			} else if(!text){
				return;
			}
			
			var label:TextField = new TextField();
			label.selectable = true;
			label.mouseEnabled = false;
			label.autoSize = TextFieldAutoSize.LEFT;
			label.antiAliasType = AntiAliasType.ADVANCED;
			label.text = text;
			
			//add by zhangzy 设置背景色，设置偏移
			if(_backgroud){
				label.background = true;
				label.backgroundColor = _backgroudColor;
			}
			
			if(font) {
				var textFormat:TextFormat = new TextFormat();
				textFormat.color = this._font.color;
				textFormat.size = this._font.size;
				if(this._font.weight == Font.BOLD) {
					textFormat.bold = true;
				}
				if(this._font.style == Font.ITALIC) {
					textFormat.italic = true;
				}
				if(this._font.family) {
					textFormat.font = this._font.family;
				}
				label.alpha = this._font.opacity;
				label.setTextFormat(textFormat);
			}
			
			if(this._halo) {
				label.filters=[this._halo.getFilter()];
			}
			
			// on calcul le centre et on place le label
			var loc:Location = f.geometry.bounds.center;
			//var px:Pixel = f.layer.map.getMapPxFromLocation(loc);
			var px:Pixel = f.layer.getLayerPxForLastReloadedStateFromLocation(loc);
			//label.x += px.x-label.textWidth/2;
			//label.y += px.y-label.textHeight/2;
			label.x += px.x-label.textWidth/2 + _xOffset; //增加偏移
			label.y += px.y-label.textHeight/2 + _yOffset;
			f.addChild(label);
		}
		/**
		 * halo
		 */
		public function get halo():Halo
		{
			return _halo;
		}
		/**
		 * @private
		 */
		public function set halo(value:Halo):void
		{
			_halo = value;
		}

		/**
		 * clone
		 */
		override public function clone():Symbolizer{
			var s:TextFieldSymbolizer = new TextFieldSymbolizer();
			if(this._font)
				s.font = this._font.clone();
			if(this._halo)
				s.halo = this._halo.clone();
			return s;
		}
		
	}
}