/*
 * 2023 - Developed by Max Jin
 * Source code subject to change. Refer to NOTICE.txt in source tree for changes and attributions.
 */

package ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.common.PlatformOS
import core.spacex.RocketLaunch
import data.spacex.SpacexRepositoryImpl
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import kmm.platform.Platform
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.util.dimenB2
import ui.util.dimenB4

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun HomeScreen(platform: Platform) {
    val spacexRepository = SpacexRepositoryImpl()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var spacexLaunches: List<RocketLaunch> by remember { mutableStateOf(emptyList()) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        try {
            spacexLaunches = spacexRepository.getLaunches(false)
        } catch (e: Exception) {
            e.message ?: "error"
        }
    }
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .paint(painter = painterResource("spacex6.png"), contentScale = ContentScale.FillWidth, alignment = Alignment.TopCenter, sizeToIntrinsics = true)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .then(
                if (platform.os == PlatformOS.IOS) {
                    Modifier.padding(top = 46.dp, bottom = 20.dp)
                } else {
                    Modifier
                }
            ),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        "SpaceX Launches",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            spacexLaunches = emptyList()
                            try {
                                spacexLaunches = spacexRepository.getLaunches(true)
                            } catch (e: Exception) {
                                e.message ?: "error"
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Reload"
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            SpacexLaunchListView(modifier = Modifier.padding(top = 8.dp), spacexLaunches)
            if (spacexLaunches.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpacexLaunchListView(modifier: Modifier = Modifier, spacexLaunches: List<RocketLaunch> = emptyList()) {
    val urlHandler = LocalUriHandler.current
    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = spacexLaunches.isNotEmpty(),
        enter = slideInVertically { it } + fadeIn(),
        exit = fadeOut(animationSpec = snap())
    ) {
        LazyColumn(modifier = modifier) {
            items(spacexLaunches.size) { index ->
                ListItem(
                    leadingContent = {
                        spacexLaunches[index].links.patch?.small?.let {
                            val asyncPainterResource = asyncPainterResource(data = Url(it)) {
                                // CoroutineContext to be used while loading the image.
                                coroutineContext = scope.coroutineContext
                                // Not a Custom HTTP request
                            }
                            KamelImage(modifier = Modifier.size(60.dp), resource = asyncPainterResource, contentDescription = null,
                                onLoading = { progress -> CircularProgressIndicator(progress) },
                                onFailure = { _ ->
                                    // TODO onFailure
                                })
                        }
                    },
                    headlineText = { Text("${spacexLaunches[index].missionName} (${spacexLaunches[index].launchYear})") },
                    supportingText = {
                        val launchSuccess = spacexLaunches[index].launchSuccess ?: false
                        Column {
                            Text(if (launchSuccess) "Successful 🚀" else "Unsuccessful", color = if (launchSuccess) Color.Green else Color.Red)
                            Text(spacexLaunches[index].details ?: "No details", modifier = Modifier.padding(top = dimenB2))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimenB4, vertical = dimenB2)
                        .clip(RoundedCornerShape(dimenB2))
                        .clickable(
                            onClick = {
                                spacexLaunches[index].links.article?.let { urlHandler.openUri(it) }
                            }, role = Role.Button
                        ),
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                )
            }
        }
    }
}