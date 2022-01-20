#version 300 es
layout(location = 0) in vec4 av_Position;
uniform mat4 transform;
void main() {
    gl_Position = (av_Position * transform);
    gl_PointSize = 10.0;
}
