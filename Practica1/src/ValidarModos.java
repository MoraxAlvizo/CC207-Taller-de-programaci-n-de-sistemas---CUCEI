import java.util.StringTokenizer;


public class ValidarModos {
	
	static Integer errori = -1;
	static Integer errorj = -1;
	
	static Integer isIMM8(String operando){
		
		Integer nodecimal;
		
		if(operando.matches("[#][$]?[@]?[%]?[-]?[A-F0-9]+")){
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando.substring(1));	
				if (nodecimal != null && nodecimal >= -256 && nodecimal <= 255){
					ValidarModos.errori = 4; 
					ValidarModos.errorj = 1; 
					return 1;
				}
				
				else return 0;
				
			}
			catch (Exception e){
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
				if (nodecimal != null && nodecimal >= -32768 && nodecimal <= 65535){
					
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
				if (nodecimal != null && nodecimal >= 0 && nodecimal <= 255){
					
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
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+")){
			try{
				System.out.println(operando.substring(1));
				nodecimal = Automata.cambiarABaseDecimal(operando);
				System.out.println(nodecimal);
				if (nodecimal != null && nodecimal >= -32768 && nodecimal <= 65535){
					 
					return 1;
				}
				
				else if (Automata.analizarEtiquetaEnOperando(operando)!=null){
					return 1;
				}
				
				else{
					if(nodecimal == null){
						ValidarModos.errori = 5; 
						ValidarModos.errorj = 1;
						return 0;
					}
					else{
						ValidarModos.errori = 0; 
						ValidarModos.errorj = 0;
						return 0;
					}
					
				}
				
			}
			catch (Exception e){
				return -1;
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
				if (Automata.validarRegistro(registro)){
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
					if (nodecimal != null && nodecimal >= -16 && nodecimal <= 15){
						if  (Automata.validarRegistro(registro)){
							return 1;
						}
						else{
							ValidarModos.errori = 8; 
							ValidarModos.errorj = 0;
							return 0;
						}
						
					}
					
					else{
						if(nodecimal != null){
							ValidarModos.errori = 5; 
							ValidarModos.errorj = 2;
							return 0;
						}
						else{
							ValidarModos.errori = 8; 
							ValidarModos.errorj = 1;
							return 0;
						}
						
					}
				}		
			}
			catch (Exception e){
				return -1;
			}
		}
		
		else if(operando.matches("[$]?[@]?[%]?[A-Z0-9]+[,][+|-][A-Za-z]+")){
			try{
				idx = new StringTokenizer(operando,",");
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
				registro = idx.nextToken();
				if (nodecimal != null && nodecimal >= 1 && nodecimal <= 8){
					if ( Automata.validarRegistro(registro.substring(1)))
						return 1;
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
						return 0;
					}
				}
					
				else{
					if(nodecimal != null){
						ValidarModos.errori = 5; 
						ValidarModos.errorj = 2;
						return 0;
					}
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 1;
						return 0;
					}
				}
			
			}
			catch (Exception e){
				return -1;
			}
		}
		
		else if(operando.matches("[$]?[@]?[%]?[A-Z0-9]+[,][A-Za-z]+[+|-]")){
			try{
				idx = new StringTokenizer(operando,",");
				nodecimal = Automata.cambiarABaseDecimal(idx.nextToken());
				registro = idx.nextToken();
				if (nodecimal != null && nodecimal >= 1 && nodecimal <= 8){
					if(Automata.validarRegistro(registro.substring(0,registro.length()-1)))
						return 1;
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
						return 0;
					}
				}
					
				else{
					if(nodecimal != null){
						ValidarModos.errori = 5; 
						ValidarModos.errorj = 2;
						return 0;
					}
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 1;
						return 0;
					}
				}
						
			}
			catch (Exception e){
				return -1;
			}
		}
		
		
		
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
				if (nodecimal != null && nodecimal >= -256 && nodecimal <= 255){
					if(Automata.validarRegistro(registro))
						return 1;
					else{
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 0;
						return 0;
					}
				}
					
				else{
					if(nodecimal == null){
						ValidarModos.errori = 8; 
						ValidarModos.errorj = 1;
						return 0;
					}
					else{
						ValidarModos.errori = 6; 
						ValidarModos.errorj = 0;
						return 0;
					}
					
				}
						
			}
			catch (Exception e){
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
				if (nodecimal >= 0 && nodecimal <= 65535 && Automata.validarRegistro(registro)){
					return 1;
				}
					
				else{
					ValidarModos.errori = 6; 
					ValidarModos.errorj = 1;
					return 0;
				}
						
			}
			catch (Exception e){
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
					if (nodecimal >= 0 && nodecimal <= 65535 && Automata.validarRegistro(registro)){
						return 1;
					}
						
					else{
						
						if(nodecimal >= 0 && nodecimal <= 65535){
							ValidarModos.errori = 6; 
							ValidarModos.errorj = 2;
						}
						if (!Automata.validarRegistro(registro)){
							ValidarModos.errori = 8; 
							ValidarModos.errorj = 0;
						}
						
						return 0;
					}
							
				}
				catch (Exception e){
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
					if (Automata.validarRegistro(registro)){
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
		}
		
		
		return 0;
	}
	
	static Integer isREL8(String operando){
		
		Integer nodecimal;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+")){
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando);	
				if (nodecimal != null && nodecimal >= -128 && nodecimal <= 127){
					return 1;
				}
				
				else if(Automata.analizarEtiquetaEnOperando(operando)!=null){
					return 1;
				}
				
				else return 0;
				
			}
			catch (Exception e){
				return -1;
			}
		}
		
		return 0;
	}
	
	static Integer isREL16(String operando){
		
		Integer nodecimal;
		
		if(operando.matches("[$]?[@]?[%]?[-]?[A-Za-z0-9]+")){
			try{
				nodecimal = Automata.cambiarABaseDecimal(operando);	
				if (nodecimal != null && nodecimal >= -32768 && nodecimal <= 65535){
					return 1;
				}
				
				else if(Automata.analizarEtiquetaEnOperando(operando)!=null){
					return 1;
				}
				
				else{
					ValidarModos.errori = 8; 
					ValidarModos.errorj = 1;
					return 0;
				}
				
			}
			catch (Exception e){
				return -1;
			}
		}
		
		return 0;
	}
	
	

}
