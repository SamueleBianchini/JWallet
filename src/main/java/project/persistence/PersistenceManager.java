package project.persistence;

import java.io.IOException;
import java.util.Optional;

/**
 * Interface defining save and load operations for persistent data.
 *
 * @param <T> the type of object to persist
 */
interface PersistenceManager<T> {

    /**
     * Saves the given state to persistent storage.
     *
     * @param state the state object to save
     * @throws IOException if an I/O error occurs
     */
    void save(T state) throws IOException;

    /**
     * Loads the state from persistent storage.
     *
     * @return an Optional containing the loaded state if present, or empty if no file exists
     * @throws IOException if an I/O error occurs
     */
    Optional<T> load() throws IOException;
}
