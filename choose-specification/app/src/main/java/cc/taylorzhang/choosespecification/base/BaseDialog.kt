package cc.taylorzhang.choosespecification.base

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 弹窗基类
 *     version: 1.0.0
 * </pre>
 */
abstract class BaseDialog : DialogFragment(), IInitView {

    private var dismissListener: (() -> Unit)? = null
    private var cancelListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.let {
            if (getAnimationStyle() != Int.MAX_VALUE) {
                it.windowAnimations = getAnimationStyle()
            }
            dialog?.window?.attributes = it
        }
        dialog?.setCanceledOnTouchOutside(getCancelOutside())
        isCancelable = getCancelable()
        // 去掉dialog默认的padding
        dialog?.window?.decorView?.setPadding(0, 0, 0, 0)

        val layoutId = getLayoutId()
        val view: View
        if (layoutId == 0) {
            val layoutView = getLayoutView()
            if (layoutView != null) {
                view = layoutView
            } else {
                throw NullPointerException("ContentView can not be null")
            }
        } else {
            view = inflater.inflate(layoutId, container, false)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initVariable(savedInstanceState)
        initView(savedInstanceState)
        initListener()
        loadDataOnCreate()
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.attributes?.let {
            it.dimAmount = getDimAmount()
            it.width = getWidth()
            it.height = getHeight()
            it.gravity = getGravity()
            dialog?.window?.attributes = it
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        dismissListener?.invoke()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        cancelListener?.invoke()
    }

    protected open fun getCancelOutside(): Boolean = true

    protected open fun getCancelable(): Boolean = true

    protected open fun getDimAmount(): Float = 0.2f

    protected open fun getWidth(): Int = WindowManager.LayoutParams.MATCH_PARENT

    protected open fun getHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    protected open fun getGravity(): Int = Gravity.CENTER

    protected open fun getFragmentTag(): String = javaClass.name

    protected open fun getAnimationStyle(): Int = Int.MAX_VALUE

    fun showDialog(fragmentManager: FragmentManager) {
        showDialog(fragmentManager, getFragmentTag())
    }

    fun showDialog(fragmentManager: FragmentManager, tag: String) {
        if (isAdded) {
            fragmentManager.beginTransaction().show(this).commitAllowingStateLoss()
        } else {
            fragmentManager.beginTransaction().add(this, tag).commitAllowingStateLoss()
        }
    }

    fun dismissDialog() {
        dismissAllowingStateLoss()
    }

    fun setOnDismissListener(listener: () -> Unit) {
        dismissListener = listener
    }

    fun setOnCancelListener(listener: () -> Unit) {
        cancelListener = listener
    }
}