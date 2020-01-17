package cc.taylorzhang.choosespecification.entity

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 规格（SKU）
 *     version: 1.0.0
 * </pre>
 */
class SpecificationEntity {
    // 规格id
    var id = 0

    // 原价（单位为分）
    var originalPrice = 0

    // 折扣价（单位为分），如果折扣价等于原价，则没有折扣价。
    var discountPrice = 0

    // 规格所包含的因子id
    var factorIdList = ArrayList<Int>()
}