/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator.data;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class FieldTypeTranslations {
    private static final Logger logger = Logger.getLogger("FieldTypeTranslations");
    private static final HashMap<String, FieldType> _cSharpTranslation = new HashMap<>();
    private static final HashMap<FieldType, String> _cSharpTranslationBack = new HashMap<>();
    
    static {
        // C# Translations from String
        _cSharpTranslation.put(AllowedFieldType.BYTE, FieldType.CSHARP_BYTE);
        _cSharpTranslation.put(AllowedFieldType.SBYTE, FieldType.CSHARP_SBYTE);
        _cSharpTranslation.put(AllowedFieldType.INT, FieldType.CSHARP_INT);
        _cSharpTranslation.put(AllowedFieldType.UINT, FieldType.CSHARP_UINT);
        _cSharpTranslation.put(AllowedFieldType.SHORT, FieldType.CSHARP_SHORT);
        _cSharpTranslation.put(AllowedFieldType.USHORT, FieldType.CSHARP_USHORT);
        _cSharpTranslation.put(AllowedFieldType.LONG, FieldType.CSHARP_LONG);
        _cSharpTranslation.put(AllowedFieldType.ULONG, FieldType.CSHARP_ULONG);
        _cSharpTranslation.put(AllowedFieldType.FLOAT, FieldType.CSHARP_FLOAT);
        _cSharpTranslation.put(AllowedFieldType.DOUBLE, FieldType.CSHARP_DOUBLE);
        _cSharpTranslation.put(AllowedFieldType.CHAR, FieldType.CSHARP_CHAR);
        _cSharpTranslation.put(AllowedFieldType.BOOL, FieldType.CSHARP_BOOL);
        _cSharpTranslation.put(AllowedFieldType.STRING, FieldType.CSHARP_STRING);
        _cSharpTranslation.put(AllowedFieldType.DECIMAL, FieldType.CSHARP_DECIMAL);
        
        // C# Translations from Type
        for(String name : _cSharpTranslation.keySet()){
            _cSharpTranslationBack.put(_cSharpTranslation.get(name), name);
        }
    }
    
    public static boolean isNumeric(FieldType type){
        switch(type){
            case CSHARP_BYTE:
            case CSHARP_SBYTE:
            case CSHARP_SHORT:
            case CSHARP_USHORT:
            case CSHARP_INT:
            case CSHARP_UINT:
            case CSHARP_LONG:
            case CSHARP_ULONG:
            case CSHARP_FLOAT:
            case CSHARP_DOUBLE:
            case CSHARP_DECIMAL:
                return true;
        }
        
        return false;
    }
    
    public static boolean isText(FieldType type){
        switch(type){
            case CSHARP_CHAR:
            case CSHARP_STRING:
                return true;
        }
        
        return false;
    }
    
    public static boolean isBoolean(FieldType type){
        switch(type){
            case CSHARP_BOOL:
                return true;
        }
        
        return false;
    }
    
    public static String GetType(FieldType type){
        if(_cSharpTranslationBack.containsKey(type))
            return _cSharpTranslationBack.get(type);
        
        return "";
    }
    
    public static boolean ExistType(String type){
        return _cSharpTranslation.containsKey(type);
    }
    
    public static FieldType GetType(String type){
        if(_cSharpTranslation.containsKey(type))
            return _cSharpTranslation.get(type);
        
        logger.warning(String.format("Returning NONE for type [%s]", type));
        return FieldType.NONE;
    }
}
