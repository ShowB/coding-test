package com.showb.codingtest.baekjoon.light;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainBack {
    public static void main(String... args) {
        Input input = new Input();
        Calculator calculator = new Calculator(input.getMaxRoomNumber(), input.getSwitchInfos());
        long result = calculator.calculate();

        Printer printer = new Printer(result);
        printer.print();
    }

    static class Input {
        private final int maxRoomNumber;
        private final int[][] switchInfos;

        public Input() {
            Scanner scanner = new Scanner(System.in);

            this.maxRoomNumber = scanner.nextInt();
            int switchCnt = scanner.nextInt();
            this.switchInfos = new int[switchCnt][4];

            for (int i = 0; i < switchCnt; i++) {
                for (int j = 0; j < 4; j++) {
                    switchInfos[i][j] = scanner.nextInt();
                }
            }
        }

        public int getMaxRoomNumber() {
            return maxRoomNumber;
        }

        public int[][] getSwitchInfos() {
            return switchInfos;
        }
    }


    static class Calculator {
        private final int maxRoomNumber;
        private final int[][] switchInfos;

        public Calculator(int maxRoomNumber, int[][] switchInfos) {
            this.maxRoomNumber = maxRoomNumber;
            this.switchInfos = switchInfos;
        }

        public long calculate() {
            List<Room> rooms = this.extractRooms();
            SwitchInRooms switchInRooms = this.extractSwitchInRooms();

            TurnedOnRooms turnedOnRooms = new TurnedOnRooms();
            Room firstRoom = rooms.get(0);
            turnedOnRooms.add(firstRoom);
            turnedOnRooms.visit(firstRoom);
            this.exploreRoom(switchInRooms, firstRoom, turnedOnRooms, firstRoom);

            return turnedOnRooms.size();
        }

        private void exploreRoom(SwitchInRooms switchInRooms, Room currentRoom, TurnedOnRooms turnedOnRooms, Room firstRoom) {
            List<Room> switchesInCurrentRoom = switchInRooms.findByRoom(currentRoom)
                    .getSwitches();
            List<Room> excludeAlreadyTurnedOnRooms = turnedOnRooms.excludeAlreadyTurnedOnRooms(switchesInCurrentRoom);

            turnedOnRooms.add(excludeAlreadyTurnedOnRooms);

            if (!excludeAlreadyTurnedOnRooms.isEmpty()) {
                // 불 켰으면 처음 방부터 다시 탐색
                turnedOnRooms.initEnteredRooms();
                exploreRoom(switchInRooms, firstRoom, turnedOnRooms, firstRoom);
            }

            List<Room> movableRooms = turnedOnRooms.findMovableRooms(currentRoom);

            if (movableRooms.isEmpty()) {
                return;
            }

            for (Room nextRoom : movableRooms) {
                exploreRoom(switchInRooms, nextRoom, turnedOnRooms, firstRoom);
            }
        }

        private List<Room> extractRooms() {
            List<Room> result = new ArrayList<>();

            for (int i = 1; i <= maxRoomNumber; i++) {
                for (int j = 1; j <= maxRoomNumber; j++) {
                    result.add(new Room(i, j));
                }
            }

            return result;
        }

        private SwitchInRooms extractSwitchInRooms() {
            SwitchInRooms switchInRooms = new SwitchInRooms(new ArrayList<>());
            for (int[] switchInfo : switchInfos) {
                Room room = new Room(switchInfo[0], switchInfo[1]);
                Room switchInRoom = new Room(switchInfo[2], switchInfo[3]);

                switchInRooms.findByRoom(room)
                        .getSwitches()
                        .add(switchInRoom);
            }

            return switchInRooms;
        }
    }

    static class Room {
        private final int x;
        private final int y;

        public Room(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean canMoveTo(Room other) {
            return (this.getX() == other.getX() && this.getY() + 1 == other.getY()) ||
                    (this.getX() == other.getX() && this.getY() - 1 == other.getY()) ||
                    (this.getX() + 1 == other.getX() && this.getY() == other.getY()) ||
                    (this.getX() - 1 == other.getX() && this.getY() == other.getY());
        }
    }

    static class SwitchInRoom {
        private final Room room;
        private final List<Room> switches;

        public SwitchInRoom(Room room, List<Room> switches) {
            this.room = room;
            this.switches = switches;
        }

        public Room getRoom() {
            return room;
        }

        public List<Room> getSwitches() {
            return switches;
        }
    }

    static class SwitchInRooms {
        private final List<SwitchInRoom> switchInRooms;

        public SwitchInRooms(List<SwitchInRoom> switchInRooms) {
            this.switchInRooms = switchInRooms;
        }

        public SwitchInRoom findByRoom(Room room) {
            for (SwitchInRoom switchInRoom : switchInRooms) {
                if (switchInRoom.getRoom().getX() == room.getX() && switchInRoom.getRoom().getY() == room.getY()) {
                    return switchInRoom;
                }
            }

            SwitchInRoom newSwitchInRoom = new SwitchInRoom(room, new ArrayList<>());
            switchInRooms.add(newSwitchInRoom);
            return newSwitchInRoom;
        }
    }

    static class TurnedOnRooms {
        private final List<Room> turnedOnRooms;
        private final List<Room> enteredRooms;

        public TurnedOnRooms() {
            this.turnedOnRooms = new ArrayList<>();
            this.enteredRooms = new ArrayList<>();
        }

        public void add(List<Room> rooms) {
            this.turnedOnRooms.addAll(rooms);
        }

        public void add(Room rooms) {
            this.turnedOnRooms.add(rooms);
        }

        public void visit(Room room) {
            if (!this.enteredRooms.contains(room)) {
                this.enteredRooms.add(room);
            }
        }

        public int size() {
            return turnedOnRooms.size();
        }

        public List<Room> findMovableRooms(Room currentRoom) {
            List<Room> result = new ArrayList<>();
            for (Room turnedOnRoom : this.turnedOnRooms) {
                if (!this.enteredRooms.contains(turnedOnRoom) && currentRoom.canMoveTo(turnedOnRoom)) {
                    this.enteredRooms.add(turnedOnRoom);
                    result.add(turnedOnRoom);
                }
            }
            return result;
        }

        public void initEnteredRooms() {
            this.enteredRooms.clear();
        }

        public List<Room> excludeAlreadyTurnedOnRooms(List<Room> rooms) {
            List<Room> result = new ArrayList<>();

            for (Room room : rooms) {
                if (!this.contains(room)) {
                    result.add(room);
                }
            }

            return result;
        }

        private boolean contains(Room room) {
            for (Room turnedOnRoom : turnedOnRooms) {
                if (turnedOnRoom.getX() == room.getX() && turnedOnRoom.getY() == room.getY()) {
                    return true;
                }
            }

            return false;
        }
    }

    static class Printer {
        private final long result;

        public Printer(long result) {
            this.result = result;
        }

        public void print() {
            System.out.println(result);
        }
    }
}
