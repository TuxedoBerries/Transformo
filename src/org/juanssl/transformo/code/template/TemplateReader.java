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
package org.juanssl.transformo.code.template;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.juanssl.transformo.app.Logger;

/**
 *
 * @author Juan Silva
 */
public class TemplateReader {

    private String _basePath;
    private String _path;

    /**
     * Creates a new instance of this TemplateReader The Base path and the
     * Template path are set to null. Use SetPath and SetBasePath to properly
     * configure.
     */
    public TemplateReader() {
        _path = "";
        _basePath = "";
    }

    /**
     * Creates a new instance of this TemplateReader with the given Template
     * File path.
     *
     * @param path
     */
    public TemplateReader(String path) {
        _path = path;
        _basePath = "";
    }

    /**
     * Creates a new instance of this TemplateReader with the given Template
     * File path and the given Template Base Path.
     *
     * @param path
     * @param basePath
     */
    public TemplateReader(String path, String basePath) {
        _path = path;
        _basePath = basePath;
    }

    /**
     * Set the path for the template file.
     *
     * @param path
     */
    public void setPath(String path) {
        _path = path;
    }

    /**
     * Set the Base Path for this Reader.
     *
     * @param path
     */
    public void setBasePath(String path) {
        _basePath = path;
    }

    /**
     * Reads the given template file.
     *
     * @param path of the template
     * @return Template String.
     */
    public String readTemplate(String path) {
        _path = path;
        return readTemplate();
    }

    /**
     * Reads the template file previously set.
     *
     * @return Template String
     */
    public String readTemplate() {
        if (_path.isEmpty()) {
            Logger.Error("Empty Path");
            return "";
        }

        String fullPath = _basePath + _path;
        File file = new File(fullPath);
        if (!file.exists()) {
            Logger.Error("File does not exist: %s", fullPath);
            return "";
        }

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fullPath), Charset.defaultCharset());
        } catch (IOException ex) {
            Logger.Error("Error Reading File: %s", ex.toString());
        }

        if (lines == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size(); ++i) {
            builder.append(lines.get(i));
            builder.append("\n");
        }

        return builder.toString();
    }
}
