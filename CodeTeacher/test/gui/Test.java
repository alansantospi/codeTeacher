package gui;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception{
        List<Class> classes = getClasses(Thread.currentThread().getContextClassLoader(),"codeteacher/err");
        for(Class c:classes){
            System.out.println("Class: "+c);
        }
    }

    public static List<Class> getClasses(ClassLoader cl,String pack) throws Exception{

        String dottedPackage = pack.replaceAll("[/]", ".");
        List<Class> classes = new ArrayList<Class>();
        URL upackage = cl.getResource(pack);

        DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
        String line = null;
        while ((line = dis.readLine()) != null) {
            if(line.endsWith(".class")) {
               classes.add(Class.forName(dottedPackage+"."+line.substring(0,line.lastIndexOf('.'))));
            }
        }
        return classes;
    }
}