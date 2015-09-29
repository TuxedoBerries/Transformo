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
import org.juanssl.transformo.code.generator.EntityGenerator;
import org.juanssl.transformo.code.generator.FieldGenerator;
import org.juanssl.transformo.code.template.TemplateReader;
import org.juanssl.transformo.code.template.TemplateWriter;
import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.generator.BaseGenerator;
import org.juanssl.transformo.data.generator.JSONGenerator;
import org.juanssl.transformo.data.generator.ShortJSONGenerator;
import org.juanssl.transformo.excel.XLSXDataReader;

/**
 *
 * @author juanssl
 */
public class Transformo {
    
    private final CodeConfiguration _configByTable;
    private final CodeConfiguration _configByField;
    private final DataConfiguration _dataConfig;
    private final TemplateWriter _writer;
    
    /**
     * Initialize this instance
     */
    public Transformo(){
        _configByTable = new CodeConfiguration();
        _configByField = new CodeConfiguration();
        _dataConfig = new DataConfiguration();
        _writer = new TemplateWriter();
    }
    
    /**
     * Get the current configuration for Tables.
     * This configuration works for generating code using the table information
     * @return Table Configuration
     */
    public CodeConfiguration GetCurrentConfigurationByTable(){
        return _configByTable;
    }
    
    /**
     * Get the current configuration for fields.
     * This configuration works for generating code using the
     * unique information of each field in all the tables.
     * @return 
     */
    public CodeConfiguration GetCurrentConfigurationByField(){
        return _configByField;
    }
    
    /**
     * Get the current data configuration.
     * This configuration holds the parameters to export data.
     * @return 
     */
    public DataConfiguration GetCurrentDataConfiguration(){
        return _dataConfig;
    }
    
    /**
     * Generate or export the given data source using the internal configuration.
     */
    public void GenerateData(){
        GenerateData(_dataConfig);
    }
    
    /**
     * Generate or export the given data source using the the given configuration.
     * @param config 
     */
    public void GenerateData(DataConfiguration config){
        if(config == null)
            return;
        
        if(!config.Validate())
            return;
        
        // Create Generator
        BaseGenerator dataGenerator = null;
        switch(config.TargetDataFormat){
            case JSON:
                dataGenerator = new JSONGenerator();
                break;
            case SHORT_JSON:
                dataGenerator = new ShortJSONGenerator();
                break;
        }
        if(dataGenerator == null)
            return;
        
        // Generate
        XLSXTableMetaReader reader = new XLSXTableMetaReader(config.DatabasePath);
        reader.Read();
        List<TableMeta> tables = reader.GetTables();
        
        XLSXDataReader dataReader = new XLSXDataReader(config.DatabasePath);
        for(int i=0; i<tables.size(); ++i){
            TableMeta tmeta = tables.get(i);
            dataReader.SetTable(tmeta);
            dataReader.Read();
            
            List<RowData> list = dataReader.GetData();
            dataGenerator.AddData(tmeta, list);
        }
        
        dataGenerator.Generate();
        _writer.WriteFile(config.GetTargetDataFullPath(), dataGenerator.GetData());
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
    public void GenerateCodeByTables(CodeConfiguration config){
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
    public void GenerateCodeByFields(CodeConfiguration config){
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
    private List<TableMeta> GetTables(CodeConfiguration config){
        XLSXTableMetaReader reader = new XLSXTableMetaReader(config.DatabasePath);
        reader.Read();
        return reader.GetTables();
    }
}
