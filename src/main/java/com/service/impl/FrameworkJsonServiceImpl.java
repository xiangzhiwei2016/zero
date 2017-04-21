package com.service.impl;

import javax.inject.Named;

import com.google.gson.Gson;
import com.service.FrameworkJsonService;

@Named("frameworkJsonService")
public class FrameworkJsonServiceImpl implements FrameworkJsonService{

	  private Gson gson;
	  
	  public FrameworkJsonServiceImpl()
	  {
	    this.gson = new Gson();
	  }
	  
	  public String toJson(Object src)
	  {
	    
	      return this.gson.toJson(src);
	    
	  }
}
