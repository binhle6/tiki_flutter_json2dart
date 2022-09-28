package com.github.binhle6.tikiflutterjson2dart.repos

import com.intellij.ide.util.PropertiesComponent
import com.github.binhle6.tikiflutterjson2dart.AnnotationOption
import com.github.binhle6.tikiflutterjson2dart.ClassOptions

class StorageRepo {
    companion object {
        fun getOptions(): ClassOptions {
            return ClassOptions(
                annotationOption = AnnotationOption.valueOf(
                    getString("annotation", AnnotationOption.JsonSerializer.toString())
                ),
                isExplicitToJson = getBoolean("isExplicitToJson", true),
                isIgnoreUnannotated = getBoolean("isIgnoreUnannotated", false),
                isFinal = getBoolean("isFinal", false),
                isNullable = getBoolean("isNullable", true),
                isNullSafety = getBoolean("isNullSafety", true),
            )
        }

        fun saveOptions(options: ClassOptions) {
            setString("annotation", options.annotationOption.toString())
            setBoolean("isExplicitToJson", options.isExplicitToJson)
            setBoolean("isIgnoreUnannotated", options.isIgnoreUnannotated)
            setBoolean("isFinal", options.isFinal)
            setBoolean("isNullable", options.isNullable)
            setBoolean("nullSafety", options.isNullSafety)
        }

        private fun getBoolean(name: String, defaultValue: Boolean): Boolean {
            return PropertiesComponent.getInstance().getBoolean(name, defaultValue)
        }

        private fun setBoolean(name: String, value: Boolean) {
            return PropertiesComponent.getInstance().setValue(name, value)
        }

        private fun setString(name: String, value: String) {
            return PropertiesComponent.getInstance().setValue(name, value)
        }


        private fun getString(name: String, defaultValue: String): String {
            return PropertiesComponent.getInstance().getValue(name, defaultValue)
        }
    }
}
