package cas.acesso.dominio;

public enum TipoUsuario {
	
	ADMINISTRADOR, COORDENADOR, SECRETARIO;
	
	public static TipoUsuario get(int i){
		switch (i) {
		case 0: return ADMINISTRADOR; 
		case 1: return COORDENADOR;
		case 2: return SECRETARIO;	
		default:return null;
		}
	}
}