
/* Simple JavaScript for handle Roassal2 in javascript
 * By milton mamani akevalion@gmail.com

 */
(function(){
  var initializing = false, fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

  this.Class = function(){};
  Class.extend = function(prop) {
    var _super = this.prototype;
    initializing = true;
    var prototype = new this();
    initializing = false;

    for (var name in prop) {
      prototype[name] = typeof prop[name] == "function" &&
        typeof _super[name] == "function" && fnTest.test(prop[name]) ?
        (function(name, fn){
          return function() {
            var tmp = this._super;
            this._super = _super[name];

            var ret = fn.apply(this, arguments);
            this._super = tmp;

            return ret;
          };
        })(name, prop[name]) :
        prop[name];
    }

    function Class() {
      if ( !initializing && this.init )
        this.init.apply(this, arguments);
    }
    Class.prototype = prototype;
    Class.prototype.constructor = Class;
    Class.extend = arguments.callee;
    return Class;
  };
})();

/*
mamani library
*/

function createSVG(name){
	return document.createElementNS("http://www.w3.org/2000/svg", name);
}

Number.prototype.max = function (n){
	if(this > n) return this.valueOf();
	return n;
}
Number.prototype.min = function (n){
	if(this < n ) return this.valueOf();
	return n;
}

Number.prototype.degreesToRadians=function(){
	return this* 0.017453292519943295;
}

/*point functions*/
function minus(a, b){
	return {x: a.x - b.x, y: a.y - b.y};
}
function plus(a, b){
	return {x: a.x+b.x, y: a.y+b.y};
}
function product(a, b){
	if(b.x != null)return {x: a.x*b.x, y: a.y*b.y};
	return {x: a.x * b, y: a.y*b};
}
function negated(a){
	return {x: -a.x, y: -a.y};
}
function normal(p){
	var n = {x: -p.y, y: p.x};
	var d = n.x*n.x + n.y*n.y;
	if(d == 0) return {x:-1, y:0};
	return this.div(n, Math.sqrt(d));
}
function div(a, b){
	if(b.x!=null)return {x: a.x/b.x, y: a.y/b.y};
	return {x: a.x/b, y: a.y/b};
}
function r(p){
	return Math.sqrt(dotProduct(p, p));
}
function dotProduct(a, b){
	return a.x*b.x + a.y*b.y;
}
function lessThan(a,b){
	return a.x<b.x && a.y<b.y;
}
function interpolate(i, f, t){
	if(i.x != null)
		return plus(i, product(minus(f, i), t) );
	else return i + (f - i)*t;
}
function eq(a, b){
	return a.x == b.x && a.y == b.y;
}
function lessEqThan(a, b){
	return a.x <= b.x && a.y <= b.y;
}
function lessThan(a, b){
	return a.x<b.x && a.y<b.y;
}
function transposed(p){
	return {x: p.y, y: p.x};
}
function distanceTo(a, b){
  var dx = b.x - a.x, dy = b.y - a.y;
  return Math.sqrt(dx*dx + dy * dy);
}
/*roassal/pharo classes*/
var Rectangle = Class.extend({
	origin: null,
	corner: null,
	init: function (origin, corner){
		this.origin = origin;
		this.corner = corner;
	},
	width: function(){
		return this.corner.x- this.origin.x;
	},
	height: function(){
		return this.corner.y - this.origin.y;
	},
	encompassing: function(list){
		var o = list[0];
		var c = o;
		for(var k = 0; k<list.length; k++){
			o = this.min(o, list[k]);
			c = this.max(c, list[k]);
		}
		this.origin = o;
		this.corner = c;
		return this;
	},
	min: function (a, b){
		return {x: a.x.min(b.x), y: a.y.min(b.y)}
	},
	max: function (a, b){
		return {x: a.x.max(b.x), y: a.y.max(b.y)}
	},
	center: function (){
		var o = this.origin;
		var c = this.corner;
		return {x: (o.x+c.x)/2, y: (o.y+c.y)/2}
	},
	mergeRect: function(rect){
		this.origin = this.min(this.origin, rect.origin);
		this.corner = this.max(this.corner, rect.corner);
	},
	rightCenter: function (){
		return {x: this.corner.x, y: this.center().y};
	},
	leftCenter: function(){
		return {x: this.origin.x, y: this.center().y};
	},
	topCenter: function(){
		return {x: this.center().x, y: this.origin.y};
	},
	bottomCenter: function(){
		return {x: this.center().x, y: this.corner.y};
	},
	bottomLeft: function(){
		return {x: this.origin.x, y: this.corner.y};
	},
	bottomRight: function(){
		return this.corner;
	},
	topLeft: function(){
		return this.origin;
	},
	topRight: function(){
		return {x: this.corner.x, y: this.origin.y};
	},
	extent: function(){
		return {x: this.corner.x - this.origin.x, y: this.corner.y-this.origin.y}
	},
	pointNearestTo: function(p){
		var res, side;
		if(this.containsPoint(p)){
			side = this.sideNearestTo(p);
			if(side == "right") res={x: this.corner.x, y: p.y};
			else if(side == "left") res={x: this.origin.x, y: p.y};
			else if(side == "bottom") res={x: p.x, y: this.corner.y};
			else res ={x: p.x, y: this.origin.y};
		}else res = this.adhereTo(p);
		return res;
	},
	adhereTo: function(p){
		if(this.containsPoint(p)) return aPoint;
		else return {x:p.x.max(this.origin.x).min(this.corner.x), y: p.y.max(this.origin.y).min(this.corner.y)};
	},
	containsPoint: function(aPoint){
		return lessEqThan(this.origin, aPoint) && lessThan(aPoint, this.corner);
	},
	eq: function(rect){
		return eq(this.origin, rect.origin) && eq(this.corner, rect.corner);
	},
	containsRect: function(rect){
		return lessEqThan(this.origin, rect.origin) && lessEqThan(rect.corner, this.corner);
	},
	intersectLineSegment: function(lineSegment){
		return lineSegment.intersectRectangle(this);
	}
});

var MatrixTransform = Class.extend({
	sx: null,
	shx: null,
	x: null,
	shy: null,
	sy: null,
	y: null,
	init: function (obj){
		if(obj == null) return this.loadIdentity();
		this.sx = obj.sx;
		this.shx = obj.shx;
		this.x = obj.x;
		this.shy = obj.shy;
		this.sy = obj.sy;
		this.y = obj.y;
	},
	loadIdentity: function(){
		this.sx = this.sy = 1.0;
		this.shx = this.shy = this.x = this.y = 0.0;
	},
	asString: function (){
		return "matrix("+this.sx+","+this.shy+","+this.shx+","+this.sy+","+this.x+","+this.y+")";
	},
	scaleBy: function(f){
		if(f.x != null){
			this.sx = this.sx*f.x;
			this.shx = this.shx*f.y;
			this.sy = this.sy*f.y;
			this.shy = this.shy*f.x;
		}else{
			this.sx = this.sx*f;
			this.shx = this.shx*f;
			this.sy = this.sy*f;
			this.shy = this.shy*f;
		}
		return this;
	},
	translateBy: function(p){
		this.x = this.sx*p.x+this.shx*p.y+this.x;
		this.y = this.shy*p.x+this.sy*p.y+this.y;
		return this;
	},
	transformRect: function(rect){
		var r = new Rectangle();
		r.origin = this.transform(rect.origin);
		r.corner = this.transform(rect.corner);
		return r;
	},
	transform: function(p){
		return {x: this.sx*p.x+this.shx*p.y+this.x,
				y: this.shy*p.x+this.sy*p.y+this.y}
	}
});

