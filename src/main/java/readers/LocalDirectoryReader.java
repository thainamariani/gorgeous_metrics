/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readers;

import builder.ProgramBuilder;
import elements.Program;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import static writers.JsonWriter.generateJsonFiles;

/**
 *
 * @author Thainá Mariani
 */
public class LocalDirectoryReader extends Reader {

    @Override
    public List<String> findDirectories(String rootPath) {
        File rootDir = new File(rootPath);
        List<String> directories = new ArrayList<>();
        for (File dir : rootDir.listFiles()) {
            if (dir.isDirectory()) {
                directories.add(dir.getAbsolutePath());
            }
        }
        return directories;
    }

    @Override
    public Set<String> findSourcePaths(String pathName) {
        Set<String> sourcePaths = new HashSet<>();
        File path = new File(pathName);
        return findSourcePaths(path, sourcePaths);
    }

    @Override
    public Set<String> findSourcePaths(Object path, Set sourcePaths) {
        try {
            File[] listFiles = ((File) path).listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        if (file.getName().equals("src")) {
                            sourcePaths.add(file.getCanonicalPath() + "/main/java");
                        } else {
                            findSourcePaths(file, sourcePaths);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LocalDirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sourcePaths;
    }

    @Override
    public HashMap<String, String> getJavaFiles(String pathName) {
        File path = new File(pathName);
        HashMap<String, String> files = new HashMap<>();
        return getJavaFiles(path, files);
    }

    @Override
    public HashMap<String, String> getJavaFiles(Object path, HashMap files) {
        File[] list = ((File) path).listFiles();
        if (list != null) {
            List<File> listFiles = new ArrayList<>();
            listFiles.addAll(Arrays.asList(((File) path).listFiles()));
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    getJavaFiles(file, files);
                } else {
                    if (file.getName().contains("java")) {
                        files.put(file.getAbsolutePath(),
                                parseJavaToString(file.getAbsolutePath()));
                    }
                }
            }
        }
        return files;
    }

    public String parseJavaToString(String path) {
        String javaFileString = "";
        String fileName = path;

        try (Stream<String> stream = Files.lines(Paths.get(fileName), Charset.forName("x-MacCentralEurope"))) {
            for (String s : stream.toArray(String[]::new)) {
                javaFileString += (s + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(LocalDirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return javaFileString;
    }

    public boolean checkDirectory(String path, String rootPath) {
        path = path.replace(":", "");
        File dir = new File(rootPath + "/" + path.substring(0, path.length() - 1) + "_metrics.json");
        return dir.isFile();
    }

    @Override
    public void readMetrics(File localRoot, String directory, ProgramBuilder builder) throws IOException {
            Program program = builder.buildProgram(localRoot.getAbsolutePath() + "/", directory, this);
            String dirs[] = directory.split("/");
            String absolutePath = localRoot + "/" + dirs[0] + "/" + dirs[1] + "/" + dirs[2];
            String commit = dirs[3];
            generateJsonFiles(program, absolutePath, commit);
            FileUtils.deleteDirectory(new File(localRoot + "/" + directory));
    }

}
