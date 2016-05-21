package cas.comum.dominio;

public enum DiaSemana {
	
	DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO;
	
	public static DiaSemana get(int i){
		switch (i) {
		case 0: return DOMINGO; 
		case 1: return SEGUNDA;
		case 2: return TERCA;
		case 3: return QUARTA;
		case 4: return QUINTA;
		case 5: return SEXTA;
		case 6: return SABADO;		
		default:return null;
		}
	}
}