var TREvent = Class.extend({
	canvas: null,
	position: null,
	raw: null
});

var TRShapeEvent = TREvent.extend({
	shape: null
});

var TRMouseClick = TRShapeEvent.extend({});
var TRMouseLeftClick = TRMouseClick.extend({});
var TRMouseRightClick = TRMouseClick.extend({});
var TRMouseDoubleClick = TRShapeEvent.extend({});

var TRMouseDragging = TRShapeEvent.extend({step: null});
var TRMouseDragStart = TRMouseDragging.extend({});
var TRMouseDragEnd = TRMouseDragging.extend({});

var TRMouseEnter = TRShapeEvent.extend({});
var TRMouseLeave = TRShapeEvent.extend({});
var TRMouseMove = TRShapeEvent.extend({});

var TRMouseWheelEvent = TRShapeEvent.extend({
	direction: null
});

/*TODO Wheel event and Key events*/

var TRInteraction = Class.extend({
	initOn: function (s){ /*override*/}
});
var RTAbstractDraggable = TRInteraction.extend({
	groupToDrag: null,
	ides: null,
	init: function(arr){
		this.ides = arr;
	},
	initOn: function (s){
		var self = this;
		s.whenDo(TRMouseDragging, function(e){
			var t = new TRGroup()
			t.addAll(self.getGroup(s));
			if(!t.includes(s)) t.add(s);
			var sc = s.canvas.camera.scale;
			t.translateBy(self.transformDistance({x: e.step.x/sc, y: e.step.y/sc}, s));
			e.canvas.update();
		});
	},
	getGroup: function(s){
		var g = this.groupToDrag;
		if(g == null){
			g = this.groupToDrag = [];
			var ides = this.ides;
			if(ides == null) return g;
			for(var k = 0; k<ides.length; k++)
				g[k] = s.canvas.getShapeById(ides[k]);
		}
		return g;
	},
	transformDistance: function(distAsPoint,s){ return distAsPoint;}
});
var RTDraggable = RTAbstractDraggable.extend({});

var RTDraggableView = TRInteraction.extend({
	initOn: function (canvas){
		canvas.whenDo(TRMouseDragging, function(e){
			var s = canvas.camera.scale;
			canvas.camera.translateBy({x: e.step.x/s, y: e.step.y/s});
		});
	}
});

var RTLinkView = TRInteraction.extend({
	link: null,
	init: function(o){
		this.link = o;
	},
	initOn: function(s){
		var link = this.link;
		s.whenDo(TRMouseClick, function(e){
			window.open(link, "_blank");
		});
	}
});

var RTZoomableView = TRInteraction.extend({
	initOn: function (canvas){
		canvas.whenDo(TRMouseWheelEvent, function(e){
			var s = canvas.camera.scale;
			var dist = minus(e.position, negated(canvas.camera.position));
			if(e.direction == "up")
        if(s * 1.25 <= 10){ s *= 1.25; dist = product(dist, -0.2);}
        else return;
			if(e.direction == "down")
        if( s * 0.8 >= 0.1){s *=0.8; dist = product(dist, 0.25);}
        else return;
			canvas.camera.scale=s;
			canvas.camera.translateBy(dist);
		});
	}
});

var RTFocusElement = TRInteraction.extend({
	initOn: function(s){
		s.whenDo(TRMouseDoubleClick, function(e){
			//TODO
			var canvas = s.canvas;
			var rec = s.encompassingRectangle();
			var is = canvas.camera.scale;
			var ip = canvas.camera.position;
			var fp = negated(rec.center());
			var sc = div(canvas.camera.extent(), product(rec.extent(), 1.1));
			var fs = sc.x<sc.y?sc.x: sc.y;
			new VITimer({
				time: 1,
				refresh: function (t){
					var v = interpolate(is, fs, t);
					canvas.camera.scale=v;
					v = interpolate(ip, fp, t);
					canvas.camera.translateTo(v);
				}
			});
		});
	}
});

var VITimer = Class.extend({
	init: function(obj){
		var t = new Date().getTime();
		var cycles = obj.cycles?obj.cycles: 10;
		var callback = obj.callback?obj.callback: (function(){});
		var tini = t;
		var f = obj.time * 1000 + t;
		var cycle = (f - t)/cycles;

		var fun = function(){
			try{ obj.refresh((t-tini)/(f-tini));
			}catch(ex){console.log(ex);}
			t = new Date().getTime();
			if(t < f)
				setTimeout(fun, cycle);
			else{
				try{
					obj.refresh(1);
					callback();
				}catch(ex){console.log(ex);}
			}
		};
		fun();
	}
});

var RTLabelled = TRInteraction.extend({
	color: null,
	lowcolor: null,
	init: function(obj){
		this.color = obj.color;
		this.lowcolor = obj.lowcolor;
		this.lbl = obj.lbl;
	},
	initOn: function (s){
		var self = this;
		s.whenDo(TRMouseEnter, function(e){
			var shape = e.canvas.getShapeById(self.lbl);
			shape.setColor(self.color);
			shape.pushFront();
		});
		s.whenDo(TRMouseLeave, function(e){
			var shape = e.canvas.getShapeById(self.lbl);
			shape.setColor(self.lowcolor);
		});
	}
});

var RTAbstractHighlightable = TRInteraction.extend({
	shapes: null,
	ides: null,
	vars: {},
	initOn: function(s){
		var self = this;
		s.whenDo(TRMouseEnter, function(e){
			self.doHighlight(s);
		});
		s.whenDo(TRMouseLeave, function(e){
			self.doUnhighlight(s);
		})
	},
	doHighlight: function(s){
		this.unhighlightRecordedShapes();
		var shapes = this.highlightShapes(s);
		for(var k = 0; k<shapes.length; k++)
			this.doHighlightShape(shapes[k]);
		s.hshapes = shapes;
		this.vars.lastShapes = shapes;
	},
	unhighlightRecordedShapes: function(){
		var shapes = this.vars.lastShapes;
		if(shapes == null) return;
		for(var k = 0; k<shapes.length; k++)
			this.doUnhighlightShape(shapes[k]);
		this.vars.lastShapes = null;
	},
	highlightShapes: function(s){
		if(this.shapes == null){
			var g = this.shapes = [];
			var ides = this.ides;
			if(ides == null){
				this.shapes[0] = s;
				return this.shapes;
			}
			for(var k = 0; k<ides.length; k++){
				var e = s.canvas.getShapeById(ides[k]);
				if(e == null) continue;
				g[g.length] = e;
			}
		}
		return this.shapes;
	},
	doUnhighlight: function(s){
		var shapes = s.hshapes;
		if(shapes == null) return;
		for(var k = 0; k<shapes.length; k++)
			this.doUnhighlightShape(shapes[k]);
		s.hshapes = null;
	},
	doHighlightShape: function(s){
		//override
	},
	doUnhighlightShape: function(s){
		//override
	}
});

var RTShowLabel = RTAbstractHighlightable.extend({
	lbl:null,
	init: function(lbl){
		this.lbl = lbl;
	},
	doHighlightShape: function(s){
		s.slabels = [];
		for(var k = 0; k<this.lbl.length;k++){
			s.slabels[k] = s.canvas.addShape(this.lbl[k]);
			s.slabels[k].update();
		}
	},
	doUnhighlightShape: function(s){
		for(var k = 0; k<s.slabels.length; k++)
			s.slabels[k].remove();
	}
});

