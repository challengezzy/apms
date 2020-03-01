package com.apms.sample;

import java.util.ArrayList;
import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.DateUtil;

public class Test {
	
	public static void main(String[] argc) throws Exception {
		
		System.out.println( ReportParseUtil.strToFloatWithIntPostion("02.86", 2,"ehr_g_a")) ;
		System.out.println( ReportParseUtil.strToFloatWithIntPostion("0286", 2,"ehr_g_a")) ;
		
		SupperC parent = new SubClass();
		System.out.println();
		
		ArrayList<String> list = new ArrayList<String>();
		String str = "string 1";
		list.add(str);
		str = "test2";
		list.add(str);
		
		str = "list item 3";
		list.add(str);
		
		for(String tempStr : list){
			System.out.println(tempStr);
		}
		
		String dstr = "2016-12-03 121534";
		Date dt = DateUtil.StringToDate(dstr, "yyyy-MM-dd HHmmss");
		
		System.out.println(dt);
		
		Date nt = new Date(1000);
		System.out.println(nt);
		
	}
}
