package com.gshockv.bw.ui.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme

@Composable
fun WorkerDetailsScreen(
  worker: BackgroundWorker?,
  onBackClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Text("Details")
}

@Composable
@Preview(showSystemUi = true)
private fun PreviewWorkerDetails() {
  BackgroundWorkersTheme {
    WorkerDetailsScreen(
      worker = null,
      onBackClicked = {}
    )
  }
}
