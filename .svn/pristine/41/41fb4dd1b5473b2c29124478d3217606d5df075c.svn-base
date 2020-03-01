package com.cool.components.datagrid
{
	import flash.display.Sprite;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class ColorDataGrid extends DataGrid4RightMenu
	{
		//用于设置颜色，参数: 当前item, rowIndex, dataIndex, 当前color
		public var rowColorFunction:Function;
		public var columnColorFunction:Function;
		//覆写drawRowBackground，目的是根据rowColorFunction返回颜色设置当前行
		override protected function drawRowBackground(
			s:Sprite, rowIndex:int, y:Number, height:Number, 
			color:uint, dataIndex:int):void
		{
			if(rowColorFunction != null) 
			{
				//从dataProvider中获取当前item
				var item:Object;
				if(dataIndex < dataProvider.length)
				{
					item = dataProvider[dataIndex];
				}
				
				
				if(item)
				{ //如果当前item存在，从rowColorFunction中获取行的颜色
					color = rowColorFunction(item, rowIndex, dataIndex, color);
				}
			}
			
			//调用父类方法设置当前行颜色
			super.drawRowBackground(s, rowIndex, y, height, color, dataIndex);
		}
		
		override  protected function drawColumnBackground(s:Sprite, columnIndex:int,
														  color:uint, column:DataGridColumn):void
		{
			if(columnColorFunction !=null)
			{
				
				color = columnColorFunction( columnIndex, color);				
			}
			super.drawColumnBackground(s,columnIndex,color,column);
			
		}
	}
}