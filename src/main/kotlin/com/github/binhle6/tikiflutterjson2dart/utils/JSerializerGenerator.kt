package com.github.binhle6.tikiflutterjson2dart.utils

import com.github.binhle6.tikiflutterjson2dart.ClassOptions

class JSerializerGenerator(classOptions: ClassOptions, fileName: String) : DartClassGenerator(classOptions, fileName) {

    override fun importString(): String {
        return """import 'package:jserializer/jserializer.dart'; """
    }

    override fun classHead(fileName: String): String {
        return ""
    }

    override fun classAnnotation(classOptions: ClassOptions): String {
        return "@JSerializable()"
    }

    override fun fieldAnnotation(name: String): String {
        return "@JKey(name: '${name}')"
    }

    override fun afterFieldsString(className: String): String {
        return ""
    }
}
