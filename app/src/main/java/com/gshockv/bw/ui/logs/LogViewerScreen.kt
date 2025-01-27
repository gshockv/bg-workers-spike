package com.gshockv.bw.ui.logs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gshockv.bw.data.LogEntry
import com.gshockv.bw.ui.UiLogEntriesState
import com.gshockv.bw.ui.WorkersViewModel
import com.gshockv.bw.ui.list.LoadingView
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme
import java.time.format.DateTimeFormatter

@Composable
fun LogViewerScreen(
  viewModel: WorkersViewModel,
  workerId: Int,
  onBackClicked: () -> Unit
) {
  val entriesState = viewModel.logEntries.collectAsStateWithLifecycle()

  LaunchedEffect(workerId) {
    viewModel.loadWorkerLogs(workerId)
  }

  Scaffold(
    topBar = {
      LogViewerAppBar(
        onBackPressed = onBackClicked,
        onClearPressed = {
          viewModel.deleteWorkerLogs(workerId)
        }
      )
    }
  ) { innerPadding ->
    when (val state = entriesState.value) {
      is UiLogEntriesState.Loading -> {
        LoadingView()
      }

      is UiLogEntriesState.Data -> {
        LogViewer(
          entries = state.entries,
          modifier = Modifier.padding(innerPadding)
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogViewerAppBar(
  onBackPressed: () -> Unit,
  onClearPressed: () -> Unit
) {
  TopAppBar(
    navigationIcon = {
      IconButton(onClick = onBackPressed) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = null
        )
      }
    },
    title = {
      Text(
        text = "Worker Logs"
      )
    },
    actions = {
      IconButton(onClick = onClearPressed) {
        Icon(
          imageVector = Icons.Outlined.Delete,
          contentDescription = null
        )
      }
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainer
    )
  )
}

@Composable
private fun LogViewer(
  entries: List<LogEntry>,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier.fillMaxSize()
  ) {
    items(entries, key = { it.id }) { logEntry ->
      println("Log entry: $logEntry")

      ListItem(
        headlineContent = {
          Text(
            text = logEntry.workerInfo
          )
        },
        supportingContent = {
          Text(
            text = logEntry.recordCreated.format(
              DateTimeFormatter.ofPattern("HH:mm, DD-mm-yyyy"))
          )
        }
      )
    }
  }
}

@Composable
@Preview(showSystemUi = true)
private fun PreviewLogViewer() {
  BackgroundWorkersTheme {
    LogViewerScreen(
      workerId = 0,
      viewModel = hiltViewModel(),
      onBackClicked = {}
    )
  }
}
