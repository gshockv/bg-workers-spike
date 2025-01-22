package com.gshockv.bw.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.ui.WorkersViewModel
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme

@Composable
fun WorkersListScreen(
  viewModel: WorkersViewModel,
  onNewWorkerClick: () -> Unit,
  onWorkerClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  val workersList = viewModel.workersList.collectAsState()

  Scaffold(
    topBar = {
      AppBar()
    },
    floatingActionButton = {
      NewWorkerButton(
        onClick = onNewWorkerClick
      )
    },
    floatingActionButtonPosition = FabPosition.EndOverlay
  ) { innerPadding ->
    Surface(
      modifier = modifier.padding(innerPadding)
    ) {
      if (workersList.value.isNotEmpty()) {
        LazyColumn(
          contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
          items(workersList.value) {
            WorkerItem(
              modifier = Modifier.padding(vertical = 8.dp),
              worker = it,
              onClick = onWorkerClick
            )
          }
        }
      } else {
        EmptyContent()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
  modifier: Modifier = Modifier
) {
  CenterAlignedTopAppBar(
    title = {
      Text(
        text = "Background Workers"
      )
    },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainer
    )
  )
}

@Composable
private fun NewWorkerButton(
  onClick: () -> Unit
) {
  ExtendedFloatingActionButton(
    onClick = onClick,
    icon = {
      Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null
      )
    },
    text = {
      Text("New Worker")
    }
  )
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PreviewListScreen_LightTheme() {
  BackgroundWorkersTheme {
    WorkersListScreen(
      viewModel = hiltViewModel(),
      onNewWorkerClick = {},
      onWorkerClick = {}
    )
  }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewListScreen_DarkTheme() {
  BackgroundWorkersTheme {
    WorkersListScreen(
      viewModel = hiltViewModel(),
      onNewWorkerClick = {},
      onWorkerClick = {}
    )
  }
}
