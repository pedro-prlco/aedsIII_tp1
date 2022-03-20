package aedsIII_tp1;

// Classe responsavel por conter os campos registrados no arquivo.
public class ContaBancaria {
	
	public int idConta;
	public String nomePessoa;
	public String cpf;
	public String cidade;
	public int transferenciasRealizadas;
	public int saldoConta;
	public boolean isDead;
	
	//Instancia de um novo objeto de ContaBancaria
	public ContaBancaria(int idConta, String nome, String cpf, String cidade) 
	{
		this.idConta = idConta;
		this.cpf = cpf;
		nomePessoa = nome;
		this.cidade = cidade;
		transferenciasRealizadas = 0;
		saldoConta = 0;
		isDead = false;
	}
	
	//Retorna todos os dados do usuario em uma string. Ingnorando os valores inteiros saldoConta e transferenciasRealizadas
	public String toString() 
	{
		String dados =  nomePessoa + ";" + cpf + ";"+ cidade;
		String lapide = isDead ? "*;" : "a;";
		dados = lapide + dados;
		return  dados;
	}
	
	//Retorna o tamnho em bytes deste objeto
	public int ByteLength()
	{
		return toString().getBytes().length;
	}
	
	//Converte este objeto em uma sequencia de bytes
	public byte[] toByteArray() 
	{
		return toString().getBytes();
	}
	
	//Ativa isDead para true. Na hora de registrar no arquivo, a lipide sera marcada com *
	public void Kill() 
	{
		isDead = true;
	}
	
	//Converte uma string em uma nova ContaBancaria.
	public static ContaBancaria Parse(String data) 
	{
		String[] splittedData = data.split(";");
		int parsedId = Integer.parseInt(splittedData[0]);
		String parsedName = splittedData[2];
		String parsedCPF = splittedData[3];
		String uf = splittedData[4];
		int parsedSaldo = Integer.parseInt(splittedData[5]);
		int parsedTransferenciasRealizadas = Integer.parseInt(splittedData[6]);
	
		ContaBancaria parsedConta = new ContaBancaria(parsedId, parsedName, parsedCPF, uf);
		parsedConta.saldoConta = parsedSaldo;
		parsedConta.transferenciasRealizadas = parsedTransferenciasRealizadas;
		
		if(splittedData[1].charAt(0) == '*') 
		{
			parsedConta.Kill();
		}
		
		return parsedConta;
	}
}
