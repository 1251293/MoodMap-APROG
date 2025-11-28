import java.util.Scanner;

public class projeto {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Sales dept. – november;");

        int numPessoas = input.nextInt();
        int numDias = input.nextInt();

        final int LOW_MOOD = 2;
        final int LIMITE_SUP_ESCALA = 5;
        final int LIMITE_INF_ESCALA = 1;

        int[][] moodMap = fazerMoodMap(numPessoas, numDias);
        float[] mediaMoodDia = calcMedia(numDias, numPessoas, moodMap);
        float[] mediaMoodPessoa = calcMedia(numPessoas, numDias, moodMap);

        printMoodMap(moodMap);
        PrintAvgMood(mediaMoodDia, numDias);
        PrintMediaPerPerson(mediaMoodPessoa);
        diasMoodMaximo(mediaMoodDia, numDias);
        PrintPercentagemMoodLevels(moodMap, LIMITE_INF_ESCALA, LIMITE_SUP_ESCALA);
        diasMauHumorConsectutivos(moodMap, numPessoas, numDias, LOW_MOOD);
        fazerGrafico(numDias, moodMap, numPessoas);

    }

    public static int determinarMaximoMatrizInt(int[][] mapa, int linha, int dias) { /* determina o valor máximo de um inteiro de um array de uma matriz*/
        int maximo = 0;
        for (int colunas = 0; colunas < dias; colunas++) {
            if (maximo < mapa[linha][colunas]) {
                maximo = mapa[linha][colunas];
            }
        }
        return maximo;
    }

    public static int determinarMaximoArrayInt(int[] mapa, int dias) { /*determina o valor máximo de um inteiro num array*/
        int maximo = 0;
        for (int colunas = 0; colunas < dias; colunas++) {
            if (maximo < mapa[colunas]) {
                maximo = mapa[colunas];
            }
        }
        return maximo;
    }


    // -----------------------------------------------------------
    // a) Ler MoodMap


    public static int[][] fazerMoodMap(int pessoas, int dias) {  /*Este método faz a leitura do input e guarda-o em formato de matriz */
        int[][] mapa = new int[pessoas][dias];

        for (int linhas = 0; linhas < pessoas; linhas++) {
            for (int colunas = 0; colunas < dias; colunas++) {
                mapa[linhas][colunas] = input.nextInt();
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
    public static void PrintAvgMood(float[] medias, int numDias) {
        System.out.println("c) Average mood each day:");
        printLinhaDay(numDias);
        printSeparator(numDias);
        System.out.print("moods     :");
        for (int d = 0; d < numDias; d++) {
            System.out.printf("%1.1f ", medias[d]);
        }
        System.out.println("");

    }

    //Formatação do output da alínea d)
    public static void PrintMediaPerPerson(float medias[]) {
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

        System.out.println();
    }


    // ------------------------------------------------------------
    //f)


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

    public static void PrintPercentagemMoodLevels(int[][] mapa, int moodMin, int moodMax) {
        System.out.println("f) Percentage of mood levels:");

        int[] contagens=contarOcorrenciasMood(mapa, moodMin, moodMax);
        int total= mapa.length * mapa[0].length;

        for(int mood = moodMax; mood >= moodMin; mood--){
            float percentagem = (contagens[mood] * 100.0f) / total;
            System.out.printf("Mood #%d: %.1f%%%n", mood, percentagem);
        }

        System.out.println();

    }


    // ------------------------------------------------------------
    //g)


    public static void diasMauHumorConsectutivos(int[][] mapa, int pessoas, int dias, int moodMin) { /*O modulo conta o numero de dias consecutivos em que a pessoa tive maus dias*/

        for (int linhas = 0; linhas < pessoas; linhas++) {
            int diasHumorBaixo = 0;
            for (int colunas = 0; colunas < dias; colunas++) {
                if (mapa[linhas][colunas] < moodMin) {
                    diasHumorBaixo++;
                } else {
                    diasHumorBaixo = 0;
                }
            }

            if (diasHumorBaixo != 0) {
                System.out.printf("Person #%d : %d consecutive days%n", linhas, diasHumorBaixo);
            }

        }
    }




    public static void fazerGrafico(int dias, final int ESCALA, int[][] mapa) {
        for (int x = ESCALA; x < 0; x--) {
            for (int espacos = dias; espacos < 0; espacos--) {
            }
        }
    }


    // ------------------------------------------------------------
    //h) Gráfico do humor por pessoa


    public static String[][] fazerMatrizGrafico(int[] array, int dias){
        int min = 5, max = 1;
        for (int i = 0; i < dias; i++) {
            if (array[i] < min) min = array[i];
            if (array[i] > max) max = array[i];
        }

        int altura = max - min + 1;
        String[][] grafico = new String[altura][dias];

        // preencher tudo com espaços
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < dias; x++) {
                grafico[y][x] = " ";
            }
        }

        // colocar *
        for (int x = 0; x < dias; x++) {
            int linha = max - array[x];
            grafico[linha][x] = "*";
        }

        return grafico;
    }
    public static void fazerGrafico(int dias, int[][] mapa, int pessoas) {

        System.out.println("h) People's Mood Level Charts:");

        for (int p = 0; p < pessoas; p++) {

            System.out.printf("Person #%d:%n", p);

            // construir matriz 1 única vez
            String[][] matriz = fazerMatrizGrafico(mapa[p], dias);

            int min = 5, max = 1;
            for (int i = 0; i < dias; i++) {
                if (mapa[p][i] < min) min = mapa[p][i];
                if (mapa[p][i] > max) max = mapa[p][i];
            }

            // imprimir de cima para baixo
            for (int nivel = max; nivel >= min; nivel--) {
                System.out.printf(" %2d |", nivel);

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
    // i) Recommended therapy


    public static void recommendedTherapy(int[][] mapa, int pessoas, int dias) {

        System.out.println("i) Recommended therapy:");

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
            }
        }

        System.out.println();
    }


    // ------------------------------------------------------------
    // j) Pessoas com moods semelhantes e pessoas com maior quantidade de dias com o humor igual.


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


