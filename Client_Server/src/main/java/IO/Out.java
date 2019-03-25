package IO;

import RSA.RSA;

import javax.crypto.Cipher;
import javax.servlet.jsp.JspWriter;

public class Out {
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJjN9hYptnQlF+7KQY/sXiDirw2/1o0yGYVNzXr8N8jYAXQcmTeRpe5z9QubUS/d150XiJ+/jVKdhpOpMLnHERLbeRjyj2ccDuIFvishnaPXLBajtDgBoU7iDz+SuTVfeS3NKaPDSJGzuF1xjH9vXowVosYdw64gRQzUoCbvq1RAgMBAAECgYBuHULFaCEI5FjZ/CLqTxIe9+BLkZrXYKo5ZANre2XB3Is0fdSOERXG/zpgNBE9tQEneNkjAuLGuKrDDtWgcDNy2KUJr90837xYTlCTSfF8bx36I3usO8lKgwzHahsasOKs5zugtH8unvcer+hRYvviDvX6za1G80LFIIySYwmzXQJBAMIZh6cQ+z3wGrO884N09VRGqly9eP6hzoCrzbRJVW66aGvoCgiCNZJbO6YOL3PqUxXjiG0FHD7p+Gv7ZOZzXfsCQQCr+Cwvah7YsmybwGnmAGvtn4eXj6L9VmIOcwVB/hjBmqPRe7UI54mpSrzralpzhlIVOh/Jt72yc378kzEnNjwjAkANArsWFsxQjYysqajFEgzz1jzLNKQspVn7RAjo4dWJM5OWUBqEEX5XzifEbMT/hyCEohjd/e6zxOkzC8nqrKcLAkEAlv49lZgg43WuRQcqNGf0W9zxyhK1MqlRSYyWdj4r8HneEsYCy4G47msP+8gTCVJL6hVx6cSqC/C68/wtcVd+bwJAPubGJWUi02/Yqk//1L05Pgaf0dnCEQW8km/5NpKCUADvyZDvv+S9XuuLJ39Qu12iwonMo9COyozFyDaJ4Wr5LA==";

    private JspWriter jspWriter;

    public Out(JspWriter jspWriter){
        this.jspWriter = jspWriter;
    }
    public void print(String str){
        try {
            jspWriter.print(RSA.privateCrypt(Cipher.ENCRYPT_MODE, str, PRIVATE_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
