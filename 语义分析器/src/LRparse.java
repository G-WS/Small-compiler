import java.util.ArrayList;

public class LRparse {

    static ArrayList<WORD> wordList = new ArrayList<WORD>();
    private static int syn;   //所读取单词的种别码
    private static int kk;
    private static String token;   //所读取单词的值
    private static int i=0;
    private static int j=1;

    public static void main(String[] args) {

        Main main_modify = new Main();
        wordList = main_modify.a();   //与词法分析实验对接，本来词法分析程序实验中Main为主类，进一步在语法和语义分析实验中
        //作为一个方法a()调用，并返回所读取的所有单词存放于wordList中，以此实现词法分析，
        //然后进行语法或语义分析
        scanner();
        lrpaeser();
    }

    private static void scanner() {
        i=i+1;
        syn=wordList.get(i-1).getTypeNum();
        token=wordList.get(i-1).getWord();
    }

    private static void lrpaeser() {
        int schain=0;
        kk=0;
        if(syn==1){
            scanner();
            schain=yucu();
            if(syn==6){
                scanner();
                if(syn==0&&(kk==0)){
                    System.out.println("success");
                }
            }else{
                if(kk!=1){
                    System.out.println("error:缺少end");
                    kk=1;
                }
            }
        }else{
            System.out.println("error:缺少begin");
            kk=1;
        }
        return;
    }

    private static int yucu() {
        int schain=0;
        schain=statement();
        while(syn==34){
            scanner();
            schain=statement();
        }
        return schain;
    }

    private static int statement() {
        String tt,eplace;
        int schain=0;
        switch(syn){
            case 10:
                tt=token;
                scanner();
                if(syn==18){
                    scanner();
                    eplace=expression();
                    emit(tt,eplace,"","");
                    schain=0;
                }else{
                    System.out.println("缺少赋值号");
                    kk=1;
                }
                return schain;
        }
        return schain;
    }

    private static void emit(String tt, String eplace, String string,
                             String string2) {
        System.out.println(tt+"="+eplace+string+string2);
    }

    private static String expression() {
        String tp,ep2,eplace,tt;
        eplace=term();
        while(syn==13||syn==14){
            if(syn==13){
                tt="+";
            }else{
                tt="-";
            }
            scanner();
            ep2=term();
            tp=newtemp();
            emit(tp,eplace,tt,ep2);
            eplace=tp;
        }
        return eplace;
    }

    private static String newtemp() {  //该函数回送一个新的临时变量名，临时变量名产生的顺序为T1、T2、T3....
        String t="T";
        j++;
        int temp;
        temp=j-1;
        return t+temp;
    }

    private static String term() {
        String tp,ep2,eplace,tt;
        eplace=factor();
        while(syn==15||syn==16){
            if(syn==15){
                tt="*";
            }else{
                tt="/";
            }
            scanner();
            ep2=factor();
            tp=newtemp();
            emit(tp,eplace,tt,ep2);
            eplace=tp;
        }
        return eplace;
    }

    private static String factor() {
        String fplace="";
        if(syn==10){
            fplace=token;
            scanner();
        }else if(syn==11){
            fplace=token;    //itoa(sum,fplace,10);
            scanner();
        }else if(syn==26){
            scanner();
            fplace=expression();
            if(syn==27){
                scanner();
            }else{
                System.out.println("error:输出）错误");
                kk=1;
            }
        }else{
            System.out.println("error:输出表达式错误");
            kk=1;
        }
        return fplace;
    }

}
