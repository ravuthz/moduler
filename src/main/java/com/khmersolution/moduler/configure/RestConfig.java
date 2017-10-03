package com.khmersolution.moduler.configure;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Vannaravuth Yo
 * Date : 9/16/2017, 11:58 AM
 * Email : ravuthz@gmail.com
 */

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

        final Set<BeanDefinition> beans = provider.findCandidateComponents(Package.BASE);

        for (BeanDefinition bean : beans) {
            try {
                Class<?> idExposedClasses = Class.forName(bean.getBeanClassName());
                config.exposeIdsFor(Class.forName(idExposedClasses.getName()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to expose `id` field due to", e);
            }
        }
    }

}