var RTHighlightable = RTAbstractHighlightable.extend({
	color: null,
	init:function(obj){
		this.color = obj.color;
		this.ides = obj.ides;
    this.ecolor = obj.ecolor;
    this.edges = obj.edges;
	},
	doHighlightShape: function(s){
    this.setColor(s, this.color);
    if(!this.ecolor) return;
    for(var k=0; k < this.edges.length; k++){
      var ed = s.canvas.getShapeById(this.edges[k]);
      this.setColor(ed, this.ecolor);
    }
	},
  setColor: function(s, color){
    s.ocolor=s.getColor();
		s.setColor(color);
  },
	doUnhighlightShape: function(s){
		if(!s.ocolor) return;
    this.setColor(s, s.ocolor);
		s.ocolor = null;
    if(!this.ecolor) return;
    for(var k=0; k < this.edges.length; k++){
      var ed = s.canvas.getShapeById(this.edges[k]);
      this.setColor(ed, ed.ocolor);
      ed.ocolor = null;
    }
	}
});

var RTShowEdge = TRInteraction.extend({
	objs: null,
	vars: {},
	init: function(t){
		this.objs = t;
	},
	initOn: function (s){
		var self = this;
		s.whenDo(TRMouseEnter, function(evt){
			self.doShowEdges(s);
		});
		s.whenDo(TRMouseLeave, function(evt){
			self.removeEdges();
		});
	},
	doShowEdges: function(s){
		this.removeEdges();
		var list = [];
		for(var k = 0;k<this.objs.length; k++){
			var d = s.canvas.addShape(this.objs[k]);
			d.update();
			list[k] = d;
		}

		this.vars.edges = list;
	},
	removeEdges: function(){
		var l = this.vars.edges;
		if(!l) return;
		for(var k =0;k<l.length; k++)l[k].remove();
		this.vars.edges = null;
	}
});

var RTAbstractPopup = TRInteraction.extend({
	popup: null,
	vars: {},
	initOn: function (s){
		var self = this;
		s.whenDo(TRMouseEnter, function(evt){
			self.createAndShowPopupFor(s, evt);
		});
		var remove = function(e){
			self.removeLastPopup();
		};
		s.whenDo(TRMouseLeave, remove);
		s.whenDo(TRMouseDragging, remove);
	},
	createAndShowPopupFor: function(s, evt){
		//override
	},
	removeLastPopup: function(s, evt){
		var popup = this.vars.lastPopup;
		if(popup == null) return;
		popup.remove();
		this.resetLastPopup();
	},
	resetLastPopup: function(){
		this.lastPopup(null);
	},
	lastPopup: function(p){
		this.vars.lastPopup= p;
	},
	closestPositionOf: function(popup, p){
		var ww = $(window).width();
		var wh = $(window).height();
		var	pe = popup.encompassingRectangle().extent();
		var off = this.popupOffset();
		if(pe.x > ww || pe.y > wh)
			return {x: p.x + off.x, y: p.y+ off.y};
		var virtualX = (p.x + pe.x + off.x) < ww? p.x+off.x: p.x-pe.x-off.x;
		var virtualY = (p.y + pe.y + off.y) < wh? p.y+off.y: p.y-pe.y-off.y;
		return {x: virtualX, y: virtualY};
	},
	popupOffset: function(){
		return {x:5, y: 5}
	}
});

var RTPopup = RTAbstractPopup.extend({
	objs: null,
	init: function(t){
		this.objs = t;
	},
	createAndShowPopupFor: function(s, evt){
		this.removeLastPopup();
		this.popup = this.createPopupFor(s);
		this.lastPopup(this.popup);

		var idealPosition = {x:evt.raw.pageX,y:evt.raw.pageY};
		var popupPosition = this.closestPositionOf(this.popup, idealPosition);
		this.popup.translateTopLeftTo(popupPosition);
		s.canvas.updateFixed();
	},
	createPopupFor: function(s){
		var g = new TRGroup();
		for(var k = 0; k<this.objs.length; k++){
			var d = s.canvas.addFixedShape(this.objs[k]);
			d.update();
			g.add(d);
		}
		return g;
	}
});

var RTAttachPoint = Class.extend({
	inverted: null,
	init: function(i){
		this.inverted = i;
	},
	basicEndingPointOf: function(anEdge){
		//override
	},
	basicStartingPointOf: function(anEdge){
		//override
	},
	endingPointOf: function (anEdge){
		return this.inverted? this.basicStartingPointOf(anEdge): this.basicEndingPointOf(anEdge);
	},
	startingPointOf: function (anEdge){
		return this.inverted? this.basicEndingPointOf(anEdge): this.basicStartingPointOf(anEdge);
	}
});

var RTVerticalAttachPoint = RTAttachPoint.extend({
	basicEndingPointOf: function (anEdge){
		return anEdge.to().encompassingRectangle().topCenter();
	},
	basicStartingPointOf: function(anEdge){
		return anEdge.from().encompassingRectangle().bottomCenter();
	}
});

var RTHorizontalAttachPoint = RTAttachPoint.extend({
	basicEndingPointOf: function (anEdge){
		return anEdge.to().encompassingRectangle().leftCenter();
	},
	basicStartingPointOf: function(anEdge){
		return anEdge.from().encompassingRectangle().rightCenter();
	}
});

var RTCenteredAttachPoint = RTAttachPoint.extend({
	basicEndingPointOf: function (anEdge){
		return anEdge.to().position();
	},
	basicStartingPointOf: function(anEdge){
		return anEdge.from().position();
	}
});

var RTAbstractShorterDistanceAttachPoint = RTAttachPoint.extend({
	basicEndingPointOf: function (anEdge){
		return this.attachPointFor(anEdge, anEdge.from(), this.destinationAttachPointsFor(anEdge));
	},
	destinationAttachPointsFor: function(anEdge){
		return this.attachPoints(anEdge, anEdge.to());
	},
	basicStartingPointOf: function(anEdge){
		return this.attachPointFor(anEdge, anEdge.to(), this.startingAttachPointsFor(anEdge));
	},
	startingAttachPointsFor: function(anEdge){
		return this.attachPoints(anEdge, anEdge.from());
	},
	attachPointFor: function(anEdge, aShape, list){
		var p = aShape.encompassingRectangle().corner;
		var dp = [];
		for(var k=0;k<list.length; k++){
			var t = minus(p, list[k]);
			dp[k] = {a: dotProduct(t,t), b: list[k]};
		}
		var res = dp[0];
		for(var k=0; k<dp.length; k++)
			if(dp[k].a<res.a)
				res = dp[k];
		return res.b;
	},
	attachPoints: function(anEdge, aShape){
		//override
	}
});

var RTShorterDistanceWithOffsetAttachPoint = RTAbstractShorterDistanceAttachPoint.extend({
	offset: null,
	edges: null,
	init: function(i){
		this._super(i);
		this.offset = 5;
	},
	attachPoints: function(anEdge, aShape){
		var rec = aShape.encompassingRectangle();
		var c = rec.center();
		return [{x: c.x, y: rec.origin.y}, {x: rec.origin.x,y:c.y}, {x:c.x,y:rec.corner.y},{x:rec.corner.x,y:c.y}];
	},
	basicEndingPointOf: function(anEdge){
		return plus(this._super(anEdge), this.offsetNormalFor(anEdge));
	},
	basicStartingPointOf: function(anEdge){
		return plus(this._super(anEdge), this.offsetNormalFor(anEdge));
	},
	offsetNormalFor: function(anEdge){
		if(!this.edges){
			var eds = [];
			for(var k in anEdge.canvas.shapes){
				var s = anEdge.canvas.shapes[k];
				if((s instanceof TRLineShape) && ( (s.to()==anEdge.to() && s.from()==anEdge.from())
					||(s.to()==anEdge.from() && s.from()==anEdge.to())) ){
					if(s.multi != null) s = s.multi
					if(eds.indexOf(s)==-1) eds[eds.length] = s;
				}
			}
			this.edges = eds;
		}
		if(this.edges.length <=1) return {x:0,y:0};
		var index = this.edges.indexOf(anEdge.multi?anEdge.multi: anEdge);
		return product(normal(minus(anEdge.to().position(), anEdge.from().position())), index * this.offset);
	}
});

