import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.reflect.Method
import java.nio.charset.Charset

class Integrals(userFun: String? = null, private val workDirPath: String) {

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
            preLoadIntegral(userFun)
            //загрузка библиотеки OnLineLib, если задана общая userFun для всех интегралов
            loadOnLineLib()
        }

        //создание классов интегралов
        simpson = IntegralSimpson(workDirPath, userFunClassName, userFunName, method, onLineLib)
        trapezes = IntegralTrapezes(workDirPath, userFunClassName, userFunName, method, onLineLib)
        middleRect = IntegralMiddleRect(workDirPath, userFunClassName, userFunName, method, onLineLib)
        rect = IntegralRect(workDirPath, userFunClassName, userFunName, method, onLineLib)
    }

    private fun preLoadIntegral(userFun: String): Int {

        if (userFunClassFileWrite(userFun) != 0) {
            println("Ошибка записи в файл")
            return -2
        }

        if (compileUserFun( workDirPath + userFunFileName) != 0) {
            println("Ошибка компиляции")
            println(compileMessage)
            return -3
        }
        return 0
    }

    private fun userFunClassFileWrite(strFunIn: String): Int {
        val strFun = strFunIn.trim()
        val file = File(workDirPath + userFunFileName)

        if (file.exists()) file.delete()
        file.writeText(userFunClassBuild(strFun), Charset.defaultCharset())

        return 0
    }

    private fun userFunClassBuild(strFunIn: String): String {
        val strBld: StringBuilder = StringBuilder("import java.lang.Math.* \n\n")
        strBld.append("class OnLineLib{ \n\n")
                .append("\tfun userFun(x: Double): Double { \n")
                .append("\t\t val ret: Double = $strFunIn  \n")
                .append("\t\treturn ret\n")
                .append("\t}\n")
                .append("}\n ")

        return strBld.toString()
    }

    private fun compileUserFun(userFunFileNameIn: String): Int{
        compileMessage = ""
        val file = File(userFunFileNameIn)

        val cmd: Process = Runtime.getRuntime().exec("C:\\IntellijIDEA\\plugins\\Kotlin\\kotlinc\\bin\\kotlinc-jvm.bat ${file.canonicalPath}  ")
        val stdoutReader = BufferedReader(InputStreamReader(cmd.inputStream))

        var line: String?
        while (stdoutReader.readLine().also { line = it } != null) {
            compileMessage += line
        }
        val stderrReader = BufferedReader(
                InputStreamReader(cmd.errorStream))
        while (stderrReader.readLine().also { line = it } != null) {
            compileMessage += line
        }
        return cmd.exitValue()
    }

    private fun loadOnLineLib(){
        val ml = ModuleLoader(workDirPath, this.javaClass.classLoader)
        ml.loadOnLineLib(userFunClassName, userFunName)
        method = ml.method
        onLineLib = ml.onLineLib
    }
}


