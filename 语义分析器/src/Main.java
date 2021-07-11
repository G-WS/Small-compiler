import java.io.File;
import java.util.ArrayList;

public class Main {

    public ArrayList<WORD> a() {
	   /* String test = "public static void main(String[] args) {";
	    test = test +"\0";*/
        //System.out.println("test :" + test);
        Analyzer analyzer = new Analyzer();

        //从文本文件得到要编译的内容
        FileInput fileInput = new FileInput();
        ArrayList<String> code_ArrayList = fileInput.getFileCode(new File("data/testData.txt"));
        ArrayList<WORD> wordList=new ArrayList<WORD>();

        WORD oneWord = new WORD();
        for(int i = 0 ; i < code_ArrayList.size();i++){
            String test = code_ArrayList.get(i);


            analyzer.setInput(test);

            //WORD oneWord = new WORD();
            int over = 1;
            while(over < 1000 ){
                //for(int i = 0; i< 5; i++){
                oneWord = analyzer.scanner();
                //if(oneWord.getTypeNum() <1000){
//	    		System.out.println(oneWord.getWord() + " : " + oneWord.getTypeNum());
                wordList.add(oneWord);
                //}
                over = oneWord.getTypeNum();
            }

        }
        return wordList;
    }
}
