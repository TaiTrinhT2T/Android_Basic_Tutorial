package example.com.luong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView msgWorking;
    Handler handler = new MyHandler(MainActivity.this);
    MyThread th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgWorking = (TextView) findViewById(R.id.TextView01);

    }
    public void setText(String txt)
    {
        msgWorking.setText(txt);
    }
    public void onStart() {
        super.onStart();
        th=new MyThread(handler);
        th.start();
    }

}