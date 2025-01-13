package com.gshockv.bw.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme

@Composable
fun MainAppScreen(
  modifier: Modifier = Modifier
) {
  Text(
    text = "Background Workers",
    modifier = Modifier
      .fillMaxSize()
      .systemBarsPadding(),
    textAlign = TextAlign.Center
  )
}

@Composable
@Preview(showSystemUi = true)
private fun MainScreenAppPreview() {
  BackgroundWorkersTheme {
    MainAppScreen()
  }
}
