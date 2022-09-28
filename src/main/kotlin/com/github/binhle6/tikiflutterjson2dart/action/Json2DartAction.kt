package com.github.binhle6.tikiflutterjson2dart.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.github.binhle6.tikiflutterjson2dart.*
import com.github.binhle6.tikiflutterjson2dart.repos.StorageRepo
import com.github.binhle6.tikiflutterjson2dart.ui.JsonInputDialog
import com.github.binhle6.tikiflutterjson2dart.utils.*

class Json2DartAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val dataContext = event.dataContext
        val module = LangDataKeys.MODULE.getData(dataContext) ?: return
        //Get the folder selected by the right mouse button
        val directory = when (val navigationTable = LangDataKeys.NAVIGATABLE.getData(dataContext)) {
            is PsiDirectory -> navigationTable
            is PsiFile -> navigationTable.containingDirectory
            else -> {
                val root = ModuleRootManager.getInstance(module)
                root.sourceRoots
                    .asSequence()
                    .mapNotNull {
                        PsiManager.getInstance(project).findDirectory(it)
                    }.firstOrNull()
            }
        } ?: return

        JsonInputDialog(project) {
            // Remove the file suffix
            var fileName = it.inputClassName.trim().split(".")[0]
            fileName = fileName.pascalCaseToSnakeCase().toLowerCaseFirstOne()
            if (containsFile(directory, fileName)) {
                Messages.showInfoMessage(project, "The $fileName.dart already exists", "Info")
                return@JsonInputDialog
            }

            val map = parseInputJson(it.inputJson)

            val classOptions = it.classOptions
            StorageRepo.saveOptions(options = classOptions)

            val suffix = it.inputSuffix.trim().split(".")[0]

            val dartClassDefinition = map2CustomClassDefinition(fileName, map, classOptions, suffix)

            val generator = DartFileGenerator(project, directory, fileName)
//            val classGenerator = if (classOptions.annotationOption == AnnotationOption.JsonSerializer)
//                JsonSerializerGenerator(classOptions, fileName) else JSerializerGenerator(classOptions, fileName)
            val classGenerator = when (classOptions.annotationOption) {
                AnnotationOption.None -> DartClassGenerator(classOptions, fileName)
                AnnotationOption.JsonSerializer -> JsonSerializerGenerator(classOptions, fileName)
                AnnotationOption.JSerializer -> JSerializerGenerator(classOptions, fileName)
            }
            val codeContent = classGenerator.generateCode(dartClassDefinition)
            generator.generateDarFile(codeContent)
        }.show()
    }


    private fun containsFile(directory: PsiDirectory, fileName: String): Boolean {
        return directory.files.filter { it.name.endsWith(".dart") }
            .firstOrNull { fileName.equals(it.name.split(".dart")[0], true) } != null
    }
}
