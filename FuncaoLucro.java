/**
 * Este projeto foi desenvolvido por
 *
 * Gabriel Cavalcante <gscavalcante@gmail.com>
 * Hebert Herman <hebert.herman@hotmail.com>
 * Lucas Carneiro <lucafoxrocha@gmail.com>
 * Lucas Pelegrino <lucas.assuncao.p@gmail.com>
 * Victor Hugo <vhsviana@gmail.com>
 *
 * FUMEC - maio/2016
 */
import java.util.Scanner;
import java.lang.ArithmeticException;

public class FuncaoLucro {
	static Scanner leia;

	public static void main(String[] args) {
		leia = new Scanner(System.in);
		boolean certo;
		double[] cof = new double[4];
		double[] x = new double[2]; // Raiz da função
		double y[] = new double[2];

		System.out.println("******* CÁLCULOS COM FUNÇÕES LUCRO *******");
		System.out.println("\n\tExemplo: L(x) = ax³ + bx² + cx + d\n" +
			"Onde x equivale a quantidade de produtos produzidos e vendidos.");

		while (true) {
			recebeFuncao(cof, 4);
			do { // loop para ver se o delta não negativo ou as ambas as raízes
				// são negativas

				// vetor raízes preenchido no método delta
				certo = delta(derivaFuncao(cof), x);

				if (certo == false) {
					recebeFuncao(cof, 4);
				} else {
					y = calculaY(x, cof);
					definirPonto(cof, x, y);
					inflexao(x, y);
				}
			} while (certo == false);

			if (validarEscolha("Deseja sair?")) {
				break;
			}
		}
	} // Fim main

	public static boolean validarEscolha(String descricao) {
		String flag;

		do {
			System.out.print("\n\t" + descricao + " (S/N) ");
			flag = leia.next();
		} while (!flag.equalsIgnoreCase("S") && !flag.equalsIgnoreCase("N"));

		if (flag.equalsIgnoreCase("S")) {
			return true;
		} else {
			return false;
		}
	}

	public static void recebeFuncao(double cof[], int quantidade) {
		boolean correto;
		String tmp;

		System.out.println("\nEntre com os coeficientes");

		do {
			// Lê dado entrado pelo usuário e converte para Double
			for (int i = 0; i < quantidade; i++) {
				int posicao = (int) 'a' + i;
				System.out.print("Coeficiente " + (char) posicao + ": ");
				tmp = leia.next();
				tmp = tmp.replace(",", ".");
				try {
					cof[i] = Double.parseDouble(tmp);
				} catch (NumberFormatException e) {
					System.out.println("ERRO: Dados não numéricos.");
					i--;
				}
			}

			// Imprime a função e confirma entrada
			System.out.print("\nFunção lucro: L(x) = ");
			imprimeFuncao(cof);

			if (validarEscolha("Deseja continuar?")) {
				correto = true;
			} else {
				System.out.println("\nInforme a função novamente");
				correto = false;
			}
			leia.nextLine();

		} while (!correto);
		System.out.print("\n");
	}

	public static double[] derivaFuncao(double cof[]) {
		double der[] = new double[cof.length - 1];
		int potencia = cof.length - 1;

		for (int i = 0; i < cof.length - 1; i++) {
			der[i] = cof[i] * potencia;
			potencia--;
		}

		System.out.print("Derivada: ");
		imprimeFuncao(der);

		return der;
	}

	public static boolean delta(double der[], double[] raizes) {
		double delta;

		// fórmula do delta
		delta = (der[1] * der[1]) - (4 * der[0] * der[2]);
		System.out.println("Delta = " + String.format("%.2f", delta));
		if (delta < 0) {
			System.out.println("\nDelta negativo, não é possível calcular.\n" +
				"Informe a função novamente:");
			return false;
		}

		if (der[0] == 0) {
			// Raiz negativa
			System.out.println("ERRO: Divisão por zero.");
			return false;
		}
		
		raizes[0] = (-(der[1]) + Math.sqrt(delta)) / (2 * der[0]);
		raizes[1] = (-(der[1]) - Math.sqrt(delta)) / (2 * der[0]);
		
		if (raizes[0] < 0 && raizes[1] < 0) {

			System.out.println("\nAmbas as raízes são negativas, não existe " +
				"quantidade de produtos negativa." +
				"\nInforme a função novamente:");
			return false;
		}

		System.out.print("x = " + String.format("%.2f", raizes[0]));
		if (raizes[0] != raizes[1]) {
			System.out.println("\tx' = " + String.format("%.2f", raizes[1]));
		} else {
			System.out.print("\n");
		}

		return true;
	}

