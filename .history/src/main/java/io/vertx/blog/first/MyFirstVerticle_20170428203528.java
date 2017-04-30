// package io.vertx.blog.first;

// import io.vertx.core.AbstractVerticle;
// import io.vertx.core.Future;

// public class MyFirstVerticle extends AbstractVerticle {

//   @Override
//   public void start(Future<Void> fut) {
//     vertx
//         .createHttpServer()
//         .requestHandler(r -> {
//           r.response().end("<h1>Hello from my first " +
//               "Vert.x 3 application</h1>");
//         })
//         .listen(8080, result -> {
//           if (result.succeeded()) {
//             fut.complete();
//           } else {
//             fut.fail(result.cause());
//           }
//         });
//   }
// }

package io.vertx.blog.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MyFirstVerticle extends AbstractVerticle{

  @Override
  public void start(Future<Void> future){
        vertx.createHttpServer()
        .requestHandler(request->{
             
             HttpServerResponse response=request.response();
             response.putHeader("content-type","text/plain");
             response.end("<h1>HELLO VERTEX</h1>");
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