package example.com.sms_gochat;



import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class FrameActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);
		 TabHost tabHost = getTabHost();
	        
	        // Tab for Photos
	        TabSpec photospec = tabHost.newTabSpec("SMS");
	        photospec.setIndicator("Photos", getResources().getDrawable(R.drawable.icon_connect));
	        Intent photosIntent = new Intent(this,ConnectActivity.class);
	        photospec.setContent(photosIntent);
	        
	        // Tab for Songs
	        TabSpec songspec = tabHost.newTabSpec("Connect");
	        // setting Title and Icon for the Tab
	        songspec.setIndicator("Songs", getResources().getDrawable(R.drawable.icon_listdanhsach));
	        Intent songsIntent = new Intent(this,ListdanhsachActivity.class);
	        songspec.setContent(songsIntent);
	        
	        // Tab for Videos
	        TabSpec videospec = tabHost.newTabSpec("Setting");
	        videospec.setIndicator("Videos", getResources().getDrawable(R.drawable.icon_sms));
	        Intent videosIntent = new Intent(this,SmsActivity.class);
	        videospec.setContent(videosIntent);
	        
	        // Adding all TabSpec to TabHost
	        tabHost.addTab(photospec); // Adding photos tab
	        tabHost.addTab(songspec); // Adding songs tab
	        tabHost.addTab(videospec); // Adding videos tab
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.frame, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
