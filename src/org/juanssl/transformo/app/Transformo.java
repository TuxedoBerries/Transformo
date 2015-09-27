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

import java.util.ArrayList;
import java.util.List;
import org.juanssl.transformo.data.FieldMeta;
import org.juanssl.transformo.data.TableMeta;
import org.juanssl.transformo.excel.XLSXTableMetaReader;
import org.juanssl.transformo.generator.EntityGenerator;
import org.juanssl.transformo.generator.FieldGenerator;
import org.juanssl.transformo.template.TemplateReader;
import org.juanssl.transformo.template.TemplateWriter;

/**
 *
 * @author juanssl
 */
public class Transformo {
    
    private final Configuration _configByTable;
    private final Configuration _configByField;
    private final TemplateWriter _writer;
    
    /**
     * Initialize this instance
     */
    public Transformo(){
        _configByTable = new Configuration();
        _configByField = new Configuration();
        _writer = new TemplateWriter();
    }
    
    /**
     * Get the current configuration for Tables.
     * This configuration works for generating code using the table information
     * @return Table Configuration
     */
    public Configuration GetCurrentConfigurationByTable(){
        return _configByTable;
    }
    
    /**
     * Get the current configuration for fields.
     * This configuration works for generating code using the
     * unique information of each field in all the tables.
     * @return 
     */
    public Configuration GetCurrentConfigurationByField(){
        return _configByField;
    }
    
    /**
     * Generate code using the table information.
     * This uses the internal configuration.
     */
    public void GenerateCodeByTables(){
        GenerateCodeByTables(_configByTable);
    }
    
    /**
     * Generate code using the table information.
     * This uses the given configuration.
     * @param config 
     */
    public void GenerateCodeByTables(Configuration config){
        if(config == null)
            return;
        
        if(!config.Validate())
            return;
        
        // Get the Table Data
        List<TableMeta> tables = GetTables(config);
        
        // Configure Template Reader
        TemplateReader templateReader = new TemplateReader(config.TemplateFile, config.TemplateFolderPath);
        String template = templateReader.ReadTemplate();
        
        // Iterate
        for(int i=0; i<tables.size(); ++i){
            TableMeta tmeta = tables.get(i);
            Logger.Info("Generating Code for: %s", tmeta.TableName);
            EntityGenerator generator = new EntityGenerator(tmeta);
            generator.SetTemplateReader(templateReader);
            String fileData = generator.Generate(template);
            String fileName = generator.Generate(config.GetTargetFullPath());
            _writer.WriteFile(fileName, fileData);
        }
    }
    
    /**
     * Generate code using the fields information.
     * This uses the internal configuration.
     */
    public void GenerateCodeByFields(){
        GenerateCodeByFields(_configByField);
    }
    
    /**
     * Generate code using the table information.
     * This uses the given configuration.
     * @param config 
     */
    public void GenerateCodeByFields(Configuration config){
        if(config == null)
            return;
        
        if(!config.Validate())
            return;
        
        // Get the Table Data
        List<TableMeta> tables = GetTables(config);
        
        // Configure Template Reader
        TemplateReader templateReader = new TemplateReader(config.TemplateFile, config.TemplateFolderPath);
        String template = templateReader.ReadTemplate();
        
        // Gather all different fields
        List<FieldMeta> fields = new ArrayList<>();
        for(int i=0; i<tables.size(); ++i){
            TableMeta tmeta = tables.get(i);
            for(int fieldIndex=0; fieldIndex<tmeta.Fields.size(); ++fieldIndex){
                FieldMeta fmeta = tmeta.Fields.get(fieldIndex);
                if(!ContainsField(fields, fmeta)){
                    fields.add(fmeta);
                }
            }
        }
        
        // Iterate
        for(int i=0; i<fields.size(); ++i){
            FieldMeta fmeta = fields.get(i);
            Logger.Info("Generating Code for: %s", fmeta.FieldName);
            FieldGenerator igenerator = new FieldGenerator(fmeta);
            igenerator.SetTemplateReader(templateReader);
            String fileData = igenerator.Generate(template);
            String fileName = igenerator.Generate(config.GetTargetFullPath());
            _writer.WriteFile(fileName, fileData);
        }
    }
    
    /**
     * Checks if the List of fields contains the given one, not by reference, by value.
     * @param fields
     * @param meta
     * @return 
     */
    private boolean ContainsField(List<FieldMeta> fields, FieldMeta meta){
        for(int i=0; i<fields.size(); ++i){
            FieldMeta field = fields.get(i);
            if(FieldMeta.Equals(field, meta))
                return true;
        }
        
        return false;
    }
    
    /**
     * Generates the Table information.
     * @param config
     * @return Table Information
     */
    private List<TableMeta> GetTables(Configuration config){
        XLSXTableMetaReader reader = new XLSXTableMetaReader(config.DatabasePath);
        reader.Read();
        return reader.GetTables();
    }
}
