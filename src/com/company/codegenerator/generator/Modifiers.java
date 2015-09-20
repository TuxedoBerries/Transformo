/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.generator;

/**
 *
 * @author Juan
 */
public class Modifiers {
    
    private static final String CAPITAL_CASE = ":capital_case";
    private static final String SENTENCE_CASE = ":sentence_case";
    private static final String LOWER_CASE = ":lower_case";
    private static final String UPPER_CASE = ":upper_case";
    private static final String CAMEL_CASE = ":camel_case";
    private static final String SNEAK_CASE = ":sneak_case";
    private static final String CLASS_CASE = ":class_case";
    private static final String UNDERSCORE_SPACE = ":underscore_space";
    private static final String CLEAN_COMMA = ":clean";
    
    public static String Apply(String src, int current, int total){
        String returnData = src;
        
        if(returnData.contains(CLEAN_COMMA)){
            if(current+1 < total){
                returnData = returnData.replace(CLEAN_COMMA, "");
            }else{
                returnData = "";
            }
            return returnData;
        }
        
        returnData = Apply(returnData);
        return returnData;
    }
    
    public static String Apply(String src){
        String returnData = src;
        
        if(returnData.contains(CAPITAL_CASE)){
            returnData = returnData.replace(CAPITAL_CASE, "");
            returnData = ToCapitalCase(returnData);
            return returnData;
        }
        
        if(returnData.contains(SENTENCE_CASE)){
            returnData = returnData.replace(SENTENCE_CASE, "");
            returnData = ToSentenceCase(returnData);
            return returnData;
        }
        
        if(returnData.contains(LOWER_CASE)){
            returnData = returnData.replace(LOWER_CASE, "");
            returnData = returnData.toLowerCase();
            return returnData;
        }
        
        if(returnData.contains(UPPER_CASE)){
            returnData = returnData.replace(UPPER_CASE, "");
            returnData = returnData.toUpperCase();
            return returnData;
        }
        
        if(returnData.contains(CAMEL_CASE)){
            returnData = returnData.replace(CAMEL_CASE, "");
            returnData = ToCamelCase(returnData);
            return returnData;
        }
        
        if(returnData.contains(SNEAK_CASE)){
            returnData = returnData.replace(SNEAK_CASE, "");
            returnData = ToSneakCase(returnData);
            return returnData;
        }
        
        if(returnData.contains(CLASS_CASE)){
            returnData = returnData.replace(CLASS_CASE, "");
            returnData = ToClassCase(returnData);
            return returnData;
        }
        
        if(returnData.contains(UNDERSCORE_SPACE)){
            returnData = returnData.replace(UNDERSCORE_SPACE, "");
            returnData = ReplaceUnderscore(returnData);
            return returnData;
        }
        
        return returnData;
    }
    
    public static String ReplaceUnderscore(String data){
        String returnData = data;
        returnData = returnData.replace("_", " ");
        return returnData;
    }
    
    public static String ToCapitalCase(String data){
        String result = data;
        boolean containSpace = data.contains(" ");
        boolean containUnderscore = data.contains("_");
        if(containSpace){
            result = ToCapitalCase(data, " ");
        }
        if(containUnderscore){
            result = ToCapitalCase(result, "_");
        }
        if(!containSpace && !containUnderscore){
            result = ToSingleCapital(result);
        }
        return result;
    }
    
    public static String ToCapitalCase(String data, String separator){
        String[] split = data.split(separator);
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<split.length; ++i){
            String word = split[i].toLowerCase();
            builder.append(ToSingleCapital(word));
            
            if(i+1 < split.length)
                builder.append(separator);
        }
        return builder.toString();
    }
    
    public static String ToSentenceCase(String data){
        return ToSingleCapital(data);
    }
    
    public static String ToSneakCase(String data){
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<data.length(); ++i){
            char c = data.charAt(i);
            if(i == 0){
                builder.append( Character.toLowerCase(c) );
            }else{
                if(Character.isUpperCase(c)){
                    builder.append( "_" );
                }
                if(Character.isWhitespace(c)){
                    builder.append( "_" );
                    continue;
                }
                builder.append( Character.toLowerCase(c) );
            }
        }
        String prefinal = builder.toString();
        prefinal = prefinal.replace("__", "_");
        return prefinal;
    }
    
    public static String ToCamelCase(String data){
        String result = data;
        boolean containSpace = data.contains(" ");
        boolean containUnderscore = data.contains("_");
        if(containSpace){
            result = ToCamelCase(result, " ");
        }
        if(containUnderscore){
            result = ToCamelCase(result, "_");
        }
        return result;
    }
    
    public static String ToCamelCase(String data, String separator){
        String[] split = data.split(separator);
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<split.length; ++i){
            String word = split[i].toLowerCase();
            if(i == 0){
                builder.append(word);
            }else{
                builder.append( ToSingleCapital(word) );
            }
        }
        
        return builder.toString();
    }
    
    public static String ToClassCase(String data){
        String result = data;
        boolean containSpace = data.contains(" ");
        boolean containUnderscore = data.contains("_");
        if(containSpace){
            result = ToClassCase(data, " ");
        }
        if(containUnderscore){
            result = ToClassCase(result, "_");
        }
        if(!containSpace && !containUnderscore){
            result = ToSingleCapital(data);
        }
        return result;
    }
    
    public static String ToClassCase(String data, String separator){
        String[] split = data.split(separator);
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<split.length; ++i){
            String word = split[i].toLowerCase();
            builder.append( ToSingleCapital(word) );
        }
        return builder.toString();
    }
    
    public static String ToSingleCapital(String word){
        StringBuilder builder = new StringBuilder();
        builder.append(word.substring(0, 1).toUpperCase());
        builder.append(word.substring(1).toLowerCase());
        
        return builder.toString();
    }
}
