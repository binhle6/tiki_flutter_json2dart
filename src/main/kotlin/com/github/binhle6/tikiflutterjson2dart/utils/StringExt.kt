package com.github.binhle6.tikiflutterjson2dart.utils

import java.util.regex.Pattern

fun String.toUpperCaseFirstOne(): String {
    return when {
        isEmpty() -> ""
        Character.isUpperCase(this[0]) -> this
        else -> StringBuilder().append(Character.toUpperCase(this[0])).append(this.substring(1)).toString()
    }
}

fun String.toLowerCaseFirstOne(): String {
    return if (Character.isLowerCase(this[0]))
        this
    else
        StringBuilder().append(Character.toLowerCase(this[0])).append(this.substring(1)).toString()
}

fun String.snakeCaseToPascalCase(): String {
    val linePattern = Pattern.compile("_([a-z])")
    val matcher = linePattern.matcher(this)
    val sb = StringBuffer()
    while (matcher.find()) {
        matcher.appendReplacement(sb, matcher.group(1).toUpperCase())
    }
    matcher.appendTail(sb)
    return sb.toString()
}

fun String.pascalCaseToSnakeCase(): String {
    val humpPattern = Pattern.compile("\\B(\\p{Upper})(\\p{Lower}*)")
    val matcher = humpPattern.matcher(this)
    val sb = StringBuffer()
    while (matcher.find()) {
        matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
    }
    matcher.appendTail(sb)
    return sb.toString()
}

//remove List class name last 's'
fun String.removeLastS(): String {
    return if (this.endsWith("s")) {
        this.substring(0, this.length - 1)
    } else {
        this
    }
}
