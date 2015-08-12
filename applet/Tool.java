package cyberhapsim1;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.serial.*;
import shiffman.box2d.*;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;

public class Tool extends MassObject{
	  
	  float w;
	  float h;

	  PApplet parent;
	  Box2DProcessing box2d;
	  
	  Tool(PApplet p, Box2DProcessing b2, float x, float y, float w_, float h_){
		box2d = b2;
		parent = p;
	    w = w_;
	    h = h_;  
	    connected = new ArrayList<MassObject>();
	    
	    // Put it in box2d world
	    makeBody(x,y,w,h);
	    body.setUserData(this);
	    
	  }
	  
	  void killBody() {
	   box2d.destroyBody(body); 
	  }
	  
	  void display() {
	    Vec2 pos = box2d.getBodyPixelCoord(body); 
	     
	    float a = body.getAngle();
	    parent.pushMatrix();
	    parent.translate(pos.x, pos.y);
	     
	    parent.rotate(-a);
	    parent.stroke(0);
	    parent.strokeWeight(1);
	    parent.rect(0, 0, w, h);
	     
	    // Let's add a line so we can see the rotation
	    parent.popMatrix();
	  }
	  
	  // Here's our function that adds the particle to the Box2D world
	  void makeBody(float x, float y, float w, float h) {
	    // Define a body
	    BodyDef bd = new BodyDef();
	    
	    // Set its position
	    bd.position = box2d.coordPixelsToWorld(x, y);
	    bd.type = BodyType.DYNAMIC;
	    
	    body = box2d.world.createBody(bd);
	    
	    // Make the body's shape a rectangle
	    PolygonShape cs = new PolygonShape();
	    cs.setAsBox(box2d.scalarPixelsToWorld(w/2), box2d.scalarPixelsToWorld(h/2));
	    
	    FixtureDef fd = new FixtureDef();
	    fd.shape = cs;
	    
	    fd.density = 2.0f;
	    fd.friction = 1.0f;
	    fd.restitution = 0.003f; // Restitution is bounciness
	    
	    body.createFixture(fd);
	    
	    // Give it a random initial velocity (and angular velocity)
	    //body.setLinearVelocity(new Vec2(random(-10f,10f),random(5f,10f)));
	    //body.setAngularVelocity(random(-10, 10));
	  }
	}
	  
