package cz.muni.fi.pa165.sampledata;

import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 * @author Miloslav Zezulka
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {SampleDataLoadingFacadeImpl.class})
public class BookingManagerSampleDataDataConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(BookingManagerSampleDataDataConfiguration.class);

    @Autowired
    SampleDataLoadingFacade sampleDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        LOGGER.debug("dataLoading()");
        sampleDataLoadingFacade.loadData();
    }
}
