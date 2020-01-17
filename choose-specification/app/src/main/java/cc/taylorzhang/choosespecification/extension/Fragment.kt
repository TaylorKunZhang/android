package cc.taylorzhang.choosespecification.extension

import android.view.View
import androidx.fragment.app.Fragment

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : Fragment扩展
 *     version: 1.0.0
 * </pre>
 */
fun Fragment.gone(vararg arr: View) {
    arr.forEach {
        if (it.visibility != View.GONE) {
            it.visibility = View.GONE
        }
    }
}

fun Fragment.visible(vararg arr: View) {
    arr.forEach {
        if (it.visibility != View.VISIBLE) {
            it.visibility = View.VISIBLE
        }
    }
}

fun Fragment.invisible(vararg arr: View) {
    arr.forEach {
        if (it.visibility != View.INVISIBLE) {
            it.visibility = View.INVISIBLE
        }
    }
}