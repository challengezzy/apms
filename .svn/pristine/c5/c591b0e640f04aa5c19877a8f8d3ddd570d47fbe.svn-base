package com.apms.bs.file;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class FileServletURI extends HttpServlet implements Serializable{

    private static final long serialVersionUID = 2L;
    
    public HttpServletRequest request;
    public HttpServletResponse response;
    public FlexSession session;
    
    private String servletName = "apmsFileDownloadServlet";


    public FileServletURI() {
        request = FlexContext.getHttpRequest();
        session = FlexContext.getFlexSession();
        response = FlexContext.getHttpResponse();
    }
    
    public String  getDownLoadURI(String url,String filename) throws IOException {

    	String uri = null;
    	try {
    	    HttpServletRequest req = FlexContext.getHttpRequest();
            String contextRoot = null;
            contextRoot = req.getContextPath();
            uri = contextRoot+"/"+servletName+"?fileName="+url+"&reportName="+filename;

        } catch (Exception e) {

            e.printStackTrace();

		}
        return uri;
    }
    
    public String getUpLodaURI(){
    	
    	String uri = null;
    	try {
    	    HttpServletRequest req = FlexContext.getHttpRequest();
            String contextRoot = null;
            contextRoot = req.getContextPath();
            uri = contextRoot+"/fileUploadServlet";

        } catch (Exception e) {

            e.printStackTrace();

		}
        return uri;
    	
    }

}
