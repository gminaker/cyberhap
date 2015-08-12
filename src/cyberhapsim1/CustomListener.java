package cyberhapsim1;

//The Nature of Code
//<http://www.shiffman.net/teaching/nature>
//Spring 2010
//Box2DProcessing example

//ContactListener to listen for collisions!

//THIS ASSUMES THAT A CONSTANT COLLISION
//IS 

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;

import processing.core.PApplet;
import processing.serial.Serial;
import shiffman.box2d.Box2DProcessing;

public class CustomListener implements ContactListener {

int index;
PApplet parent;
Box2DProcessing box2d;
ArrayList<Body> b;

Fixture f1;
Fixture f2;

Body b1;
Body b2;

Object o1;
Object o2;

CustomListener(PApplet p, Box2DProcessing b2) {
	box2d = b2;
	b = new ArrayList<Body>();
}

// This function is called when a new collision occurs
public void beginContact(Contact cp) {
 // Get both fixtures
 f1 = cp.getFixtureA();
 f2 = cp.getFixtureB();
 
 // Get both bodies
 b1 = f1.getBody();
 b2 = f2.getBody();

 //Vec2 force = b1.m_force;
 
 // Get our objects that reference these bodies
 o1 = b1.getUserData();
 o2 = b2.getUserData();
 
 if(o1 != null && o1.getClass() == Tool.class){
	 b.add(b1);
 }
 
 if(o2 != null && o2.getClass() == Tool.class){
	 b.add(b2);
 }
 
 //float x = f1.getWidth();
 
 float a1 = b1.getAngle();
 float a2 = b2.getAngle();
 
 float m1 = b1.getMass();
 float m2 = b2.getMass();
 
 if(b1 != null && b2 !=null){
	 Vec2 c1 = box2d.getBodyPixelCoord(b1);
	 Vec2 c2 = box2d.getBodyPixelCoord(b2);
 }
 
// Points:
// float c1x = c1.x;
// float c1y = c1.y;
// float c2x = c2.x;
// float c2y = c2.y;
// 
 
 //b1.getFixtureList();
 
// println(c1);
// println(c2);
// println(a1);
// println(a2);
// println(m1);
 // println(m2);
 
 //WorldManifold man;
 //cp.getWorldManifold();

}

public void endContact(Contact contact) {

	 Fixture f1t = contact.getFixtureA();
	 Fixture f2t = contact.getFixtureB();
	 
	 // Get both bodies
	 Body b1t = f1.getBody();
	 Body b2t = f2.getBody();
	 
	 if(contact.isTouching() == false){
			remove(b1t);
			remove(b2t);
	 }else{
		 System.out.println("False Alarm");
	 }

}

public void remove(Body bod){
	MassObject mo = (MassObject) bod.getUserData();
	Boolean done = false;

	if(mo != null){
		for(int i=0; i<b.size(); i++){
			
			Body bod2 = b.get(i);
			
			MassObject mo2 = (MassObject) bod2.getUserData();
	
			if(done == false && mo2 != null && mo.id == mo2.id){
				b.remove(i);
				done = true;
			}
		}
	}
}

public void preSolve(Contact contact, Manifold oldManifold) {
 // TODO Auto-generated method stub
 
 
}

public void postSolve(Contact contact, ContactImpulse impulse) {
 // TODO Auto-generated method stub
}
}

