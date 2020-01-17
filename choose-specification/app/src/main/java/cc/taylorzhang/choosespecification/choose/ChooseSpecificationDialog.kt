package cc.taylorzhang.choosespecification.choose

import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
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
    private var product: ProductEntity? = null
    // 规格因子组适配器
    private val factorGroupAdapter =
        SpecificationFactorGroupAdapter()
    // 选好规格监听
    private var onSelectedListener: ((
        product: ProductEntity, specification: SpecificationEntity, count: Int
    ) -> Unit)? = null
    // 选择规格计算器
    private var specificationCalculator: ChooseSpecificationCalculator? = null

    // 当前选择的数量
    private var count = 1
    // 当前选择的规格
    private var currentSpecification: SpecificationEntity? = null

    override fun getLayoutId(): Int = R.layout.dialog_choose_specification

    override fun getGravity(): Int = Gravity.BOTTOM

    override fun initVariable(savedInstanceState: Bundle?) {
        super.initVariable(savedInstanceState)

        if (product == null) {
            dismissDialog()
            return
        }

        specificationCalculator = ChooseSpecificationCalculator(product!!)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        // 设置删除线
        tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        recycleFactorGroup.layoutManager = LinearLayoutManager(context)
        factorGroupAdapter.bindToRecyclerView(recycleFactorGroup)

        // 显示商品名称
        product?.let {
            tvServiceName.text = it.name
            factorGroupAdapter.setNewData(it.factorGroupList)
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
            if (product != null && currentSpecification != null) {
                onSelectedListener?.invoke(product!!, currentSpecification!!, count)
                dismissDialog()
            }
        }

        ibClose.setOnClickListener {
            // 关闭弹窗
            dismissDialog()
        }

        ibMinusService.setOnClickListener {
            // 减少规格数量
            if (product != null && currentSpecification != null && count > 1) {
                count--
                updatePrice()
            }
        }

        ibPlusService.setOnClickListener {
            // 增加规格数量
            if (product != null && currentSpecification != null) {
                count++
                updatePrice()
            }
        }

        factorGroupAdapter.setOnFactorClickListener { factor ->
            // 处理因子子项点击
            handleFactorItemClick(factor)
        }
    }

    fun onSelected(
        listener: (product: ProductEntity, specification: SpecificationEntity, count: Int) -> Unit
    ): ChooseSpecificationDialog {
        onSelectedListener = listener
        return this
    }

    fun showDialog(product: ProductEntity, manager: FragmentManager) {
        this.product = product
        showDialog(manager)
    }

    private fun checkSelectedSpecification() {
        currentSpecification = specificationCalculator?.getSelectedSpecification()
        if (currentSpecification == null) {
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
        tvCount.text = count.toString()
        if (currentSpecification!!.discountPrice == currentSpecification!!.originalPrice) {
            // 折扣价和原价相等，则没有折扣价，否则有折扣价
            invisible(tvOriginalPrice)
            tvActualPrice.text = formatCent(currentSpecification!!.originalPrice * count)
            tvActualPrice.setTextColor(ContextCompat.getColor(context!!, R.color.green_12))
        } else {
            visible(tvOriginalPrice)
            tvActualPrice.text = formatCent(currentSpecification!!.discountPrice * count)
            tvActualPrice.setTextColor(ContextCompat.getColor(context!!, R.color.red_EE))
            tvOriginalPrice.text = formatCent(currentSpecification!!.originalPrice * count)
        }
    }

    private fun handleFactorItemClick(factor: FactorEntity) {
        when (factor.status) {
            FactorEntity.Status.DISABLED -> return
            FactorEntity.Status.AVAILABLE -> specificationCalculator?.selectedFactor(factor)
            FactorEntity.Status.SELECTED -> specificationCalculator?.unselectedFactor(factor)
        }

        (recycleFactorGroup.layoutManager as LinearLayoutManager).let {
            val firstVisibleItemPosition = it.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = it.findLastVisibleItemPosition()

            for (position in firstVisibleItemPosition..lastVisibleItemPosition) {
                val recycleView =
                    factorGroupAdapter.getViewByPosition(position, R.id.recycleList) as RecyclerView
                (recycleView.adapter as SpecificationFactorAdapter).let { adapter ->
                    adapter.data.indices.forEach { index ->
                        adapter.notifyItemChanged(
                            index,
                            SpecificationFactorAdapter.UPDATE_TYPE_STATUS
                        )
                    }
                }
            }
        }

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