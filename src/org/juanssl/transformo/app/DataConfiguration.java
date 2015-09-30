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
package org.juanssl.transformo.app;

import java.io.File;
import org.juanssl.transformo.data.generator.DataFormat;

/**
 *
 * @author juanssl
 */
public class DataConfiguration {
    
    public String DatabasePath;
    // Data
    public DataFormat TargetDataFormat;
    public String TargetDataFile;
    public String TargetDataFolder;
    
    /**
     * Initialize this instance
     */
    public DataConfiguration(){
        TargetDataFile = "";
        TargetDataFolder = "";
        TargetDataFormat = DataFormat.NONE;
    }
    
    /**
     * Validate this configuration
     * @return 
     */
    public boolean Validate(){
        if(!CheckDatabasePath()){
            Logger.Error("Database Path not valid: %s", DatabasePath);
            return false;
        }
        
        if(!CheckTarget()){
            Logger.Error("Target Path not valid: ", GetTargetDataFullPath());
            return false;
        }
        
        return true;
    }
    
    public boolean CheckDatabasePath(){
        if(DatabasePath == null){
            Logger.Warning("Database Path is null");
            return false;
        }
        
        if(DatabasePath.isEmpty()){
            Logger.Warning("Database Path is Empty");
            return false;
        }
        
        if(!DatabasePath.endsWith(".xlsx")){
            Logger.Warning("Database is not an XLSX file");
            return false;
        }
        
        File file = new File(DatabasePath);
        if(!file.exists()){
            Logger.Warning("Database file does not exist: ", file.getAbsolutePath());
            return false;
        }
        
        return true;
    }
    
    public boolean CheckTarget(){
        if(TargetDataFile == null){
            Logger.Warning("Target Data File Name is null");
            return false;
        }
        
        if(TargetDataFile.isEmpty()){
            Logger.Warning("Target Data File Name is empty");
            return false;
        }
        
        if(TargetDataFolder != null && !TargetDataFolder.isEmpty()){
            if(!TargetDataFolder.endsWith("/")){
                TargetDataFolder = TargetDataFolder.concat("/");
            }
        }
        
        return true;
    }
    
    /**
     * Gets the Full path of the Target Data
     * @return 
     */
    public String GetTargetDataFullPath() {
        return TargetDataFolder.concat(TargetDataFile);
    }
}
