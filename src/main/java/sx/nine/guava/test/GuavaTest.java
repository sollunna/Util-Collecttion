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

    public int[] SquareArray(int[] A) {
        // write your code here
        for(int i=0;i<A.length;i++){
            A[i]= (int) Math.sqrt(2);
        }
        int[] res = new int[4];

        int min ;
        int max ;
        for(int i=0;i<A.length;i++){
            min = A[i];
            for(int j=0;j<A.length;j++){
                if(min>A[j]){
                    min=A[j];
                }
            }
            res[i]=min;
        }
        return  res;
    }
}
