package com.example.firstmultiplatformproject.android.presentation.ui.hilt_compose_recipe

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun BatteryStatus(context: HiltComposeRecipeActivity) {

    val receivedNewState = remember { mutableStateOf(false) }

    val isChargingState = remember { mutableStateOf(false) }
    val acChargeState = remember { mutableStateOf(false) }
    val usbChargeState = remember { mutableStateOf(false) }

    if( receivedNewState.value )
        SnackbarSample(isChargingState.value, usbChargeState.value, acChargeState.value)

    SystemBroadcastReceiver(Intent.ACTION_BATTERY_CHANGED) { batteryStatus ->

        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        // How are we charging?
        val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

        isChargingState.value = isCharging
        usbChargeState.value = usbCharge
        acChargeState.value = acCharge

        receivedNewState.value = true

        Toast.makeText(
            context,
            "Phone carhing: ${isCharging}, Usb charge: ${usbCharge}, Ac charge: ${acCharge}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackbarSample(isChargingState: Boolean, usbChargeState: Boolean, acChargeState: Boolean) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val modifier = Modifier

    Box(modifier.fillMaxSize()) {
//        Button(onClick = {
//            coroutineScope.launch {
//                snackbarHostState.showSnackbar(message = "Phone carhing: ${isChargingState}, Usb charge: ${usbChargeState}, Ac charge: ${acChargeState}", "", duration = SnackbarDuration.Short )
//            }
//        }) {
//            Text(text = "Click me!")
//        }

        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = "Phone carhing: ${isChargingState}, Usb charge: ${usbChargeState}, Ac charge: ${acChargeState}", "", duration = SnackbarDuration.Short )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun SystemBroadcastReceiver(
    systemAction: String,
    onSystemEvent: (intent: Intent?) -> Unit
) {
    // Grab the current context in this part of the UI tree
    val context = LocalContext.current

    // Safely use the latest onSystemEvent lambda passed to the function
    //val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)

    // If either context or systemAction changes, unregister and register again
    DisposableEffect(context, systemAction) {
        val intentFilter = IntentFilter(systemAction)
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onSystemEvent(intent)
            }
        }

        context.registerReceiver(broadcast, intentFilter)

        // When the effect leaves the Composition, remove the callback
        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}

