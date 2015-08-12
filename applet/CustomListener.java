package cyberhapsim1;

//The Nature of Code
//<http://www.shiffman.net/teaching/nature>
//Spring 2010
//Box2DProcessing example

//ContactListener to listen for collisions!

//THIS ASSUMES THAT A CONSTANT COLLISION
//IS 

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

public class CustomListener implements ContactListener {

int index;
PApplet parent;
Box2DProcessing box2d;

CustomListener(PApplet p, Box2DProcessing b2) {
	box2d = b2;
}

// This function is called when a new collision occurs
public void beginContact(Contact cp) {
 // Get both fixtures
 Fixture f1 = cp.getFixtureA();
 Fixture f2 = cp.getFixtureB();
 
 // Get both bodies
 Body b1 = f1.getBody();
 Body b2 = f2.getBody();
 
 Vec2 force = b1.m_force;
 
 
 // Get our objects that reference these bodies
 Object o1 = b1.getUserData();
 Object o2 = b2.getUserData();
 
 //float x = f1.getWidth();
 
 float a1 = b1.getAngle();
 float a2 = b2.getAngle();
 
 float m1 = b1.getMass();
 float m2 = b2.getMass();
 
 Vec2 c1 = box2d.getBodyPixelCoord(b1);
 Vec2 c2 = box2d.getBodyPixelCoord(b2);
 
// index = vecs.size();
// vecs.add(c1);
// vecs.add(c2);
 
 // Points:
 float c1x = c1.x;
 float c1y = c1.y;
 float c2x = c2.x;
 float c2y = c2.y;
 
 
 b1.getFixtureList();
 
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
// vecs.remove(index+1);
// vecs.remove(index);
}

public void preSolve(Contact contact, Manifold oldManifold) {
 // TODO Auto-generated method stub
 
 
}

public void postSolve(Contact contact, ContactImpulse impulse) {
 // TODO Auto-generated method stub
}
}

