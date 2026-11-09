package project.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import project.model.BankAccountImpl;

/**
 * Basic JSON implementation of {@link PersistenceManager} using Google Gson.
 * Specifies UTF-8 character encoding and handles directory creation to comply with QA standards.
 */
public final class GsonPersistenceManager implements PersistenceManager<BankAccountImpl> {

    private final File file;
    private final Gson gson;

    /**
     * Constructs a new GsonPersistenceManager.
     *
     * @param filePath the path to the JSON save file
     */
    public GsonPersistenceManager(final String filePath) {
        this.file = new File(filePath);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void save(final BankAccountImpl account) throws IOException {
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
    public Optional<BankAccountImpl> load() throws IOException {
        if (!file.exists() || file.length() == 0) {
            return Optional.empty();
        }
        try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            final BankAccountImpl account = gson.fromJson(reader, BankAccountImpl.class);
            return Optional.ofNullable(account);
        }
    }
}
