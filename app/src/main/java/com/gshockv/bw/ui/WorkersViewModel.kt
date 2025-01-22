package com.gshockv.bw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.data.BackgroundWorkerRepository
import com.gshockv.bw.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class UiListState {
  data object Loading : UiListState()
  data class Data(val workers: List<BackgroundWorker>)
}

@HiltViewModel
class WorkersViewModel @Inject constructor(
  private val repo: BackgroundWorkerRepository
) : ViewModel() {

  val workersList = repo.observeAll().stateIn(
    scope = viewModelScope,
    started = WhileUiSubscribed,
    initialValue = emptyList()
  )

}
