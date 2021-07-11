public class Analyzer {

    private String input ="";//输入缓冲
    private String token ="";//单词缓冲

    private int p_input = 0;//输入缓冲指针
    //private int p_token = 0;//单词缓冲指针

    private char ch = ' ';
    private String[] keyWords = {"begin","if","then","while","do","end","int","String","boolean","class"};

    public Analyzer(){
        p_input = 0;
    }

    //外部接口
    public void setInput(String input) {
        this.input = input;
        p_input = 0;//每一行数据进来，这个下标要置0
    }

    //从输入缓冲区读取一个字符到ch中
    public char getCh(){
        ch = input.charAt(p_input);
        p_input++;
        return ch;
    }

    //去掉空白字符
    public char getNoBlankChar(){
        while(ch ==' ' || ch == 10){
            ch = input.charAt(p_input);
            p_input++;
        }
        return ch;
    }

    //判断该字符是否是字母
    private boolean isLetter(char ch){
        if(ch >='a' && ch <= 'z' || ch >='A' && ch <= 'Z'){
            return true;
        }else
            return false;
    }

    //判断该字符是否是数字
    private boolean isNumber(char ch){
        if(ch >='0' && ch <= '9' ){
            return true;
        }else
            return false;
    }
    //拼接单词
    void concat(){
        String ch_String  = String.valueOf(ch);//先转换成String类型才能拼接
        //System.out.println("ch_String : " + ch_String );
        //token.concat(ch_String);
        token = token + ch_String;
        //System.out.println("token : " + token );

    }

    //回退一个字符
    void retrace(){
        p_input --;
    }

    //检索关键字，返回关键字的种别码（即数组的下标）
    int reverse(){
        for(int i = 0 ; i < keyWords.length; i++){
            if(keyWords[i].equals(token)){
                return i+1;//返回关键字的下标
            }
        }
        return 10;//不是关键字则返回10
    }
    //判断该字符是否是空格字符
    private boolean isBlankChar(char ch){
        if(ch == ' '){
            return true;
        }else{
            return false;
        }
    }

    //判断该字符串是否都是数字
    private boolean isNumberString(String token){
        for(int i = 0 ; i < token.length() ; i++){
            if(!isNumber(token.charAt(i))){//只要有一个字符不是数字，即返回false
                return false;
            }
        }
        return true;
    }


    //词法扫描
    public WORD scanner(){
        //System.out.println("执行Scanner()");
        WORD myWord  = new WORD();
        myWord.setTypeNum(10);
        myWord.setWord("");
        //p_token = 0;
        token ="";

        getCh();//得到第一个字符
        getNoBlankChar();//去掉空白字符串的情况,确保得到的是有效字符

        //如果第一个字符是字母的情况
        if(isLetter(ch)){
            //while(isLetter(ch) || isNumber(ch)){//如果当前字符是数字或者字母
            while(isLetter(ch) || isNumber(ch)){//如果当前字符是数字或者字母
                concat();//将当前字符和之前的字符拼接起来
                getCh();//继续读取下一个字符
            }
            retrace();//回退一个字符（因为多读了一个字符）
            myWord.setTypeNum(reverse());
            myWord.setWord(token);
            return myWord;
        }
        else if(isNumber(ch)){//第一个字符是数字
            //while(isNumber(ch)){
            while(isLetter(ch) || isNumber(ch)){//如果当前字符是数字或者字母
                concat();
                getCh();
            }
            retrace();//回退一个字符（因为多读了一个字符）
            if(isNumberString(token)){//如果该单词都是数字字符
                myWord.setTypeNum(11);//数字的种别码是11
            }else{
                myWord.setTypeNum(600);//错误单词的种别码是600
            }
            myWord.setWord(token);
            return myWord;
        }
        else switch(ch){
                case '=': getCh();
                    if(ch == '='){
                        myWord.setTypeNum(39);
                        myWord.setWord("==");
                        return myWord;
                    }
                    else{//等号后不是等号则回退一位
                        retrace();
                        myWord.setTypeNum(21);
                        myWord.setWord("=");
                        return myWord;
                    }

                case '+': myWord.setTypeNum(13);
                    myWord.setWord("+");
                    return myWord;

                case '-': myWord.setTypeNum(14);
                    myWord.setWord("-");
                    return myWord;

                case '*': myWord.setTypeNum(15);
                    myWord.setWord("*");
                    return myWord;

                case '/': myWord.setTypeNum(16);
                    myWord.setWord("/");
                    return myWord;

                case '(': myWord.setTypeNum(26);
                    myWord.setWord("(");
                    return myWord;

                case ')': myWord.setTypeNum(27);
                    myWord.setWord(")");
                    return myWord;

                case '[': myWord.setTypeNum(28);
                    myWord.setWord("[");
                    return myWord;

                case ']': myWord.setTypeNum(29);
                    myWord.setWord("]");
                    return myWord;

                case '{': myWord.setTypeNum(30);
                    myWord.setWord("{");
                    return myWord;

                case '}': myWord.setTypeNum(31);
                    myWord.setWord("}");
                    return myWord;

                case ',': myWord.setTypeNum(32);
                    myWord.setWord(",");
                    return myWord;

                case '.': myWord.setTypeNum(41);
                    myWord.setWord(".");
                    return myWord;

                case '#': myWord.setTypeNum(0);
                    myWord.setWord("#");
                    return myWord;

                case ';': myWord.setTypeNum(34);
                    myWord.setWord(";");
                    return myWord;

                case '"': myWord.setTypeNum(50);
                    myWord.setWord("\"");
                    return myWord;

                case '\\': myWord.setTypeNum(51);
                    myWord.setWord("\\");
                    return myWord;

                case '>': getCh();
                    if(ch == '='){
                        myWord.setTypeNum(37);
                        myWord.setWord(">=");
                        return myWord;
                    }else{
                        retrace();
                        myWord.setTypeNum(35);
                        myWord.setWord(">");
                        return myWord;
                    }


                case '<': getCh();
                    if(ch == '='){
                        myWord.setTypeNum(38);
                        myWord.setWord("<=");
                        return myWord;
                    }else{
                        retrace();
                        myWord.setTypeNum(36);
                        myWord.setWord("<");
                        return myWord;
                    }

                case ':': getCh();
                    if(ch == '='){
                        myWord.setTypeNum(18);
                        myWord.setWord(":=");
                        return myWord;
                    }else{
                        retrace();
                        myWord.setTypeNum(33);
                        myWord.setWord(":");
                        return myWord;
                    }

                case '!': getCh();
                    if(ch == '='){
                        myWord.setTypeNum(40);
                        myWord.setWord("!=");
                        return myWord;
                    }else{
                        retrace();
                        myWord.setTypeNum(-1);
                        myWord.setWord("ERROR");
                        return myWord;
                    }

                case '\0':  myWord.setTypeNum(1000);
                    myWord.setWord("该行分析结束");
                    return myWord;

                case '\t':  myWord.setTypeNum(601);
                    myWord.setWord("Tab键");
                    return myWord;

                default:    myWord.setTypeNum(-1);
                    myWord.setWord("ERROR");
                    return myWord;

            }
    }
}

