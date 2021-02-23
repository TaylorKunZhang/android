package cc.taylorzhang.choosespecification.choose

import android.graphics.drawable.ShapeDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cc.taylorzhang.choosespecification.R
import cc.taylorzhang.choosespecification.entity.FactorEntity
import cc.taylorzhang.choosespecification.entity.FactorGroupEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 因子组适配器
 *     version: 1.0.0
 * </pre>
 */
class SpecificationFactorGroupAdapter : BaseQuickAdapter<FactorGroupEntity, BaseViewHolder>(
    R.layout.recycle_item_factor_group
) {
    private var mOnFactorClickListener: ((factor: FactorEntity) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: FactorGroupEntity) {
        helper.setText(R.id.tvName, item.name)
        helper.getView<RecyclerView>(R.id.recycleList).let {
            if (it.adapter == null) {
                it.layoutManager = FlexboxLayoutManager(mContext)
                it.adapter = SpecificationFactorAdapter().also { adapter ->
                    adapter.setOnItemClickListener { _, _, position ->
                        mOnFactorClickListener?.invoke(adapter.data[position])
                    }
                }

                val itemDecoration = FlexboxItemDecoration(it.context)
                itemDecoration.setDrawable(ShapeDrawable().apply {
                    paint.color = ContextCompat.getColor(mContext, android.R.color.transparent)
                    intrinsicWidth = mContext.resources.getDimensionPixelSize(R.dimen.dp_12)
                    intrinsicHeight = mContext.resources.getDimensionPixelSize(R.dimen.dp_10)
                })
                it.addItemDecoration(itemDecoration)
            }

            (it.adapter as SpecificationFactorAdapter).setNewData(item.list)
        }
    }

    fun setOnFactorClickListener(listener: (factor: FactorEntity) -> Unit) {
        mOnFactorClickListener = listener
    }
}