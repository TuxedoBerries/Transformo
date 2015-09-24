/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juanssl.transformo.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class TemplateWriter {
    
    private static final Logger logger = Logger.getLogger("FileWriter");
    
    private File _file;
    private FileWriter _fileWriter;
    private BufferedWriter _bufferedWriter;
    
    
    public void WriteFile(String path, String content){
        if(!CreateFile(path))
            return;
        
        if(!OpenFileWriter())
            return;
        
        if(!OpenBufferedWriter()){
            CloseFileWriter();
            CloseFile();
            return;
        }
        
        WriteToFile(content);
        
        CloseBufferedWriter();
        CloseFileWriter();
        CloseFile();
    }
    
    private boolean CreateFile(String path){
        _file = new File(path);
        if(_file.exists()){
            logger.warning("File already exit. Overwriting file.");
        }else{
            try {
                _file.createNewFile();
            } catch (IOException ex) {
                logger.severe(String.format("Could not create file[%s]: %s", path, ex.toString()));
                return false;
            }
        }
        
        return true;
    }
    
    private void CloseFile(){
        _file = null;
    }
    
    private boolean OpenFileWriter(){
        try {
            _fileWriter = new FileWriter(_file.getAbsoluteFile());
        } catch (IOException ex) {
            logger.severe(String.format("Could not create File Writer [%s]: %s", _file.getPath(), ex.toString()));
            return false;
        }
        
        return true;
    }
    
    private void CloseFileWriter(){
        if(_fileWriter == null){
            return;
        }
        
        try {
            _fileWriter.close();
        } catch (IOException ex) {
            logger.severe(String.format("Could not close File Writer: %s", ex.toString()));
        }
        
        _fileWriter = null;
    }
    
    private boolean OpenBufferedWriter(){
        _bufferedWriter = new BufferedWriter(_fileWriter);
        return true;
    }
    
    private boolean WriteToFile(String content){
        try {
            _bufferedWriter.write(content);
        } catch (IOException ex) {
            logger.severe(String.format("Could not write data to file: %s", ex.toString()));
        }
        return true;
    }
    
    private void CloseBufferedWriter(){
        if(_bufferedWriter == null)
            return;
        
        try {
            _bufferedWriter.close();
        } catch (IOException ex) {
            logger.severe(String.format("Could not close Buffered Writer: %s", ex.toString()));
        }
        
        _bufferedWriter = null;
    }
}
