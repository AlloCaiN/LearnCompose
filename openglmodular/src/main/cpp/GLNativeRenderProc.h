//
// Created by allo cai on 2022/1/2.
//

#ifndef LEARNOPENGL_GLNATIVERENDERPROC_H
#define LEARNOPENGL_GLNATIVERENDERPROC_H
#include "gles.h"
#include "utils/ShaderUtils.h"


void SetViewPortSize(float width,float height);
void renderImpl();
void loadGLProgram(const char * vertexCode,const char * fragmentCode);


extern "C" JNIEXPORT void JNICALL
Java_com_allo_openglmodular_env_glnative_GLNativeRender_onViewportChanged(JNIEnv *env, jobject type,jfloat width,jfloat height) {
    SetViewPortSize(width,height);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_allo_openglmodular_env_glnative_GLNativeRender_loadGLProgram(JNIEnv *env, jobject thiz,
                                                          jstring vertex_code, jstring fragment_code) {
    const char * vertexCode =  (env)->GetStringUTFChars(vertex_code, NULL);
    const char * fragmentCode =  (env)->GetStringUTFChars(fragment_code, NULL);
    loadGLProgram(vertexCode,
                  fragmentCode);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_allo_openglmodular_env_glnative_GLNativeRender_OnDraw(JNIEnv *env, jobject thiz) {
    renderImpl();
}
#endif //LEARNOPENGL_GLNATIVERENDERPROC_H