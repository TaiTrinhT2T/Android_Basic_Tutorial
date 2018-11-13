package example.com.androidsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase database;

    Button create,
            drop,addClass,
            addStudent,searchStudent,
            deleteStudent, updateClass, delCl;

    EditText clcodeEdit, clnameEdit,
            stcodeEdit, stnameEdit, stclEdit, searchStCodeEdit, delStCodeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create=(Button) findViewById(R.id.button1);
        create.setOnClickListener(this);

        drop=(Button) findViewById(R.id.button2);
        drop.setOnClickListener(this);

        addClass=(Button) findViewById(R.id.button3);
        addClass.setOnClickListener(this);

        addStudent=(Button) findViewById(R.id.button4);
        addStudent.setOnClickListener(this);

        searchStudent=(Button) findViewById(R.id.button5);
        searchStudent.setOnClickListener(this);

        deleteStudent=(Button) findViewById(R.id.button6);
        deleteStudent.setOnClickListener(this);

        delCl=(Button) findViewById(R.id.button8);
        delCl.setOnClickListener(this);

        updateClass=(Button) findViewById(R.id.button7);
        updateClass.setOnClickListener(this);

        clcodeEdit=(EditText) findViewById(R.id.editText1);
        clnameEdit=(EditText) findViewById(R.id.editText2);
        stcodeEdit=(EditText) findViewById(R.id.editText3);
        stnameEdit=(EditText) findViewById(R.id.editText4);
        stclEdit=(EditText) findViewById(R.id.editText5);
        searchStCodeEdit=(EditText) findViewById(R.id.editText6);
        delStCodeEdit=(EditText) findViewById(R.id.editText7);


        //createDb();
        //createTbClass();
        //createTbStudent();

        database=openOrCreateDatabase("svcl.db",MODE_PRIVATE,null);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if(v==addClass)
        {
            insertRecordClass();
        }
        else if(v==addStudent)
        {
            loadAllClass();
        }
        else if(v==drop)
        {
            deleteDb();
        }
        else if(v==updateClass)
        {
            updateClName(clcodeEdit.getText().toString(),clnameEdit.getText().toString());
        }
        else if(v==delCl)
        {
            deleteRecordClass(clcodeEdit.getText().toString());
        }
        else if(v==searchStudent)
        {
            insertRecordStudent();
        }
        else if(v==deleteStudent)
        {
            searchStudentByClass(delStCodeEdit.getText().toString());
        }



    }
    public void searchStudentByClass(String clcode)
    {

        Cursor c = database.rawQuery("SELECT * FROM tbclass, tbstudent " +
                "WHERE tbclass.clcode = tbstudent.clcode and tbclass.clcode = '"+clcode+
                "'", null);

        c.moveToFirst();
        String data="";
        while(c.isAfterLast()==false)
        {
            data+=c.getString(0)+"-"+
                    c.getString(1)+"-"+
                    c.getString(2)+"-"+
                    c.getString(3)+"-";
            data+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        c.close();
    }


    public void createDb()
    {
        database=openOrCreateDatabase("svcl.db",MODE_PRIVATE,null);


        Toast.makeText(this, "created", Toast.LENGTH_LONG).show();
    }
    public void updateClName(String clcode,String newClname)
    {
        ContentValues values=new ContentValues();
        values.put("clname", newClname);
        String msg="";
        int ret=database.update("tbclass", values,
                "clcode=?", new String[]{clcode});
        if(ret==0){
            msg="Failed to update";
        }
        else{
            msg="updating is successful";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void deleteDb()
    {
        String sms="";
        if(deleteDatabase("svcl.db")==true)
        {
            sms="Delete database [svcl.db] is successful";
        }
        else
        {
            sms="Delete database [svcl.db] is failed";
        }
        Toast.makeText(this, sms, Toast.LENGTH_LONG).show();
    }


    public void createTbClass()
    {
        String sql="CREATE TABLE tbclass (";
        sql+="clcode TEXT primary key,";
        sql+="clname TEXT)";
        database.execSQL(sql);
    }


    public void createTbStudent()
    {
        String sql="CREATE TABLE tbstudent ("+
                "stcode TEXT PRIMARY KEY ,"+
                "stname TEXT,"+
                "clcode TEXT NOT NULL CONSTRAINT clcode "+
                " REFERENCES tbclass(clcode) ON DELETE CASCADE)";
        database.execSQL(sql);
    }

    public void loadAllClass()
    {
        Cursor c=database.query("tbclass",
                null, null, null, null, null, null);
        c.moveToFirst();
        String data="";
        while(c.isAfterLast()==false)
        {
            data+=c.getString(0)+"-"+
                    c.getString(1)+"-";
            data+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        c.close();
    }
    public void deleteRecordClass(String clcode)
    {
        ContentValues values=new ContentValues();
        values.put("clcode", clcode);
        String msg="";
        int ret=database.delete("tbclass", "clcode = '" + clcode+"'",
                null);
        if(ret==0){
            msg="Failed to delete";
        }
        else{
            msg="deleting is successful";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    public void insertRecordClass()
    {
        ContentValues values=new ContentValues();
        values.put("clcode", clcodeEdit.getText().toString());
        values.put("clname", clnameEdit.getText().toString());
        String msg="";
        if(database.insert("tbclass", null, values)==-1){
            msg="Failed to insert record";
        }
        else{
            msg="insert record is successful";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void insertRecordStudent()
    {
        ContentValues values=new ContentValues();
        values.put("stcode", stcodeEdit.getText().toString());
        values.put("stname", stnameEdit.getText().toString());
        values.put("clcode", stclEdit.getText().toString());
        String msg="";
        if(database.insert("tbstudent", null, values)==-1){
            msg="Failed to insert record";
        }
        else{
            msg="insert record is successful";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
