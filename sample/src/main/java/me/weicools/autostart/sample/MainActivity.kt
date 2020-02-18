package me.weicools.autostart.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.weicools.auto.starter.AutoStartPermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    rlAutoStart.setOnClickListener {
      val success = AutoStartPermissionHelper.getInstance().getAutoStartPermission(this@MainActivity)
      val message = if (success) "Successful" else "Failed"
      Toast.makeText(this@MainActivity, "Action $message", Toast.LENGTH_SHORT).show()
    }
  }
}
