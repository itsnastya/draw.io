package com.mxgraph.shapes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class customShapesGiver
 */
public class customShapesGiver extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TYPE = "type";
	private static final String CLASS_TYPE = "class";
	private static final String JS_FILE_CLASS = "function firstShape()\n" + 
			"{\n" + 
			"	mxCylinder.call(this);\n" + 
			"};\n" + 
			"\n" + 
			"mxUtils.extend(firstShape, mxCylinder);\n" + 
			"firstShape.prototype.size = 30;\n" + 
			"firstShape.prototype.redrawPath = function (graphics, x, y, width, height, f) {\n" + 
			"	graphics.moveTo(0, height / 2);\n" + 
			"	graphics.lineTo(width / 2, 0);\n" + 
			"	graphics.lineTo(width, height / 2);\n" + 
			"	graphics.end();\n" + 
			"};\n" + 
			"\n" + 
			"mxCellRenderer.prototype.defaultShapes.firstShape = firstShape;\n" + 
			"function secondShape()\n" + 
			"{\n" + 
			"	mxCylinder.call(this);\n" + 
			"};\n" + 
			"\n" + 
			"mxUtils.extend(secondShape, mxCylinder);\n" + 
			"secondShape.prototype.size = 30;\n" + 
			"secondShape.prototype.redrawPath = function (graphics, x, y, width, height, f) {\n" + 
			"	graphics.moveTo(0, 0);\n" + 
			"	graphics.lineTo(width / 2, height / 2);\n" + 
			"	graphics.lineTo(width, 0);\n" + 
			"	graphics.end();\n" + 
			"};\n" + 
			"mxCellRenderer.prototype.defaultShapes.secondShape = secondShape;\n" + 
			"\n" + 
			"Sidebar.prototype.addCustomPalette = function (a) {\n" + 
			"	this.addPaletteFunctions(\"custom\", \"Custom Shapes\", false, this.createCustomShapes());\n" + 
			"}\n" + 
			"\n" + 
			"Sidebar.prototype.createCustomShapes = function () {\n" + 
			"	var a = this;\n" + 
			"	var c = new mxCell(\"List Item\", new mxGeometry(0, 0, 60, 26), \"text;html\\x3d1;strokeColor\\x3dnone;fillColor\\x3dnone;align\\x3dleft;verticalAlign\\x3dtop;spacingLeft\\x3d4;spacingRight\\x3d4;whiteSpace\\x3dwrap;overflow\\x3dhidden;rotatable\\x3d0;points\\x3d[[0,0.5],[1,0.5]];portConstraint\\x3deastwest;\");\n" + 
			"	c.vertex = true;\n" + 
			"	return [\n" + 
			"		this.createVertexTemplateEntry(\"shape\\x3dfirstShape;perimeter\\x3drhombusPerimeter;whiteSpace\\x3dwrap;html\\x3d1;\", 80, 80, \"\", \"Custom Figure 1\", null, null, \"collate\"),\n" + 
			"		this.createVertexTemplateEntry(\"shape\\x3dsecondShape;perimeter\\x3drhombusPerimeter;whiteSpace\\x3dwrap;html\\x3d1;\", 80, 80, \"\", \"Custom Figure 2\", null, null, \"collate\")\n" + 
			"	];\n" + 
			"};\n" + 
			"\n" + 
			"MyApp.sidebar.addCustomPalette();\n";
	private static final String JS_FILE_ACTIVITY = "function firstShape()\n" + 
			"{\n" + 
			"	mxCylinder.call(this);\n" + 
			"};\n" + 
			"\n" + 
			"mxUtils.extend(firstShape, mxCylinder);\n" + 
			"firstShape.prototype.size = 30;\n" + 
			"firstShape.prototype.redrawPath = function (graphics, x, y, width, height, f) {\n" + 
			"	graphics.moveTo(0, height);\n" + 
			"	graphics.lineTo(width / 2, 0);\n" + 
			"	graphics.lineTo(width, height);\n" + 
			"	graphics.end();\n" + 
			"};\n" + 
			"\n" + 
			"mxCellRenderer.prototype.defaultShapes.firstShape = firstShape;\n" + 
			"function secondShape()\n" + 
			"{\n" + 
			"	mxCylinder.call(this);\n" + 
			"};\n" + 
			"\n" + 
			"mxUtils.extend(secondShape, mxCylinder);\n" + 
			"secondShape.prototype.size = 30;\n" + 
			"secondShape.prototype.redrawPath = function (graphics, x, y, width, height, f) {\n" + 
			"	graphics.moveTo(0, 0);\n" + 
			"	graphics.lineTo(width / 2, height);\n" + 
			"	graphics.lineTo(width, 0);\n" + 
			"	graphics.end();\n" + 
			"};\n" + 
			"mxCellRenderer.prototype.defaultShapes.secondShape = secondShape;\n" + 
			"\n" + 
			"Sidebar.prototype.addCustomPalette = function (a) {\n" + 
			"	this.addPaletteFunctions(\"custom\", \"Custom Shapes\", false, this.createCustomShapes());\n" + 
			"}\n" + 
			"\n" + 
			"Sidebar.prototype.createCustomShapes = function () {\n" + 
			"	var a = this;\n" + 
			"	var c = new mxCell(\"List Item\", new mxGeometry(0, 0, 60, 26), \"text;html\\x3d1;strokeColor\\x3dnone;fillColor\\x3dnone;align\\x3dleft;verticalAlign\\x3dtop;spacingLeft\\x3d4;spacingRight\\x3d4;whiteSpace\\x3dwrap;overflow\\x3dhidden;rotatable\\x3d0;points\\x3d[[0,0.5],[1,0.5]];portConstraint\\x3deastwest;\");\n" + 
			"	c.vertex = true;\n" + 
			"	return [\n" + 
			"		this.createVertexTemplateEntry(\"shape\\x3dfirstShape;perimeter\\x3drhombusPerimeter;whiteSpace\\x3dwrap;html\\x3d1;\", 80, 80, \"\", \"Custom Figure 1\", null, null, \"collate\"),\n" + 
			"		this.createVertexTemplateEntry(\"shape\\x3dsecondShape;perimeter\\x3drhombusPerimeter;whiteSpace\\x3dwrap;html\\x3d1;\", 80, 80, \"\", \"Custom Figure 2\", null, null, \"collate\")\n" + 
			"	];\n" + 
			"};\n" + 
			"\n" + 
			"MyApp.sidebar.addCustomPalette();\n";
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String type = String.valueOf(request.getParameter(TYPE));
		if (CLASS_TYPE.equals(type))
		{
			out.print(JS_FILE_CLASS);
		}
		else
		{
			out.print(JS_FILE_ACTIVITY);
		}
	}

}
