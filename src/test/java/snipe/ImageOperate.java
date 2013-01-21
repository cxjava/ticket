package snipe;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author Maty Chen
 * @date 2013-1-21上午10:37:13
 */
public class ImageOperate {
	static  
    {   
        System.loadLibrary("snipe");//载入dll  
    }  
  
    //用native关键字修饰将被其它语言实现的方法  
    public native String ImageToString(byte[] bytes);//函数声明  
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file=new File("D:/05_Document/Downloads/passCodeAction.jpg");
		ImageOperate op=new ImageOperate();
		String code=op.ImageToString(FileUtils.readFileToByteArray(file));
		System.out.println(code);
//		System.out.println(System.getProperty("java.library.path"));
	}

}
