package example.com.luong;

import android.os.Handler;
import android.os.Message;

public class MyThread extends Thread{
	
	Handler handler;
	public MyThread(Handler handler)
	{
		this.handler=handler;
	}
	
	 public void run() {
         try {
             for (int i = 0; i < 60 ; i++) {
                 Thread.sleep(1000);
                 String data="gia tri "+i;
                 Message msg = handler.obtainMessage(1, (String) data);
                 handler.sendMessage(msg);
             }
         } catch (Throwable t) {}
     }

}
