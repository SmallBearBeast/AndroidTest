package com.example.plugintest

import com.android.build.gradle.AppExtension
import com.example.plugintest.costtime.CostTimeTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomTransformPlugin: Plugin<Project>{
    override fun apply(project: Project) {
        println("Hello CustomTransformPlugin")
        // 1、获取 Android 扩展
        val androidExtension = project.extensions.getByType(AppExtension::class.java)
        // 2、注册 Transform
        androidExtension.registerTransform(CustomTransform())
        androidExtension.registerTransform(CostTimeTransform())
    }
}