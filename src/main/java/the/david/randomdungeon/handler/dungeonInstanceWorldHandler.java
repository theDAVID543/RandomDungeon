package the.david.randomdungeon.handler;

import org.codehaus.plexus.util.FileUtils;
import the.david.randomdungeon.RandomDungeon;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class dungeonInstanceWorldHandler{
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
		try{
			FileUtils.deleteDirectory(targetFile);
			FileUtils.copyDirectoryStructure(new File(sourceFile.getPath() + "/region"), new File(targetFile.getPath() + "/region"));
			FileUtils.copyDirectoryStructure(new File(sourceFile.getPath() + "/data"), new File(targetFile.getPath() + "/data"));
//			FileUtils.copyFileToDirectory(new File(sourceFile.getPath() + "/level.dat"), new File(targetFile.getPath() + "/level.dat"));

			InputStream in = Files.newInputStream(new File(sourceFile.getPath() + "/level.dat").toPath());
			OutputStream out = Files.newOutputStream(new File(targetFile.getPath() + "/level.dat").toPath());
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0)
				out.write(buffer, 0, length);
			in.close();
			out.close();
		}catch(IOException ignored){

		}
	}
}
