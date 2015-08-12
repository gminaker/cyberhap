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
import controlP5.*;

public class Lesson extends PApplet {

	//CONSTANTS
	
	public static int SCREEN_WIDTH = 1000;
	public static int SCREEN_HEIGHT = 600;
	public static int SPACING = 25;
	
	public static int EDITOR_WIN_WIDTH = (int) (SCREEN_WIDTH*2)/3;
	public static int EDITOR_WIN_HEIGHT = (int) (SCREEN_HEIGHT*3)/4;
	
	public static int SETTINGS_WIN_WIDTH = (int) SCREEN_WIDTH/3;
	public static int SETTINGS_WIN_HEIGHT = (int) (SCREEN_HEIGHT*3)/4;
	
	public static int TOOLBOX_WIN_WIDTH = (int) SCREEN_WIDTH;
	public static int TOOLBOX_WIN_HEIGHT = (int) SCREEN_HEIGHT/4;
	
	public static int FULCRUM_WIDTH = 400;
	public static int FULCRUM_HEIGHT = 10;
	
	public static int INITIAL_FINGER_Y = 300;
	
	// VARIABLES & DECLARATIONS
	Box2DProcessing box2d;
	Fulcrum fulcrum;
	Finger finger;
	ArrayList<Tool> tools;
	double sector_pulley_position;
	Spring spring;
	ArrayList<Vec2> vecs;
	int finger_force;
	
	//Control P5
	ControlP5 cp5;
	Button reset;
	
	//serialports 
	// Arduino board serial port index, machine-dependent:
	int serialPortIndex = 0;
	int SERIAL_WRITE_LENGTH = 32;
	Serial myPort;
	
	//MISC
	boolean locked;
	float xOffset;
	float yOffset;
	float springOffset;
	
	CustomListener cl;
	
	public void setup() {
		  size(SCREEN_WIDTH, SCREEN_HEIGHT);
		  smooth(); 
		  
		  cp5 = new ControlP5(this);
		  
		  // Initialize Serial Comms
		  myPort = new Serial(this, Serial.list()[4], 9600); 
		  myPort.bufferUntil('\n');
		  
		  // Intialize Box2D
		  box2d = new Box2DProcessing(this);
		  box2d.createWorld();
		  cl = new CustomListener(this, box2d);
		  box2d.world.setContactListener(cl);
		  
		  // List of physics toolbox objects to go in Box2D world
		  tools = new ArrayList<Tool>();
		  
		  addInputWidgets();
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
		  
		  drawEditorPanel();
		  drawSettingsPanel();
		  drawToolboxPanel();

		  
		  spring.update(finger.getX(), (float) (sector_pulley_position + INITIAL_FINGER_Y));
		  springOffset = 0;
		  spring.display();
		  
		  for (int i = tools.size()-1; i >= 0; i--) {
		    Tool t = tools.get(i);
		    t.display();
		  }
		  
		  textSize(26); 
		  text("Physics Lesson - Fulcrum", 25, 40);
		  textSize(10); 
		  fill(125);
		  text(Double.toString(sector_pulley_position), 25, 50);
		  text(finger_force, 25, 60);
		  
		  for(int i=0; i<cl.b.size(); i++){
			 Body bod = cl.b.get(i);
			 Vec2 coord = box2d.getBodyPixelCoord(bod);
			 
			 // Points:
			 float c1x = coord.x;
			 float c1y = coord.y;

		     ellipse(c1x, c1y, 25, 25);
		  }  
		  
		  fulcrum.display();
		  finger.display();
		  fill(0);
	
	}
	
	private void drawEditorPanel() {
		pushMatrix();
		translate((EDITOR_WIN_WIDTH/2)+SPACING, (EDITOR_WIN_HEIGHT/2)+SPACING*3);
		noFill(); 
		rect(0, 0, EDITOR_WIN_WIDTH, EDITOR_WIN_HEIGHT);  
		popMatrix();
	}
	
	private void drawSettingsPanel() {
		pushMatrix();
		translate((SETTINGS_WIN_WIDTH/2)+(SPACING*2)+EDITOR_WIN_WIDTH, 
				(SETTINGS_WIN_HEIGHT/2)+SPACING*3);
		noFill(); 
		rect(0, 0, SETTINGS_WIN_WIDTH, SETTINGS_WIN_HEIGHT);  
		popMatrix();
	}
	
	private void addInputWidgets(){
		float settings_x = EDITOR_WIN_WIDTH+(SPACING*2);
		float settings_y = SPACING*3;
		
		reset = cp5.addButton("Reset Simulation")
		     .setValue(1)
		     .setPosition(settings_x+50, settings_y+SETTINGS_WIN_HEIGHT-30)
		     .setSize(85,20);
	}
	
	private void drawToolboxPanel() {
		pushMatrix();
		translate((float) ((TOOLBOX_WIN_WIDTH/2)+SPACING*1.5), 
				(TOOLBOX_WIN_HEIGHT/2)+(SPACING*4)+EDITOR_WIN_HEIGHT);
		noFill(); 
		rect(0, 0,TOOLBOX_WIN_WIDTH+SPACING, TOOLBOX_WIN_HEIGHT);  
		popMatrix();
		
		pushMatrix();
		translate(SPACING, (SPACING*4)+EDITOR_WIN_HEIGHT);
		textSize(16);
		text("Physics Toolbox", 5, 20);
		popMatrix();
		
	}
	
	public void controlEvent(ControlEvent theEvent) {
		  if(theEvent.isFrom(reset)) {
		    for(int i=0; i<tools.size(); i++){
		    	tools.get(i).killBody();
		    	tools.remove(i);
		    }
		  }
	      
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
			springOffset = mouseY-yOffset;
		    spring.update(mouseX-xOffset, mouseY-yOffset);
		  }
	}

	public void mouseReleased() {
		  locked = false;
	}

	/**
	* TODO: Document
	*/
	public void serialEvent(Serial port) {
		    String inString = "";
		    
		    while(myPort.available() > 0)
		    {
		      inString = myPort.readStringUntil('\n');
		    }
		    
		    if (inString != null)
		    {
		       try {
		        
		        String[] list = split(inString, ',');
		        //print("O: ");
		        //println(inString);
		        
		        // trim off whitespaces.
		        String xString = trim(list[0]); 
     
		        // convert to a number.
		        int xByte = Integer.valueOf(xString);  
		        
		        // TODO: Clean this up. Abstract out mapping of values.
		        if(!Float.isNaN(xByte) && xByte != 0){
		          double updated_x = map(xByte, -3000, 3000, -1000, 1000);
		          sector_pulley_position = updated_x;
		        }       
		        
		       } catch (Exception e) {}
		    
		     }
	}

}

