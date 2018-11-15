package example.com.democontentprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button btnshowallcontact;
    Button btnaccesscalllog;
    Button btnaccessmediastore;
    Button btnaccessbookmarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnshowallcontact=(Button) findViewById(R.id.btnshowallcontact);
        btnshowallcontact.setOnClickListener(this);
        btnaccesscalllog=(Button) findViewById(R.id.btnaccesscalllog);
        btnaccesscalllog.setOnClickListener(this);
        btnaccessmediastore=(Button) findViewById(R.id.btnmediastore);
        btnaccessmediastore.setOnClickListener(this);
        //btnaccessbookmarks=(Button) findViewById(R.id.btnaccessbookmarks);
        //btnaccessbookmarks.setOnClickListener(this);
        if (!checkPermission()) {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        if(v==btnshowallcontact)
        {
            intent=new Intent(this, ShowAllContactActivity.class);
            startActivity(intent);
        }
        else if(v==btnaccesscalllog)
        {
            accessTheCallLog();
        }
        else if(v==btnaccessmediastore)
        {
            accessMediaStore();
        }
        else if(v==btnaccessbookmarks)
        {
            //accessBookmarks();
        }
    }

    public void accessTheCallLog()
    {
        String [] projection=new String[]{
                Calls.DATE,
                Calls.NUMBER,
                Calls.DURATION
        };
        @SuppressLint("MissingPermission") Cursor c=getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                Calls.DURATION+"<?",new String[]{"10"},
                Calls.DATE +" Asc");
        c.moveToFirst();
        String s="";
        while(c.isAfterLast()==false){
            for(int i=0;i<c.getColumnCount();i++){
                s+=c.getString(i)+" - ";
            }
            s+="\n";
            c.moveToNext();
        }
        c.close();
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    /**
     * hàm đọc danh sách các Media trong SD CARD
     */
    public void accessMediaStore()
    {
        String []projection={
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.MIME_TYPE
        };
        CursorLoader loader=new CursorLoader
                (this, Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);
        Cursor c=loader.loadInBackground();
        c.moveToFirst();
        String s="";
        while(!c.isAfterLast()){
            for(int i=0;i<c.getColumnCount();i++){
                s+=c.getString(i)+" - ";
            }
            s+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        c.close();
    }

    // ----- https://developer.android.com/about/versions/marshmallow/android-6.0-changes#behavior-bookmark-browser
    // ----- If your app targets Android 6.0 (API level 23) or higher, don't access bookmarks from the global provider or use the bookmark permissions. Instead, your app should store bookmarks data internally.

//    /**
//     * hàm đọc danh sách Bookmark trong trình duyệt
//     */
//    public void accessBookmarks()
//    {
//        String []projection={
//                Browser.BookmarkColumns.TITLE,
//                Browser.BookmarkColumns.URL,
//        };
//        Cursor c=getContentResolver()
//                .query(Browser.BOOKMARKS_URI, projection,
//                        null, null, null);
//        c.moveToFirst();
//        String s="";
//        int titleIndex=c.getColumnIndex
//                (Browser.BookmarkColumns.TITLE);
//        int urlIndex=c.getColumnIndex
//                (Browser.BookmarkColumns.URL);
//        while(!c.isAfterLast())
//        {
//            s+=c.getString(titleIndex)+" - "+
//                    c.getString(urlIndex);
//            c.moveToNext();
//        }
//        c.close();
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//    }
}
