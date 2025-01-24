package com.gshockv.bw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.data.BackgroundWorkerRepository
import com.gshockv.bw.data.LogEntry
import com.gshockv.bw.data.LogEntryRepository
import com.gshockv.bw.data.SchedulePeriod
import com.gshockv.bw.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiListState {
  data object Loading : UiListState()
  data class Data(val workers: List<BackgroundWorker>) : UiListState()
}

data class UiDetailsState(
  val worker: BackgroundWorker = prepareEmptyWorker()
)

sealed class UiLogEntriesState {
  data object Loading : UiLogEntriesState()
  data class Data(val entries: List<LogEntry>) : UiLogEntriesState()
}

@HiltViewModel
class WorkersViewModel @Inject constructor(
  private val repo: BackgroundWorkerRepository,
  private val logsRepo: LogEntryRepository
) : ViewModel() {

  val uiListState = repo.observeAll()
    .map {
      UiListState.Data(workers = it)
    }
    .stateIn(
      scope = viewModelScope,
      started = WhileUiSubscribed,
      initialValue = UiListState.Loading
    )

  private val _uiDetails = MutableStateFlow(UiDetailsState())
  val uiDetailsState = _uiDetails.asStateFlow()

  private val workerIdForLog = MutableStateFlow(0)

  @OptIn(ExperimentalCoroutinesApi::class)
  private val _logEntries = workerIdForLog
    .flatMapLatest { id ->
      logsRepo.observeWorkerLogs(id)
    }.map {
      UiLogEntriesState.Data(it)
    }

  val logEntries = _logEntries
    .stateIn(
      scope = viewModelScope,
      started = WhileUiSubscribed,
      initialValue = UiLogEntriesState.Loading
    )

  fun loadWorkerDetails(id: Int) {
    if (id > 0) {
      viewModelScope.launch {
        val worker = repo.loadWorker(id)
        _uiDetails.update { current ->
          current.copy(
            worker = worker
          )
        }
      }
    } else {
      _uiDetails.update { current ->
        current.copy(
          worker = prepareEmptyWorker()
        )
      }
    }
  }

  fun saveWorker() {
    viewModelScope.launch {
      repo.saveWorker(_uiDetails.value.worker)
    }
  }

  fun deleteWorker() {
    viewModelScope.launch {
      repo.deleteWorker(_uiDetails.value.worker)
    }
  }

  fun setWorkerName(name: String) {
    _uiDetails.update { current ->
      current.copy(
        worker = _uiDetails.value.worker.copy(
          name = name
        )
      )
    }
  }

  fun setWorkerActive(active: Boolean) {
    _uiDetails.update { current ->
      current.copy(
        worker = _uiDetails.value.worker.copy(
          active = active
        )
      )
    }
  }

  fun setWorkerSchedulePeriod(period: SchedulePeriod) {
    _uiDetails.update { current ->
      current.copy(
        worker = _uiDetails.value.worker.copy(
          schedulePeriod = period
        )
      )
    }
  }

  fun loadWorkerLogs(workerId: Int) {
    this.workerIdForLog.value = workerId
  }

  fun deleteWorkerLogs(workerId: Int) {
    viewModelScope.launch {
      logsRepo.deleteWorkerLogs(workerId)
    }
  }
}

private fun prepareEmptyWorker() =
  BackgroundWorker(
    id = 0,
    name = "",
    active = false,
    schedulePeriod = SchedulePeriod.FORTY_FIVE_MINUTES
  )
