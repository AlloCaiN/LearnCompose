package com.allo.openglmodular.env.gl

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLES30
import android.opengl.Matrix
import com.allo.openglmodular.R
import com.allo.openglmodular.env.gl.env.GLThread
import com.allo.openglmodular.env.shader.GLShader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*

class GLCustomSurfaceRender {
    private val glThread: GLThread = GLThread(hashCode())
    private var renderProgram: Int = 0
    private val POSITION_VERTEX =  floatArrayOf(0.0f, 0.5f, 0.0f, // top
        -0.5f, -0.5f, 0.0f, // bottom left
        0.5f, -0.5f, 0.0f)  // bottom right
    var modelMatrix : FloatArray? = FloatArray(16)
    var vertexBuffer = ByteBuffer.allocateDirect(POSITION_VERTEX.size * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(POSITION_VERTEX)
        .position(0)

    fun initProgram(context : Context) {
        context.let {
            renderProgram = ShaderUtils.loadShaderFromRaw(
                R.raw.sample_vertex,
                R.raw.sample_fragment, it.resources
            )
        }

    }

    fun initProgram() {
        renderProgram = ShaderUtils.loadShader(
          GLShader.sample_vertex,GLShader.sample_fragment
        )
        queue {
            surfaceTexture = SurfaceTexture(EGLUtil.genTexture())
        }
    }



    fun queue(callback : () -> Unit) {
        glThread.queue {
            callback.invoke()
        }
    }
    fun bindSurface(surface : Any) {
        glThread.bindSurface(surface)
    }

    fun onSurfaceChanged( width: Int, height: Int) {
        GLES30.glViewport(0,0,width,height)
    }
    var surfaceTexture : SurfaceTexture? = null
    fun acquireTexture() : SurfaceTexture? {
        return surfaceTexture
    }
    var rotate = 0f
    fun calculateMatrix() {
        Matrix.setIdentityM(modelMatrix,0)
        Matrix.rotateM(modelMatrix,0,rotate,1f,0f,1f)
        rotate += 1
    }

    fun startRender() {
        glThread.startTimerRender({
            onDrawFrame()
        },0)
    }

    fun onDrawFrame() {
        calculateMatrix()
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        GLES30.glUseProgram(renderProgram)
        GLES30.glEnableVertexAttribArray(0)
        val transformLoc = GLES30.glGetUniformLocation(renderProgram, "transform")
        GLES30.glUniformMatrix4fv(transformLoc, 1, false, modelMatrix, 0)
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 3)
        GLES30.glDisableVertexAttribArray(0)
        glThread.renderImpl()
        startRender()
    }
}