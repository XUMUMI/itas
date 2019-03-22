/*
 * This class implements log show.
 *
 * @ author  XUMUMI
 * @ create 07/13/2018
 *
 *
 * 用于输出日志
 *
 * @ 作者  XUMUMI
 * @ 创建时间  07/13/2018
 *
 */

package Log;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {
    /* status log
     * 状态日志 */
    public static void sta(String str){
        System.out.println("\033[36m" + getNow() + " \033[32m[info]\033[0m: " + str);
    }

    /* warning log
     * 警告日志 */
    public static void warn(String str){
        System.out.println("\033[33m[Warning] \033[36m" + getNow() + "\033[0m: " + str);
    }

    /* error log
     * 错误日志 */
    public static void err(String str){
        System.out.println("\033[31m[ERR!] \033[36m" + getNow() + "\033[0m: " + str);
    }

    private static String getNow(){
        return LocalDate.now().toString() + " " + LocalTime.now().toString();
    }

    public static void main(String[] args){
        sta("info test");
        warn("warn test");
        err("error test");
    }
}
