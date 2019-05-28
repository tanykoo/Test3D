package com;

import com.tanykoo.stringutil.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {

    /**
     * 对字符串进行gzip压缩
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String compress(String data) throws IOException {
        if (null == data || data.length() <= 0) {
            return data;
        }
        //创建一个新的byte数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        //将b.length个字节写入此输出流
        gzip.write(data.getBytes());
        gzip.flush();
        gzip.close();

        //使用指定的charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("ISO-8859-1");
    }

    /**
     * 对字符串进行解压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String unCompress(String data) throws Exception {
        if (null == data && data.length() <= 0) {
            return data;
        }
        //创建一个新的byte数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //创建一个byte数组输入流
        ByteArrayInputStream in = new ByteArrayInputStream(StringUtil.hex2Byte(StringUtil.base642Hex(data)));
        //创建gzip输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = gzip.read(buf)) >= 0) {
            out.write(buf, 0, len);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString("UTF-8");
    }

    public static void main(String[] args) {
        try {
            System.out.println(GzipUtils.unCompress("H4sIAAAAAAAAA82UTWvUQBjHv8ucU5nXTJIv4KEn8STiYZqZNoHZZMlMFCkLSi+iCB4UWy+KFwvFWpBC8eKXMdvut/DJC1233QXXi5vbTP7PPC/zm/8+qowbuT2UoKuzg8u3x9MXb5qXH1GAfOmVrconDiUsQFp5hZKH+8gU2ucjA3ocJhwnGIN2t7Z2XOUpbEt8Bwdop3YuKytfqE7avH7efDr7dfG1+XlweXI0PX919e0dxGXK7g5xTLRxziufl8V12PvZ4XfQ5c7mhXFpZnRt4UBQVnXR10ElrKCqFaHajFXlb5fc7w9BaanbMikmEvPtewRSwlhyXStb1KMdU6GERgF6bLI8tcY/HYMahpJrlEQER0yEjHIhMIsizkE4HNv+3+IyJoKKSLIIcyo4DykIhlaGxCHvmkxV4Yy1KCHtEbU2hV+YqjPK98nR9Oh09uxD8+NLF6i0vjkcnUMRRXsjlENCWKYljGzxvH7aZbXXdYIJobj7oKe4vR2XrWhxEiyQECV0LRJmJ4fN6ef/SsK85GUkcIoZxvcf3N1eBwVMKGWERTKmhAnO4vam+xl3KAjJOOUSMwCN0lBS3LKyiEIsNwAFTq9RABbkHyjcbvEGCnFC1kJhA0xhXvIyFP7FFLZCeEkkglckRCjjEF7/3Nz+2hU2AoWVrrCsx8mjzjgHV2udBE1+A/O6eopiBgAA"));;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}