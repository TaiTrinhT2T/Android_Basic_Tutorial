package example.com.luong;

import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler{
	MainActivity activity;
	
	public MyHandler(MainActivity activity)
	{
		this.activity=activity;
	}
	@Override
    public void handleMessage(Message msg) {
    	String txt = (String)  msg.obj;
    	activity.setText(txt);
    }

}
