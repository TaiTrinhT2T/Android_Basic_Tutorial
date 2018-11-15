package example.com.cameratest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    Preview preview;
    Camera camera;
    Context context;
    boolean isShowCamera = false;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        preview = new Preview(this, (SurfaceView)findViewById(R.id.surfaceView));

        preview.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        ((LinearLayout) findViewById(R.id.layout)).addView(preview);

        preview.setKeepScreenOn(true);
        preview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (camera != null && isShowCamera) {
                    camera.takePicture(shutter, raw, jpeg);
                }
            }
        });
        Toast.makeText(context, getString(R.string.take_photo_help), Toast.LENGTH_LONG).show();

    }

    private void showCam(){
        int numCams = Camera.getNumberOfCameras();
        if(numCams > 0){
            try{
                camera = Camera.open(0);
                preview.setCamera(camera);
                camera.startPreview();
            } catch (RuntimeException ex){
                Toast.makeText(context, getString(R.string.camera_not_found),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCam();
    }

    @Override
    protected void onPause() {
        if(camera != null) {
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    private void resetCam() {
        camera.startPreview();
        preview.setCamera(camera);
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
        public void onShutter() {
            Toast.makeText(MainActivity.this,"shutter Callback", Toast.LENGTH_LONG ).show();
        }
    };

    Camera.PictureCallback raw = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Toast.makeText(MainActivity.this,"raw Callback", Toast.LENGTH_LONG ).show();
        }
    };

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            //Lưu file ảnh
            FileOutputStream outStream = null;
            try {
                //Tạo folder tav trong thẻ sdCard
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File (sdCard.getAbsolutePath() + "/tav");
                dir.mkdirs();
                //Tạo tên file và file
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                //Tạo đôí tương FileOutputStream đê ghi ảnh
                outStream = new FileOutputStream(outFile);
                outStream.write(data);
                outStream.close();
                //Cập nhật Gallery
                refreshGallery(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            Toast.makeText(MainActivity.this,"jpeg Callback", Toast.LENGTH_LONG ).show();
            //Khởi động lại Camera
            resetCam();
        }
    };
}
