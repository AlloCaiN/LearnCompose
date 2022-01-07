package com.allo.fluttermodule.util

import android.content.Context
import com.allo.fluttermodule.event.HybridRouteAndMessageHandleCenter
import com.allo.fluttermodule.manager.AlloFlutterApplication
import io.flutter.FlutterInjector
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

object FlutterEngineManager {
    const val ENGINE_ID = "render"
    const val RENDER_ENTRY = "render"
    // 如果有就复用，如果没有就重建,这边传入的参数主要形式有engineId 和 entryPoint两个
    fun flutterEngine(context : Context, engineId : String, entryPoint : String ): FlutterEngine? {
        // 通过系统的cache去获取缓存
        var engine = FlutterEngineCache.getInstance().get(engineId)
        if (engine == null) {
            // 新建engine
            val app = context.applicationContext as AlloFlutterApplication
            val dartEntryPoint = DartExecutor.DartEntrypoint(FlutterInjector.instance().flutterLoader().findAppBundlePath(),entryPoint)
            engine = app.engineGroup.createAndRunEngine(context,dartEntryPoint)
            FlutterEngineCache.getInstance().put(engineId,engine)
        }
        return engine
    }

    fun routeToFlutter(context: Context) {
//        flutterEngine(context,"render","render")
        val messageCenter = HybridRouteAndMessageHandleCenter(context)
        context.startActivity(FlutterActivity.withCachedEngine("render").build(context))
    }


    fun destroyEngine() {
        FlutterEngineCache.getInstance().get(ENGINE_ID)?.destroy()
    }

}