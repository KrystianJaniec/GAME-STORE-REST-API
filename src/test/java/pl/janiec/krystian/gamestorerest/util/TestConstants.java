package pl.janiec.krystian.gamestorerest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestConstants {

    public static final long ACTION_ID = 1L;
    public static final long SPORT_ID = 2L;
    public static final Integer PRODUCER_ID = 1;
    public static final String CATEGORY_ACTION = "Action games";
    public static final String CATEGORY_SPORT = "Sport games";
    public static final String ADAM = "Adam";
    public static final String ANNA = "Anna";
    public static final String KOWALSKI = "Kowalski";
    public static final String NOWAK = "Nowak";
    public static final long ADAM_KOWALSKI_ID = 1L;
    public static final long ANNA_NOWAK_ID = 2L;
    public static final String CD_PROJEKT_RED = "CD Projekt Red Company";
    public static final String CDPR = "CD Projekt Red";
    public static final long CDPR_ID = 100L;
    public static final String TWO_K_SPORTS = "2K Games Company";
    public static final String TWO_K = "2K Games";
    public static final long TWO_K_ID = 200L;
    public static final String WITCHER_3 = "The Witcher 3: Wild Hunt";
    public static final String WITCHER_DESCRIPTION = "The Witcher 3: Wild Hunt is a 2015 action role-playing video game developed and published by CD Projekt";
    public static final Double WITCHER_PRICE = 99.99;
    public static final Long WITCHER_ID = 11L;
    public static final String MAFIA_II = "Mafia II";
    public static final String MAFIA_DESCRIPTION = "Mafia II is an open world action-adventure video game developed by 2K Czech and published by 2K Games";
    public static final Double MAFIA_PRICE = 128.90;
    public static final Long MAFIA_ID = 12L;
    public static final Long ORDER_ID = 1L;
    public static final String ORDER_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    public static final Integer ORDER_QUANTITY = 4;
    public static final Double ORDER_PRICE = 359.96;

}
