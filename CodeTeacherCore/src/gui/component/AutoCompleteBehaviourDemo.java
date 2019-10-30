package gui.component;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import gui.component.AutoCompleteBehaviour.DefaultAutoCompleteCallback;

/**

 * Tiny demo showing how to use the {@link AutoCompleteBehaviour}.

 *

 * @author tobias.gierke@code-sourcery.de

 */

public class AutoCompleteBehaviourDemo {

    protected static final class Person {

        public String name;

        public String email;



        public Person(String name, String email) {

            this.name = name;

            this.email = email;

        }

    }



    private static Person person(String name, String email) {

        return new Person(name,email);

    }



	public static void main(String[] args) throws Exception

    {

        final Runnable r = new Runnable() 

        {

            @Override

            public void run()

            {

                // prepare some test data

                final List<Person> choices = new ArrayList<>();

                choices.addAll( Arrays.asList( 

                        person("Joe Black","john.black@megacorp.com"),

                        person("John Doe","john.doe@unknown.net") , person("Sarah Doe" , "sarah.doe@unknown.net"),

                        person("Arthur Dent" , "arthur@guide.com") , person("Lara Croft" , "laracroft@longgone.com" ) ) );



                final AutoCompleteBehaviour<Person> autoComplete = new AutoCompleteBehaviour<Person>();



                // add callback that will generate proposals

                // and map the user's selection back to what will

                // be inserted into the JEditorPane

                autoComplete.setCallback( new DefaultAutoCompleteCallback<Person>() 

                {

                    @Override

                    public List<Person> getProposals(String input) 

                    {

                        if ( input.length() < 2 ) {

                            return Collections.emptyList();

                        }

                        final String lower = input.toLowerCase();



                        final List<Person>  result = new ArrayList<>();

                        for ( Person c : choices ) {

                            if ( c.name.contains( lower ) || c.email.contains(lower)  ) {

                                result.add( c );

                            }

                        }

                        return result;

                    }



                    @Override

                    public String getStringToInsert(Person person) 

                    {

                        return person.email;

                    }

                }); 



                // set a custom renderer for our proposals

                final DefaultListCellRenderer renderer = new DefaultListCellRenderer() {

                    public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)

                    {

                        final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                        final Person p = (Person) value;

                        setText( p.name+" <"+p.email+">" );

                        return result;

                    }

                };

                autoComplete.setListCellRenderer( renderer );



                // setup initial size

                autoComplete.setInitialPopupSize( new Dimension(100,300) );



                // how many proposals to display before showing a scroll bar

                autoComplete.setVisibleRowCount( 3 );



                final JEditorPane editor = new JEditorPane();

                editor.setSize( new Dimension(640,480 ) );



                // attach behaviour to editor

                autoComplete.attachTo( editor );



                final JFrame frame = new JFrame("demo");

                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

                frame.getContentPane().add( new JScrollPane( editor) );

                frame.pack();

                frame.setLocationRelativeTo( null );

                frame.setVisible( true );            

            }

        };

        SwingUtilities.invokeAndWait( r );

    }

}