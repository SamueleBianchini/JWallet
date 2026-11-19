package project.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Optional;
import project.model.AppState;

/**
 * Basic JSON implementation of {@link PersistenceManager} using Google Gson.
 */
public final class GsonPersistenceManager implements PersistenceManager<AppState> {

    private final File file;
    private final Gson gson;

    /**
     * Constructs a new GsonPersistenceManager.
     *
     * @param filePath the path to the JSON save file
     */
    public GsonPersistenceManager(final String filePath) {
        this.file = new File(filePath);
        /* Gson cannot handle LocalDate alone: we save it as text (2026-11-18) and read it back as a date. */
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (date, type, context) -> new JsonPrimitive(date.toString()))
                .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void save(final AppState account) throws IOException {
        if (account == null) {
            throw new IllegalArgumentException("BankAccount cannot be null");
        }
        final File parent = file.getParentFile();
        if (parent != null) {
            Files.createDirectories(parent.toPath());
        }
        try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            gson.toJson(account, writer);
        }
    }

    @Override
    public Optional<AppState> load() throws IOException {
        if (!file.exists() || file.length() == 0) {
            return Optional.empty();
        }
        try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            final AppState account = gson.fromJson(reader, AppState.class);
            return Optional.ofNullable(account);
        }
    }
}
