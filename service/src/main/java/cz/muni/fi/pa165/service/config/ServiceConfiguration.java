package cz.muni.fi.pa165.service.config;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.service.RoomServiceImpl;
import cz.muni.fi.pa165.service.UserServiceImpl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {RoomServiceImpl.class, UserServiceImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        //dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }
//    public class DozerCustomConfig extends BeanMappingBuilder {
//        @Override
//        protected void configure() {
//            //mapping(Room.class, RoomDTO.class);
//        }
//    }
}