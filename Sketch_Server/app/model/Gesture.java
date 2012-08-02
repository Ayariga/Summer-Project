package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import play.Logger;
import play.libs.Akka;
import play.libs.F.Callback;
import play.mvc.WebSocket;

public class Gesture extends UntypedActor{
		
	static ActorRef actor = Akka.system().actorOf(new Props(Gesture.class));
	static boolean train ;
	
	
	public static void register(final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out, final boolean t)
			throws Exception {
		
		
		  in.onMessage(new Callback<JsonNode>() {
              public void invoke(JsonNode event) throws JsonParseException, JsonMappingException, IOException { 
            	   
            	   
            	   ObjectMapper mapper = new ObjectMapper(); 
            	  
            @SuppressWarnings("unchecked")
			Map<String,ArrayList<Double>> jsonMap = mapper.readValue(event, Map.class); 
            
            @SuppressWarnings("unchecked")
			Map<String,ArrayList<Integer>> jsonMap2 = mapper.readValue(event, Map.class); 
            
            
            
              	  
            actor.tell(new Features(jsonMap,jsonMap2, t));
            
            
            
            	  // System.out.println(jsonMap.get("x"));          	  
                  //Logger.info(jsonMap.get("x").toString());
                  //Logger.info(jsonMap.get("y").toString());
                 // Logger.info(jsonMap2.get("timeArray").toString());                
              } 
           });				
		      }

	@Override
	public void onReceive(Object message) throws Exception {
		
		if(message instanceof Features) {
		
		Features msg = (Features) message;
		   
		   msg.getD();
		   msg.feature1();
		   msg.feature2();
		   msg.feature3();
		   msg.feature4();
		   msg.feature5();
		   msg.feature6();
		   msg.feature7(); 
		   msg.feature8();
		   msg.feature9();
		   msg.feature10();
		   msg.feature11();
		   msg.feature12();
		   msg.feature13(); 	   	   
		   msg.prints();
		   
		   }
		
		else { unhandled(message);}
       
	}
		
	
	public static class Features	{
		
		Double d = 0.00;
		Double f1 = 0.00;
		Double f2 = 0.00;
		double f3 = 0.00;
		double f4 = 0.00;
		double f5 = 0.00;
		double f6 = 0.99;
		double f7 = 0.00;
		double f8 = 0.00;
		double f9 = 0.00;
		double f10 = 0.00;
		double f11 = 0.00;
		double f12 = 0.00;
		double f13 = 0.00;
			  
	  
		//Classifier classifier;
		
		 public static final int           NFEATURES         = 13;	
		static List<Object> features = new ArrayList<>(13);
		Map <String,ArrayList<Double>> jsonMap;
		Map <String,ArrayList<Integer>> jsonMap2;
		boolean train ;
		
		
		
		public Features (Map <String,ArrayList<Double>> jsonMap, Map <String,ArrayList<Integer>> jsonMap2 ,boolean t)
		{  			
			super();
			this.jsonMap = jsonMap;	
			this.jsonMap2 = jsonMap2;
			this.train = t;
			
					}
		
		public Features () {}					
 
		
	public void getD()
	{ 
	Double a =  Math.pow((jsonMap.get("x").get(2) - jsonMap.get("x").get(0)), 2);	
	Double b = Math.pow((jsonMap.get("y").get(2) - jsonMap.get("y").get(0)), 2);
	
	  a = Math.abs(a);
	  b = Math.abs(b);
	d = Math.sqrt(a + b);	
	}
		
	public void feature1()
	{		
	 f1 =  Math.abs((jsonMap.get("x").get(2) - jsonMap.get("x").get(0))) / d;
	 features.add(0,f1);
	}
	
	public void feature2()
	{
	f2=	Math.abs((jsonMap.get("y").get(2) - jsonMap.get("y").get(0))) / d;
	features.add(1,f2);
	}
	
	public void feature3()
	{
	double dx = Math.abs((jsonMap.get("maxPoint").get(0) - jsonMap.get("minPoint").get(0)));
	double dy = Math.abs((jsonMap.get("maxPoint").get(1) - jsonMap.get("minPoint").get(1)));
	double sqr = Math.pow(dx, 2) + Math.pow(dy,2) ;
	 f3 = Math.sqrt(sqr);
	 features.add(2,f3);
	}
	
