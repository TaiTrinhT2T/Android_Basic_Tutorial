package example.com.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_layout);
//		TextView tv = (TextView)findViewById(R.id.abc);
//		
//		Intent itent=getIntent();
//		Bundle db=itent.getBundleExtra("bdl");
//		
//		Abc sinhvien = (Abc)db.getSerializable("a");
//		tv.setText(sinhvien.getName());
		
		final Button bt=(Button)findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				bt.setText("ok");
			}
		});
		
	}

}
