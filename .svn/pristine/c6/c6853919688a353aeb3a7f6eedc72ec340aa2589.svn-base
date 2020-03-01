package com.apms.webfigure;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;

import com.apms.bs.vibration.VibrationService;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.function.ApmsMatlabFuncService;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigure;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigureHtmlGenerator;
import com.mathworks.toolbox.javabuilder.MWJavaObjectRef;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.mathworks.toolbox.javabuilder.MWException;

public class MagicSquareServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ApmsMatlabFuncService funcService;
	
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);

        try
        {
        	funcService = new ApmsMatlabFuncService();
        }
        catch(MWException e)
        {
            e.printStackTrace();
        }
    }

    public void destroy()
    {
        super.destroy();

        ApmsMatlabFuncService.disposeAllInstances();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        MWNumericArray size = new MWNumericArray(Integer.parseInt(request.getParameter("size")));
        double[][] square = new double[0][];
        WebFigure figure = null;
        try
        {
            Object[] results = funcService.getMagicWebFigure(1, size);
        	MWJavaObjectRef ref = (MWJavaObjectRef)results[0];
        	
        	System.out.println("*******************************************************************");
//        	VibrationService vibService = new VibrationService();
//        	Object result = vibService.analyseVibration("B6236", "2");
//        	MWJavaObjectRef ref = (MWJavaObjectRef)result;
        	
            figure = (WebFigure)ref.get();
            getServletContext().setAttribute("UserPlot",figure);

            Object[] result2 = funcService.getMagic(1, size);

            MWNumericArray array = (MWNumericArray)result2[0];
            square = (double[][])array.toArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();

        //WebFigureHtmlGenerator webFigureHtmlGen = new WebFigureHtmlGenerator("WebFigures",getServletContext());
        WebFigureHtmlGenerator webFigureHtmlGen = new WebFigureHtmlGenerator(request,response);
        if(figure!=null)
        {
            try
            {
//                String outputString = webFigureHtmlGen.getFigureEmbedString(figure,"UserPlot", "application","530","530",null);
            	String outputString = webFigureHtmlGen.getFigureEmbedString(figure,"UserPlot", "application","400","430",null);
                buffer.append(outputString);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        buffer.append("<BR>");
        buffer.append("<BR>");

        buffer.append("<TABLE >");
        for (double[] row : square)
        {
            buffer.append("<TR>");
            for (double value : row)
            {
                buffer.append("<TH>");
                buffer.append(new Double(value).intValue());
            }
        }
        buffer.append("</TABLE>");
        buffer.append("<BR>");
        response.getOutputStream().print(buffer.toString());
    }
}
