package com.cool.components.item
{
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.controls.listClasses.IListItemRenderer;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	
	import smartx.flex.components.assets.AssetsFileLib;
	import smartx.flex.components.util.TempletDataUtil;
	import smartx.flex.components.vo.TempletItemVO;

	/**
	 * 真实值发生变化
	 * */
	[Event(name="realValueChange",type="com.cool.components.event.ItemComponentEvent")]
	
	public class ItemComponent extends Canvas //implements IListItemRenderer
	{
		private var clearIcon:Class = AssetsFileLib.clearIcon;
		
		public var column:DataGridColumn;

		public var templetItemVO:TempletItemVO;
		
		public var controlsArray:Array;
		
		protected var hbox:HBox = new HBox();
		protected var hbox2:HBox = new HBox();
		protected var nameLabel:Label = new Label();
		protected var clearButton:Button = new Button();
		private var isClearButtonUnderMouse:Boolean = false;
		
		protected var inputControl:UIComponent = new UIComponent();
		public var showLabel:Boolean = true;
		
		public function ItemComponent(){
			this.addEventListener(FlexEvent.CREATION_COMPLETE,creationCompleteHandler);
			
		}
		
		protected function creationCompleteHandler(event:FlexEvent):void
		{
			this.addChild(hbox);
			
			nameLabel.text = templetItemVO.itemname;
			nameLabel.width = 100;
			if(showLabel)
				hbox.addChild(nameLabel);
			//inputControl.width = 170;
			inputControl.height = 24;
			inputControl.addEventListener(FocusEvent.FOCUS_IN,inputFocusIn);
			inputControl.addEventListener(FocusEvent.FOCUS_OUT,inputFocusOut);
			
			hbox.addChild(hbox2);
			
			hbox2.setStyle("horizontalGap",0);
			hbox2.setStyle("verticalAlign","middle");
			hbox2.addChild(inputControl);
			
			//清除按钮
			clearButton.setStyle("icon",clearIcon);
			clearButton.addEventListener(MouseEvent.CLICK,clearContent);
			clearButton.addEventListener(MouseEvent.CLICK,clearButtonMouseClick);
			clearButton.toolTip = "清除输入的内容";
			clearButton.width = 20;
			clearButton.height = 20;
			clearButton.addEventListener(MouseEvent.MOUSE_OVER,clearButtonMouseOver);
			clearButton.addEventListener(MouseEvent.MOUSE_OUT,clearButtonMouseOut);
			clearButton.tabFocusEnabled = false;
			hbox2.addChild(clearButton);
			callLater(hideClearButton);
			
			initUiControls();
		}
		
		protected function initUiControls():void{
			
		}
		
		public function hideClearButton():void{
			clearButton.setVisible(false);
		}
		
		private function clearButtonMouseClick(event:MouseEvent):void{
			hideClearButton();
		}
		
		private function clearButtonMouseOver(event:MouseEvent):void{
			isClearButtonUnderMouse = true;
		}
		
		private function clearButtonMouseOut(event:MouseEvent):void{
			isClearButtonUnderMouse = false;
		}
		
		private function inputFocusIn(event:FocusEvent):void{
			if(editable){
				clearButton.setVisible(true);
			}
		}
		
		private function inputFocusOut(event:FocusEvent):void{
			if(editable && !isClearButtonUnderMouse){
				hideClearButton();
			}		
		}
		
		public function getQueryConditon(isPreciseQuery:Boolean=false):String{
			return " (1=1) ";
		}
		
		public function get realValue():Object{
			return null;
		}
		
		public function get stringValue():String{
			return null;
		}
		
		public function get sqlStringValue():String{
			if(realValue == null)
				return "null";
			else
				return "'"+TempletDataUtil.convertSQLValue(stringValue)+"'";
		}

		
		protected function clearContent(event:MouseEvent):void{
			return;
		}
		
		[Bindable]
		protected var _editable:Boolean = true;
		
		public function set editable(editable:Boolean):void{
			this._editable = editable;
			//clearButton.setVisible(_editable);
		}
		
		public function get editable():Boolean{
			return _editable;
		}
		
		public function setNameLabelColor(color:String):void{
			nameLabel.setStyle("color",color);
		}
		
		public function setInputWidth(inputWidth:int):void{
			inputControl.width = inputWidth;
		}
		
		public function getSortFunction(fieldName:String):Function{
			return null;
		}
		
		public function setNameLabelWitdh(number:int):void{
			nameLabel.width = number;
		}
		
	}
}