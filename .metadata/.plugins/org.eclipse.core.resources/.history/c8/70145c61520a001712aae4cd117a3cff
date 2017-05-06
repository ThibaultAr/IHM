package e214.skeleton;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CElement;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CSegment;
import fr.lri.swingstates.canvas.Canvas;

public class MagneticGuide extends CExtensionalTag {
	private CSegment seg;
	private boolean horizontal;
	
	public MagneticGuide(Canvas canvas, CSegment seg) {
		super(canvas);
		this.canvas = canvas;
		this.seg = seg;
		seg.setStroke(new BasicStroke(2));
		seg.addTag(this);
		this.horizontal = (seg.getMinY() == seg.getMaxY());
	}
	
	public boolean isHorizontal() {
		return this.horizontal;
	}
}
