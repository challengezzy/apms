package com.apms.bs.datatask;

import java.util.Date;

import org.dom4j.Element;

import com.apms.bs.email.EmailService;
import com.apms.bs.util.DateUtil;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 邮件发送测试
 * @author jerry
 * @date Jun 24, 2014
 */
public class DTEmailSendTest implements DataTaskExecuteIFC{

	@Override
	public void dataTaskExec(Element task, DataTaskExecThread mainThread) throws Exception {
		
		
		String host = task.elementText("HOST");
		String username = task.elementText("USERNAME");
		String password = task.elementText("PASSWORD");
		String from = task.elementText("FROM");
		String to = task.elementText("TO");
		
		String content = "邮件发送测试！！" + "\n" + " " + DateUtil.getDateStr(new Date()) ;
		
		mainThread.logTaskRun("执行邮件发送任务!username= "+username+", password= " + password + " , from=" + from);
		EmailService mailService = new EmailService(host, username, password, from, false);
		mailService.sendMail(to, "邮件发送测试"+DateUtil.getDateStr(new Date()), content);
		
	}
	
}
