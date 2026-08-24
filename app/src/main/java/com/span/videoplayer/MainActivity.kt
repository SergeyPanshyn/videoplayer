package com.span.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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
                PermissionGate {
                    AppNavHost()
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
