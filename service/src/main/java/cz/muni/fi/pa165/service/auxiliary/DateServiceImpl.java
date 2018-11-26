package cz.muni.fi.pa165.service.auxiliary;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 * Returns the real system date.
 * @author Miloslav Zezulka
 */
@Service
public class DateServiceImpl implements DateService {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

}
