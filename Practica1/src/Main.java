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
		
		Integer i = 1000;
		System.out.println("Sin complemento a 1 = "+i);
		System.out.println(Integer.toHexString(i));
		i = ~i;
		System.out.println("Con complemento a 1 = "+i);
		System.out.println(Integer.toHexString(i));
		int largo =Integer.toHexString(i).length();
		System.out.println(Integer.toHexString(i).substring(largo-2,largo));
	}
	



}
