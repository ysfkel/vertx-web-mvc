package com.gulfnews.verticles;



import com.gulfnews.config.RoutesConfig;
import com.gulfnews.controllers.ArticlesController;
import com.gulfnews.controllers.HomeController;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.PebbleTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

public class App extends AbstractVerticle{
	

    private HomeController home;
    private ArticlesController articles;
    private RoutesConfig routesConfig;
    
	  @Override
	  public void start(Future<Void> future){

	      Router router=Router.router(vertx);
        
	       final PebbleTemplateEngine engine = PebbleTemplateEngine.create(vertx);
	       //Initialize controllers
	       final HomeController home=new HomeController(engine,router);
	       final ArticlesController articles=new ArticlesController(engine,router);
	       //configure routes
	       final RoutesConfig config=new RoutesConfig(home,articles,router);
	       config.ConfigRoutes();
         
	       startServer(future,router);
	  }
	  
	
	  
	  public void startServer(Future<Void>future,Router router){
		    router.route("/assets/*").handler(StaticHandler.create("assets"));
		     vertx.createHttpServer().requestHandler(router::accept)
		     .listen(
		    		 config().getInteger("http.port",9000),
		    		 result->{
		    			 if(result.succeeded()){
		    				 future.complete();
		    			 }else{
		    				 future.fail(result.cause());
		    			 }
		    		 }
		    		 );
	  }

}