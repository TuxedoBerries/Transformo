/*
 * Copyright (C) 2015 Juan Silva <juanssl@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.juanssl.transformo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.juanssl.transformo.app.Logger;

/**
 *
 * @author Juan
 */
public abstract class BaseXLSXReader {
    
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
            Logger.Error("Error Reading File: %s", ex.toString());
        }
        
        CloseWorkbook();
        CloseStream();
        CloseFile();
    }
    
    protected abstract void doRead();
    
    private boolean OpenFile(){
        _file = new File(_filePath);
        if(!_file.exists()){
            Logger.Error("File does not exist");
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
            Logger.Error("Error reading file: %s", ex.toString());
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
            Logger.Error("Error closing stream: %s", ex.toString());
        }
        _stream = null;
    }
    
    private boolean OpenWorkbook(){
        boolean retValue = true;
        try {
            _workBook = new XSSFWorkbook(_stream);
        } catch (IOException ex) {
            Logger.Error("Error reading Excel: %s", ex.toString());
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
            Logger.Error("Error closing Excel: %s", ex.toString());
        }
        _workBook = null;
    }
}
