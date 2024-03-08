import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DigrafoValorado {
  protected static final String NEWLINE = System.getProperty("line.separator");

  protected Map<String, List<Edge>> graph;

  public DigrafoValorado() {
    graph = new HashMap<>();
  }

  public DigrafoValorado(String filename) { 
    this();
    In in = new In(filename);
    String line;

    while ((line = in.readLine()) != null) { //Enquanto ainda tem linhas
      int controlador = 0;
      String[] edge = line.split(" "); //separa por espaço em branco
      String verticeOrigem = edge[edge.length - 1]; // o mesmo na linha inteira
      String verticeDestino;
      int peso;

      //percorre a linha pegando os elementos e os pesos, até chegar na seta
      while (!(edge[controlador].equals("->"))) {
        verticeDestino = edge[controlador + 1]; // pega o destino
        peso = Integer.parseInt(edge[controlador]); // pega o peso
        addEdge(verticeDestino, verticeOrigem, peso); //função que adiciona a aresta no dicionario 
        controlador = controlador + 2; // pula para o proximo elemento
      }
    }
    in.close();
  }

  public void addEdge(String v, String w, int weight) {
    Edge e = new Edge(v, w, weight);
    addToList(v, e);
    // comentei para ficar unidiracional
    // addToList(w, e);
  }

  public Iterable<Edge> getAdj(String v) {
    return graph.get(v);
  }

  public Set<String> getVerts() {
    return graph.keySet();
  }

  public String toDot() {
    // Usa um conjunto de arestas para evitar duplicatas
    Set<String> edges = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    sb.append("graph {" + NEWLINE);
    sb.append("rankdir = LR;" + NEWLINE);
    sb.append("node [shape = circle];" + NEWLINE);
    for (String v : getVerts().stream().sorted().toList()) {
      for (Edge e : getAdj(v)) {
        String edge = e.toString();
        if (!edges.contains(edge)) {
          sb.append(String.format("%s -- %s [label=\"%d\"]", e.getV(), e.getW(), e.getWeight()) + NEWLINE);
          edges.add(edge);
        }
      }
    }
    sb.append("}" + NEWLINE);
    return sb.toString();
  }

  // Adiciona um vértice adjacente a outro, criando a lista
  // de adjacências caso ainda não exista no dicionário
  protected List<Edge> addToList(String v, Edge e) {
    List<Edge> list = graph.get(v);
    if (list == null)
      list = new LinkedList<>();
    list.add(e);
    graph.put(v, list);
    return list;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////
  //Primeiro algoritmo que implementei, mas não tem eficiencia pela falta do dicionario
  public BigInteger calcularHidrogenioOld(String verticeInicial) {
    // Printa qual vertice esta sendo trabalhado
    // System.out.printf("\nCalculando Vertice Inicial: " + verticeInicial + "\n");
    // se for o ouro, tem valor 1
    if (verticeInicial.equals("ouro")) {
      BigInteger valor = BigInteger.ONE;
      // System.out.printf("\tValor de ouro é " + valor);
      return valor;
    }
    BigInteger resultado = BigInteger.valueOf(0);
    List<Edge> listaFilhos = graph.get(verticeInicial);
    for (Edge edge : listaFilhos) {
      // System.out.printf("\t\tAresta: " + edge.toString());
      BigInteger valorAresta = BigInteger.valueOf(edge.getWeight());
      // System.out.printf(" - Valor aresta: " + valorAresta);
      BigInteger valorFilho = calcularHidrogenioOld(edge.getW());
      // System.out.printf(" - Valor filho: " + valorFilho);

      resultado = resultado.add(valorAresta.multiply(valorFilho));
      // System.out.printf(" - Destino " + edge.getW() + " tem valor: " + resultado +
      // "\n");
    }
    // System.out.printf("\tValor de " + verticeInicial + " é " + resultado);
    return resultado;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////
  public void imprimirGrafo() {
    System.out.println("GRAFO:");
    for (Map.Entry<String, List<Edge>> entry : graph.entrySet()) {
      String v = entry.getKey();
      List<Edge> arestas = entry.getValue();

      System.out.print(v + " -> ");
      for (Edge e : arestas) {
        System.out.print(e.getW() + "(" + e.getWeight() + "), ");
      }
      System.out.println();
    }
  }

  public BigInteger calcularHidrogenio(String verticeInicial, String verticeFinal) {
    Map<String, BigInteger> memoriaDeValores = new HashMap<>();
    memoriaDeValores.put(verticeFinal, BigInteger.ONE);
    return hidrogenioRecursivo(verticeInicial, memoriaDeValores);
  }

  private BigInteger hidrogenioRecursivo(String verticeAtual, Map<String, BigInteger> memoriaDeValores) {
    // procura na memoria se o valor do vertice ja foi calculado. Se ja existir so retorna ele.
    if (memoriaDeValores.containsKey(verticeAtual)) {return memoriaDeValores.get(verticeAtual);}
    // inicializa o resultado com zero, acessa os filhos e vai somando o valor deles.
    BigInteger resultado = BigInteger.valueOf(0);
    List<Edge> listaFilhos = graph.get(verticeAtual);
    for (Edge edge : listaFilhos) {
      // pega o valor da aresta do filho atual
      BigInteger valorAresta = BigInteger.valueOf(edge.getWeight());
      // e pega o valor dele recursivamente (caso ja estiver na memoria, no inico da
      // recursão, já vai retornar)
      BigInteger valorFilho = hidrogenioRecursivo(edge.getW(), memoriaDeValores);
      // soma o valor de todos os filhos recursivamente
      resultado = resultado.add(valorAresta.multiply(valorFilho));
    }
    // coloca na memoria o valor do vertice atual para uso futuro
    memoriaDeValores.put(verticeAtual, resultado);
    // retorna o valor
    return resultado;
  }

}
