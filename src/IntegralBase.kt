import java.lang.reflect.Method
import kotlin.math.round
import kotlin.Any

abstract class IntegralBase(private val workDirPath: String, private val userFunClassName: String, private val userFunName: String, private val methodIn: Method? = null, private val onLineLibIn: Any? = null){
    var rndToSign: Double = 1000000.0
    val y: HashMap<Int, StepsValues> = HashMap()
    var messages: String = ""
    var result: Double? = null

    private var method : Method
    private var onLineLib: Any

    init {
        if (methodIn == null) {
            val ml = ModuleLoader(workDirPath, this.javaClass.classLoader)
            val param = Double::class.java
            val loadClass = ml.findClass(userFunClassName)
            val constructor = loadClass.constructors.first()
            onLineLib = constructor.newInstance()
            method = loadClass.getMethod(userFunName, param)
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
        return method.invoke(onLineLib, arg).toString().toDouble()
    }

    fun arrayPrint(firstVal: Int = 0, lastVal: Int = 0){
        var i = y.keys.first()
        val i_max = y.keys.last()

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
    }

}