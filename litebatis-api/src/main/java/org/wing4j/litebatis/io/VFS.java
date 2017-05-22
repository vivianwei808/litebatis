package org.wing4j.litebatis.io;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by wing4j on 2017/5/22.
 */
public abstract class VFS {
    public static VFS getInstance() {
        return null;
    }

    protected abstract List<String> list(URL url, String forPath) throws IOException;
    public abstract List<String> list(String path) throws IOException;

}
