package me.phastixtv.phallysystemvelocity.database;

import com.velocitypowered.api.plugin.annotation.DataDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MySQLDriver {

    private static final String MYSQL_VERSION = "8.0.33";

    private final Path dataDirectory;

    public MySQLDriver(@DataDirectory Path dataDirectory) throws Exception {
        this.dataDirectory = dataDirectory;

        if (ReflectUtil.getClass("com.mysql.cj.jdbc.Driver") != null) return;
        if (!Files.exists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }
        if (loadCache()) {
            return;
        }
        downloadAndLoad();
    }

    private String getMySQLDownloadUrl() {
        return "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/"
                .concat(MYSQL_VERSION)
                .concat("/")
                .concat(getMySQLLibraryName());
    }

    public boolean loadCache() {
        File libraryFile = getMySQLLiraryFile();
        if (libraryFile.exists()) {
            try {
                ReflectUtil.addFileLibrary(libraryFile);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private String getMySQLLibraryName() {
        return "mysql-connector-j-" + MYSQL_VERSION + ".jar";
    }
    private File getMySQLLiraryFile() {
        return new File(dataDirectory.toFile(), getMySQLLibraryName());
    }

    public void downloadAndLoad() throws Exception {
        File libraryFile = getMySQLLiraryFile();
        Path path = new DownloadTask(getMySQLDownloadUrl(), libraryFile.toPath()).call();
        try {
            ReflectUtil.addFileLibrary(libraryFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
