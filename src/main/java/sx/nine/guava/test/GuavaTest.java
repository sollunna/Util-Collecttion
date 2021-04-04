package sx.nine.guava.test;


import javax.swing.*;
import java.io.*;

/**
 * @Author NineEr
 * @Description //
 * @Date $ $
 **/
public class GuavaTest {
    public static void main(String[] args) throws IOException {
        try
        {
            String fileName= "hellojava";
            File in=new File(fileName);
            FileReader reader=new FileReader(in);
            //读取文本
            char[] count=new char[(int)in.length()];
            reader.read(count);
            //统计单词数量
            String s=String.valueOf(count);
            String[ ] words=s.replaceAll("[^a-zA-Z]+"," ").trim( ).split(" ");

            System.out.println("字符数量："+count.length+"\n单词数量："+words.length);
            reader.close();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
