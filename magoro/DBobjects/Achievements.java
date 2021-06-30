package com.example.magoro.DBobjects;

public class Achievements {

    public float walk2Km, walk20Km, walk100Km, walk1000Km, walk2H, walk10H, walk50H, walk500H;
    public int sleep2, sleep10, sleep30, sleep100, food30, food250, food500, food1000, NumberMagoros, Main;
    public boolean walk2KmCompleted, walk20KmCompleted, walk100KmCompleted, walk1000KmCompleted, walk2HCompleted,
            walk10HCompleted, walk50HCompleted, walk500HCompleted, sleep2Completed, sleep10Completed,sleep30Completed,
            sleep100Completed, food30Completed, food250Completed, food500Completed, food1000Completed,
            NumberMagorosCompleted, MainCompleted;

    public Achievements(){

    }

    public Achievements(float walk2Km, float walk20Km, float walk100Km, float walk1000Km, float walk2H, float walk10H, float walk50H,
                        float walk500H, int sleep2, int sleep10, int sleep30, int sleep100, int food30, int food250, int food500,
                        int food1000, int numberMagoros, int main, boolean walk2KmCompleted, boolean walk20KmCompleted,
                        boolean walk100KmCompleted, boolean walk1000KmCompleted, boolean walk2HCompleted, boolean walk10HCompleted,
                        boolean walk50HCompleted, boolean walk500HCompleted, boolean sleep2Completed, boolean sleep10Completed,
                        boolean sleep30Completed, boolean sleep100Completed, boolean food30Completed, boolean food250Completed,
                        boolean food500Completed, boolean food1000Completed, boolean numberMagorosCompleted, boolean mainCompleted) {

        this.walk2Km = walk2Km;
        this.walk20Km = walk20Km;
        this.walk100Km = walk100Km;
        this.walk1000Km = walk1000Km;
        this.walk2H = walk2H;
        this.walk10H = walk10H;
        this.walk50H = walk50H;
        this.walk500H = walk500H;
        this.sleep2 = sleep2;
        this.sleep10 = sleep10;
        this.sleep30 = sleep30;
        this.sleep100 = sleep100;
        this.food30 = food30;
        this.food250 = food250;
        this.food500 = food500;
        this.food1000 = food1000;
        this.NumberMagoros = numberMagoros;
        this.Main = main;
        this.walk2KmCompleted = walk2KmCompleted;
        this.walk20KmCompleted = walk20KmCompleted;
        this.walk100KmCompleted = walk100KmCompleted;
        this.walk1000KmCompleted = walk1000KmCompleted;
        this.walk2HCompleted = walk2HCompleted;
        this.walk10HCompleted = walk10HCompleted;
        this.walk50HCompleted = walk50HCompleted;
        this.walk500HCompleted = walk500HCompleted;
        this.sleep2Completed = sleep2Completed;
        this.sleep10Completed = sleep10Completed;
        this.sleep30Completed = sleep30Completed;
        this.sleep100Completed = sleep100Completed;
        this.food30Completed = food30Completed;
        this.food250Completed = food250Completed;
        this.food500Completed = food500Completed;
        this.food1000Completed = food1000Completed;
        this.NumberMagorosCompleted = numberMagorosCompleted;
        this.MainCompleted = mainCompleted;
    }
}
