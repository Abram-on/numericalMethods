import java.lang.reflect.Method

class IntegralTrapezes(workDirPath: String, userFunClassName: String, userFunName: String,
                            methodIn: Method? = null, onLineLibIn: Any? = null
                       ) : IntegralBase(workDirPath, userFunClassName, userFunName, methodIn, onLineLibIn) {

    private var trapezesUserFun: String? = null
    private val trapezesUserFunClassName: String = "TrapezesOnLineLib"
    private val trapezesUserFunFileName: String = "TrapezesOnLineLib.kt"
    private val trapezesUserFunName: String = "userFun"
    private var trapezesCompileMessage: String = ""

    fun setTrapezesUserFun(rectUserFunIn: String?) {
        this.trapezesUserFun = rectUserFunIn
        val cuf = CompileUserFun(trapezesUserFun!!, workDirPath, trapezesUserFunFileName, trapezesUserFunClassName)
        cuf.compile()
        trapezesCompileMessage = cuf.compileMessage
        if (trapezesCompileMessage != "")  println(trapezesCompileMessage)

        trapezesCompileMessage = cuf.compileMessage

        userFunClassName = trapezesUserFunClassName
        userFunName = trapezesUserFunName

        loadOnLineLib()
    }

    fun trapezesSteps(A: Double, B: Double, cntSteps: Int) {
        y.clear()

        var thisSteps: Int = cntSteps
        var oneStep: Double = rnd((B - A) / thisSteps)

        if (thisSteps % 2.0 == 0.0) {
            thisSteps --
            oneStep = rnd((B - A) / (thisSteps ))
            messages += "Колличество шагов было увеличено до четного ${thisSteps+1}\n"
        }


        messages+="steps=${thisSteps+1}, oneStep=$oneStep\n"

        fillPreValues(thisSteps, oneStep, A, B)

        updatePrev()

        result = sumResult()
    }

    fun trapezesOneStep(A: Double, B: Double, oneStep: Double) {
        y.clear()

        var thisSteps: Int = rnd((B - A)/oneStep).toInt()
        var thisOneStep: Double = oneStep

        if (thisSteps % 2.0 == 0.0) {
            thisSteps --
            thisOneStep = rnd((B - A) / (thisSteps ))
            messages += "Колличество шагов было увеличено до четного ${thisSteps+1}\n"
        }

        messages+="steps=${thisSteps+1}, oneStep=$thisOneStep\n"

        fillPreValues(thisSteps, oneStep, A, B)

        updatePrev()

        result = sumResult()

    }

    private fun fillPreValues(cntSteps: Int, oneStep: Double, A: Double, B: Double) {
        var i = 0
        var x: Double = A
        var fx: Double
        var thisOneStep: Double = oneStep

        while (i <= cntSteps) {
            //if (i + 1 == cntSteps){
            if (i == cntSteps){
                if (B >= rnd(x)){
                    thisOneStep = rnd((B - x))

                    messages+="в последней итерации ${i} шаг $oneStep был увеличен до $thisOneStep\n"

                    x += thisOneStep
                } else {
                    if (B < rnd(x + thisOneStep)){
                        thisOneStep = rnd((B - x))

                        messages+="в последней итерации ${i} шаг $oneStep был уменьшен до $thisOneStep\n"

                        x -= thisOneStep
                    }
                }
            }
            else x = rnd(A + i* thisOneStep)


            fx = rnd(callUserFun(x))

            y[i] = StepsValues(x, fx, thisOneStep, 0.0)


            i++
        }
    }

    private fun updatePrev(){
        var xPre: Double
        var fxPre: Double
        val last: Int = y.size - 1
        val first: Int = 0//y.keys.first()
        var i: Int
        i = last
        do{
            xPre = y[i]!!.x
            fxPre = y[i]!!.fx
            i--
            y[i]!!.k = rnd(xPre - y[i]!!.x)
            y[i]!!.step = fxPre
        } while (i > first)
    }

    private fun sumResult(): Double{
        var sm = 0.0
        var i = 0
        while (i < y.size - 1) {
            sm += ((y[i]!!.step + y[i]!!.fx) / 2.0) * y[i]!!.k //((a+b)/2)*h
            i++
        }
        return rnd(sm)
    }
}