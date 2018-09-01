package com.chnet.tools.crypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACDemo {
    /**
     * hmac util 
     * mode = HmacSHA256 HmacSHA1 HmacMD5
     * @param data
     * @param key
     * @param mode
     * @return
     */
    public static String hamcsha(byte[] data, byte[] key,String mode) 
    {
          try {
              SecretKeySpec signingKey = new SecretKeySpec(key, mode);
              Mac mac = Mac.getInstance(mode);
              mac.init(signingKey);
              return byte2hex(mac.doFinal(data));
          } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
          } catch (InvalidKeyException e) {
               e.printStackTrace();
          }
         return null;
     }
    
    public static String byte2hex(byte[] b) 
    {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }

    public static void main(String[] args) {
    	String mode = "HmacSHA256";
        System.out.println(hamcsha("123456".getBytes(),"12345678".getBytes(),mode));
        mode = "HmacSHA1";
        System.out.println(hamcsha("123456".getBytes(),"12345678".getBytes(),mode));
    }

}
