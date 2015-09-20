/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasegenerator;

import com.company.codegenerator.data.FieldMeta;
import com.company.codegenerator.data.RowData;
import com.company.codegenerator.data.TableMeta;
import com.company.codegenerator.excel.XLSXDataReader;
import com.company.codegenerator.excel.XLSXTableMetaReader;
import com.company.codegenerator.generator.EntityGenerator;
import com.company.codegenerator.generator.FieldGenerator;
import com.company.codegenerator.template.TemplateReader;
import java.util.List;
import java.util.logging.Logger;

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
        //GenerateTemplates();
        GenerateData();
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
    
    public static void GenerateTemplates(){
        XLSXTableMetaReader reader = new XLSXTableMetaReader(BASE_PATH);
        reader.Read();
        List<TableMeta> tables = reader.GetTables();
        
        String template = GetTemplate("template.txt");
        String interfaceTemplate = GetTemplate("interface.txt");
        for(int i=0; i<tables.size(); ++i){
            logger.info(tables.get(i).toString());
            TableMeta tmeta = tables.get(i);
            EntityGenerator generator = new EntityGenerator(tmeta);
            generator.Generate(template);
            for(FieldMeta fmeta : tmeta.Fields){
                FieldGenerator igenerator = new FieldGenerator(fmeta);
                igenerator.Generate(interfaceTemplate);
            }
        }
    }
    
    public static String GetTemplate(String path){
        TemplateReader templateReader = new TemplateReader(path);
        return templateReader.ReadTemplate();
    }
}
