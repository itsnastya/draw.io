console.log('Code loaded');

//Добавление формы Штуковина
function sht21()
{
	mxCylinder.call(this);
};

mxUtils.extend(sht21, mxCylinder);
sht21.prototype.size = 30;
sht21.prototype.redrawPath = function(b, a, d, c, e, f) {
	a = Math.max(0, Math.min(c, Math.min(e, parseFloat(mxUtils.getValue(this.style, "size", this.size)))));
    f ? (b.moveTo(a, e),
    //b.lineTo(a, a),
    b.lineTo(0, 0),
    b.moveTo(a, a),
    b.lineTo(c, a)) : (b.moveTo(0, 0),
    //b.lineTo(c - a, 0),
    b.lineTo(c, a),
    b.lineTo(c, e),
    b.lineTo(a, e),
    //b.lineTo(0, e - a),
    b.lineTo(0, 0),
    b.close());
    b.end()
}
;
mxCellRenderer.prototype.defaultShapes.annatoly2shtuk = sht21;

function sht22()
{
	mxCylinder.call(this);
};

mxUtils.extend(sht22, mxCylinder);
sht22.prototype.size = 30;
sht22.prototype.redrawPath = function(b, a, d, c, e, f) {
	a = Math.max(0, Math.min(c, Math.min(e, parseFloat(mxUtils.getValue(this.style, "size", this.size)))));
    f ? (b.moveTo(a, e),
    b.lineTo(a, a),
    b.lineTo(0, 0),
    b.moveTo(a, a),
    b.lineTo(c, a)) : (b.moveTo(0, 0),
    b.lineTo(c - a, 0),
    //b.lineTo(c, a),
    //b.lineTo(c, e),
    //b.lineTo(a, e),
    b.lineTo(0, e - a),
    b.lineTo(0, 0),
    b.close());
    b.end()
}
;
mxCellRenderer.prototype.defaultShapes.annatoly2shtuk2 = sht22;

// Создание палитры
Sidebar.prototype.addAnnatoly2Palette = function(a) {
    this.addPaletteFunctions("annatoly2", mxResources.get("annatoly2"), null != a ? a : !1, this.createAnnatoly2Shapes())
}

Sidebar.prototype.createAnnatoly2Shapes = function() {
	var a = this
    , c = new mxCell("List Item",new mxGeometry(0,0,60,26),"text;html\x3d1;strokeColor\x3dnone;fillColor\x3dnone;align\x3dleft;verticalAlign\x3dtop;spacingLeft\x3d4;spacingRight\x3d4;whiteSpace\x3dwrap;overflow\x3dhidden;rotatable\x3d0;points\x3d[[0,0.5],[1,0.5]];portConstraint\x3deastwest;");
  c.vertex = !0;
  return [
	  this.createVertexTemplateEntry("shape\x3dannatoly2shtuk2;perimeter\x3drhombusPerimeter;whiteSpace\x3dwrap;html\x3d1;", 80, 80, "", "Shtukovina!!!", null, null, "collate"),
	  this.createVertexTemplateEntry("shape\x3dshtuk;perimeter\x3drhombusPerimeter;whiteSpace\x3dwrap;html\x3d1;", 80, 80, "", "Shtukovina!!!", null, null, "collate"),
  ]
};

MyApp.sidebar.addAnnatoly2Palette();
