package cz.muni.fi.pa165.sampledata;

import java.io.IOException;

/**
 * Populates database with sample data.
 *
 * @author Miloslav Zezulka
 */
public interface SampleDataLoadingFacade {

    void loadData() throws IOException;
}
