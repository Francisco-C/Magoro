package com.example.magoro.DBobjects;

public class Items {

    public int apple, banana, cereals, salad, cookie, juice, yogurt, muffin, iceCream, pizza, sushi, bolognese;
    public String defaultMagoro;
    public int defaultLivingRoom, defaultKitchen, defaultBedroom;
    public boolean livingRoom1, livingRoom2, livingRoom3, kitchen1, kitchen2, bedRoom1, bedRoom2;
    public boolean magoroRed, magoroLightBlue, magoroDarkBlue, magoroGreen, magoroPurple, magoroOrange, magoroRainbow;

    public Items(){

    }

    public Items(int apple, int banana, int cereals, int salad, int cookie, int juice, int yogurt, int muffin, int iceCream, int pizza,
                 int sushi, int bolognese, String defaultMagoro, boolean magoroRed, boolean magoroLightBlue, boolean magoroDarkBlue,
                 boolean magoroGreen, boolean magoroPurple, boolean magoroOrange, boolean magoroRainbow, int defaultLivingRoom,
                 int defaultKitchen, int defaultBedroom, boolean livingRoom1, boolean livingRoom2, boolean livingRoom3,
                 boolean kitchen1, boolean kitchen2, boolean bedRoom1, boolean bedRoom2) {

        this.apple = apple;
        this.banana = banana;
        this.cereals = cereals;
        this.salad = salad;
        this.cookie = cookie;
        this.juice = juice;
        this.yogurt = yogurt;
        this.muffin = muffin;
        this.iceCream = iceCream;
        this.pizza = pizza;
        this.sushi = sushi;
        this.bolognese = bolognese;
        this.defaultMagoro = defaultMagoro;
        this.magoroRed = magoroRed;
        this.magoroLightBlue = magoroLightBlue;
        this.magoroDarkBlue = magoroDarkBlue;
        this.magoroGreen = magoroGreen;
        this.magoroPurple = magoroPurple;
        this.magoroOrange = magoroOrange;
        this.magoroRainbow = magoroRainbow;
        this.defaultLivingRoom = defaultLivingRoom;
        this.defaultKitchen = defaultKitchen;
        this.defaultBedroom = defaultBedroom;
        this.livingRoom1 = livingRoom1;
        this.livingRoom2 = livingRoom2;
        this.livingRoom3 = livingRoom3;
        this.kitchen1 = kitchen1;
        this.kitchen2 = kitchen2;
        this.bedRoom1 = bedRoom1;
        this.bedRoom2 = bedRoom2;
    }
}
