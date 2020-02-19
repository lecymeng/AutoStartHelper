package me.weicools.autostart.sample

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weicools.auto.starter.AutoStartHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  companion object {
    const val TASK_INVALID = -1
    const val TASK_REQUEST_AUTO_START = 0
  }

  @IntDef(TASK_INVALID, TASK_REQUEST_AUTO_START)
  @Retention(AnnotationRetention.SOURCE)
  annotation class Task

  @Task
  private var currentTask = TASK_INVALID

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val autoStartListener = View.OnClickListener {
      val success = AutoStartHelper.getInstance().getAutoStartPermission(this@MainActivity, object : AutoStartHelper.PermissionCallbackAdapter() {
        override fun onStartHomePageSuccess() {
          Toast.makeText(this@MainActivity, "跳转手机管家主页成功", Toast.LENGTH_SHORT).show()
        }

        override fun onStartHomePageFailed() {
          Toast.makeText(this@MainActivity, "跳转手机管家主页失败", Toast.LENGTH_SHORT).show()
        }

        override fun onStartPermissionPageSuccess() {
          Toast.makeText(this@MainActivity, "跳转自启动授权页成功", Toast.LENGTH_SHORT).show()
        }

        override fun onStartPermissionPageFailed() {
          Toast.makeText(this@MainActivity, "跳转自启动授权页失败", Toast.LENGTH_SHORT).show()
        }
      })
      if (success) {
        currentTask = TASK_REQUEST_AUTO_START
      }
    }

    val sp = getSharedPreferences(AutoBootReceiver.PREF_FILE_BOOT_INFO, Context.MODE_PRIVATE)
    val receiveStatus = sp.getInt(AutoBootReceiver.PREF_KEY_RECEIVE_BOOT_ACTION_STATUS, AutoBootReceiver.RECEIVE_BOOT_ACTION_STATUS_UNKNOWN)
    if (receiveStatus == AutoBootReceiver.RECEIVE_BOOT_ACTION_STATUS_SUCCESS) {
      Toast.makeText(this, "AutoStart is enable", Toast.LENGTH_LONG).show()
    } else {
      Toast.makeText(this, "AutoStart is disable or unknown", Toast.LENGTH_LONG).show()
      rlAutoStart.setOnClickListener(autoStartListener)
    }
  }

  override fun onResume() {
    super.onResume()
    if (currentTask == TASK_REQUEST_AUTO_START) {
      AlertDialog.Builder(this)
        .setTitle("是否已授予自动启动权限")
        .setPositiveButton("已授权") { dialog, _ ->
          switchCompat.isChecked = true
          Toast.makeText(this@MainActivity, "已获得自动启动权限", Toast.LENGTH_SHORT).show()
          dialog?.dismiss()
        }
        .setNegativeButton("未授权") { dialog, _ ->
          switchCompat.isChecked = false
          Toast.makeText(this@MainActivity, "未获得自动启动权限", Toast.LENGTH_SHORT).show()
          dialog?.dismiss()
        }
        .setCancelable(false)
        .show()

      currentTask = TASK_INVALID
    }
  }
}
