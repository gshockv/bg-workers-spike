package com.gshockv.bw.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gshockv.bw.ui.details.WorkerDetailsScreen
import com.gshockv.bw.ui.list.WorkersListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class DetailsRoute(val id: Int)

@Composable
fun MainAppScreen() {
  AppNavigationGraph(
    navController = rememberNavController(),
    viewModel = hiltViewModel()
  )
}

@Composable
private fun AppNavigationGraph(
  navController: NavHostController,
  viewModel: WorkersViewModel
) {
  NavHost(
    startDestination = ListRoute,
    navController = navController,
  ) {

    composable<ListRoute> {
      WorkersListScreen(
        viewModel = viewModel,
        onNewWorkerClick = {
          navController.navigate(DetailsRoute(id = 0))
        },
        onWorkerClick = { id ->
          navController.navigate(DetailsRoute(id = id))
        }
      )
    }

    composable<DetailsRoute> { backStackEntry ->
      val route: DetailsRoute = backStackEntry.toRoute()

      // TODO: Load worker details by ID

      WorkerDetailsScreen(
        worker = null,
        onBackClicked = {
          navController.popBackStack()
        }
      )
    }
  }
}
