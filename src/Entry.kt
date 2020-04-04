import com.alibaba.excel.EasyExcel
import com.alibaba.excel.context.AnalysisContext
import com.alibaba.excel.enums.WriteDirectionEnum
import com.alibaba.excel.event.AnalysisEventListener
import com.alibaba.excel.write.metadata.fill.FillConfig
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.sqrt

//---以下自定义修改---/////////////////////////////////////////////////////////
/**
 * true:显示删除过程  false:仅显示最终结果
 */
const val shouldShowProgressMessage = true
/**
 * 保留的位数
 */
const val scale = 2
/**
 * 输入路径
 */
const val input = "C:\\Users\\laoYao\\Desktop\\input.xlsx"
/**
 * 输出路径
 */
const val output = "C:\\Users\\laoYao\\Desktop\\input_new.xlsx"
/**
 * result输出路径
 */
const val result_output = "C:\\Users\\laoYao\\Desktop\\result_output.xlsx"
/**
 * 期望值
 */
val limits = mapOf(
    Pair("比重", 0.3F),
    Pair("含水量", 0.3F),
    Pair("容重", 0.3F),
    Pair("孔隙比", 0.1F),
    Pair("饱和度", 0.3F),
    Pair("液限", 0.3F),
    Pair("塑限", 0.3F),
    Pair("塑性指数", 0.3F),
    Pair("液性指数", 0.3F),
    Pair("内聚力", 0.3F),
    Pair("摩擦力", 0.25F),
    Pair("压缩系数", 0.3F),
    Pair("压缩模量", 0.3F)
)
/**
 * 标准值 + Or -
 */
val plusOr = mapOf(
    Pair("比重", "+"),
    Pair("含水量", "+"),
    Pair("容重", "+"),
    Pair("孔隙比", "+"),
    Pair("饱和度", "+"),
    Pair("液限", "+"),
    Pair("塑限", "+"),
    Pair("塑性指数", "+"),
    Pair("液性指数", "+"),
    Pair("内聚力", "-"),
    Pair("摩擦力", "-"),
    Pair("压缩系数", "+"),
    Pair("压缩模量", "-")
)
//---以上自定义修改---/////////////////////////////////////////////////////////

fun main() {
    val list = mutableListOf<Item>()
    EasyExcel.read(input, Item::class.java, object : AnalysisEventListener<Item>() {
        override fun doAfterAllAnalysed(p0: AnalysisContext?) {
            println("${list.size} -- $list")
        }

        override fun invoke(p0: Item?, p1: AnalysisContext?) {
            p0?.let { list.add(p0) }
        }
    }).sheet().doRead()

    println("\n....比重 -- 开始计算....")
    loopCheckBiZhong(list)

    println("\n....含水量 -- 开始计算....")
    loopCheckHanShuiLiang(list)

    println("\n....容重 -- 开始计算....")
    loopCheckRongZhong(list)

    println("\n....孔隙比 -- 开始计算....")
    loopCheckKongXiBi(list)

    println("\n....饱和度 -- 开始计算....")
    loopCheckBaoHeDu(list)

    println("\n....液限 -- 开始计算....")
    loopCheckYeXian(list)

    println("\n....塑限 -- 开始计算....")
    loopCheckSuXian(list)

    println("\n....塑性指数 -- 开始计算....")
    loopCheckSuXingZhiShu(list)

    println("\n....液性指数 -- 开始计算....")
    loopCheckYeXingZhiShu(list)

    println("\n....内聚力 -- 开始计算....")
    loopCheckNeiJuLi(list)

    println("\n....摩擦力 -- 开始计算....")
    loopCheckMoCaLi(list)

    println("\n....压缩系数 -- 开始计算....")
    loopCheckYaSuoXiShu(list)

    println("\n....压缩模量 -- 开始计算....")
    loopCheckYaSuoMoLiang(list)

    EasyExcel.write(output, Item::class.java).sheet("sheet0").doWrite(list)
    writeResult()
}

val resultList = mutableListOf<Result>()

fun writeResult() {
    EasyExcel.write(result_output).withTemplate("result_template.xlsx").sheet().doFill(
        resultList,
        FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build()
    )
}

