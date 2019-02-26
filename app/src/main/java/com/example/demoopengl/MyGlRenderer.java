package com.example.demoopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGlRenderer implements GLSurfaceView.Renderer {

    private Photocube cube;
    private static float angelcube=0;//rotational angle in degree of cube
    private static float speedcube=0.5f; // Rotational speed of cube

    //Constructor
    public MyGlRenderer(Context context)
    {
        cube=new Photocube(context); //new
    }

    //call back when the surface is first created or re-created
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor( 0.0f,0.0f,0.0f,1.0f);//set color to clear value is black
        gl.glClearDepthf( 1.0f );
        gl.glEnable( GL10.GL_DEPTH_TEST );
        gl.glDepthFunc( GL10.GL_LEQUAL );
        gl.glHint( GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NICEST );
        gl.glShadeModel( GL10.GL_SMOOTH );
        gl.glDisable( GL10.GL_DITHER );

        //setup texture each time the surface is created
        cube.loadTexture(gl);
        gl.glEnable( GL10.GL_TEXTURE_2D );


    }

    //call back after the create surfaceview
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(height==0)height=1;          // To prevent divided by zero
        float aspect=(float)width/height;

        //set the viewport (display area) to cover the entire window
        gl.glViewport( 0,0,width,height );

        //setup perspective projection with aspect ratio matches viewpoints
        gl.glMatrixMode( GL10.GL_PROJECTION ); //select projection matrix
        gl.glLoadIdentity();                   //reset projection matrix

        // Use perspective projection
        GLU.gluPerspective(gl,45,aspect,0.1f,100.f);

        gl.glMatrixMode( GL10.GL_MODELVIEW );
        gl.glLoadIdentity();

    }
    //call back and draw the current frame
    @Override
    public void onDrawFrame(GL10 gl) {
        //clear color and depth buffer
        gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );

        //Render the cube
        gl.glLoadIdentity();                        // Reset the model view matrix
        gl.glTranslatef( 0.0f,0.0f,-6.0f );  // Translate in to the Screen
        gl.glRotatef( angelcube,0.15f,1.0f,0.3f );// rotated
        cube.draw(gl);
        //Update the rotational angle after the each refresh
        angelcube +=speedcube;


    }
}
