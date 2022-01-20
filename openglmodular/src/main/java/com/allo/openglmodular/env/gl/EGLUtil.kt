package com.allo.openglmodular.env.gl

import android.graphics.Bitmap
import android.opengl.*
import com.allo.openglmodular.env.gl.env.GLEnv
import java.nio.ByteBuffer

object EGLUtil {
    private val es3Config = intArrayOf(
        EGL14.EGL_RED_SIZE, 8,
        EGL14.EGL_GREEN_SIZE, 8,
        EGL14.EGL_BLUE_SIZE, 8,
        EGL14.EGL_ALPHA_SIZE, 8,
        EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT,
        EGL14.EGL_RENDERABLE_TYPE,
        EGL14.EGL_OPENGL_ES2_BIT or EGLExt.EGL_OPENGL_ES3_BIT_KHR,
        0x3142, 1,
        EGL14.EGL_NONE)

    fun eglBuildEnv(): GLEnv {
        val display = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        if (display == EGL14.EGL_NO_DISPLAY) {
            throw RuntimeException("unable to get egl14 display")
        }
        val version = IntArray(2)
        if (!EGL14.eglInitialize(display,version,0,version,1)) {
            throw RuntimeException("unable to initialize egl14")
        }

        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(display, es3Config,0,configs,0,configs.size,numConfigs,0)
        val attribList = intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION,3,EGL14.EGL_NONE)

        val eglContext = EGL14.eglCreateContext(display,configs[0],EGL14.EGL_NO_CONTEXT,attribList,0)
        EGL14.eglMakeCurrent(display,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_SURFACE,eglContext)
        ShaderUtils.checkGLError("eglBuildEnv")
        return GLEnv(display,eglContext,EGL14.EGL_NO_SURFACE)
    }

    fun eglBuildEnv(surface : Any): GLEnv {
        val display = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        if (display == EGL14.EGL_NO_DISPLAY) {
            throw RuntimeException("unable to get egl14 display")
        }
        val version = IntArray(2)
        if (!EGL14.eglInitialize(display,version,0,version,1)) {
            throw RuntimeException("unable to initialize egl14")
        }

        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(display, es3Config,0,configs,0,configs.size,numConfigs,0)
        val attribList = intArrayOf(EGL14.EGL_CONTEXT_CLIENT_VERSION,3,EGL14.EGL_NONE)
        val eglContext = EGL14.eglCreateContext(display,configs[0],EGL14.EGL_NO_CONTEXT,attribList,0)
        val surfaceAttribs = intArrayOf(EGL14.EGL_NONE)
        val mEGLSurface =  EGL14.eglCreateWindowSurface(display, configs[0], surface, surfaceAttribs, 0)
        if (mEGLSurface != null) {
            EGL14.eglMakeCurrent(display, mEGLSurface, mEGLSurface, eglContext)
        }
        return GLEnv(display,eglContext,mEGLSurface)
    }

    // 创建eglsurface
    fun eglCreateSurface(mEGLDisplay: EGLDisplay, mSurface: Any?): EGLSurface {
        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(mEGLDisplay, es3Config, 0, configs, 0, configs.size, numConfigs, 0)
        return EGL14.eglCreateWindowSurface(mEGLDisplay, configs[0], mSurface, intArrayOf(EGL14.EGL_NONE), 0)
    }

    fun saveTexture(texture: Int, width: Int, height: Int): Bitmap {
        val frame = IntArray(1)
        GLES30.glGenFramebuffers(1, frame, 0)
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, frame[0])
        GLES30.glFramebufferTexture2D(GLES30.GL_FRAMEBUFFER, GLES30.GL_COLOR_ATTACHMENT0, GLES30.GL_TEXTURE_2D, texture, 0)
        val buffer = ByteBuffer.allocate(width * height * 4)
        GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(buffer)
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, 0)
        GLES30.glDeleteFramebuffers(1, frame, 0)
        return bitmap
    }

    fun saveGLPixels(width: Int, height: Int): Bitmap {
        val buffer = ByteBuffer.allocate(width * height * 4)
        GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.copyPixelsFromBuffer(buffer)
        return bitmap
    }

    fun genTexture(): Int {
        val textures = IntArray(1)
        GLES30.glGenTextures(1, textures, 0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]) // fuck, don't forget!
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE)
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
        return textures[0]
    }

}