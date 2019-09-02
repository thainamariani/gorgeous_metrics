/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readers;

import builder.ProgramBuilder;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Thainá Mariani
 * @param <T>
 */
public abstract class Reader<T> {

    public Reader() {
    }
    
    public abstract List<String> findDirectories(String rootPathName);

    public abstract Set<String> findSourcePaths(String pathName);

    public abstract Set<String> findSourcePaths(T path, Set<String> sourcePathsNames);

    public abstract HashMap<String, String> getJavaFiles(String pathName);

    public abstract HashMap<String, String> getJavaFiles(T path, HashMap<String, String> files);
    
    public abstract void readMetrics(File localRoot, String directory,ProgramBuilder builder) throws IOException;
    
}