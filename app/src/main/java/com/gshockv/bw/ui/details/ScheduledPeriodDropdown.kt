package com.gshockv.bw.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.gshockv.bw.data.SchedulePeriod

@Composable
fun ScheduledPeriodDropdown(
  schedulePeriod: SchedulePeriod,
  onSchedulePeriodSelected: (SchedulePeriod) -> Unit,
  modifier: Modifier = Modifier
) {
  var dropdownOpened by remember { mutableStateOf(false) }
  var dropdownPosition by remember { mutableStateOf(Offset.Zero) }
  val density = LocalDensity.current

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Repeat Period",
      modifier = Modifier.weight(1f),
      style = MaterialTheme.typography.titleMedium
    )
    Row(
      modifier = Modifier
        .clickable { dropdownOpened = true }
        .padding(start = 16.dp, top = 20.dp, bottom = 20.dp, end = 4.dp)
        .onGloballyPositioned { coords ->
          dropdownPosition = coords.positionInParent()
        }
    ) {
      Text(text = schedulePeriod.toString())
      Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = null
      )
    }
    DropdownMenu(
      expanded = dropdownOpened,
      onDismissRequest = { dropdownOpened = false },
      offset = with(density) {
        DpOffset(dropdownPosition.x.toDp() - 16.dp, dropdownPosition.y.toDp() - 80.dp)
      }
    ) {
      SchedulePeriod.entries.forEach {
        DropdownMenuItem(
          text = {
            Text(text = it.toString())
          },
          onClick = {
            dropdownOpened = false
            onSchedulePeriodSelected(it)
          }
        )
      }
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun PreviewDropdown() {
  ScheduledPeriodDropdown(
    schedulePeriod = SchedulePeriod.FIFTEEN_MINUTES,
    onSchedulePeriodSelected = {}
  )
}
