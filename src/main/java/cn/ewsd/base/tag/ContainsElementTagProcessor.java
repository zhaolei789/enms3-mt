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

public class ContainsElementTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "contains";
    private static final int PRECEDENCE = 1000;

    public ContainsElementTagProcessor(final String dialectPrefix) {
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

        //获取标签自定义属性的值 这里定义了两个，srcList和str
        String srcList = tag.getAttributeValue("strList");
        String str = tag.getAttributeValue("str");

        if (srcList.contains(str)) {
            structureHandler.removeTags();
        } else {
            structureHandler.removeTags();
            structureHandler.removeElement();
        }
    }

}