	public void feature4()
	{
	double ty = jsonMap.get("maxPoint").get(1) - jsonMap.get("minPoint").get(1);
	double tx = jsonMap.get("maxPoint").get(0) - jsonMap.get("minPoint").get(0);
	tx = Math.abs(tx);
	ty = Math.abs(ty);			
	f4 = Math.atan(ty / tx );
	features.add(3,f4);
	}
	
	public void feature5()
	{
    int  s = jsonMap.get("x").size() - 1; // to get the last index of the array
    double m = jsonMap.get("x").get(0) - jsonMap.get("x").get(s);
    double n = jsonMap.get("y").get(0) - jsonMap.get("y").get(s);
    m = Math.abs(m);
    n = Math.abs(n);
    double p = Math.pow(m, 2) + Math.pow(n,2);
    f5 = Math.sqrt(p);
    features.add(4,f5);
	}
	
	public void feature6()
	{
		int  l = jsonMap.get("x").size() - 1; // to get the last index of tha array
	    double g = jsonMap.get("x").get(0) - jsonMap.get("x").get(l);
	    g = Math.abs(g);
	    f6 = g/ f5;	
	    features.add(5,f6);
	}
	
	public void feature7()
	{
		int  l = jsonMap.get("y").size() - 1; // to get the last index of tha array
	    double i = jsonMap.get("y").get(0) - jsonMap.get("y").get(l);
	    i = Math.abs(i);
	    f7 = i/ f5;	 
	    features.add(6, f7);
	}
	
	public void feature8()
	{ int size = 0;
	 double dx = 0.00;
	 double dy = 0.00;
	 int z = 0;
	 for(int x  = 0; x < jsonMap.get("x").size(); x++)
	 {  
		 if(x >0) {
	     z = x -1;
	     
	   dx = jsonMap.get("x").get(x) - jsonMap.get("y").get(z);	   
	   dy = jsonMap.get("y").get(x) - jsonMap.get("y").get(z);
	 
	   dx = Math.pow(dx,2);
	   dy = Math.pow(dy,2); 
	   	   
	   if(size < (jsonMap.get("x").size() - 2)) 
	   {
		  size++;
		 f8 += Math.sqrt( dx + dy);
		 features.add(7,f8);
	   }
	   }
	 }	 
	}
	
	
	public void feature9()
	{ double  dx1 = 0.00;
	  double dx2 = 0.00;
	  int size = 0;
	  double dy1 = 0.00;
	  double dy2 = 0.00;
	  int z = 0;
	  double n = 0.00;
	  double m = 0.00;
	  
	  for(int x  = 0; x < jsonMap.get("x").size(); x++)
		 { dx1=  dx2;
		   dy1 = dy2;
//this is to check whether we have value for first and second coordinates to determine dx and dy
		  
			 if(x >0) {
				 
		     z = x -1;
		dx2 = jsonMap.get("x").get(x) - jsonMap.get("y").get(z);	   
		dy2 = jsonMap.get("y").get(x) - jsonMap.get("y").get(z);	
		
		  if(x > 1) 
		  { //this ensures that we have the second values for the arctan calculations
		m = (dx2 * dy1) - (dx1 * dy2);
		n = (dx2 * dx1)+(dy2 * dy1);

		if(size < (jsonMap.get("x").size() - 2)) 
		   { 
			  size++;
			 f9 += ( m/n); 	
			 features.add(8, f9);
		   } }
		} }	
	  }
	
	
	public void feature10()
	{ int s = jsonMap.get("x").size();
		 double  dx1 = 0.00;
		  double dx2 = 0.00;
		  int size = 0;
		  double dy1 = 0.00;
		  double dy2 = 0.00;
		  int z = 0;
		  double n = 0.00;
		  double m = 0.00;
		  
		  for(int x  = 0; x < s; x++)
			 { dx1=  dx2;
			   dy1 = dy2;
	//this is to check whether we have value for first and second coordinates to determine dx and dy
			  
				 if(x >0) {
					 
			     z = x -1;
			dx2 = jsonMap.get("x").get(x) - jsonMap.get("y").get(z);	   
			dy2 = jsonMap.get("y").get(x) - jsonMap.get("y").get(z);	
			
			  if(x > 1) 
			  { //this ensures that we have the second values for the arctan calculations
			m = (dx2 * dy1) - (dx1 * dy2);
			n = (dx2 * dx1)+(dy2 * dy1);

			if(size < (s - 2)) 
			   { 
				  size++;
				 f10 += Math.abs( m/n); 
				 features.add(9,f10);
			   } }
			} }		
	}

