package the.david.randomdungeon.handler;

import org.codehaus.plexus.util.FileUtils;
import the.david.randomdungeon.RandomDungeon;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class dungeonInstanceWorldHandler {
//    public static void copyWorld(File source, File target){
//        try {
//            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
//            if(!ignore.contains(source.getName())) {
//                if(source.isDirectory()) {
//                    if(!target.exists())
//                        if (!target.mkdirs())
//                            throw new IOException("Couldn't create world directory!");
//                    String files[] = source.list();
//                    for (String file : files) {
//                        File srcFile = new File(source, file);
//                        File destFile = new File(target, file);
//                        copyWorld(srcFile, destFile);
//                    }
//                } else {
//                    InputStream in = new FileInputStream(source);
//                    OutputStream out = new FileOutputStream(target);
//                    byte[] buffer = new byte[1024];
//                    int length;
//                    while ((length = in.read(buffer)) > 0)
//                        out.write(buffer, 0, length);
//                    in.close();
//                    out.close();
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static void copyDirectory(File sourceFile, File targetFile){
        try {
            FileUtils.deleteDirectory(targetFile);
            FileUtils.copyDirectoryStructure(sourceFile, targetFile);
        } catch (IOException ignored) {

        }
        FileUtils.fileDelete(targetFile.getPath() + "\\\\uid.dat");
        FileUtils.fileDelete(targetFile.getPath() + "\\\\session.lock");
    }
}
