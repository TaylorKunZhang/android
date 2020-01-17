package cc.taylorzhang.choosespecification.entity

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 因子
 *     version: 1.0.0
 * </pre>
 */
class FactorEntity {

    // 因子id
    var id = 0

    // 因子名称
    var name = ""

    // 因子状态
    var status = Status.AVAILABLE

    // 包含因子的规格列表
    var specificationList = ArrayList<SpecificationEntity>()

    /**
     * 因子状态
     */
    enum class Status {
        /**
         * 不可用
         */
        DISABLED,
        /**
         * 可用
         */
        AVAILABLE,
        /**
         * 选中
         */
        SELECTED
    }
}