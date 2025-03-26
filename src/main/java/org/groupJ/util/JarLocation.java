package org.groupJ.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class JarLocation { //Separate Class, better

    public static String getJarDirectory() throws IOException, URISyntaxException {

        // get the location of this specific class (should reflect as the jarfile)
        CodeSource codeSource = JarLocation.class.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            return null;
        }

        File jarFile;
        try {
            jarFile = new File(codeSource.getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw e;
        }

        String jarDir;

        if (jarFile.isFile()) {
            jarDir = jarFile.getParentFile().getAbsolutePath();
        } else {
            // handle cases where we may not compile to jar files ( very unlikely )
            jarDir = jarFile.getAbsolutePath();
        }

        return jarDir;

    }
}