package cc.taylorzhang.choosespecification.util

import android.content.Context
import android.util.TypedValue

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2020/01/15
 *     desc   : 尺寸转换工具类
 *     version: 1.0.0
 * </pre>
 */
object ConvertUtil {

    fun dip2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics
        ).toInt()
    }
}