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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.spacex.RocketLaunch
import data.spacex.SpacexRepositoryImpl
import kmm.platform.Platform
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(platform: Platform) {
    val spacexRepository = SpacexRepositoryImpl()
    var spacexLaunches: List<RocketLaunch> by rememberSaveable { mutableStateOf(emptyList()) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        try {
            spacexLaunches = spacexRepository.getLaunches(false)
        } catch (e: Exception) {
            e.message ?: "error"
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
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
                }
            )
        }
    ) { paddingValues ->
        SpacexLaunchListView(modifier = Modifier.padding(paddingValues), spacexLaunches)
        if (spacexLaunches.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpacexLaunchListView(modifier: Modifier = Modifier, spacexLaunches: List<RocketLaunch> = emptyList()) {
    AnimatedVisibility(
        visible = spacexLaunches.isNotEmpty(),
        enter = slideInVertically { it } + fadeIn(),
        exit = fadeOut(animationSpec = snap())
    ) {
        LazyColumn(modifier = modifier) {
            items(spacexLaunches.size) {
                ListItem(
                    headlineText = { Text("Launch name: ${spacexLaunches[it].missionName}") },
                    supportingText = {
                        val launchSuccess = spacexLaunches[it].launchSuccess ?: false
                        Column {
                            Text(if (launchSuccess) "Successful" else "Unsuccessful", color = if (launchSuccess) Color.Green else Color.Red)
                            Text("Launch year: ${spacexLaunches[it].launchYear}")
                            Text("Launch detail: ${spacexLaunches[it].details}")
                        }
                    },
                    leadingContent = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}