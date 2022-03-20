//OS COMANDOS RODAM NA CLASSE MAIN.
//O PROGRAMA APRESENTA UMA CLASSE ContaBancaria, ESTA CLASSE REPRESENTA O REGISTRO NO ARQUIVO
//PARA CADA OPERACAO REALIZADA NO REGISTRO, ANTES E RETORNADA UMA INSTANCIA DESTA CLASSE PARA QUE SEUS VALORES POSSAM
//SER ALTERADOS, E DEPOIS, NO MESMO FRAME DE EXECUCAO A INSTANCIA E SOBRESCRITA OU CRIADA NO REGISTRO.
//A CLASSE BANCO E RESPONSAVEL POR EFETUAR COMANDOS DE LEITURA E ESCRITURA DE DADOS NO ARQUIVO

package aedsIII_tp1;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner scan = new Scanner(System.in);
		int opcaoEscolhida = 0;

		// variaveis que podem ser passados como parametro
		int id1, id2, id3;
		String nomePessoa, cpf, estado;
		
		
		//Inicializando Banco.
		//Uma path e passada como parametro para definir qual arquivo o processo de leitura e escritura
		//sera realizada.
		Banco banco = new Banco("teste.txt");
		
		
		
		//do-while que ira rodar enquanto a opcao escolhida nao for igual a 6
		do{
			// Menu de acoes
			System.out.println("Menu de acoes: ");
			System.out.println("\t1) Criar conta");
			System.out.println("\t2) Excluir conta");
			System.out.println("\t3) Atualizar conta");
			System.out.println("\t4) Ler informacoes de uma conta");
			System.out.println("\t5) Realizar transacao bancaria");
			System.out.println("\t6) Sair");

			System.out.print("Digite a opcao desejada: ");
			opcaoEscolhida = scan.nextInt();
			scan.nextLine();
			System.out.print("\n------------------------------------------------------------------\n");

			switch (opcaoEscolhida){

				// Criar conta
				case 1:
				{
					System.out.print("\nDigite o nome: ");
					nomePessoa = scan.nextLine();
			
					System.out.print("\nDigite o cpf: ");
					cpf = scan.nextLine();
					
					if(cpf.length() == 11) 
					{
						System.out.print("\nDigite o estado: ");
						estado = scan.nextLine();
						
						banco.CreateAccount(nomePessoa, cpf, estado, 100, 0);
					}
					else 
					{
						System.out.println("Digite um cpf valido");
					}
				}
				break;
	
				// Excluir conta
				case 2:
				{
					System.out.print("\nDigite o id da conta que deseja apagar: ");
					id1 = scan.nextInt();
					banco.DeactivateAccount(id1);
					PrintResultado("A conta com id: " + id1 + " foi desativada com sucesso !");
				}
				break;
	
				// Atualizar conta
				case 3:
				{
					System.out.print("\nDigite o id que da conta deseja atualizar: ");
					id1 = scan.nextInt();
					scan.nextLine();
					
					//Retornar uma instancia de ContaBancaria atraves da leitura do arquivo. Id1 e a Id desejada da conta
					ContaBancaria conta = banco.ReadAccount(id1);
					
					
					//A conta retornada pode ser nula ou pode esta desativada. Em ambas as situacoes o processo de atualizacao
					//de dados ira ser cancelado
					if(conta == null) 
					{
						PrintResultado("Nao existe uma conta com a ID: " + id1);
						continue;
					}
					if(conta.isDead) 
					{
						PrintResultado("Esta conta esta desativada");
						continue;
					}
					
					
					//calculando a lenght da conta
					//Essa variavel sera utilizada na hora de verificar se a atualizacao dos dados resultou
					//em uma alteracao no tamanho do registro
					int contaSize = (conta.nomePessoa.length() + conta.cpf.length() + conta.cidade.length());
					int newSize = 0;
					
					
					//Coletando os novos valores dessa atualizacao no registro
					System.out.print("\nDigite o nome: ");
					nomePessoa = scan.nextLine();
			
					System.out.print("\nDigite o cpf: ");
					cpf = scan.nextLine();
					while(cpf.length() != 11) 
					{
						System.out.println("CPF tem que ter 11 digitos");
						cpf = scan.nextLine();
					}
			
					System.out.print("\nDigite o estado: ");
					estado = scan.nextLine();
					
					
					////Essa variavel sera utilizada na hora de verificar se a atualizacao dos dados resultou
					//em uma alteracao no tamanho do registro
					newSize = (nomePessoa.length() + cpf.length() + estado.length());
					
					
					//Se contaSize for diferente que newSize, quer dizer que os novos valores resultou em uma alteracao no tamanho do registro
					if(contaSize != newSize) 
					{
						//Desativando a conta de tamanho antigo
						banco.DeactivateAccount(id1);
						//Criando uma conta com os dados atualizados
						banco.CreateAccount(nomePessoa, cpf, estado, conta.saldoConta, conta.transferenciasRealizadas);
					}
					else 
					{
						//Se nao houver alteracao no tamanho do registro, entao os dados poderao ser apenas sobrescritos no registro
						banco.OverwriteAccount(id1, nomePessoa, cpf, estado, conta.saldoConta, conta.transferenciasRealizadas, conta.isDead);
					}
				}
				break;
	
				// Ler informacoes de uma conta
				case 4:
				{
					System.out.print("\nDigite o id da conta que deseja ler: ");
					id1 = scan.nextInt();
					
					//Percorrer o arquivo ate encotrar a id desejada e retornar uma nova instancia de ContaBancaria
					ContaBancaria conta = banco.ReadAccount(id1);
					if(conta == null) 
					{
						PrintResultado("Nao foi possivel encontrar uma conta com a ID: " + id1);
						continue;
					}
					PrintResultado("CONTA # " + conta.idConta + " - "+ conta.nomePessoa +" / CONTA ATIVA: " + !conta.isDead + "/ ESTADO: " + conta.cidade + "\nSALDO: " + conta.saldoConta + "\nTRANSFERENCIAS REALIZADAS: " + conta.transferenciasRealizadas);
				}
				break;
	
				// Realizar transacao bancaria
				case 5:
				{
					//Coletando as informacoes para a transacao
					System.out.print("\nDigite o id da conta que fara a transferencia (debitado): ");
					id1 = scan.nextInt();
					scan.nextLine();
					
					System.out.print("\nDigite o id da conta que recebera a transferencia (creditado): ");
					id3 = scan.nextInt();
					scan.nextLine();
					
					System.out.print("\nDigite o a quantidade que deseja transferir: ");
					id2 = scan.nextInt();
					scan.nextLine();
					
					
					
					//Percorrer o arquivo ate encotrar a id desejada e retornar uma nova instancia de ContaBancaria para a conta que sera debitada
					ContaBancaria contaA = banco.ReadAccount(id1);
					
					
					
					//Se a conta retornada for nulo ou estiver desativada entao o processo de transacao sera cancelado
					if(contaA == null) 
					{
						PrintResultado("A conta de ID: " + id1 + " nao existe");
						continue;
					}
					else if(contaA.isDead) 
					{
						PrintResultado("Conta desativada. ID: " + id1);
						continue;
					}

					
					
					//Percorrer o arquivo ate encotrar a id desejada e retornar uma nova instancia de ContaBancaria para a conta que sera debitada
					ContaBancaria contaB = banco.ReadAccount(id3);
					
					
					//Se a conta retornada for nulo ou estiver desativada entao o processo de transacao sera cancelado
					if(contaB == null) 
					{
						PrintResultado("A conta de ID: " + id3 + " nao existe");
						continue;
					}
					else if(contaB.isDead) 
					{
						PrintResultado("Conta desativada. ID: " + id3);
						continue;
					}
					
					
					//Diminuindo o saldo da conta debidata, aumentando o saldo da conta creditada e aumentando a transferencia das duas contas
					contaA.saldoConta -= id2;
					contaA.transferenciasRealizadas ++;
					contaB.saldoConta += id2;
					
					
					
					//Sobrescrevendo os valores das duas contas no registro
					banco.OverwriteAccount(id1, contaA.nomePessoa, contaA.cpf, contaA.cidade, contaA.saldoConta, contaA.transferenciasRealizadas, contaA.isDead);
					banco.OverwriteAccount(id3, contaB.nomePessoa, contaB.cpf, contaB.cidade, contaB.saldoConta, contaB.transferenciasRealizadas, contaB.isDead);
				
					PrintResultado("Uma transferencia de " + contaA.nomePessoa + " no valor de: " + id2 + "R$ para a conta de " + contaB.nomePessoa + " foi realizada !");
				}
				break;
				
				// Sair
				case 6:
					System.out.println("\nSaindo...");
				try {
					banco.Close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
	
				// Opcao invalida
				default:
					System.out.println("\nERRO: opcao invalida! tente novamente\n");
				break;
			}
			
			System.out.print("\n------------------------------------------------------------------\n\n");
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while(opcaoEscolhida!=6);
		
	}
	
	//Funcao apenas para printar um valor segundo um layout:
	// ///////////////////////// { string } /////////////////////////////
	static void PrintResultado(String resultado) 
	{
		System.out.println("/////////////////////////////////////////////////");
		System.out.println(resultado);
		System.out.println("/////////////////////////////////////////////////");
	}
}
