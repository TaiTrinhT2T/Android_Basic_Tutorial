package example.com.logintestpreference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btnlogin;
    private EditText eduser,edpassword;
    private CheckBox check;
    private String preference="pre_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin=(Button) findViewById(R.id.btnlogin);
        eduser =(EditText)findViewById(R.id.editUser);
        edpassword=(EditText)findViewById(R.id.editPassword);
        check=(CheckBox)findViewById(R.id.chksaveacount);
        btnlogin.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        login();
                    }
                });

    }
    public void login()
    {
        finish();
        Intent i=new Intent(this, NewActivity.class);
        i.putExtra("user", eduser.getText().toString());
        startActivity(i);
    }
    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    public void savePreferences()
    {
        SharedPreferences pre=getSharedPreferences("pre_data", MODE_PRIVATE);
        SharedPreferences.Editor editor=pre.edit();
        String user=eduser.getText().toString();
        String pwd=edpassword.getText().toString();
        boolean bchk=check.isChecked();
        if(!bchk)
        {
            editor.clear();
        }
        else
        {
            editor.putString("user", user);
            editor.putString("pwd", pwd);
            editor.putBoolean("checked", bchk);
        }
        editor.commit();
    }

    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences(preference,MODE_PRIVATE);
        boolean ck=pre.getBoolean("checked", false);
        if(ck)
        {
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            eduser.setText(user);
            edpassword.setText(pwd);
        }
        check.setChecked(ck);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
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
}
