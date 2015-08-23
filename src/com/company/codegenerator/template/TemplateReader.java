/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.template;

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
    private String _path;
    
    public TemplateReader(){
        _path = "";
    }
    
    public TemplateReader(String path){
        _path = path;
    }
    
    public void SetPath(String path){
         _path = path;
    }
    
    public String ReadTemplate(String path){
         _path = path;
         return ReadTemplate();
    }
    
    public String ReadTemplate(){
        if(_path.isEmpty()){
            logger.severe("Empty Path");
            return "";
        }

        File file = new File(_path);
        if(!file.exists()){
            logger.severe(String.format("File does not exist: %s", _path));
            return "";
        }
        
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(_path), Charset.defaultCharset());
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
