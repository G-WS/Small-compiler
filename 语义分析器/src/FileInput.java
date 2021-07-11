import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileInput {

    private ArrayList<String> code_Array = new ArrayList<String>();//用来存放文本文件的代码，一下标存储一行代码

    String s = null;//暂时存放一行代码

    public ArrayList<String> getFileCode(File file){

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            // String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                //result.append(System.lineSeparator()+s);
                s = s + "\0";
                code_Array.add(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
//	  System.out.println("文本行数： " + code_Array.size());
        return code_Array;
    }
}

