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
package org.juanssl.transformo.app;

import java.util.ArrayList;
import java.util.List;
import org.juanssl.transformo.data.FieldMeta;
import org.juanssl.transformo.data.TableMeta;
import org.juanssl.transformo.excel.XLSXTableMetaReader;
import org.juanssl.transformo.code.generator.EntityGenerator;
import org.juanssl.transformo.code.generator.FieldGenerator;
import org.juanssl.transformo.code.template.TemplateReader;
import org.juanssl.transformo.code.template.TemplateWriter;
import org.juanssl.transformo.data.RowData;
import org.juanssl.transformo.data.generator.BaseGenerator;
import org.juanssl.transformo.data.generator.JSONGenerator;
import org.juanssl.transformo.data.generator.ShortJSONGenerator;
import org.juanssl.transformo.excel.XLSXDataReader;

/**
 *
 * @author Juan Silva
 */
public class Transformo {

    private final CodeConfiguration _configByTable;
    private final CodeConfiguration _configByField;
    private final DataConfiguration _dataConfig;
    private final TemplateWriter _writer;

    /**
     * Initialize this instance
     */
    public Transformo() {
        _configByTable = new CodeConfiguration();
        _configByField = new CodeConfiguration();
        _dataConfig = new DataConfiguration();
        _writer = new TemplateWriter();
    }

    /**
     * Get the current configuration for Tables. This configuration works for
     * generating code using the table information
     *
     * @return Table Configuration
     */
    public CodeConfiguration getCurrentConfigurationByTable() {
        return _configByTable;
    }

    /**
     * Get the current configuration for fields. This configuration works for
     * generating code using the unique information of each field in all the
     * tables.
     *
     * @return
     */
    public CodeConfiguration getCurrentConfigurationByField() {
        return _configByField;
    }

    /**
     * Get the current data configuration. This configuration holds the
     * parameters to export data.
     *
     * @return
     */
    public DataConfiguration getCurrentDataConfiguration() {
        return _dataConfig;
    }

    /**
     * Generate or export the given data source using the internal
     * configuration.
     */
    public void generateData() {
        generateData(_dataConfig);
    }

    /**
     * Generate or export the given data source using the the given
     * configuration.
     *
     * @param config
     */
    public void generateData(DataConfiguration config) {
        if (config == null) {
            return;
        }

        if (!config.validate()) {
            return;
        }

        // Create Generator
        BaseGenerator dataGenerator = null;
        switch (config.TargetDataFormat) {
            case JSON:
                dataGenerator = new JSONGenerator();
                break;
            case SHORT_JSON:
                dataGenerator = new ShortJSONGenerator();
                break;
        }
        if (dataGenerator == null) {
            return;
        }

        // Generate
        XLSXTableMetaReader reader = new XLSXTableMetaReader(config.DatabasePath);
        reader.read();
        List<TableMeta> tables = reader.getTables();

        XLSXDataReader dataReader = new XLSXDataReader(config.DatabasePath);
        for (int i = 0; i < tables.size(); ++i) {
            TableMeta tmeta = tables.get(i);
            dataReader.setTable(tmeta);
            dataReader.read();

            List<RowData> list = dataReader.getData();
            dataGenerator.addData(tmeta, list);
        }

        dataGenerator.generate();
        _writer.writeFile(config.getTargetDataFullPath(), dataGenerator.getData());
    }

    /**
     * Generate code using the table information. This uses the internal
     * configuration.
     */
    public void generateCodeByTables() {
        generateCodeByTables(_configByTable);
    }

    /**
     * Generate code using the table information. This uses the given
     * configuration.
     *
     * @param config
     */
    public void generateCodeByTables(CodeConfiguration config) {
        if (config == null) {
            return;
        }

        if (!config.validate()) {
            return;
        }

        // Get the Table Data
        List<TableMeta> tables = getTables(config);

        // Configure Template Reader
        TemplateReader templateReader = new TemplateReader(config.TemplateFile, config.TemplateFolderPath);
        String template = templateReader.readTemplate();

        // Iterate
        for (int i = 0; i < tables.size(); ++i) {
            TableMeta tmeta = tables.get(i);
            Logger.Info("Generating Code for: %s", tmeta.TableName);
            EntityGenerator generator = new EntityGenerator(tmeta);
            generator.setTemplateReader(templateReader);
            String fileData = generator.generate(template);
            String fileName = generator.generate(config.getTargetFullPath());
            _writer.writeFile(fileName, fileData);
        }
    }

    /**
     * Generate code using the fields information. This uses the internal
     * configuration.
     */
    public void generateCodeByFields() {
        generateCodeByFields(_configByField);
    }

    /**
     * Generate code using the table information. This uses the given
     * configuration.
     *
     * @param config
     */
    public void generateCodeByFields(CodeConfiguration config) {
        if (config == null) {
            return;
        }

        if (!config.validate()) {
            return;
        }

        // Get the Table Data
        List<TableMeta> tables = getTables(config);

        // Configure Template Reader
        TemplateReader templateReader = new TemplateReader(config.TemplateFile, config.TemplateFolderPath);
        String template = templateReader.readTemplate();

        // Gather all different fields
        List<FieldMeta> fields = new ArrayList<>();
        for (int i = 0; i < tables.size(); ++i) {
            TableMeta tmeta = tables.get(i);
            for (int fieldIndex = 0; fieldIndex < tmeta.Fields.size(); ++fieldIndex) {
                FieldMeta fmeta = tmeta.Fields.get(fieldIndex);
                if (!containsField(fields, fmeta)) {
                    fields.add(fmeta);
                }
            }
        }

        // Iterate
        for (int i = 0; i < fields.size(); ++i) {
            FieldMeta fmeta = fields.get(i);
            Logger.Info("Generating Code for: %s", fmeta.FieldName);
            FieldGenerator igenerator = new FieldGenerator(fmeta);
            igenerator.setTemplateReader(templateReader);
            String fileData = igenerator.generate(template);
            String fileName = igenerator.generate(config.getTargetFullPath());
            _writer.writeFile(fileName, fileData);
        }
    }

    /**
     * Checks if the List of fields contains the given one, not by reference, by
     * value.
     *
     * @param fields
     * @param meta
     * @return
     */
    private boolean containsField(List<FieldMeta> fields, FieldMeta meta) {
        for (int i = 0; i < fields.size(); ++i) {
            FieldMeta field = fields.get(i);
            if (FieldMeta.equals(field, meta)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Generates the Table information.
     *
     * @param config
     * @return Table Information
     */
    private List<TableMeta> getTables(CodeConfiguration config) {
        XLSXTableMetaReader reader = new XLSXTableMetaReader(config.DatabasePath);
        reader.read();
        return reader.getTables();
    }
}
