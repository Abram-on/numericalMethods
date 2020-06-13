import java.lang.reflect.Method

class IntegralSimpson(workDirPath: String, userFunClassName: String, userFunName: String,
                      methodIn: Method? = null, onLineLibIn: Any? = null
                     ) : IntegralBase(workDirPath, userFunClassName, userFunName, methodIn, onLineLibIn) {

    fun simpsonOneStep(A: Double, B: Double, oneStep: Double) {
        var thisOneStep: Double = oneStep
        var thisCntSteps: Int = ((B - A)/thisOneStep).toInt()

        y.clear()

        if (thisCntSteps % 2.0 != 0.0) {
            thisCntSteps ++
            messages += "Колличество шагов было увеличено до четного $thisCntSteps\n"
            thisOneStep = rnd((B - A)/thisCntSteps)
        }
        messages+="steps=$thisCntSteps, oneStep=$thisOneStep\n"

        fillPreValues(thisCntSteps, thisOneStep, A, B)

        result = sumResult(A, B)
    }


    fun simpsonSteps(A: Double, B: Double, cntSteps: Int) {
        var thisCntSteps: Int = cntSteps
        var thisOneStep: Double = rnd((B - A)/thisCntSteps)

        y.clear()

        if (thisCntSteps % 2.0 != 0.0) {
            thisCntSteps++
            thisOneStep = rnd((B - A)/thisCntSteps)
            messages += "Колличество шагов было увеличено до четного $thisCntSteps\n"
        }
        messages+="steps=$thisCntSteps, oneStep=$thisOneStep\n"

        fillPreValues(thisCntSteps, thisOneStep, A, B)

        result = sumResult(A, B)
    }

    private fun fillPreValues(cntSteps: Int, oneStep: Double, A: Double, B: Double){
        var thisOneStep = oneStep
        var i = 0
        var x = A
        var xPre = A

        while (i <= cntSteps) {

            if (i == 0 || i == cntSteps)
                y[i]= StepsValues(x, rnd(callUserFun(x)), 1.0, rnd(x - xPre))
            else
                y[i]= StepsValues(x, rnd(callUserFun(x)), if ((i + 2.0) % 2.0 > 0) 4.0 else 2.0, rnd(x - xPre))

            if (i + 1 == cntSteps){
                if (rnd(B - x) > oneStep){
                    thisOneStep = rnd(B - x)
                    y[i]!!.step = thisOneStep
                    messages+="в последней итерации ${i+1} шаг $oneStep был увеличен до $thisOneStep\n"
                } else {
                    if (rnd(B - x) < oneStep) {
                        thisOneStep = rnd(B - x)
                        y[i]!!.step = thisOneStep
                        messages+="в последней итерации ${i+1} шаг $oneStep был уменьшен до $thisOneStep\n"
                    }
                }
            }
            xPre = x
            x = rnd(x + thisOneStep)
            i++
        }
    }

    private fun sumResult(A: Double, B: Double): Double{
        var sm = 0.0
        var i = 0
        while (i < y.size) {
            sm += (y[i]!!.fx * y[i]!!.k)
            i ++
        }
        val k: Double = rnd((B - A)/(y.size - 1))/3.0
        return rnd(k * sm)
    }
}