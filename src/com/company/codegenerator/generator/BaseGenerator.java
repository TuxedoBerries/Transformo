/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.generator;

import com.company.codegenerator.template.TemplateReader;
import com.company.codegenerator.data.FieldMeta;
import com.company.codegenerator.data.FieldTypeTranslations;
import com.company.codegenerator.data.TableMeta;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public abstract class BaseGenerator {
    private static final Logger logger = Logger.getLogger("BaseGenerator");
    
    // Date
    private static final String DATE_NOW = "date";
    // Table
    private static final String TABLE_NAME = "table_name";
    // Fields
    private static final String FIELDS = "fields:";
    private static final String FIELD_NAME = "field_name";
    private static final String FIELD_SHORT_NAME = "field_short_name";
    private static final String FIELD_TYPE = "field_type";
    // File
    private static final String FILE_TYPE = "file:";
    
    /**
     * Generate the specific filled template.
     * @param template
     * @return 
     */
    public abstract String Generate(String template);
    
    /**
     * Generate a filled file given the specific template and table meta
     * @param template
     * @param tmeta
     * @return 
     */
    protected String GenerateFile(String template, TableMeta tmeta){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
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
                subset += ReplaceFile(returnData, tmeta, tmeta.Fields.get(i));
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
    
    protected String GenerateData(String template, TableMeta tmeta, FieldMeta fmeta){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            builder.append(DirectReplace(words[i], tmeta, fmeta));
        }
        return builder.toString();
    }
    
    protected String ReplaceFile(String data, TableMeta tmeta){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            TemplateReader reader = new TemplateReader();
            returnData = reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData, tmeta);
        }
        
        return returnData;
    }
    
    protected String ReplaceFile(String data, TableMeta tmeta, FieldMeta fmeta){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            TemplateReader reader = new TemplateReader();
            returnData = reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData, tmeta, fmeta);
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
    
    protected String DirectReplace(String data, TableMeta meta, FieldMeta fmeta){
        String returnData = data;
        returnData = returnData.replace(TABLE_NAME, meta.TableName);
        returnData = returnData.replace(DATE_NOW, GetDate());
        returnData = returnData.replace(FIELD_NAME, fmeta.FieldName);
        returnData = returnData.replace(FIELD_SHORT_NAME, fmeta.FieldShortName);
        returnData = returnData.replace(FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
        returnData = Modifiers.Apply(returnData);
        
        return returnData;
    }
    
    protected String GetDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
