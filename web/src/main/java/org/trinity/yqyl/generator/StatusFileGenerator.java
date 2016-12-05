package org.trinity.yqyl.generator;

import java.io.File;
import java.io.IOException;

public class StatusFileGenerator {

    public static void generate() throws IOException {
        final File resourceFolder = new File("./src/main/resources");

        final String folderPath = resourceFolder.getCanonicalFile().getPath();

        final File vmFolder = new File(folderPath + "/velocity");
        final File jsFolder = new File(folderPath + "/static/js/business");
        final File cssFolder = new File(folderPath + "/static/css/business");

        synchronizeFolder(vmFolder, jsFolder, ".js");
        synchronizeFolder(vmFolder, cssFolder, ".css");
    }

    public static void main(final String[] args) throws IOException {
        generate();
    }

    public static void synchronizeFolder(final File vmFolder, final File targetFolder, final String extension) throws IOException {
        if (!targetFolder.exists()) {
            targetFolder.mkdir();
        }

        for (final File item : vmFolder.listFiles()) {
            if (item.isDirectory()) {
                synchronizeFolder(item, new File(targetFolder.getPath() + "/" + item.getName()), extension);
                continue;
            }

            if (item.getName().endsWith(".vm")) {
                final File targetFile = new File(targetFolder.getPath() + "/" + item.getName().replace(".vm", "") + extension);
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                    System.out.println(targetFile.getPath());
                    continue;
                }
            }
        }
    }

}
