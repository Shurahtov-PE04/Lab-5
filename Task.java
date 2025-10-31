import java.util.*;

class RouteOptimizer {
    private int[][] distanceMatrix;
    private int cityCount;
    
    public RouteOptimizer(int[][] matrix) {
        this.distanceMatrix = matrix;
        this.cityCount = matrix.length;
    }
    
    private void logCurrentPath(List<Integer> path, int totalLength) {
        System.out.print("Анализируем маршрут: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) System.out.print(" → ");
        }
        System.out.println(" | Протяженность: " + totalLength);
    }
    
    public void findShortestRoute() {
        List<Integer> cities = new ArrayList<>();
        for (int i = 0; i < cityCount; i++) {
            cities.add(i);
        }

        int minRouteLength = Integer.MAX_VALUE;
        List<Integer> bestRoute = new ArrayList<>();
        RouteGenerator generator = new RouteGenerator();

        System.out.println("Запуск расчета маршрутов...");
        
        for (List<Integer> route : generator.generateRoutes(cities)) {
            int currentLength = 0;
            for (int i = 0; i < cityCount - 1; i++) {
                currentLength += distanceMatrix[route.get(i)][route.get(i + 1)];
            }
            currentLength += distanceMatrix[route.get(cityCount - 1)][route.get(0)];

            logCurrentPath(route, currentLength);

            if (currentLength < minRouteLength) {
                minRouteLength = currentLength;
                bestRoute = new ArrayList<>(route);
                System.out.println(">>> Обновлен лучший маршрут! <<<");
            }
        }

        System.out.println("\nИТОГИ РАСЧЕТА:");
        System.out.println("Минимальная протяженность: " + minRouteLength);
        System.out.print("Оптимальный маршрут: ");
        for (int city : bestRoute) {
            System.out.print(city + " → ");
        }
        System.out.println(bestRoute.get(0));
    }
}

class RouteGenerator {
    public <T> List<List<T>> generateRoutes(List<T> elements) {
        List<List<T>> allRoutes = new ArrayList<>();
        generateAllRoutes(elements, 0, allRoutes);
        return allRoutes;
    }

    private <T> void generateAllRoutes(List<T> elements, int currentIndex, List<List<T>> allRoutes) {
        if (currentIndex == elements.size() - 1) {
            allRoutes.add(new ArrayList<>(elements));
            return;
        }
        for (int i = currentIndex; i < elements.size(); i++) {
            Collections.swap(elements, i, currentIndex);
            generateAllRoutes(elements, currentIndex + 1, allRoutes);
            Collections.swap(elements, i, currentIndex);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int[][] cityDistances = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        
        RouteOptimizer optimizer = new RouteOptimizer(cityDistances);
        optimizer.findShortestRoute();
    }
}