fun loopCheckBiZhong(list: List<Item>) {
    val property = "比重"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.biZhong }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.biZhong == min }?.biZhong = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.biZhong == max }?.biZhong = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckBiZhong(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckHanShuiLiang(list: List<Item>) {
    val property = "含水量"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.hanShuiLiang }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.hanShuiLiang == min }?.hanShuiLiang = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.hanShuiLiang == max }?.hanShuiLiang = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckHanShuiLiang(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckRongZhong(list: List<Item>) {
    val property = "容重"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.rongZhong }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.rongZhong == min }?.rongZhong = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.rongZhong == max }?.rongZhong = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckRongZhong(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckKongXiBi(list: List<Item>) {
    val property = "孔隙比"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.kongXiBi }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.kongXiBi == min }?.kongXiBi = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.kongXiBi == max }?.kongXiBi = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckKongXiBi(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckBaoHeDu(list: List<Item>) {
    val property = "饱和度"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.baoHeDu }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.baoHeDu == min }?.baoHeDu = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.baoHeDu == max }?.baoHeDu = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckBaoHeDu(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckYeXian(list: List<Item>) {
    val property = "液限"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.yeXian }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.yeXian == min }?.yeXian = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.yeXian == max }?.yeXian = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckYeXian(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckSuXian(list: List<Item>) {
    val property = "塑限"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.suXian }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.suXian == min }?.suXian = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.suXian == max }?.suXian = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckSuXian(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckSuXingZhiShu(list: List<Item>) {
    val property = "塑性指数"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.suXingZhiShu }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.suXingZhiShu == min }?.suXingZhiShu = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.suXingZhiShu == max }?.suXingZhiShu = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckSuXingZhiShu(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckYeXingZhiShu(list: List<Item>) {
    val property = "液性指数"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.yeXingZhiShu }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.yeXingZhiShu == min }?.yeXingZhiShu = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.yeXingZhiShu == max }?.yeXingZhiShu = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckYeXingZhiShu(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckNeiJuLi(list: List<Item>) {
    val property = "内聚力"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.neiJuLi }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.neiJuLi == min }?.neiJuLi = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.neiJuLi == max }?.neiJuLi = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckNeiJuLi(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckMoCaLi(list: List<Item>) {
    val property = "摩擦力"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.moCaLi }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.moCaLi == min }?.moCaLi = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.moCaLi == max }?.moCaLi = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckMoCaLi(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckYaSuoXiShu(list: List<Item>) {
    val property = "压缩系数"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.yaSuoXiShu }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.yaSuoXiShu == min }?.yaSuoXiShu = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.yaSuoXiShu == max }?.yaSuoXiShu = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckYaSuoXiShu(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun loopCheckYaSuoMoLiang(list: List<Item>) {
    val property = "压缩模量"
    val limit = limits.getValue(property)
    val plusOr = plusOr.getValue(property)
    val filteredList = list.mapNotNull { it.yaSuoMoLiang }
    if (shouldShowProgressMessage) show(property, limit, plusOr, filteredList)
    if (shouldShowProgressMessage) println("开始检查")
    if (ratio(filteredList) > limit) {
        if (actions.getValue(property)) {
            val min = min(filteredList)
            list.findLast { it.yaSuoMoLiang == min }?.yaSuoMoLiang = null
            actions[property] = false
            if (shouldShowProgressMessage) println("\t删除最小$property\t$min")
        } else {
            val max = max(filteredList)
            list.findLast { it.yaSuoMoLiang == max }?.yaSuoMoLiang = null
            actions[property] = true
            if (shouldShowProgressMessage) println("\t删除最大$property\t$max")
        }
        loopCheckYaSuoMoLiang(list)
    } else {
        if (shouldShowProgressMessage) println("\t满足期望值 无删除")
        showResult(property, limit, plusOr, filteredList)
        println("....$property -- 结束计算....")
    }
}

fun show(property: String, limit: Float, plusOr: String, list: List<Float>) {
    println(
        "${property}当前值:\n" +
                "\t期望\t$limit\n" +
                "\t个数\t${list.size}\n" +
                "\t最大值\t${max(list)}\n" +
                "\t最小值\t${min(list)}\n" +
                "\t平均值\t${average(list)}\n" +
                "\t方差\t${fangCha(list)}\n" +
                "\t变异系数\t${ratio(list)}\n" +
                "\t标准值${plusOr}\t${if (plusOr == "+") stdPlus(list) else std(list)}"
    )
}

fun showResult(property: String, limit: Float, plusOr: String, list: List<Float>) {
    println(
        "${property}最终值:\n" +
                "\t期望\t$limit\n" +
                "\t个数\t${list.size}\n" +
                "\t最大值\t${max(list)}\n" +
                "\t最小值\t${min(list)}\n" +
                "\t平均值\t${average(list)}\n" +
                "\t方差\t${fangCha(list)}\n" +
                "\t变异系数\t${ratio(list)}\n" +
                "\t标准值${plusOr}\t${if (plusOr == "+") stdPlus(list) else std(list)}"
    )
    resultList.add(
        Result(
            property,
            "${list.size}",
            "${max(list)}",
            "${min(list)}",
            "${BigDecimal(average(list)).setScale(scale, RoundingMode.HALF_UP)}",
            "${BigDecimal(fangCha(list)).setScale(scale, RoundingMode.HALF_UP)}",
            "${BigDecimal(ratio(list)).setScale(scale, RoundingMode.HALF_UP)}",
            "${BigDecimal(if (plusOr == "+") stdPlus(list) else std(list)).setScale(scale, RoundingMode.HALF_UP)}"
        )
    )
}

fun max(list: List<Float>): Float? = list.max()

fun min(list: List<Float>): Float? = list.min()

fun average(list: List<Float>) = list.average()

fun fangCha(list: List<Float>): Double {
    val sumOfSquare = list.map { it * it }.sum().toDouble()
    val squareOfSum = list.sum() * list.sum().toDouble()
    return sqrt((sumOfSquare - squareOfSum / list.size) / (list.size - 1))
}

fun ratio(list: List<Float>) = fangCha(list) / average(list)

fun std(list: List<Float>) =
    average(list) * (1 - ratio(list) * ((1.704 / (sqrt(list.size.toDouble()))) + (4.678 / (list.size * list.size))))

fun stdPlus(list: List<Float>) =
    average(list) * (1 + ratio(list) * ((1.704 / (sqrt(list.size.toDouble()))) + (4.678 / (list.size * list.size))))

val actions = mutableMapOf(
    Pair("比重", false),
    Pair("含水量", false),
    Pair("容重", false),
    Pair("孔隙比", false),
    Pair("饱和度", false),
    Pair("液限", false),
    Pair("塑限", false),
    Pair("塑性指数", false),
    Pair("液性指数", false),
    Pair("内聚力", false),
    Pair("摩擦力", false),
    Pair("压缩系数", false),
    Pair("压缩模量", false)
)