var RTShorterDistanceAttachPoint = RTAbstractShorterDistanceAttachPoint.extend({
	attachPoints: function(anEdge, aShape){
		var rec = aShape.encompassingRectangle();
		var c = rec.center();
		return [{x: c.x, y: rec.origin.y}, {x: rec.origin.x,y:c.y}, {x:c.x,y:rec.corner.y},{x:rec.corner.x,y:c.y}];
	},
});

var RTBorderAttachPoint = RTAttachPoint.extend({
	basicEndingPointOf: function(anEdge){
		return this.basicPointFromToOf(anEdge.to(), anEdge.from(), anEdge);
	},
	basicStartingPointOf: function(anEdge){
		return this.basicPointFromToOf(anEdge.from(), anEdge.to(), anEdge);
	},
	basicPointFromToOf: function(fromElement, toElement, anEdge){
		var source = fromElement.geometry();
		var target = toElement.geometry();
		var shift = this.offsetNormalFor(anEdge);
		var line = new RTLineSegment(source.center(), target.center());
		shift = product(eq(line.vector(), {x:0, y:0})?
			{x: -1, y: 0}:
			normal(line.vector()), shift);
		line = new RTLineSegment(plus(source.center(), shift), plus(target.center(), shift));
		return this.borderPointFrom(source, target, line);
	},
	offsetNormalFor: function(anEdge){
		return 0;
	},
	borderPointFrom: function(source, target, line){
		var pts = line.intersect(source);
		if(pts.length == 0) return source.center();
		var min = pts[0];
		for(var k=1;k<pts.length;k++){
			min = min(min, dist(pts[k], line.to()) );
		}
		return min;
	}
});

var RTLineSegment = Class.extend({
	from: null,
	to: null,
	init: function(f, t){
		this.fromTo(f,t);
	},
	fromTo: function(f,t){
		this.from = f;
		this.to = t;
		return this;
	},
	intersect: function(otherShape){
		return otherShape.intersectLineSegment(this);
	},
	intersectHorizontal: function(other){
		var p = this.intersectHorizontalLine(other);
		if(!p) return p;
		if(p.x < this.left().x || p.x > this.right().x||p.y<other.bottom().y||p.y>other.top().y) return null;
		return p;
	},
	bottom: function(){
		return this.from.y < this.to.y? this.from:this.to;
	},
	top: function(){
		return this.from.y > this.to.y? this.from:this.to;
	},
	left: function(){
		return this.from.x < this.to.x? this.from: this.to;
	},
	right: function(){
		return this.from.x > this.to.x? this.from: this.to;
	},
	intersectHorizontalLine: function(other){
		var vector = other.vector();
		if(vector.y == 0) return null;
		var y = this.from.y;
		var x = (this.from.y-other.from.y)*other.cotan()+other.from.x;
		return {x:x, y:y};
	},
	cotan: function(){
		var vector = this.vector();
		if(vector.y == 0) return null;
		return vector.x/vector.y;
	},
	intersectVertical: function(other){
		var pt = this.transposed().intersectHorizontal(other.transposed());
		if(!pt) return null;
		return transposed(pt);
	},
	transposed: function(){
		return new RTLineSegment(transposed(this.from), transposed(this.to));
	},
	vector: function(){
		return minus(this.to, this.from);
	},
	intersectRectangle: function(aRect){
		var bl = aRect.bottomLeft(),
			br = aRect.bottomRight(),
			tl = aRect.topLeft(),
			tr = aRect.topRight(),
			pts = [],
			t= new RTLineSegment();
		this.add(pts, t.fromTo(tl, tr).intersectHorizontal(this));
		this.add(pts, t.fromTo(bl, br).intersectHorizontal(this));
		this.add(pts, t.fromTo(tl, bl).intersectVertical(this));
		this.add(pts, t.fromTo(tr, br).intersectVertical(this));
		return pts;
	},
	add: function(pts, p){
		if(p == null) return;
		var flag = true;
		for(var k = 0; k<pts.length; k++)
			if(eq(pts[k], p)) eq = false;
		if(flag) pts.push(p);
	}
});
var TRAnnouceableObject = Class.extend({
	registry: null,
	whenDo: function (anEventClass, aFunction){
		var r = this.getRegistry();
		r[r.length] = {e: anEventClass, f: aFunction};
	},
	getRegistry: function(){
		if(this.registry == null) this.registry = [];
		return this.registry;
	},
	announce: function (anEvent){
		var list = this.subscriptionsHandling(anEvent);
		for( var i = 0; i < list.length; i++){
			try{list[i].f(anEvent);}
			catch(ex){console.log(ex);}
		}
	},
	subscriptionsHandling: function (anEvent){
		var r = this.getRegistry();
		var res = [];
		for( var k = 0; k<r.length; k++)
			if(anEvent instanceof r[k].e)
				res[res.length] = r[k];

		return res;
	},
	handleEventClass: function (anEventClass ){
		var obj = new anEventClass();
		var list = this.subscriptionsHandling(obj);
		return list.length != 0;
	},
	addInteraction: function(interaction){
		if(!(interaction instanceof TRInteraction) )
			interaction = new interaction();
		interaction.initOn(this);
	},
	addInteractions: function(list){
		for(var k = 0; k< list.length; k++)
			this.addInteraction(list[k]);
	}
});

