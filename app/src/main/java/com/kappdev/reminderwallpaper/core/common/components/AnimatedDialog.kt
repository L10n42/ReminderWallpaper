package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AnimatedDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    enterAnim: EnterTransition,
    exitAnim: ExitTransition,
    enterDelay: Long = 0,
    exitDelay: Long = 300,
    content: @Composable (AnimatedTransitionDialogHelper) -> Unit
) {
    val onDismissSharedFlow: MutableSharedFlow<Any> = remember { MutableSharedFlow() }
    val scope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }

    suspend fun startDismissWithAnimation() {
        animateTrigger.value = false
        delay(exitDelay)
        onDismissRequest()
    }

    LaunchedEffect(Unit) {
        launch {
            delay(enterDelay)
            animateTrigger.value = true
        }
        launch {
            onDismissSharedFlow.asSharedFlow().collectLatest {
                startDismissWithAnimation()
            }
        }
    }

    Dialog(
        properties = properties,
        onDismissRequest = {
            scope.launch {
                startDismissWithAnimation()
            }
        }
    ) {
        AnimatedVisibility(
            visible = animateTrigger.value,
            enter = enterAnim,
            exit = exitAnim,
        ) {
            content(AnimatedTransitionDialogHelper(scope, onDismissSharedFlow))
        }
    }
}

class AnimatedTransitionDialogHelper(
    private val scope: CoroutineScope,
    private val onDismissFlow: MutableSharedFlow<Any>
) {
    fun triggerAnimatedDismiss() {
        scope.launch {
            onDismissFlow.emit(Any())
        }
    }
}