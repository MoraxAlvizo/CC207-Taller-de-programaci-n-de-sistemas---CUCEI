/*
 * 
 */
import java.awt.EventQueue;
import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main{

	/**
	 * EL metodo Principal. Metodo donde inicia el programa
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrafica frame = new InterfazGrafica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		/*
		for(int i=0;i<255;i++){
			System.out.println("caracter : "+(char)i+" ASCII: "+i);
		}*/
		
	}
	



}
