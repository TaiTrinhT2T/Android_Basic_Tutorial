package example.com.customizedlistview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys
    static final String KEY_SONG = "song"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_TITLE = "title";
    static final String KEY_ARTIST = "artist";
    static final String KEY_DURATION = "duration";
    static final String KEY_THUMB_URL = "thumb_url";

    ListView list;
    LazyAdapter adapter;
    ArrayList<HashMap<String, String>> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songsList = new ArrayList<HashMap<String, String>>();

        list=(ListView)findViewById(R.id.list);

        // Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, songsList);
        list.setAdapter(adapter);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });

        // we will using AsyncTask during parsing
        new AsyncTaskParseXml().execute();
    }

    // you can make this class as another java file so it will be separated from your main activity.
    public class AsyncTaskParseXml extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            String firstname = "";
                XMLParser parser = new XMLParser();
                String xml = parser.getXmlFromUrl(URL); // getting XML from URL
                Document doc = parser.getDomElement(xml); // getting DOM element

                NodeList nl = doc.getElementsByTagName(KEY_SONG);
                // looping through all song nodes <song>
                for (int i = 0; i < nl.getLength(); i++) {
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    // adding each child node to HashMap key => value
                    map.put(KEY_ID, parser.getValue(e, KEY_ID));
                    map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
                    map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
                    map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
                    map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

                    // adding HashList to ArrayList
                    songsList.add(map);
                }
            return firstname;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            adapter.notifyDataSetChanged();
        }
    }
}
