package test.camara;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class NewSurface extends SurfaceView {
	/** Members*/
	public int count = 0;
	private final int speed = 20;
	
	private float x,y;
	private float dest_x,dest_y;
	
	/**Constructors*/
	public NewSurface(Context context){
		super(context);
		Log.e("Custom","Stange Constructor !!!");
	}
	
	public NewSurface(Context context, AttributeSet attr){
		super(context,attr);
		x = 200;
		y = 200;
		dest_x = 200;
		dest_y = 200;
		
		this.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int tX,tY;
				tX = (int) event.getRawX();
				tY = (int) event.getRawY();
				NewSurface.this.walkTo(tX,tY);
				Log.e("Listener","Touched Down @ "+tX+" : "+tY);
				return false;
			}
			
		});
		
		Log.e("Custom","New Object Created");
	}
	
	// set destination
	public void walkTo(int _dest_x,int _dest_y){
		dest_x = (_dest_x>this.getWidth())?this.getWidth():_dest_x;
		dest_y = (_dest_y>this.getHeight())?this.getHeight():_dest_y;
		invalidate();
		Log.e("walk","Start walking");
	}
	
	@Override
	public void onDraw(Canvas canvas){
		Paint paint = new Paint();
		paint.setColor(Color.CYAN);
		paint.setAlpha(127);
		canvas.drawCircle(x,y,90,paint);
		Log.e("Custom","onDraw Called at "+ count);
		count = count + 1;
		if(Math.abs(dest_x-x) > 10 || Math.abs(dest_y-y) > 100){
			// move one step per frame
			float move_x = (float) ((dest_x - x)/Math.sqrt(Math.pow((dest_x-x),2.0)+Math.pow((dest_y-y),2.0))*speed);
			float move_y = (float) ((dest_y - y)/Math.sqrt(Math.pow((dest_x-x),2.0)+Math.pow((dest_y-y),2.0))*speed);
			x = x + move_x;
			y = y + move_y;
			Log.e("Walk","Walking "+move_x+" : "+move_y);
			invalidate();
		} else {
			Log.e("walk", "Done ! @"+x+" : "+y);
		}
		
	}
	
}