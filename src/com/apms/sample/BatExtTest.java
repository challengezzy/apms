package com.apms.sample;

import java.io.IOException;

/**
 * 在JAVA中，可以通过Runtime来实现对BAT或EXE的调用：
 * @author jerry
 * @date Oct 1, 2015
 */
public class BatExtTest {

	public static void main(String[] args) { 
	    Runtime rt = Runtime.getRuntime(); 
	    try {
	      long begin = System.currentTimeMillis();
	      String file = "D:/apudata/train_apu.bat"; 
	      rt.exec("cmd.exe /c start " + file);
	      System.out.println("bat文件调用成功");
	      
	      long end = System.currentTimeMillis();
	      
	      System.out.println("耗时"+(end-begin)/1000+" s");
	      
	    } catch (IOException e) { 
	      e.printStackTrace(); 
	    } 
	  }
	
}
