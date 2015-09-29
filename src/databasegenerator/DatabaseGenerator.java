/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasegenerator;

import org.juanssl.transformo.app.CodeConfiguration;
import org.juanssl.transformo.app.DataConfiguration;
import org.juanssl.transformo.app.Transformo;
import org.juanssl.transformo.data.generator.DataFormat;

/**
 *
 * @author Juan
 */
public class DatabaseGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //GenerateMeta();
        GenerateData();
    }
    
    public static void GenerateData(){
        Transformo transformo = new Transformo();
        DataConfiguration config = transformo.GetCurrentDataConfiguration();
        config.DatabasePath = "Database.xlsx";
        config.TargetDataFile = "data.json";
        config.TargetDataFormat = DataFormat.JSON;
        
        transformo.GenerateData();
    }
    
    public static void GenerateMeta(){
        Transformo transformo = new Transformo();
        CodeConfiguration tableConfig = transformo.GetCurrentConfigurationByTable();
        tableConfig.DatabasePath = "Database.xlsx";
        tableConfig.TargetFolder = "Generated/Models/";
        tableConfig.TargetNameFormat = "$table_name:class_case$.cs";
        tableConfig.TemplateFolderPath = "Templates/CSharp-Models/";
        tableConfig.TemplateFile = "template.txt";
        transformo.GenerateCodeByTables();
        
        CodeConfiguration fieldConfig = transformo.GetCurrentConfigurationByField();
        fieldConfig.DatabasePath = "Database.xlsx";
        fieldConfig.TargetFolder = "Generated/Interfaces/";
        fieldConfig.TargetNameFormat = "I$field_name:class_case$.cs";
        fieldConfig.TemplateFolderPath = "Templates/CSharp-Interfaces/";
        fieldConfig.TemplateFile = "template.txt";
        transformo.GenerateCodeByFields();
    }
}
