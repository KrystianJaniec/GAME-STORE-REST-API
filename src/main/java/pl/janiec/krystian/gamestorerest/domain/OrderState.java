package pl.janiec.krystian.gamestorerest.domain;

public enum OrderState {

    CREATED("order created"),
    ORDERED("order ordered"),
    CANCELED("order canceled");

    private final String text;

    OrderState(String text) {
        this.text = text;
    }
}
