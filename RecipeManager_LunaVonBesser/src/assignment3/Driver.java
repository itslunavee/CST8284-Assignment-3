package assignment3;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
/*
This class contains the main() method. It will generate all
the menus, read input from the user, and make calls to RecipeManager.
Driver should not directly access the recipe objects.
 */
public class Driver {
    private RecipeManager recipeManager;
    private Scanner scanner;

    public static void main(String[] args) {
        Driver driver = new Driver(); 
        driver.recipeManager = new RecipeManager(); 
        driver.scanner = new Scanner(System.in);    
        driver.recipeManager.loadRecipes();
        driver.runMenu();
    }

    private void runMenu() {
        boolean exit = false;
        while (!exit) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                menuSelection(choice);
                exit = (choice == 4);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. View Recipes");
        System.out.println("2. Place Order");
        System.out.println("3. Generate/Save Shopping List");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private void menuSelection(int choice) {
        switch (choice) {
            case 1 -> displayRecipes();
            case 2 -> placeOrder();
            case 3 -> generateShoppingList();
            case 4 -> System.out.println("Exiting");
            default -> System.out.println("Invalid choice");
        }
    }

    private void displayRecipes() {
        List<String> titles = recipeManager.getRecipeTitles();
        if (titles.isEmpty()) {
            System.out.println("No recipes loaded.");
            return;
        }
        System.out.println("\nAvailable Recipes:");
        for (int i = 0; i < titles.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, titles.get(i));
        }
    }

    private void placeOrder() {
        displayRecipes();
        List<String> titles = recipeManager.getRecipeTitles();
        if (titles.isEmpty()) return;

        try {
            System.out.print("Enter recipe number: ");
            int recipeNumber = scanner.nextInt();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (recipeNumber < 1 || recipeNumber > titles.size()) {
                System.out.println("Invalid recipe number.");
                return;
            }
            if (quantity < 0) {
                System.out.println("Quantity cannot be negative. Setting to 0.");
                quantity = 0;
            }

            recipeManager.setRecipeQuantity(recipeNumber, quantity);
            System.out.printf("Ordered: %s x%d%n", titles.get(recipeNumber - 1), quantity);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Numbers only.");
            scanner.nextLine();
        }
    }

    private void generateShoppingList() {
        if (!recipeManager.hasOrder()) {
            System.out.println("No orders placed.");
            return;
        }

        System.out.print("Save shopping list to file? (yes|no): ");
        String response = scanner.nextLine().trim();
        if (response.equalsIgnoreCase("yes")) {
            recipeManager.createShoppingList();
        } else {
            System.out.println("Shopping list not saved.");
        }
    }
}
