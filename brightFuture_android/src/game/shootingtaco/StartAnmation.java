package game.shootingtaco;

import com.badlogic.gdx.backends.android.AndroidApplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.VideoView;

public class StartAnmation extends Activity implements OnCompletionListener,OnTouchListener{
	
	FullScreen vv;
	
	public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        String fileName = "android.resource://"+  getPackageName() +"/"+R.raw.anime;

         vv = (FullScreen) this.findViewById(R.id.surface);
         vv.setVideoURI(Uri.parse(fileName));
         vv.setOnTouchListener(this);
         vv.setOnCompletionListener(this);
         vv.start();

    }
	
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, ShootingTacoAndroid.class);
        startActivity(intent);      
        finish();
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		//System.out.println("111");
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(vv != null){
				vv.stopPlayback();
				Intent intent = new Intent(this, ShootingTacoAndroid.class);
		        startActivity(intent);      
		        finish();
			}
		}
		return false;
	}

}
