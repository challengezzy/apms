package com.apms.bs.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 字符串工具类
 * @author jerry
 *
 */
public class StringUtil {
	
	/**
	 * 字符串转换为数字，如果字符串为非法数据，则返回null
	 * @param str
	 * @return
	 */
	public static Double StringToDouble(String str){
		Double value = null;
		
		try{
			value = new Double(str);
		}catch (Exception e) {
			//非法数字
		}
		
		return value;
	}
	
	/**
	 * 字符串转换为数字，如果字符串为非法数据，则返回null
	 * @param str
	 * @return
	 */
	public static Float StringToFloat(String str){
		Float value = null;
		
		try{
			value = new Float(str);
		}catch (Exception e) {
			//非法数字
		}
		
		return value;
	}
	
	
	/**
	 * @param srcStr 为输入源字符串，此处为";aa;;"
	 * @param split 为分隔字符串，此处为";"
	 * @param flag 表示：当最后的那个分隔符后面是null时，是否也将他作为一个元素返回
	 * @return String[]
	 */
	public static String[] splitString2Array(String srcStr, String split, boolean flag) {
		ArrayList<String> list = new ArrayList<String>();
        int m = 0 - split.length();
        int n = srcStr.indexOf(split);
        if (n == -1) {
            return (String[])list.toArray(new String[0]);
        }
        while (true) {
            if (n == -1) {
                break;
            }
            String str = srcStr.substring(m + split.length(), n);
            list.add(str);
            m = n;
            n = srcStr.indexOf(split, m + split.length());
        }
        String ss = srcStr.substring(m + split.length());
        if (ss.length() != 0 || flag) {
            list.add(ss);
        }
        
        return (String[])list.toArray(new String[0]);
	}
	
	/**
     * 替换字符串
     * @param str_par 字符串
     * @param old_item 比较值
     * @param new_item 新值
     * @return
     */
    public static String replaceAll(String str_par, String old_item, String new_item) {
        String str_return = "";
        String str_remain = str_par;
        int li_pos=-1;
        while ((li_pos = str_remain.indexOf(old_item))>=0) {
            String str_prefix = str_remain.substring(0, li_pos);
            str_return = str_return + str_prefix + new_item; // 将结果字符串加上原来前辍
            str_remain = str_remain.substring(li_pos + old_item.length(), str_remain.length());
        }
        str_return = str_return + str_remain; // 将剩余的加上
        return str_return;
    }
    
    /**
     * 替换符合正则表达式的所有子字符串为新字符串
     * @param src
     * @param pattern
     * @param to
     * @return
     */
	public static String replaceAll2(String src, String pattern, String to) {
		if (src == null) {
			return null;
		}
		if (pattern == null) {
			return src;
		}

		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(src);

		int i = 1;
		while (m.find()) {
			// System.out.println("找到第" + i + "个匹配:" + m.group() +
			// " 位置为:" + m.start() + "-" + (m.end() - 1));
			m.appendReplacement(sb, to);
			i++;
		}
		m.appendTail(sb);
		// System.out.println("替换后的结果字符串为:" + sb);
		return sb.toString();
	}
	
	 /**
     * xml字符串格式化
     * @param str
     * @return
     * @throws Exception
     */
	public static String xmlFormat(String str) throws Exception {
		// System.out.println(" str :  " + str);
		Document doc = DocumentHelper.parseText(str);
		// 创建输出格式
		OutputFormat formater = OutputFormat.createPrettyPrint();
		// 设置xml的输出编码
		formater.setEncoding("gbk");
		// 创建输出(目标)
		StringWriter out = new StringWriter();
		// 创建输出流
		XMLWriter writer = new XMLWriter(out, formater);
		// 输出格式化的串到目标中，执行后。格式化后的串保存在out中。
		writer.write(doc);

		//System.out.println(out.toString());
		// 返回我们格式化后的结果
		return out.toString();
	}
	
	/**
     * Document转换成String输出
     * @param str
     * @return
     * @throws Exception
     */
	public static String doc4String(Document doc) throws Exception {
		// 创建输出格式
		OutputFormat formater = OutputFormat.createPrettyPrint();
		// 设置xml的输出编码
		formater.setEncoding("gbk");
		// 创建输出(目标)
		StringWriter out = new StringWriter();
		// 创建输出流
		XMLWriter writer = new XMLWriter(out, formater);
		// 输出格式化的串到目标中，执行后。格式化后的串保存在out中。
		writer.write(doc);

		//System.out.println(out.toString());
		// 返回我们格式化后的结果
		return out.toString();
	}
	
	/**
     * 参数截取器。（东方格式，任意字符组成）
     * 取得一个字符串中以{}包括的所有宏代码参数集
     * 比如"{aa} and  {bb}" 返回 ['aa','bb']
     * @param str 待查的字串
     * @return
     * @throws Exception
     */
    public static String[] getFormulaMacParamsEast(String str){
    	Matcher mt=Pattern.compile("\\{([a-z,0-9,A-Z,.,_,\u4e00-\u9fa5,\\#]+[0-9]*\\(*\\)*)\\}").matcher(str);//    	
    	LinkedHashMap map=new LinkedHashMap();
    	
    	while(mt.find()){
    		map.put(mt.group(1),mt.group(1));
    	}
    	if(map.size()==0){return new String[0];}
    	String[] rt=(String[])map.keySet().toArray(new String[0]);
    	return (rt==null)?(new String[0]):rt;  	
    }
    
