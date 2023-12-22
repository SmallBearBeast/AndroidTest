package com.example.plugintest.costtime

import com.android.build.api.variant.VariantInfo
import com.example.plugintest.BaseCustomTransform
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.InputStream
import java.io.OutputStream

class CostTimeTransform : BaseCustomTransform(true){

    override fun getName(): String {
        return "CostTimeTransform"
    }

    override fun classFilter(className: String): Boolean {
        return className.endsWith("MainAct.class")
    }

    override fun applyToVariant(variant: VariantInfo?): Boolean {
        println("applyToVariant: variant = $variant")
        return variant?.buildTypeName == "debug"
    }

    override fun provideFunction(): (InputStream, OutputStream) -> Unit {
        return { inputStream, outputStream ->
            // 使用 input 输入流构建 ClassReader
            val reader = ClassReader(inputStream)
            // 使用 ClassReader 和 flags 构建 ClassWriter
            val writer = ClassWriter(reader, ClassWriter.COMPUTE_FRAMES)
            // 使用 ClassWriter 构建我们自定义的 ClassVisitor
            val visitor = CostTimeClassVisitor(writer)
            // 最后通过 ClassReader 的 accept 将每一条字节码指令传递给 ClassVisitor
            reader.accept(visitor, ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES)
            // 将修改后的字节码文件转换成字节数组，最后通过输出流修改文件，这样就实现了字节码的插桩
            outputStream.write(writer.toByteArray())
        }
    }
}