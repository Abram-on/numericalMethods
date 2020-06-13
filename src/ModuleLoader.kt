import java.io.*
import java.lang.reflect.Method

class ModuleLoader(private val pathtobin: String, parent: ClassLoader?) : ClassLoader(parent) {

    var method: Method? = null
    var onLineLib: Any? = null

    fun loadOnLineLib(userFunClassName: String, userFunName: String){
        val param = Double::class.java
        val loadClass = this.findClass(userFunClassName)
        val constructor = loadClass.constructors.first()
        onLineLib = constructor.newInstance()
        method = loadClass.getMethod(userFunName, param)

    }


    @Throws(ClassNotFoundException::class)
    override fun findClass(className: String): Class<*> {
        return try {
            val b = fetchClassFromFS("$pathtobin$className.class")
            println("$pathtobin$className.class")
         //return loadClass(className)

        return defineClass(className, b, 0, b.size)
        } catch (ex: FileNotFoundException) {
            super.findClass(className)
        } catch (ex: IOException) {
            super.findClass(className)
        }
    }

    @Throws(FileNotFoundException::class, IOException::class)
    private fun fetchClassFromFS(path: String): ByteArray {

        val ist: InputStream = FileInputStream(File(path))
        val length = File(path).length()

        val bytes = ByteArray(length.toInt())

        var offset = 0
        var numRead = 0
        while (offset < bytes.size
                && ist.read(bytes, offset, bytes.size - offset).also { numRead = it } >= 0) {
            offset += numRead
        }

        if (offset < bytes.size) {
            throw IOException("Could not completely read file $path")
        }

        ist.close()
        return bytes
    }
}