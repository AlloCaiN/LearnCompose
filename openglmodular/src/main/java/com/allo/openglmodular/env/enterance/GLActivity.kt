package com.allo.openglmodular.env.enterance

import android.app.Activity
import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.TextureView
import com.allo.openglmodular.env.gl.GLCustomSurfaceRender

class GLActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = TextureView(this)
        val render = GLCustomSurfaceRender()
        rootView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                render.queue {
                    render.initProgram()
                    render.bindSurface(surface)
                    render.onSurfaceChanged(width, height)
                    render.onDrawFrame()
                }
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
               return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }

        setContentView(rootView)
    }
}