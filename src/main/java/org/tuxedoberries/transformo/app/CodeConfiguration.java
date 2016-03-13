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
package org.tuxedoberries.transformo.app;

import java.io.File;

/**
 *
 * @author Juan Silva
 */
public class CodeConfiguration {

    public String DatabasePath;
    // Code
    public String TemplateFolderPath;
    public String TemplateFile;
    public String TargetFolder;
    public String TargetNameFormat;

    public CodeConfiguration() {
        DatabasePath = "";
        TemplateFolderPath = "";
        TemplateFile = "";
        TargetFolder = "";
        TargetNameFormat = "";
    }

    public boolean validate() {
        if (!checkDatabasePath()) {
            Logger.Error("Database Path not valid: %s", DatabasePath);
            return false;
        }

        if (!checkTemplate()) {
            Logger.Error("Template Path not valid: %s", getTemplateFullPath());
            return false;
        }

        if (!checkTarget()) {
            Logger.Error("Target Path not valid: ", getTargetFullPath());
            return false;
        }

        return true;
    }

    public boolean checkDatabasePath() {
        if (DatabasePath == null) {
            Logger.Warning("Database Path is null");
            return false;
        }

        if (DatabasePath.isEmpty()) {
            Logger.Warning("Database Path is Empty");
            return false;
        }

        if (!DatabasePath.endsWith(".xlsx")) {
            Logger.Warning("Database is not an XLSX file");
            return false;
        }

        File file = new File(DatabasePath);
        if (!file.exists()) {
            Logger.Warning("Database file does not exist: ", file.getAbsolutePath());
            return false;
        }

        return true;
    }

    public boolean checkTemplate() {
        if (TemplateFile == null) {
            Logger.Warning("Template File is null");
            return false;
        }

        if (TemplateFile.isEmpty()) {
            Logger.Warning("Template File is empty");
            return false;
        }

        if (TemplateFolderPath != null && !TemplateFolderPath.isEmpty()) {
            if (!TemplateFolderPath.endsWith("/")) {
                TemplateFolderPath = TemplateFolderPath.concat("/");
            }
        }

        File file = new File(getTemplateFullPath());
        if (!file.exists()) {
            Logger.Warning("Template File does not exist: ", file.getAbsolutePath());
            return false;
        }

        return true;
    }

    public boolean checkTarget() {
        if (TargetNameFormat == null) {
            Logger.Warning("Target File Name is null");
            return false;
        }

        if (TargetNameFormat.isEmpty()) {
            Logger.Warning("Target File Name is empty");
            return false;
        }

        if (TargetFolder != null && !TargetFolder.isEmpty()) {
            if (!TargetFolder.endsWith("/")) {
                TargetFolder = TargetFolder.concat("/");
            }
        }

        return true;
    }

    public String getTemplateFullPath() {
        return TemplateFolderPath.concat(TemplateFile);
    }

    public String getTargetFullPath() {
        return TargetFolder.concat(TargetNameFormat);
    }
}
