package cyberhapsim1;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;
import processing.serial.*;
import shiffman.box2d.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;


public class Lesson extends PApplet {

	//CONSTANTS
	public static int FULCRUM_WIDTH = 400;
	public static int FULCRUM_HEIGHT = 10;
	
	// VARIABLES & DECLARATIONS
	Box2DProcessing box2d;
	Fulcrum fulcrum;
	Finger finger;
	ArrayList<Tool> tools;
	double sector_pulley_position;
	Spring spring;
	ArrayList<Vec2> vecs;
	int finger_force;
	
	//serialports 
	// Arduino board serial port index, machine-dependent:
	int serialPortIndex = 0;
	int SERIAL_WRITE_LENGTH = 32;
	Serial myPort;
	
	//MISC
	boolean locked;
	float xOffset;
	float yOffset;
	
	public void setup() {
		 size(700,500);
		  smooth(); 
		  
		  // Initialize Serial Comms
		  myPort = new Serial(this, Serial.list()[4], 9600); 
		  myPort.bufferUntil('\n');
		  
		  // Intialize Box2D
		  box2d = new Box2DProcessing(this);
		  box2d.createWorld();
		  box2d.world.setContactListener(new CustomListener(this, box2d));
		  
		  vecs = new ArrayList<Vec2>();
		  tools = new ArrayList<Tool>();
		  
		  tools.add(new Tool(this, box2d, 200, 50, 30, 20));
		  
		  finger = new Finger(this, box2d, 180, 300);
		  fulcrum = new Fulcrum(this, box2d, 350, 250);
		  finger_force = 0;
		  
		    // Make the spring (it doesn't really get initialized until the mouse is clicked)
		  spring = new Spring(this, box2d);
		  spring.bind(finger.getX(),finger.getY(),finger);
	}

	public void draw() {
		 background(255);
		  box2d.step();
		  
		  //finger.setPosition(finger.getX(), sector_pulley_position);
		  
		  spring.update(finger.getX(), (float) sector_pulley_position);
		  spring.display();
		  
		  for (int i = tools.size()-1; i >= 0; i--) {
		    Tool t = tools.get(i);
		    t.display();
		  }
		  
		  
		  text(Double.toString(sector_pulley_position), 25, 25);
		  text(finger_force, 25, 35);
		  
		  for(int i=0; i<vecs.size(); i++){
		    ellipse(vecs.get(i).x, vecs.get(i).y, 5, 5);
		  }  
		  
		  fulcrum.display();
		  finger.display();
		  fill(0);
	
	}
	
	public void mousePressed() {
		  if(finger.clickedOnFinger()){
		    locked = true;
		  }else{
		    locked = false; 
		    tools.add(new Tool(this, box2d, mouseX, mouseY, 40, 20)); 
		  }
		  xOffset = mouseX-finger.getX(); 
		  yOffset = mouseY-finger.getY(); 
	}

	public void mouseDragged() {
		  if(locked) {
		    //finger.setPosition(mouseX-xOffset, mouseY-yOffset);
		    spring.update(mouseX-xOffset, mouseY-yOffset);
		  }
	}

	public void mouseReleased() {
		  locked = false;
	}

		/**
		* TODO: Document
		*/
	void serialEvent(Serial port) {
		    String inString = "";
		    
		    while(myPort.available() > 0)
		    {
		      inString = myPort.readStringUntil('\n');
		    }
		    
		    if (inString != null)
		    {
		       try {
		        
		        String[] list = split(inString, ',');
		        
		        String xString = trim(list[0]); 
		        //println(xString);        // trim off whitespaces.
		        int xByte = Integer.parseInt(xString);           // convert to a number.
		        //println(xByte); 
		        if(!Float.isNaN(xByte) && xByte != 0){
		          double updated_x = map(xByte, -3000, 3000, -1000, 1000);
		          sector_pulley_position = updated_x;
		          
		        }       
		       } finally {}
		    
		     }
	}

}

