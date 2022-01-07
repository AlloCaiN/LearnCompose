package com.allo.learn_compose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.allo.fluttermodule.util.FlutterEngineManager
import com.allo.learn_compose.ui.theme.Learn_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Learn_composeTheme {
                // A surface container using the 'background' color from the theme
                Column(Modifier.padding(Dp(300f), Dp(300f))) {
                    Greeting(name = "Android")
                }
            }
        }
//        startActivity(
//            FlutterActivity.createDefaultIntent(this)
//        )
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    val context = LocalContext.current
    Button(onClick = {
        FlutterEngineManager.routeToFlutter(context = context)
    //    startActivity(context,FlutterActivity.createDefaultIntent(context),null)
    }) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Learn_composeTheme {
        Greeting("Android")
    }
}