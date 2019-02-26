package com.example.demoopengl;

import android.app.Activity;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class MyGlActivity extends Activity {

    //use Gl surface view
    private GLSurfaceView glSurfaceView;
    private MediaPlayer mp;

    //call back when the activity is started,and initialize the view


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        glSurfaceView = new GLSurfaceView( this ); //allocate Gl surface
        glSurfaceView.setRenderer( new MyGlRenderer( this ) );//use custom renderer
        this.setContentView( glSurfaceView );

        // song
        glSurfaceView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp=MediaPlayer.create( MyGlActivity.this,R.raw.harry_potter_theme );
                mp.start();
            }
        } );
    }

    //call back when the activity is going to background
    @Override
    protected  void onPause(){
        super.onPause();
        glSurfaceView.onPause();

        mp.release();
    }

    //call back after the onpause
    @Override
    protected  void onResume(){
        super.onResume();
        glSurfaceView.onResume();
    }


}
