public class EuclideanAlgorithm {
    public static void main(String[] args) {
        int num1 = 455;
        int num2 = 8;
        int steps = 0;
        int dividend = num1;
        int divisor = num2;
        int gcd;
        int quotient;
        int remainder;
        System.out.printf("Finding the greatest common divisor of %d and %d\n",num1,num2); //Figure out how to do this
        if (divisor > dividend) {
            System.out.println("These outputs would have caused an unnecessary step.");
            dividend = num2;
            divisor = num1;
        } else {
            System.out.println("An extra step was avoided.");
        }
        do {
            steps++;
            quotient = dividend / divisor;
            remainder = dividend % divisor;
            System.out.printf("Step %d: %d = %d * %d + %d\n",steps,dividend,divisor,quotient,remainder); // Figure out how to do it
            gcd = divisor;
            dividend = divisor;
            divisor = remainder;
        } 
        while (remainder > 0);
        System.out.println("The GCD is " + gcd); //figure out how to do this
        switch (steps){
            case 1:
                System.out.print("Only one step was needed!");
                break;
            case 2:
                System.out.println("Two steps were taken!");
                break;
            case 3:
                System.out.println("This process took three steps!");
                break;
            case 4:
                System.out.println("Wow! Four steps.");
                break;
            default:
                System.out.printf("%d steps is a lot of steps!\n",steps);
        }
        String prime = (gcd == 1) ? ("relatively prime"):("not reletively prime");
        System.out.printf("%d and %d are %s.",num1,num2,prime);
        

    }
}
