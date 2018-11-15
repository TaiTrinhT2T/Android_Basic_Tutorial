package somitsolutions.android.audio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import example.com.soundrecordandanalysis.R;
import somitsolutions.android.audio.base.Function;

import example.com.soundrecordandanalysis.MainActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import ca.uol.aig.fftpack.RealDoubleFFT;


public class SoundRecordAndAnalysisActivity extends Activity implements OnClickListener{
	
	int frequency = 8000;
    int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;


    private RealDoubleFFT transformer;
    int blockSize = 512;
    Button startStopButton;
    boolean started = false;

    RecordAudio recordTask;
    ImageView imageViewDisplaySectrum;
    MyImageView imageViewScale;
    Bitmap bitmapDisplaySpectrum;
   
    Canvas canvasDisplaySpectrum;
    
    
    Paint paintSpectrumDisplay;
    Paint paintScaleDisplay;
    static SoundRecordAndAnalysisActivity mainActivity;
    LinearLayout main;
    MainActivity xm;

    private static final int PERMISSION_REQUEST_CODE = 200;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkPermission()) {
            requestPermission();
        } else {
            xm=new MainActivity();
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
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    xm=new MainActivity();
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
        new AlertDialog.Builder(SoundRecordAndAnalysisActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private class RecordAudio extends AsyncTask<Void, double[], Void> {
    	
    	public int si=0;
    	public double db[];
    	public String myst="";
    	
        @Override
        protected Void doInBackground(Void... params) {
        	
        	if(isCancelled()){
        		return null;
        	}
        //try {
            int bufferSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration, audioEncoding);
                    AudioRecord audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.DEFAULT, frequency,
                    channelConfiguration, audioEncoding, bufferSize);

                    short[] buffer = new short[blockSize];
                    double[] toTransform = new double[blockSize];
                    try{
                    	audioRecord.startRecording();
                    }
                    catch(IllegalStateException e){
                    	Log.e("Recording failed", e.toString());
                    	
                    }
                    try
                    {
                    	Thread.sleep(2000);
                    }
                    
                    catch(Exception ex){}
                    int n=0;
                    while (started && n<6) {
                    	try
                        {
                        	Thread.sleep(20);
                        }
                    	catch(Exception ex){}
                    	
                    	if(isCancelled()){
                    		break;
                    	}
                    	n++;
                    int bufferReadResult = audioRecord.read(buffer, 0, blockSize);

                    for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
                        toTransform[i] = (double) buffer[i] / 32768.0; // signed 16 bit
                        }
                     
                    transformer.ft(toTransform);
                    
                    
                    publishProgress(toTransform);
                    
                    
                    
                    }
                    
                    
                    try{
                    	audioRecord.stop();
                    }
                    catch(IllegalStateException e){
                    	Log.e("Stop failed", e.toString());
                    	
                    }
                    
                    return null;
                    
                    }
        public int getSi()
        {
        	return si;
        }
        public String getMySt()
        {
        	return myst;
        }
        public double[] rtdb()
        {
//        	String s="";
//        	for(int i=0;i<db.length;i++)
//        	{
//        		s+=db[i]+" ";
//        	}
        	//Toastmessage(s);
        	//write("kira.txt",s);
        	return db;
        }
        void Toastmessage(String msg) {
        	  Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        	 }
        
        
        protected void onProgressUpdate(double[]... toTransform) {
        	Log.e("RecordingProgress", "Displaying in progress");
        	
        	si=toTransform[0].length;
            db=toTransform[0];
            
             /////////////////////
            ArrayList<Double> l=new ArrayList<Double>();
            for(int i=0;i<db.length;i++)
            {
            	l.add((100 - db[i]*10));
            }
            try
            {
            myst+=Function.getPitch(l)+"\n"; 
            }
            catch(Exception ex){}
            /////////////////////////
                      
            for (int i = 0; i < toTransform[0].length; i++) {
            int x = i;
            int downy = (int) (100 - (toTransform[0][i] * 10));
            int upy = 100;
            canvasDisplaySpectrum.drawLine(x, downy, x, upy, paintSpectrumDisplay);
            }
            imageViewDisplaySectrum.invalidate();
//            String st="";
//            for(int i=0;i<si;i++)
//            {
//            	st+=(100 - db[i]*10)+"\n ";
//            }
//            write("",st);
         }
        
        }

   
    private String readFile(String filename)
    {
    	String read_data = "";
    	try {
    		String SDCardRoot = Environment.getExternalStorageDirectory()
    		        .getAbsolutePath()
    		        + "/";
    		File file = new File(SDCardRoot + "/kira.txt");
    		
			FileInputStream fIn = new FileInputStream(file);
			BufferedReader myReader = new BufferedReader(
					new InputStreamReader(fIn));
			String aDataRow = "";
			
			while ((aDataRow = myReader.readLine()) != null) {
				read_data += aDataRow + "\n";
			}
			myReader.close();
		} catch (Exception e) {
		}
    	   return read_data;
    }
    public String write(String filename, String data)
    {
    	String path="";
    	try {
    		
    		String SDCardRoot = Environment.getExternalStorageDirectory()
    		        .getAbsolutePath()
    		        + "/";
    		path=SDCardRoot;
    		File file = new File(SDCardRoot + "/kira.txt");
//    	    file.createNewFile();
    	    
    	    FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter myOutWriter = 
									new OutputStreamWriter(fOut);
			myOutWriter.append(data);
			myOutWriter.close();
			fOut.close();
//    	    
//    	     FileOutputStream fos = openFileOutput(SDCardRoot+"/"+filename,
//    	       Context.MODE_PRIVATE);
//    	     fos.write(data.getBytes());
//    	     fos.close();
    	    path=file.getAbsolutePath();
    	     


    		} catch (Exception e) {

    	     e.printStackTrace();
    	    }
    	
    	
    	return path;

    }
    public double amountArray(double s[])
    {
    	double total=0;
    	for(int i=0;i<s.length;i++)
    	{
    		total+=Math.abs(s[i]);
    	}
    	total=total/s.length;
    	return total;
    }
    public double match(double s[], double temp[])
    {
    	double total1=amountArray(s);
    	double total2=amountArray(temp);
    	double ts=(double)0.0;
    	
    	for(int i=0;i<s.length;i++)
    	{
    		ts+=Math.abs(temp[i]-s[i]);
    	}
    	return ts/temp.length;
    }
    public double[] convertString(String st)
    {
    	String ss[]=st.split(" ");
    	double rt[]=new double[ss.length];
    	for(int i=0;i<ss.length;i++)
    	{
    		try
    		{
    			rt[i]=Double.parseDouble(ss[i]);
    		}
    		catch(NumberFormatException ex){
    			rt[i]=(double)0.0;
    		}
    	}
    	return rt;
    }
        public void onClick(View v) {
    
        if (started == true) {
        started = false;
        startStopButton.setText("Start");
        recordTask.cancel(true);
        AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
//        String st="";
//        ArrayList<Double> l=new ArrayList<Double>();
//        for(int i=0;i<recordTask.getSi();i++)
//        {
//        	l.add((100 - recordTask.rtdb()[i]*10));
//        }
//        write("",l+"");
        alertDialog.setMessage(recordTask.getMySt());
        alertDialog.show();
        write("",recordTask.getMySt());
        
        recordTask = null;
        canvasDisplaySpectrum.drawColor(Color.BLACK);
        } else {
        started = true;
        startStopButton.setText("Stop");
        recordTask = new RecordAudio();
        recordTask.execute();
        }  
        
     }
        
        static SoundRecordAndAnalysisActivity getMainActivity(){
        	return mainActivity;
        }
        
       /* public void onStop(){
        	super.onStop();
        	started = false;
            startStopButton.setText("Start");
            if(recordTask != null){
            	recordTask.cancel(true); 
            } 	     		
        }*/
        
        public void onStart(){
        	super.onStart();
        	main = new LinearLayout(this);
        	main.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT));
        	main.setOrientation(LinearLayout.VERTICAL);
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        	
            transformer = new RealDoubleFFT(blockSize);
            
            imageViewDisplaySectrum = new ImageView(this);
            bitmapDisplaySpectrum = Bitmap.createBitmap((int)256,(int)100,Bitmap.Config.ARGB_8888);
            canvasDisplaySpectrum = new Canvas(bitmapDisplaySpectrum);
            paintSpectrumDisplay = new Paint();
            paintSpectrumDisplay.setColor(Color.GREEN);
            imageViewDisplaySectrum.setImageBitmap(bitmapDisplaySpectrum);
            imageViewDisplaySectrum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            main.addView(imageViewDisplaySectrum);
            
            
            imageViewScale = new MyImageView(this);
           
            imageViewScale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            main.addView(imageViewScale);
            
            startStopButton = new Button(this);
            startStopButton.setText("Start");
            startStopButton.setOnClickListener(this);
            startStopButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
           
            main.addView(startStopButton);
          
            setContentView(main);
           
            mainActivity = this;
            
            
        }
        @Override
        public void onBackPressed() {
        	if(recordTask != null){
        		recordTask.cancel(true); 
        	}
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	startActivity(intent);
        }
        //Custom Imageview Class
        @SuppressLint("AppCompatCustomView")
        public class MyImageView extends ImageView {
        	Paint paintScaleDisplay;
        	Bitmap bitmapScale;
        	Canvas canvasScale;
        	public MyImageView(Context context) {
        		super(context);
        		// TODO Auto-generated constructor stub
        		bitmapScale =  Bitmap.createBitmap((int)256,(int)50,Bitmap.Config.ARGB_8888);
        		paintScaleDisplay = new Paint();
        		paintScaleDisplay.setColor(Color.WHITE);
                paintScaleDisplay.setStyle(Paint.Style.FILL);
                
                canvasScale = new Canvas(bitmapScale);
                
                setImageBitmap(bitmapScale);
                
                
        	}
        	@Override
            protected void onDraw(Canvas canvas)
            {
                // TODO Auto-generated method stub
                super.onDraw(canvas);
                canvasScale.drawLine(0, 30, 256, 30, paintScaleDisplay);
                for(int i = 0,j = 0; i<256; i=i+64, j++){
                	for (int k = i; k<(i+64); k=k+8){
                		canvasScale.drawLine(k, 30, k, 25, paintScaleDisplay);
                	}
                	canvasScale.drawLine(i, 40, i, 25, paintScaleDisplay);
                	String text = Integer.toString(j) + " KHz";
                	canvasScale.drawText(text, i, 45, paintScaleDisplay);
                }
                canvas.drawBitmap(bitmapScale, 0, 100, paintScaleDisplay);
                invalidate();
            }
           
        }
    
}
    
