package cc.taylorzhang.choosespecification

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.taylorzhang.choosespecification.choose.ChooseSpecificationDialog
import cc.taylorzhang.choosespecification.entity.ProductEntity
import cc.taylorzhang.choosespecification.util.JsonUtil
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2019/12/30
 *     desc   : 主界面
 *     version: 1.0.0
 * </pre>
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<Pair<String, ProductEntity>>()
        getProduct1().let { list.add(it.name to it) }
        getProduct2().let { list.add(it.name to it) }
        getProduct3().let { list.add(it.name to it) }

        listView.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list.map { it.first })

        listView.setOnItemClickListener { _, _, position, _ ->
            ChooseSpecificationDialog()
                .onSelected { product, specification, count ->
                    Toast.makeText(
                        this,
                        "${product.name} specification id is ${specification.id}, count is $count",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .showDialog(list[position].second, supportFragmentManager)
        }
    }

    private fun getProduct1(): ProductEntity {
        val json = """
            {
              "id": 1,
              "name": "前保险杠",
              "factorGroupList": [
                {
                  "id": 10,
                  "name": "漆类",
                  "list": [
                    {
                      "id": 11,
                      "name": "普通漆"
                    },
                    {
                      "id": 12,
                      "name": "金属漆"
                    }
                  ]
                },
                {
                  "id": 20,
                  "name": "钣金",
                  "list": [
                    {
                      "id": 21,
                      "name": "普通钣金"
                    },
                    {
                      "id": 22,
                      "name": "复杂钣金"
                    }
                  ]
                }
              ],
              "specificationList": [
                {
                  "id": 101,
                  "originalPrice": 1000,
                  "discountPrice": 1000,
                  "factorIdList": [
                    11
                  ]
                },
                {
                  "id": 102,
                  "originalPrice": 1100,
                  "discountPrice": 1100,
                  "factorIdList": [
                    12
                  ]
                },
                {
                  "id": 103,
                  "originalPrice": 2000,
                  "discountPrice": 1500,
                  "factorIdList": [
                    11,
                    21
                  ]
                },
                {
                  "id": 104,
                  "originalPrice": 2500,
                  "discountPrice": 2000,
                  "factorIdList": [
                    11,
                    22
                  ]
                },
                {
                  "id": 105,
                  "originalPrice": 3000,
                  "discountPrice": 2500,
                  "factorIdList": [
                    12,
                    21
                  ]
                }
              ]
            }
        """.trimIndent()
        return JsonUtil.parseObject(json, ProductEntity::class.java)!!
    }

    private fun getProduct2(): ProductEntity {
        val json = """
            {
              "id": 2,
              "name": "后保险杠",
              "factorGroupList": [
                {
                  "id": 10,
                  "name": "漆类",
                  "list": [
                    {
                      "id": 11,
                      "name": "普通漆"
                    },
                    {
                      "id": 12,
                      "name": "金属漆"
                    }
                  ]
                },
                {
                  "id": 20,
                  "name": "钣金",
                  "list": [
                    {
                      "id": 21,
                      "name": "普通钣金"
                    },
                    {
                      "id": 22,
                      "name": "复杂钣金"
                    }
                  ]
                }
              ],
              "specificationList": [
                {
                  "id": 201,
                  "originalPrice": 1100,
                  "discountPrice": 1100,
                  "factorIdList": [
                    12
                  ]
                },
                {
                  "id": 202,
                  "originalPrice": 1200,
                  "discountPrice": 1200,
                  "factorIdList": [
                    21
                  ]
                },
                {
                  "id": 203,
                  "originalPrice": 1300,
                  "discountPrice": 1300,
                  "factorIdList": [
                    22
                  ]
                },
                {
                  "id": 204,
                  "originalPrice": 2500,
                  "discountPrice": 2000,
                  "factorIdList": [
                    11,
                    22
                  ]
                },
                {
                  "id": 205,
                  "originalPrice": 2500,
                  "discountPrice": 2100,
                  "factorIdList": [
                    12,
                    21
                  ]
                },
                {
                  "id": 206,
                  "originalPrice": 2500,
                  "discountPrice": 2200,
                  "factorIdList": [
                    12,
                    22
                  ]
                }
              ]
            }
        """.trimIndent()
        return JsonUtil.parseObject(json, ProductEntity::class.java)!!
    }

    private fun getProduct3(): ProductEntity {
        val json = """
            {
              "id": 3,
              "name": "前车盖",
              "factorGroupList": [
                {
                  "id": 10,
                  "name": "漆类",
                  "list": [
                    {
                      "id": 11,
                      "name": "普通漆"
                    },
                    {
                      "id": 12,
                      "name": "金属漆"
                    },
                    {
                      "id": 13,
                      "name": "珠光漆"
                    },
                    {
                      "id": 14,
                      "name": "清漆"
                    },
                    {
                      "id": 15,
                      "name": "哑光漆"
                    }
                  ]
                },
                {
                  "id": 20,
                  "name": "钣金",
                  "list": [
                    {
                      "id": 21,
                      "name": "普通钣金"
                    },
                    {
                      "id": 22,
                      "name": "复杂钣金"
                    }
                  ]
                },
                {
                  "id": 30,
                  "name": "细分车型",
                  "list": [
                    {
                      "id": 31,
                      "name": "微型车"
                    },
                    {
                      "id": 32,
                      "name": "小型车"
                    },
                    {
                      "id": 33,
                      "name": "紧凑车型"
                    },
                    {
                      "id": 34,
                      "name": "中型车"
                    },
                    {
                      "id": 35,
                      "name": "中大型车"
                    },
                    {
                      "id": 36,
                      "name": "豪华车"
                    },
                    {
                      "id": 37,
                      "name": "SUV"
                    },
                    {
                      "id": 38,
                      "name": "MPV"
                    },
                    {
                      "id": 39,
                      "name": "面包车"
                    }
                  ]
                }
              ],
              "specificationList": [
                {
                  "id": 301,
                  "originalPrice": 800,
                  "discountPrice": 800,
                  "factorIdList": [
                    11,
                    21
                  ]
                },
                {
                  "id": 302,
                  "originalPrice": 900,
                  "discountPrice": 900,
                  "factorIdList": [
                    11,
                    22
                  ]
                },
                {
                  "id": 303,
                  "originalPrice": 1000,
                  "discountPrice": 1000,
                  "factorIdList": [
                    12,
                    21
                  ]
                },
                {
                  "id": 304,
                  "originalPrice": 1100,
                  "discountPrice": 1100,
                  "factorIdList": [
                    13,
                    21
                  ]
                },
                {
                  "id": 305,
                  "originalPrice": 1200,
                  "discountPrice": 1200,
                  "factorIdList": [
                    13,
                    22
                  ]
                },
                {
                  "id": 306,
                  "originalPrice": 3000,
                  "discountPrice": 2000,
                  "factorIdList": [
                    14,
                    21,
                    31
                  ]
                },
                {
                  "id": 307,
                  "originalPrice": 3000,
                  "discountPrice": 2100,
                  "factorIdList": [
                    14,
                    21,
                    32
                  ]
                },
                {
                  "id": 308,
                  "originalPrice": 3000,
                  "discountPrice": 2200,
                  "factorIdList": [
                    14,
                    21,
                    33
                  ]
                },
                {
                  "id": 309,
                  "originalPrice": 3000,
                  "discountPrice": 2300,
                  "factorIdList": [
                    14,
                    21,
                    34
                  ]
                },
                {
                  "id": 310,
                  "originalPrice": 3000,
                  "discountPrice": 2400,
                  "factorIdList": [
                    14,
                    21,
                    35
                  ]
                },
                {
                  "id": 311,
                  "originalPrice": 3000,
                  "discountPrice": 2500,
                  "factorIdList": [
                    15,
                    22,
                    36
                  ]
                },
                {
                  "id": 312,
                  "originalPrice": 3000,
                  "discountPrice": 2600,
                  "factorIdList": [
                    15,
                    22,
                    37
                  ]
                },
                {
                  "id": 313,
                  "originalPrice": 3000,
                  "discountPrice": 2700,
                  "factorIdList": [
                    15,
                    22,
                    38
                  ]
                },
                {
                  "id": 314,
                  "originalPrice": 3000,
                  "discountPrice": 2800,
                  "factorIdList": [
                    15,
                    22,
                    39
                  ]
                },
                {
                  "id": 315,
                  "originalPrice": 4000,
                  "discountPrice": 2900,
                  "factorIdList": [
                    11,
                    22,
                    31
                  ]
                },
                {
                  "id": 316,
                  "originalPrice": 4000,
                  "discountPrice": 3000,
                  "factorIdList": [
                    11,
                    22,
                    33
                  ]
                },
                {
                  "id": 317,
                  "originalPrice": 4000,
                  "discountPrice": 3100,
                  "factorIdList": [
                    11,
                    22,
                    35
                  ]
                },
                {
                  "id": 318,
                  "originalPrice": 4000,
                  "discountPrice": 2900,
                  "factorIdList": [
                    12,
                    22,
                    31
                  ]
                },
                {
                  "id": 319,
                  "originalPrice": 4000,
                  "discountPrice": 3100,
                  "factorIdList": [
                    12,
                    22,
                    34
                  ]
                },
                {
                  "id": 320,
                  "originalPrice": 4000,
                  "discountPrice": 3200,
                  "factorIdList": [
                    12,
                    22,
                    37
                  ]
                },
                {
                  "id": 321,
                  "originalPrice": 4000,
                  "discountPrice": 3300,
                  "factorIdList": [
                    13,
                    22,
                    32
                  ]
                },
                {
                  "id": 322,
                  "originalPrice": 4000,
                  "discountPrice": 3400,
                  "factorIdList": [
                    13,
                    22,
                    34
                  ]
                },
                {
                  "id": 323,
                  "originalPrice": 4000,
                  "discountPrice": 3500,
                  "factorIdList": [
                    13,
                    22,
                    36
                  ]
                }
              ]
            }
        """.trimIndent()
        return JsonUtil.parseObject(json, ProductEntity::class.java)!!
    }

}