	public void feature11()
	{   int s = jsonMap.get("x").size();
		 double  dx1 = 0.00;
		  double dx2 = 0.00;
		  int size = 0;
		  double dy1 = 0.00;
		  double dy2 = 0.00;
		  int z = 0;
		  double n = 0.00;
		  double m = 0.00;
		  
		  for(int x  = 0; x < s; x++)
			 { dx1=  dx2;
			   dy1 = dy2;
	//this is to check whether we have value for first and second coordinates to determine dx and dy
			  
				 if(x >0) {
					 
			     z = x -1;
			dx2 = jsonMap.get("x").get(x) - jsonMap.get("y").get(z);	   
			dy2 = jsonMap.get("y").get(x) - jsonMap.get("y").get(z);	
			
			  if(x > 1) //this ensures that we have the second values for the arctan calculations 
			  { 
			m = (dx2 * dy1) - (dx1 * dy2);
			n = (dx2 * dx1)+(dy2 * dy1);

			if(size < (s- 2)) 
			   { 
				  size++;
				 f11 += Math.pow(( m/n),2);
				 features.add(10, f11);
			   } }
			} }		
	}
	

	public void feature12()
	{   int s = jsonMap2.get("timeArray").size(); 
		double result = 0.00;
		 int size = 0;
		 double dx = 0.00;
		 double dy = 0.00;
		 int dt =  0;
		 double dt2 = 0.00;
		 int z = 0;
		 for(int x  = 0; x < s; x++)
		 {  
			 if(x >0) {
		     z = x -1;
		     
		   dx = jsonMap.get("x").get(x) - jsonMap.get("y").get(z);	   
		   dy = jsonMap.get("y").get(x) - jsonMap.get("y").get(z);
		   dt = jsonMap2.get("timeArray").get(x) - jsonMap2.get("timeArray").get(z);
		 
		   dx = Math.pow(dx,2);
		   dy = Math.pow(dy,2); 
		   dt2 = (double) Math.pow(dt,2); 
			 
		   if(size < (s - 2)) //setting p limit to p - 2
		   {
			   result = (dx + dy)/dt; //calculating f12 
			   
			   if(result > f12)  //selecting max f12
				   f12 = result;
			   features.add(11, f12);
		   }
			 }	
	} } 
	

	public void feature13()
	{
	  int t0 = jsonMap2.get("timeArray").get(0);
	  int size = jsonMap2.get("timeArray").size() - 2 ; //to get tp - 1 we take size size of the array since array counting starts from 0 and not 1
	 int  tp = jsonMap2.get("timeArray").get(size);
	  f13 = (double) tp - t0 ;
	  features.add(12,f13);
	}
		
	     	  
		
	public void prints()
	{	
	System.out.println(" this is size  " + jsonMap.get("x").size());
	System.out.println(" this is from ddd  " +  d);
	System.out.println(" this is from f1  " +  f1);
	System.out.println(" this is from f2  " +  f2);
	System.out.println(" this is from f3  " +  f3);
	System.out.println(" this is from f4  " +  f4);
	System.out.println(" this is from f5  " +  f5);
	System.out.println(" this is from f6  " +  f6);
	System.out.println(" this is from f7  " +  f7);
	System.out.println(" this is from f8  " +  f8);
	System.out.println(" this is from f9  " +  f9);
	System.out.println(" this is from f10  " + f10);
	System.out.println(" this is from f11  " + f11);
	System.out.println(" this is from f12  " + f12);
	System.out.println(" this is from f13  " + f13);  
	}	

	}
}










