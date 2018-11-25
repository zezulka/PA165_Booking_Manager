package cz.muni.fi.pa165.service.config;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.service.RoomServiceImpl;
import cz.muni.fi.pa165.service.UserServiceImpl;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
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
@ComponentScan(basePackageClasses = {RoomServiceImpl.class, UserServiceImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer(){
        return new DozerBeanMapper(Arrays.asList("dozer-mappings.xml"));
    }
}
