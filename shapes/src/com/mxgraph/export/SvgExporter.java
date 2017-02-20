package com.mxgraph.export;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SvgExporter
 */
public class SvgExporter extends HttpServlet {
	private static final String MIME = "image/svg+xml";
	private static final String DEFAULT_FILENAME = "ExportedDiagram.svg";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SvgExporter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Does nothing
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String xmlString = URLDecoder.decode(request.getParameter("xml"), "UTF-8");
		try {
			String filename = DEFAULT_FILENAME;
			byte[] data = xmlString.getBytes("UTF-8");

			if (data != null) {
				response.setStatus(HttpServletResponse.SC_OK);

				response.setContentType(MIME);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + filename + "\"; filename*=UTF-8''" + filename);

				OutputStream out = response.getOutputStream();
				out.write(data);
				out.close();
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
	}

}
