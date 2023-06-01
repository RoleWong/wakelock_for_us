package creativemaybeno.wakelockforus

import android.app.Activity
import android.view.WindowManager
import creativemaybeno.wakelockforus.Messages.IsEnabledMessage
import creativemaybeno.wakelockforus.Messages.ToggleMessage

internal class Wakelock {
  var activity: Activity? = null

  private val enabled
    get() = activity!!.window.attributes.flags and
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON != 0

  fun toggle(message: ToggleMessage) {
    if (activity == null) {
      throw NoActivityException()
    }

    val activity = this.activity!!
    val enabled = this.enabled

    if (message.enable!!) {
      if (!enabled) activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    } else if (enabled) {
      activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
  }

  fun isEnabled(): IsEnabledMessage {
    if (activity == null) {
      throw NoActivityException()
    }

    val msg = IsEnabledMessage()
    msg.enabled = enabled
    return msg
  }
}

class NoActivityException : Exception("wakelock requires a foreground activity")
