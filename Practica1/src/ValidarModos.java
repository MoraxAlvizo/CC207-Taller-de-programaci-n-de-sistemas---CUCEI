import java.util.StringTokenizer;


public class ValidarModos {
	
	static Integer errori = -1;
	static Integer errorj = -1;
	
	static Integer isIMM8(String operando){
		
		Integer nodecimal;
		
		if(operando.matches("[#][$]?[@]?[%]?[-]?[A-F0-9]+")){
			
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando.substring(1));
	
				if (Automata.validarNumero(nodecimal,8,255,-256)){		
					return 1;
				}
				else{
					ValidarModos.errori = 4; 
					ValidarModos.errorj = 1; 
					
					return 0;
				}
			}catch (Exception e){
				return -1;
			}
		}
		
		return 0;
	}
	
	static Integer isIMM16(String operando){
		
		Integer nodecimal;
		
		if(operando.matches("[#][$]?[@]?[%]?[-]?[A-F0-9]+")){
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando.substring(1));
	
				if (Automata.validarNumero(nodecimal,16,65535,-32768)){		
					return 1;
				}
				else{
					ValidarModos.errori = 4; 
					ValidarModos.errorj = 2; 
					
					return 0;
				}
				
				
			}
			catch (Exception e){
				return -1;
			}
		}
		
		return 0;
	}
	
	static Integer isDIR(String operando){
		
		Integer nodecimal;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-F0-9]+")){
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando);
	
				if (Automata.validarNumero(nodecimal,8,255,0)){		
					return 1;
				}
				else{
					ValidarModos.errori = 5; 
					ValidarModos.errorj = 0; 
					
					return 0;
				}
				
			}
			catch (Exception e){
				return -1;
			}
		}
		
		return 0;
	}
	
	static Integer isEXT(String operando){
		
		Integer nodecimal;
		String etiqueta;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+")){
			try{
				
				nodecimal = Automata.cambiarABaseDecimal(operando);
	
				if (Automata.validarNumero(nodecimal,16,65535,-32768)){		
					return 1;
				}
				else{
					ValidarModos.errori = 5; 
					ValidarModos.errorj = 1; 
					
					return 0;
				}
				
			}
			catch (Exception e){
				if((etiqueta = Automata.analizarEtiquetaEnOperando(operando)) != null){
					return 1;
				}
				
				if (etiqueta == null){
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 2; 
				}
				
				return 0;
			}
		}
		return 0;
	}
	
	static Integer isIDX(String operando){
		
		Integer nodecimal;
		StringTokenizer idx;
		String registro;
		String acumulador;
		
		if(operando.matches("[A|B|C|D][,][A-Za-z]+")){
			try{
				idx = new StringTokenizer(operando,",");
				acumulador = idx.nextToken();
				registro = idx.nextToken();
				if (Automata.validarRegistro(registro) && acumulador != null){
					return 1;
				}
					
				else{
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 0;
					return 0;
				}
						
			}
			catch (Exception e){
				return -1;
			}
		}
		
		else if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]*[,][A-Za-z]+")){
			try{
				if(operando.charAt(0)==','){
					if (Automata.validarRegistro(operando.substring(1))){
						return 1;
					}
					
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
						return 0;
					}
					
				}
				else{
					idx = new StringTokenizer(operando,",");
					nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
					registro = idx.nextToken();
					
					boolean banderaN;
					if ((banderaN =Automata.validarNumero(nodecimal,4,15,-16)) && Automata.validarRegistro(registro)){		
						return 1;
					}
					else{
						if(!banderaN){
							ValidarModos.errori = 5; 
							ValidarModos.errorj = 2; 
						}
						else{
							ValidarModos.errori = 8; 
							ValidarModos.errorj = 0;
						}
						
						return 0;
					}
					
				
					
				}		
			}
			catch (Exception e){
				return -1;
			}
		}
		
		else if(operando.matches("[$]?[@]?[%]?[-]?[A-Z0-9]+[,][+|-][A-Za-z]+")){
			try{
				idx = new StringTokenizer(operando,",");
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
				registro = idx.nextToken();
				
				boolean banderaN;
				if ((banderaN =Automata.validarNumero(nodecimal,5,8,1)) &&  Automata.validarRegistro(registro.substring(1))){		
					return 1;
				}
				else{
					if(!banderaN){
						ValidarModos.errori = 5; 
						ValidarModos.errorj = 2; 
					}
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
					}
					
					return 0;
				}		
			}
			catch (Exception e){
				ValidarModos.errori = 8; 
				ValidarModos.errorj = 1;
				return -1;
			}
		}// fin del pre
		
		else if(operando.matches("[$]?[@]?[%]?[-]?[A-Z0-9]+[,][A-Za-z]+[+|-]")){
			try{
				idx = new StringTokenizer(operando,",");
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
				registro = idx.nextToken();
				
				boolean banderaN;
				if ((banderaN =Automata.validarNumero(nodecimal,4,8,1)) &&  Automata.validarRegistro(registro.substring(0,registro.length()-1))){		
					return 1;
				}
				else{
					if(!banderaN){
						ValidarModos.errori = 5; 
						ValidarModos.errorj = 2; 
					}
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
					}
					
					return 0;
				}		
						
			}
			catch (Exception e){
				ValidarModos.errori = 8; 
				ValidarModos.errorj = 1;
				return -1;
			}
		}// fin del post
		
		
		
		return 0;
	}
	
	static Integer isIDX1(String operando){
		
		Integer nodecimal;
		StringTokenizer idx;
		String registro;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+[,][A-Za-z]+")){
			try{
				idx = new StringTokenizer(operando,",");
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
				registro = idx.nextToken();
				
				boolean banderaN;
				if ((banderaN =Automata.validarNumero(nodecimal,8,255,-256)) &&  Automata.validarRegistro(registro)){		
					return 1;
				}
				else{
					if(!banderaN){
						ValidarModos.errori = 6; 
						ValidarModos.errorj = 0; 
					}
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
					}
					
					return 0;
				}		
						
			}
			catch (Exception e){
				ValidarModos.errori = 8; 
				ValidarModos.errorj = 1;
				return -1;
			}
		}
		
		return 0;
	}
	
	static Integer isIDX2(String operando){
		
		Integer nodecimal;
		StringTokenizer idx;
		String registro;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+[,][A-Za-z]+")){
			try{
				idx = new StringTokenizer(operando,",");
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
				registro = idx.nextToken();
				boolean banderaN;
				
				if ((banderaN =Automata.validarNumero(nodecimal,16,65535,0)) &&  Automata.validarRegistro(registro)){		
					return 1;
				}
				else{
					if(!banderaN){
						ValidarModos.errori = 6; 
						ValidarModos.errorj = 1; 
					}
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
					}
					
					return 0;
				}		
						
			}
			catch (Exception e){
				ValidarModos.errori = 8; 
				ValidarModos.errorj = 1;
				return -1;
			}
		}
		
		return 0;
	}
	
	static Integer isINDIRECTOIDX2(String operando){
		
		Integer nodecimal;
		StringTokenizer idx;
		String registro;
		
		if(operando.charAt(0)=='['&&operando.charAt(operando.length()-1)==']'){
			operando = operando.substring(1, operando.length()-1);
			if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+[,][A-Za-z]+")){
				try{
					
					idx = new StringTokenizer(operando,",");
					nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
					registro = idx.nextToken();
					boolean banderaN;
					
					if ((banderaN =Automata.validarNumero(nodecimal,16,65535,0)) &&  Automata.validarRegistro(registro)){		
						return 1;
					}
					else{
						if(!banderaN){
							ValidarModos.errori = 6; 
							ValidarModos.errorj = 2; 
						}
						else{
							ValidarModos.errori = 8; 
							ValidarModos.errorj = 0;
						}
						
						return 0;
					}	
							
				}
				catch (Exception e){
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 1;
					return -1;
				}
			}
		}
		
		
		return 0;
	}
	
	static Integer isDIDX(String operando){
		
		String acumulador;
		StringTokenizer idx;
		String registro;
		
		if(operando.charAt(0)=='[' && operando.charAt(operando.length()-1)==']'){
			operando = operando.substring(1, operando.length()-1);
			if(operando.matches("[D]+[,][A-Za-z]+")){
				try{
					idx = new StringTokenizer(operando,",");
					acumulador = idx.nextToken();
					registro = idx.nextToken();
					if (Automata.validarRegistro(registro) && acumulador.compareTo("D")==0){
						return 1;
					}
						
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
						return 0;
					}
							
				}
				catch (Exception e){
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 1;
					return -1;
				}
			}
		}
		
		
		return 0;
	}
	
	static Integer isREL8(String operando){
		
		
		Integer nodecimal;
		String etiqueta;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-F0-9]+")){
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando);
	
				if (Automata.validarNumero(nodecimal,8,255,0)){		
					return 1;
				}
				else{
					ValidarModos.errori = 7; 
					ValidarModos.errorj = 1; 
					
					return 0;
				}
				
			}
			catch (Exception e){
				if((etiqueta = Automata.analizarEtiquetaEnOperando(operando)) != null){
					return 1;
				}
				
				if (etiqueta == null){
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 2; 
				}
				
				return 0;
			}
		}
		
		return 0;
	}
	
	static Integer isREL16(String operando){
		Integer nodecimal;
		String etiqueta;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+")){
			try{			
				nodecimal = Automata.cambiarABaseDecimal(operando);

				if (Automata.validarNumero(nodecimal,16,65535,-32768)){		
					return 1;
				}
				else{
					ValidarModos.errori = 7; 
					ValidarModos.errorj = 2; 
					
					return 0;
				}
				
			}
			catch (Exception e){
				if((etiqueta = Automata.analizarEtiquetaEnOperando(operando)) != null){
					return 1;
				}
				
				if (etiqueta == null){
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 2; 
				}
				
				return 0;
			}
		}
		return 0;
	}
	
	

}
