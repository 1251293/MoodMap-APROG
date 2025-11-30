import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class projeto {


    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("Mood Tracker Analysis");
        System.out.println("---------------------\n");
        System.out.println("Read data from Keyboard (1)");
        System.out.println("Read data from File (2)");

        int opcao = obterDado(1, 2, input);
        input.nextLine(); // Consumir o \n após nextInt()

        if (opcao == 1) {
            executarPrograma(input);
        } else {
            System.out.print("Name of file: \n");
            String nomeFicheiro = input.nextLine();
            lerDadosFicheiro(nomeFicheiro);
        }

        input.close();
    }

    // -----------------------------------------------------------
    // Executa o programa com qualquer Scanner (teclado ou ficheiro)
    public static void executarPrograma(Scanner scanner) {
        // Ler descrição (primeira linha)
        if (!scanner.hasNextLine()) {
            System.out.println("Error: Empty file or invalid format");
            return;
        }
        String descricao = scanner.nextLine();

        // Ler dimensões
        int numPessoas = obterNumeroDePessoasOuDiasValidado(scanner);
        if (numPessoas == -1) {
            return;
        }

        int numDias = obterNumeroDePessoasOuDiasValidado(scanner);
        if (numDias == -1) {
            return;
        }

        final int LOW_MOOD = 3;
        final int LIMITE_SUP_ESCALA = 5;
        final int LIMITE_INF_ESCALA = 1;

        // Ler MoodMap com validação
        int[][] moodMap = fazerMoodMapValidado(numPessoas, numDias, scanner,
                LIMITE_INF_ESCALA, LIMITE_SUP_ESCALA);

        if (moodMap == null) {
            return;
        }

        // Calcular médias
        float[] mediaMoodDia = calcMedia(numDias, numPessoas, moodMap);
        float[] mediaMoodPessoa = calcMedia(numPessoas, numDias, moodMap);

        // Executar todas as funcionalidades
        System.out.println();
        // System.out.println(descricao);
        printMoodMap(moodMap);
        printAvgMood(mediaMoodDia, numDias);
        printMediaPerPerson(mediaMoodPessoa);
        diasMoodMaximo(mediaMoodDia, numDias);
        printPercentagemMoodLevels(moodMap, LIMITE_INF_ESCALA, LIMITE_SUP_ESCALA);
        diasMauHumorConsectutivos(moodMap, numPessoas, numDias, LOW_MOOD);
        fazerGrafico(numDias, moodMap, numPessoas);
        recommendedTherapy(moodMap, numPessoas, numDias);
        pessoasMaisSemelhantes(moodMap, numPessoas, numDias);
    }

    // -----------------------------------------------------------
    // Ler dados do ficheiro

    public static void lerDadosFicheiro(String nomeFicheiro) throws FileNotFoundException {

        File file = new File(nomeFicheiro);
        Scanner ficheiro = new Scanner(file);

        executarPrograma(ficheiro);

        ficheiro.close();

    }

    // -----------------------------------------------------------
    // Métodos auxiliares para obter dados

    // Lê dados e verifica se está dentro de um intervalo
    public static int obterDado(int min, int max, Scanner input) { /*Lê um dado inteiro do input entre min e max*/
        int dado;
        dado = input.nextInt();
        while (dado < min || dado > max) {
            System.out.println("Insert a valid number between " + min + " and " + max + ": ");
            dado = input.nextInt();
        }
        return dado;
    }

    // Lê o número de pessoas ou dias e garante que é um inteiro > 0.
    // Em caso de erro devolve -1 para o main saber que deve abortar.
    public static int obterNumeroDePessoasOuDiasValidado(Scanner scanner) { /*Lê o número de pessoas ou dias com validação para ficheiros*/
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Invalid number - expected an integer");
            return -1;
        }
        int valor = scanner.nextInt();
        if (valor < 1) {
            System.out.println("Erro: Number must be greater than 0 (read value: " + valor + ")");
            return -1;
        }
        return valor;
    }


    // -----------------------------------------------------------
    // a) Ler MoodMap
    // Lê o MoodMap validando se todos os valores estão dentro do intervalo permitido.

    public static int[][] fazerMoodMapValidado(int pessoas, int dias, Scanner scanner,
                                               int limiteInf, int limiteSup) { /*Lê MoodMap com validação completa para ficheiros*/
        int[][] mapa = new int[pessoas][dias];

        for (int linhas = 0; linhas < pessoas; linhas++) {
            for (int colunas = 0; colunas < dias; colunas++) {
                if (!scanner.hasNextInt()) {
                    System.out.printf("Erro: Valor inválido na pessoa %d, dia %d - esperado um inteiro%n",
                            linhas, colunas);
                    return null;
                }

                int valor = scanner.nextInt();

                if (valor < limiteInf || valor > limiteSup) {
                    System.out.printf("Erro: Valor de humor fora do intervalo [%d-%d]: %d (pessoa %d, dia %d)%n",
                            limiteInf, limiteSup, valor, linhas, colunas);
                    return null;
                }

                mapa[linhas][colunas] = valor;
            }
        }
        return mapa;
    }


    // ------------------------------------------------------------
    // b) MoodMap formatado

    public static void printMoodMap(int[][] moods) {

        int P = moods.length;
        int D = moods[0].length;

        System.out.println("b) Mood (level/day(person)");

        // Cabeçalho dos dias
        printLinhaDay(D);

        // Separador
        printSeparator(D);

        // Cada pessoa
        for (int pessoa = 0; pessoa < P; pessoa++) {
            printLinhaPessoa(pessoa, moods[pessoa], D);
        }

        System.out.println();
    }

    // Cabeçalho "day       :  0   1   2   ..."
    public static void printLinhaDay(int D) {

        System.out.print("day       :");

        for (int j = 0; j < D; j++) {

            System.out.printf(" %2d ", j);
        }
        System.out.println();
    }

    //Separador "----------|---|---|..."
    public static void printSeparator(int numDias) {

        System.out.print("----------");

        for (int j = 0; j < numDias; j++) {
            System.out.print("|---");
        }
        System.out.println("|");
    }

    // Linha "Person #i :  4   2   3 ..."
    public static void printLinhaPessoa(int pessoa, int[] moods, int numDias) {

        System.out.printf("Person #%d : ", pessoa);

        for (int j = 0; j < numDias; j++) {

            System.out.printf("%2d  ", moods[j]);
        }

        System.out.println();
    }


    // -----------------------------------------------------------
    // c) e d) Médias


    //Métodos utilizados para o cálculo das alíneas c) e d)
    // Reutilizado para calcular médias por dia (colunas) ou por pessoa (linhas),
    // dependendo dos parâmetros numColunas / numLinhas.
    public static float[] calcMedia(int numColunas, int numLinhas, int[][] mapa) {

        float[] medias = new float[numColunas];

        for (int colunas = 0; colunas < numColunas; colunas++) {

            int somaMood = 0;

            if (numLinhas < numColunas) { /*Esta condicional aparece para alterar a disposição dos indices da matriz para impedir haja erro no programa */
                for (int linhas = 0; linhas < numLinhas; linhas++) {
                    somaMood += mapa[linhas][colunas];
                }
            } else {
                for (int linhas = 0; linhas < numLinhas; linhas++) {
                    somaMood += mapa[colunas][linhas];
                }
            }

            medias[colunas] = (float) somaMood / numLinhas; /*adição do resultado da média ao array*/
        }

        return medias;
    }

    //Formatação do output da alínea c)
    public static void printAvgMood(float[] medias, int numDias) {
        System.out.println("c) Average mood each day:");
        printLinhaDay(numDias);
        printSeparator(numDias);
        System.out.print("moods      ");
        for (int d = 0; d < numDias; d++) {
            System.out.printf("%1.1f ", medias[d]);
        }
        System.out.println("");
        System.out.println();

    }

    //Formatação do output da alínea d)
    public static void printMediaPerPerson(float medias[]) {
        System.out.println("d) Average of each person's mood:");
        for (int pessoa = 0; pessoa < medias.length; pessoa++) {
            printLinhaPessoaMedia(pessoa, medias[pessoa]);
        }

        System.out.println();
    }

    public static void printLinhaPessoaMedia(int pessoa, float media) {
        System.out.printf("Person #%d  : %.1f%n", pessoa, media);
    }


    // ------------------------------------------------------------
    // e) Dias com maior média
    public static void diasMoodMaximo(float[] mapa, int dias) { /*Este modulo faz o print dos dias que possuem que o o valor máximo da média por dia*/

        float maximo = 0;

        for (int colunas = 0; colunas < dias; colunas++) {
            if (mapa[colunas] > maximo) {
                maximo = mapa[colunas];
            }
        }

        System.out.printf("e) Days with the highest average mood (%.1f) : ", maximo);

        for (int colunas = 0; colunas < dias; colunas++) {
            if (mapa[colunas] == maximo) {
                System.out.printf("%d ", colunas);
            }
        }
        System.out.printf("%n %n");
    }


    // ------------------------------------------------------------
    // f) Percentagem dos níveis de humor


    // Conta quantas vezes aparece cada nível de humor entre moodMin e moodMax
    public static int[] contarOcorrenciasMood(int[][] mapa, int moodMin, int moodMax) {

        int[] contagens = new int[moodMax + 1];

        for (int pessoa = 0; pessoa < mapa.length; pessoa++) {
            for (int dia = 0; dia < mapa[0].length; dia++) {
                int valor = mapa[pessoa][dia];
                if (valor >= moodMin && valor <= moodMax) {
                    contagens[valor]++;
                }
            }
        }

        return contagens;
    }

    public static void printPercentagemMoodLevels(int[][] mapa, int moodMin, int moodMax) {
        System.out.println("f) Percentage of mood levels:");

        int[] contagens = contarOcorrenciasMood(mapa, moodMin, moodMax);
        int total = mapa.length * mapa[0].length;

        for (int mood = moodMax; mood >= moodMin; mood--) {
            float percentagem = (contagens[mood] * 100.0f) / total;
            System.out.printf("Mood #%d: %.1f%%%n", mood, percentagem);
        }

        System.out.println();

    }


    // ------------------------------------------------------------
    // g) Pessoas com transtorno emocional
    // Procura o maior número de dias consecutivos com mau humor (< moodMin) para cada pessoa.

    public static void diasMauHumorConsectutivos(int[][] mapa, int pessoas, int dias, int moodMin) { /*O modulo conta o numero de dias consecutivos em que a pessoa teve maus dias*/

        System.out.println("g) People with emotional disorders:");

        boolean encontrou = false;

        for (int linhas = 0; linhas < pessoas; linhas++) {
            int maxConsecutivos = 0;
            int consecutivosAtuais = 0;

            for (int colunas = 0; colunas < dias; colunas++) {
                if (mapa[linhas][colunas] < moodMin) {
                    consecutivosAtuais++;
                    if (consecutivosAtuais > maxConsecutivos) {
                        maxConsecutivos = consecutivosAtuais;
                    }
                } else {
                    consecutivosAtuais = 0;
                }
            }

            if (maxConsecutivos >= 2) {
                System.out.printf("Person #%d : %d consecutive days%n", linhas, maxConsecutivos);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum");
        }

        System.out.println();
    }


    // ------------------------------------------------------------
    // h) Gráfico do humor por pessoa

    // Constrói a matriz de caracteres para o gráfico de uma pessoa,
    // usando o nível mínimo (min) e máximo (max) já calculados.
    public static String[][] fazerMatrizGrafico(int[] array, int dias, int min, int max) {

        int altura = max - min + 1;
        String[][] grafico = new String[altura][dias];

        // preencher tudo com espaços
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < dias; x++) {
                grafico[y][x] = " ";
            }
        }

        // colocar '*'
        for (int x = 0; x < dias; x++) {
            int linha = max - array[x];  // quanto maior o mood, mais acima fica
            grafico[linha][x] = "*";
        }

        return grafico;
    }


    // Constrói um gráfico vertical de '*' para os níveis de humor de cada pessoa.
    // A escala adapta-se automaticamente ao mínimo e máximo dessa pessoa.
    public static void fazerGrafico(int dias, int[][] mapa, int pessoas) {

        System.out.println("h) People's Mood Level Charts:");

        for (int p = 0; p < pessoas; p++) {

            System.out.printf("Person #%d:%n", p);

            // calcular min e max dessa pessoa apenas uma vez
            int min = 5, max = 1;
            for (int i = 0; i < dias; i++) {
                if (mapa[p][i] < min) min = mapa[p][i];
                if (mapa[p][i] > max) max = mapa[p][i];
            }

            // construir matriz do gráfico usando o min e max calculados
            String[][] matriz = fazerMatrizGrafico(mapa[p], dias, min, max);

            // imprimir de cima para baixo (do nível máximo para o mínimo)
            for (int nivel = max; nivel >= min; nivel--) {
                System.out.printf("  %2d |", nivel);

                int linha = max - nivel;
                for (int x = 0; x < dias; x++) {
                    System.out.print(matriz[linha][x]);
                }
                System.out.println();
            }

            // rodapé
            System.out.print("Mood +");
            for (int i = 0; i < dias; i++) System.out.print("-");
            System.out.println();

            // legenda
            System.out.print("      ");              // mesmo nº de caracteres que "Mood +"
            for (int start = 0; start < dias; start += 5) {
                System.out.printf("%-5d", start);    // 0   5   10  ...
            }
            System.out.println("\n");
        }
    }



    // ------------------------------------------------------------
    // i) Terapia recomendada


    public static void recommendedTherapy(int[][] mapa, int pessoas, int dias) {

        System.out.println("i) Recommended therapy:");

        boolean encontrou = false;

        for (int p = 0; p < pessoas; p++) {

            int maxConsec = 0;
            int atual = 0;

            for (int d = 0; d < dias; d++) {
                if (mapa[p][d] < 3) {           // low mood é < 3
                    atual++;
                    if (atual > maxConsec) maxConsec = atual;
                } else {
                    atual = 0;
                }
            }

            if (maxConsec >= 2) {  // só imprimir quem tem transtorno
                System.out.printf("Person #%d : ", p);

                if (maxConsec >= 5)
                    System.out.println("psychological support");
                else
                    System.out.println("listen to music");

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum");
        }

        System.out.println();
    }


    // ------------------------------------------------------------
    // j) Pessoas com moods semelhantes
    // Compara todas as pares de pessoas e conta em quantos dias tiveram o mesmo mood.
    // Fica com o par que tem mais dias iguais.

    public static void pessoasMaisSemelhantes(int[][] mapa, int pessoas, int dias) {

        int maxIgual = 0;
        int p1Res = -1;
        int p2Res = -1;

        for (int p1 = 0; p1 < pessoas; p1++) {
            for (int p2 = p1 + 1; p2 < pessoas; p2++) {

                int iguais = 0;

                for (int d = 0; d < dias; d++) {
                    if (mapa[p1][d] == mapa[p2][d]) {
                        iguais++;
                    }
                }

                if (iguais > maxIgual) {
                    maxIgual = iguais;
                    p1Res = p1;
                    p2Res = p2;
                }
            }
        }

        System.out.print("j) People with the most similar moods: ");

        if (maxIgual == 0) {
            System.out.println("Nenhum");
        } else {
            System.out.printf("(Person #%d and Person #%d have the same mood on %d days)%n",
                    p1Res, p2Res, maxIgual);
        }

        System.out.println();
    }
}