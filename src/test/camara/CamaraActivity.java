package test.camara;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CamaraActivity extends Activity implements SurfaceHolder.Callback {
    /** Members */
	private Camera mCamera;
	//private SurfaceView mSurface;
	private NewSurface mSurface;
	private SurfaceHolder mHolder;
	
	private TextView stat,num;
	private Button go,stop,move;
	private EditText editL,editR;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.e("Applications","start Main onCreate");
        
        stat = (TextView) findViewById(R.id.textView1);
        num = (TextView) findViewById(R.id.textView2);
        go = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        move = (Button) findViewById(R.id.button3);
        
        editL = (EditText) findViewById(R.id.editText1);
        editR = (EditText) findViewById(R.id.editText2);
        
        mSurface = (NewSurface) findViewById(R.id.surfaceView1);
        //mSurface = (SurfaceView) findViewById(R.id.surfaceView1);
        
        mHolder = mSurface.getHolder();
        mHolder.addCallback(CamaraActivity.this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

	/** SurfaceView controller part */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
    	
		Log.e("Surface","Surface did Changed as W = " + width + " H = " + height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.e("Surface","Surface being Created");
		mSurface.setWillNotDraw(false);
		mCamera = Camera.open();
		
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			Log.e("IO Exception catch", "Did come into exception");
			mCamera.release();
			mCamera = null;
		}
		
		Camera.Parameters para = mCamera.getParameters();
		para.setPreviewSize(mSurface.getWidth()/2, mSurface.getHeight()/2);
		mCamera.setParameters(para);
		 
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	
	}
	
	/** onClick Implementation*/
	
	public void goClicked(View view){
		mCamera.startPreview();
		
		stat.setText("Go");
		
	}
	
	public void stopClicked(View view){
		mCamera.stopPreview();
		stat.setText("Stop");
		Log.e("NewSurface","count = " + mSurface.count);
	}
	
	public void moveClicked(View view){
		int x,y;
		x = Integer.parseInt("0" + editL.getText().toString());
		y = Integer.parseInt("0" + editR.getText().toString());
		mSurface.walkTo(x, y);
		num.setText("Reached");

	}
    
}


