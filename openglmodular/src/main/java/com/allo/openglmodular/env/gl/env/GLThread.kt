package com.allo.openglmodular.env.gl.env

import android.opengl.EGL14
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.allo.openglmodular.env.gl.EGLUtil

class GLThread(id: Int) {
    // 线程
    private var threadHandler : HandlerThread = HandlerThread("GLThread$id")
    private var thread : Handler?
    // gl环境
    private var glEnv : GLEnv? = null
    init {
        threadHandler.start()
        thread = Handler(threadHandler.looper)
        thread?.post {
            glEnv = EGLUtil.eglBuildEnv()
        }
    }


    fun queue(runnable: (() -> Unit)) {
        thread?.post {
            runnable.invoke()
        }
    }


    fun bindSurface(surface : Any) {
        val enc = glEnv?:return
        EGLUtil.eglCreateSurface(enc.eglDisplay,surface).let {
            Log.e("czf","surface is $it and ${EGL14.EGL_NO_SURFACE}" )
            enc.eglSurface =it
            EGL14.eglMakeCurrent(enc.eglDisplay,it,it,enc.eglContext)
        }
    }

    fun renderImpl() {
        // 错在了一起执行
        val enc = glEnv?:return
        val surface = enc.eglSurface
        EGL14.eglSwapBuffers(enc.eglDisplay,surface)
    }
}