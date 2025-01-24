package com.gshockv.bw.ui.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gshockv.bw.ui.WorkersViewModel
import com.gshockv.bw.ui.theme.BackgroundWorkersTheme

@Composable
fun WorkerDetailsScreen(
  viewModel: WorkersViewModel,
  workerId: Int,
  onBackClicked: () -> Unit,
  onNavigateToLogsClicked: (Int) -> Unit
) {
  val uiState = viewModel.uiDetailsState.collectAsState()

  LaunchedEffect(workerId) {
    viewModel.loadWorkerDetails(workerId)
  }

  Scaffold(
    topBar = {
      DetailsAppBar(
        onBackClicked = onBackClicked,
        onDeleteConfirmed = {
          viewModel.deleteWorker()
          onBackClicked()
        }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(24.dp)
      ) {
        WorkerNameField(
          name = uiState.value.worker.name,
          onNameChanged = {
            viewModel.setWorkerName(it)
          },
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        WorkerStatus(
          isActive = uiState.value.worker.active,
          onActiveChanged = {
            viewModel.setWorkerActive(it)
          },
          modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        ScheduledPeriodDropdown(
          schedulePeriod = uiState.value.worker.schedulePeriod,
          onSchedulePeriodSelected = {
            viewModel.setWorkerSchedulePeriod(it)
          },
          modifier = Modifier.padding(horizontal = 8.dp)
        )
      }

      if (workerId > 0) {
        OutlinedButton(
          onClick = { onNavigateToLogsClicked(workerId) },
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              horizontal = 52.dp,
              vertical = 12.dp
            )
            .height(58.dp)
        ) {
          Text("Worker Logs")
        }
      }

      Button(
        onClick = {
          viewModel.saveWorker()
          onBackClicked()
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            horizontal = 52.dp,
            vertical = 12.dp
          )
          .height(58.dp)
      ) {
        Text("Save")
      }
    }
  }
}

@Composable
private fun WorkerNameField(
  name: String,
  onNameChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    modifier = modifier,
    label = {
      Text("Worker name")
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Outlined.AccountCircle,
        contentDescription = null
      )
    },
    value = name,
    onValueChange = onNameChanged,
    placeholder = {
      Text("Enter worker name")
    }
  )
}

@Composable
private fun WorkerStatus(
  isActive: Boolean,
  onActiveChanged: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Is Active",
      modifier = Modifier.weight(1f),
      style = MaterialTheme.typography.titleMedium
    )
    Switch(
      checked = isActive,
      onCheckedChange = onActiveChanged
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsAppBar(
  onBackClicked: () -> Unit,
  onDeleteConfirmed: () -> Unit
) {
  TopAppBar(
    navigationIcon = {
      IconButton(onClick = onBackClicked) {
        Icon(
          imageVector = Icons.AutoMirrored.Default.ArrowBack,
          contentDescription = null
        )
      }
    },

    title = {
      Text("Worker Properties")
    },
    actions = {
      IconButton(onClick = onDeleteConfirmed) {
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
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PreviewWorkerDetails_LightTheme() {
  BackgroundWorkersTheme {
    WorkerDetailsScreen(
      viewModel = hiltViewModel(),
      workerId = 0,
      onBackClicked = {},
      onNavigateToLogsClicked = {}
    )
  }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewWorkerDetails_DarkTheme() {
  BackgroundWorkersTheme {
    WorkerDetailsScreen(
      viewModel = hiltViewModel(),
      workerId = 0,
      onBackClicked = {},
      onNavigateToLogsClicked = {}
    )
  }
}
