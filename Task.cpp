#include <iostream>
#include <vector>
#include <climits>

using namespace std;

class RouteFinder {
private:
    vector<vector<int>> distance_matrix;
    int city_count;
    
    void logCurrentPath(const vector<int>& path, int total_length) {
        cout << "Проверяем маршрут: ";
        for (int i = 0; i < path.size(); i++) {
            cout << path[i];
            if (i < path.size() - 1) cout << " -> ";
        }
        cout << " | Длина: " << total_length << endl;
    }

public:
    RouteFinder(const vector<vector<int>>& matrix) {
        distance_matrix = matrix;
        city_count = matrix.size();
    }

    void calculateOptimalRoute() {
        vector<int> current_route(city_count);
        for (int i = 0; i < city_count; i++) {
            current_route[i] = i;
        }

        int shortest_route = INT_MAX;
        vector<int> optimal_path;

        cout << "Начинаем поиск оптимального маршрута..." << endl;
        
        do {
            int route_length = 0;
            for (int i = 0; i < city_count - 1; i++) {
                route_length += distance_matrix[current_route[i]][current_route[i + 1]];
            }
            route_length += distance_matrix[current_route[city_count - 1]][current_route[0]];

            logCurrentPath(current_route, route_length);

            if (route_length < shortest_route) {
                shortest_route = route_length;
                optimal_path = current_route;
                cout << "!!! Найден новый оптимальный маршрут !!!" << endl;
            }
        } while (next_permutation(current_route.begin(), current_route.end()));

        cout << "\nРЕЗУЛЬТАТ ПОИСКА:" << endl;
        cout << "Оптимальная длина маршрута: " << shortest_route << endl;
        cout << "Маршрут обхода: ";
        for (int i = 0; i < city_count; i++) {
            cout << optimal_path[i] << " → ";
        }
        cout << optimal_path[0] << endl;
    }
};

int main() {
    setlocale(LC_ALL, "");
    
    vector<vector<int>> city_distances = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };

    RouteFinder finder(city_distances);
    finder.calculateOptimalRoute();

    return 0;
}
