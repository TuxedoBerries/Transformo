/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasegenerator;

import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.TableMeta;
import org.juanssl.transformo.excel.XLSXDataReader;
import org.juanssl.transformo.excel.XLSXTableMetaReader;
import java.util.List;
import org.juanssl.transformo.app.Configuration;
import org.juanssl.transformo.app.Logger;
import org.juanssl.transformo.app.Transformo;

/**
 *
 * @author Juan
 */
public class DatabaseGenerator {

    private static final String BASE_PATH = "Database.xlsx";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        GenerateMeta();
        //GenerateData();
    }
    
    public static void GenerateData(){
        XLSXTableMetaReader reader = new XLSXTableMetaReader(BASE_PATH);
        reader.Read();
        List<TableMeta> tables = reader.GetTables();
        
        for(int i=0; i<tables.size(); ++i){
            TableMeta tmeta = tables.get(i);
            XLSXDataReader dataReader = new XLSXDataReader(BASE_PATH, tmeta);
            dataReader.Read();
            
            List<RowData> list = dataReader.GetData();
            for(int r=0; r<list.size(); ++r){
                RowData rdata = list.get(r);
                Logger.Info(rdata.toString());
            }
        }
    }
    
    public static void GenerateMeta(){
        Transformo transformo = new Transformo();
        Configuration tableConfig = transformo.GetCurrentConfigurationByTable();
        tableConfig.TargetFolder = "Generated/Models/";
        tableConfig.TargetNameFormat = "$table_name:class_case$.cs";
        tableConfig.TemplateFolderPath = "Templates/CSharp-Models/";
        tableConfig.TemplateFile = "template.txt";
        transformo.GenerateCodeByTables();
        
        Configuration fieldConfig = transformo.GetCurrentConfigurationByField();
        fieldConfig.TargetFolder = "Generated/Interfaces/";
        fieldConfig.TargetNameFormat = "I$field_name:class_case$.cs";
        fieldConfig.TemplateFolderPath = "Templates/CSharp-Interfaces/";
        fieldConfig.TemplateFile = "template.txt";
        transformo.GenerateCodeByFields();
    }
}
