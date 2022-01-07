package com.allo.fluttermodule.event

import android.content.Context
import android.os.Build
import android.util.Log
import com.allo.fluttermodule.util.FlutterEngineManager
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StandardMessageCodec
import java.util.*

class HybridRouteAndMessageHandleCenter(val context : Context) {
    var flutterEngine : FlutterEngine? = null
    var messageChannel : BasicMessageChannel<Any>? = null
    // TODO（是否可以随意命名？可以的，只要两端保持一致就好了）
    var channelName  = "com.allo.fluttermodule.event.message.channel"
    var mReplay : BasicMessageChannel.Reply<Any>? = null
    init {
        flutterEngine = FlutterEngineManager.flutterEngine(context,FlutterEngineManager.ENGINE_ID,FlutterEngineManager.RENDER_ENTRY)
        flutterEngine?.let {
            messageChannel = BasicMessageChannel(it.dartExecutor,channelName,StandardMessageCodec.INSTANCE)
            messageChannel?.setMessageHandler { message, reply ->
                mReplay = reply
                message?.let {
                    doHandleMessage(it,reply)
                }
            }
        }
    }

    // 处理事件
    private fun doHandleMessage(message : Any?,reply: BasicMessageChannel.Reply<Any>) {
        Log.i("czf","do handle message is $message")
        // 发送当前的设备型号
        val deviceInfo =Build. BRAND +"_" + Build.DEVICE
        sendMessage(deviceInfo)
    }

    fun sendMessage(message: Any) {
        messageChannel?.let {
            it.send(message) {
                // 发送回调
            }
        }
    }

}