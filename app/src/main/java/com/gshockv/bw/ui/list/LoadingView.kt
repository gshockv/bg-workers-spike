package com.gshockv.bw.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme

@Composable
fun LoadingView(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator(
      modifier = Modifier.size(88.dp)
    )
  }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PreviewLoading_LightTheme() {
  BackgroundWorkersTheme {
    Surface {
      LoadingView()
    }
  }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewLoading_DarkTheme() {
  BackgroundWorkersTheme {
    Surface {
      LoadingView()
    }
  }
}
