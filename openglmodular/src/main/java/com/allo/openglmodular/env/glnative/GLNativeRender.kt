package com.allo.openglmodular.env.glnative

object GLNativeRender {

    /**
     * A native method that is implemented by the 'glnativerender' native library,
     * which is packaged with this application.
     */
    // Used to load the 'glnativerender' library on application startup.
    init {
        System.loadLibrary("glnativerender")
    }
    external fun loadGLProgram(vertexCode : String,fragment: String)
    external fun onViewportChanged(width: Float,height : Float)
    external fun OnDraw()


}