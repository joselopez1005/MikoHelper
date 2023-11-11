package com.example.mikohelper.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miko.R
import com.example.mikohelper.presentation.ui.theme.MikoHelperTheme

@Composable
fun ProfileCard(
    recipientName: String,
    recipientPicture: Int,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = recipientPicture),
            contentDescription = null,
            contentScale= ContentScale.Crop,
            modifier = Modifier
                .requiredSize(64.dp)
                .border(
                    BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary),
                    CircleShape
                )
                .clip(CircleShape)
        )
        Text(
            text = recipientName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreview(){
    MikoHelperTheme {
        ProfileCard("Jose", R.drawable.ic_profile_akeshi)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardPreviewDark(){
    MikoHelperTheme(darkTheme = true) {
        ProfileCard("Jose", R.drawable.ic_profile_akeshi)
    }
}