import java.util.Scanner;
public class projeto {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int numPessoas = input.nextInt();
        int numDias = input.nextInt();
        
        int[][] moodMap = fazerMoodMap(numPessoas, numDias, input);

    }

    public static int[][] fazerMoodMap(int pessoas, int dias, Scanner entrada){
        int[][] mapa = new int[pessoas][dias];

        for(int linhas = 0; linhas < pessoas; linhas ++){
            for(int colunas = 0; colunas < dias; colunas++){
                mapa [linhas][colunas] = entrada.nextInt();
            } 
        }
        return mapa;
    }
}