    /**
     * 参数截取器（西方格式，即字母或者下划线开头，字母数字下划线结尾的名称）。
     * 取得一个字符串中以{}包括的所有宏代码参数集
     * 比如"{aa} and  {bb}" 返回 ['aa','bb']
     * @param str 待查的字串
     * @return
     * @throws Exception
     */
    public static String[] getFormulaMacParamsWest(String str){
    	return getFormulaMacParamsEast(str);
    }
    
    /**
     * 解析参数字符创，根据制定的开始和结束符号
     * @param str
     * @param start 参数开始符号
     * @param end  参数结束符号
     * @return
     */
    public static String[] getFormulaMacParams(String str,String start,String end){
    	Matcher mt=Pattern.compile("\\"+start+"([a-z,A-Z,.,_,\u4e00-\u9fa5,\\#]+[0-9]*\\(*\\)*)\\" + end).matcher(str);//    	
    	LinkedHashMap map=new LinkedHashMap();
    	
    	while(mt.find()){
    		map.put(mt.group(1),mt.group(1));
    	}
    	if(map.size()==0){return new String[0];}
    	String[] rt=(String[])map.keySet().toArray(new String[0]);
    	return (rt==null)?(new String[0]):rt;  	
    }
    
    /**
	 * 运算带有参数的字符串。参数为start和end包围的部分
	 * 1)参数没有或者空串：返回"null"
	 * @param map 参数表
	 * @param exp 表达式，包含{表达式}
	 * @return 参数被替换了的表达式
	 */
	public static String buildExpression(HashMap<String,Object> map,String exp,String start,String end){
		if(exp==null)return null;
		String[] keys=getFormulaMacParams(exp,start,end);
		String rt=exp;
		for(int i=0;i<keys.length;i++){
			if(!map.containsKey(keys[i])){
				continue;
			}
			String v=String.valueOf(map.get(keys[i]));
			rt=replaceAll(rt,start+keys[i]+end,v);
		}
		return rt;
	}

    /**
	 * 运算带有参数的字符串。参数为大括号包围的部分，如表达式abc"{def}"{xyz}dd的参数有两个：def和xyz
	 * 1)参数没有或者空串：返回"null"
	 * @param map 参数表
	 * @param exp 表达式，包含{表达式}
	 * @return 参数被替换了的表达式
	 */
	public static String buildExpression(Map<String,String> map,String exp){
		if(exp==null)
			return null;
		if(map==null)
			return exp;
		
		String[] keys=getFormulaMacParamsWest(exp);
		String rt=exp;
		for(int i=0;i<keys.length;i++){
			if(!map.containsKey(keys[i])){
				continue;
			}
			String v=String.valueOf(map.get(keys[i])==null?"":map.get(keys[i]));
			//如果对应null，则解析为空字符串
			if(v == null)
				v = "";
			
			rt=replaceAll(rt,"{"+keys[i]+"}",v);
		}
		return rt;
	}
	
	/**
	 * 运算带有参数的字符串。参数为大括号包围的部分，如表达式abc"{def}"{xyz}dd的参数有两个：def和xyz
	 * 1)参数没有或者空串：返回"null"
	 * @param map1 参数表1
	 * @param map2 参数表2,参数表1中未找到对应参数时，从参数表2中查找
	 * @param exp 表达式，包含{表达式}
	 * @return 参数被替换了的表达式
	 */
	public static String buildExpression(Map<String,String> map1,Map<String,Object> map2,String exp){
		if (exp == null)
			return null;
		if (map1 == null || map2 == null)
			return exp;
		
		String[] keys = getFormulaMacParamsWest(exp);
		String rt = exp;
		for (int i = 0; i < keys.length; i++) {
			//从两个map对象中取替换值，如果参数1中存在则取参数1
			String val = null;
			String valMap1 = map1.get(keys[i]);
			
			if( valMap1 == null){
				Object val2Obj = map2.get( keys[i] );
				if(val2Obj != null){
					val = val2Obj.toString();
				}
			}else{
				val = valMap1;
			}
			
			if(val == null){//两个参数中均未查到对应值
				continue;
			}
			
			rt = replaceAll( rt, "{" + keys[i] + "}", val );
		}
		return rt;
	}
	
	public static void main(String[] args) {
		String str = "告警:{ALARM_LEVEL},时间：{TIME} .{ACNUM}氧气压力当前{T_PRESS}标态15度{S46ST_PRESS}，低于门限{LIMIT_VALUE}。";
		String[] keys = getFormulaMacParamsEast(str);
		
		System.out.println(keys);
	}

}
