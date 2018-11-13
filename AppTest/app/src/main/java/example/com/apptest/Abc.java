package example.com.apptest;

import java.io.Serializable;

public class Abc implements Serializable{
	private String name;
	
	public Abc(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}

}
