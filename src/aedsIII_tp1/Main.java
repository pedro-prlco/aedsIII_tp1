package aedsIII_tp1;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int opcaoEscolhida = 0;

		// variaveis que podem ser passados como parametro
		int id1, id2;
		String nomePessoa, cpf, estado;
		
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
			System.out.print("\n------------------------------------------------------------------\n");

			switch (opcaoEscolhida){

				// Criar conta
				case 1:
					System.out.print("\nDigite o nome: ");
					nomePessoa = scan.next();
			
					System.out.print("\nDigite o cpf: ");
					cpf = scan.next();
			
					System.out.print("\nDigite o estado: ");
					estado = scan.next();

					/* banco.criar(nomePessoa, cpf, estado); */

				break;
	
				// Excluir conta
				case 2:
					System.out.print("\nDigite o id da conta que deseja apagar: ");
					id1 = scan.nextInt();

					/* banco.excluir(id1); */

				break;
	
				// Atualizar conta
				case 3:
					System.out.print("\nDigite o id que da conta deseja atualizar: ");
					id1 = scan.nextInt();
					
					System.out.print("\nDigite o nome: ");
					nomePessoa = scan.next();
			
					System.out.print("\nDigite o cpf: ");
					cpf = scan.next();
			
					System.out.print("\nDigite o estado: ");
					estado = scan.next();

					/* banco.atualizar(id1, nome, cpf, estado); */

				break;
	
				// Ler informacoes de uma conta
				case 4:
					System.out.print("\nDigite o id da conta que deseja ler: ");
					id1 = scan.nextInt();

					/* banco.ler(id1); */

				break;
	
				// Realizar transacao bancaria
				case 5:
					System.out.print("\nDigite o id da conta que fara a transferencia (debitado): ");
					id1 = scan.nextInt();
					
					System.out.print("\nDigite o id da conta que recebera a transferencia (creditado): ");
					id2 = scan.nextInt();
					
					System.out.print("\nDigite o a quantidade que deseja transferir: ");
					id2 = scan.nextInt();

					/* banco.realizarTransacao(id1, id2, valor); */

				break;
				
				// Sair
				case 6:
					System.out.println("\nSaindo...");
				break;
	
				// Opcao invalida
				default:
					System.out.println("\nERRO: opcao invalida! tente novamente\n");
				break;
			}
			
			System.out.print("\n------------------------------------------------------------------\n\n");

		} while(opcaoEscolhida!=6);
		
	}
	
}
