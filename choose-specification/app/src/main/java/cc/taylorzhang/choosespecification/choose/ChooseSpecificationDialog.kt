package cc.taylorzhang.choosespecification.choose

import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.taylorzhang.choosespecification.R
import cc.taylorzhang.choosespecification.base.BaseDialog
import cc.taylorzhang.choosespecification.entity.FactorEntity
import cc.taylorzhang.choosespecification.entity.ProductEntity
import cc.taylorzhang.choosespecification.entity.SpecificationEntity
import cc.taylorzhang.choosespecification.extension.invisible
import cc.taylorzhang.choosespecification.extension.visible
import kotlinx.android.synthetic.main.dialog_choose_specification.*

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 选择规格弹窗
 *     version: 1.0.0
 * </pre>
 */
class ChooseSpecificationDialog : BaseDialog() {

    // 商品
    private var mProduct: ProductEntity? = null

    // 规格因子组适配器
    private val mFactorGroupAdapter = SpecificationFactorGroupAdapter()

    // 选好规格监听
    private var mOnSelectedListener: ((
        product: ProductEntity, specification: SpecificationEntity, count: Int
    ) -> Unit)? = null

    // 选择规格计算器
    private var mSpecificationCalculator: ChooseSpecificationCalculator? = null

    // 当前选择的数量
    private var mCount = 1

    // 当前选择的规格
    private var mCurrentSpecification: SpecificationEntity? = null

    override fun getLayoutId(): Int = R.layout.dialog_choose_specification

    override fun getGravity(): Int = Gravity.BOTTOM

    override fun getHeight(): Int = WindowManager.LayoutParams.MATCH_PARENT

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)

        if (mProduct == null) {
            dismissDialog()
            return
        }

        mSpecificationCalculator = ChooseSpecificationCalculator(mProduct!!)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        // 设置删除线
        tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        initRecyclerView()

        // 显示商品名称
        mProduct?.let {
            tvServiceName.text = it.name
            mFactorGroupAdapter.setNewData(it.factorGroupList)
        }

        checkSelectedSpecification()
    }

    override fun initListener() {
        super.initListener()

        flRoot.setOnClickListener {
            // 关闭弹窗
            dismissDialog()
        }

        llRoot.setOnClickListener {
            // 只消费点击事件，不要处理
        }

        btnSelected.setOnClickListener {
            // 回调选好规格监听，关闭弹窗
            if (mProduct != null && mCurrentSpecification != null) {
                mOnSelectedListener?.invoke(mProduct!!, mCurrentSpecification!!, mCount)
                dismissDialog()
            }
        }

        ibClose.setOnClickListener {
            // 关闭弹窗
            dismissDialog()
        }

        ibMinusService.setOnClickListener {
            // 减少规格数量
            if (mProduct != null && mCurrentSpecification != null && mCount > 1) {
                mCount--
                updatePrice()
            }
        }

        ibPlusService.setOnClickListener {
            // 增加规格数量
            if (mProduct != null && mCurrentSpecification != null) {
                mCount++
                updatePrice()
            }
        }

        mFactorGroupAdapter.setOnFactorClickListener { factor ->
            // 处理因子子项点击
            handleFactorItemClick(factor)
        }
    }

    fun onSelected(
        listener: (product: ProductEntity, specification: SpecificationEntity, count: Int) -> Unit
    ): ChooseSpecificationDialog {
        mOnSelectedListener = listener
        return this
    }

    fun showDialog(product: ProductEntity, manager: FragmentManager) {
        this.mProduct = product
        showDialog(manager)
    }

    private fun initRecyclerView() {
        recycleFactorGroup.layoutManager = LinearLayoutManager(context)
        mFactorGroupAdapter.bindToRecyclerView(recycleFactorGroup)

        recycleFactorGroup.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = resources.getDimensionPixelSize(R.dimen.dp_20)
            }
        })
    }

    private fun checkSelectedSpecification() {
        mCurrentSpecification = mSpecificationCalculator?.getSelectedSpecification()
        if (mCurrentSpecification == null) {
            btnSelected.setBackgroundResource(R.drawable.rect_gray_f2_radius_25)
            btnSelected.setTextColor(ContextCompat.getColor(context!!, R.color.gray_C8))
            invisible(btnSelectedShadow, clPrice)
        } else {
            btnSelected.setBackgroundResource(R.drawable.rect_green_12_radius_25)
            btnSelected.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            visible(btnSelectedShadow, clPrice)
            updatePrice()
        }
    }

    private fun updatePrice() {
        tvCount.text = mCount.toString()
        if (mCurrentSpecification!!.discountPrice == mCurrentSpecification!!.originalPrice) {
            // 折扣价和原价相等，则没有折扣价，否则有折扣价
            invisible(tvOriginalPrice)
            tvActualPrice.text = formatCent(mCurrentSpecification!!.originalPrice * mCount)
            tvActualPrice.setTextColor(ContextCompat.getColor(context!!, R.color.green_12))
        } else {
            visible(tvOriginalPrice)
            tvActualPrice.text = formatCent(mCurrentSpecification!!.discountPrice * mCount)
            tvActualPrice.setTextColor(ContextCompat.getColor(context!!, R.color.red_EE))
            tvOriginalPrice.text = formatCent(mCurrentSpecification!!.originalPrice * mCount)
        }
    }

    private fun handleFactorItemClick(factor: FactorEntity) {
        when (factor.status) {
            FactorEntity.Status.DISABLED -> return
            FactorEntity.Status.AVAILABLE -> mSpecificationCalculator?.selectedFactor(factor)
            FactorEntity.Status.SELECTED -> mSpecificationCalculator?.unselectedFactor(factor)
        }
        mFactorGroupAdapter.notifyDataSetChanged()
        checkSelectedSpecification()
    }

    private fun formatCent(money: Int): String {
        return if (money / 100 * 100 == money) {
            "¥ ${money / 100}"
        } else {
            "¥ ${money.toDouble() / 100}"
        }
    }
}