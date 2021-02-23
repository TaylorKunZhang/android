package cc.taylorzhang.choosespecification.choose

import androidx.core.content.ContextCompat
import cc.taylorzhang.choosespecification.R
import cc.taylorzhang.choosespecification.entity.FactorEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 因子适配器
 *     version: 1.0.0
 * </pre>
 */
class SpecificationFactorAdapter : BaseQuickAdapter<FactorEntity, BaseViewHolder>(
    R.layout.recycle_item_factor
) {
    companion object {
        // 更新因子状态
        const val UPDATE_TYPE_STATUS = 1
    }

    override fun convert(helper: BaseViewHolder, item: FactorEntity) {
        helper.setText(R.id.tvName, item.name)
        updateStatus(helper, item)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            handleLocalRefresh(holder, mData[holder.layoutPosition - headerLayoutCount], payloads)
        }
    }

    private fun handleLocalRefresh(
        helper: BaseViewHolder,
        item: FactorEntity,
        payloads: MutableList<Any>
    ) {
        when (payloads.first() as Int) {
            UPDATE_TYPE_STATUS -> updateStatus(helper, item)
        }
    }

    private fun updateStatus(helper: BaseViewHolder, item: FactorEntity) {
        when (item.status) {
            FactorEntity.Status.DISABLED -> {
                helper.setTextColor(R.id.tvName, ContextCompat.getColor(mContext, R.color.gray_C8))
                helper.setBackgroundRes(R.id.tvName, R.drawable.rect_gray_f2_radius_20)
            }
            FactorEntity.Status.AVAILABLE -> {
                helper.setTextColor(R.id.tvName, ContextCompat.getColor(mContext, R.color.black_1F))
                helper.setBackgroundRes(R.id.tvName, R.drawable.rect_gray_f2_radius_20)
            }
            FactorEntity.Status.SELECTED -> {
                helper.setTextColor(R.id.tvName, ContextCompat.getColor(mContext, R.color.green_12))
                helper.setBackgroundRes(R.id.tvName, R.drawable.factor_selected_bg)
            }
        }
    }
}