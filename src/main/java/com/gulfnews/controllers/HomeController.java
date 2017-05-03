package com.gulfnews.controllers;

import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.TemplateEngine;

public class HomeController {
	
	private Router router;
	private TemplateEngine engine;
	private final String mainPageUrl="assets/app/server/views/home/index.peb";
	
	public HomeController(TemplateEngine engine,Router router){
		this.router=router;
		this.engine=engine;
	}
	
	public void Index(RoutingContext context){

		   context.put("name", "Vert.x Web");

	        engine.render(context,mainPageUrl,res->{
	        	responseHandler(res,context);
	        }); 
	}
	
	private void responseHandler(AsyncResult<Buffer> res,RoutingContext context){
	          if (res.succeeded()) {
	        	  context.response().end(res.result());
	          } else {
	        	  context.fail(res.cause());
	          }
	        
	}

}
