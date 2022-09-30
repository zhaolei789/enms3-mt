package cn.ewsd.base.tag;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class SampleAttributeTagProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "sample1";
    private static final int PRECEDENCE = 10000;

    public SampleAttributeTagProcessor(final String dialectPrefix) {
        /*templateMode: 模板模式，这里使用HTML模板。
         dialectPrefix: 标签前缀。即xxx:text中的xxx。在此例子中prefix为thSys。
         elementName：匹配标签元素名。举例来说如果是div，则我们的自定义标签只能用在div标签中。为null能够匹配所有的标签。
         prefixElementName: 标签名是否要求前缀。
         attributeName: 自定义标签属性名。这里为text。
         prefixAttributeName：属性名是否要求前缀，如果为true，Thymeeleaf会要求使用text属性时必须加上前缀，即thSys:text。
         precedence：标签处理的优先级，此处使用和Thymeleaf标准方言相同的优先级。
         removeAttribute：标签处理后是否移除自定义属性。*/
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's own precedence)
                true);             // Remove the matched attribute afterwards
    }


    @Override
    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        final IEngineConfiguration configuration = context.getConfiguration();

        /*
         * Obtain the Thymeleaf Standard Expression parser
         */
        final IStandardExpressionParser parser =
                StandardExpressions.getExpressionParser(configuration);

        /*
         * Parse the attribute value as a Thymeleaf Standard Expression
         */
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);

        /*
         * Execute the expression just parsed
         */
        final Integer position = (Integer) expression.execute(context);

        if (position.equals(1)) {
            structureHandler.setAttribute("style", "background:green");
        } else {
            structureHandler.setAttribute("style", "background:red");
        }
    }

}
