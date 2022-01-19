package com.allo.openglmodular.env.gl
import android.content.Context
import android.opengl.GLSurfaceView
import com.allo.openglmodular.R
import com.allo.openglmodular.env.glnative.GLNativeRender
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLNativeSurfaceRender(val context: Context): GLSurfaceView.Renderer {


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexCode = ShaderUtils.loadFromRawFile(R.raw.sample_vertex, context.resources) ?:return
        val fragmentCode = ShaderUtils.loadFromRawFile(R.raw.sample_fragment, context.resources) ?:return
        GLNativeRender.loadGLProgram(vertexCode,fragmentCode)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
       GLNativeRender.onViewportChanged(width.toFloat(),height.toFloat())
    }

    override fun onDrawFrame(gl: GL10?) {
        GLNativeRender.OnDraw()
    }


}