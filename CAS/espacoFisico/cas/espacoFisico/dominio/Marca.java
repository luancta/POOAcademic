package cas.espacoFisico.dominio;

public enum Marca {

	BENKIN, EPSON, SONY, PANASONIC, PHILLIPS, SEM_MARCA;
	
	public static Marca getMarca(int marca){
		
		switch (marca) {
		case 0:
			return BENKIN;
		case 1:
			return EPSON;
		case 2:
			return SONY;
		case 3:
			return PANASONIC;
		case 4:
			return PHILLIPS;
		default:
			return SEM_MARCA;
		}
	}
}