var TRCanvas = TRAnnouceableObject.extend({
	svg: null,
	canvas: null,
	shapes: null,
	fixedShapes: null,
	extend: null,
	fixedCanvas: null,
	animations: null,
	camera: null,
	color: null,
	definedShapes: null,
	eventBeginingDragging: null,
	shapeBeingPointed: null,
	init: function (){
		this.svg = createSVG("svg");
		document.getElementsByTagName("body")[0].appendChild(this.svg);
		$(this.svg).css("float","left");
		this.canvas = createSVG("g");
		this.fixedCanvas = createSVG("g");
		this.svg.appendChild(this.canvas);
		this.svg.appendChild(this.fixedCanvas);
		this.camera = new TRCamera(this);
		this.definedShapes = {"ellipse": TREllipseShape, "box": TRBoxShape, "line": TRLineShape,
		"label": TRLabelShape, "bitmap": TRBitmapShape, "multi": TRMultiCompositeShape,
		"path": TRSVGPath, "arc": TRArcShape, "polygon": TRPolygonShape, "simplearrow": TRSimpleArrowShape,
		"headarrow": TREmptyHeadArrowShape, "bezier": TRBezierShape, "svgline": TRDualLineWidth};
		var self = this;
		$(this.svg).
			mousedown(function (e){self.mousedown(e)}).
			mouseup(function (e){self.mouseup(e)}).
			mousemove(function (e){self.mousemove(e)}).
			dblclick(function (e){self.mouseDouble(e)}).
			bind("wheel mousewheel",function(e){ self.mousewheel(e); return false;});
		$(this.svg).attr("unselectable","on").bind("selectstart", function(){ return false; });
		this.shapes = [];
		this.fixedShapes = [];
	},
	color: function (c){
		this.color = c;
		var color = c.r == undefined?c:c.a == undefined?"rgb("+c.r+","+c.g+"," +c.b+")":"rgba("+c.r+","+c.g+"," +c.b+","+c.a+")";
		$(this.svg).css("background-color", color);
	},
	createShape: function (s){
		var shape = this.definedShapes[s.type];
		shape = new shape(s);
		shape.setCanvas(this);
		shape.setColor(s.color);
		if(s.ints != null) shape.addInteractions(s.ints);
		if(shape instanceof TRAbstractBoxShape) shape.setBorder(s);
		return shape;
	},
	addShape: function (s){
		var shape = this.createShape(s);
		shape.addInCanvas();
		this.shapes[s.id] = shape;
		return shape;
	},
	focusOnCenterScaled: function(){
		this.camera.focusOnCenterScaled(product( this.camera.extent(), 0.9));
	},
	addFixedShape: function(s){
		var shape = this.createShape(s);
		shape.addInFixedCanvas();
		this.fixedShapes[s.id] = shape;
		return shape;
	},

	addFastShapes: function(objs){
		var arr = [];
		for(var k = 0; k< objs.length; k++)
			arr[k] = this.addShape(objs[k]);
		this.update();
		return arr;
	},
	addShapes: function (objs){
		this.addFastShapes(objs)
		this.camera.focusOnCenter();
	},
	addFixedShapes: function (objs){
		for(var k = 0; k<objs.length; k++)
			this.addFixedShape(objs[k]);
		this.updateFixed();
	},
	update: function(){
		for(var k in this.shapes)
			this.shapes[k].update();
	},
	updateFixed: function(){
		for(var k in this.fixedShapes)
			this.fixedShapes[k].update();
	},
	getShapeById: function (id){
		var res = this.fixedShapes[id];
		if(res) return res;
		return this.shapes[id];
	},
	isDragging: function (){
		return this.eventBeginingDragging != null;
	},
	mouseup: function(e){
		if(this.isDragging())
			this.rtmouseDragEnd(e);
	},
	mousemove: function (e){
		if(this.isDragging()) this.rtmouseDragging(e);
		else{
			if(this.shapeBeingPointed == null){
				this.shapeBeingPointed = this.shapeWith(TRMouseEnter, e);
				this.trmouseEnter(e);
			}
			var currentElement = this.shapeWith(TRMouseLeave, e);
			if(currentElement != this.shapeBeingPointed){
				var ev = new TRMouseLeave();
				this.prepare(this.shapeBeingPointed, ev, e);
				this.shapeBeingPointed = currentElement;
				this.trmouseEnter(e);
			}
			this.rtmouseMoving(e);
		}
	},
	mouseDouble: function(e){
		var shape = this.shapeWith(TRMouseDoubleClick, e);
		this.prepare(shape, new TRMouseDoubleClick, e);
	},
	trmouseEnter: function(e){
		var shape = this.shapeWith(TRMouseEnter, e);
		var ev = new TRMouseEnter();
		this.prepare(shape, ev, e);
	},
	mousedown: function (e){
		this.rtmouseClick(e);
		this.rtmouseDragBegin(e);
	},
	shapeWith: function (trevent, e){
		var s = document.elementFromPoint(e.pageX, e.pageY).id;
		s = this.getShapeById(s);
		if(s != null && s.handleEventClass(trevent)) return s;
		return this;
	},
	rtmouseClick: function(e){
		var ev = e.button == 0?new TRMouseLeftClick(): e.button ==2?new TRMouseRightClick(): new TRMouseClick();
		var shape = this.shapeWith(TRMouseClick, e);
		this.prepare(shape, ev, e);
	},
	prepare: function(shape, ev, e){
		ev.shape = shape;
		ev.canvas = this;
		ev.position = this.camera.fromPixelToSpace(e);
		ev.raw = e;
		shape.announce(ev);
	},
	rtmouseDragBegin: function (e){
		this.eventBeginingDragging = e;
		var shape = this.shapeBeingPointed = this.shapeWith(TRMouseDragging, e);

		var ee = new TRMouseDragStart();
		ee.step = {x: 0, y: 0};
		this.prepare(shape, ee, e);
	},
	rtmouseMoving: function (e){
		var shape = this.shapeWith(TRMouseMove, e);
		var ev = new TRMouseMove();
		this.prepare(shape, ev, e);
	},
	rtmouseDragging: function(e){
		var ee = this.eventBeginingDragging;
		if(ee== null) return;
		var step = {x: e.pageX-ee.pageX, y: e.pageY-ee.pageY};
		var shape = this.shapeBeingPointed;
		if(shape == null)
			shape = this.shapeBeingPointed = this.shapeWith(TRMouseDragging, e);
		var event = new TRMouseDragging();
		event.step = step;
		this.prepare(shape, event, e);
		this.eventBeginingDragging = e;
	},
	rtmouseDragEnd: function(e){
		var ee = new TRMouseDragEnd();
		var ee = this.eventBeginingDragging;
		var step = {x: e.pageX-ee.pageX, y: e.pageY-ee.pageY};
		ee.step = step;
		this.prepare(this.shapeBeingPointed, ee, e);
		this.eventBeginingDragging = null;
		this.shapeBeingPointed = null;
	},
	mousewheel: function(evt){
		var e = new TRMouseWheelEvent();
		evt.pageX = evt.originalEvent.pageX;
		evt.pageY = evt.originalEvent.pageY;
		e.position = this.camera.fromPixelToSpace(evt);
		e.raw = evt;
		if(evt.originalEvent.deltaY) evt.originalEvent.wheelDelta = -evt.originalEvent.deltaY;//moz fix
		e.direction = evt.originalEvent.wheelDelta /120 > 0?"up":"down";
		this.announce(e);
	}
});

var TRGroup = TRAnnouceableObject.extend({
	items : null,
	add: function (s){
		var i = this.getItems();
		i[i.length] = s;
	},
	getItems: function (){
		if(this.items == null) this.items = [];
		return this.items;
	},
	each: function(f){
		var i = this.getItems();
		for(var k =0; k<i.length; k++)
			f(i[k]);
	},
	addAll: function (objs){
		for(var k = 0; k< objs.length; k++)
			this.add(objs[k]);
	},
	includes: function (shape){
		var i = this.getItems();;
		for(var k = 0; k< i.length; k++)
			if(i[k] == shape) return true;
		return false;
	},
	encompassingRectangle: function(){
		var r = null;
		this.each(function (e){
			var er = e.encompassingRectangle();
			if (r == null) r = er;
			else r.mergeRect(er);
		});
		if(r == null) r = new Rectangle({x:0, y:0}, {x:0,y:0});
		return r;
	},
	translateBy: function(p){
		var i = this.getItems();
		for(var k = 0; k< i.length; k++){
			i[k].translateBy(p);
		}
	},
	translateTopLeftTo: function(p){
		var c = this.encompassingRectangle().origin;
		this.translateBy({x:p.x-c.x, y:p.y-c.y});
	},
	remove: function(){
		this.each(function(e){e.remove()});
	}
});

