package com.allo.fluttermodule.manager

import android.app.Application
import io.flutter.embedding.engine.FlutterEngineGroup

class AlloFlutterApplication : Application() {
    lateinit var engineGroup : FlutterEngineGroup
    override fun onCreate() {
        super.onCreate()
        engineGroup = FlutterEngineGroup(this)
    }
}