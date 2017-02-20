function firstShape()
{
	mxCylinder.call(this);
};

mxUtils.extend(firstShape, mxCylinder);
firstShape.prototype.size = 30;
firstShape.prototype.redrawPath = function(graphics, x, y, width, height, f) {
	graphics.moveTo(0, height);
	graphics.lineTo(width / 2, 0);
	graphics.lineTo(width, height);
    graphics.end();
};
mxCellRenderer.prototype.defaultShapes.firstShape = firstShape;

function secondShape()
{
	mxCylinder.call(this);
};

mxUtils.extend(secondShape, mxCylinder);
secondShape.prototype.size = 30;
secondShape.prototype.redrawPath = function(graphics, x, y, width, height, f) {
	graphics.moveTo(0, 0);
	graphics.lineTo(width / 2, height);
	graphics.lineTo(width, 0);
    graphics.end();
};
mxCellRenderer.prototype.defaultShapes.secondShape = secondShape;

Sidebar.prototype.addCustomPalette = function(a) {
    this.addPaletteFunctions("custom", "Custom Shapes", false, this.createCustomShapes());
}

Sidebar.prototype.createCustomShapes = function() {
	var a = this;
	var c = new mxCell("List Item",new mxGeometry(0,0,60,26),"text;html\x3d1;strokeColor\x3dnone;fillColor\x3dnone;align\x3dleft;verticalAlign\x3dtop;spacingLeft\x3d4;spacingRight\x3d4;whiteSpace\x3dwrap;overflow\x3dhidden;rotatable\x3d0;points\x3d[[0,0.5],[1,0.5]];portConstraint\x3deastwest;");
  c.vertex = true;
  return [
	  this.createVertexTemplateEntry("shape\x3dfirstShape;perimeter\x3drhombusPerimeter;whiteSpace\x3dwrap;html\x3d1;", 80, 80, "", "Custom Figure 1", null, null, "collate"),
	  this.createVertexTemplateEntry("shape\x3dsecondShape;perimeter\x3drhombusPerimeter;whiteSpace\x3dwrap;html\x3d1;", 80, 80, "", "Custom Figure 2", null, null, "collate")
  ];
};

MyApp.sidebar.addCustomPalette();