var TRCamera = Class.extend({
	position: null,
	canvas: null,
	scale: null,
	init: function (c) {
		this.canvas = c;
		this.scale = 1.000001;
		this.position = { x : 0 , y : 0};
	},
	setScale: function(s){
		this.scale = s;
		this.update();
	},
	extent: function(){
		return {x: $(window).width(), y: $(window).height()};
	},
	update: function(){
		var ww = $(window).width();
		var wh = $(window).height();
		$(this.canvas.svg).css("width", ww).css("height", wh);
		$(this.canvas.canvas).attr("transform", "translate(" +(ww/2)+", " +(wh/2)+") scale("+this.scale+") translate(" +this.position.x+","+ this.position.y+")");;
	},
	translateTo: function (point){
		this.position = point;
		this.update();
	},
	focusOnCenter: function(){
		if(this.canvas.shapes.length == 0) return;
		var c = this.encompassingRectangle().center();
		this.translateTo({x: -c.x, y: -c.y });
	},
	focusOnCenterScaled: function(factor){
		var r = this.encompassingRectangle();
		this.position = negated(r.center());
		var sc = div(factor, r.extent());
		sc = sc.x < sc.y ? sc.x:sc.y;
		this.setScale(sc);
	},
	encompassingRectangle: function (){
		return this.encompassingRectangleOf(this.canvas.shapes);
	},
	encompassingRectangleOf: function(shapes){
		var rect = null;

		for(var i in shapes){
			if( rect == null){
				rect = shapes[i].encompassingRectangle();
				continue;
			}
			rect.mergeRect(shapes[i].encompassingRectangle());
		}
		return rect;
	},
	translateBy: function(p){
		this.position.x += p.x;
		this.position.y += p.y;
		this.update();
	},
	fromPixelToSpace: function (e){
		var p = e.pageX?{x: e.pageX, y: e.pageY}:e;
		var ww = $(window).width();
		var wh = $(window).height();
		var p2 = this.position;
		p = (new  MatrixTransform()).translateBy({x: -p2.x, y: -p2.y}).
			scaleBy(1/this.scale).translateBy({x: -ww/2, y: -wh/2}).transform(p);
		return p;
	}
});

var TRShape = TRAnnouceableObject.extend({
	canvas: null,
	color: null,
	path: null,
	id: null,
	rotationAngleDegrees: null,
	matrix: null,
	strokeColor: null,
	encompassingRectangle: function (){
		//override
	},
	resetPath: function(){
		//override
	},
	setCanvas: function(canvas){
		this.canvas = canvas;
	},
	addInFixedCanvas: function(){
		if(this.path != null) this.canvas.fixedCanvas.appendChild(this.path);
	},
	addInCanvas: function(){
		if(this.path != null) this.canvas.canvas.appendChild(this.path);
	},
	setColor: function(c){
		this.setFillColor(c);
	},
  getColor: function(){
    return this.color;
  },
  setFillColor: function(c){
    if(c == null) return;
		this.color = c;
		if(c.r == undefined) $(this.path).css("fill", c);
		else $(this.path).css("fill", "rgb("+c.r+","+c.g+"," +c.b+")");
		if(c.a == null)	c.a = 1;
		$(this.path).css("fill-opacity", c.a);
  },
	setStrokeColor: function(c){
		this.strokeColor = c;
		if(c.r == null) $(this.path).css("stroke", c);
		else $(this.path).css("stroke", "rgb("+c.r+","+c.g+"," +c.b+")");
		if(c.a != null)	$(this.path).css("stroke-opacity", c.a);
	},
	setBorder: function(obj){
		if(obj.bordercolor != null){
			this.setStrokeColor(obj.bordercolor);
			this.setStrokeWidth(obj.sw);
		}
	},
	setStrokeWidth: function(w){
		this.strokeWidth = w;
		$(this.path).css("stroke-width", w);
	},
	translateBy: function(p){
		var u = this.position();
		this.setPosition({x: p.x + u.x, y: p.y+ u.y});
	},
	setPosition: function(p){
		this.matrix.x = p.x;
		this.matrix.y = p.y;
	},
	position: function (){
		var m = this.matrix;
		if(this.matrix == null) debugger;
		return {x: m.x, y: m.y}
	},
	update: function(){
		$(this.path).attr("transform", this.matrix.asString());
	},
	remove: function(){
		$(this.path).remove();
		delete this.canvas.shapes[this.id];
		delete this.canvas.fixedShapes[this.id];
	},
	pushFront: function(){
		if(this.path == null) return;
		this.path.parentNode.appendChild(this.path);
	},
	geometry: function(){
		return this.encompassingRectangle();
	}
});

var TRAbstractBoxShape = TRShape.extend({
	rectangle: null,
	strokeWith: null,
	width: function (newWidth){
		this.extent({x: newWidth, y: this.rectangle.height});
	},
	height: function (newHeight){
		this.extent({x: this.rectangle.width, y: newHeight});
	},
	extent: function(newExtent){
		this.rectangle = new Rectangle ( {x: -newExtent.x/2,y: -newExtent.y/2}, newExtent);
		this.resetPath();
	},
	encompassingRectangle: function(){
		return this.matrix.transformRect(this.rectangle);
	}
});

var TREllipseShape = TRAbstractBoxShape.extend({
	init: function (obj){
		this.id = obj.id;
		this.color = obj.color;
		this.path = createSVG("ellipse");
		$(this.path).attr({cx: 0, cy: 0, id: obj.id});
		this.rectangle = new Rectangle({x: -obj.rx, y: -obj.ry}, {x: obj.rx, y: obj.ry});
		this.matrix = new MatrixTransform(obj.matrix);
		this.resetPath();
		this.update();
	},
	resetPath: function (){
		var r = this.rectangle;
		$(this.path).attr({rx: r.width()/2, ry: r.height()/2});
	},
	geometry: function(){
		return this.encompassingRectangle();
	}
});

var TRBoxShape = TRAbstractBoxShape.extend({
	init: function (obj){
		this.id = obj.id;
		this.color = obj.color;
		this.path = createSVG("rect");
		var o = {x: -obj.w/2, y: -obj.h/2, id: obj.id};
		$(this.path).attr(o);
		this.rectangle = new Rectangle(o, {x: obj.w/2, y: obj.h/2});
		this.matrix = new MatrixTransform(obj.matrix);
		this.resetPath();
		this.update();
	},
	resetPath: function (){
		var r = this.rectangle;
		$(this.path).attr({width: r.width(),"height": r.height() } );
	}
});

var TRBitmapShape = TRShape.extend({
	init: function(obj){
		this.id = obj.id;
		this.path = createSVG("image");
		var w = obj.w;
		var h = obj.h;
		this.path.setAttributeNS("http://www.w3.org/1999/xlink","href","data:image/png;base64,"+obj.base64);
		this.matrix = new MatrixTransform(obj.matrix);
		var o = {x: -w/2, y: -h/2, width: w, height: h, id: obj.id};
		this.rectangle = new Rectangle(o, {x: w/2, y: h/2});
		$(this.path).attr(o);
	},
	encompassingRectangle: function(){
		return this.matrix.transformRect(this.rectangle);
	}
});

