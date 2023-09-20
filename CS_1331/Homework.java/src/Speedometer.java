public class Speedometer {
    // My name is Matthias and I have another class in the same exact room right before this one
    public static void main(String[] args) {
        int kilometers = 50;
        double kmPerMile = 1.60934;
        double hours = .54;
        String name = "Matthias";
        double miles = kilometers / kmPerMile;

        double milesTrunc = ((double)((int)(miles * 1000)))/1000;


        double speed = ((double)((int)((milesTrunc / hours)*100)))/100;


        System.out.println(name + " drove at a speed of " + speed + " mph for " + milesTrunc + " miles!");
        

    }
}