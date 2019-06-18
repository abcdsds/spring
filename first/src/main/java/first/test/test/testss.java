package first.test.test;

public class testss {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println(unicodeConvert("\\uc778\\uc99d \\uc2e4\\ud328\\ud558\\uc600\\uc2b5\\ub2c8\\ub2e4."));


	}
	
	public static String unicodeConvert(String str) {
        StringBuilder sb = new StringBuilder();
        char ch;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            ch = str.charAt(i);
            if (ch == '\\' && str.charAt(i+1) == 'u') {
                sb.append((char) Integer.parseInt(str.substring(i+2, i+6), 16));
                i+=5;
                continue;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

}
