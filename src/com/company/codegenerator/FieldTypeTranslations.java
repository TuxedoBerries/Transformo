/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.codegenerator;

import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Juan
 */
public class FieldTypeTranslations {
    private static final Logger logger = LogManager.getLogger(FieldTypeTranslations.class);
    private static final HashMap<String, FieldType> _cSharpTranslation = new HashMap<>();
    
    static {
        // C# Translations
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
    }
    
    public static boolean ExistType(String type){
        return _cSharpTranslation.containsKey(type);
    }
    
    public static FieldType GetType(String type){
        if(_cSharpTranslation.containsKey(type))
            return _cSharpTranslation.get(type);
        
        logger.warn("Returning NONE for type [{}]", type);
        return FieldType.NONE;
    }
}
