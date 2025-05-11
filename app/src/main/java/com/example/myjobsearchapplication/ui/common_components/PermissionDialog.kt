//package com.example.myjobsearchapplication.ui.common_components
//
//
//import androidx.compose.material3.AlertDialog
//import androidx.compose.runtime.Composable
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//
//@Composable
//fun PermissionDialog(
//    PermissionTextProvider: PermissionTextProvider,
//    isPermanentlyDeclined: Boolean,
//    onDismiss: () -> Unit,
//    onClick: () -> Unit,
//    onGoToAppSettings: () -> Unit
//){
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            if(isPermanentlyDeclined){
//                TextButton(onClick = onGoToAppSettings) {
//                    Text(text = "Grant Permission")
//                }
//            }else{
//                TextButton(onClick = onClick) {
//                    Text(text = "OK")
//                }
//            }
//        },
//        title = {
//            Text(text = "Permission required")
//        },
//        text = {
//            Text(text = PermissionTextProvider.getDescription(
//                isPermanentlyDeclined = isPermanentlyDeclined
//            ))
//        }
//    )
//}
//
//
//interface PermissionTextProvider {
//    fun getDescription(isPermanentlyDeclined: Boolean): String
//}
//
//class Notification_PermissionTextProvider: PermissionTextProvider {
//    override fun getDescription(isPermanentlyDeclined: Boolean): String {
//        return if(isPermanentlyDeclined) {
//            "It seems you permanently declined notification permission. " +
//                    "You can go to the app settings to grant it."
//        } else {
//            "This app needs access to notifications so that you be able " +
//                    "to set reminders."
//        }
//    }
//}
