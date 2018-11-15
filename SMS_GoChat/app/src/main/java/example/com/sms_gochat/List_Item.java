package example.com.sms_gochat;

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

public class List_Item extends BaseAdapter {
	  	private Activity activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    public List_Item(Activity a, ArrayList<HashMap<String, String>> d) {
	        activity = a;
	        data=d;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final  View vi= inflater.inflate(R.layout.new_row, null);
        TextView title = (TextView)vi.findViewById(R.id.tv_ThongBao); // title
        TextView artist = (TextView)vi.findViewById(R.id.tv_Name); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.tv_Time); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        title.setText(song.get(ListdanhsachActivity.KEY_TITLE));
        artist.setText(song.get(ListdanhsachActivity.KEY_ARTIST));
        duration.setText(song.get(ListdanhsachActivity.KEY_DURATION));
        
        if(position%3==0)
        vi.setBackgroundColor(Color.parseColor("#486d93"));
        else
        	if(position%3==2)
        		vi.setBackgroundColor(Color.parseColor("#f0592c"));
        	else
        		vi.setBackgroundColor(Color.parseColor("#419a41"));
        
vi.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
});
		// TODO Auto-generated method stub
return vi;

		
	}
	

}

