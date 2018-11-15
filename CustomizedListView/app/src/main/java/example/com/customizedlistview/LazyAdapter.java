package example.com.customizedlistview;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	final  View vi= inflater.inflate(R.layout.row_layout, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        title.setText(song.get(MainActivity.KEY_TITLE));
        artist.setText(song.get(MainActivity.KEY_ARTIST));
        duration.setText(song.get(MainActivity.KEY_DURATION));
        
        if(position%3==0)
        vi.setBackgroundColor(Color.parseColor("#486d93"));
        else
        	if(position%3==2)
        		vi.setBackgroundColor(Color.parseColor("#f0592c"));
        	else
        		vi.setBackgroundColor(Color.parseColor("#419a41"));
        vi.setOnTouchListener(new View.OnTouchListener() {
			
        	@Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                	vi.setBackgroundColor(Color.parseColor("#00592c"));
                else if (event.getAction() == MotionEvent.ACTION_UP)
                	vi.setBackgroundColor(Color.parseColor("#419a41"));
                return false;
            }
		});
        //imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}