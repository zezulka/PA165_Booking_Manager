package cz.muni.fi.pa165.service.config;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Petr Valenta
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165")
public class ServiceConfiguration {
    @Bean
    public Mapper dozer(){
        return DozerBeanMapperBuilder.create()
                .withMappingFiles("dozer-mappings.xml", "dozerJdk8Converters.xml")
                .build();
    }
}
