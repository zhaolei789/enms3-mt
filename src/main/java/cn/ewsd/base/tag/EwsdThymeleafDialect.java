package cn.ewsd.base.tag;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

@Component
public class EwsdThymeleafDialect extends AbstractProcessorDialect {

    //https://blog.csdn.net/whatlookingfor/article/details/78459649
    //private final IExpressionObjectFactory EXPRESSION_OBJECTS_FACTORY = new WorkFocusExpressionFactory();

    private static final String DIALECT_NAME = "zuoyoutech";
    private static final String PREFIX = "ewsd";
    public static final int PROCESSOR_PRECEDENCE = 1000;

    public EwsdThymeleafDialect() {
        // We will set this dialect the same "dialect processor" precedence as
        // the Standard Dialect, so that processor executions can interleave.
        super(DIALECT_NAME, PREFIX, PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        //在这里增加自定义的处理器
        processors.add(new SampleAttributeTagProcessor(dialectPrefix));
        processors.add(new SampleElementTagProcessor(dialectPrefix));
        processors.add(new ContainsElementTagProcessor(dialectPrefix));
        processors.add(new RbacElementTagProcessor(dialectPrefix));
        return processors;
    }

    /*@Bean
    @ConditionalOnMissingBean
    public WorkFocusDialect wlfDialect() {
        return new WorkFocusDialect();
    }*/

}