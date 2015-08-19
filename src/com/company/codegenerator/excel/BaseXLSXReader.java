/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Juan
 */
public abstract class BaseXLSXReader {
    private static final Logger logger = LogManager.getLogger(BaseXLSXReader.class);
    
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
            logger.error("Error Reading File");
        }
        
        CloseWorkbook();
        CloseStream();
        CloseFile();
    }
    
    protected abstract void doRead();
    
    private boolean OpenFile(){
        _file = new File(_filePath);
        if(!_file.exists()){
            logger.error("File does not exist");
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
            logger.error("Error reading file", ex);
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
            logger.error("Error closing stream", ex);
        }
        _stream = null;
    }
    
    private boolean OpenWorkbook(){
        boolean retValue = true;
        try {
            _workBook = new XSSFWorkbook(_stream);
        } catch (IOException ex) {
            logger.error("Error reading Excel", ex);
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
            logger.error("Error closing Excel", ex);
        }
        _workBook = null;
    }
}
