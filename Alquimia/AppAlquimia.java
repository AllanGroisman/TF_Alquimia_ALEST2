
public class AppAlquimia {
    public static void main(String[] args) {

        // Lista de arquivos na ordem desejada
        String[] arquivos = {
                "casoa5.txt",
                "casoa20.txt",
                "casoa40.txt",
                "casoa60.txt",
                "casoa80.txt",
                "casoa120.txt",
                "casoa180.txt",
                "casoa240.txt",
                "casoa280.txt",
                "casoa320.txt",
                "casoa360.txt",
                "casoa400.txt",
                "casoEnunciado.txt"
        };

        // Percorre a lista de arquivos
        for (String teste : arquivos) {
            // Imprime e manda rodar o algoritmo de hidrogênio
            System.out.println("Teste: " + teste);
            algoritmoHidrogenio(teste);

        }

    }

    private static void algoritmoHidrogenio(String teste) {

        System.out.println(teste);
        long startTime;
        long endTime;
        long tempoFinal;

        // Calcula o tempo de carregamento do grafo
        startTime = System.currentTimeMillis(); // Captura o tempo inicial

        // cria um novo grafo para cada arquivo
        DigrafoValorado grafoAlquimia = new DigrafoValorado("casosTeste\\" + teste);

        endTime = System.currentTimeMillis(); // Captura o tempo final
        tempoFinal = endTime - startTime; // Calcula o tempo decorrido
        System.out.println("Tempo de carregamento: " + tempoFinal + " milissegundos");

        // Executa o algoritmo sobre o arquivo
        String verticeInicial = "hidrogenio";
        String verticeFinal = "ouro";

        startTime = System.currentTimeMillis(); // Captura o tempo inicial

        // Calcula e printa na tela o numero de hidrogenios
        System.out.println("Valor até o ouro: " + grafoAlquimia.calcularHidrogenio(verticeInicial, verticeFinal));

        endTime = System.currentTimeMillis(); // Captura o tempo final
        tempoFinal = endTime - startTime; // Calcula o tempo decorrido
        System.out.println("Tempo de execução: " + tempoFinal + " milissegundos");
        System.out.println();
        // grafoAlquimia.imprimirGrafo();
    }

}