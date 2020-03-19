import org.apache.poi.hslf.record.ExObjListAtom;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: NineEr
 * @Date: 2020/3/18 15:26
 * @Description:
 */
public class Demo {
    public static void main(String[] args) {
        ArrayList<String> paths = new ArrayList<String>();
        getAllFilePaths(new File("D:\\DeveloperTools\\Git"),paths);
        for (String path : paths) {
            System.out.println(path);
        }
    }

    private static void getAllFilePaths(File filePaths, ArrayList<String> paths) {
        File[] files = filePaths.listFiles();

        if (files==null){
            return;
        }else{
            for (File file : files) {
                if(file.isDirectory()){
                    paths.add(file.getPath());
                    getAllFilePaths(file,paths);
                }else {
                    paths.add(file.getPath());
                }
            }
        }
    }

    private static void getAllFilePaths2(File filePath, List<String> paths) {
        File[] files = filePath.listFiles();
        if(files==null){
            return;
        }
        for (File file : files) {
            if(file.isDirectory()){
                paths.add(file.getPath());
                //getAllFilePaths(file,paths);
            }else{
                paths.add(file.getPath());
            }
        }

    }



}
