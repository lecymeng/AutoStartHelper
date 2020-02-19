package me.weicools.autostart.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.annotation.IntDef

/**
 * @author Weicools Create on 2020.02.19
 *
 * desc:
 */
class AutoBootReceiver : BroadcastReceiver() {
  companion object {
    private const val TAG = "AutoBootReceiver"
    const val PREF_FILE_BOOT_INFO = "pref_file_boot_info"
    const val PREF_KEY_RECEIVE_BOOT_ACTION_STATUS = "PREF_KEY_RECEIVE_BOOT_ACTION_STATUS"

    const val RECEIVE_BOOT_ACTION_STATUS_UNKNOWN = -1
    const val RECEIVE_BOOT_ACTION_STATUS_FAILED = 0
    const val RECEIVE_BOOT_ACTION_STATUS_SUCCESS = 1
  }

  @IntDef(RECEIVE_BOOT_ACTION_STATUS_UNKNOWN, RECEIVE_BOOT_ACTION_STATUS_FAILED, RECEIVE_BOOT_ACTION_STATUS_SUCCESS)
  @Retention(AnnotationRetention.SOURCE)
  public annotation class ReceiveBootActionStatus

  override fun onReceive(context: Context?, intent: Intent?) {
    if (intent == null || context == null) {
      Log.e(TAG, "receive boot intent == null")
      return
    }

    if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
      val ed = context.getSharedPreferences(PREF_FILE_BOOT_INFO, Context.MODE_PRIVATE).edit()
      ed.putInt(PREF_KEY_RECEIVE_BOOT_ACTION_STATUS, RECEIVE_BOOT_ACTION_STATUS_SUCCESS)
      ed.apply()

      val msg = "receive boot completed success"
      Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
      Log.e(TAG, msg)

      try {
        context.startActivity(context.packageManager.getLaunchIntentForPackage(context.packageName))
      } catch (ex: Exception) {
        Log.e(TAG, msg + ", startActivity Exception=" + ex.message)
      }
    }
  }
}