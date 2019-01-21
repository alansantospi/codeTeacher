import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassInfo {
   private static String indent = "   ";
   public static void main( String[ ] args ) throws 
                                ClassNotFoundException {
      if ( args.length < 1 ) return; // exit if no classes given
      for ( int i = 0; i < args.length; i++ ) {
      	System.out.println(args[i]);
         new ClassInfo().printInfo( args[ i ] );
         System.out.println();
      }
   }   
   private void printInfo( String s ) throws ClassNotFoundException {
   	// problem
      Class c = Class.forName( s );      
      String modifiers = stringifyModifiers( c.getModifiers() );
      String name = c.getName();
      if ( c.isInterface() ) 
        System.out.print( modifiers + name );
      else
        System.out.print( modifiers + " class " + name + " extends " +
                          c.getSuperclass().getName() );
      Class[ ] interfaces = c.getInterfaces();
      if ( interfaces != null && interfaces.length > 0 ) {
        if ( c.isInterface() ) System.out.print( " extends " );
        else System.out.print( " implements " );
        for ( int i = 0; i < interfaces.length; i++ ) {
          if ( i > 0 )
            System.out.print( ", " );
          System.out.print( interfaces[ i ].getName() );
        }
      }
      System.out.println( " {" );
      System.out.println( indent +  "// Constructors" );
      Constructor[ ] constructors = c.getDeclaredConstructors();
      for ( int i = 0; i < constructors.length; i++ )
         printMethod( constructors[ i ] );
      System.out.println( indent + "// Other methods " );
      Method[ ] methods = c.getDeclaredMethods();
      for ( int i = 0; i < methods.length; i++ )
         printMethod( methods[ i ] );   
      System.out.println( indent + "// Fields " );
      Field[ ] fields = c.getDeclaredFields();
      if ( fields != null )
        for ( int i = 0; i < fields.length; i++ )
           printField( fields[ i ] );
      System.out.println( "}" );
   }
   private void printMethod( Member m ) {
      Class rt = null;
      Class[ ] params, exceptions;
      if ( m instanceof Method ) { // nonconstructor method
        Method method = (Method) m;
        rt = method.getReturnType();
        params = method.getParameterTypes();
        exceptions = method.getExceptionTypes();
      }
      else { // a constructor
        Constructor c =  (Constructor) m;
        params = c.getParameterTypes();
        exceptions = c.getExceptionTypes();
      }
      System.out.print( indent + stringifyModifiers( m.getModifiers() ) 
               + " " + (( rt != null ) ? getTypename( rt ) + " " : "" ) 
               + m.getName() + "( " );
      for ( int i = 0; i < params.length; i++ ) {
         if ( i > 0 )
           System.out.print( ", " );
         System.out.print( getTypename( params[ i ] ) );
      }
      if ( params.length > 0 )
        System.out.print( " )" );   // at least 1 param
      else
        System.out.print( ")" );    // no params
      if ( exceptions.length > 0 )
        System.out.print( " throws" );
      for ( int i = 0; i < exceptions.length; i++ ) {
         if ( i > 0 )
           System.out.print( ", " );
         System.out.print( getTypename( exceptions[ i ] ) );
      }
      System.out.println( ";" );
   } //*** printMethod
   private void printField( Field f ) {
      System.out.println( indent +
                          stringifyModifiers( f.getModifiers() ) + 
                          " " + getTypename( f.getType() ) + 
                          " " + f.getName() + ";" ); 
   }   
   private String stringifyModifiers( int i ) {
      return (i == 0) ? "" : Modifier.toString( i );
   }   
   private String getTypename( Class c ) {
      String b = "";
      while( c.isArray() ) {
        b += "[ ]";
        c = c.getComponentType();
      }
      return c.getName() + b;
   }
   
}   