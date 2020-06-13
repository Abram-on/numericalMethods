
fun main(args: Array<String>) {


    //val integrals: Integrals = Integrals("2.0 * pow(x, 2.0) + 3.0 * x + 5.1","C:\\IDEADev\\numericalMethod\\")
    val integrals = Integrals("sqrt(1.0+2.0*pow(x,2.0) - pow(x,3.0))", "C:\\IDEADev\\numericalMethod\\")

    integrals.simpson.simpsonOneStep(1.2, 2.0, 0.000701)
//    integrals.simpson.simpsonOneStep(10.0, 100.0, 0.00002)
    //integrals.simpson.arrayPrint()
    println(integrals.simpson.messages)
    integrals.simpson.messages = ""
    println("Simpson OneStep=${integrals.simpson.result}")
    integrals.simpson.result = null
    println("-----------------------------------------------------")

    integrals.simpson.simpsonSteps(1.2, 2.0, 1142)
//    integrals.simpson.simpsonSteps(10.0, 100.0, 4500000)
    //integrals.simpson.arrayPrint(5,5)
    println(integrals.simpson.messages)
    integrals.simpson.messages = ""
    println("Simpson steps=${integrals.simpson.result}")
    integrals.simpson.result = null
    println("-----------------------------------------------------")


    integrals.trapezes.trapezesOneStep(1.2, 2.0, 0.000701)
//    integrals.trapezes.trapezesOneStep(10.0, 100.0, 0.00002)
    //integrals.trapezes.arrayPrint()
    println(integrals.trapezes.messages)
    integrals.trapezes.messages = ""
    println("Trapezes OneStep=${integrals.trapezes.result}")
    integrals.trapezes.result = null
    println("-----------------------------------------------------")


    integrals.trapezes.trapezesSteps(1.2, 2.0, 1142)
    //integrals.trapezes.rndToSign = 100000000000000000000.0
//    integrals.trapezes.trapezesSteps(10.0, 100.0, 4500000)
    //integrals.trapezes.arrayPrint( lastVal = 10 )
    println(integrals.trapezes.messages)
    integrals.trapezes.messages = ""
    println("Trapezes steps=${integrals.trapezes.result}")
    integrals.trapezes.result = null
    println("-----------------------------------------------------")


    integrals.middleRect.middleRectOneStep(1.2, 2.0, 0.000701)
//    integrals.middleRect.trapezesOneStep(10.0, 100.0, 0.00002)
    //integrals.middleRect.arrayPrint()
    println(integrals.middleRect.messages)
    integrals.middleRect.messages = ""
    println("MiddleRect OneStep=${integrals.middleRect.result}")
    integrals.middleRect.result = null
    println("-----------------------------------------------------")


    integrals.middleRect.middleRectSteps(1.2, 2.0, 1142)
    //integrals.middleRect.rndToSign = 100000000000000000000.0
//    integrals.middleRect.trapezesSteps(10.0, 100.0, 4500000)
    //integrals.middleRect.arrayPrint( 5, 5 )
    println(integrals.middleRect.messages)
    integrals.middleRect.messages = ""
    println("MiddleRect steps=${integrals.middleRect.result}")
    integrals.middleRect.result = null
    println("-----------------------------------------------------")

    val rectUserFun = "2.0 * pow(x, 2.0) + 3.0 * x + 5.1"

    integrals.rect.setRectUserFun(rectUserFun)

    integrals.rect.rectOneStep(1.2, 2.0, 0.000701)
//    integrals.rect.rectOneStep(10.0, 100.0, 0.00002)
    //integrals.rect.arrayPrint()
    println(integrals.rect.messages)
    integrals.rect.messages = ""
    println("Rect OneStep=${integrals.rect.result}")
    integrals.rect.result = null
    println("-----------------------------------------------------")


    integrals.rect.rectSteps(1.2, 2.0, 1142)
    //integrals.rect.rndToSign = 100000000000000000000.0
//    integrals.rect.rectSteps(10.0, 100.0, 4500000)
    //integrals.rect.arrayPrint( 5, 5 )
    println(integrals.rect.messages)
    integrals.rect.messages = ""
    println("Rect steps=${integrals.rect.result}")
    integrals.rect.result = null
    println("-----------------------------------------------------")

}




