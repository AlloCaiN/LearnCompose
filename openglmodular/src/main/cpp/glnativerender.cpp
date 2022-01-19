#include <jni.h> //引入jni文件
#include <string>
#include <GLES3/gl3.h> // 引入GL库文件
#include <GLES3/gl3ext.h>
#include <android/asset_manager_jni.h> //资源下发
#include <android/asset_manager.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_allo_openglmodular_env_glnative_GLNativeRender_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
