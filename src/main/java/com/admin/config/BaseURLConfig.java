package com.admin.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.linkbuilder.StandardLinkBuilder;

@Configuration
public class BaseURLConfig {

	@Value("${page.host}")
	private String host;
	@Value("${page.https.enabled}")
	private boolean enabled;
    @Bean
    public BaseUrlLinkBuilder baseUrlLinkBuilder(TemplateEngine templateEngine){
        BaseUrlLinkBuilder baseUrlLinkBuilder = new BaseUrlLinkBuilder();
        if(enabled) {
        	 baseUrlLinkBuilder.setBaseUrl("https://"+host);
        }else {
        	 baseUrlLinkBuilder.setBaseUrl("http://"+host);
        }
      
        templateEngine.setLinkBuilder(baseUrlLinkBuilder);
        return baseUrlLinkBuilder;
    }

    public static class BaseUrlLinkBuilder extends StandardLinkBuilder {

        private String baseUrl;

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        protected String computeContextPath(IExpressionContext context, String base, Map<String, Object> parameters) {
            if (!(context instanceof IWebContext)) {
                throw new TemplateProcessingException(
                        "Link base \"" + base + "\" cannot be context relative (/...) unless the context " +
                                "used for executing the engine implements the " + IWebContext.class.getName() + " interface");
            }

            if(baseUrl==null){
                throw new TemplateProcessingException("baseUrl 不能为空");
            }

            return baseUrl;
        }
    }

}
