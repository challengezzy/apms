package org.openscales.fx.layer
{
	import org.openscales.geometry.basetypes.Location;
	import org.openscales.core.layer.ImageTMS;
	
	/**
	 * Abstract TMS Flex wrapper
	 */
	public class FxImageTMS extends FxGrid
	{
		public function FxImageTMS()
		{
			super();
			if(this._layer == null)
				this._layer = new ImageTMS("","");
		}
		
		public function set format(value:String):void {
			if(this._layer != null)
				(this._layer as ImageTMS).format = value;
		}
		
		public function set origin(value:String):void {
			if(this._layer != null) {
				(this._layer as ImageTMS).origin = Location.getLocationFromString(value);
			}
		}
		
		public function set layerName(value:String):void {
			if(this._layer != null) {
				(this._layer as ImageTMS).layerName = value;
			}
		}
		// end of function
	}
	//end of class
}