import java.lang.reflect.Method


class Integrals( private val userFun: String? = null, private val workDirPath: String) {

    private val userFunClassName: String = "OnLineLib"
    private val userFunFileName: String = "OnLineLib.kt"
    private val userFunName: String = "userFun"
    private var compileMessage: String = ""
    private var method : Method? = null
    private var onLineLib: Any? = null


    val simpson: IntegralSimpson
    val trapezes: IntegralTrapezes
    val middleRect: IntegralMiddleRect
    val rect: IntegralRect

    init {
        if (userFun != null) {
            //обработка userFun в библиотечный класс, запись, компиляция
            val cuf = CompileUserFun(userFun, workDirPath, userFunFileName, userFunClassName)
            cuf.compile()
            compileMessage = cuf.compileMessage
            println(compileMessage)
            //загрузка библиотеки OnLineLib, если задана общая userFun для всех интегралов
            loadOnLineLib()
        }

        //создание классов интегралов
        simpson = IntegralSimpson(workDirPath, userFunClassName, userFunName, method, onLineLib)
        trapezes = IntegralTrapezes(workDirPath, userFunClassName, userFunName, method, onLineLib)
        middleRect = IntegralMiddleRect(workDirPath, userFunClassName, userFunName, method, onLineLib)
        rect = IntegralRect(workDirPath, userFunClassName, userFunName, method, onLineLib)
    }

    private fun loadOnLineLib(){
        val ml = ModuleLoader(workDirPath, this.javaClass.classLoader)
        ml.loadOnLineLib(userFunClassName, userFunName)
        method = ml.method
        onLineLib = ml.onLineLib
    }
}


