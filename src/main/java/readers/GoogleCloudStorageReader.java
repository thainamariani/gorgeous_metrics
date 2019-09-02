/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readers;

import builder.ProgramBuilder;
import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.StorageOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thaina
 */
public class GoogleCloudStorageReader extends LocalDirectoryReader {

    private Storage storage;
    private final String bucket;
    private String rootPath;

    public GoogleCloudStorageReader(String token, String bucket, File rootPath) {
        this.bucket = bucket;
        connect(token, bucket, rootPath.getAbsolutePath());
    }

    private void connect(String token, String bucket, String rootPath) {
        try {
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(token));
            this.storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(bucket).build().getService();
            this.rootPath = rootPath;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GoogleCloudStorageReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleCloudStorageReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<String> findDirectories(String rootPath) {
        List<String> directories = new ArrayList<>();
        Page<Blob> dirs = storage.list(bucket, BlobListOption.currentDirectory(), BlobListOption.prefix(rootPath));
        do {
            for (Blob dir : dirs.iterateAll()) {
                Page<Blob> subdirs = storage.list(bucket, BlobListOption.currentDirectory(), BlobListOption.prefix(dir.getName()));
                for (Blob subdir : subdirs.iterateAll()) {
                    Page<Blob> subsubdirs = storage.list(bucket, BlobListOption.currentDirectory(), BlobListOption.prefix(subdir.getName()));
                    for (Blob subsubdir : subsubdirs.iterateAll()) {
                        if (subsubdir.getName().contains("BR_") || subsubdir.getName().contains("AR_")) {
                            directories.add(subsubdir.getName());
                        }
                    }
                }
            }
            dirs = dirs.getNextPage();
        } while (dirs.hasNextPage());
        return directories;
    }

    public void readRepository(String path) {
        Page<Blob> blobs = storage.list(bucket,
                BlobListOption.prefix(path));
        for (Blob blob : blobs.iterateAll()) {
            if ((!blob.isDirectory()) && (blob.getName().endsWith("java"))) {
                try {
                    path = rootPath + "/" + blob.getName().replace(":", "");
                    File file = new File(path);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    blob.downloadTo(Paths.get(file.getAbsolutePath()));
                } catch (IOException ex) {
                    Logger.getLogger(GoogleCloudStorageReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void readMetrics(File localRoot, String directory, ProgramBuilder builder) throws IOException {
        if (!checkDirectory(directory, rootPath)) {
            System.out.println("reading metrics");
            readRepository(directory);
            directory = directory.replace(":", "");
            super.readMetrics(localRoot, directory, builder);
        }
    }

}
