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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.juanssl.transformo.app.Logger;

/**
 *
 * @author Juan Silva
 */
public class TemplateWriter {

    private File _file;
    private FileWriter _fileWriter;
    private BufferedWriter _bufferedWriter;

    public void writeFile(String path, String content) {
        if (!createFile(path)) {
            return;
        }

        if (!openFileWriter()) {
            return;
        }

        if (!openBufferedWriter()) {
            closeFileWriter();
            closeFile();
            return;
        }

        Logger.Info("Writing data to [%s]", _file.getPath());
        writeToFile(content);

        closeBufferedWriter();
        closeFileWriter();
        closeFile();
    }

    private boolean createFile(String path) {
        _file = new File(path);
        if (_file.exists()) {
            Logger.Warning("File already exit. Overwriting file [%s]", _file.getPath());
            return true;
        }

        // Create Folder if does not exist
        File folder = _file.getParentFile();
        if (folder != null && !folder.exists()) {
            boolean result = folder.mkdirs();
            if (!result) {
                Logger.Warning("Could not create parent folder");
                return false;
            }
        }

        // Create File
        try {
            _file.createNewFile();
        } catch (IOException ex) {
            Logger.Error("Could not create file[%s]: %s", path, ex.toString());
            return false;
        }

        return true;
    }

    private void closeFile() {
        _file = null;
    }

    private boolean openFileWriter() {
        try {
            _fileWriter = new FileWriter(_file.getAbsoluteFile());
        } catch (IOException ex) {
            Logger.Error("Could not create File Writer [%s]: %s", _file.getPath(), ex.toString());
            return false;
        }

        return true;
    }

    private void closeFileWriter() {
        if (_fileWriter == null) {
            return;
        }

        try {
            _fileWriter.close();
        } catch (IOException ex) {
            Logger.Error("Could not close File Writer: %s", ex.toString());
        }

        _fileWriter = null;
    }

    private boolean openBufferedWriter() {
        _bufferedWriter = new BufferedWriter(_fileWriter);
        return true;
    }

    private boolean writeToFile(String content) {
        try {
            _bufferedWriter.write(content);
        } catch (IOException ex) {
            Logger.Error("Could not write data to file: %s", ex.toString());
        }
        return true;
    }

    private void closeBufferedWriter() {
        if (_bufferedWriter == null) {
            return;
        }

        try {
            _bufferedWriter.close();
        } catch (IOException ex) {
            Logger.Error("Could not close Buffered Writer: %s", ex.toString());
        }

        _bufferedWriter = null;
    }
}
