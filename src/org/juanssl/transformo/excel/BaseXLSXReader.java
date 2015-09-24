/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.juanssl.transformo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Juan
 */
public abstract class BaseXLSXReader {
    private static final Logger logger = Logger.getLogger("BaseXLSXReader");
    
    protected String _filePath;
    protected XSSFWorkbook _workBook;
    private File _file;
    private FileInputStream _stream;
    
    public void Read(){
        if(!OpenFile())
            return;
        
        if(!OpenStrean()){
            CloseFile();
            return;
        }
        
        if(!OpenWorkbook()){
            CloseFile();
            CloseStream();
            return;
        }
        
        try {
            doRead();
        } catch (Exception ex) {
            logger.severe(String.format("Error Reading File: %s", ex.toString()));
        }
        
        CloseWorkbook();
        CloseStream();
        CloseFile();
    }
    
    protected abstract void doRead();
    
    private boolean OpenFile(){
        _file = new File(_filePath);
        if(!_file.exists()){
            logger.severe("File does not exist");
            return false;
        }
        
        return true;
    }
    
    private void CloseFile(){
        _file = null;
    }
    
    private boolean OpenStrean(){
        boolean retValue = true;
        try {
            _stream = new FileInputStream(_file);
        } catch (Exception ex) {
            logger.severe(String.format("Error reading file: %s", ex.toString()));
            retValue = false;
        }
        
        return retValue;
    }
    
    private void CloseStream(){
        if(_stream == null)
            return;
        
        try {
            _stream.close();
        } catch (IOException ex) {
            logger.severe(String.format("Error closing stream: %s", ex.toString()));
        }
        _stream = null;
    }
    
    private boolean OpenWorkbook(){
        boolean retValue = true;
        try {
            _workBook = new XSSFWorkbook(_stream);
        } catch (IOException ex) {
            logger.severe(String.format("Error reading Excel: %s", ex.toString()));
            retValue = false;
        }
        return retValue;
    }
    
    private void CloseWorkbook(){
        if(_workBook == null)
            return;
        
        try {
            _workBook.close();
        } catch (IOException ex) {
            logger.severe(String.format("Error closing Excel: %s", ex.toString()));
        }
        _workBook = null;
    }
}
