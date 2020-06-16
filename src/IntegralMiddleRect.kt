import java.lang.reflect.Method

class IntegralMiddleRect(workDirPath: String, userFunClassName: String, userFunName: String,
                            methodIn: Method? = null, onLineLibIn: Any? = null
                         ) : IntegralBase(workDirPath, userFunClassName, userFunName, methodIn, onLineLibIn) {

    private var middleRectUserFun: String? = null
    private val middleRectUserFunClassName: String = "MiddleRectOnLineLib"
    private val middleRectUserFunFileName: String = "MiddleRectOnLineLib.kt"
    private val middleRectUserFunName: String = "userFun"
    private var middleRectCompileMessage: String = ""

    fun setMiddleRectUserFun(rectUserFunIn: String?) {
        this.middleRectUserFun = rectUserFunIn
        val cuf = CompileUserFun(middleRectUserFun!!, workDirPath, middleRectUserFunFileName, middleRectUserFunClassName)
        cuf.compile()
        middleRectCompileMessage = cuf.compileMessage

        userFunClassName = middleRectUserFunClassName
        userFunName = middleRectUserFunName

        loadOnLineLib()
    }

    fun middleRectSteps(A: Double, B: Double, cntSteps: Int) {
        y.clear()

        var thisSteps: Int = cntSteps
        var oneStep: Double = rnd((B - A)/thisSteps)

        if (thisSteps % 2.0 != 0.0) {
            thisSteps++
            oneStep = rnd((B - A)/thisSteps)
            messages += "Колличество шагов было увеличено до четного $thisSteps\n"
        }
        messages+="steps=$thisSteps, oneStep=$oneStep\n"

        fillPreValues(thisSteps, oneStep, A, B)

        result = sumResult()
    }

    fun middleRectOneStep(A: Double, B: Double, oneStep: Double) {
        y.clear()
        var thisOneStep = oneStep
        var thisSteps = rnd((B - A)/oneStep).toInt()

        if (thisSteps % 2.0 != 0.0) {
            thisSteps++
            thisOneStep = rnd((B - A)/thisSteps)
            messages += "Колличество шагов было увеличено до четного $thisSteps\n"
        }
        messages+="steps=$thisSteps, oneStep=$oneStep\n"

        fillPreValues(thisSteps, thisOneStep, A, B)

        result = sumResult()
    }

    private fun fillPreValues(cntSteps: Int, oneStep: Double, A: Double, B: Double){
        var thisOneStep = oneStep
        var x = 0.0
        var i = 0

        while (i < cntSteps) {

            if (i == cntSteps - 1){
                thisOneStep = rnd(B - x)
                x += thisOneStep
                messages+="в последней итерации $i шаг $oneStep был приведен к $thisOneStep\n"
            }
            else x = rnd(A + i * thisOneStep)

            y[i] = StepsValues(rnd(x),rnd(callUserFun(rnd(x + thisOneStep/2.0))), thisOneStep, 0.0)

            i++
        }
    }

    private fun sumResult(): Double{
        var sm = 0.0
        var i = 0
        while (i < y.size) {
            sm += y[i]!!.fx * y[i]!!.k
            i++
        }
        return rnd(sm)
    }
}