	public static double[] calculaY(double[] raizes, double cof[]) {
		double[] ys = new double[2];
		// calcula os ys

		for (int i = 0; i < 2; i++) {
			ys[i] = resolveFuncao(cof, raizes[i]);
		}

		System.out.print("y = " + String.format("%.2f", ys[0]));
		if (ys[0] != ys[1]) {
			System.out.println("\ty' = " + String.format("%.2f", ys[1]));
		} else {
			System.out.print("\n");
		}

		return ys;
	}

	public static void imprimeFuncao(double funcao[]) {
		for (int i = 0; i < funcao.length; i++) {
			int potencia = funcao.length - i - 1;
			String coeficiente;

			// Coeficiente
			if (funcao[i] >= 0) {
				// Positivo ou zero
				coeficiente = funcao[i] - (int)funcao[i] > 0 ?
					String.format("%.2f", funcao[i]) :
					String.format("%.0f", funcao[i]);
			} else {
				// Negativo
				coeficiente = funcao[i] - (int)funcao[i] > 0 ?
					"(" + String.format("%.2f", funcao[i]) + ")" :
					"(" + String.format("%.0f", funcao[i]) + ")";
			}

			System.out.print(coeficiente);

			// Potência
			if (potencia != 0) {
				System.out.print("x");
				
				switch (potencia) {
					case 1:
						System.out.print("¹");
						break;
					case 2:
						System.out.print("²");
						break;
					case 3:
						System.out.print("³");
						break;
				}
				
				System.out.print(" + ");
			} else {
				System.out.print("\n");
			}
		}
	}

	/**
	 * Define o ponto
	 * O ponto pode ser máximo/mínimo, cálcula e exibe
	 *
	 * @param cof[] coeficientes da minha função
	 * @param raiz[] raizes da função
	 * @param y[] L(x) da função nas raizes
	 */
	public static void definirPonto(double cof[], double raiz[],double y[]) {
		String definicao = "";

		for (int i = 0; i < raiz.length; i = i + 1) {
			double tmp;
			double derivada[] = cof;
			boolean erro = false;

			while (true) {
				derivada = derivaFuncao(derivada);
				tmp = resolveFuncao(derivada, raiz[i]);

				// Salva a definição do ponto
				if (tmp > 0) {
					definicao = "mínimo";
					break;
				} else if (tmp < 0) {
					definicao = "máximo";
					break;
				} else {
					// Caso não tenha mais como derivar, apresentar erro
					if (derivada.length == 1) {
						erro = true;
						break;
					}
				}
			} // Fim do loop

			if (raiz[i] >= 0) {
				// Apenas uma impressão quando coordenadas dos pontos forem iguais
				if ((raiz[0] == raiz[1] && i == 0) || raiz[0] != raiz[1]) {
					if (!erro) {
						String ponto = "(" +
							String.format("%.2f", raiz[i]) + "; " +
							String.format("%.2f", y[i]) + ")";
						System.out.println("O ponto " + ponto + " é " +
							definicao + ".");
					} else {
						System.out.println("ERRO: Não encontrado.");
					}
				}
			}
		} // Fim raízes
	}

	/**
	 * Exibe o ponto de inflexão da função
	 *
	 * @param x[] x da função
	 * @param y[] y da função
	 * @version 2.0
	 */
	public static void inflexao(double x[], double y[]) {
		double resultadoX = media(x);
		double resultadoY = media(y);

		System.out.println("O ponto de inflexão é " +
				String.format("%.2f", resultadoX) + "; " +
				String.format("%.2f", resultadoY) + ").");
	}

	/**
	 * Calcula da média dado um vetor
	 *
	 * @param valores[] vetor com valores para serem calculados
	 * @return média calculada
	 */
	public static double media(double valores[]) {
		int i;
		double resultado = 0;

		for (i = 0; i < valores.length; i++) {
			resultado += valores[i];
		}

		return resultado / i;
	}

	/**
	 * Substitui o x na função e calcula
	 *
	 * @param coeficiente[] vetor com os coeficientes da função
	 * @param x valor de substituir o x na função
	 * @return resultado da operação
	 */
	public static double resolveFuncao(double coeficiente[], double x) {
		// 2x^2 + 3x^1 + 1x^0 = 0
		double resultado = 0; // Inicia zerado para sempre somar com ela mesma

		for (int i = 0; i < coeficiente.length; i = i + 1) {
			// Potência = Quantidade de coeficientes - posição do cálculo - 1
			int potencia = coeficiente.length - i - 1;

			resultado += coeficiente[i] * Math.pow(x, potencia);
		}

		return resultado;
	}
}
