/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juanssl.transformo.template;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class TemplateReader {
    
    private static final Logger logger = Logger.getLogger("TemplateReader");
    private String _basePath;
    private String _path;
    
    /**
     * Creates a new instance of this TemplateReader
     * The Base path and the Template path are set to null.
     * Use SetPath and SetBasePath to properly configure.
     */
    public TemplateReader(){
        _path = "";
        _basePath = "";
    }
    
    /**
     * Creates a new instance of this TemplateReader with the given Template File path.
     * @param path 
     */
    public TemplateReader(String path){
        _path = path;
        _basePath = "";
    }
    
    /**
     * Creates a new instance of this TemplateReader with the given Template File path
     * and the given Template Base Path.
     * @param path
     * @param basePath 
     */
    public TemplateReader(String path, String basePath){
        _path = path;
        _basePath = basePath;
    }
    
    /**
     * Set the path for the template file.
     * @param path 
     */
    public void SetPath(String path){
         _path = path;
    }
    
    /**
     * Set the Base Path for this Reader.
     * @param path 
     */
    public void SetBasePath(String path){
        _basePath = path;
    }
    
    /**
     * Reads the given template file.
     * @param path of the template
     * @return Template String.
     */
    public String ReadTemplate(String path){
         _path = path;
         return ReadTemplate();
    }
    
    /**
     * Reads the template file previously set.
     * @return Template String
     */
    public String ReadTemplate(){
        if(_path.isEmpty()){
            logger.severe("Empty Path");
            return "";
        }

        String fullPath = _basePath+_path;
        File file = new File(fullPath);
        if(!file.exists()){
            logger.severe(String.format("File does not exist: %s", fullPath));
            return "";
        }
        
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fullPath), Charset.defaultCharset());
        } catch (IOException ex) {
            logger.severe(String.format("Error Reading File: %s", ex.toString()));
        }
        
        if(lines == null)
            return "";
        
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<lines.size(); ++i){
            builder.append(lines.get(i));
            builder.append("\n");
        }
        
        return builder.toString();
    }
}
