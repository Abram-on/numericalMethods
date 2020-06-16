import java.lang.reflect.Method

class IntegralRect(workDirPath: String, userFunClassName: String, userFunName: String,
                   methodIn: Method? = null, onLineLibIn: Any? = null
                    ) : IntegralBase(workDirPath, userFunClassName, userFunName, methodIn, onLineLibIn) {

    private var rectUserFun: String? = null
    private val rectUserFunClassName: String = "RectOnLineLib"
    private val rectUserFunFileName: String = "RectOnLineLib.kt"
    private val rectUserFunName: String = "userFun"
    private var rectCompileMessage: String = ""

    fun setRectUserFun(rectUserFunIn: String?) {
        this.rectUserFun = rectUserFunIn
        val cuf = CompileUserFun(rectUserFun!!, workDirPath, rectUserFunFileName, rectUserFunClassName)
        cuf.compile()
        rectCompileMessage = cuf.compileMessage
        if (rectCompileMessage != "")  println(rectCompileMessage)

        userFunClassName = rectUserFunClassName
        userFunName = rectUserFunName

        loadOnLineLib()
    }

    fun rectSteps(A: Double, B: Double, cntSteps: Int) {
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

    fun rectOneStep(A: Double, B: Double, oneStep: Double) {
        y.clear()
        var thisOneStep: Double = oneStep
        var thisSteps: Int = rnd((B - A)/oneStep).toInt()

        if (thisSteps % 2.0 != 0.0) {
            thisSteps++
            thisOneStep = rnd((B - A)/thisSteps)
            messages += "Колличество шагов было увеличено до четного $thisSteps\n"
        }
        messages+="steps=$thisSteps, oneStep=$thisOneStep\n"

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

            y[i] = StepsValues(rnd(x),rnd(callUserFun(rnd(x + thisOneStep))), thisOneStep, 0.0)

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

