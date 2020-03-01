package org.bytearray.gif.frames
{
	import flash.display.BitmapData;
	import flash.geom.Matrix;
	
	public class GIFFrame 
	{
		public var bitmapData:BitmapData;
		public var bitmapData_orig:BitmapData;
		public var delay:int;
			
		public function GIFFrame( pImage:BitmapData, pDelay:int )	
		{
			bitmapData = pImage;
			delay = pDelay;	
		}
		
		public function resize(w:Number, h:Number):void
		{
			if (!bitmapData_orig) 
			{   
				bitmapData_orig = bitmapData.clone();  
			}  
			var m:Matrix = new Matrix();  
			m.scale(w / bitmapData_orig.rect.width, h / bitmapData_orig.rect.height);  
			var bmd:BitmapData = new BitmapData(w, h, true, 0x000000);  
			bmd.draw(bitmapData_orig, m, null, null, null, true); 
			
			bitmapData = bmd;
		}
		
	}
}