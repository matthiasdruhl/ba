import java.util.Random;
import java.util.Scanner;


public class PokemonBattle{
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        String myPokemon;
        String rivalPokemon;
       
        Double rivalPokemonHealth = Rounding((double)rand.nextInt(20) + 40);
        System.out.println(rivalPokemonHealth);

        System.out.print("Enter your Pokemon's nickname: ");
        myPokemon = scan.nextLine().trim();
        System.out.print("Enter your rival Pokemon's nickname: ");
        rivalPokemon = scan.nextLine().trim();
        System.out.printf("Your rival has chosen %s to fight, which has %.2f health.\n",rivalPokemon,rivalPokemonHealth);
        int turns = 0;

        do {
            AttackType attack = AttackType.values()[rand.nextInt(3)];
            double damage = 0;
            switch (attack){
                case SCRATCH:
                    int scratchNum = rand.nextInt(2) + 1;
                    Double scratchDamage = Rounding(rand.nextDouble() * 5 + 1);
                    damage = Rounding(scratchNum * scratchDamage);
                case SURF:
                    damage = Rounding(rand.nextDouble() * 9 + 2);
                case TACKLE:
                    damage = Rounding(rand.nextDouble() * 2 + 7);
                }
            rivalPokemonHealth = rivalPokemonHealth - damage;
            System.out.printf("%s used %s and did %.2f damage. Your rival has %.2f health remaining.\n",rivalPokemon,attack,damage,(rivalPokemonHealth > 0 ? rivalPokemonHealth : 0));
            turns++;
            }
            while (rivalPokemonHealth >= 0);
            
            System.out.printf("%s fainted after %d turns!\n",rivalPokemon,turns);

            Double prizeMoney = rand.nextDouble() * 1200 + 1200;
            System.out.printf("You have earned $%.2f",prizeMoney);
        } 
    private static double Rounding(double x){
        x = ((double)Math.round(x*100))/100;
        return x;
    }

    }


