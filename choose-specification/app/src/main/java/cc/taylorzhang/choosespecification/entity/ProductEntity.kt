package cc.taylorzhang.choosespecification.entity

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 商品
 *     version: 1.0.0
 * </pre>
 */
class ProductEntity {
    // 商品id
    var id = 0

    // 商品名称
    var name = ""

    // 因子组列表
    var factorGroupList = ArrayList<FactorGroupEntity>()

    // 规格列表
    var specificationList = ArrayList<SpecificationEntity>()

    // 是否初始化因子的规格列表
    var isInitFactorSpecificationList = false
}