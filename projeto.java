import java.util.Scanner;
public class projeto {
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {

        int numPessoas = input.nextInt();
        int numDias = input.nextInt();

        int[][] moodMap = fazerMoodMap(numPessoas, numDias);
        float[] mediaMoodDia = calcMedia(numDias, numPessoas, moodMap);
        float[] mediaMoodPessoa = calcMedia(numPessoas, numDias, moodMap);
        diasMoodMaximo(mediaMoodDia, numDias);
    }

    public static int[][] fazerMoodMap(int pessoas, int dias){ /*Este metodo faz a leitura do input e guarda-o em formato de matriz */
        int[][] mapa = new int[pessoas][dias];

        for(int linhas = 0; linhas < pessoas; linhas ++){
            for(int colunas = 0; colunas < dias; colunas++){
                mapa[linhas][colunas] = input.nextInt();
            }
        }
        return mapa;
    }

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

}
