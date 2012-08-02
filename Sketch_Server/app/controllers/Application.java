package controllers;

import model.Gesture;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import play.*;
import play.api.libs.json.JsObject;
import play.api.libs.json.Json;
import play.libs.F.Callback;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  	
  public static Result index() {	   	  
    return ok(index.render(null));
  }
  
  public static WebSocket<JsonNode> createClass() {
      return new WebSocket<JsonNode>() { 
          // called when the websocket is established
          public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
              // register a callback for processing instream events      	  
              in.onMessage(new Callback<JsonNode>() {
                  public void invoke(JsonNode event) throws JsonParseException, JsonMappingException, IOException { 
                	                  	 
                	  //ObjectMapper mapper = new ObjectMapper();                 
                	//@SuppressWarnings("unchecked")
					//Map<String,ArrayList> jsonMap = mapper.readValue(event, Map.class);               	  
                	 //System.out.println(jsonMap.get("x"));            	  
                      //Logger.info(jsonMap.get("x").toString());
              }  }
               );                             
          }  
      };
   }
    
  
  public static WebSocket<JsonNode> addGesture() {
	  
      return new WebSocket<JsonNode>() { 	  
          // called when the websocket is established
          public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
	 	        	
        try{    	
        Gesture.register(in, out,true);          
	    }
	    catch(Exception e){e.printStackTrace();}	           
    }};
} 
	     
  public static WebSocket<JsonNode> sockHandler() {
	  
      return new WebSocket<JsonNode>()
    		  {
          // called when the websocket is established
          public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {       	                                                    
        	 	        	
              try{ 
                 Gesture.register(in, out ,false);  
      	    }
      	    catch(Exception e){e.printStackTrace();}     	                
          }
      };
  }
  
  
}