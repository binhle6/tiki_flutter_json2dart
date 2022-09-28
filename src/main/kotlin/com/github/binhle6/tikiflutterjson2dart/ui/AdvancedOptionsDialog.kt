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

    private lateinit var noneRadioButton: JRadioButton
    private lateinit var jSerializerRadioButton: JRadioButton
    private lateinit var jsonSerializerRadioButton: JRadioButton
    private lateinit var radioGroup: ButtonGroup

    private lateinit var explicitToJsonCheckBox: JCheckBox
    private lateinit var ignoreUnannotatedCheckBox: JCheckBox

    private lateinit var finalCheckBox: JCheckBox
    private lateinit var nullableCheckBox: JCheckBox

//    private lateinit var withCopyCheckBox: JCheckBox
//    private lateinit var withEqualityCheckBox: JCheckBox

    private lateinit var nullSafetyCheckBox: JCheckBox

    init {
        init()
        setOKButtonText("OK")
        title = "Advanced Options";
    }

    private fun setViewValues() {
        noneRadioButton.isSelected = classOptions.annotationOption == AnnotationOption.None
        jsonSerializerRadioButton.isSelected = classOptions.annotationOption == AnnotationOption.JsonSerializer
        jSerializerRadioButton.isSelected = classOptions.annotationOption == AnnotationOption.JSerializer

        explicitToJsonCheckBox.isSelected = classOptions.isExplicitToJson
        ignoreUnannotatedCheckBox.isSelected = classOptions.isIgnoreUnannotated

        finalCheckBox.isSelected = classOptions.isFinal
        nullableCheckBox.isSelected = classOptions.isNullable

//        withCopyCheckBox.isSelected = classOptions.withCopy
//        withEqualityCheckBox.isSelected = classOptions.withEquality

        nullSafetyCheckBox.isSelected = classOptions.isNullSafety
    }

    override fun createCenterPanel(): JComponent? {
        val messagePanel = JPanel(BorderLayout())
        messagePanel.preferredSize
        val tabs = TabbedPaneImpl(SwingConstants.TOP)

        noneRadioButton = createRadioButton("None")
        jSerializerRadioButton = createRadioButton("JSerializer");
        jsonSerializerRadioButton = createRadioButton("JsonSerializer")
        radioGroup = ButtonGroup()
        radioGroup.add(noneRadioButton)
        radioGroup.add(jsonSerializerRadioButton)
        radioGroup.add(jSerializerRadioButton)
        val annotationPanel = createPanel()
        annotationPanel.add(noneRadioButton)
        annotationPanel.add(jsonSerializerRadioButton)
        annotationPanel.add(jSerializerRadioButton)

        explicitToJsonCheckBox = createCheckbox("Explicit to json")
        ignoreUnannotatedCheckBox = createCheckbox("Ignore unannotated properties")
        val annoParamPanel = createPanel()
        annoParamPanel.add(explicitToJsonCheckBox)
        annoParamPanel.add(ignoreUnannotatedCheckBox)

        finalCheckBox = createCheckbox("Final");
        nullableCheckBox = createCheckbox("Nullable")
        val propertyPanel = createPanel()
        propertyPanel.add(finalCheckBox)
        propertyPanel.add(nullableCheckBox)

//        withCopyCheckBox = createCheckbox("With Copy")
//        withEqualityCheckBox = createCheckbox("With Equality")
//        val generatePanel = createPanel()
//        generatePanel.add(withCopyCheckBox)
//        generatePanel.add(withEqualityCheckBox)

        nullSafetyCheckBox = createCheckbox("Support null safety")

        tabs.border = JBEmptyBorder(16, 16, 5, 16)
        tabs.addTab("Annotation", annotationPanel)
        tabs.addTab("Parameter", annoParamPanel)
        tabs.addTab("Property", propertyPanel)
        //tabs.addTab("Generate", generatePanel)

        messagePanel.add(tabs, BorderLayout.NORTH)
        messagePanel.add(nullSafetyCheckBox, BorderLayout.SOUTH)
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
                annotationOption = if (noneRadioButton.isSelected) AnnotationOption.None
                else if (jsonSerializerRadioButton.isSelected) AnnotationOption.JsonSerializer
                else AnnotationOption.JSerializer,
                isExplicitToJson = explicitToJsonCheckBox.isSelected,
                isIgnoreUnannotated = ignoreUnannotatedCheckBox.isSelected,
                isFinal = finalCheckBox.isSelected,
                isNullable = nullableCheckBox.isSelected,
//                withCopy = withCopyCheckBox.isSelected,
//                withEquality = withEqualityCheckBox.isSelected,
                isNullSafety = nullSafetyCheckBox.isSelected,
            )
        )
        super.doOKAction()
    }
}
