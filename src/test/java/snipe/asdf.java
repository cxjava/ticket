package snipe;

import org.xvolks.jnative.JNative;

/**
 * @author Maty Chen
 * @date 2013-1-21下午1:00:26
 */
public class asdf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static  int testJNative(int a,int b) throws NativeException, IllegalAccessException{  
        JNative n = null;  
        try{  
            n = new JNative("zzz.dll", "add");  
            n.setRetVal(Type.INT);       
            n.setParameter(0 , a ) ;    
            n.setParameter(1, b);  
            n.invoke();        
            System.out.println(  "返回："  + n.getRetVal());        
            return Integer.parseInt(n.getRetVal());  
        }finally{  
            if (n != null)  
                n.dispose();  
        }  
    }  
}
