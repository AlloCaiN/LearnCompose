package com.allo.learn_compose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.allo.fluttermodule.util.FlutterEngineManager
import com.allo.learn_compose.ui.theme.Learn_composeTheme
import com.allo.openglmodule.manager.OpenglRenderUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Learn_composeTheme {
                Greeting()
            }
        }
    }

}

@Composable
fun Greeting() {
    val context = LocalContext.current
    Column( Modifier.alpha(1.0f),
        Arrangement.Center) {
        Button(onClick = {
            FlutterEngineManager.routeToFlutter(context = context)
            //    startActivity(context,FlutterActivity.createDefaultIntent(context),null)
        }) {
            Text(text = "Flutter")
        }
        Button(onClick = {
            OpenglRenderUtil.routeToGLActivity(context)
            //    startActivity(context,FlutterActivity.createDefaultIntent(context),null)
        }) {
            Text(text = "OpenGL")
        }
        Button(onClick = {
            OpenglRenderUtil.routeToGLNativeActivity(context)
            //    startActivity(context,FlutterActivity.createDefaultIntent(context),null)
        }) {
            Text(text = "OpenGLNative")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Learn_composeTheme {
        Greeting()
    }
}