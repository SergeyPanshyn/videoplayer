package com.span.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.span.videoplayer.presentation.list.VideoListScreen
import com.span.videoplayer.presentation.permission.PermissionGate
import com.span.videoplayer.presentation.player.VideoPlayerScreen
import com.span.videoplayer.ui.theme.VideoPlayerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoPlayerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PermissionGate {
                        AppNavHost()
                    }
                }
            }
        }
    }
}

@Serializable
data object VideoList

@Serializable
data class VideoPlayer(val videoId: Long)

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = VideoList) {
        composable<VideoList> {
            VideoListScreen(onVideoClick = { videoId ->
                navController.navigate(VideoPlayer(videoId))
            })
        }
        composable<VideoPlayer> { backStackEntry ->
            val args = backStackEntry.toRoute<VideoPlayer>()
            VideoPlayerScreen(videoId = args.videoId, onBack = { navController.popBackStack() })
        }
    }
}
