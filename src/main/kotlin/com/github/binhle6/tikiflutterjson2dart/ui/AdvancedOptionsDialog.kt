package com.github.binhle6.tikiflutterjson2dart.ui

import com.github.binhle6.tikiflutterjson2dart.ClassOptions
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.TabbedPaneImpl
import com.intellij.util.ui.JBEmptyBorder
import com.github.binhle6.tikiflutterjson2dart.AnnotationOption
import java.awt.BorderLayout
import javax.swing.*

/**
 * Json input Dialog
 */
open class AdvancedOptionsDialog(
    project: Project?,
    var classOptions: ClassOptions,
    canBeParent: Boolean,
    val doOKAction: (classOptions: ClassOptions) -> Unit
) : DialogWrapper(project, canBeParent) {

    private lateinit var finalCheckBox: JCheckBox
    private lateinit var nullableCheckBox: JCheckBox
    private lateinit var ignoreUnannotatedCheckBox: JCheckBox
    private lateinit var withCopyCheckBox: JCheckBox
    private lateinit var withEqualityCheckBox: JCheckBox
    private lateinit var nullSafetyCheckBox: JCheckBox
    private lateinit var jSerializerRadioButton: JRadioButton
    private lateinit var jsonSerializerRadioButton: JRadioButton
    private lateinit var radioGroup: ButtonGroup

    init {
        init()
        setOKButtonText("OK")
        title = "Advanced Options";
    }

    private fun setViewValues() {
        finalCheckBox.isSelected = classOptions.isFinal
        nullableCheckBox.isSelected = classOptions.jsNullable
        ignoreUnannotatedCheckBox.isSelected = classOptions.jsIgnoreUnannotated
        withEqualityCheckBox.isSelected = classOptions.withEquality
        withCopyCheckBox.isSelected = classOptions.withCopy
        nullSafetyCheckBox.isSelected = classOptions.nullSafety
        jsonSerializerRadioButton.isSelected = classOptions.annotationOption == AnnotationOption.JsonSerializer
        jSerializerRadioButton.isSelected = classOptions.annotationOption == AnnotationOption.JSerializer
    }

    override fun createCenterPanel(): JComponent? {
        val messagePanel = JPanel(BorderLayout())
        messagePanel.preferredSize
        val tabs = TabbedPaneImpl(SwingConstants.TOP);

        finalCheckBox = createCheckbox("Final");
        nullableCheckBox = createCheckbox("Nullable");
        ignoreUnannotatedCheckBox = createCheckbox("Ignore Unannotated Properties");
        withCopyCheckBox = createCheckbox("With Copy");
        withEqualityCheckBox = createCheckbox("With Equality");
        nullSafetyCheckBox = createCheckbox("Support null safety");

        jSerializerRadioButton = createRadioButton("JSerializer");
        jsonSerializerRadioButton = createRadioButton("JsonSerializer");
        radioGroup = ButtonGroup();
        radioGroup.add(jsonSerializerRadioButton);
        radioGroup.add(jSerializerRadioButton)

        val propertyPanel = createPanel();

        propertyPanel.add(finalCheckBox)
        propertyPanel.add(nullableCheckBox)
        propertyPanel.add(ignoreUnannotatedCheckBox)

        val generatePanel = createPanel()

        generatePanel.add(withCopyCheckBox)
        generatePanel.add(withEqualityCheckBox)

        val optionsPanel = createPanel()
        optionsPanel.add(nullSafetyCheckBox);

        val annotationPanel = createPanel();
        annotationPanel.add(jsonSerializerRadioButton);
        annotationPanel.add(jSerializerRadioButton);

        tabs.border = JBEmptyBorder(16, 16, 5, 16)
        tabs.addTab("Property", propertyPanel)
        //tabs.addTab("Generate", generatePanel)
        tabs.addTab("Options", optionsPanel)
        tabs.addTab("Annotation", annotationPanel)

        messagePanel.add(tabs, BorderLayout.NORTH)
        setViewValues()
        return messagePanel
    }

    private fun createCheckbox(title: String): JCheckBox {
        val checkbox = JCheckBox(title, false);

        checkbox.horizontalAlignment = SwingConstants.CENTER;
        return checkbox;
    }

    private fun createRadioButton(title: String): JRadioButton {
        val button = JRadioButton(title, false);
        button.horizontalAlignment = SwingConstants.CENTER;
        return button;
    }

    private fun createPanel(): JPanel {
        val panel = JPanel()

        panel.border = JBEmptyBorder(16, 0, 0, 0)
        val boxLayout = BoxLayout(panel, BoxLayout.Y_AXIS)
        panel.layout = boxLayout

        return panel
    }

    override fun doOKAction() {

        doOKAction(
            ClassOptions(
                isFinal = finalCheckBox.isSelected,
                jsNullable = nullableCheckBox.isSelected,
                jsIgnoreUnannotated = ignoreUnannotatedCheckBox.isSelected,
                withCopy = withCopyCheckBox.isSelected,
                withEquality = withEqualityCheckBox.isSelected,
                nullSafety = nullSafetyCheckBox.isSelected,
                annotationOption = if (jsonSerializerRadioButton.isSelected) AnnotationOption.JsonSerializer else AnnotationOption.JSerializer,
            )
        )
        super.doOKAction()
    }
}