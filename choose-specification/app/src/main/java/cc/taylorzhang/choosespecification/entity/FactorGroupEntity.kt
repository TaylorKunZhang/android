package cc.taylorzhang.choosespecification.entity

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 因子组
 *     version: 1.0.0
 * </pre>
 */
class FactorGroupEntity {
    // 因子组id
    var id = 0

    // 因子组名称
    var name = ""

    // 因子列表
    var list = ArrayList<FactorEntity>()
}