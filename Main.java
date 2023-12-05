public class Main{
    public static void main(String[] args){
        Matrix m1 = new Matrix();
        //Matrix m2 = new Matrix();
        //Matrix m3 = new Matrix();

        m1.interMatrix();
        int p = Matrix.searchrank(m1);
        System.out.println(p);


    }
}