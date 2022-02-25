
	/*
	 * Uma classe responsável por conter os comandos de CRUD. Essa classe vai se chamar Banco. Ao ser instanciada, sera passado como argumento
	 * um caminho onde o arquivo foi criado - Logo, irár ser inicializado ja com a leitura de todos os dados registrados -.
	 * 
	 * Para cada alteracao, o Objecto banco irá registrar no arquivo e re-ler o arquivo para att o Objeto.
	 * 
	 * classe Banco:
	 * 		- constructor ( caminho do arquivo )
	 * 
	 * 		- contaBancaria[] contasColetadas
	 *	
	 *		- CriarConta
	 *		- Efetuar Transferencia
	 *		- Apagar conta
	 *
	 * */

package aedsIII_tp1;

import java.util.ArrayList;

public class Banco {
	
	String path;
	ArrayList<ContaBancaria> contas;
	
	public Banco(String path) 
	{
		this.path = path;
		contas = new ArrayList<ContaBancaria>();
	}
	
	public void CriarConta(String nomePessoa, String cpf, String estado) 
	{
		//criar uma conta
	}
	
	public void EfetuarTransferencia(int contaId1, int contaId2, float valor) 
	{
		//efetuar transferencia
	}
	
	public void ApagarConta(int idConta) 
	{
		//Apagar conta
	}
	
	void LerDados() 
	{
		//Ler os dados e att a array de contas
	}
	
	void RegistrarDados() 
	{
		//Pegar as entidades e registra-las no arquivo em path
	}
}
