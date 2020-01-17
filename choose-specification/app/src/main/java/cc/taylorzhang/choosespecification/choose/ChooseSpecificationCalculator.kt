package cc.taylorzhang.choosespecification.choose

import cc.taylorzhang.choosespecification.entity.FactorEntity
import cc.taylorzhang.choosespecification.entity.FactorGroupEntity
import cc.taylorzhang.choosespecification.entity.ProductEntity
import cc.taylorzhang.choosespecification.entity.SpecificationEntity

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 选择规格计算器
 *     version: 1.0.0
 * </pre>
 */
class ChooseSpecificationCalculator(private val product: ProductEntity) {
    // 选中因子列表
    private val selectedFactorList = ArrayList<FactorEntity>()
    // 选中因子id列表
    private val selectedFactorIdList = ArrayList<Int>()

    init {
        initList()
    }

    fun selectedFactor(factor: FactorEntity) {
        if (factor.status != FactorEntity.Status.AVAILABLE) {
            return
        }

        // 如果当前因子组存在选中因子，则将该因子变为可用状态
        val factorGroup = product.factorGroupList.find { it.list.contains(factor) }!!
        factorGroup.list.find { it.status == FactorEntity.Status.SELECTED }?.let {
            removeSelectedFactor(it)
        }

        addSelectedFactor(factor)
        updateAllFactorStatus(factor)
    }

    fun unselectedFactor(factor: FactorEntity) {
        if (factor.status != FactorEntity.Status.SELECTED) {
            return
        }

        removeSelectedFactor(factor)
        updateAllFactorStatus(factor)
    }

    fun getSelectedSpecification(): SpecificationEntity? {
        if (selectedFactorList.isEmpty()) {
            return null
        }

        // 对包含选中因子列表中第一个的因子的规格进行遍历，查看当前选中的因子列表是否能构成一个规格
        return selectedFactorList.first().specificationList.find { specification ->
            specification.factorIdList.size == selectedFactorList.size &&
                    specification.factorIdList.containsAll(selectedFactorIdList)
        }
    }

    private fun initList() {
        // 初始化因子可用状态
        if (!product.isInitFactorSpecificationList) {
            // 遍历每一个因子，找到包含该因子的规格
            product.factorGroupList.forEach { factorGroup ->
                factorGroup.list.forEach { factor ->
                    factor.specificationList.addAll(product.specificationList.filter {
                        it.factorIdList.contains(factor.id)
                    })
                }
            }

            // 移除没有规格的因子
            product.factorGroupList.forEach { factorGroup ->
                factorGroup.list = factorGroup.list.filter {
                    it.specificationList.isNotEmpty()
                } as ArrayList<FactorEntity>
            }

            // 移除没有因子的因子组
            product.factorGroupList = product.factorGroupList.filter {
                it.list.isNotEmpty()
            } as ArrayList<FactorGroupEntity>

            product.isInitFactorSpecificationList = true
        }

        // 默认所有因子可用（经过上一步的筛选，剩下的因子至少包含一个规格，在没有选中因子的时候，所有的因子都是可用的）
        product.factorGroupList.forEach { factorGroup ->
            factorGroup.list.forEach { factor ->
                factor.status = FactorEntity.Status.AVAILABLE
            }
        }

        // 默认选中因子列表中顺序靠前的因子组合（该组合必须构成一个规格）
        run breaking@{
            product.factorGroupList.forEach forEach1@{ factorGroup ->
                factorGroup.list.forEach forEach2@{ factor ->
                    if (factor.status == FactorEntity.Status.AVAILABLE) {
                        // 选中该因子
                        selectedFactor(factor)
                        if (getSelectedSpecification() != null) {
                            // 找到规格，结束寻找
                            return@breaking
                        }
                        return@forEach1
                    }
                }
            }
        }
    }

    private fun addSelectedFactor(factor: FactorEntity) {
        factor.status = FactorEntity.Status.SELECTED
        selectedFactorList.add(factor)
        selectedFactorIdList.add(factor.id)
    }

    private fun removeSelectedFactor(factor: FactorEntity) {
        factor.status = FactorEntity.Status.AVAILABLE
        selectedFactorList.remove(factor)
        selectedFactorIdList.remove(factor.id)
    }

    private fun updateAllFactorStatus(currentFactor: FactorEntity) {
        // 用户目前选中的所有因子id，在判断因子组中其他因子是否可用的时候，会剔除因子组选中的因子id
        val factorIdList = ArrayList<Int>()

        product.factorGroupList.forEach { factorGroup ->
            if (!factorGroup.list.contains(currentFactor)) {
                // 只处理不包含当前操作因子的因子组
                factorIdList.clear()
                factorIdList.addAll(selectedFactorIdList)
                factorGroup.list.find { it.status == FactorEntity.Status.SELECTED }?.let {
                    // 剔除当前因子组选中的因子id
                    factorIdList.remove(it.id)
                }

                factorGroup.list.forEach { factor ->
                    if (factor.status != FactorEntity.Status.SELECTED) {
                        // 只对非选中因子进行判断
                        val find = factor.specificationList.find { specification ->
                            specification.factorIdList.containsAll(factorIdList)
                        }
                        if (find == null) {
                            factor.status = FactorEntity.Status.DISABLED
                        } else {
                            factor.status = FactorEntity.Status.AVAILABLE
                        }
                    }
                }
            }
        }
    }
}