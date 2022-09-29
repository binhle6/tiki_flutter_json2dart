package com.github.binhle6.tikiflutterjson2dart.action

import com.github.binhle6.tikiflutterjson2dart.utils.DirectoryGenerator
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

class CleanArchTemplateAction : AnAction() {

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

        generateTemplate(event.dataContext, "test", false)
    }

    /**
     * Generates the Flutter Clean-Architecture structure in a [dataContext].
     * If a [root] String is provided, it will create the structure in a new folder.
     */
    private fun generateTemplate(dataContext: DataContext, root: String, doSplitSource: Boolean) {
        val project = CommonDataKeys.PROJECT.getData(dataContext) ?: return
        val selected = PlatformDataKeys.VIRTUAL_FILE.getData(dataContext) ?: return

        var folder = if (selected.isDirectory) selected else selected.parent
        WriteCommandAction.runWriteCommandAction(project) {
            if (root.isNotBlank()) {
                val result = DirectoryGenerator.createFolder(
                    project, folder, root
                ) ?: return@runWriteCommandAction
                folder = result[root]
            }
            if (doSplitSource) {
                val mapOrFalse = DirectoryGenerator.createFolder(
                    project, folder,
                    "data",
                    "repositories"
                ) ?: return@runWriteCommandAction
                mapOrFalse["data"]?.let { data: VirtualFile ->
                    DirectoryGenerator.createFolder(
                        project, data,
                        "local",
                        "models", "data_sources"
                    )
                    DirectoryGenerator.createFolder(
                        project, data,
                        "remote",
                        "models", "data_sources"
                    )
                }
            } else {
                DirectoryGenerator.createFolder(
                    project, folder,
                    "data",
                    "repositories", "data_sources", "models"
                )
            }
            DirectoryGenerator.createFolder(
                project, folder,
                "di"
            )
            DirectoryGenerator.createFolder(
                project, folder,
                "domain",
                "repositories", "use_cases", "entities"
            )
            DirectoryGenerator.createFolder(
                project, folder,
                "presentation",
                "managers", "pages", "widgets"
            )
        }
    }
}
