package com.chnet.tools.crypt;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
/** 
 *  
 * 
 * @author chengjian.he 
 * @version  3.4, 2016年8月5日 上午11:35:13  
 * @since   Yeexun 3.4 
 */  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.security.InvalidKeyException;  
import java.security.Key;  
import java.security.NoSuchAlgorithmException;  
import java.security.SecureRandom;  
  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.KeyGenerator;  
import javax.crypto.NoSuchPaddingException;  
import javax.crypto.ShortBufferException;  
  
public class AES {  
      
    private Key key;  
      
    /** 
     * 生成AES对称秘钥 
     * @throws NoSuchAlgorithmException 
     */  
    public void generateKey() throws NoSuchAlgorithmException {  
        KeyGenerator keygen = KeyGenerator.getInstance("AES");  
        SecureRandom random = new SecureRandom();  
        keygen.init(random);  
        this.key = keygen.generateKey();  
        System.out.println(key.toString());
        System.out.println(this.key.getFormat());
        System.out.println(this.key.getAlgorithm());
        System.out.printf("%h", this.key.getEncoded());
    }  
      
      
    /** 
     * 加密 
     * @param in 
     * @param out 
     * @throws InvalidKeyException 
     * @throws ShortBufferException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws IOException 
     */  
    public void encrypt(InputStream in, OutputStream out) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {  
        this.crypt(in, out, Cipher.ENCRYPT_MODE);  
    }  
      
    /** 
     * 解密 
     * @param in 
     * @param out 
     * @throws InvalidKeyException 
     * @throws ShortBufferException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws IOException 
     */  
    public void decrypt(InputStream in, OutputStream out) throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {  
        this.crypt(in, out, Cipher.DECRYPT_MODE);  
    }  
  
    /** 
     * 实际的加密解密过程 
     * @param in 
     * @param out 
     * @param mode 
     * @throws IOException 
     * @throws ShortBufferException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     */  
    public void crypt(InputStream in, OutputStream out, int mode) throws IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(mode, this.key);  
          
        int blockSize = cipher.getBlockSize();  
        int outputSize = cipher.getOutputSize(blockSize);  
        byte[] inBytes = new byte[blockSize];  
        byte[] outBytes = new byte[outputSize];  
          
        int inLength = 0;  
        boolean more = true;  
        while (more) {  
            inLength = in.read(inBytes);  
            if (inLength == blockSize) {  
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);  
                out.write(outBytes, 0, outLength);  
            } else {  
                more = false;  
            }  
        }  
        if (inLength > 0)  
            outBytes = cipher.doFinal(inBytes, 0, inLength);  
        else  
            outBytes = cipher.doFinal();  
        out.write(outBytes);  
        out.flush();  
    }  
  
    public void setKey(Key key) {  
        this.key = key;  
    }  
  
    public Key getKey() {  
        return key;  
    }  
      
    public static void main(String[] args) throws Exception {  
        AES aes = new AES();  
        aes.generateKey();  
        File file = new File("/tmp/screen.png");    
        FileInputStream in = new FileInputStream(file);   
        File file1 = new File("/tmp/pub.key");    
        FileOutputStream out = new FileOutputStream(file1);   
        aes.encrypt(in, out);
        out.flush();
        out.close();
        in.close();
        in = new FileInputStream(file1);
        File file2 = new File("/tmp/pri.key");    
        out = new FileOutputStream(file2);  
        aes.decrypt(in, out);
        out.flush();
        out.close();
        in.close();
    }  
  
}  
