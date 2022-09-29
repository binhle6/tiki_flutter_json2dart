package com.github.binhle6.tikiflutterjson2dart.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField

class FeatureInfoDialog(project: Project?) :
    DialogWrapper(project) {
    private var contentPanel: JPanel? = null
    private var featureNameTextField: JTextField? = null
    private var splitDataSourceCheckBox: JCheckBox? = null

    /**
     * @return feature name
     */
    fun getName(): String = featureNameTextField?.text ?: "new_feature"

    /**
     * @return split data sources
     */
    fun splitSource(): Boolean = splitDataSourceCheckBox?.isSelected ?: false

    override fun createCenterPanel(): JComponent? {
        return contentPanel
    }

    /**
     * Sets focus on the text field
     */
    override fun getPreferredFocusedComponent(): JComponent? {
        return featureNameTextField
    }

    init {
        init()
        title = "Clean Architecture Generator"
    }
}