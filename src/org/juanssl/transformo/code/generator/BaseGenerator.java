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
package org.juanssl.transformo.code.generator;

import org.juanssl.transformo.code.template.TemplateReader;
import org.juanssl.transformo.data.FieldMeta;
import org.juanssl.transformo.data.FieldTypeTranslations;
import org.juanssl.transformo.data.TableMeta;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Juan
 */
public abstract class BaseGenerator {
    
    // Date
    private static final String DATE_NOW = "@date";
    // Table
    private static final String TABLE_NAME = "table_name";
    // Fields
    private static final String FIELDS = "fields:";
    private static final String FIELD_NAME = "field_name";
    private static final String FIELD_SHORT_NAME = "field_short_name";
    private static final String FIELD_TYPE = "field_type";
    // File
    private static final String FILE_TYPE = "file:";
    private static final String FILE_FIELD_TYPE = "[field_type]";
    // Template Reader
    private TemplateReader _mainReader;
    
    /**
     * Generate the specific filled template.
     * @param template
     * @return 
     */
    public abstract String Generate(String template);
    
    /**
     * Sets the Template Reader for this Generator.
     * If not set, one will be created.
     * @param reader 
     */
    public void SetTemplateReader(TemplateReader reader){
        _mainReader = reader;
    }
    
    /**
     * Generate a filled file given the specific template and table meta
     * @param template
     * @param tmeta
     * @return 
     */
    protected String GenerateFile(String template, TableMeta tmeta){
        String[] words = template.split("\\$*\\$");
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            String replaced = DirectReplace(words[i], tmeta);
            builder.append(GenerateFields(replaced, tmeta));
        }
        return builder.toString();
    }
    
    /**
     * Generates Fields if any given the specific template and table meta.
     * @param data
     * @param tmeta
     * @return 
     */
    protected String GenerateFields(String data, TableMeta tmeta){
        String returnData = data;
        if(returnData.contains(FIELDS)){
            returnData = returnData.replace(FIELDS, "");
            String subset = "";
            for(int i=0; i<tmeta.Fields.size(); ++i){
                subset += ReplaceFile(returnData, tmeta, tmeta.Fields.get(i), i, tmeta.Fields.size());
            }
            returnData = subset; 
        }
        return returnData;
    }
    
    protected String GenerateData(String template, TableMeta tmeta){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            String replaced = DirectReplace(words[i], tmeta);
            replaced = ReplaceFile(replaced, tmeta);
            builder.append(GenerateFields(replaced, tmeta));
        }
        return builder.toString();
    }
    
    protected String GenerateData(String template, FieldMeta fmeta, int current, int total){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            String replaced = DirectReplace(words[i], fmeta, current, total);
            replaced = ReplaceFile(replaced, fmeta, current, total);
            builder.append(replaced);
        }
        return builder.toString();
    }
    
    protected String GenerateData(String template, TableMeta tmeta, FieldMeta fmeta, int current, int total){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            builder.append(DirectReplace(words[i], tmeta, fmeta, current, total));
        }
        return builder.toString();
    }
    
    protected String ReplaceFile(String data, TableMeta tmeta){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            
            TemplateReader reader = GetTemplateReader();
            returnData = reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData, tmeta);
        }
        
        return returnData;
    }
    
    protected String ReplaceFile(String data, FieldMeta fmeta, int current, int total){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            returnData = returnData.replace(FILE_FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
            
            TemplateReader reader = GetTemplateReader();
            returnData = reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData, fmeta, current, total);
        }
        
        return returnData;
    }
    
    protected String ReplaceFile(String data, TableMeta tmeta, FieldMeta fmeta, int current, int total){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            returnData = returnData.replace(FILE_FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
            
            TemplateReader reader = GetTemplateReader();
            returnData = reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData, tmeta, fmeta, current, total);
        }
        
        return returnData;
    }
    
    protected String DirectReplace(String data, TableMeta meta){
        String returnData = data;
        returnData = returnData.replace(TABLE_NAME, meta.TableName);
        returnData = returnData.replace(DATE_NOW, GetDate());
        returnData = Modifiers.Apply(returnData);
        
        return returnData;
    }
    
    protected String DirectReplace(String data, FieldMeta fmeta){
        String returnData = data;
        returnData = returnData.replace(DATE_NOW, GetDate());
        returnData = returnData.replace(FIELD_NAME, fmeta.FieldName);
        returnData = returnData.replace(FIELD_SHORT_NAME, fmeta.FieldShortName);
        returnData = returnData.replace(FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
        returnData = Modifiers.Apply(returnData);
        
        return returnData;
    }
    
    protected String DirectReplace(String data, FieldMeta fmeta, int current, int total){
        String returnData = data;
        returnData = returnData.replace(DATE_NOW, GetDate());
        returnData = returnData.replace(FIELD_NAME, fmeta.FieldName);
        returnData = returnData.replace(FIELD_SHORT_NAME, fmeta.FieldShortName);
        returnData = returnData.replace(FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
        returnData = Modifiers.Apply(returnData);
        returnData = Modifiers.Apply(returnData, current, total);
        
        return returnData;
    }
    
    protected String DirectReplace(String data, TableMeta meta, FieldMeta fmeta, int current, int total){
        String returnData = data;
        returnData = returnData.replace(TABLE_NAME, meta.TableName);
        returnData = returnData.replace(DATE_NOW, GetDate());
        returnData = returnData.replace(FIELD_NAME, fmeta.FieldName);
        returnData = returnData.replace(FIELD_SHORT_NAME, fmeta.FieldShortName);
        returnData = returnData.replace(FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
        returnData = Modifiers.Apply(returnData);
        returnData = Modifiers.Apply(returnData, current, total);
        
        return returnData;
    }
    
    protected String GetDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    
    /**
     * Gets a Template Reader ready to read any template given.
     * @return 
     */
    private TemplateReader GetTemplateReader(){
        if(_mainReader == null)
            _mainReader = new TemplateReader();
        
        return _mainReader;
    }
}
