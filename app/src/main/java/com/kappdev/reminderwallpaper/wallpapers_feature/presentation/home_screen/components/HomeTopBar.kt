package com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ui.TopAppBarContent
import com.google.accompanist.insets.ui.TopAppBarSurface
import com.kappdev.reminderwallpaper.R
import com.kappdev.reminderwallpaper.wallpapers_feature.presentation.home_screen.TypeTab

@Composable
fun HomeTopBar(
    isHeaderVisible: Boolean,
    changeTab: (TypeTab) -> Unit
) {
    TopAppBarSurface(
        backgroundColor = MaterialTheme.colorScheme.surface,
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Content(
                isVisible = isHeaderVisible,
                onMore = { /* TODO */ }
            )

            CategoryTabs(changeTab)
        }
    }
}

@Composable
private fun CategoryTabs(
    onTabChanged: (TypeTab) -> Unit
) {
    val (tabIndex, setTabIndex) = remember { mutableIntStateOf(0) }

    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        edgePadding = 0.dp
    ) {
        TypeTab.values().forEachIndexed { index, tab ->
            val isSelected = (tabIndex == index)
            Tab(
                selected = isSelected,
                text = {
                    Text(
                        text = stringResource(tab.titleRes),
                        fontSize = 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = when {
                            isSelected -> MaterialTheme.colorScheme.onSurface
                            else -> MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                onClick = {
                    setTabIndex(index)
                    onTabChanged(tab)
                }
            )
        }
    }
}

@Composable
private fun Content(
    isVisible: Boolean,
    onMore: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(
            animationSpec = spring(stiffness = Spring.StiffnessHigh),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(
            animationSpec = spring(stiffness = Spring.StiffnessHigh),
            shrinkTowards = Alignment.Top
        )
    ) {
        TopAppBarContent(
            title = {
                Text(
                    text = stringResource(R.string.wallpapers_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            actions = {
                IconButton(onClick = onMore) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.home_more_btn)
                    )
                }
            }
        )
    }
}