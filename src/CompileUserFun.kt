import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.Charset

class CompileUserFun(private val userFun: String, private val workDirPath: String, private val userFunFileName: String, private val className: String) {

    var compileMessage: String =""

    fun compile(): Int{
        if (userFunClassFileWrite(userFun) == 1) {
            compileMessage += "UserFun не изменилась, комиляция отменена\n"
            return 1
        }

        if (compileUserFun( workDirPath + userFunFileName) != 0) {
            println("Ошибка компиляции")
            println(compileMessage)
            return -3
        }
        return 0

    }

    private fun userFunClassBuild(strFunIn: String): String {
        val strBld: StringBuilder = StringBuilder("import java.lang.Math.* \n\n")
//        strBld.append("class OnLineLib{ \n\n")
        strBld.append("class $className { \n\n")
                .append("\tfun userFun(x: Double): Double { \n")
                .append("\t\t val ret: Double = $strFunIn  \n")
                .append("\t\treturn ret\n")
                .append("\t}\n")
                .append("}\n ")

        return strBld.toString()
    }

    private fun userFunClassFileWrite(strFunIn: String): Int {
        val strFun = strFunIn.trim()
        val file = File(workDirPath + userFunFileName)
        val s = file.readText(Charsets.UTF_8)
        val sBuild = userFunClassBuild(strFun)
        if (s == sBuild) return 1
        if (file.exists()) file.delete()
        file.writeText(sBuild, Charsets.UTF_8)

        return 0
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

}