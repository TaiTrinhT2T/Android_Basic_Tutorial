package example.com.httptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Do may chu local nen chua sua lạ code duoc
public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button bt;
    EditText name, pwd;
    URL url;
    HttpURLConnection httpurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.iv);


//        String path="http://192.168.56.1:8080/MyWeb/login.do";
//        GetData gt=new GetData();
//        gt.execute(new String[]{path});


    }
    public void getDataHTTP()
    {
        HttpClient client =new DefaultHttpClient();
        HttpGet httpget=new
                HttpGet("http://192.168.56.1:8080/MyShop/login?id=6");
        ResponseHandler<String> handler=new BasicResponseHandler();
        try {
            String content=client.execute(httpget, handler);
            Log.d("thongbao", ""+content);
        } catch (Exception e)
        {
            Log.d("loi" ,"loi: "+e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


    class GetData extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            HttpClient httpclient= new DefaultHttpClient();
            HttpPost httppost=new HttpPost(arg0[0]);
            List<NameValuePair> param=new ArrayList<NameValuePair>(1);
            param.add(new BasicNameValuePair("username","tav"));
            param.add(new BasicNameValuePair("password","xyz"));
            try {

                UrlEncodedFormEntity entity=new UrlEncodedFormEntity(param,"UTF-8");
                httppost.setEntity(entity);
                ResponseHandler<String> handler = new BasicResponseHandler();
                String content = httpclient.execute(httppost, handler);
                return content;
            }catch(Exception e)
            {
                Log.d("loi", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            tv.setText("ban dang dang nhap voi : "+ result);
        }


    }
}
