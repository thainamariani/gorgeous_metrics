package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import readers.GoogleCloudStorageReader;

public class MainMetrics {

    //private static final File ROOT_PATH = new File("/home/tmariani");
    private static final File ROOT_PATH = new File("/Users/Thaina/Downloads");
    // private static final String ROOT_PATH_GOOGLE = "repositories/";
    private static final String TOKEN = "repositoriesapp-d3f6c6243a6d.json";
    private static final String BUCKET = "repositoriesapp.appspot.com";

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException, IOException {
        File fileToRead = new File("repositories.txt");
        Scanner sc = new Scanner(fileToRead);

        HashSet<String> directories = new HashSet<>();
        while (sc.hasNextLine()) {
            String directory = sc.nextLine();
            directories.add(directory);
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        GoogleCloudStorageReader reader = new GoogleCloudStorageReader(TOKEN, BUCKET, ROOT_PATH);
        ROOT_PATH.mkdirs();
        for (String directory : directories) {
            threadPool.submit(new ThreadDownload(directory, reader));
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

}