package example.com.contentproviderex;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Provider.MyContent;

public class MainActivity extends AppCompatActivity {

    TextView tvStt, tvName, tvGrade, tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStt = findViewById(R.id.tv_stt);
        tvName = findViewById(R.id.tv_name);
        tvGrade = findViewById(R.id.tv_grade);
        tvId = findViewById(R.id.tv_id);

        showAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickAddName(View view){
        ContentValues values = new ContentValues();

        values.put(MyContent.NAME, ((EditText) findViewById(R.id.txtName)).getText().toString());
        values.put(MyContent.GRADE, ((EditText) findViewById(R.id.txtGrade)).getText().toString());

        Uri uri = getContentResolver().insert(MyContent.CONTENT_URI, values );

        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();

        showAll();
    }

    public void showAll(){
        String stt = "";
        String name = "";
        String grade = "";
        String id = "";
        String URI = "content://com.provider.example/students";
        Uri students = Uri.parse(URI);
        Cursor c =  managedQuery(students, null, null, null, "id");
        int i = 1;
        if(c.moveToFirst()){
            do{
                Toast.makeText(this, c.getString(c.getColumnIndex(MyContent.ID)) +
                        ", " + c.getString(c.getColumnIndex(MyContent.NAME)) +
                        ", " + c.getString(c.getColumnIndex(MyContent.GRADE)), Toast.LENGTH_LONG).show();

                stt += i + "\n";
                name += c.getString(c.getColumnIndex(MyContent.NAME)) + "\n";
                grade += c.getString(c.getColumnIndex(MyContent.GRADE)) + "\n";
                id += c.getString(c.getColumnIndex(MyContent.ID)) + "\n";
                i++;
            }while (c.moveToNext());
        }

        tvId.setText(id);
        tvStt.setText(stt);
        tvName.setText(name);
        tvGrade.setText(grade);
    }

    public void onClickRetrieveStudents (View view){
        String URI = "content://com.provider.example/students";
        Uri students = Uri.parse(URI);
        Cursor c =  managedQuery(students, null, null, null, "name");
        if(c.moveToFirst()){
            do{
                Toast.makeText(this, c.getString(c.getColumnIndex(MyContent.ID)) +
                        ", " + c.getString(c.getColumnIndex(MyContent.NAME)) +
                        ", " + c.getString(c.getColumnIndex(MyContent.GRADE)), Toast.LENGTH_LONG).show();
            }while (c.moveToNext());
        }

        showAll();
    }
}
