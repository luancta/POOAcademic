package cas.util.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author gilley
 */
public class BancoUtil {

    Connection conexao = null;

    public void CriarTabelas() {
        ConexaoUtil con = new ConexaoUtil();
        try {
            if (conexao == null) {
                conexao = con.getConexao();
            }
            java.sql.Statement stm = conexao.createStatement();

            String sqlCreate1 =  Schemas()+
            					TabelaTurno() +
            					TabelaPessoa() +
            					TabelaDisciplina() +
            					TabelaTurma() +
            					TabelaAplicativo() +
            					TabelaLaboratorio() +
            					TabelaProjetor() +
            					TabelaSala() ;

            String sqlCreate2 = TabelaDocente() +
								TabelaEstudante() +
								TabelaHorario() +
            					TabelaLocalAula()+
            					TabelaUsuario() +
            					TabelaLaboratorioAplicativo() + 
            					TabelaReserva();
            
            String sqlDados = PopulaDados();
						
            
            stm.executeQuery(sqlCreate1);
            stm.executeQuery(sqlCreate2);
            stm.executeQuery(sqlDados);

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e);
            e.printStackTrace();
        }
    }
    
    private String Schemas(){
    	return " CREATE schema acesso;\n " +
    			" CREATE schema comum;\n " +
    			" CREATE schema ensino;\n " +
    			" CREATE schema espaco_fisico;\n ";
    			
    }
    
    private String TabelaTurno(){
    	return "CREATE TABLE comum.turno ( id_turno integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1), descricao character varying (255), CONSTRAINT pk_TurnoID PRIMARY KEY (id_turno));\n";
    }
   
    private String TabelaPessoa(){
    	return 
    	" CREATE TABLE comum.pessoa " +
    	" (" +
    	" id_pessoa integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1)," +
    	" nome character varying (255)," +
    	" data_nascimento date," +
    	" cpf character varying (15)," +
    	" registro_geral character varying (20)," +
    	" nome_mae character varying (255)," +
    	" nome_pai character varying (255)," +
    	" endereco character varying (255)," +
    	" bairro character varying (255)," +
    	" cep character varying (9)," +
    	" CONSTRAINT pk_PessoaID PRIMARY KEY (id_pessoa)" +
    	" );" +
		"\n";
    }
   
    private String TabelaDisciplina(){
    	return
    	" CREATE TABLE ensino.disciplina" +
    	" (" +
    	" id_disciplina integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1)," +
    	" nome character varying (255)," +
    	" sigla character varying (30)," +
    	" CONSTRAINT pk_DisciplinaID PRIMARY KEY (id_disciplina)" +
    	" );" +
    	"\n";

    }
    
    private String TabelaDocente(){
    	return    	
        " CREATE TABLE ensino.docente" +
        " (" +
        " id_docente integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1)," +
        " matricula character varying (30)," +
        " id_pessoa integer," +
        " CONSTRAINT pk_DocenteID PRIMARY KEY (id_docente)," +
        " FOREIGN KEY (id_pessoa) REFERENCES comum.pessoa(id_pessoa)" +
        " );" +
        "\n";
    }
    
    private String TabelaEstudante(){
    	return    	
		" CREATE TABLE ensino.estudante" +
		" (" +
		" id_estudante integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1), " + 
		" matricula character varying (30)," +
		" id_pessoa integer," +
		" curso character varying (255)," +
		" CONSTRAINT pk_EstudanteID PRIMARY KEY (id_estudante)," +
		" FOREIGN KEY (id_pessoa) REFERENCES comum.pessoa(id_pessoa)" +
		" );" +
		"\n";
    }
    
    private String TabelaHorario(){
    	return    	
	    " CREATE TABLE ensino.horario" +
	    " (" +
	    " id_horario integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1)," +
	    " id_turno integer," +
	    " hora_inicio timestamp without time zone," +
	    " hora_time  timestamp without time zone," +
	    " dia_semana integer," +
	    " curso character varying (255)," +
	    " CONSTRAINT pk_HorarioID PRIMARY KEY (id_horario)," +
	    " FOREIGN KEY (id_turno) REFERENCES comum.turno(id_turno)" +
	    " );" +
		"\n";
    }
    
    private String TabelaTurma(){
    	return    	
    	" CREATE TABLE ensino.turma" +
	    " (" +
	    " id_turma integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " codigo character varying (255)," +
	    " CONSTRAINT pk_TurmaID PRIMARY KEY (id_turma)," +
	    " );" +
		"\n";
    }
    
    private String TabelaUsuario(){
    	return 
		"CREATE TABLE acesso.usuario " + 
		"( " + 
		" id_usuario integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 2, INCREMENT BY 1), " + 
		" login character varying (255), " + 
		" senha character varying (255), " + 
		" tipo_usuario integer, " + 
		" id_pessoa integer not null, " +
		" CONSTRAINT pk_UsuarioID PRIMARY KEY (id_usuario), " + 
		" FOREIGN KEY (id_pessoa) REFERENCES comum.pessoa(id_pessoa)" +
		" );" +
		"\n ";
    }
    
    private String TabelaAplicativo(){
    	return     
    	" CREATE TABLE espaco_fisico.aplicativo" +
	    " (" +
	    " id_aplicativo integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " nome character varying (255)," +
	    " CONSTRAINT pk_AplicativoID PRIMARY KEY (id_aplicativo)," +
	    " );" + 
		" \n ";
    }
    
    private String TabelaLaboratorio(){
    	return     
	    " CREATE TABLE espaco_fisico.laboratorio" +
	    " (" +
	    " id_laboratorio integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " nome character varying (255)," +
	    " CONSTRAINT pk_LaboratorioID PRIMARY KEY (id_laboratorio)" +
	    " );" +
		" \n ";
    }
    
    private String TabelaLaboratorioAplicativo(){
    	return     
	    " CREATE TABLE espaco_fisico.laboratorio_aplicativo" +
	    " (" +
	    " id_laboratorio_aplicativo integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " id_laboratorio integer," +
	    " id_aplicativo integer," +
	    " CONSTRAINT pk_Laboratorio_aplicativo_ID PRIMARY KEY (id_laboratorio_aplicativo)," +
	    " FOREIGN KEY (id_laboratorio) REFERENCES espaco_fisico.laboratorio(id_laboratorio), " +
	    " FOREIGN KEY (id_aplicativo) REFERENCES espaco_fisico.aplicativo(id_aplicativo) " +
	    " );" +
		" \n ";
    }
    
    private String TabelaProjetor(){
    	return         
	    " CREATE TABLE espaco_fisico.projetor" +
	    " (" +
	    " id_projetor integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " marca integer," +
	    " tombo character varying (255)," +
	    " CONSTRAINT pk_ProjetorID PRIMARY KEY (id_projetor)" +
	    " );" +
		" \n ";
    }
    
    private String TabelaSala(){
    	return         
	    " CREATE TABLE espaco_fisico.sala" +
	    " (" +
	    " id_sala integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " numero character varying (255)," +
	    " CONSTRAINT pk_SalaID PRIMARY KEY (id_sala)" +
	    " );" +
		" \n ";
    }
    
    private String TabelaLocalAula(){
    	return         
	    " CREATE TABLE espaco_fisico.local_aula" +
	    " (" +
	    " id_local_aula integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " id_laboratorio integer," +
	    " id_sala integer," +
	    " bloco character varying (255)," +
	    " capacidade integer," +
	    " CONSTRAINT pk_LocalAulaID PRIMARY KEY (id_local_aula)," +
	    " FOREIGN KEY (id_laboratorio) REFERENCES espaco_fisico.laboratorio(id_laboratorio)," +
	    " FOREIGN KEY (id_sala) REFERENCES espaco_fisico.sala(id_sala)" +
	    " );" +
		" \n ";
    }
    
    private String TabelaReserva(){
    	return         
	    " CREATE TABLE espaco_fisico.reserva" +
	    " (" +
	    " id_reserva integer not null GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
	    " usa_projetor boolean," +
	    " ativo boolean," +
	    " data Date," +
	    " id_turma integer," +
	    " id_local_aula integer," +
	    " CONSTRAINT pk_ReservaAulaID PRIMARY KEY (id_reserva)," +
	    " FOREIGN KEY (id_turma) REFERENCES ensino.turma(id_turma)," +
	    " FOREIGN KEY (id_local_aula) REFERENCES espaco_fisico.local_aula(id_local_aula)" +
	    ");" +
		" \n ";
    }
   
    /* ONLY DESENVOLVIMENTO */
    private String PopulaDados(){
    	return 
    	" INSERT INTO comum.pessoa (id_pessoa, nome, cpf, registro_geral, nome_mae, nome_pai, endereco, bairro, cep, data_nascimento) " + 
    	" VALUES (1, 'Jo�o Eduardo Fernandes', '480.423.232-04', '1432256', 'Ana Maria Fernandes', 'Miguel Fernandes', 'Av. Miguel Castro, 616', 'Lagoa Nova', '59022020', '1990-05-30');\n "  +
    	
    	" INSERT INTO acesso.usuario (id_usuario, login, senha, tipo_usuario, id_pessoa) " + 
    	" VALUES (1, 'admin', '1', 1, 1);\n " + 
    	
		" INSERT INTO espaco_fisico.sala (numero) VALUES ('N�o Informado'); " +

    	" INSERT INTO espaco_fisico.laboratorio (nome) VALUES ('N�o Informado'); "; 
    	
    	
    }

}
