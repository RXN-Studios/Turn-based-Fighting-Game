import java.util.Scanner;
import java.util.Random;

public class Game {

    static final int MAX_HEALTH = (rand.nextInt(16) + 5) * 10;
    static final int ATTACK_DAMAGE = (rand.nextInt(4) + 1) * 10;
    static final int HEAL_AMOUNT = (rand.nextInt(4) + 1) * 10;

    Player player;
    Player enemy;

    Action lastPlayerAction = null;

    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    public Game() {
        player = new Player(MAX_HEALTH);
        enemy = new Player(MAX_HEALTH);
    }

    public void start() {
        System.out.println("=== Turn-Based Action Game ===");

        while (player.isAlive() && enemy.isAlive()) {
            showStatus();

            Action playerAction = getPlayerAction();
            Action enemyAction = decideEnemyAction();

            System.out.println("Player chose: " + playerAction);
            System.out.println("Enemy chose: " + enemyAction);

            resolveRound(playerAction, enemyAction);

            lastPlayerAction = playerAction;
            System.out.println("--------------------------------");
        }

        endGame();
    }

    void showStatus() {
        System.out.println("Player Health: " + player.getHealth());
        System.out.println("Enemy Health : " + enemy.getHealth());
    }

    Action getPlayerAction() {
        while (true) {
            System.out.println("Choose action: 1-Attack  2-Reflect  3-Heal");
            int choice = scanner.nextInt();

            if (choice == 1) return Action.ATTACK;
            if (choice == 2) return Action.REFLECT;
            if (choice == 3) return Action.HEAL;

            System.out.println("Invalid input. Try again.");
        }
    }

    Action decideEnemyAction() {

        int enemyHealth = enemy.getHealth();
        int playerHealth = player.getHealth();

        // Rule 1: Low health → prefer healing
        if (enemyHealth <= 30) {
            int roll = random.nextInt(100);
            if (roll < 60) return Action.HEAL;
            else if (roll < 85) return Action.REFLECT;
            else return Action.ATTACK;
        }

        // Rule 2: React to player attack
        if (lastPlayerAction == Action.ATTACK) {
            int roll = random.nextInt(100);
            if (roll < 50) return Action.REFLECT;
            else if (roll < 80) return Action.ATTACK;
            else return Action.HEAL;
        }

        // Rule 3: If enemy is stronger
        if (enemyHealth > playerHealth) {
            int roll = random.nextInt(100);
            if (roll < 60) return Action.ATTACK;
            else return Action.REFLECT;
        }

        // Default behaviour
        int roll = random.nextInt(3);
        return Action.values()[roll];
    }

    void resolveRound(Action p, Action e) {

        // Both Attack
        if (p == Action.ATTACK && e == Action.ATTACK) {
            player.takeDamage(ATTACK_DAMAGE);
            enemy.takeDamage(ATTACK_DAMAGE);
        }

        // Attack vs Reflect
        else if (p == Action.ATTACK && e == Action.REFLECT) {
            player.takeDamage(ATTACK_DAMAGE);
        }
        else if (p == Action.REFLECT && e == Action.ATTACK) {
            enemy.takeDamage(ATTACK_DAMAGE);
        }

        // Attack vs Heal
        else if (p == Action.ATTACK && e == Action.HEAL) {
            enemy.heal(HEAL_AMOUNT);
            enemy.takeDamage(ATTACK_DAMAGE);
        }
        else if (p == Action.HEAL && e == Action.ATTACK) {
            player.heal(HEAL_AMOUNT);
            player.takeDamage(ATTACK_DAMAGE);
        }

        // Reflect vs Reflect
        else if (p == Action.REFLECT && e == Action.REFLECT) {
            // No change
        }

        // Heal vs Heal
        else if (p == Action.HEAL && e == Action.HEAL) {
            player.heal(HEAL_AMOUNT);
            enemy.heal(HEAL_AMOUNT);
        }

        // Reflect vs Heal
        else if (p == Action.REFLECT && e == Action.HEAL) {
            enemy.heal(HEAL_AMOUNT);
        }
        else if (p == Action.HEAL && e == Action.REFLECT) {
            player.heal(HEAL_AMOUNT);
        }
    }

    void endGame() {
        if (player.isAlive()) {
            System.out.println("You win!");
        } else {
            System.out.println("Enemy wins!");
        }
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