var TRMultiCompositeShape = TRShape.extend({
	objs: null,
	offsets: null,
	shapes: null,
	ap: null,
	init: function(obj){
		this.id = obj.id;
		this.objs = obj.shapes;
		this.offsets = obj.offsets;
		this.ap = obj.ap;
	},
  setColor: function(c){
    if(this.shapes) this.shapes[0].setColor(c);
  },
  getColor: function(){
    return this.shapes[0].getColor();
  },
	encompassingRectangle: function(){
		var s = this.shapes;
		var r = s[0].encompassingRectangle();
		for(var k = 1;k<s.length; k++) r.mergeRect(s[k].encompassingRectangle());
		return r;
	},
	addInFixedCanvas: function(){
		this.addIn(this.canvas.fixedCanvas, this.canvas.fixedShapes, true);
		for(var k = 0;k<this.shapes.length; k++) this.shapes[k].addInFixedCanvas();
		this.postAdd();
	},
	postAdd: function(){
		this.update();
		var c = this.encompassingRectangle().center();
		c = {x: -c.x, y: -c.y};
		for(var k = 0;k<this.shapes.length; k++) this.shapes[k].translateBy(c);
	},
	addInCanvas: function(){
		this.addIn(this.canvas.canvas, this.canvas.shapes, false);
		for(var k = 0;k<this.shapes.length; k++) this.shapes[k].addInCanvas();
	},
	addIn: function(g, list, b){
		var o = this.objs;
		this.shapes = [];
		for(var k = 0; k<o.length; k++){
			var s = this.canvas.createShape(o[k]);
			this.shapes[k] = s;
			s.multi = this;
			try{
				if(b)s.setPosition(this.offsets[k]);
			}catch(x){debugger;}
			list[s.id] = s;
		}
		if(o.length == 2)
			if(this.shapes[0] instanceof TRBezierShape){
				var l = this.shapes[0].ides;
				this.shapes[1].fromid = l[l.length - 2];
			}
	},
	update: function(){
		var s = this.shapes;
		this.updateForAp(s);
		for(var k = 0; k<s.length; k++)
			s[k].update();
	},
	updateForAp: function(s){
		if(this.ap != null){
			if(this.from == null)
				for(var k = 0; k<s.length; k++)
					if(s[k] instanceof TRLineShape){
						var f = s[k].from();
						this.from = function (){return f;};
						var t = s[k].to();
						this.to = function(){return t;};
						s[k].ignoreap=true;
					}
			var ap = this.ap, p1, p2, l;
			var from = ap.startingPointOf(this);
			var to = ap.endingPointOf(this);
			p1 = from;
			l = s.length;
			for(var k = 0; k<l; k++){
				p2 = plus(from,product(div(minus(to, from), l), k+1));
				$(s[k].path).attr({x1: p1.x, y1: p1.y, x2: p2.x, y2: p2.y});
				p1 = p2;
			}
		}
	},
	translateBy: function(p){
		for(var k = 0; k<this.shapes.length; k++)
			this.shapes[k].translateBy(p);
	},
	remove: function(){
		delete this.canvas.shapes[this.id];
		delete this.canvas.fixedShapes[this.id];
		for(var k = 0; k<this.shapes.length; k++)
			this.shapes[k].remove();
	},
	position: function(){
		if(this.shapes.length==0) return {x:0,y:0};
		var r = null;
		for(var k = 0; k<this.shapes.length; k++){
			var er = this.shapes[k].encompassingRectangle();
			if (r == null) r = er;
			else r.mergeRect(er);
		}
		return r.center();
	}
});

var TRLineShape = TRAbstractBoxShape.extend({
	width: null,
	fromid: null,
	toid: null,
	attachPoint: null,
	ignoreap: false,
	init: function (obj){
		this.id = obj.id;
		this.fromid = obj.fromid;
		this.toid = obj.toid;
		this.path = createSVG("line");
		$(this.path).attr("id", obj.id);
		this.setWidth(obj.width);
		this.setAttachPoint(obj.ap);
	},
	setAttachPoint: function (ap){
		if(!(ap instanceof RTAttachPoint)) ap = new ap();
		this.attachPoint = ap;
	},
	setColor: function(c){
		this.setStrokeColor(c);
	},
	setWidth: function(w){
		this.width = w;
		$(this.path).css("stroke-width", this.width);
	},
	update: function (){
		if(this.ignoreap) return;
		var from = this.from();
		var to = this.to();
		if(from == null || to == null ) return;
		if(to instanceof TRShape){
			from = this.attachPoint.startingPointOf(this);
			to = this.attachPoint.endingPointOf(this);
		}
		$(this.path).attr({x1: from.x, y1: from.y, x2: to.x, y2: to.y});
	},
	find: function(id){
		return typeof id == "string"? this.canvas.getShapeById(id): id;
	},
	from: function (){
		return this.find(this.fromid);
	},
	to: function(){
		return this.find(this.toid);
	},
	encompassingRectangle: function(){
		var r = new Rectangle();
		var from, to;
		try{ from = this.from().position();
		}catch(ex){from = {x:0,y:0}}
		try{ to = this.to().position();
		}catch(ex){to = {x:0, y: 0}}
		return r.encompassing([{x: from.x, y: from.y}, {x: to.x, y: to.y}]);
	},
	translateBy: function(p){},
	setPosition: function(p){}
});

var TRBezierShape = TRLineShape.extend({
	ides: null,
	tension: null,
	init: function (obj){
		this.id = obj.id;
		this.fromid = obj.fromid;
		this.toid = obj.toid;
		this.path = createSVG("path");
		this.ides = obj.ides;
		this.tension = obj.tension;
		$(this.path).attr("id", obj.id);
		$(this.path).css("fill","none");
		this.setWidth(obj.width);
		this.setAttachPoint(obj.ap);
	},
	update: function (){
		if(this.ignoreap) return;
		var from = this.from();
		var to = this.to();
		if(from == null || to == null ) return;
		var list = [];
		list[0] = this.attachPoint.startingPointOf(this);
		for(var k = 0; k < this.ides.length; k++) list[list.length] = this.getP(this.ides[k]);
		list[list.length] = this.attachPoint.endingPointOf(this);
		this.applyTension(list);
		this.buildPath(list);
	},
	buildPath: function(points){
		var p1 = points[0],p2,
		d = "M"+ p1.x+" "+p1.y;
		for(var k = 1; k< points.length-2; k++){
			p1 = points[k];
			p2 = points[k+1];
			d += "Q"+p1.x+" "+p1.y+" "+((p1.x+p2.x)/2)+" "+((p1.y+p2.y)/2);
		}
		p1 = points[points.length-2];
		p2 = points[points.length-1];
		d += "Q"+p1.x+" "+p1.y+" "+p2.x+" "+p2.y;
		$(this.path).attr("d", d);
	},
	applyTension: function(points){
		var tension = this.tension,
		n = points.length-1,
		p1 = points[0],
		dp = minus(points[n],p1),
		p,t;
		for(var k = 1; k< n; k++){
			p = points[k];
			t = k/n;
			points[k] = plus(product(p,tension), product( plus(p1, product(dp, t)),1-tension));
		}
	},
	getP: function(id){
		return this.canvas.getShapeById(id).position();
	}
});
var TRDualLineWidth = TRLineShape.extend({
	init: function (obj){
    this._super(obj);
    this.path = createSVG("path");
    this.widthFrom = obj.widthFrom;
    this.widthTo = obj.widthTo;
  },
  setColor: function(c){
    this.setFillColor(c);
  },
  update: function (){
    var from = this.from();
    var to = this.to();
    if(from == null || to == null) return;
    if(to instanceof TRShape){
			from = this.attachPoint.startingPointOf(this);
			to = this.attachPoint.endingPointOf(this);
		}
    var d = "", e = 0.001, w1 = this.widthTo, w2 = this.widthFrom, t, first;
    w1 = w1.max(e);
    w2 = w2.max(e);
    var dist = distanceTo(from, to).max(e);
    z = (w1 + w2)/2.
    if(z > dist){
      t = dist / (z + z* 0.1);
      w1 = w1 * t;
      w2 = w2 * t;
    }
    function point(p){
      d = d+ p.x+ " "+p.y+ " ";
    }
    function side(vector, end, size){
      var u, left, right, unit, middle, l, ri, us;
      u = normal(vector);
      unit = div(vector, r(vector).max(0.001));
      us = product(u,size);
      middle = minus(end, product(unit, size ) );
      left = minus(middle, us);
      l = minus(end, us);
      right = plus(middle, us);
      ri = plus(end, us);
      d = d + (first?"M":"L");
      point(left);
      d = d+"C";
      point(left);
      point(l);
      point(end);
      d = d+"C";
      point(end);
      point(ri);
      point(right);
    }
    first = 1;
    side(minus(to, from), to, w1/2 );
    first = 0;
    side(minus(from, to),from, w2/2);
    $(this.path).attr("d", d+"Z");
  },
});
var TRSimpleArrowShape = TRLineShape.extend({
	size: null,
	baseSize: null,
  percentage: null,
	init: function(obj){
		this.id = obj.id;
		this.fromid = obj.fromid;
		this.toid = obj.toid;
		this.path = createSVG("path");
		$(this.path).attr("id", obj.id);
		$(this.path).css("fill", "none");
		this.setWidth(obj.width);
		this.setAttachPoint(obj.ap);
		this.size = obj.size;
		this.baseSize = obj.base;
		this.percentage = obj.percentage;
	},
	update: function(){
		if(this.fromid == this.toid) return;
		var from = this.from();
		var to = this.to();
		if(from == null || to == null ) return;
		var base = this.baseSize;
		from = this.attachPoint.startingPointOf(this);
		to = this.attachPoint.endingPointOf(this);
		var vector = minus(to, from);
		var u = normal(vector);
		var unit = div(vector, r(vector));
		var endPoint = minus(to, product(vector, (1 - this.percentage)));
		var middle = minus(endPoint, product(unit, this.size));
		var left = minus(middle, product(u, base/2));
		var right = plus(middle, product(u, base/2));
		var d = "M "+left.x+" "+left.y+" L "+endPoint.x+" "+endPoint.y+" L "+right.x+ " "+right.y;
		$(this.path).attr("d", d);
	}
});

