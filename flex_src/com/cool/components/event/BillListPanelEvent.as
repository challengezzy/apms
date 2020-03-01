package com.cool.components.event
{
	import flash.events.Event;

	public class BillListPanelEvent extends Event
	{
		public static const QUERY_START:String = "queryStart";
		public static const QUERY_END:String = "queryEnd";
		public static const INSERT:String = "insert";
		public static const INITCOMPLETE:String = "initComplete";
		
		public var insertObject:Object;
		
		public function BillListPanelEvent(type:String, insertObject:Object = null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}