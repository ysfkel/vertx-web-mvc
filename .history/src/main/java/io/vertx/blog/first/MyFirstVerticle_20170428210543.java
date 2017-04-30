

package io.vertx.blog.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;

public class MyFirstVerticle extends AbstractVerticle{

  @Override
  public void start(Future<Void> future){
        vertx.createHttpServer()
        .requestHandler(request->{
             
             HttpServerResponse response=request.response();
             response.putHeader("content-type","text/plain");
             request.response().end("<h1>HELLO VERTEX</h1>");
        })
        .listen(8080,res->{
             if(res.succeeded()){
               future.complete();
             }else{
               future.fail(res.cause());
             }
        });
  }
}