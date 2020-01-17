package cc.taylorzhang.choosespecification.choose

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cc.taylorzhang.choosespecification.R
import cc.taylorzhang.choosespecification.entity.FactorEntity
import cc.taylorzhang.choosespecification.entity.FactorGroupEntity
import cc.taylorzhang.choosespecification.util.ConvertUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
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
    private var onFactorClickListener: ((factor: FactorEntity) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: FactorGroupEntity) {
        helper.setText(R.id.tvName, item.name)
        val recycleList = helper.getView<RecyclerView>(R.id.recycleList)
        if (recycleList.adapter == null) {
            recycleList.layoutManager = FlexboxLayoutManager(mContext)
            recycleList.adapter = SpecificationFactorAdapter().apply {
                setNewData(item.list)
                setOnItemClickListener { _, _, position ->
                    onFactorClickListener?.invoke(this.data[position])
                }
            }
        } else {
            (recycleList.adapter as SpecificationFactorAdapter).setNewData(item.list)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        initItemDecoration(recyclerView)
    }

    fun setOnFactorClickListener(listener: (factor: FactorEntity) -> Unit) {
        onFactorClickListener = listener
    }

    private fun initItemDecoration(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = ConvertUtil.dip2px(mContext, 20)
            }
        })
    }
}