package sx.nine.guava.test;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author NineEr
 * @Description //
 * @Date $ $
 **/
public class GuavaTest {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("D:\\gitworkspace\\Util-Collecttion\\src\\main\\java\\sx\\nine\\guide\\Redis.md");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\gitworkspace\\Util-Collecttion\\src\\main\\java\\sx\\nine\\guide\\Redis2.md");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        int size;
        byte[] bytes = new byte[1024];
        while ((size = bufferedInputStream.read(bytes))!=-1){
            bufferedOutputStream.write(bytes,0,size);
            bufferedOutputStream.flush();
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
