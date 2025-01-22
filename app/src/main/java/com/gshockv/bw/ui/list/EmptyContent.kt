package com.gshockv.bw.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme

@Composable
fun EmptyContent(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxSize().padding(horizontal = 12.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "There are no registered Background Workers yet.\n" +
          "Use New Worker button to create new one.",
      textAlign = TextAlign.Center,
      lineHeight = 32.sp,
      fontWeight = FontWeight.Normal,
      fontSize = 16.sp,
      color = MaterialTheme.colorScheme.onPrimaryContainer
    )
  }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewEmpty_LightTheme() {
  BackgroundWorkersTheme {
    Surface {
      EmptyContent()
    }
  }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewEmpty_DarkTheme() {
  BackgroundWorkersTheme {
    Surface {
      EmptyContent()
    }
  }
}
