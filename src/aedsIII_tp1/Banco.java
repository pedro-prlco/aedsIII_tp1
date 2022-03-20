package aedsIII_tp1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class Banco {
	
	String path;
	RandomAccessFile file;
	
	
	//Construtor de Banco.
	//Uma path e passada como argumento. Esta path ira decidir em qual
	//arquivo serao processadas as leituras e escrituras de registros
	public Banco(String path) 
	{
		this.path = path;
		try {
			
			
			
			//Ao inicializar o banco, uma unica instancia de RandomAccessFile e utilizada durante toda execucao do programa
			//Ela fechada - file.Close() - somente quando o usuario resolver sair do programa - efetuando o comando 6 -
			file = new RandomAccessFile(path, "rw");
			
			
			//Se o arquivo na path estiver vazia, o programa ira inserir um valor zero
			//Este valor ira representar a header inicial. Indicando que nao tem registros
			//efeituados no arquivo ainda.
			if(file.length() == 0)
			{
				WriteInt(0, 0);
			}
			else 
			{
				//Notificando ao usuario a quantidade de registros no arquivo carregado
				System.out.println("Arquivo carregado com " + ReadHeader() + " registros");
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//Funcao responsavel por criar uma nova conta com os dados passados nos argumentose registrar no arquivo.
	//Uma instancia de ContaBancaria e criada, e a partir dela e obtida um valor em bytes[] contendo os dados da conta a ser 
	//registrada no arquivo.
	public void CreateAccount(String nomePessoa, String cpf, String estado, int saldo, int transferencias) throws IOException 
	{
		
		
		//Lendo o valor da header atual
		int id = ReadHeader();
		
		
		//Criando uma instancia de ContaBancaria e inicializando os valores dela
		ContaBancaria novaConta = new ContaBancaria(0, nomePessoa, cpf, estado);
		novaConta.saldoConta = saldo;
		novaConta.transferenciasRealizadas = transferencias;
		
		
		//Pegando todos os valores nao inteiros da instancia. 
		byte[] contaBytes = novaConta.toByteArray();
		
		
		
		//PROCESSO DE ESCRITURA NO ARQUIVO
		
		//Primeiro escrever no arquivo a id da conta
		WriteInt(id, (int)file.length());
		
		//Depois escrever no arquivo o tamanho do registro em bytes + 8 bytes que são dos valores inteiros: Saldo e transferencias
		WriteByte(novaConta.toString().length()+8, (int)file.length());
		
		//Escrevendo no arquivo os dados não inteiros da nova conta: Lapide, Nome, CPF e Cidade.
		Write(contaBytes, contaBytes.length, (int)file.length());
		
		//Por fim, registrando os dados inteiros da nova conta.
		WriteInt(novaConta.saldoConta, (int)file.length());
		WriteInt(novaConta.transferenciasRealizadas, (int)file.length());
		
		//Alterando o valor do contador de elementos registrados
		AttHeader(++id);
		
		
		//Estrutura do registro
		//[4 bytes]   [4 bytes]    [     1 byte     ]   [1 byte]      [     variavel      ]   [            8 bytes              ]
		//[HEADER ]   [id     ]    [tamanho em bytes]   [lapide]      [nome + cpf + estado]   [saldo e transferencias realizadas]
		
	}
	
	
	
	
	//Funcao responsavel por percorrer os registros no arquivo ate encontrar a ID desejada
	//Se nenhuma conta for encontrada: Retorna null
	//Se a id e encontrada: Retorna uma instancia de ContaBancaria contendo os valores no registro
	public ContaBancaria ReadAccount(int id) throws IOException 
	{
		//Definindo a posicao do ponteiro na posicao 4, ignorado os 4 primeiros bytes que representam a header
		file.seek(4);
		
		
		//Processo de varredura no arquivo.
		//O programa ira procurar a id desejada ate chegar no limite do arquivo
		while(file.getFilePointer() < file.length()) 
		{
			
			//guardando a posicao atual do ponteiro na variavel pointer
			int pointer = (int)file.getFilePointer();
			
			//Lendo a id registrada no arquivo atraves da funcao ReadInt()
			int currentId = ReadInt(pointer);
			
			
			//Se a id lida nao for igual a id desejada entao um processo de offset sera realizado, ignorando todos os dados registrados dessa currentId
			if(currentId != id)
			{
				
				
				//Lendo o byte que indica o tamanho do registro
				byte registerSize = ReadByte();
				
				
				//Movendo o ponteiro para o final deste registro.
				//Repetindo entao o processo ate encontrar a id desejada
				int newPointerPosition = (int) (file.getFilePointer() + registerSize);
				file.seek(newPointerPosition);
			}
			else //Se a id lida for igual a id desejada entao um processo de conversao de string para ContaBancaria sera realizada
			{
				
				//Lendo o byte que indica o tamanho do registro
				byte registerSize = ReadByte();
				
				//Lendo os dados nao inteiros deste registro. registerSize - 8 bytes
				byte[] noIntData = Read((int)file.getFilePointer(), registerSize-8);
				
				
				//Como a estrutura do registro mantem todos os seus valores inteiros no final.
				//Entao a leitura dos valores de saldo e transferencias realizadas e feita atraves da funcao ReadInt
				int saldo = ReadInt((int)file.getFilePointer());
				int transferenciasRealizadas = ReadInt((int)file.getFilePointer());
				
				
				//Convertendo todos os dados nao inteiros em uma String result
				String result = new String(noIntData, StandardCharsets.UTF_8);
				
				//Retornando uma nova instancia de ContaBancaria atraves da string formada:
				//idConta;result;saldo;transferenciasRealizadas
				//Eg. 0;pedro lourenco;111111111111;100;0
				return ContaBancaria.Parse(id+";"+result+";"+saldo+";"+transferenciasRealizadas);
			}
		}
		
		//Se nao for encontrado nenhuma conta entao retorne nulo
		return null;
	}
	
	//Funcao responsavel por desativar a conta.
	public void DeactivateAccount(int id) throws IOException 
	{
		//Le os dados da conta que deseja desativar
		ContaBancaria conta = ReadAccount(id);
		//A conta entao e desativada atraves da funcao Kill(), alterando sua lapide
		conta.Kill();
		//Por fim, conta e sobrescrita no arquivo com esse novo valor de sua lapide
		OverwriteAccount(id, conta.nomePessoa, conta.cpf, conta.cidade, conta.saldoConta, conta.transferenciasRealizadas, conta.isDead);
	}
	
	//Funcao responsavel por retornar a posicao do ponteiro sobre uma conta atraves de sua ID
	public int GetPointerPositionByAccountId(int id) throws IOException 
	{
		
		file.seek(4);
		while(file.getFilePointer() < file.length()) 
		{
			int pointer = (int)file.getFilePointer();
			int currentId = ReadInt(pointer);
			if(currentId != id) 
			{
				byte registerSize = ReadByte();
				
				int newPointerPosition = (int) (file.getFilePointer() + registerSize + 8);
				
				file.seek(newPointerPosition);
			}
			else 
			{
				return pointer;
			}
		}
		
		return -1;
	}
	
	//Fecha o RandomAccess file
	public void Close() throws IOException 
	{
		file.close();
	}
	
	void OverwriteAccount(int id, String nome, String cpf, String estado, int saldo, int transferencias, boolean isDead) throws IOException 
	{
		ContaBancaria conta = ReadAccount(id);
		
		conta.nomePessoa = nome;
		conta.cpf = cpf;
		conta.cidade = estado;
		conta.saldoConta = saldo;
		conta.transferenciasRealizadas = transferencias;
		conta.isDead = isDead;
		
		int contaPosition = GetPointerPositionByAccountId(id);
		
		if(contaPosition < 0) 
		{
			return;
		}
		
		byte[] contaBytes = conta.toByteArray();
		
		//Primeiro registrar a id da conta
		WriteInt(id, contaPosition);
		contaPosition += 4;
		
		//Depois registrar o tamanho do registro em bytes + 8 bytes que dos valores inteiros presentes na conta - Saldo e transferencias
		WriteByte(conta.toString().length() + 8, contaPosition);
		contaPosition += 1;
		
		//Registrando os dados nao inteiros da conta
		Write(contaBytes, contaBytes.length, contaPosition);
		contaPosition += contaBytes.length;
		//Por fim, registrar os dados inteiros da conta
		WriteInt(conta.saldoConta, (int)file.getFilePointer());
		WriteInt(conta.transferenciasRealizadas, (int)file.getFilePointer());
	}
	
	//Funcao responsavel ler um inteiro registrado no arquivo.
	//Esta funcao recebe um inteiro como parametro que indica a posicao inicial de leitura do ponteiro
	int ReadInt(int number) throws IOException 
	{
		//retorna os bytes lidos no arquivo convertivos como inteiro. Seguindo os passos
		//1. Le 4 bytes registrados no arquivo atraves da funcao read.
		//2. java.nio.ByteBuffer.wrap().getInt() => serve para converter os 4 bytes lidos pela funcao Read em inteiro.
		return java.nio.ByteBuffer.wrap(Read(number, 4)).getInt();
	}
	
	
	//Funcao responsavel por ler a heade presente no arquivo.
	//Ele apenas retorna um valor inteiro retornada pela funcao ReadInt definida acima
	int ReadHeader() throws IOException 
	{
		return ReadInt(0);
	}
	
	//Funcao responsavel por atualizar o valor da Header 
	//para "novoValor"
	void AttHeader(int novoValor) throws IOException 
	{
		//Funcao chamada que serve para escrever um inteiro no arquivo
		WriteInt(novoValor, 0);
	}
	
	//Funcao base de leitura no arquivo. Nele recebe uma posicao inicial de leitura e o tamanho em bytes que o ponteiro devera andar para realizar
	//esta leitura.
	byte[] Read(int startPos, int size) throws IOException 
	{
		file.seek(startPos);
		byte[] readData = new byte[size];
		file.read(readData, 0, size);
		return readData;
	}
	
	//Funcao base de leitura no arquivo. Diferentemente da funcao Read acima, este serve apenas para ler um byte no registro.
	byte ReadByte() throws IOException 
	{
		return file.readByte();
	}
	
	//Funcao responsavel por escrever um inteiro no arquivo.
	//Ele recebe dois parametros.
	//value => valor inteiro que deseja ser escrito no arquivo
	//l => posicao inicial do pointeiro na hora de efetuar a escritura
	void WriteInt(int value, int l) throws IOException 
	{
		//Antes de efetuar a escritura, e feita uma conversao do numero inteiro em seus respectivos 4 bytes.
		byte[] data = java.nio.ByteBuffer.allocate(4).putInt(value).array();
		//Depois de coletar os bytes do inteiro. Utiliza-se a funcao Write
		Write(data, 4, l);
	}
	
	//Funcao responsavel por escrever uma sequencia de bytes no arquivo
	//value => Os dados que irao ser escritos no arquivo
	//size => tamanho dos dados em bytes que serao escritos no arquivo
	//position => posicao inicial do ponteiro na hora de escrever no arquivo
	void Write(byte[] value, int size, int position) throws IOException 
	{
		file.seek(position);
		file.write(value, 0, size);
	}
	
	//Funcao responsavel por escrever apenas um byte no arquivo
	void WriteByte(int value, int position) throws IOException 
	{
		file.seek(position);
		file.writeByte(value);
	}

}
