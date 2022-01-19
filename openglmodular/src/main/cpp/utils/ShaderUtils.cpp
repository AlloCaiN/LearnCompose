//
// Created by allo cai on 2022/1/2.
//

#include "ShaderUtils.h"

AAssetManager* aAssetManager = nullptr;
// 根据Asset给定的位置加载gl文件


void checkNativeGLError(const char* op) {
    if (glGetError() != GL_NO_ERROR) {
        LOGE("error in gl %s",&op);
    }
}

unsigned char * loadFileContent(const char *path,int &fileSize) {
    unsigned char * fileContent = nullptr;
    fileSize = 0;
    AAsset * asset = AAssetManager_open(aAssetManager,path,AASSET_MODE_UNKNOWN);
    if (asset == nullptr) {
        LOGE("Load File Content asset is null ,load shader error");
        return nullptr;
    }
    fileSize = AAsset_getLength(asset);
    fileContent = new unsigned char[fileSize];
    AAsset_read(asset,fileContent,fileSize);
    fileContent[fileSize]='\0';
    AAsset_close(asset);
    LOGE("LoadFileContent success ... %s",path);
    return fileContent;
}
GLuint loadShader(GLenum shaderType,const char * shaderCode) {
    GLint shader = glCreateShader(shaderType);
    if (shader != GL_FALSE) {
        // 与java层开始不一样，这边传入的参数为shader,长度，程序源码
        glShaderSource(shader,1,&shaderCode, nullptr);
        glCompileShader(shader);
        checkNativeGLError("compile shader");
        GLint compileResult = GL_TRUE;
        glGetShaderiv(shader,GL_COMPILE_STATUS,&compileResult);
        if (compileResult == GL_FALSE) {
            char glLog[1024] = {0};
            GLsizei logLen = 0;
            glGetShaderInfoLog(shader,1024,&logLen,glLog);
            LOGE("compile error,log :%s",glLog);
            LOGE("compile error,shader %s",shaderCode);
            glDeleteShader(shader);
            return 0;
        }
        return shader;
    }
}
GLuint createProgram(const char * vShaderCode,const char * fShaderCode) {
    GLint vShader = loadShader(GL_VERTEX_SHADER,vShaderCode);
    if (vShader == 0) {
        return 0;
    }
    GLint fShader = loadShader(GL_FRAGMENT_SHADER,fShaderCode);
    if (fShader == 0) {
        return 0;
    }
    GLint program = glCreateProgram();
    if (program != GL_FALSE) {
        glAttachShader(program,vShader);
        checkNativeGLError("AttachVertexShader");
        glAttachShader(program,fShader);
        checkNativeGLError("AttachFragmentShader");
        glLinkProgram(program);
        GLint linkStatus = GL_TRUE;
        // 把lingstatus 指针传入
        glGetProgramiv(program,GL_LINK_STATUS,&linkStatus);
        if (linkStatus != GL_TRUE) {
            char glLog[1024] = {0};
            GLsizei logLen = 0;
            glGetProgramInfoLog(program,1024,&logLen,glLog);
            LOGE("ERROR in LinkProgram %s",glLog);
            glDeleteProgram(program);
            program = 0;
        }
    } else {
        LOGE("gl create program failed :%d",program);
    }
    return program;
}



extern "C" JNIEXPORT void JNICALL
Java_com_allo_glnativerender_GLNativeRender_InitAssetManager(JNIEnv *env, jobject type,jobject am) {
    aAssetManager =  AAssetManager_fromJava(env,am);
}