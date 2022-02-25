package aedsIII_tp1;


// Classe responsavel por conter os campos registrados no arquivo.
public class ContaBancaria {
	
	int idConta;
	boolean desativado;
	String nomePessoa;
	String cpf;
	String cidade;
	int transferenciasRealizadas;
	float saldoConta;
	
	//Instancia de um novo objeto de ContaBancaria
	public ContaBancaria(int idConta, String nome, String cpf, String cidade) 
	{
		this.idConta = idConta;
		this.cpf = cpf;
		nomePessoa = nome;
		this.cidade = cidade;
		transferenciasRealizadas = 0;
		saldoConta = 0;
		desativado = false;
	}
	
	//Responsavel por somar o valor passado no argumento ao saldo da conta.
	//Se desejar aumentar o saldo coloque um valor positivo ( > 0 )
	//Se desejar diminuir o saldo coloque um valor negativo ( < 0 )
	public float AddSaldo(float valor) 
	{
		saldoConta += valor;
		return saldoConta;
	}
}
