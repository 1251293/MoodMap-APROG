import java.util.Scanner;
public class projeto {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        
        int numPessoas = input.nextInt();
        int numDias = input.nextInt();

        final int LOW_MOOD = 2;
        final int LIMITE_SUP_ESCALA = 5;
        final int LIMITE_INF_ESCALA = 1;

        int[][] moodMap = fazerMoodMap(numPessoas, numDias);
        float[] mediaMoodDia = calcMedia(numDias, numPessoas, moodMap);
        float[] mediaMoodPessoa = calcMedia(numPessoas, numDias, moodMap);
        diasMoodMaximo(mediaMoodDia, numDias);
        diasMauHumorConsectutivos(moodMap, numPessoas, numDias, LOW_MOOD);
    }

    public static int determinarMaximoMatrizInt(int[][] mapa, int linha, int dias){/* determina o valor máximo de um inteiro de um array de uma matriz*/
        int maximo  = 0;
        for(int colunas = 0; colunas < dias; colunas++){
            if(maximo< mapa[linha][colunas]){
                maximo = mapa[linha][colunas];
            }
        }
        return maximo;
    }

    public static int determinarMaximoArrayInt(int[] mapa, int dias){ /*determina o valor máximo de um inteiro num array*/
        int maximo  = 0;
        for(int colunas = 0; colunas < dias; colunas++){
            if(maximo< mapa[colunas]){
                maximo = mapa[colunas];
            }
        }
        return maximo;
    }

// a) 
    public static int[][] fazerMoodMap(int pessoas, int dias){ /*Este metodo faz a leitura do input e guarda-o em formato de matriz */
        int[][] mapa = new int[pessoas][dias];

        for(int linhas = 0; linhas < pessoas; linhas ++){
            for(int colunas = 0; colunas < dias; colunas++){
                mapa[linhas][colunas] = input.nextInt();
            }
        }
        return mapa;
    }

//c) e d)
    public static float[] calcMedia(int numColunas, int numLinhas, int[][] mapa){ /*Este método faz o calculo das médias que são utilizadas nas alineas c) e d) */
        float[] medias = new float[numColunas];
        for(int colunas = 0; colunas < numColunas; colunas++){
            int somaMood = 0;
            if(numLinhas < numColunas){ /*Esta condicional aparece para alterar a disposição dos indices da matriz para impedir haja erro no programa */
                for(int linhas = 0; linhas < numLinhas; linhas++){
                    somaMood = somaMood + mapa[linhas][colunas];
                }
            }else {
                for(int linhas = 0; linhas < numLinhas; linhas++){
                    somaMood = somaMood + mapa[colunas][linhas];
                }
            }            
            medias[colunas] = (float) somaMood/numLinhas;/*adição do resultado da média ao array*/
        }
        return medias;
    }

// e)
    public static void diasMoodMaximo(float[] mapa, int dias){ /*Este modulo faz o print dos dias que possuem que o o valor máximo da média por dia*/
        float maximo = 0; 
        for(int colunas = 0; colunas < dias; colunas ++){ /*No ciclo "for" são comparados os valores do array entre si para determinar o maior valor da média*/
            if (mapa[colunas] > maximo){
                maximo = mapa[colunas];
            }
        }
        System.out.printf("e) Days with the highest average mood (%.1f) : ", maximo);
        for(int colunas = 0; colunas < dias; colunas++){ /*No ciclo "for" são comparados os valores do array com o valor maximo determinado acima e são impressos*/
            if(mapa[colunas] == maximo){
                System.out.printf("%d ", colunas);
            }
        }
        System.out.printf("%n");
    }

// g)
    public static void diasMauHumorConsectutivos(int[][] mapa, int pessoas, int dias, int moodMin){/*O modulo conta o numero de dias consecutivos em que a pessoa tive maus dias*/

        for(int linhas = 0; linhas < pessoas; linhas ++){
            int diasHumorBaixo = 0;
            for(int colunas = 0;  colunas < dias ; colunas++){
                if(mapa[linhas][colunas] < moodMin){
                    diasHumorBaixo++;
                }else{
                    diasHumorBaixo = 0;
                }
            }

            if(diasHumorBaixo != 0 ){
                System.out.printf("Person #%d : %d consecutive days%n", linhas, diasHumorBaixo);
            }
        }
    }

// h) 
    public static String[][] fazerMatrizGrafico(int[] array, int eixoY, int eixoX){
        String[][] grafico = new String[eixoY][eixoX];
        for(int colunas = 0; colunas < eixoX; colunas++){
            grafico[colunas][array[colunas]] = "*";
        }
        for(int linhas = 0; linhas < eixoY; linhas++){
            for(int colunas = 0; colunas < eixoX; colunas++){
                if(grafico[linhas][colunas] == null){
                    grafico[linhas][colunas] = " ";
                }
            }
        }
        return grafico;
    }


    public static void fazerGrafico(int dias, int[][] mapa, int pessoas){
        
        for(int linhas = 0; linhas < pessoas; linhas++){
            System.out.printf("Person #%d:%n", linhas);
            System.out.printf("   %d |", linhas);
            int escala = determinarMaximoArrayInt(mapa[linhas], dias);
            String[][] matrizGrafico = fazerMatrizGrafico(mapa[linhas], escala, dias);
            for(int colunas = 0; colunas < dias ; colunas++){
                System.out.printf("%s", );
            }
            System.out.printf("%n");
        }
        System.out.printf("Mood +");
        for(int colunas = 0; colunas < dias; colunas++){
            System.out.println("-");
        }

    }




}
