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
	
	//Instancia de um nPovo objeto de ContaBancaria
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
	
	public String toString() 
	{
		String dados =  nomePessoa + ";" + cpf + ";"+ cidade;
		String lapide = isDead ? "*;" : "a;";
		dados = lapide + dados;
		return  dados;
	}
	
	public int ByteLength()
	{
		return toString().getBytes().length;
	}
	
	public byte[] toByteArray() 
	{
		return toString().getBytes();
	}
	
	public void Kill() 
	{
		isDead = true;
	}
	
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
