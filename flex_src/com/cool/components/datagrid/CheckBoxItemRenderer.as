package com.cool.components.datagrid
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	import mx.collections.ArrayCollection;
	import mx.controls.CheckBox;
	import mx.controls.DataGrid;
	
	
	public class CheckBoxItemRenderer extends CheckBox
	{
		private var currentData:Object; //保存当前一行值的对象  
		
		public function CheckBoxItemRenderer(){  
			super();
			this.addEventListener(MouseEvent.CLICK,onClickCheckBox);  
		}  
		
		/** 使用此控件的数据值属性必须是isChecked */
		override public function set data(value:Object):void{  
			this.selected = value.isChecked;  
			this.currentData = value; //保存整行的引用  
		} 
		
		override public function get data():Object{
			return currentData;      
		}  
		
		//点击check box时，根据状况向selectedItems array中添加当前行的引用，或者从array中移除  
		private function onClickCheckBox(e:Event):void{   
			this.currentData.isChecked = this.selected;//根据是否选中的状态，更改数据源中选中的标记 
			
			var dg:DataGrid = DataGrid(listData.owner);//获取DataGrid对象
			dg.setFocus();
			
			var dpArr:ArrayCollection = dg.dataProvider as ArrayCollection;
			for( var i:int=0; i<dpArr.length; i=i+1 )
			{
				var rowData:Object = dpArr.getItemAt(i);
				if(rowData == currentData){
					dg.selectedIndex = i ;//选中checkbox时，选中到当前行
					dg.editedItemPosition = {rowIndex:i,columnIndex:0};
					break;
				}
			}
			
//			var column:CheckBoxColumn = dg.columns[listData.columnIndex];//获取整列的显示对象  
//			var selectItems:Array = column.selectItems; 
//			
//			
//			if(this.selected){  
//				selectItems.push(this.currentData);  
//			}else{  
//				for(var i:int = 0; i<selectItems.length; i++){  
//					if(selectItems[i] == this.currentData){  
//						selectItems.splice(i,1)  
//					}  
//				}  
//			}  
		} 
		
		/* eat keyboard events, the underlying list will handle them */
		override protected function keyDownHandler(event:KeyboardEvent):void
		{
		}
		
		/* eat keyboard events, the underlying list will handle them */
		override protected function keyUpHandler(event:KeyboardEvent):void
		{
		}
		
		// 居中展现    
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void    
		{    
			super.updateDisplayList(unscaledWidth, unscaledHeight);    
			var n:int = numChildren;    
			for(var i:int = 0; i < n; i++)    
			{    
				var c:DisplayObject = getChildAt(i);    
				if( !(c is TextField))    
				{    
					c.x = Math.round((unscaledWidth - c.width) / 2);    
					c.y = Math.round((unscaledHeight - c.height) /2 );    
				}    
			}
		}

	}
}