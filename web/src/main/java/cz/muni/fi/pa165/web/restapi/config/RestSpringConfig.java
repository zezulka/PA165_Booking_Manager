package cz.muni.fi.pa165.web.restapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.sampledata.BookingManagerSampleDataConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validator;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * Taken from the fi-muni eshop project.
 *
 * @author Martin Kuba makub@ics.muni.cz
 */

@EnableHypermediaSupport(type = HypermediaType.HAL)
@EnableWebMvc
@Configuration
@Import({BookingManagerSampleDataConfiguration.class})
@ComponentScan(basePackages = {"cz.muni.fi.pa165.web.restapi.controllers", "cz.muni.fi.pa165.web.restapi.hateoas"})
public class RestSpringConfig implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer c) {
        c.defaultContentType(MediaTypes.HAL_JSON);
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));
        return objectMapper;
    }
    
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
