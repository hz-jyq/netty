package com.jinyuanqian.NettyTetst;



import java.util.ArrayList;


public class GenericTypes<T extends ArrayList> {


    public  static  void Test1 (ArrayList<? super B>  ex){
        C c =new C();
        c.setName("222");
        ex.add(c);
        A ss = (A) ex.get(0);
        A ss1 = (A) ex.get(1);
    }

    public static void main(String[] args) {
          ArrayList<A> list = new ArrayList<>();
          A a= new A();
          a.setName("11");
          list.add(a);
          Test1(list);
    }

}
