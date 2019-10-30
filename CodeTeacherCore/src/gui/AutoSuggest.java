package gui;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.text.Document;
import javax.swing.JTextArea;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import utils.ClassPathUtils;

public class AutoSuggest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextField jtfCountries = null;
	private JLabel jlbPaises = null;
	private JScrollPane BarraDeRolagem = null;
	private JTextArea jTextAreaPaiese = null;

	private JTextField getJtfCountries() {
		if (jtfCountries == null) {
			jtfCountries = new JTextField();
			jtfCountries.setBounds(new Rectangle(111, 319, 393, 28));
		}
		return jtfCountries;
	}

	private JScrollPane getBarraDeRolagem() {
		if (BarraDeRolagem == null) {
			// jTextArea dentro do JScrollPane
			BarraDeRolagem = new JScrollPane(jTextAreaPaiese);
			// tamanho do jScrollPane
			BarraDeRolagem.setBounds(new Rectangle(7, 67, 607, 242));
			// BarraDeRolagem.setBackground(new Color(217, 232, 238));
			// só mostra a barra vertical se necessário
			BarraDeRolagem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			// só mostra a barra de rolagem horizontal se necessario
			BarraDeRolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			BarraDeRolagem.setViewportView(getJTextAreaPaiese());
			jTextAreaPaiese.getText();
			// Não corta a palavra ao quebra a linha
			jTextAreaPaiese.setWrapStyleWord(true);
			// Quebra de linha automatica
			jTextAreaPaiese.setLineWrap(true);
		}
		// retorna o jScrollPane
		return BarraDeRolagem;
	}

	private JTextArea getJTextAreaPaiese() {
		if (jTextAreaPaiese == null) {
			jTextAreaPaiese = new JTextArea();
			// jTextAreaPaiese.setBounds(new Rectangle(7, 67, 607, 242));
			jTextAreaPaiese.setBackground(new Color(217, 232, 238));
			jTextAreaPaiese.setText("\n Afghanistan " + "\n Albania " + "\n Algeria " + "\n Andorra " + "\n Angola "
					+ "\n Argentina  " + "\n Armenia " + "\n Austria" + "\n Bahamas " + "\n Bahrain " + "\n Bangladesh "
					+ "\n Barbados " + "\n Belarus" + "\n Belgium " + "\n Benin " + "\n Bhutan "
					+ "\n BoliviaBosnia & Herzegovina " + "\n Botswana " + "\n Brazil " + "\n Bulgaria"
					+ "\n Burkina Faso " + "\n Burma " + "\n Burundi" + "\n Cambodia " + "\n Cameroon " + "\nCanada "
					+ "\n China " + "\n Colombia " + "\n Comoros " + "\n Congo " + "\n Croatia " + "\n Cuba "
					+ "\n Cyprus " + "\n Czech Republic" + "\n Denmark " + "\n Georgia " + "\n Germany " + "\n Ghana "
					+ "\n Great " + "\n Britain " + "\n Greece " + "\n Hungary \n Holland " + "\n India " + "\n Iran "
					+ "\n Iraq \n Italy" + "\n Somalia " + "\n Spain " + "\n Sri Lanka " + "\n Sudan "
					+ "\n South Africa" + "\n Suriname" + "\n Swaziland " + "\n Sweden " + "\n Switzerland "
					+ "\n Syria " + "\n Uganda" + "\n Ukraine " + "\n United Arab Emirates " + "\n United Kingdom"
					+ "\n United States " + "\n Uruguay \n Uzbekistan " + "\n Vanuatu" + "\n Venezuela " + "\n Vietnam "
					+ "\n Yemen " + "\n Zaire " + "\n Zambia " + "\n Zimbabwe");
			// Ponhe o cursor na primeira linha da TextArea
			jTextAreaPaiese.setCaretPosition(1);
		}
		return jTextAreaPaiese;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AutoSuggest thisClass = new AutoSuggest();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	public AutoSuggest() {
		super();
		initialize();

		// Create the completion service.
		NameService nameService = new NameService();

		// Create the auto completing document model with a reference to the
		// service and the input field.
		Document autoCompleteDocument = new AutoCompleteDocument(nameService, jtfCountries);
		// Set the auto completing document as the document model on our input
		// field.
		jtfCountries.setDocument(autoCompleteDocument);

	}

	private void initialize() {
		this.setSize(627, 386);
		this.setContentPane(getJContentPane());
		this.setTitle("AutoComplete JTextFild In Java");
		// Tamanho fixo do programa, sem alteção
		this.setResizable(false);
		// Deixa o programa no meio da tela, centralizado
		this.setLocationRelativeTo(null);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jlbPaises = new JLabel();
			jlbPaises.setBounds(new Rectangle(9, 3, 603, 42));
			jlbPaises.setFont(new Font("Arial", Font.BOLD, 28));
			// Centraliza um texto em uma Label
			jlbPaises.setHorizontalAlignment(SwingConstants.CENTER);
			jlbPaises.setText("AutoSuggest in JTextField");

			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJtfCountries(), null);
			jContentPane.add(jlbPaises, null);
			jContentPane.add(getBarraDeRolagem(), null);
			// jContentPane.add(getJTextAreaPaiese(), null);
		}
		return jContentPane;
	}

	// *******************************************************************
	// Samuel Sjoberg
	// http://samuelsjoberg.com/archive/2010/01/autocompletion-follow-up
	// samuel.sjoeberg@gmail.com
	// *******************************************************************
	public class NameService implements CompletionService<String> {

		private List<String> data;

		public NameService() {
//			data = Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Argentina", "Armenia",
//					"Austria", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Benin", "Bhutan",
//					"Bolivia", "Bosnia & Herzegovina", "Botswana", "Brazil", "Bulgaria", "Burkina Faso", "Burma",
//					"Burundi", "Cambodia", "Cameroon", "Canada", "China", "Colombia", "Comoros", "Congo", "Croatia",
//					"Cuba", "Cyprus", "Czech Republic", "Denmark", "Georgia", "Germany", "Ghana", "Great Britain",
//					"Greece", "Hungary", "Holland", "India", "Iran", "Iraq", "Italy", "Somalia", "Spain", "Sri Lanka",
//					"Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Uganda", "Ukraine",
//					"United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu",
//					"Venezuela", "Vietnam", "Yemen", "Zaire", "Zambia", "Zimbabwe");
			data = ClassPathUtils.match("");
			System.out.println(data);
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			for (String o : data) {
				b.append(o).append("\n");
			}
			return b.toString();
		}

		public String autoComplete(String startsWith) {
			// Naive implementation, but good enough for the sample
			String hit = null;
			for (String o : data) {
				if (o.startsWith(startsWith)) {
					// CompletionService contract states that we only
					// should return completion for unique hits.
					if (hit == null) {
						hit = o;
					} else {
						hit = null;
						break;
					}
				}
			}
			return hit;
		}

	}// fim da classe NameService

	public interface CompletionService<T> {

		T autoComplete(String startsWith);
	}

	public class AutoCompleteDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;
		private CompletionService<?> completionService;
		private JTextComponent documentOwner;

		public AutoCompleteDocument(CompletionService<?> service, JTextComponent documentOwner) {
			this.completionService = service;
			this.documentOwner = documentOwner;
		}

		protected String complete(String str) {
			Object o = completionService.autoComplete(str);
			return o == null ? null : o.toString();
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null || str.length() == 0) {
				return;
			}

			String text = getText(0, offs); // Current text.
			String completion = complete(text + str);
			int length = offs + str.length();
			if (completion != null && text.length() > 0) {
				str = completion.substring(length - 1);
				super.insertString(offs, str, a);
				documentOwner.select(length, getLength());
			} else {
				super.insertString(offs, str, a);
			}
		}
	}
}