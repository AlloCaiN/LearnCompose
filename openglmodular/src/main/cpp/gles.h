//
// Created by allo cai on 2022/1/2.
//
#include <jni.h> //引入jni文件
#include <string>
#include <GLES3/gl3.h> // 引入GL库文件
#include <GLES3/gl3ext.h>
#include <android/asset_manager_jni.h> //资源下发
#include <android/asset_manager.h>

#define __DEBUG__ANDROID_ON
#ifdef __DEBUG__ANDROID_ON
#include <android/log.h> //android logcat 库
#define LOG_TAG  "AlloGL-JNI-Log"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#endif
#ifndef LEARNOPENGL_GLES_H
#define LEARNOPENGL_GLES_H

#endif //LEARNOPENGL_GLES_H
