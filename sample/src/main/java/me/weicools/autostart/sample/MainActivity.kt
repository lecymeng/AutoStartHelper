package me.weicools.autostart.sample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weicools.auto.starter.AutoStartPermissionHelper
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
      val success = AutoStartPermissionHelper.getInstance().getAutoStartPermission(this@MainActivity)
      var message = "Failed"
      if (success) {
        message = "Successful"
        currentTask = TASK_REQUEST_AUTO_START
      }
      Toast.makeText(this@MainActivity, "Action $message", Toast.LENGTH_SHORT).show()
    }

    rlAutoStart.setOnClickListener(autoStartListener)
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
