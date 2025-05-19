package com.example.plugintest.costtime

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.Handle
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.TypePath
import org.objectweb.asm.commons.AdviceAdapter

// AdviceAdapter 是 MethodVisitor 的子类，使用 AdviceAdapter 可以更方便的修改方法的字节码。
// AdviceAdapter其中几个重要方法如下：
// void visitCode()：表示 ASM 开始扫描这个方法
// void onMethodEnter()：进入这个方法
// void onMethodExit()：即将从这个方法出去
// void onVisitEnd()：表示方法扫描完毕
class CostTimeMethodVisitor(
    api: Int,
    private val visitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String?
) : AdviceAdapter(api, visitor, access, name, descriptor) {

    override fun onMethodEnter() {
        visitor.visitLdcInsn("Asm_Hook_Execute_Time")
        visitor.visitMethodInsn(
            INVOKESTATIC,
            "com/example/libcommon/Util/TimeRecordUtil",
            "markStart",
            "(Ljava/lang/String;)V",
            false
        )
    }

    override fun onMethodExit(opcode: Int) {
        visitor.visitVarInsn(ALOAD, 0)
        visitor.visitFieldInsn(
            GETFIELD,
            "com/example/administrator/androidtest/demo/MainAct",
            "TAG",
            "Ljava/lang/String;"
        )
        visitor.visitTypeInsn(NEW, "java/lang/StringBuilder")
        visitor.visitInsn(DUP)
        visitor.visitMethodInsn(
            INVOKESPECIAL,
            "java/lang/StringBuilder",
            "<init>",
            "()V",
            false
        )
        visitor.visitLdcInsn("onCreate: Asm_Hook_Execute_Time = ")
        visitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "append",
            "(Ljava/lang/String;)Ljava/lang/StringBuilder;",
            false
        )
        visitor.visitLdcInsn("Asm_Hook_Execute_Time")
        visitor.visitMethodInsn(
            INVOKESTATIC,
            "com/example/libcommon/Util/TimeRecordUtil",
            "getDuration",
            "(Ljava/lang/String;)J",
            false
        )
        visitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "append",
            "(J)Ljava/lang/StringBuilder;",
            false
        )
        visitor.visitMethodInsn(
            INVOKEVIRTUAL,
            "java/lang/StringBuilder",
            "toString",
            "()Ljava/lang/String;",
            false
        )
        visitor.visitMethodInsn(
            INVOKESTATIC,
            "android/util/Log",
            "i",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        visitor.visitInsn(POP)
        visitor.visitLdcInsn("Asm_Hook_Execute_Time")
        visitor.visitMethodInsn(
            INVOKESTATIC,
            "com/example/libcommon/Util/TimeRecordUtil",
            "remove",
            "(Ljava/lang/String;)V",
            false
        )
    }

    override fun visitFrame(
        type: Int,
        numLocal: Int,
        local: Array<out Any>?,
        numStack: Int,
        stack: Array<out Any>?
    ) {
        println("$TAG visitFrame() called with: type = $type, numLocal = $numLocal, local = $local, numStack = $numStack, stack = $stack")
        super.visitFrame(type, numLocal, local, numStack, stack)
    }

    override fun visitIincInsn(`var`: Int, increment: Int) {
        println("$TAG visitIincInsn() called with: var = $`var`, increment = $increment")
        super.visitIincInsn(`var`, increment)
    }

    override fun visitLocalVariable(
        name: String?,
        descriptor: String?,
        signature: String?,
        start: Label?,
        end: Label?,
        index: Int
    ) {
        println("$TAG visitLocalVariable() called with: name = $name, descriptor = $descriptor, signature = $signature, start = $start, end = $end, index = $index")
        super.visitLocalVariable(name, descriptor, signature, start, end, index)
    }

    override fun visitLocalVariableAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        start: Array<out Label>?,
        end: Array<out Label>?,
        index: IntArray?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("$TAG visitLocalVariableAnnotation() called with: typeRef = $typeRef, typePath = $typePath, start = $start, end = $end, index = $index, descriptor = $descriptor, visible = $visible")
        return super.visitLocalVariableAnnotation(
            typeRef,
            typePath,
            start,
            end,
            index,
            descriptor,
            visible
        )
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        println("$TAG visitMaxs() called with: maxStack = $maxStack, maxLocals = $maxLocals")
        super.visitMaxs(maxStack, maxLocals)
    }

    override fun visitMethodInsn(
        opcodeAndSource: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        println("$TAG visitMethodInsn() called with: opcodeAndSource = $opcodeAndSource, owner = $owner, name = $name, descriptor = $descriptor, isInterface = $isInterface")
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
    }

    override fun visitCode() {
        println("$TAG visitCode() called")
        super.visitCode()
    }

    override fun visitInsn(opcode: Int) {
        println("$TAG visitInsn() called with: opcode = $opcode")
        super.visitInsn(opcode)
    }

    override fun visitIntInsn(opcode: Int, operand: Int) {
        println("$TAG visitIntInsn() called with: opcode = $opcode, operand = $operand")
        super.visitIntInsn(opcode, operand)
    }

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        println("$TAG visitVarInsn() called with: opcode = $opcode, var = $`var`")
        super.visitVarInsn(opcode, `var`)
    }

    override fun visitTypeInsn(opcode: Int, type: String?) {
        println("$TAG visitTypeInsn() called with: opcode = $opcode, type = $type")
        super.visitTypeInsn(opcode, type)
    }

    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        println("$TAG visitFieldInsn() called with: opcode = $opcode, owner = $owner, name = $name, descriptor = $descriptor")
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }

    override fun visitInvokeDynamicInsn(
        name: String?,
        descriptor: String?,
        bootstrapMethodHandle: Handle?,
        vararg bootstrapMethodArguments: Any?
    ) {
        println("$TAG visitInvokeDynamicInsn() called with: name = $name, descriptor = $descriptor, bootstrapMethodHandle = $bootstrapMethodHandle, bootstrapMethodArguments = $bootstrapMethodArguments")
        super.visitInvokeDynamicInsn(
            name,
            descriptor,
            bootstrapMethodHandle,
            *bootstrapMethodArguments
        )
    }

    override fun visitJumpInsn(opcode: Int, label: Label?) {
        println("$TAG visitJumpInsn() called with: opcode = $opcode, label = $label")
        super.visitJumpInsn(opcode, label)
    }

    override fun visitLabel(label: Label?) {
        println("$TAG visitLabel() called with: label = $label")
        super.visitLabel(label)
    }

    override fun visitLdcInsn(value: Any?) {
        println("$TAG visitLdcInsn() called with: value = $value")
        super.visitLdcInsn(value)
    }

    override fun visitTableSwitchInsn(min: Int, max: Int, dflt: Label?, vararg labels: Label?) {
        println("$TAG visitTableSwitchInsn() called with: min = $min, max = $max, dflt = $dflt, labels = $labels")
        super.visitTableSwitchInsn(min, max, dflt, *labels)
    }

    override fun visitLookupSwitchInsn(dflt: Label?, keys: IntArray?, labels: Array<out Label>?) {
        println("$TAG visitLookupSwitchInsn() called with: dflt = $dflt, keys = $keys, labels = $labels")
        super.visitLookupSwitchInsn(dflt, keys, labels)
    }

    override fun visitMultiANewArrayInsn(descriptor: String?, numDimensions: Int) {
        println("$TAG visitMultiANewArrayInsn() called with: descriptor = $descriptor, numDimensions = $numDimensions")
        super.visitMultiANewArrayInsn(descriptor, numDimensions)
    }

    override fun visitTryCatchBlock(start: Label?, end: Label?, handler: Label?, type: String?) {
        println("$TAG visitTryCatchBlock() called with: start = $start, end = $end, handler = $handler, type = $type")
        super.visitTryCatchBlock(start, end, handler, type)
    }

    override fun visitParameter(name: String?, access: Int) {
        println("$TAG visitParameter() called with: name = $name, access = $access")
        super.visitParameter(name, access)
    }

    override fun visitAnnotationDefault(): AnnotationVisitor {
        println("$TAG visitAnnotationDefault() called")
        return super.visitAnnotationDefault()
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        println("$TAG visitAnnotation() called with: descriptor = $descriptor, visible = $visible")
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("$TAG visitTypeAnnotation() called with: typeRef = $typeRef, typePath = $typePath, descriptor = $descriptor, visible = $visible")
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitAnnotableParameterCount(parameterCount: Int, visible: Boolean) {
        println("$TAG visitAnnotableParameterCount() called with: parameterCount = $parameterCount, visible = $visible")
        super.visitAnnotableParameterCount(parameterCount, visible)
    }

    override fun visitParameterAnnotation(
        parameter: Int,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("$TAG visitParameterAnnotation() called with: parameter = $parameter, descriptor = $descriptor, visible = $visible")
        return super.visitParameterAnnotation(parameter, descriptor, visible)
    }

    override fun visitAttribute(attribute: Attribute?) {
        println("$TAG visitAttribute() called with: attribute = $attribute")
        super.visitAttribute(attribute)
    }

    override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        println("$TAG visitMethodInsn() called with: opcode = $opcode, owner = $owner, name = $name, descriptor = $descriptor")
        super.visitMethodInsn(opcode, owner, name, descriptor)
    }

    override fun visitInsnAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("$TAG visitInsnAnnotation() called with: typeRef = $typeRef, typePath = $typePath, descriptor = $descriptor, visible = $visible")
        return super.visitInsnAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitTryCatchAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("$TAG visitTryCatchAnnotation() called with: typeRef = $typeRef, typePath = $typePath, descriptor = $descriptor, visible = $visible")
        return super.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitLineNumber(line: Int, start: Label?) {
        println("$TAG visitLineNumber() called with: line = $line, start = $start")
        super.visitLineNumber(line, start)
    }

    override fun visitEnd() {
        println("$TAG visitEnd() called")
        super.visitEnd()
    }

    companion object {
        private const val TAG = "CostTimeMethodVisitor"
    }
}