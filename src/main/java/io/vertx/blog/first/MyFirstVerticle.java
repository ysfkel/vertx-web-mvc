

package io.vertx.blog.first;
 import java.util.LinkedHashMap;
import java.util.Map;

import Controllers.ArticlesController;
import Controllers.HomeController;
import Models.Book;

//import com.mitchellbosecke.pebble.PebbleEngine;

// io.vertx.blog.first.MyFirstVerticle
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
//import io.vertx.ext.web.templ.JadeTemplateEngine;
import io.vertx.ext.web.templ.PebbleTemplateEngine;//te.ext.web.templ.PebbleTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

public class MyFirstVerticle extends AbstractVerticle{

    private Map<Integer,Book> books=new LinkedHashMap<>();
    private HomeController home;
    private ArticlesController articles;
    
	  @Override
	  public void start(Future<Void> future){
		  seetData();
	      Router router=Router.router(vertx);

	      // In order to use a template we first need to create an engine
	       final PebbleTemplateEngine engine = PebbleTemplateEngine.create(vertx);
	       //initialze controllers;
	       iniControllers(engine,router);
	       //configure routes 
	       router.get("/").handler(home::Index);
	       router.get("/articles").handler(articles::Index);
            
	 	   api(router);
	       
	       startServer(future,router);
	  }
	  
	  private void iniControllers(TemplateEngine engine,Router router){
		   home=new HomeController(engine,router);
	       articles=new ArticlesController(engine,router);
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  private void seetData(){
		    Book book=new Book("book 1","author 1");
		    books.put(book.getId(),book);
		    
		    Book book2=new Book("book 2","author of book 2");
		    books.put(book2.getId(),book2);
		    
	  }
	  
	  private void api(Router router){
		  router.route("/api/books*").handler(BodyHandler.create());
		  router.get("/api/books").handler(this::getAll);
		  router.post("/api/books").handler(this::addOne);
		  router.delete("/api/books/:id").handler(this::deleteOne);
		  
	  }
	  
	  private void getAll(RoutingContext routingContext){
		    routingContext.response()
		    .putHeader("content-type","application/json; charset=utf-8")
		    .end(Json.encodePrettily(books.values()));
	  }
	  
	  private void addOne(RoutingContext context){
		   final Book book=Json.decodeValue(context.getBodyAsString(),Book.class);
		   books.put(book.getId(),book);
		   HttpServerResponse response=context.response();
		   response.setStatusCode(201)
		   .putHeader("content-type","application/json; charset=utf-8")
		   .end(Json.encodePrettily(books.values()));
		   
		   
	  }
	  
	  private void deleteOne(RoutingContext context){
		   String id=context.request().getParam("id");
		   
		   if(id==null){
			   context.response().setStatusCode(400).end();
		   }else{
			   int idAsInteger=Integer.valueOf(id);
			   books.remove(idAsInteger);
			   context.response().setStatusCode(204).end();
		   }
		   
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