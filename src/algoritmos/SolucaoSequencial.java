package algoritmos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SolucaoSequencial {
	private final Map<String, Map<String, Integer>> distances;

	public SolucaoSequencial(Map<String, Map<String, Integer>> distances) {
		this.distances = distances;
	}

	public static <T> void swap(T[] array, int first, int second) {
		T temp = array[first];
		array[first] = array[second];
		array[second] = temp;
	}

	private static <T> void allPermutationsHelper(T[] permutation, List<T[]> permutations, int n) {
		if (n <= 0) {
			permutations.add(permutation);
			return;
		}
		T[] tempPermutation = Arrays.copyOf(permutation, permutation.length);
		for (int i = 0; i < n; i++) {
			swap(tempPermutation, i, n - 1);
			allPermutationsHelper(tempPermutation, permutations, n - 1);
			swap(tempPermutation, i, n - 1); 
		}
	}

	private static <T> List<T[]> permutations(T[] original) {
		List<T[]> permutations = new ArrayList<>();
		allPermutationsHelper(original, permutations, original.length);
		return permutations;
	}

	public int pathDistance(String[] path) {
		String last = path[0];
		int distance = 0;
		for (String next : Arrays.copyOfRange(path, 1, path.length)) {
			distance += distances.get(last).get(next);
			last = next;
		}
		return distance;
	}

	public String[] findShortestPath() {
		String[] cities = distances.keySet().toArray(String[]::new);
		List<String[]> paths = permutations(cities);
		String[] shortestPath = null;
		int minDistance = Integer.MAX_VALUE; 
		for (String[] path : paths) {
			int distance = pathDistance(path);
			distance += distances.get(path[path.length - 1]).get(path[0]);
			if (distance < minDistance) {
				minDistance = distance;
				shortestPath = path;
			}
		}
		shortestPath = Arrays.copyOf(shortestPath, shortestPath.length + 1);
		shortestPath[shortestPath.length - 1] = shortestPath[0];
		return shortestPath;
	}

	public static void main(String[] args) {
		Map<String, Map<String, Integer>> vtDistances = Map.of(
				"Cornelio", Map.of(
			            "Londrina", 67,
			            "Maringa", 162,
			            "Bandeirantes", 37,
			            "Santa Mariana", 18,
			            "Cambé", 60,
			            "Rolândia", 80,
			            "Ibiporã", 55,
			            "Apucarana", 90,
			            "Arapongas", 85
			        ),
			        "Londrina", Map.of(
			            "Cornelio", 67,
			            "Maringa", 100,
			            "Bandeirantes", 103,
			            "Santa Mariana", 83,
			            "Cambé", 15,
			            "Rolândia", 26,
			            "Ibiporã", 14,
			            "Apucarana", 60,
			            "Arapongas", 50
			        ),
			        "Maringa", Map.of(
			            "Cornelio", 162,
			            "Londrina", 100,
			            "Bandeirantes", 198,
			            "Santa Mariana", 100,
			            "Cambé", 110,
			            "Rolândia", 103,
			            "Ibiporã", 115,
			            "Apucarana", 110,
			            "Arapongas", 95
			        ),
			        "Bandeirantes", Map.of(
			            "Cornelio", 37,
			            "Londrina", 103,
			            "Maringa", 198,
			            "Santa Mariana", 20,
			            "Cambé", 120,
			            "Rolândia", 110,
			            "Ibiporã", 90,
			            "Apucarana", 140,
			            "Arapongas", 130
			        ),
			        "Santa Mariana", Map.of(
			            "Cornelio", 18,
			            "Londrina", 83,
			            "Maringa", 100,
			            "Bandeirantes", 20,
			            "Cambé", 70,
			            "Rolândia", 77,
			            "Ibiporã", 65,
			            "Apucarana", 85,
			            "Arapongas", 75
			        ),
			        "Cambé", Map.of(
			            "Cornelio", 60,
			            "Londrina", 15,
			            "Maringa", 110,
			            "Bandeirantes", 120,
			            "Santa Mariana", 70,
			            "Rolândia", 25,
			            "Ibiporã", 20,
			            "Apucarana", 50,
			            "Arapongas", 40
			        ),
			        "Rolândia", Map.of(
			            "Cornelio", 80,
			            "Londrina", 26,
			            "Maringa", 103,
			            "Bandeirantes", 110,
			            "Santa Mariana", 77,
			            "Cambé", 25,
			            "Ibiporã", 30,
			            "Apucarana", 45,
			            "Arapongas", 35
			        ),
			        "Ibiporã", Map.of(
			            "Cornelio", 55,
			            "Londrina", 14,
			            "Maringa", 115,
			            "Bandeirantes", 90,
			            "Santa Mariana", 65,
			            "Cambé", 20,
			            "Rolândia", 30,
			            "Apucarana", 60,
			            "Arapongas", 50
			        ),
			        "Apucarana", Map.of(
			            "Cornelio", 90,
			            "Londrina", 60,
			            "Maringa", 110,
			            "Bandeirantes", 140,
			            "Santa Mariana", 85,
			            "Cambé", 50,
			            "Rolândia", 45,
			            "Ibiporã", 60,
			            "Arapongas", 20
			        ),
			        "Arapongas", Map.of(
			            "Cornelio", 85,
			            "Londrina", 50,
			            "Maringa", 95,
			            "Bandeirantes", 130,
			            "Santa Mariana", 75,
			            "Cambé", 40,
			            "Rolândia", 35,
			            "Ibiporã", 50,
			            "Apucarana", 20
			        )
			    );
		
		SolucaoSequencial tsp = new SolucaoSequencial(vtDistances);
		long startTime = System.nanoTime();
		String[] shortestPath = tsp.findShortestPath();
		long endTime = System.nanoTime();
		int distance = tsp.pathDistance(shortestPath);
		double durationInMillis = (endTime - startTime) / 1_000_000.0;
		System.out.println("O caminho mais curto é " + Arrays.toString(shortestPath) + " em " +
				distance + " Km.");
		 System.out.printf("Tempo de execução (sequencial): %.4f ms%n", durationInMillis);
	}
}
