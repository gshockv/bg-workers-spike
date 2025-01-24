package com.gshockv.bw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.data.BackgroundWorkerRepository
import com.gshockv.bw.data.SchedulePeriod
import com.gshockv.bw.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiListState {
  data object Loading : UiListState()
  data class Data(val workers: List<BackgroundWorker>)
}

data class UiDetailsState(
  val worker: BackgroundWorker = prepareEmptyWorker()
)

@HiltViewModel
class WorkersViewModel @Inject constructor(
  private val repo: BackgroundWorkerRepository
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
}

private fun prepareEmptyWorker() =
  BackgroundWorker(
    id = 0,
    name = "",
    active = false,
    schedulePeriod = SchedulePeriod.FORTY_FIVE_MINUTES
  )
