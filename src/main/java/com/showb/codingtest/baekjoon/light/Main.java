package com.showb.codingtest.baekjoon.light;

import java.util.*;

public class Main {
    static int N, M;
    static boolean[][] lit;
    static boolean[][] visited;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    static class Location {
        int x;
        int y;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        List<Location>[][] switches = new ArrayList[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                switches[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int a = sc.nextInt();
            int b = sc.nextInt();
            switches[x][y].add(new Location(a, b));
        }

        lit = new boolean[N + 1][N + 1];
        lit[1][1] = true;

        int result = 1;
        while (true) {
            boolean turnedOnNewLight = false;
            visited = new boolean[N + 1][N + 1];

            // 1. 현재 불 켜진 방을 기반으로 방문 가능한 곳 탐색 (BFS)
            Queue<Location> queue = new LinkedList<>();
            if (lit[1][1]) {
                queue.add(new Location(1, 1));
                visited[1][1] = true;
            }

            while (!queue.isEmpty()){
                Location current = queue.poll();

                for (int i = 0; i < 4; i++){
                    int nx = current.x + dx[i];
                    int ny = current.y + dy[i];

                    if(nx > 0 && ny > 0 && nx <= N && ny <= N && !visited[nx][ny] && lit[nx][ny]){
                        visited[nx][ny] = true;
                        queue.add(new Location(nx, ny));
                    }
                }
            }


            // 2. 방문 가능한 모든 방의 스위치를 켠다.
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (visited[i][j]) { // 방문 가능한 방이라면
                        for (Location target : switches[i][j]) {
                            if (!lit[target.x][target.y]) {
                                lit[target.x][target.y] = true;
                                turnedOnNewLight = true;
                                result++;
                            }
                        }
                    }
                }
            }

            // 새로 켠 불이 없다면 더 이상 진행할 수 없으므로 종료
            if (!turnedOnNewLight) {
                break;
            }
        }

        System.out.println(result);
    }
}
