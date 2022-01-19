package com.allo.openglmodular.env.util

import android.content.Context
import android.content.Intent
import com.allo.openglmodular.env.enterance.GLActivity
import com.allo.openglmodular.env.enterance.GLNativeActivity

object OpenglRenderUtil {
    fun routeToGLActivity(context : Context) {
        context.startActivity(Intent().apply {
            setClass(context, GLActivity::class.java)
        })
    }

    fun routeToGLNativeActivity(context: Context) {
        context.startActivity(Intent().apply {
            setClass(context, GLNativeActivity::class.java)
        })
    }

}