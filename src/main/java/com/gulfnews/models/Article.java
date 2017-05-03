package com.gulfnews.models;

public class Article {
	
	private String title;
	private String body;
	
	
	public Article setTitle(String title){
		this.title=title;
		return this;
	}
	
	public Article setBody(String body){
		this.body=body;
		return this;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getBody(){
		return body;
	}

}
