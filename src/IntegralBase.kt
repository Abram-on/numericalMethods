import java.lang.reflect.Method
import kotlin.math.round
import kotlin.Any

abstract class IntegralBase(protected var workDirPath: String, protected var userFunClassName: String, protected var userFunName: String, methodIn: Method? = null, onLineLibIn: Any? = null){
    var rndToSign: Double = 1000000.0
    val y: HashMap<Int, StepsValues> = HashMap()
    var messages: String = ""
    var result: Double? = null

    protected var method : Method? = null
    protected var onLineLib: Any? = null

    init {
        if (methodIn == null) {
            loadOnLineLib()
        } else {
            method = methodIn
            onLineLib = onLineLibIn!!
        }
    }

    class StepsValues(val x: Double, var fx: Double, var k: Double, var step: Double)

    fun rnd(x: Double, digit: Double? = null): Double{
        return  if (digit != null) round((x * digit)) /digit else round((x * rndToSign)) /rndToSign
    }

    fun callUserFun(arg: Any): Double {
        return method!!.invoke(onLineLib, arg).toString().toDouble()
    }

    fun arrayPrint(firstVal: Int = 0, lastVal: Int = 0){
//        var i = y.keys.first()
//        val i_max = y.keys.last()
        var i = 0
        val i_max = y.size - 1

        if (firstVal == 0 && lastVal == 0) {
            while (i <=  i_max) {
                println("i=$i, x=${y[i]!!.x}, fx=${y[i]!!.fx}, k=${y[i]!!.k}, step=${y[i]!!.step}")
                i++
            }
        } else {
            while (i <=  i_max) {
                if ((i <= firstVal && firstVal != 0) || (y.size - i <= lastVal && lastVal != 0)) {
                    println("i=$i, x=${y[i]!!.x}, fx=${y[i]!!.fx}, k=${y[i]!!.k}, step=${y[i]!!.step}")
                }
                i++
            }
        }
        print("\ni=$i, i_max=$i_max size=${y.size}\n")
    }

    protected fun loadOnLineLib(){
        val ml = ModuleLoader(workDirPath, this.javaClass.classLoader)
        ml.loadOnLineLib(userFunClassName, userFunName)
        method = ml.method
        onLineLib = ml.onLineLib

    }

}