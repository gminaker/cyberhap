package cyberhapsim1;

import java.util.ArrayList;

import processing.serial.*;
import shiffman.box2d.*;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;

public abstract class MassObject {
	  
	  //Objects touching
	  ArrayList<MassObject> connected;
	  
	  // Body of current object
	  Body body;
	  
	  // unique ID
	  float id;
	  
	//  void addConnection(MassObject o){
//	    connected.add(o);
	//  }
	//  
	//  void removeConnection(int _id){
//	    for(int i = 0; i < connected.size(); i++){
//	      MassObject mo = connected.get(i);
//	      if(mo.id == _id){
//	       connected.remove(i); 
//	      }
//	    }
	//  }
	}