/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;
/**
 *
 * @author G
 */
public class Functions {
    public Functions(){}
    public void out(Object... in){
        for(int x = 0;x<in.length;x++){
            Class c = in[x].getClass();
            if(c==Integer.class || c==Double.class||c==String.class||c==Long.class||c==Float.class||c==null||c==char.class||c==Boolean.class||c==Class.class){
                System.out.println(in[x]);
            }else{
                out(c);
            }
        }
    }
}
