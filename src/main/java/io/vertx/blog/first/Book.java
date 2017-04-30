package io.vertx.blog.first;

import java.util.concurrent.atomic.AtomicInteger;

public class Book {
	
	private static final AtomicInteger COUNTER=new AtomicInteger();
	
	private final int id;
	
	private String name;
	
	private String author;
	
	public Book(String name,String author){
		this.id=COUNTER.getAndIncrement();
		this.name=name;
		this.author=author;
	}
	
	public int getId(){
		return this.id;
	}
	public Book(){
		this.id=COUNTER.getAndIncrement();
	}
	
	public String getName(){
		return name;
	}
	
	public String getAuthor(){
		return author;
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setAuthor(String author){
		this.author=author;
	}
	

}
