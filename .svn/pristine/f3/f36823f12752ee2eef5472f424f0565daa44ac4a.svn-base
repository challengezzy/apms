package com.apms.flex.util
{
	import mx.utils.Base64Decoder;
	import mx.utils.Base64Encoder;

	public class StringUtil
	{
		public static function parseUrlParameters(url:String):Object{
			// Remove everything before the question mark, including
			// the question mark.
			var myPattern:RegExp = /.*\?/;  
			var s:String = url;
			s = s.replace(myPattern, "");
			
			// Create an Array of name=value Strings.
			var params:Array = s.split("&");
			
			var result:* = new Object();
			
			// Set the values of the salutation.
			for (var i:int = 0; i < params.length; i++) {
				var temp:Array = params[i].split("=");                        
				result[temp[0]] = temp[1];
			}                    
			return result;
		}
		
		/** 
		 * StringReplaceAll 
		 * @param source:String 源数据 
		 * @param find:String 替换对象 
		 * @param replacement:Sring 替换内容 
		 * @return String 
		 * **/  
		public static function StringReplaceAll( source:String, find:String, replacement:String ):String{  
			return source.split( find ).join( replacement );  
		} 
		
		public static function hashCode(str:String):uint{
			var hash:uint = 0;
			for (var i:int = 0; i < str.length; i++) {
				hash = 31*hash + str.charCodeAt(i) ;
			}
			return hash;
		}
		
		/**
		 * Base64加密
		 */ 
		public static function base64Encode($orgin:String):String{                 
			var $base64:Base64Encoder = new Base64Encoder();                 
			$base64.insertNewLines = false;//该值等于true时，输出的结果会自动换行，默认为true，                
			$base64.encodeUTFBytes($orgin);//这里注意，如果想加密中文就不要使用$base64.encode();                 
			var $result:String = $base64.toString();//输出结果                 
			return $result;             
		}
		
		/**
		 * Base64解密
		 */ 
		public static function btnDecode_clickHandler($origi:String):String{                 
			var $base64:Base64Decoder = new Base64Decoder();                 
			$base64.decode($origi);                 
			var $result:String = $base64.toByteArray().toString();//输出结果，decode类只能输出ByteArray类型的数据，因此要转换成string                 
			return $result;             
		}
		
		/**
		 * 输入字符串为abcd{aa}，然后用dataValue提供的变量进行替换
		 */
		public static function parseVariable(input:String,dataValue:Object):String{
			var variableStr:String = input;
			var lastIndex:int = 0;
			while(variableStr.indexOf("{",lastIndex) > -1 && variableStr.indexOf("}",lastIndex) > -1){	
				var paramName:String = variableStr.substring(variableStr.indexOf("{",lastIndex)+1,variableStr.indexOf("}",lastIndex));
				if(dataValue[paramName] != null){
					variableStr = variableStr.replace("{".concat(paramName).concat("}"),dataValue[paramName]);
				}
				lastIndex = variableStr.indexOf("{",lastIndex);
			}
			return variableStr;
		}
		public static function trim(str:String):String
		{
			if (str == null) return '';
			
			var startIndex:int = 0;
			while (isWhitespace(str.charAt(startIndex)))
				++startIndex;
			
			var endIndex:int = str.length - 1;
			while (isWhitespace(str.charAt(endIndex)))
				--endIndex;
			
			if (endIndex >= startIndex)
				return str.slice(startIndex, endIndex + 1);
			else
				return "";
		}
		/**
		 *  Returns <code>true</code> if the specified string is
		 *  a single space, tab, carriage return, newline, or formfeed character.
		 *
		 *  @param str The String that is is being queried. 
		 *
		 *  @return <code>true</code> if the specified string is
		 *  a single space, tab, carriage return, newline, or formfeed character.
		 *  
		 *  @langversion 3.0
		 *  @playerversion Flash 9
		 *  @playerversion AIR 1.1
		 *  @productversion Flex 3
		 */
		public static function isWhitespace(character:String):Boolean
		{
			switch (character)
			{
				case " ":
				case "\t":
				case "\r":
				case "\n":
				case "\f":
					return true;
					
				default:
					return false;
			}
		}
	}
}