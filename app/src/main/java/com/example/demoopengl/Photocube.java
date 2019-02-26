package com.example.demoopengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Photocube {

    private FloatBuffer vertexBuffer; //vertext buffer
    private FloatBuffer texBuffer;    // texture coords buffer

    private int numFace = 6;
    private int[] imageFileIds = {
            R.drawable.pic1,
            R.drawable.pics2,
            R.drawable.pics3,
            R.drawable.pics4,
            R.drawable.pics5,
            R.drawable.pics6,};

    private int[] textureIDs = new int[numFace];
    private Bitmap[] bitmaps = new Bitmap[numFace];
    private float cubehalfsize = 1.2f;

    //Constructor
    public Photocube(Context context) {
        // allocate vertex buffer ,an float has 4 byte
        ByteBuffer vbb = ByteBuffer.allocateDirect( 12 * 4 * numFace );
        vbb.order( ByteOrder.nativeOrder() );
        vertexBuffer = vbb.asFloatBuffer();


        //read the images. find the aspect ratio and adjust the vertices accordingly
        for (int face = 0; face < numFace; face++) {
            bitmaps[face] = BitmapFactory.decodeStream( context.getResources().openRawResource( imageFileIds[face] ) );
            int imawidth = bitmaps[face].getWidth();
            int imaheight = bitmaps[face].getHeight();
            float facewidth = 2.0f;
            float faceheight = 2.0f;

            //adjust aspect ratio
            if (imawidth > imaheight) {
                faceheight = faceheight * imaheight / imawidth;
            } else {
                facewidth = facewidth * imawidth / imaheight;
            }

            float faceLeft = -facewidth / 2;
            float faceRight = -faceLeft;
            float faceTop = faceheight / 2;
            float faceBottom = -faceTop;

            // define the vertices for face

            float[] vertices = {faceLeft, faceBottom, 0.0f, //left-bottom-front
                    faceRight, faceBottom, 0.0f, //right-bottom-front
                    faceLeft, faceTop, 0.0f,    //left-top-front
                    faceRight, faceTop, 0.0f    //right-top-front
            };
            vertexBuffer.put( vertices ); //Populate

        }

        vertexBuffer.position( 0 );

        //allocate texturebuffer an float for 4 bytes and 4 time repeat

        float[] texCoords = {0.0f, 1.0f,//left-bottom
                1.0f, 1.0f,//right-bottom
                0.0f, 0.0f,//left-top
                1.0f, 0.0f,//right-top
        };

        ByteBuffer tbb = ByteBuffer.allocateDirect( texCoords.length * 4 * numFace );
        tbb.order( ByteOrder.nativeOrder() );
        texBuffer = tbb.asFloatBuffer();
        for (int face = 0; face < numFace; face++) {
            texBuffer.put( texCoords );
        }
        texBuffer.position( 0 );//revind

    }

    // Render the shap
    public void draw(GL10 gl) {
        gl.glFrontFace( GL10.GL_CCW );
        gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
        gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
        gl.glVertexPointer( 3, GL10.GL_FLOAT, 0, vertexBuffer );
        gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 0, texBuffer );

        //front
        gl.glPushMatrix();
        //gl.glRotatef( 270.0f,0f,1f,0f );
        gl.glTranslatef( 0f, 0f, cubehalfsize );
        gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[0] );
        gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 0, 4 );
        gl.glPopMatrix();

        //left
        gl.glPushMatrix();
        gl.glRotatef( 270.0f, 0f, 1f, 0f );
        gl.glTranslatef( 0f, 0f, cubehalfsize );
        gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[1] );
        gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 4, 4 );
        gl.glPopMatrix();

        //back
        gl.glPushMatrix();
        gl.glRotatef( 180.0f, 0f, 1f, 0f );
        gl.glTranslatef( 0f, 0f, cubehalfsize );
        gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[2] );
        gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 8, 4 );
        gl.glPopMatrix();

        //right
        gl.glPushMatrix();
        gl.glRotatef( 90.0f, 0f, 1f, 0f );
        gl.glTranslatef( 0f, 0f, cubehalfsize );
        gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[3] );
        gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 12, 4 );
        gl.glPopMatrix();

        //top
        gl.glPushMatrix();
        gl.glRotatef( 270.0f, 0f, 1f, 0f );
        gl.glTranslatef( 0f, 0f, cubehalfsize );
        gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[4] );
        gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 16, 4 );
        gl.glPopMatrix();

        //bottom
        gl.glPushMatrix();
        gl.glRotatef( 90.0f, 0f, 1f, 0f );
        gl.glTranslatef( 0f, 0f, cubehalfsize );
        gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[5] );
        gl.glDrawArrays( GL10.GL_TRIANGLE_STRIP, 20, 4 );
        gl.glPopMatrix();


        gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
        gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );


    }
    // load  images in  6 GL texture
    public void loadTexture(GL10 gl) {
//generate texture ids
        gl.glGenTextures( 6,textureIDs,0 );

        // Generate openGl texture images
        for (int face = 0; face <numFace; face++) {
        gl.glBindTexture( GL10.GL_TEXTURE_2D,textureIDs[face] );
        gl.glTexParameterf( GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST );
        gl.glTexParameterf( GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);

        //build texture from loaded bitmap for the currently texture ids

            GLUtils.texImage2D( GL10.GL_TEXTURE_2D,0,bitmaps[face],0 );
            bitmaps[face].recycle();


        }
    }
}
