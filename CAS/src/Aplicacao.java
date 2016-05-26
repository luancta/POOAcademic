import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cas.comum.dao.TurnoDAO;
import cas.comum.dominio.Turno;
import cas.util.util.BancoUtil;
import cas.util.util.ConexaoUtil;

public class Aplicacao {

	public static void main(String[] args) {
		
		/*ConexaoUtil con = new ConexaoUtil();
        con.setBanco("CasDB");
		boolean existeBD = false;
		 existeBD = con.verificarBanco();
        if(!existeBD){ 
			ConexaoUtil.createDB();
	        BancoUtil bd = new BancoUtil();
	        bd.CriarTabelas();
        }*/
		
		TurnoDAO turnDao = new TurnoDAO();
		Scanner entradaDescricao = new Scanner(System.in);
		
		System.out.println("Digite o turno a ser buscado");
		
		String descricao = entradaDescricao.nextLine();
		
		List<Turno> turnos = new ArrayList<Turno>();
		turnos = turnDao.findTurnobyDescricao(descricao);
		
		
		
		if(turnos != null && !turnos.isEmpty()){
			System.out.println("Os turnos encontratos foram: ");
			for(Turno tur : turnos){
				System.out.println(tur);
			}
			entradaDescricao.close();
		}else{
			System.out.println("Nenhum Registro Encontrado");
		}
			
	}
}
