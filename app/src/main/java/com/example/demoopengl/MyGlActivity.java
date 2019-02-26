package com.example.demoopengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class MyGlActivity extends Activity {

    //use Gl surface view
    private GLSurfaceView glSurfaceView;

    //call back when the activity is started,and initialize the view


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        glSurfaceView = new GLSurfaceView( this ); //allocate Gl surface
        glSurfaceView.setRenderer( new MyGlRenderer( this ) );//use custom renderer
        this.setContentView( glSurfaceView );
    }

    //call back when the activity is going to background
    @Override
    protected  void onPause(){
        super.onPause();
        glSurfaceView.onPause();
    }

    //call back after the onpause
    @Override
    protected  void onResume(){
        super.onResume();
        glSurfaceView.onResume();
    }


}
