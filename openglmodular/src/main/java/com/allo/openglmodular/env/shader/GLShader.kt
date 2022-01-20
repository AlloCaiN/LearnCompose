package com.allo.openglmodular.env.shader

import org.intellij.lang.annotations.Language

object GLShader {
    @Language("glsl")
    const val sample_vertex = """#version 300 es
        layout(location = 0) in vec4 av_Position;
        uniform mat4 transform;
        void main() {
            gl_Position = av_Position * transform;
            gl_PointSize = 10.0; 
        }
    """
    @Language("glsl")
    const val sample_fragment = """#version 300 es
        precision mediump float;
        out vec4 fragColor;
        void main() {
            fragColor = vec4(1.0,0.0,0.0,1.0);
        }
    """

}