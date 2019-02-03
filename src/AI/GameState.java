package AI;

public enum GameState {
    WON(100),
    LOSE(-100),
    DRAW(0);

    int value;

    GameState(int value) {
        this.value=value;
    }
}
