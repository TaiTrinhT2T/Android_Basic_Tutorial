package somitsolutions.android.audio.base;


public class Point {
	
	private int key;
	private double value;
	
	public Point(int key, double value)
	{
		this.key=key;
		this.value=value;
	}
	public void setValue(int key, double value)
	{
		this.key=key;
		this.value=value;
	}
	public int getKey()
	{
		return key;
	}
	public double getValue()
	{
		return value;
	}

}
