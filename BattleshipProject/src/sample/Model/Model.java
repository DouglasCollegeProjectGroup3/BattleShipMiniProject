package sample.Model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private int boardSize;
    private int shipLength;
    private int numShips;

    private Ship ships[] = new Ship[3];

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getShipLength() {
        return shipLength;
    }

    public void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    public int getNumShips() {
        return numShips;
    }

    public void setNumShips(int numShips) {
        this.numShips = numShips;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

    //Model
    public void generateShipLocations() {
        List<String> locations;

        for (int i = 0; i < this.numShips; i++) {

            do {
                locations = this.generateShip();
            } while (this.collision(locations));
            this.ships[i].setLocations(locations);
        }
        System.out.println("Ships array: ");
        System.out.println(this.ships);
    }

    //Model
    public List<String> generateShip() {
        double direction = Math.floor(Math.random() * 2);
        double row, col;

        if (direction == 1) { // horizontal
            row = Math.floor(Math.random() * this.boardSize);
            col = Math.floor(Math.random() * (this.boardSize - this.shipLength + 1));
        } else { // vertical
            row = Math.floor(Math.random() * (this.boardSize - this.shipLength + 1));
            col = Math.floor(Math.random() * this.boardSize);
        }


        List<String> newShipLocations = new ArrayList<>();
        for (int i = 0; i < this.shipLength; i++) {
            if (direction == 1) {
                newShipLocations.add(row + "" + (col + i));

            } else {
                newShipLocations.add((row + i) + "" + col);
            }
        }
        return newShipLocations;
    }

    //Model
    public boolean collision(List<String> locations) {
        for (int i = 0; i < this.numShips; i++) {
            Ship ship = this.ships[i];
            if(ship == null) {
                ship = new Ship();
                ship.setLocations(new ArrayList<>());
                this.ships[i] = ship;
            }
            for (int j = 0; j < locations.size(); j++) {
                if (ship.getLocations().indexOf(locations.get(j)) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //Model
    public boolean isSunk(Ship ship) {
        for (int i = 0; i < this.shipLength; i++)  {
            if (!"hit".equals(ship.getHits().get(i))) {
                return false;
            }
        }
        return true;
    }
}
