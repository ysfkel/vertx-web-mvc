package com.gulfnews;

import com.gulfnews.verticles.App;

import io.vertx.core.Vertx;

public class Startup {
	


    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());


    }

}
