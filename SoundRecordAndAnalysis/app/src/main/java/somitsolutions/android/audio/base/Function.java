package somitsolutions.android.audio.base;

import java.util.ArrayList;
import java.util.Hashtable;

public class Function {
	
	public static int COUNT_SAMPLE=512;
	public static int FREQUENCY_REAL=4000;
	public static int FREQUENCY_MAX=300;
	public static int FREQUENCY_MIN=50;
	
	public static double getStep()
	{
		return (double)FREQUENCY_REAL/(double)COUNT_SAMPLE;
	}
	public static int getMaxIndex()
	{
		return ((int)Math.round(((double)FREQUENCY_MAX/getStep()))+1);
	}
	public static int getMinIndex()
	{
		return (int)Math.round(((double)FREQUENCY_MIN/getStep()))-1;
	}
	
	public static ArrayList<Point> getNumber(int num, ArrayList<Double> ar, int maxIndex, int minIndex)
	{
		int indexTmp=minIndex;
		double valueTmp=ar.get(minIndex);
		Hashtable<Integer, Double> list=new Hashtable<Integer, Double>();
		ArrayList<Point> lPoint=new ArrayList<Point>();
		
		for(int j=0; j<num;j++)
		{
		for(int i=minIndex;i<maxIndex;i++)
		{
			if((ar.get(i)>valueTmp) && (!list.contains(ar.get(i))))
			{
				indexTmp=i;
				valueTmp=ar.get(i);
			}
				
		}

		Point p=new Point(indexTmp, valueTmp);
		lPoint.add(p);
		list.put(indexTmp, valueTmp);
		indexTmp=minIndex;
		valueTmp=ar.get(minIndex);
		}

		
		return lPoint;
	}

	public  static int checkMax(int index, int n, ArrayList<Double> ar)
	{
		int check=1;
		for(int i=index-n;i<index+n;i++)
		{
			if(i!=index && (ar.get(i)>ar.get(index)))
				check=0;
				
		}
		return check;
	}
	
	public  static int checkFre(int index, int n, ArrayList<Double> ar, int size)
	{
		int sum=0;
		for(int i=1;i<size;i++)
		{
			int m=checkMax(index*i, n, ar)+checkMax(index*i, n-2, ar)+checkMax(index*i, n-2, ar);
			if(m>0) m=1;
			sum+=m;
		}
		return sum;
	}
	public static double getPitch(ArrayList<Double> ar)
	{
		ArrayList<Point>  list=getNumber(6, ar, Function.getMaxIndex(), 0);
		int id=0;
		int sum=checkFre(list.get(0).getKey(), 5, ar,  5);
		for(int i=1;i<list.size();i++)
		{
			if((checkFre(list.get(i).getKey(), 5, ar,  5)>sum) || (checkFre(list.get(i).getKey(), 5, ar,  5)==sum && list.get(i).getKey()<list.get(id).getKey()))
			{
				id=i;
				sum=checkFre(list.get(i).getKey(), 5, ar,  5);
			}
		}
		return (list.get(id).getKey()+0.5)*Function.getStep();
	}

}
