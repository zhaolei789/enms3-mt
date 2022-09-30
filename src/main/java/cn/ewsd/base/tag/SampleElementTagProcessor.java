package cn.ewsd.base.tag;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class SampleElementTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "sample3";
    private static final int PRECEDENCE = 1000;

    public SampleElementTagProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                TAG_NAME,          // Tag name: match specifically this tag
                true,              // Apply dialect prefix to tag name
                null,              // No attribute name: will match by tag name
                false,             // No prefix to be applied to attribute name
                PRECEDENCE);       // Precedence (inside dialect's own precedence)
    }

    @Override
    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final IElementTagStructureHandler structureHandler) {

        /*
         * Read the 'order' attribute from the tag. This optional attribute in our tag
         * will allow us to determine whether we want to show a random headline or
         * only the latest one ('latest' is default).
         */
        final String statusValue = tag.getAttributeValue("status");

        final IEngineConfiguration configuration = context.getConfiguration();
        /*
         * Obtain the Thymeleaf Standard Expression parser
         */
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression = parser.parseExpression(context, statusValue);

        final Integer parseStatus = (Integer) expression.execute(context);

        /*
         * Create the DOM structure that will be substituting our custom tag.
         */
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        if (parseStatus.equals(0)) {
            model.add(modelFactory.createOpenElementTag("td", "style", "background:green"));
            model.add(modelFactory.createText(HtmlEscape.escapeHtml5("停用")));
        } else {
            model.add(modelFactory.createOpenElementTag("td", "style", "background:red"));
            model.add(modelFactory.createText(HtmlEscape.escapeHtml5("启用")));
        }
        model.add(modelFactory.createCloseElementTag("td"));

        /*
         * Instruct the engine to replace this entire element with the specified model.
         */
        structureHandler.replaceWith(model, false);
    }

}