/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.generator;

import com.company.codegenerator.data.FieldMeta;
import com.company.codegenerator.data.FieldTypeTranslations;
import com.company.codegenerator.data.TableMeta;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class CSharpGenerator {
    private static final Logger logger = Logger.getLogger("CSharpGenerator");
    
    // Replacement
    private static final String TABLE_NAME = "table_name";
    // Fields
    private static final String FIELDS = "fields:";
    private static final String FIELD_NAME = "field_name";
    private static final String FIELD_SHORT_NAME = "field_short_name";
    private static final String FIELD_TYPE = "field_type";
    // File
    private static final String FILE_TYPE = "file:";
    // Modifiers
    private static final String CAPITAL_CASE = ":capital_case";
    private static final String LOWER_CASE = ":lower_case";
    private static final String CAMEL_CASE = ":camel_case";
    private static final String SNEAK_CASE = ":sneak_case";
    
    
    private final TemplateReader _reader;
    private TableMeta _meta;
    private String _basePath;
    
    public CSharpGenerator(){
        _meta = null;
        _basePath = "template.txt";
        _reader = new TemplateReader(_basePath);
    }
    
    public CSharpGenerator(TableMeta meta){
        _meta = meta;
        _basePath = "template.txt";
        _reader = new TemplateReader(_basePath);
    }
    
    public CSharpGenerator(TableMeta meta, String basePath){
        _meta = meta;
        _basePath = basePath;
        _reader = new TemplateReader(_basePath);
    }
    
    public void SetPath(String basePath){
        _basePath = basePath;
    }
    
    public void SetMeta(TableMeta meta){
        _meta = meta;
    }
    
    public void GenerateFiles(){
        if(_meta == null){
            logger.severe("Null Table Meta");
            return;
        }
        
        String template = _reader.ReadTemplate();
        template = GenerateTable(template);
        
        logger.info(template);
    }
    
    private String GenerateTable(String template){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            String replaced = DirectReplace(words[i]);
            builder.append(GenerateFields(replaced));
        }
        return builder.toString();
    }
    
    private String GenerateData(String template){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            String replaced = DirectReplace(words[i]);
            replaced = ReplaceFile(replaced);
            builder.append(GenerateFields(replaced));
        }
        return builder.toString();
    }
    
    private String GenerateFields(String data){
        String returnData = data;
        if(returnData.contains(FIELDS)){
            returnData = returnData.replace(FIELDS, "");
            String subset = "";
            for(int i=0; i<_meta.Fields.size(); ++i){
                subset += ReplaceFile(returnData, _meta.Fields.get(i));
            }
            returnData = subset; 
        }
        return returnData;
    }
    
    private String GenerateData(String template, FieldMeta fmeta){
        String[] words = template.split("\\$*\\$");
        // Generate Fields
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<words.length; ++i){
            builder.append(DirectReplace(words[i], fmeta));
        }
        return builder.toString();
    }
    
    private String ReplaceFile(String data){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            returnData = _reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData);
        }
        
        return returnData;
    }
    
    private String ReplaceFile(String data, FieldMeta fmeta){
        String returnData = data;
        if(returnData.contains(FILE_TYPE)){
            returnData = returnData.replace(FILE_TYPE, "");
            returnData = _reader.ReadTemplate(returnData);
            returnData = GenerateData(returnData, fmeta);
        }
        
        return returnData;
    }
    
    private String DirectReplace(String data){
        String returnData = data;
        returnData = returnData.replace(TABLE_NAME, _meta.TableName);
        returnData = ApplyModifiers(returnData);
        
        return returnData;
    }
    
    private String DirectReplace(String data, FieldMeta fmeta){
        String returnData = data;
        returnData = returnData.replace(TABLE_NAME, _meta.TableName);
        returnData = returnData.replace(FIELD_NAME, fmeta.FieldName);
        returnData = returnData.replace(FIELD_SHORT_NAME, fmeta.FieldShortName);
        returnData = returnData.replace(FIELD_TYPE, FieldTypeTranslations.GetType(fmeta.DataType));
        returnData = ApplyModifiers(returnData);
        
        return returnData;
    }
    
    private String ApplyModifiers(String data){
        String returnData = data;
        
        if(returnData.contains(CAPITAL_CASE)){
            returnData = returnData.replace(CAPITAL_CASE, "");
            returnData = returnData.substring(0, 1).toUpperCase() + returnData.substring(1).toLowerCase();
        }
        
        if(returnData.contains(LOWER_CASE)){
            returnData = returnData.replace(LOWER_CASE, "");
            returnData = returnData.toLowerCase();
        }
        
        if(returnData.contains(CAMEL_CASE)){
            returnData = returnData.replace(CAMEL_CASE, "");
            returnData = ToCamelCase(returnData);
        }
        
        if(returnData.contains(SNEAK_CASE)){
            returnData = returnData.replace(SNEAK_CASE, "");
            returnData = ToSneakCase(returnData);
        }
        
        return returnData;
    }
    
    private String ToSneakCase(String data){
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<data.length(); ++i){
            char c = data.charAt(i);
            if(i == 0){
                builder.append( Character.toLowerCase(c) );
            }else{
                if(Character.isUpperCase(c)){
                    builder.append( "_" );
                }
                builder.append( Character.toLowerCase(c) );
            }
        }
        return builder.toString();
    }
    
    private String ToCamelCase(String data){
        String[] split = data.split("_");
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<split.length; ++i){
            String work = split[i].toLowerCase();
            if(i == 0){
                builder.append(work);
            }else{
                builder.append(work.substring(0, 1).toUpperCase().concat( work.substring(1).toLowerCase() ));
            }
        }
        
        return builder.toString();
    }
}
