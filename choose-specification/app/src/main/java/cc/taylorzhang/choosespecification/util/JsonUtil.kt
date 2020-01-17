package cc.taylorzhang.choosespecification.util

import com.google.gson.Gson
import com.google.gson.JsonParser
import java.lang.reflect.Type

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2020/01/03
 *     desc   : Json工具类
 *     version: 1.0.0
 * </pre>
 */
object JsonUtil {

    val gson by lazy { Gson() }

    /**
     * 解析json，需要传递引用类型
     */
    fun <T> parse(json: String, typeOfT: Type): T? {
        var t: T? = null
        try {
            t = gson.fromJson(json, typeOfT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return t
    }

    fun <T> parseObject(json: String, clazz: Class<T>): T? {
        var t: T? = null
        try {
            t = gson.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return t
    }

    fun <T> parseArray(json: String, clazz: Class<T>): ArrayList<T>? {
        val list = ArrayList<T>()
        try {
            val array = JsonParser.parseString(json).asJsonArray
            array.forEach { list.add(gson.fromJson(it, clazz)) }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun toJson(obj: Any): String {
        var str = ""
        try {
            str = gson.toJson(obj)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return str
    }
}