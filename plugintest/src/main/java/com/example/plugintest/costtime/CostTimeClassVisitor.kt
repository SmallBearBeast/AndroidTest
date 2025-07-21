package com.example.plugintest.costtime

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.ModuleVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.RecordComponentVisitor
import org.objectweb.asm.TypePath

class CostTimeClassVisitor(visitor: ClassVisitor) : ClassVisitor(Opcodes.ASM6, visitor) {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("$TAG visitMethod: access = $access, name = $name, descriptor = $descriptor, signature = $signature, exceptions = $exceptions")
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (name == "onCreate" || name == "testSpToMmkv" || name == "onWindowFocusChanged") {
            return CostTimeMethodVisitor(Opcodes.ASM6, methodVisitor, access, name, descriptor)
        }
        return methodVisitor
    }

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        println("$TAG visit: version = $version, access = $access, name = $name, signature = $signature, superName = $superName, interfaces = $interfaces")
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitSource(source: String?, debug: String?) {
        println("$TAG visitSource: source = $source, debug = $debug")
        super.visitSource(source, debug)
    }

    override fun visitModule(name: String?, access: Int, version: String?): ModuleVisitor {
        println("$TAG visitModule: name = $name, access = $access, version = $version")
        return super.visitModule(name, access, version)
    }

    override fun visitNestHost(nestHost: String?) {
        println("$TAG visitNestHost: nestHost = $nestHost")
        super.visitNestHost(nestHost)
    }

    override fun visitOuterClass(owner: String?, name: String?, descriptor: String?) {
        println("$TAG visitOuterClass: owner = $owner, name = $name, descriptor = $descriptor")
        super.visitOuterClass(owner, name, descriptor)
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        println("$TAG visitAnnotation: descriptor = $descriptor, visible = $visible")
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitTypeAnnotation(
        typeRef: Int,
        typePath: TypePath?,
        descriptor: String?,
        visible: Boolean
    ): AnnotationVisitor {
        println("$TAG visitTypeAnnotation: typeRef = $typeRef, typePath = $typePath, descriptor = $descriptor, visible = $visible")
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitAttribute(attribute: Attribute?) {
        println("$TAG visitAttribute: attribute = $attribute")
        super.visitAttribute(attribute)
    }

    override fun visitNestMember(nestMember: String?) {
        println("$TAG visitNestMember: nestMember = $nestMember")
        super.visitNestMember(nestMember)
    }

    override fun visitPermittedSubclass(permittedSubclass: String?) {
        println("$TAG visitPermittedSubclass: permittedSubclass = $permittedSubclass")
        super.visitPermittedSubclass(permittedSubclass)
    }

    override fun visitInnerClass(
        name: String?,
        outerName: String?,
        innerName: String?,
        access: Int
    ) {
        println("$TAG visitInnerClass: name = $name, outerName = $outerName, innerName = $innerName, access = $access");
        super.visitInnerClass(name, outerName, innerName, access)
    }

    override fun visitRecordComponent(
        name: String?,
        descriptor: String?,
        signature: String?
    ): RecordComponentVisitor {
        println("$TAG visitRecordComponent: name = $name, descriptor = $descriptor, signature = $signature")
        return super.visitRecordComponent(name, descriptor, signature)
    }

    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?
    ): FieldVisitor {
        println("$TAG visitField: access = $access, name = $name, descriptor = $descriptor, signature = $signature, value = $value")
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitEnd() {
        println("$TAG visitEnd")
        super.visitEnd()
    }

    companion object {
        private const val TAG = "CostTimeClassVisitor"
    }
}