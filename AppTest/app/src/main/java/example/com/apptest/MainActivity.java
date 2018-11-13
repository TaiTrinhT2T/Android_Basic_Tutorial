package example.com.apptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt=(Button)findViewById(R.id.button1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(MainActivity.this, HelloActivity.class);
                //intent.putExtra("sv1", "nguyen van b");
                Bundle bdl=new Bundle();
                Abc abc=new Abc("tavistu");
                bdl.putSerializable("a", abc);
                intent.putExtra("bdl", bdl);
                startActivity(intent);

            }
        });
    }
}
