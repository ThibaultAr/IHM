package e201.skeleton ;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font ;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JFrame ;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape ;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.transitions.EnterOnTag;
import fr.lri.swingstates.canvas.transitions.LeaveOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnTag;
import fr.lri.swingstates.canvas.transitions.ReleaseOnTag;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Release;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class SimpleButton {

    private CText label ;
    private CRectangle rect;
    protected CStateMachine stateMachine;

    SimpleButton(Canvas canvas, String text) {
	   label = canvas.newText(0, 0, text, new Font("verdana", Font.PLAIN, 12));
	   rect = canvas.newRectangle(label.getMinX() - 10, label.getMinY() - 10, text.length()*12/2 + 6 + 20, 12 + 20);
	   rect.setFillPaint(Color.white);
	   rect.setParent(label);
	   label.above(rect);
	   
	   rect.addTag("rectangle");
	   
	   this.stateMachine = new CStateMachine() {
		   Paint initColor;
		   Stroke initStroke;
		   State idle = new State() {
			public void enter() {
//				SimpleButton.this.rect.setFillPaint(bla);
			}

				Transition io = new EnterOnTag("rectangle", ">> over"){
		    		public void action() {
		    			initStroke = getShape().getStroke();
		    			getShape().setStroke(new BasicStroke(3));
		    		}
		    	};
			};
			
			State over = new State() {
				Transition oi = new LeaveOnTag("rectangle", ">> idle") {
					public void action() {
						getShape().setStroke(initStroke);
					}
				};
				
				Transition op = new PressOnTag("rectangle", 1, ">> pressed") {
					public void action() {
						initColor = getShape().getFillPaint();
						getShape().setFillPaint(Color.YELLOW);
						getShape().setStroke(initStroke);
					}
				};
			};
			
			State pressed = new State() {
				Transition po = new ReleaseOnTag("rectangle", 1, ">> over") {
					public void action() {
						getShape().setStroke(new BasicStroke(3));
						getShape().setFillPaint(initColor);
					}
				};
				
				Transition pd = new LeaveOnTag("rectangle", ">> deactivated") {
					public void action() {
						getShape().setFillPaint(initColor);
					}
				};
			};
			
			State deactivated = new State() {
				Transition dp = new EnterOnTag("rectangle", ">> pressed") {
					public void action(){
						getShape().setFillPaint(Color.YELLOW);
					}
				};
				
				Transition di = new Release(1, ">> idle") {};
			};
			
		};
		this.stateMachine.attachTo(canvas);
    }

    public void action() {
	   System.out.println("ACTION!") ;
    }

    public CShape getShape() {
	   return label ;
    }

    static public void main(String[] args) {    	
	   JFrame frame = new JFrame() ;
	   Canvas canvas = new Canvas(400,400) ;
	   frame.getContentPane().add(canvas) ;
	   frame.pack() ;
	   frame.setVisible(true) ;

	   SimpleButton simple = new SimpleButton(canvas, "simple") ;
	   simple.getShape().translateBy(100,100);
	   
	   	JFrame viz = new JFrame() ;
   		viz.getContentPane().add(new StateMachineVisualization(simple.stateMachine)) ;
   		viz.pack() ;
   		viz.setVisible(true) ;
    }

}
