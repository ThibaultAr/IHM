package e214.skeleton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CSegment;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.DragOnTag;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.canvas.transitions.ReleaseOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class MagneticGuides extends JFrame {

	private Canvas canvas;
	private CExtensionalTag oTag;
	private CStateMachine stateMachine;

	public MagneticGuides(String title, int width, int height) {
		super(title);
		canvas = new Canvas(width, height);
		canvas.setAntialiased(true);
		getContentPane().add(canvas);

		oTag = new CExtensionalTag(canvas) {
		};

		stateMachine = new CStateMachine() {

			private Point2D p;
			private CShape draggedShape;
			private Map<CSegment, MagneticGuide> magnetics = new HashMap<CSegment, MagneticGuide>();

			public State start = new State() {
				Transition pressCanvas1 = new Press(BUTTON1, ">> start") {
					public void action() {
						CSegment seg = canvas.newSegment(-canvas.getWidth() * 100, getPoint().getY(),
								canvas.getWidth() * 100, getPoint().getY());
						MagneticGuide magnetic = new MagneticGuide(canvas, seg);
						magnetics.put(seg, magnetic);
						magnetic.belowAll();
					}
				};

				Transition pressCanvas2 = new Press(BUTTON3, ">> start") {
					public void action() {
						CSegment seg = canvas.newSegment(getPoint().getX(), -canvas.getHeight() * 100,
								getPoint().getX(), canvas.getHeight() * 100);
						MagneticGuide magnetic = new MagneticGuide(canvas, seg);
						magnetics.put(seg, magnetic);
						magnetic.belowAll();
					}
				};

				Transition pressOnObject = new PressOnTag(oTag, BUTTON1, ">> oDrag") {
					public void action() {
						p = getPoint();
						draggedShape = getShape();
					}
				};

				Transition pressOnLine = new PressOnTag(MagneticGuide.class, BUTTON1, ">> mDrag") {
					public void action() {
						p = getPoint();
						draggedShape = getShape();
					}
				};
			};

			public State oDrag = new State() {
				Transition drag = new Drag(BUTTON1) {
					public void action() {
						Point2D q = getPoint();
						draggedShape.translateBy(q.getX() - p.getX(), q.getY() - p.getY());
						p = q;
						for(CShape shape : canvas.pickAll(p)) {
							if(shape instanceof CSegment) {
								shape.setStroke(new BasicStroke(4));
								if(!draggedShape.hasTag(magnetics.get(shape)))
									draggedShape.addTag(magnetics.get(shape));
							} 
						}
					}
				};
//				Transition grass = new EnterOnTag(MagneticGuide.class) {
//					public void action() {
//						if(getShape() instanceof CSegment) {
//							getShape().setStroke(new BasicStroke(4));
//							draggedShape.addTag(getTag());
//						}
//					}
//				};
				
				Transition unGrass = new LeaveOnTag(MagneticGuide.class) {
					public void action() {
						if(getShape() instanceof CSegment) {
							getShape().setStroke(new BasicStroke(2));
							draggedShape.removeTag((CExtensionalTag) getTag());
						}
					}
				};

				Transition release = new Release(BUTTON1, ">> start") {
				};
				
				Transition releaseOnTag = new ReleaseOnTag(MagneticGuide.class, BUTTON1, ">> start") {
					public void action() {						
						for(CShape shape : canvas.pickAll(p))
							if(shape instanceof CSegment)
								shape.setStroke(new BasicStroke(2));
					}
				};
				
			};

			public State mDrag = new State() {
				Transition drag = new Drag(BUTTON1) {
					public void action() {
						Point2D q = getPoint();
						draggedShape.translateBy(q.getX() - p.getX(), q.getY() - p.getY());
						for(CShape shape : canvas.getFilledShapes()) {
							MagneticGuide guide = magnetics.get(draggedShape);
							if(shape.getTags().contains(guide) && shape != draggedShape) {
								if(guide.isHorizontal()) shape.translateBy(0, q.getY() - p.getY());
								else shape.translateBy(q.getX() - p.getX(), 0);
							}
						}
						p = q;
					}
				};
				Transition release = new Release(BUTTON1, ">> start") {
				};
			};

		};
		stateMachine.attachTo(canvas);

		pack();
		setVisible(true);
		canvas.requestFocusInWindow();
	}

	public void populate() {
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		double s = (Math.random() / 2.0 + 0.5) * 30.0;
		double x = s + Math.random() * (width - 2 * s);
		double y = s + Math.random() * (height - 2 * s);

		int red = (int) ((0.8 + Math.random() * 0.2) * 255);
		int green = (int) ((0.8 + Math.random() * 0.2) * 255);
		int blue = (int) ((0.8 + Math.random() * 0.2) * 255);

		CRectangle r = canvas.newRectangle(x, y, s, s);
		r.setFillPaint(new Color(red, green, blue));
		r.addTag(oTag);
	}

	public static void main(String[] args) {
		MagneticGuides guides = new MagneticGuides("Magnetic guides", 600, 600);
		for (int i = 0; i < 20; ++i)
			guides.populate();
		guides.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(guides.stateMachine));
		viz.pack();
		viz.setVisible(true);
	}

}
