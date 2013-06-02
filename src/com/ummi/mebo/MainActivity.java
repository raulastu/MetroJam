package com.ummi.mebo;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.metrojam.R;
import com.huahcoding.metrojam.BackTrackActivity;

//angel hack 2013 go!

public class MainActivity extends Activity implements OnClickListener, SurfaceHolder.Callback {
	MediaRecorder recorder;
	SurfaceHolder holder;

	Button recordButton;

	boolean recording = false;
	public static final String TAG = "com.ummi.mebo2";

	String finalVideoDestination;

	//
	// private Handler mHandler = new Handler();
	// private Runnable runnable = new BackgroundTask();

	class BackgroundTask extends AsyncTask<Void,Void,Void> {

//		@Override
//		protected Object doInBackground(Object... arg0) {
//			Log.v(TAG, "background FTP send empezó la enviada po!");
//			Util.sendFTPFile(finalVideoDestination);
//			return null;
//		}
		@Override
		protected Void doInBackground(Void... arg0) {
			Log.v(TAG, "background FTP send empezó la enviada po!");
			Util.sendFTPFile(finalVideoDestination);
			return null;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		recorder = new MediaRecorder();
		initRecorder();

		SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceView1);
		// android.view.ViewGroup.LayoutParams lp = cameraView.getLayoutParams();
		// lp.

		// cameraView.set
		// cameraView.set
		holder = cameraView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

		recordButton = (Button) this.findViewById(R.id.button1);
		recordButton.setOnClickListener(this);
	}

	private void initRecorder() {
		recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

		CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
		recorder.setProfile(cpHigh);

		// finalVideoDestination =
		// Environment.getExternalStorageDirectory().getPath() +
		// "/angelhack88.mp4";
		finalVideoDestination = getFilesDir() + "/angelhack88.mp4";
		Log.v(TAG, "lugar=" + finalVideoDestination);
		recorder.setOutputFile(finalVideoDestination);

		recorder.setMaxDuration(30000); // 15 seconds only video
		recorder.setMaxFileSize(2000000); // 1 mbyte limit video size
	}

	private void prepareRecorder() {
		// holder.lockCanvas().rotate(90);
		recorder.setPreviewDisplay(holder.getSurface());

		// set low quality video
		recorder.setAudioChannels(1);
		recorder.setVideoFrameRate(15);
		recorder.setVideoSize(640, 480);
		recorder.setAudioSamplingRate(8000);
		recorder.setAudioEncodingBitRate(8000);
		recorder.setVideoEncodingBitRate(250000);

		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			finish();
		} catch (IOException e) {
			e.printStackTrace();
			finish();
		}
	}

	public void onClick(View v) {
		if (recording) {
			recorder.stop();
			recording = false;
			Log.v(TAG, "Recording Stopped");
			recordButton.setText("***");
			//recordButton.setText("Press here to record again");
			Toast.makeText(MainActivity.this, "sending message!", Toast.LENGTH_LONG).show();
			
			//get readt ti seb abitge ibe
			//initRecorder();
			//prepareRecorder();
			// now try to send your recording using FTP
			//mHandler.postDelayed(runnable, 0);
			new BackgroundTask().execute();

		} else {
			recording = true;
			recorder.start();
			recordButton.setText("Press here to stop recording");
			Toast.makeText(MainActivity.this, "recording started!", Toast.LENGTH_LONG).show();
			Log.v(TAG, "Recording Started");
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		prepareRecorder();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {

		if (recording) {
			recorder.stop();
			recording = false;
		}
		recorder.release();
		finish();
	}

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_action_menu, menu);
        return true;
    }
    
    @Override
      public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_create_route:
            
//          spam();
            
//        Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT)
//            .show();
          Intent intent = new Intent(this, BackTrackActivity.class);
          startActivity(intent);
          break;
        case R.id.action_refresh:
//        Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
//            .show();
          break;
        default:
          break;
        }
        return true;
      } 
    

}
