package com.chnet.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyStore;

public class UncompExe4j {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("/home/tony/down/FTPA-GATESERVER.exe");// exe文件路径
		File f1 = new File("/home/tony/down/FTPA-GATESERVER.exe.rar");// 生成的rar文件路径
		FileInputStream fin;
		try {
			fin = new FileInputStream(f);
			FileOutputStream fout = new FileOutputStream(f1);
			BufferedInputStream bin = new BufferedInputStream(fin);
			BufferedOutputStream bout = new BufferedOutputStream(fout);
			int in = 0;
			do {
				in = bin.read();
				if (in == -1)
					break;
				in ^= 0x88;
				bout.write(in);
			} while (true);
			bin.close();
			fin.close();
			bout.close();
			fout.close();
			
			KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");//PKCS12,JKS
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
