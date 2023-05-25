package Level;

// +
public class Level {

    private int[][] levelData;

    public Level(int[][] levelData) {
        this.levelData = levelData;
    }

    public int getSpriteIndex(int x, int y) {                                    // return tile sprite of level at x,y
        return levelData[y][x];
    }

    public int[][] getlevelData() {
        return this.levelData;
    }

}
