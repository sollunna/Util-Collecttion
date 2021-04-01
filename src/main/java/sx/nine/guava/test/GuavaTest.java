package sx.nine.guava.test;



/**
 * @Author NineEr
 * @Description //
 * @Date $ $
 **/
public class GuavaTest {
    public static void main(String[] args)  {



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