var TREmptyHeadArrowShape = TRLineShape.extend({
	size: null,
	baseSize: null,
	init: function(obj){
		this.id = obj.id;
		this.fromid = obj.fromid;
		this.toid = obj.toid;
		this.path = createSVG("path");
		$(this.path).attr("id", obj.id);
		this.setWidth(obj.width);
		this.setAttachPoint(obj.ap);
		this.size = obj.size;
		this.baseSize = obj.base;
		this.setBorder(obj);
	},
	update: function(){
		var from = this.from();
		var to = this.to();
		if(from == null || to == null ) return;
		var base = this.baseSize;
		from = this.attachPoint.startingPointOf(this);
		to = this.attachPoint.endingPointOf(this);
		var vector = minus(to, from);
		var u = normal(vector);
		var unit = div(vector, r(vector));
		var middle = minus(to, product(unit, this.size));
		var left = minus(middle, product(u, base));
		var right = plus(middle, product(u, base));
		var d = "M "+left.x+" "+left.y+" L "+to.x+" "+to.y+" L "+right.x+ " "+right.y+" L "+left.x+" "+left.y+" Z";
		$(this.path).attr("d", d);
	},
	setColor: function(c){
		this.setFillColor(c);
	},
	setStrokeColor: function(c){
		this.strokeColor = c;
		if(c.r == null) $(this.path).css("stroke", c);
		else $(this.path).css("stroke", "rgb("+c.r+","+c.g+"," +c.b+")");
		if(c.a != null)	$(this.path).css("stroke-opacity", c.a);
	},
});

var TRSVGPath = TRShape.extend({
	d: null,
	rect: null,
	init: function(obj){
		this.id = obj.id;
		this.path = createSVG("path");
		$(this.path).attr({id: obj.id, d: obj.d});
		this.matrix = new MatrixTransform(obj.matrix);
		this.d = obj.d;
		this.rect = new Rectangle(obj.o,obj.c);
		this.setBorder(obj);
	},
	encompassingRectangle: function(){
		return this.matrix.transformRect(this.rect);
	}
});

var TRPolygonShape = TRShape.extend({
	points: null,
	rect: null,
	init: function(obj){
		this.id = obj.id;
		this.path = createSVG("polygon");
		$(this.path).attr({id: obj.id, points: obj.p});
		this.matrix = new MatrixTransform(obj.matrix);
		this.rect = new Rectangle(obj.o,obj.c);
		this.setBorder(obj);
	},
	encompassingRectangle: function(){
		return this.matrix.transformRect(this.rect);
	}
});

var TRArcShape = TRShape.extend({
	i: null,
	e: null,
	a: null,
	b: null,
	rect: null,
	init: function(obj){
		this.id = obj.id;
		this.path = createSVG("path");
		$(this.path).attr("id",obj.id);
		this.matrix=new MatrixTransform(obj.matrix);
		this.i=obj.ir;
		this.e=obj.er;
		this.a= obj.aAngle;
		this.b=obj.bAngle;
		this.rect = new Rectangle(obj.o, obj.c);
		this.createD();
	},
	createD: function(){
		var aAngle = -this.a.degreesToRadians();
		var bAngle = -this.b.degreesToRadians();
		var i = this.i;
		var e = this.e;
		var xAngle = (aAngle+bAngle)/2;
		var xCos = Math.cos(xAngle);
		var xSin = Math.sin(xAngle);
		var aCos = Math.cos(aAngle);
		var aSin = Math.sin(aAngle);
		var bCos = Math.cos(bAngle);
		var bSin = Math.sin(bAngle);
		var str= "M "+(i*aCos)+" "+(i*aSin)+
		" L "+(e*aCos)+" "+(e*aSin)+
		" A "+e+" "+e+" 0 0 0 "+(e*xCos)+" "+(e*xSin)+
		" A "+e+" "+e+" 0 0 0 "+(e*bCos)+" "+(e*bSin)+
		" L "+(i*bCos)+" "+(i*bSin)+
		" A "+i+" "+i+" 0 0 1 "+(i*xCos)+" "+(i*xSin)+
		" A "+i+" "+i+" 0 0 1 "+(i*aCos)+" "+(i*aSin)+ " Z";
		$(this.path).attr("d",str);

	},
	encompassingRectangle: function (){
		return this.matrix.transformRect(this.rect);
	}
});

var TRLabelShape = TRShape.extend({
	text: null,
	font: null,
	rect: null,
	calc: false,
	vars: {},
	init: function(obj){
		this.id = obj.id;
		this.path = createSVG("text");
		$(this.path).attr({id: obj.id});
		this.matrix = new MatrixTransform(obj.matrix);
		this.setText(obj.text);
		this.setFont(obj.font);
		this.rect=new Rectangle(obj.o, obj.c);
	},
	setFont: function(f){
		if(f.n != "Verdana, Geneva, sans-serif") this.loadFont(f.n);
		this.font = f;
		$(this.path).css({"font-family":f.n, "font-size":f.s});
	},
	loadFont: function(f){
		var fonts = this.vars.fonts
		if(fonts == null) fonts =this.vars.fonts = [];
		if(fonts[f] == null){
			 fonts[f] = 1;
			 $("head").append("<link href=\"https://fonts.googleapis.com/css?family=" +
			 (f.replace(/ /g, "+"))+ "\" rel=\"stylesheet\" type=\"text/css\">");
		}
	},
	setText: function(t){
		this.text = t;
		$(this.path).append(t);
	},
	update: function (){
		$(this.path).attr("transform", this.matrix.asString());
		if(this.calc) return;
		this.calc = true;
		var r = this.path.getBBox();
		var w = r.width;
		var h = r.height;
		$(this.path).attr({x: this.rect.origin.x, y: this.rect.origin.y+h});
		this.path.setAttribute("textLength",this.rect.width());
		this.path.setAttribute("lengthAdjust", "spacingAndGlyphs");
	},
	encompassingRectangle: function(){
		return this.matrix.transformRect(this.rect);
	}
});

/*main events*/
var mamani;

$(document).ready( function(){
	mamani = new TRCanvas();
	$(window).resize(function(){
		$("body").height($(window).height());
		mamani.camera.update();
	});
	$(window).trigger("resize");
	buildView();
});
