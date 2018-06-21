package com.sea.shiroThymeleaf.processor.element;

import com.sea.shiroThymeleaf.processor.ShiroFacade;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

import static com.sea.shiroThymeleaf.processor.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static com.sea.shiroThymeleaf.processor.ThymeleafFacade.getRawValue;


public class HasAnyRolesElementProcessor extends AbstractElementTagProcessor {
    private static final String ELEMENT_NAME = "hasanyroles";
    private static final int PRECEDENCE = 300;

    private static final String DELIMITER = ",";

    public HasAnyRolesElementProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                ELEMENT_NAME, // Tag name: match specifically this tag
                true, // Apply dialect prefix to tag name
                null, // No attribute name: will match by tag name
                false, // No prefix to be applied to attribute name
                PRECEDENCE); // Precedence (inside dialect's own precedence)
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             IElementTagStructureHandler iElementTagStructureHandler) {
        final String rawValue = getRawValue(iProcessableElementTag, "name");
        final List<String> values = evaluateAsStringsWithDelimiter(iTemplateContext, rawValue, DELIMITER);

        if (ShiroFacade.hasAnyRoles(values)) {
            iElementTagStructureHandler.removeTags();
        } else {
            iElementTagStructureHandler.removeElement();
        }
    }
}