/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import de.sbe.utils.logging.Execution;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public abstract class FileUtils {
    public static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    @Execution
    public static String getRelativePath(File _src, File _relativeTo) throws IOException {
        StringBuilder path = new StringBuilder();
        List<String> src = FileUtils.splitFilePath(_src);
        List<String> relativeTo = FileUtils.splitFilePath(_relativeTo.isDirectory() ? _relativeTo : _relativeTo.getParentFile());
        Iterator<String> itSrc = src.iterator();
        Iterator<String> itRelativeTo = relativeTo.iterator();
        while (itSrc.hasNext() && itRelativeTo.hasNext() && itSrc.next().equals(itRelativeTo.next())) {
            itSrc.remove();
            itRelativeTo.remove();
        }
        itRelativeTo = relativeTo.iterator();
        if (itRelativeTo.hasNext()) {
            itRelativeTo.next();
            path.append(".").append(".");
            if (itRelativeTo.hasNext()) {
                path.append("/");
            }
        }
        itSrc = src.iterator();
        while (itSrc.hasNext()) {
            if (path.length() > 0) {
                path.append("/");
            }
            path.append(itSrc.next());
        }
        return path.toString();
    }

    public static void mkdirs(File _dir) {
        FileUtils.mkdirs(TMP_DIR, _dir);
    }

    public static void mkdirs(File _root, File _dir) {
        FileUtils.mkdirs(_root, _dir, true, true, true);
    }

    public static void mkdirs(File _root, File _dir, boolean _executable, boolean _readable, boolean _writable) {
        if (_root.equals(_dir)) {
            return;
        }
        if (!_dir.exists()) {
            _dir.mkdirs();
        }
        _dir.setExecutable(_executable, false);
        _dir.setReadable(_readable, false);
        _dir.setWritable(_writable, false);
        FileUtils.mkdirs(_root, _dir.getParentFile(), _executable, _readable, _writable);
    }

    private static List<String> splitFilePath(File _file) throws IOException {
        ArrayList<String> files = new ArrayList<String>();
        for (String chunk : _file.getCanonicalPath().split(Pattern.quote(File.separator))) {
            files.add(chunk);
        }
        return files;
    }

    private FileUtils() {
    }
}

