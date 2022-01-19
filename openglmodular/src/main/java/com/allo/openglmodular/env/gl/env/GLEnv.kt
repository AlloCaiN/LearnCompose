package com.allo.openglmodular.env.gl.env

import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.opengl.EGLSurface

data class GLEnv(var eglDisplay: EGLDisplay, var  eglContext: EGLContext, var eglSurface: EGLSurface)