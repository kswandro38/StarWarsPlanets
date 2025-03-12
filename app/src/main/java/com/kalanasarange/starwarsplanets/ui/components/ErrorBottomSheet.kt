package com.kalanasarange.starwarsplanets.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalanasarange.starwarsplanets.R

/**
 * Displays an error bottom sheet with a custom message and a retry button.
 *
 * @param isVisible Controls the visibility of the bottom sheet. When true, the bottom sheet is shown.
 *                  When false, the bottom sheet is hidden.
 * @param message The error message to be displayed in the bottom sheet. Defaults to a common error message
 *                defined in `R.string.error_common`.
 * @param buttonText The text to be displayed on the retry button. Defaults to "Retry" defined in
 *                  `R.string.error_button_retry`.
 * @param buttonCallback A callback function that is invoked when the retry button is clicked.
 *                       Typically used to trigger a retry action. Defaults to an empty lambda.
 *
 * Example Usage:
 * ```
 *   ErrorBottomSheet(
 *         isVisible = true,
 *         message = (responseState as ResponseState.Error).errorMessage,
 *         buttonText = stringResource(R.string.error_button_retry),
 *         buttonCallback = {
 *             isRefreshing = true
 *             viewModel.loadPlanetDetails(planetId)
 *         }
 *     )
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheet(
    isVisible: Boolean,
    message: String = stringResource(R.string.error_common),
    buttonText : String = stringResource(R.string.error_button_retry),
    buttonCallback: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    showBottomSheet = isVisible

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id= R.drawable.ic_common_error),
                    contentDescription = stringResource(R.string.cd_error_image),
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 32.dp)
                )
                Text(
                    text = message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {
                    showBottomSheet = false
                    buttonCallback()
                }) {
                    Text(buttonText)
                }
            }
        }
    } else null
}