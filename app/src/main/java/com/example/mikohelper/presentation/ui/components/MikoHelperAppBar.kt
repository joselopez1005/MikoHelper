@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mikohelper.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.miko.R
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MikoHelperAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        actions = actions,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onNavIconPressed.invoke() }
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MikoHelperAppBarNoNavigation(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary

        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MikoHelperAppBarPreview() {
    MikoHelperTheme {
        MikoHelperAppBar(
            title = { ProfileCard(recipientName = "Jose", recipientPicture = R.drawable.ic_profile_akeshi) },
            actions = {Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MikoHelperAppBarPreviewDark() {
    MikoHelperTheme(darkTheme = true) {
        MikoHelperAppBar(title = { ProfileCard(recipientName = "Jose", recipientPicture = R.drawable.ic_profile_akeshi) }) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MioHelperAppBarHomeScreenPreview() {
    MikoHelperTheme {
        MikoHelperAppBarNoNavigation(
            title = { Text(
                text = "Chats",
                style = MaterialTheme.typography.headlineMedium
            )},
            actions = {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                )
            },
        )
    }
}