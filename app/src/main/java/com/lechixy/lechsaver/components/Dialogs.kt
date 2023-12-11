package com.lechixy.lechsaver.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

private val DialogVerticalPadding = PaddingValues(vertical = 24.dp)
private val IconPadding = PaddingValues(bottom = 16.dp)
private val DialogHorizontalPadding = PaddingValues(horizontal = 24.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val TextPadding = PaddingValues(bottom = 24.dp)
private val ButtonsMainAxisSpacing = 8.dp
private val ButtonsCrossAxisSpacing = 12.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LechDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    confirmButton: @Composable (() -> Unit)? = null,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = containerColor,
            tonalElevation = tonalElevation,
        ) {
            Column(
                modifier = Modifier.padding(DialogVerticalPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                icon?.let {
                    CompositionLocalProvider(LocalContentColor provides iconContentColor) {
                        Box(
                            Modifier
                                .padding(IconPadding)
                                .padding(DialogHorizontalPadding)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            icon()
                        }
                    }
                }
                title?.let {

                    val titleTextStyle = MaterialTheme.typography.headlineSmall
                    val descriptionTextStyle = MaterialTheme.typography.bodySmall
                    Box(
                        // Align the title to the center when an icon is present.
                        modifier = Modifier
                            .padding(TitlePadding)
                            .padding(DialogHorizontalPadding)
                            .align(
                                if (icon == null) {
                                    Alignment.Start
                                } else {
                                    Alignment.CenterHorizontally
                                }
                            )
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CompositionLocalProvider(LocalContentColor provides titleContentColor) {
                                ProvideTextStyle(titleTextStyle) {
                                    title()
                                }
                            }
                            description?.let {
                                CompositionLocalProvider(LocalContentColor provides textContentColor) {
                                    ProvideTextStyle(descriptionTextStyle) {
                                        description()
                                    }
                                }
                            }
                        }
                    }
                }
                text?.let {
                    CompositionLocalProvider(LocalContentColor provides textContentColor) {
                        val textStyle =
                            MaterialTheme.typography.bodyMedium
                        ProvideTextStyle(textStyle) {
                            Box(
                                Modifier
                                    .weight(weight = 1f, fill = false)
                                    .padding(
                                        if(confirmButton != null || dismissButton != null){
                                            TextPadding
                                        } else {
                                            PaddingValues(0.dp)
                                        }
                                    )
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                text()
                            }
                        }
                    }
                }
                if(confirmButton != null || dismissButton != null){
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(DialogHorizontalPadding)
                    ) {
                        val textStyle =
                            MaterialTheme.typography.labelLarge
                        ProvideTextStyle(value = textStyle) {
                            Row {
                                dismissButton?.invoke()
                                confirmButton?.invoke()
                            }
                        }
                    }
                }
            }
        }
    }

}