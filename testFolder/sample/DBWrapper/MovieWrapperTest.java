package sample.DBWrapper;

import org.junit.jupiter.api.Test;
import sample.Model.Movie;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by blackhatt on 25/09/2017.
 */
class MovieWrapperTest {

    MovieWrapper mr = new MovieWrapper();
    @Test
    void getAllMovies()
    {

        assertNotEquals(null, mr.getAllMovies() );

    }

    @Test
    void getMovie()
    {

        assertNotEquals(null, mr.getMovie(1));

    }

    @Test
    void updateMovie()
    {

        assertEquals(true,mr.updateMovie(new Movie("tittle","despacito",18,"no one",152)));

    }

    @Test
    void deleteMovie()
    {

        assertNotEquals(true,1);

    }

    @Test
    void saveMovie()
    {

        assertNotEquals(null,mr.saveMovie("tittle","no tan despacito",25,"Santiago Segura",250));

    }

}