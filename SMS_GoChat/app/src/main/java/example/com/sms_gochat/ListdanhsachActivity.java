package example.com.sms_gochat;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class ListdanhsachActivity extends Activity implements OnQueryTextListener {
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	SearchView searchView;
	
	ListView list;
    List_Item adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listdanhsach);
		  ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();


			for (int i = 0; i < 10; i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(KEY_ID, "dapter");
				map.put(KEY_TITLE, "Than Bang");
				map.put(KEY_ARTIST, "Nguyễn Thúy");
				map.put(KEY_DURATION, "5:30");
				map.put(KEY_THUMB_URL, "");

				// adding HashList to ArrayList
				songsList.add(map);
			}
			

			list=(ListView)findViewById(R.id.list);
			
			// Getting adapter by passing xml data ArrayList
	        adapter=new List_Item(this, songsList);        
	        list.setAdapter(adapter);
	        

	        // Click event for single list row
	        list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
						Intent it=new Intent(ListdanhsachActivity.this,InfoActivity.class);		
						startActivity(it);
				}
			});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listdanhsach, menu);
		searchView = (SearchView) menu.findItem(R.id.action_search_mm).getActionView();
		
		Log.d("abc", searchView+"");
        searchView.setOnQueryTextListener(this);
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

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
