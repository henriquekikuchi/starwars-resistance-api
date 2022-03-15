package br.com.kikuchi.henrique.starwarsapi.model;

public enum ResourcesEnum {
    WEAPON("weapon",4),
    BULLET("bullet",3),
    WATER("water",2),
    FOOD("food",1);

    private final String describe;
    private final int points;

    ResourcesEnum(String describe, int points){
        this.describe = describe;
        this.points = points;
    }

    public String getDescribe() {
        return describe;
    }

    public int getPoints() {
        return points;
    }
}
