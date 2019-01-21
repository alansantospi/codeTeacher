package gui.component;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import gui.component.AutoCompleteBehaviour.DefaultAutoCompleteCallback;
import utils.ClassPathUtils;

/**

 * Tiny demo showing how to use the {@link AutoCompleteBehaviour}.

 *

 * @author tobias.gierke@code-sourcery.de

 */

public class AutoCompleteBehaviourClassPath {
	
    protected static final class Klazz {
        public String name;

        public String canonicalName;
        public Klazz(String name, String canonicalName) {
            this.name = name;

            this.canonicalName = canonicalName;

        }
    }
    
    private static Klazz person(String name, String email) {
        return new Klazz(name,email);

    }
    
    final AutoCompleteBehaviour<Klazz> autoComplete = new AutoCompleteBehaviour<Klazz>();
    
    public AutoCompleteBehaviourClassPath(final JTextField editor) {
    	// prepare some test data

        final List<Klazz> choices = new ArrayList<>();

        

        // add callback that will generate proposals

        // and map the user's selection back to what will

        // be inserted into the JEditorPane

        autoComplete.setCallback( new DefaultAutoCompleteCallback<Klazz>()  {
            @Override
            public List<Klazz> getProposals(String input) {
                if ( input.length() < 2 ) {
                    return Collections.emptyList();
                }

                final String lower = input.toLowerCase();
                final List<Klazz>  result = new ArrayList<>();
                
                List<String> match = ClassPathUtils.match(input);
                for (String klazzName : match) {
                	String name = klazzName.substring(klazzName.lastIndexOf(".") + 1);
					choices.add(person(name, klazzName));
				}

                for ( Klazz c : choices ) {
                	if ( c.name.toLowerCase().contains( lower ) || c.canonicalName.toLowerCase().contains(lower)  ) {
                        result.add( c );
                    }
                }

                return result;
            }
            
            @Override
            public String getStringToInsert(Klazz person) {
                return person.canonicalName;
            }

        }); 

        // set a custom renderer for our proposals
        final DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
            public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                final Klazz p = (Klazz) value;

                setText( p.name + " - " + p.canonicalName );

                return result;
            }
        };

        autoComplete.setListCellRenderer(renderer);

        // setup initial size
        autoComplete.setInitialPopupSize(new Dimension(100, 300));

        // how many proposals to display before showing a scroll bar
        autoComplete.setVisibleRowCount(6);

        editor.setSize( new Dimension(640, 480));

        // attach behaviour to editor
        autoComplete.attachTo(editor);

	}
    
    public void setInitialPopupSize(Dimension initialPopupSize) {
    	autoComplete.setInitialPopupSize(new Dimension(100, 300));
    }
    
	public static void main(String[] args) throws Exception {
		
        final Runnable r = new Runnable()  {
            @Override
            public void run() {
                // prepare some test data

                final List<Klazz> choices = new ArrayList<>();

                final AutoCompleteBehaviour<Klazz> autoComplete = new AutoCompleteBehaviour<Klazz>();

                // add callback that will generate proposals

                // and map the user's selection back to what will

                // be inserted into the JEditorPane

                autoComplete.setCallback( new DefaultAutoCompleteCallback<Klazz>()  {
                    @Override
                    public List<Klazz> getProposals(String input) {
                        if ( input.length() < 2 ) {
                            return Collections.emptyList();
                        }

                        final String lower = input.toLowerCase();
                        final List<Klazz>  result = new ArrayList<>();
                        
                        List<String> match = ClassPathUtils.match(input);
                        for (String klazzName : match) {
                        	String name = klazzName.substring(klazzName.lastIndexOf(".") + 1);
							choices.add(person(name, klazzName));
						}

                        for ( Klazz c : choices ) {
                            if ( c.name.toLowerCase().contains( lower ) || c.canonicalName.toLowerCase().contains(lower)  ) {
                                result.add( c );
                            }
                        }
                        return result;
                    }
                    
                    @Override
                    public String getStringToInsert(Klazz person) {
                        return person.canonicalName;
                    }
                }); 

                // set a custom renderer for our proposals
                final DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
                    public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                        final Klazz p = (Klazz) value;

                        setText( p.name+" - "+p.canonicalName );

                        return result;
                    }
                };

                autoComplete.setListCellRenderer( renderer );

                // setup initial size
                autoComplete.setInitialPopupSize( new Dimension(100,300) );

                // how many proposals to display before showing a scroll bar
                autoComplete.setVisibleRowCount(6);

                final JEditorPane editor = new JEditorPane();
                editor.setSize( new Dimension(640,480));

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