/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasegenerator;

import org.juanssl.transformo.data.FieldMeta;
import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.TableMeta;
import org.juanssl.transformo.excel.XLSXDataReader;
import org.juanssl.transformo.excel.XLSXTableMetaReader;
import org.juanssl.transformo.generator.EntityGenerator;
import org.juanssl.transformo.generator.FieldGenerator;
import org.juanssl.transformo.template.TemplateReader;
import java.util.List;
import java.util.logging.Logger;
import org.juanssl.transformo.template.TemplateWriter;

/**
 *
 * @author Juan
 */
public class DatabaseGenerator {

    private static final Logger logger = Logger.getLogger("DatabaseGenerator");
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
                logger.info(rdata.toString());
            }
        }
    }
    
    public static void GenerateMeta(){
        XLSXTableMetaReader reader = new XLSXTableMetaReader(BASE_PATH);
        reader.Read();
        List<TableMeta> tables = reader.GetTables();
        
        GenerateModelTemplates(tables);
        GenerateInterfaceTemplates(tables);
    }
    
    public static void GenerateModelTemplates(List<TableMeta> tables){
        TemplateReader templateReader = new TemplateReader("template.txt", "Templates/C#-Models/");
        String template = templateReader.ReadTemplate();
        for(int i=0; i<tables.size(); ++i){
            TableMeta tmeta = tables.get(i);
            EntityGenerator generator = new EntityGenerator(tmeta);
            generator.SetTemplateReader(templateReader);
            String fileData = generator.Generate(template);
            String fileName = generator.Generate("Generated/Models/$table_name:class_case$.cs");
            WriteFilledTemplate(fileName, fileData);
        }
    }
    
    public static void GenerateInterfaceTemplates(List<TableMeta> tables){
        TemplateReader templateReader = new TemplateReader("template.txt", "Templates/C#-Interfaces/");
        String interfaceTemplate = templateReader.ReadTemplate();
        for(int i=0; i<tables.size(); ++i){
            TableMeta tmeta = tables.get(i);
            for(FieldMeta fmeta : tmeta.Fields){
                FieldGenerator igenerator = new FieldGenerator(fmeta);
                igenerator.SetTemplateReader(templateReader);
                String fileData = igenerator.Generate(interfaceTemplate);
                String fileName = igenerator.Generate("Generated/Interfaces/I$field_name:class_case$.cs");
                WriteFilledTemplate(fileName, fileData);
            }
        }
    }
    
    public static void WriteFilledTemplate(String path, String data){
        TemplateWriter templateWriter = new TemplateWriter();
        templateWriter.WriteFile(path, data);
    }
}
