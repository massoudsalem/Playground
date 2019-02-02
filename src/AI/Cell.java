package AI;

public enum Cell{
    EMPTY(0),
    PLAYER1(1),
    PLAYER2(2);

    byte value;

    Cell(int value) {
        this.value=(byte)value;
    }
}