package com.gshockv.bw.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme
import com.gshockv.bw.ui.theme.IndicatorActiveColor
import com.gshockv.bw.ui.theme.IndicatorNotActiveColor

@Composable
fun WorkerItem(
  worker: BackgroundWorker,
  onClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val infoLabel = remember(worker.id) {
    val activeLabel = if (worker.active) "Active" else "Not Active"
    "${worker.refreshPeriod} ${worker.refreshTimeUnit} / $activeLabel"
  }

  val indicatorColor = remember(worker.active) {
    if (worker.active) IndicatorActiveColor else IndicatorNotActiveColor
  }

  Surface(
    modifier = modifier
  ) {
    Card(
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
      ),
      shape = Shapes().medium,
      border = BorderStroke(
        width = 1.dp,
        color = IndicatorNotActiveColor.copy(alpha = 0.38f)
      ),
      modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .clickable { onClick(worker.id) },
    ) {
      Row(
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .weight(weight = 1f)
            .fillMaxHeight()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "BW",
            modifier = Modifier
              .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
              )
              .size(50.dp)
              .padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(start = 12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
          ) {
            Text(
              text = worker.name,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = infoLabel,
              style = MaterialTheme.typography.bodySmall
            )
          }
        }
        Box(
          modifier = Modifier
            .width(96.dp)
            .fillMaxHeight()
            .background(indicatorColor)
        )
      }
    }
  }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PreviewItem_Active_LightTheme() {
  BackgroundWorkersTheme {
    Surface(
      modifier = Modifier.padding(16.dp)
    ) {
      WorkerItem(
        worker = BackgroundWorker(
          id = 0,
          name = "Active Worker",
          active = true,
          refreshPeriod = 30,
          refreshTimeUnit = "min"
        ),
        onClick = { }
      )
    }
  }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PreviewItem_NotActive_LightTheme() {
  BackgroundWorkersTheme {
    Surface(
    ) {
      WorkerItem(
        worker = BackgroundWorker(
          id = 0,
          name = "Active Worker",
          active = false,
          refreshPeriod = 30,
          refreshTimeUnit = "min"
        ),
        onClick = { }
      )
    }
  }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewItem_Active_DarkTheme() {
  BackgroundWorkersTheme {
    Surface(
      modifier = Modifier.padding(16.dp)
    ) {
      WorkerItem(
        worker = BackgroundWorker(
          id = 0,
          name = "Active Worker",
          active = true,
          refreshPeriod = 30,
          refreshTimeUnit = "min"
        ),
        onClick = { }
      )
    }
  }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewItem_NotActive_DarkTheme() {
  BackgroundWorkersTheme {
    Surface(
      modifier = Modifier.padding(16.dp)
    ) {
      WorkerItem(
        worker = BackgroundWorker(
          id = 0,
          name = "Active Worker",
          active = false,
          refreshPeriod = 30,
          refreshTimeUnit = "min"
        ),
        onClick = { }
      )
    }
  }
}

