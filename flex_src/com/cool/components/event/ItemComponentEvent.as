package com.cool.components.event
{
	import flash.events.Event;
	
	public class ItemComponentEvent extends Event
	{
		public static const REAL_VALUE_CHANGE:String = "realValueChange";
		public function ItemComponentEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}

	}
}