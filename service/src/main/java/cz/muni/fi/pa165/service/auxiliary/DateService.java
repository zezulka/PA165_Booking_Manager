package cz.muni.fi.pa165.service.auxiliary;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * @author Miloslav Zezulka
 * 
 * Service which returns the current date.
 */
@Service
public interface DateService {
    
    LocalDate getCurrentDate();
}
