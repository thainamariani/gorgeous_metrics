/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import builder.ProgramBuilder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import readers.GoogleCloudStorageReader;

/**
 *
 * @author thaina
 */
public class ThreadDownload implements Runnable {

    private String directory;
    private GoogleCloudStorageReader reader;
    private File ROOT_PATH = new File("/Users/Thaina/Downloads");

    public ThreadDownload(String directory, GoogleCloudStorageReader reader) {
        this.directory = directory;
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            ProgramBuilder builder = new ProgramBuilder();
            reader.readMetrics(ROOT_PATH, directory, builder);
            System.out.println(directory);
        } catch (IOException ex) {
            Logger.getLogger(ThreadDownload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}