package cyberhapsim1;

import java.util.ArrayList;
import java.util.UUID;

import processing.serial.*;
import shiffman.box2d.*;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.*;

public class MassObject {
	  
	  //Objects touching
	  ArrayList<MassObject> connected;
	  
	  // Body of current object
	  Body body;
	  
	  int num_contacts;
	  
	  // unique ID
	  UUID id;
	  
	  void startContact() { num_contacts++; }
	  void endContact() { num_contacts++; }
	  
	}