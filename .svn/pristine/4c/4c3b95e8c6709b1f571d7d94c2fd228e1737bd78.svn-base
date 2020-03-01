package com.apms.flex.util
{
	public class UtilPeriod
	{
		public function UtilPeriod()
		{
		}
		public static function parse( _strPeriod:String ):int
		{
			var strPeriod:String = StringUtil.trim(_strPeriod);
			
			//	check	begin
			if( strPeriod.length != 6 )
			{
				return -1;	
			}
			
			var year:Number = Number(strPeriod.substring(0,4));	
			if( isNaN(year) )
			{
				return -1;
			}
			
			var pno:Number = Number(strPeriod.substring(4));	
			
			if( isNaN(pno) || pno < 1  )
			{
				return -1;
			}	
			//	check	end
			
			return year*100 + pno;				
		}
	}
}