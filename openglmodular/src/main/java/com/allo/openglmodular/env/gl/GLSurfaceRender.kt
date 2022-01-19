package com.allo.openglmodular.env.gl

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView

import com.allo.openglmodular.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 使用GlSurfaceView来搭建环境，渲染脚本直接使用原生
 */
class GLSurfaceRender(val context : Context) : GLSurfaceView.Renderer{
    private val POSITION_VERTEX =  floatArrayOf(0.0f, 0.5f, 0.0f, // top
        -0.5f, -0.5f, 0.0f, // bottom left
        0.5f, -0.5f, 0.0f)  // bottom right

    var renderProgram = -1
    var vertexBuffer = ByteBuffer.allocateDirect(POSITION_VERTEX.size * 4)
                        .order(ByteOrder.nativeOrder())
                        .asFloatBuffer()
                        .put(POSITION_VERTEX)
                        .position(0)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        renderProgram = ShaderUtils.loadShaderFromRaw(
            R.raw.sample_vertex,
            R.raw.sample_fragment,
            context.resources
        )
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0,0,width,height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        GLES30.glUseProgram(renderProgram)
        GLES30.glEnableVertexAttribArray(0)
        GLES30.glVertexAttribPointer(0,3,GLES30.GL_FLOAT,false,0,vertexBuffer)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP,0,3)
        GLES30.glDisableVertexAttribArray(0)
    }
}