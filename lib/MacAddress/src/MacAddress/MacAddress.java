/*
 * MAC data type
 *
 * @ author  XUMUMI
 * @ create 07/21/2018
 *
 *
 * MAC 数据类型
 *
 * @ 作者  XUMUMI
 * @ 创建时间  07/21/2018
 *
 */

package MacAddress;

public class MacAddress {
    private String cont;
    public MacAddress(String cont) throws MacFormatException {
        setCont(cont);
    }

    private static boolean via(String testStr){
        String regexp = "([A-F0-9]{2}:){5}[A-F0-9]{2}";
        return testStr.matches(regexp);
    }

    void setCont(String setStr) throws MacFormatException {
        setStr = setStr.toUpperCase();
        setStr = setStr.replace(" ", "");
        setStr = setStr.replace("-", ":");
        if (via(setStr)) cont = setStr;
        else throw new MacFormatException("MAC format error");
    }

    public String getCont(){
        return cont;
    }

    public static void main(String[] args){
        try {
            String testStr = "91:65:2D:49:EC:63";
            MacAddress testMac = new MacAddress(testStr);
            System.out.print(testMac.getCont());
        } catch (MacFormatException e) {
            e.printStackTrace();
        }
    }
}

