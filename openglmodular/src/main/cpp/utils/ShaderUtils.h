//
// Created by allo cai on 2022/1/2.
//
#include <android/asset_manager_jni.h> //资源下发
#include <android/asset_manager.h>
#include "../gles.h"
#ifndef LEARNOPENGL_SHADERUTILS_H
#define LEARNOPENGL_SHADERUTILS_H

unsigned char * loadFileContent(const char *path,int &fileSize);
GLuint loadShader(GLenum shaderType,const char * shaderCode);
GLuint createProgram(const char * vShaderCode,const char * fShaderCode);



#endif //LEARNOPENGL_SHADERUTILS_H
