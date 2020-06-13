import java.lang.reflect.Method

class IntegralTrapezes(workDirPath: String, userFunClassName: String, userFunName: String,
                            methodIn: Method? = null, onLineLibIn: Any? = null
                       ) : IntegralBase(workDirPath, userFunClassName, userFunName, methodIn, onLineLibIn) {

    fun trapezesSteps(A: Double, B: Double, cntSteps: Int) {
        y.clear()

        var thisSteps: Int = cntSteps
        var oneStep: Double = rnd((B - A) / thisSteps)

        if (thisSteps % 2.0 != 0.0) {
            thisSteps ++
            oneStep = rnd((B - A) / thisSteps)
            messages += "Колличество шагов было увеличено до четного $thisSteps\n"
        }


        messages+="steps=$thisSteps, oneStep=$oneStep\n"

        fillPreValues(thisSteps, oneStep, A, B)

        updatePrev()

        result = sumResult()
    }

    fun trapezesOneStep(A: Double, B: Double, oneStep: Double) {
        y.clear()

        var thisSteps: Int = rnd((B - A)/oneStep).toInt()
        var thisOneStep: Double = oneStep

        if (thisSteps % 2.0 != 0.0) {
            thisSteps ++
            thisOneStep = rnd((B - A) / thisSteps)
            messages += "Колличество шагов было увеличено до четного $thisSteps\n"
        }

        messages+="steps=$thisSteps, oneStep=$thisOneStep\n"

        fillPreValues(thisSteps, oneStep, A, B)

        updatePrev()

        result = sumResult()

    }

    private fun fillPreValues(cntSteps: Int, oneStep: Double, A: Double, B: Double) {
        var i = 1
        var x = A
        var fx: Double
        var thisOneStep: Double = oneStep

        while (i <= cntSteps) {
            fx = rnd(callUserFun(x))

            y[i] = StepsValues(x, fx, oneStep, 0.0)

            if (i + 1 == cntSteps){
            //if (i == cntSteps){
                if (B >= rnd(x)){
                    thisOneStep = rnd((B - x))

                    messages+="в последней итерации ${i+1} шаг $oneStep был увеличен до $thisOneStep\n"

                    x += thisOneStep
                } else {
                    if (B < rnd(x + thisOneStep)){
                        thisOneStep = rnd((B - x))

                        messages+="в последней итерации ${i+1} шаг $oneStep был уменьшен до $thisOneStep\n"

                        x -= thisOneStep
                    }
                }
            }
            else x = rnd(x + thisOneStep)

            i++
        }
    }

    private fun updatePrev(){
        var xPre: Double
        var fxPre: Double
        val last: Int = y.keys.last()
        val first: Int = y.keys.first()
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
        var i = 1
        while (i < y.size) {
            sm += ((y[i]!!.step + y[i]!!.fx) / 2.0) * y[i]!!.k //((a+b)/2)*h
            i++
        }
        return rnd(sm)
    }
}