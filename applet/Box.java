package cyberhapsim1;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.serial.*;
import shiffman.box2d.*;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;

public class Box extends MassObject{
	  
	  float w;
	  float h;
	  String name;
	  PApplet parent;
	  Box2DProcessing box2d;
	  
	  Box(PApplet p, Box2DProcessing b2, float x, float y, float w_, float h_, boolean lock, String name_){
		box2d = b2;
		parent = p;
	    w = w_;
	    h = h_;
	    name = name_;
	    
	    BodyDef bd = new BodyDef();
	    bd.position.set(box2d.coordPixelsToWorld(new Vec2(x,y)));
	    if (lock) bd.type = BodyType.STATIC;
	    else bd.type = BodyType.DYNAMIC;
	    bd.setUserData(this);
	    
	    body = box2d.createBody(bd);
	    
	    // Define the shape -- a  (this is what we use for a rectangle)
	    PolygonShape sd = new PolygonShape();
	    float box2dW = box2d.scalarPixelsToWorld(w/2);
	    float box2dH = box2d.scalarPixelsToWorld(h/2);
	    sd.setAsBox(box2dW, box2dH);
	    
	    // Define a fixture
	    FixtureDef fd = new FixtureDef();
	    fd.shape = sd;
	    // Parameters that affect physics
	    fd.density = 1;
	    fd.friction = 1.0f;
	    fd.restitution = 0.0f;
	    
	    body.createFixture(fd);

	    // Give it some initial random velocity
	    //body.setLinearVelocity(new Vec2(random(-5,5),random(2,5)));
	    //body.setAngularVelocity(random(-5,5));
	  }
	  
	    // This function removes the particle from the box2d world
	  void killBody() {
	    box2d.destroyBody(body);
	  }

	  // Drawing the box
	  void display() {
	    // We look at each body and get its screen position
	    Vec2 pos = box2d.getBodyPixelCoord(body);
	    // Get its angle of rotation
	    float a = body.getAngle();

	    parent.rectMode(PConstants.CENTER);
	    parent.pushMatrix();
	    parent.translate(pos.x,pos.y);
	    parent.rotate(-a);
	    parent.fill(175);
	    parent.stroke(0);
	    parent.rect(0,0,w,h);
	    parent.popMatrix();
	  }
	  
	  String getObjectName(){
	     return name;
	  }
	}