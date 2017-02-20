package com.mxgraph.tooltips;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TooltipService
 */
public class TooltipService extends HttpServlet {
	
	private static final String ELLIPSE_CLASS_NAME = "mxellipse";
	private static final String RECTANGLE_CLASS_NAME = "mxlabel";
	private static final String TYPE = "type";
	private static final String RECTANGLE_TOOLTIP_JSON = "{ \"value\" : [ \"red\", \"green\", \"blue\" ] }";
	private static final String ELLIPSE_TOOLTIP_JSON = "{ \"value\" : [ \"yellow\", \"pink\", \"orange\" ] }";
	private static final String DEFAULT_TOOLTIP_JSON = "{ \"value\" : [\"gray\"] }";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TooltipService() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Does nothing
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter(TYPE);
		PrintWriter out = response.getWriter();
		switch (type.toLowerCase()) {
		case RECTANGLE_CLASS_NAME:
			out.println(RECTANGLE_TOOLTIP_JSON);
			break;

		case ELLIPSE_CLASS_NAME:
			out.print(ELLIPSE_TOOLTIP_JSON);
			break;
			
		default:
			out.print(DEFAULT_TOOLTIP_JSON);
			break;
		}
		
		out.flush();
		out.close();
	}

}
