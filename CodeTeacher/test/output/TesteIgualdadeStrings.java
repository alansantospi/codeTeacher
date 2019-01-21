
package output;


public class TesteIgualdadeStrings {
    public static void main(String[] args) {
        String str1, str2;
        
        str1 = "Curso de Java";
        str2 = str1;
        
        System.out.println("String1: " + str1);
        System.out.println("String2: " + str2);
        
        System.out.println("Mesmo objeto? " + (str1 == str2));
        
        str2 = new String(str1);
        
        System.out.println("String1: " + str1);
        System.out.println("String2: " + str2);
        
        System.out.println("Mesmo objeto? " + (str1 == str2));
        
        System.out.println("Mesmo valor? " + str1.equals(str2));
    
      
    }
    
}
