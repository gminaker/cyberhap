package cyberhapsim1;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;
import java.util.UUID;

public class Finger extends MassObject{
	  
	  PImage hand_img;
	  int w = 40;
	  int h = 40;
	  Box2DProcessing box2d;
	  PApplet parent;
	  
	  Finger(PApplet p, Box2DProcessing b2, int x, int y){
		parent = p;
		box2d = b2;
	    hand_img = parent.loadImage("hand.png");
	    id = UUID.randomUUID();
	    num_contacts = 0;
	    
	    BodyDef bd = new BodyDef();
	    bd.position.set(box2d.coordPixelsToWorld(new Vec2((int) x,(int) y)));
	    bd.type = BodyType.DYNAMIC;
	    bd.fixedRotation = true;
	    
	    body = box2d.createBody(bd);
	    body.setGravityScale(0);
	    
	    PolygonShape sd = new PolygonShape();
	    float box2dW = box2d.scalarPixelsToWorld(w/2);
	    float box2dH = box2d.scalarPixelsToWorld(h/2);
	    sd.setAsBox(box2dW, box2dH);
	    
	    // Define a fixture
	    FixtureDef fd = new FixtureDef();
	    fd.shape = sd;
	    // Parameters that affect physics
	    fd.density = 1f;
	    fd.friction = 0.3f;
	    fd.restitution = 0.5f;
	    
	    body.createFixture(fd);
	  }
	  
	  void display(){
	    
	    // We look at each body and get its screen position
	    Vec2 pos = box2d.getBodyPixelCoord(body);
	    // Get its angle of rotation
	    float a = body.getAngle();

	    parent.rectMode(PConstants.CENTER);
	    parent.pushMatrix();
	    parent.translate(pos.x,pos.y);
	    parent.rotate(-a);
	    parent.image(hand_img, -w/2, -h/2, w, h);
	    parent.popMatrix();
	  }
	  
	  float getX(){
	    Vec2 position = box2d.getBodyPixelCoord(body);
	    return position.x;
	  }
	  
	  float getY(){
	    Vec2 position = box2d.getBodyPixelCoord(body);
	    return position.y;
	  }
	  
	  void setPosition(double _x, double _y){
		body.setTransform(box2d.coordPixelsToWorld(new Vec2((int)_x,(int)_y)), body.getAngle());
	  }
	  
	  boolean clickedOnFinger(){
	    Vec2 position = box2d.getBodyPixelCoord(body);
	    
	    if(parent.mouseX >= position.x - w/2 && parent.mouseX <= position.x + w/2
	    &&parent.mouseY >= position.y - h/2 && parent.mouseY <= position.y + h/2){
	      return true;
	    }
	    
	    return false;
	  }
	  
	}
