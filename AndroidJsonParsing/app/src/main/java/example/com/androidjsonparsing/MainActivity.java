package example.com.androidjsonparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tc=(TextView)findViewById(R.id.tv);

        // we will using AsyncTask during parsing
        new AsyncTaskParseJson().execute();
    }

    // you can make this class as another java file so it will be separated from your main activity.
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        //String yourJsonStringUrl = "http://demo.codeofaninja.com/tutorials/json-example-with-php/index.php";
        String yourJsonStringUrl = "http://api.plos.org/search?q=title:%22Drosophila%22%20and%20body:%22RNA%22&fl=id,abstract&wt=json&indent=on";
        // contacts JSONArray


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            String firstname = "";
            try {

                // instantiate our json parser
                JsonParser jParser = new JsonParser();

                // get json string from url
                JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);

                // get the array of users
                JSONObject dataJsonObj = json.getJSONObject("response");
                JSONArray dataJsonArr = dataJsonObj.getJSONArray("docs");

                // loop through all users
                for (int i = 0; i < dataJsonArr.length(); i++) {

                    JSONObject c = dataJsonArr.getJSONObject(i);

                    firstname += (i+1) + " : "+c.getString("id") + "\n";
                    String lastname = c.getString("abstract");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return firstname;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
            tc.setText(strFromDoInBg);
        }
    }
}
