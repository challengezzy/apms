package com.apms.webfigure;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.vibration.VibrationFigureVo;
import com.apms.bs.vibration.VibrationService;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigure;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigureHtmlGenerator;

public class VibrationFigureServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
    public void init(ServletConfig config) throws ServletException
    {
    	super.init(config);
    }

    public void destroy()
    {
        super.destroy();
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
    	String acnum = request.getParameter("acnum");
        String groupid = request.getParameter("groupid");
        String dim = request.getParameter("dim");
        
        try
        {
           System.out.println("抖动分析图展示....");
        	VibrationService vibService = new VibrationService();
        	HashVO[] vos = vibService.getVibVos(acnum, groupid);
        	StringBuffer buffer = new StringBuffer();
        	
        	VibrationFigureVo figureVoZ = vibService.vibrationCompute(vos,acnum,groupid,"Z",true,true);
        	if(figureVoZ.getSpwvdFigure() == null){
        		System.out.println(figureVoZ.getResultDesc());
        		buffer.append("<BR>");
        		buffer.append(figureVoZ.getResultDesc());
        		buffer.append("<BR>");
        		
        		PrintWriter out = response.getWriter();
        		out.println(buffer.toString());
        		return ;
        	}
        	VibrationFigureVo figureVoY = vibService.vibrationCompute(vos,acnum,groupid,"Y",true,true);        	
        	
        	String width = "560";
        	String height = "300";
        	String heightSpw = "400";
        	
            //图形输出为HTML格式
            String spwvdZStr = getFigureEmbedString(figureVoZ.getSpwvdFigure().getFigure(),"spwvd_z",heightSpw,width,request,response );
            String spwvdYStr = getFigureEmbedString(figureVoY.getSpwvdFigure().getFigure(),"spwvd_y",heightSpw,width,request,response );
            
        	String timeZstr = getFigureEmbedString(figureVoZ.getFftResult().gettFigure(),"time_z",height,width,request,response );
        	String timeYstr = getFigureEmbedString(figureVoY.getFftResult().gettFigure(),"time_y",height,width,request,response );
        	String freqZstr = getFigureEmbedString(figureVoZ.getFftResult().getfFigure(),"freq_z",height,width,request,response );
        	String freqYstr = getFigureEmbedString(figureVoY.getFftResult().getfFigure(),"freq_y",height,width,request,response );
            
            buffer.append("<TABLE border=\"1\" >");
            
			// 小波图
			buffer.append("<TR>");
			buffer.append("<TD>");
			buffer.append(spwvdZStr);
			buffer.append("</TD>");

			buffer.append("<TD>");
			buffer.append(spwvdYStr);
			buffer.append("</TD>");
			buffer.append("</TR>");

			// 时域图
			buffer.append("<TR>");
			buffer.append("<TD>");
			buffer.append(timeZstr);
			buffer.append("</TD>");

			buffer.append("<TD>");
			buffer.append(timeYstr);
			buffer.append("</TD>");
			buffer.append("</TR>");

			// 频域图
			buffer.append("<TR>");
			buffer.append("<TD>");
			buffer.append(freqZstr);
			buffer.append("</TD>");

			buffer.append("<TD>");
			buffer.append(freqYstr);
			buffer.append("</TD>");
			buffer.append("</TR>");

			buffer.append("</TABLE>");
			buffer.append("<BR>");

			response.getOutputStream().print(buffer.toString());
        }
        catch(Exception e)
        {
        	System.out.println("生成抖动分析图形异常！");
            e.printStackTrace();
        }finally{
        	new CommDMO().releaseContext(ApmsConst.DS_APMS);
        }

    }
    
    /**
     * 生成图形异常的字符串
     * @param figure 图形对象
     * @param figureId 图形标识符
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private String getFigureEmbedString(WebFigure figure,String figureId,String height,String width,HttpServletRequest request, HttpServletResponse response) throws Exception{
       
    	getServletContext().setAttribute(figureId,figure);
        //图形输出为HTML格式
        WebFigureHtmlGenerator webFigureHtmlGen = new WebFigureHtmlGenerator(request,response);
        String figureStr = webFigureHtmlGen.getFigureEmbedString(figure,figureId, "application",width,height,null);
        
        return figureStr;
    }
    
}
