package com.allo.openglmodular.env.enterance

import android.app.Activity
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.view.TextureView
import com.allo.openglmodular.env.gl.GLNativeCustomSurfaceRender

class GLNativeActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = TextureView(this)
        val render = GLNativeCustomSurfaceRender()
        rootView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                render.queue {
                    render.initProgram(this@GLNativeActivity)
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