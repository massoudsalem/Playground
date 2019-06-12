package AI;

public enum GameState {
    WON(10),
    LOSE(-10),
    DRAW(0);

    int value;

    GameState(int value) {
        this.value=value;
    }
}
