package com.github.binhle6.tikiflutterjson2dart

import com.github.binhle6.tikiflutterjson2dart.utils.toUpperCaseFirstOne
import com.github.binhle6.tikiflutterjson2dart.utils.snakeCaseToPascalCase

abstract class TypeDefinition(val name: String, var typeName: String)

/**
 * custom class type
 * need generate class code
 */
class CustomClassType(
    name: String,
    val fieldList: List<TypeDefinition>,
    var classOptions: ClassOptions
) : TypeDefinition(name, name.snakeCaseToPascalCase().toUpperCaseFirstOne()) {
}

/**
 * dart language internal class type
 * like int,double,String,bool
 * don't need to generate class code
 */
class InternalClassType(
    name: String, fieldType: String
) : TypeDefinition(name, fieldType)

class ListClassType(
    name: String, val genericsType: TypeDefinition
) :
    TypeDefinition(name, "List<${genericsType.typeName}>")

enum class AnnotationOption {
    JsonSerializer, JSerializer
}

class ClassOptions(
    var isFinal: Boolean = false,
    var jsNullable: Boolean = false,
    var jsIgnoreUnannotated: Boolean = false,
    var withCopy: Boolean = false,
    var withEquality: Boolean = false,
    var nullSafety: Boolean = false,
    var annotationOption: AnnotationOption = AnnotationOption.JsonSerializer,
)