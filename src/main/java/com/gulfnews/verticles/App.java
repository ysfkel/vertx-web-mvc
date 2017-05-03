package com.gulfnews.verticles;



import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;
import com.algolia.search.AsyncIndex;
import com.algolia.search.Index;
import com.algolia.search.objects.Query;
import com.gulfnews.config.RoutesConfig;
import com.gulfnews.controllers.ArticlesController;
import com.gulfnews.controllers.HomeController;
import com.gulfnews.models.Article;

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
	       
	       AsyncAPIClient client = new AsyncHttpAPIClientBuilder("QZYNF1WM35", "cf5164fb85e04f764a4d1d6c3ac23f97").build();
        
	       Article article=new Article();
	  
	       vertx.executeBlocking(f -> {
	    	     AsyncIndex<Article> index = client.initIndex("articles", Article.class);
	    	   // Call some blocking API that takes a significant amount of time to return
	    	
		       index.addObject(new Article()
		    		      .setTitle("As the sun rose")
		    		      .setBody("this is a story"));
	    	   f.complete();
	    	 }, res -> {
	    		
	    	   System.out.println("The result is: " + res.result());
	    	   
	    	 });
	       
	       
	       

	    		      
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