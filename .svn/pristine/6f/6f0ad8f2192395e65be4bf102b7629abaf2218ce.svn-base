package com.apms.flex.modules.map
{
	
	import com.apms.flex.modules.map.vo.AcstopVo;
	
	import mx.collections.ArrayCollection;
	
	import smartx.flex.components.util.Hashtable;

	/**
	 * 地图相关数据常量
	 * */
	public class AirportMapCache
	{
		/** 机位map, <String AcstopVo>, <aptportid+code, AcstopVo> */
		public static var acstopMap:Hashtable = new Hashtable(); 
		public static function setAcstopMap( acstopArr:ArrayCollection ):void
		{
			//添加机位信息缓存
			if( acstopArr == null ){
				return;
			}
			acstopMap.clear();
			var acstop:AcstopVo;
			var key:String;
			var i:int;
			for( i=0; i<acstopArr.length; i=i+1 )
			{
				acstop = acstopArr.getItemAt(i) as AcstopVo;
				key = acstop.airportid + "_" + acstop.code;
				acstopMap.add( key , acstop );
			}
			
		}
		
		/**
		 * 根据机场ID,机位编号查找机位对象
		 * */
		public static function getAcstopVo(aptid:String, acstopCode:String):AcstopVo{
			var key:String = aptid + "_" + acstopCode;
			var stopVo:AcstopVo =  acstopMap.find( key ) as AcstopVo;
			
			return stopVo;
		}
		
	}
}