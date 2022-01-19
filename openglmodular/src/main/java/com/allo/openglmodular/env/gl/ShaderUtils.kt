package com.allo.openglmodular.env.gl

import android.content.res.Resources
import android.opengl.GLES30
import android.util.Log
import java.io.ByteArrayOutputStream

object ShaderUtils {
    private const val TAG_ERROR = "ES30_ERROR"
    private fun loadShader(shaderType : Int, source: String?): Int {
        // 标准
        var shader = GLES30.glCreateShader(shaderType) // 根据传入类型尝试创建shader,
        if (shader != 0) { // 环境异常时就会出现0的情况
            GLES30.glShaderSource(shader,source) // 将source的程序替代到shader中，实现着色器与环境绑定
            GLES30.glCompileShader(shader)// 编译着色器程序
            checkGLError("glCompilerShader") // 检查是否编译成功
            val compiled = IntArray(1)
            GLES30.glGetShaderiv(shader,GLES30.GL_COMPILE_STATUS,compiled,0) // 获取编译后的程序，如果结果为0则为编译失败
            if (compiled[0] == 0) {
                Log.e(TAG_ERROR,"Could not compile shader $shaderType:")
                Log.e(TAG_ERROR,"ERROR:${GLES30.glGetShaderInfoLog(shader)}")
                GLES30.glDeleteShader(shader)
                shader = 0
            }
        } else {
            Log.e(TAG_ERROR,"Could not create shader $shaderType , Error : $shader")
        }
        return shader
    }

    fun createProgram(vertexSource : String,fragmentSource : String) : Int {
        // 加载顶点着色器
        val vertexShader = loadShader(GLES30.GL_VERTEX_SHADER,vertexSource)
        if (vertexShader == 0) {
            return 0
        }
        // 加载片段着色器
        val fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER,fragmentSource)
        if (fragmentShader == 0) {
            return 0
        }
        // 创建渲染程序
        var program = GLES30.glCreateProgram()
        if (program != 0) {
            // 绑定顶点着色器
            GLES30.glAttachShader(program,vertexShader)
            checkGLError("AttachVertexShader")
            // 绑定片段着色器
            GLES30.glAttachShader(program,fragmentShader)
            checkGLError("AttachFragmentShader")
            //连接程序
            GLES30.glLinkProgram(program)
            val linkStatus = IntArray(1)
            //获取程序指针，确定是否创建成功
            GLES30.glGetProgramiv(program,GLES30.GL_LINK_STATUS,linkStatus,0)
            if (linkStatus[0] != GLES30.GL_TRUE) {
                Log.e(TAG_ERROR,"ERROR: ${GLES30.glGetProgramInfoLog(program)}")
                GLES30.glDeleteProgram(program)
                program = 0
            }
        } else {
            Log.e(TAG_ERROR,"glCreateProgram Failed :$program")
        }
        return program
    }

    // 以文件方式加载
    fun loadShaderFromAssets(vShaderName : String,fShaderName : String,resource : Resources): Int {
        val vertexText = loadFromAssetsFile(vShaderName,resource) ?:return 0
        val fragText = loadFromAssetsFile(fShaderName,resource) ?:return 0
        return createProgram(vertexText,fragText)
    }
    // 以raw文件格式加载
    fun loadShaderFromRaw(vShaderName : Int,fShaderName : Int,resource : Resources) : Int {
        val vertexText = loadFromRawFile(vShaderName,resource) ?:return 0
        val fragText = loadFromRawFile(fShaderName,resource) ?:return 0
        return createProgram(vertexText,fragText)
    }


    private fun loadFromAssetsFile(fileName : String,resource: Resources) : String? {
        var result : String? = null
        try {
            val inputStream = resource.assets.open(fileName)
            var ch = 0
            val baos = ByteArrayOutputStream()
            while (inputStream.read().also { ch = it } != -1) {
                baos.write(ch)
            }
            val buffer = baos.toByteArray()
            baos.close()
            inputStream.close()
            result = String(buffer)
            result = result.replace("\\r\\n".toRegex(),"\n")
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun loadFromRawFile(id : Int,resource: Resources) : String? {
        var result : String? = null
        try {
            val inputStream = resource.openRawResource(id)
            var ch = 0
            val baos = ByteArrayOutputStream()
            while (inputStream.read().also { ch = it } != -1) {
                baos.write(ch)
            }
            val buffer = baos.toByteArray()
            baos.close()
            inputStream.close()
            result = String(buffer)
            result = result.replace("\\r\\n".toRegex(),"\n")
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun checkGLError(op : String) {
        var error : Int
        while (GLES30.glGetError().also { error = it } != GLES30.GL_NO_ERROR) {
            Log.e(TAG_ERROR,"$op:glError $error")
            throw RuntimeException("$op: glError $error")
        }
    }
}