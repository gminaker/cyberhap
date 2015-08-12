package cyberhapsim1;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class Fulcrum {
	 
	  RevoluteJoint joint;
	  Box box1;
	  Box box2; 
	  PApplet parent;
	  Box2DProcessing box2d;
	  
	  Fulcrum(PApplet p, Box2DProcessing b2, float x, float y) {
		parent = p;
		box2d = b2;
	    box1 = new Box(p, box2d, x, y-20, Lesson.FULCRUM_WIDTH, Lesson.FULCRUM_HEIGHT, false, "lever"); 
	    box2 = new Box(p, b2, x, y, 10, 40, true, "pivot"); 
	    
	    RevoluteJointDef rjd = new RevoluteJointDef();
	    
	    //Vec2 offset = box2d.vectorPixelsToWorld(new Vec2(0,60));
	    
	    rjd.initialize(box1.body, box2.body, box1.body.getWorldCenter());
	    
	    rjd.enableLimit = true;
	    rjd.lowerAngle = PApplet.radians(-15);
	    rjd.upperAngle = PApplet.radians(15);
	    
	    joint = (RevoluteJoint) box2d.world.createJoint(rjd);
	    
	  }
	    
	  void display() {
	    box2.display();
	    box1.display();
	  }
	}
