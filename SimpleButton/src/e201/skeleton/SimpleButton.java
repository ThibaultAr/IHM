package e201.skeleton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JFrame;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.canvas.transitions.ReleaseOnShape;
import fr.lri.swingstates.canvas.transitions.ReleaseOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Release;
import fr.lri.swingstates.sm.transitions.TimeOut;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class SimpleButton {

	private CText label;
	private CRectangle rect;
	protected CStateMachine stateMachine;
	protected int buttonNumber;

	SimpleButton(Canvas canvas, String text, int buttonNumber) {
		this.buttonNumber = buttonNumber;
		label = canvas.newText(0, 0, text, new Font("verdana", Font.PLAIN, 12));
		rect = canvas.newRectangle(label.getMinX() - 10, label.getMinY() - 10, text.length() * 12 / 2 + 6 + 20,
				12 + 20);
		rect.setFillPaint(Color.white);
		rect.setParent(label);
		label.above(rect);

		rect.addTag("rectangle");

		this.stateMachine = new CStateMachine() {
			Paint initColor;
			Stroke initStroke;
			State idle = new State() {
				public void enter() {
					SimpleButton.this.rect.setFillPaint(Color.WHITE);
				}

				Transition io = new EnterOnShape(">> over");
			};

			State over = new State() {
				public void enter() {
					initStroke = SimpleButton.this.rect.getStroke();
					SimpleButton.this.rect.setStroke(new BasicStroke(3));
				}
				
				Transition oi = new LeaveOnShape(">> idle") {
					public void action() {
						SimpleButton.this.rect.setStroke(initStroke);
					}
				};

				Transition op = new PressOnShape(SimpleButton.this.buttonNumber, ">> pressed") {
					public void action() {
						armTimer(1000, false);
					}
				};
			};

			State pressed = new State() {
				public void enter() {
					initColor = SimpleButton.this.rect.getFillPaint();
					SimpleButton.this.rect.setFillPaint(Color.YELLOW);
					SimpleButton.this.rect.setStroke(initStroke);
				}

				Transition to = new TimeOut(">> pressed") {
					public void action() {
						SimpleButton.this.actionDemiClick();
					}
				};
				Transition po = new ReleaseOnShape(SimpleButton.this.buttonNumber, ">> click") {
					public void action() {
						SimpleButton.this.rect.setFillPaint(initColor);
					}
				};

				Transition pd = new LeaveOnShape(">> deactivated");
			};

			State deactivated = new State() {
				public void enter() {
					SimpleButton.this.rect.setFillPaint(initColor);
				}
				
				Transition dp = new EnterOnShape(">> pressed");

				Transition di = new Release(SimpleButton.this.buttonNumber, ">> idle") {
				};
			};
			
			State click = new State() {
				public void enter() {
					SimpleButton.this.action();
					armTimer(500,false);
				}
				
				Transition to = new TimeOut(">> over") {
					public void action() {
						disarmTimer();
					}
				};
				
				Transition cdouble = new PressOnShape(SimpleButton.this.buttonNumber, ">> suiteClick");
			};
			
			State suiteClick = new State() {
				public void enter() {
					armTimer(1000, false);
				}
				
				Transition to = new TimeOut(">> pressed") {
					public void action() {
						disarmTimer();
						SimpleButton.this.actionClickDemi();
					}
				};
				
				Transition rel = new ReleaseOnShape(SimpleButton.this.buttonNumber, ">> over") {
					public void action() {
						SimpleButton.this.rect.setFillPaint(initColor);
						SimpleButton.this.actionDoubleClick();
					}
				};
			};
		};
		this.stateMachine.attachTo(canvas);
	}

	public void action() {
		System.out.println("ACTION Click!");
	}
	
	public void actionDemiClick() {
		System.out.println("ACTION Demi-Click!");
	}
	
	public void actionClickDemi() {
		System.out.println("ACTION Click-Demi!");
	}
	
	public void actionDoubleClick() {
		System.out.println("ACTION Double-Click!");
	}

	public CShape getShape() {
		return label;
	}

	static public void main(String[] args) {
		JFrame frame = new JFrame();
		Canvas canvas = new Canvas(400, 400);
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);

		SimpleButton simple = new SimpleButton(canvas, "simple", 1);
		simple.getShape().translateBy(100, 100);

		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(simple.stateMachine));
		viz.pack();
		viz.setVisible(true);
	}

}
