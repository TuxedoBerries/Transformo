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
package org.juanssl.transformo.app.frontend;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.juanssl.transformo.app.CodeConfiguration;
import org.juanssl.transformo.app.DataConfiguration;
import org.juanssl.transformo.app.Logger;
import org.juanssl.transformo.app.Transformo;
import org.juanssl.transformo.data.generator.DataFormat;

/**
 *
 * @author Juan Silva
 */
public class CommandLineLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // Build Options
        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        // Catch Any Errors
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException ex) {
            Logger.Error("%s", ex.getMessage());
            printHelp(options);
        }
        // Close if something goes wrong
        if(cmd == null)
            return;
        
        // Print Help if needed
        if(cmd.hasOption("help")){
            printHelp(options);
            return;
        }
        
        // Generation
        if(cmd.hasOption("data")){
            generateData(cmd);
            return;
        }
        if(cmd.hasOption("tables")){
            generateMetaTables(cmd);
            return;
        }
        if(cmd.hasOption("fields")){
            generateMetaFields(cmd);
        }
    }
    
    public static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("transformo", options, true);
    }
    
    public static Options getOptions(){
        Options options = new Options();
        options.addOption("help", "Print this message.");
        
        // Generation
        OptionGroup genGroup = new OptionGroup();
        Option tablesOption = new Option("tables", "Generate filled templates by using each table information.");
        Option fieldsOption = new Option("fields", "Generate filled templates by using each field information.");
        Option dataOption = new Option("data", "Generate data using a specific format.");
        genGroup.addOption(tablesOption);
        genGroup.addOption(fieldsOption);
        genGroup.addOption(dataOption);
        genGroup.setRequired(true);
        options.addOptionGroup(genGroup);
        
        // Database
        Option database = new Option("d", "Use the given Database. Just XLSX format Supported for now.");
        database.setArgs(1);
        database.setArgName("file");
        database.setRequired(true);
        options.addOption(database);
        
        // Target Folder
        Option targetFolder = new Option("tfolder", "Target folder where to save the result.");
        targetFolder.setArgs(1);
        targetFolder.setArgName("folder");
        options.addOption(targetFolder);
        
        // Target File
        Option targetFile = new Option("tfile", "Target file name expresion. For -tables or -fields can use $file_name$, $field_name$ and/or $field_type$ and any modifiers.");
        targetFile.setArgs(1);
        targetFile.setArgName("file");
        options.addOption(targetFile);
        
        // Target Format
        OptionGroup formatGroup = new OptionGroup();
        Option jsonOption = new Option("json", "[-data only] Generate data using JSON format.");
        Option shortJsonOption = new Option("sjson", "[-data only] Generate data using Short field name JSON format.");
        formatGroup.addOption(jsonOption);
        formatGroup.addOption(shortJsonOption);
        options.addOptionGroup(formatGroup);
        
        // Template Folder
        Option templateFolder = new Option("sfolder", "[-tables or -fields only] Template source folder. Where to locate all different templates to use.");
        templateFolder.setArgs(1);
        templateFolder.setArgName("folder");
        options.addOption(templateFolder);
        
        // Template File
        Option templateFile = new Option("sfile", "[-tables or -fields only] Template index source file. The point of origin for the templates.");
        templateFile.setArgs(1);
        templateFile.setArgName("file");
        options.addOption(templateFile);
        
        return options;
    }
    
    public static void generateData(CommandLine cmd){
        Transformo transformo = new Transformo();
        DataConfiguration config = transformo.GetCurrentDataConfiguration();
        
        // Database
        if(cmd.hasOption("d")){
            String database = cmd.getOptionValue("d");
            Logger.Info("Database: %s", database);
            config.DatabasePath = database;
        }else{
            Logger.Error("Missing database path");
            return;
        }
        
        // Target File
        if(cmd.hasOption("tfile")){
            String fileName = cmd.getOptionValue("tfile");
            Logger.Info("Output data file: %s", fileName);
            config.TargetDataFile = fileName;
        }else{ 
            Logger.Info("Setting default data output file to \"data.json\"");
            config.TargetDataFile = "data.json";
        }
        
        // Target Folder
        if(cmd.hasOption("tfolder")){
            String folder = cmd.getOptionValue("tfolder");
            Logger.Info("Output folder: %s", folder);
            config.TargetDataFolder = folder;
        }
        
        // Target Format
        if(!cmd.hasOption("json") && !cmd.hasOption("sjson")){
            config.TargetDataFormat = DataFormat.JSON;
            Logger.Info("Setting default output format JSON");
        }else{
            if(cmd.hasOption("json")){
                Logger.Info("Format set to JSON");
                config.TargetDataFormat = DataFormat.JSON;
            }
            if(cmd.hasOption("sjson")){
                Logger.Info("Format set to Short JSON");
                config.TargetDataFormat = DataFormat.SHORT_JSON;
            }
        }
        
        Logger.Info("------------------");
        transformo.GenerateData();
    }
    
    public static void generateMetaTables(CommandLine cmd){
        Transformo transformo = new Transformo();
        CodeConfiguration tableConfig = transformo.GetCurrentConfigurationByTable();
        
        // Database
        if(cmd.hasOption("d")){
            String database = cmd.getOptionValue("d");
            Logger.Info("Database: %s", database);
            tableConfig.DatabasePath = database;
        }else{
            Logger.Error("Missing database path");
            return;
        }
        
        // Target Folder
        if(cmd.hasOption("tfolder")){
            String folder = cmd.getOptionValue("tfolder");
            Logger.Info("Output folder: %s", folder);
            tableConfig.TargetFolder = folder;
        }
        
        // Target File
        if(cmd.hasOption("tfile")){
            String fileName = cmd.getOptionValue("tfile");
            Logger.Info("Output file: %s", fileName);
            tableConfig.TargetNameFormat = fileName;
        }else{ 
            Logger.Info("Setting default data output file to \"$table_name:class_case$\"");
            tableConfig.TargetNameFormat = "$table_name:class_case$";
        }
        
        // Template Folder
        if(cmd.hasOption("sfolder")){
            String folder = cmd.getOptionValue("sfolder");
            Logger.Info("Template source folder: %s", folder);
            tableConfig.TemplateFolderPath = folder;
        }
        
        // Template File
        if(cmd.hasOption("sfile")){
            String file = cmd.getOptionValue("sfile");
            Logger.Info("Template source file: %s", file);
            tableConfig.TemplateFile = file;
        }else{
            Logger.Info("Setting default template source file to \"template.txt\"");
            tableConfig.TemplateFile = "template.txt";
        }
        
        Logger.Info("------------------");
        transformo.GenerateCodeByTables();
    }
    
    public static void generateMetaFields(CommandLine cmd){
        Transformo transformo = new Transformo();
        CodeConfiguration fieldConfig = transformo.GetCurrentConfigurationByField();
        
        // Database
        if(cmd.hasOption("d")){
            String database = cmd.getOptionValue("d");
            Logger.Info("Database: %s", database);
            fieldConfig.DatabasePath = database;
        }else{
            Logger.Error("Missing database path");
            return;
        }
        
        // Target Folder
        if(cmd.hasOption("tfolder")){
            String folder = cmd.getOptionValue("tfolder");
            Logger.Info("Output folder: %s", folder);
            fieldConfig.TargetFolder = folder;
        }
        
        // Target File
        if(cmd.hasOption("tfile")){
            String fileName = cmd.getOptionValue("tfile");
            Logger.Info("Output file: %s", fileName);
            fieldConfig.TargetNameFormat = fileName;
        }else{ 
            Logger.Info("Setting default data output file to \"$field_name:class_case$\"");
            fieldConfig.TargetNameFormat = "$field_name:class_case$";
        }
        
        // Template Folder
        if(cmd.hasOption("sfolder")){
            String folder = cmd.getOptionValue("sfolder");
            Logger.Info("Template source folder: %s", folder);
            fieldConfig.TemplateFolderPath = folder;
        }
        
        // Template File
        if(cmd.hasOption("sfile")){
            String file = cmd.getOptionValue("sfile");
            Logger.Info("Template source file: %s", file);
            fieldConfig.TemplateFile = file;
        }else{
            Logger.Info("Setting default template source file to \"template.txt\"");
            fieldConfig.TemplateFile = "template.txt";
        }
        
        Logger.Info("------------------");
        transformo.GenerateCodeByFields();
    }
}
