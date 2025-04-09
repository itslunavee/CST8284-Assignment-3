package assignment3;

//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/*
 * RecipeManager is the class that maintains the list of recipes. It also maintains the
way that the RecipeManagerTest (the driver class) gets things done.
- It will read recipes from your hard drive.

- You have been given a Recipes text file to read. Your software must be able
to read from this specific file provided. You are required not to use absolute
paths, but you must use relative paths.

- It has methods that access the list of Recipes in various ways and is the only
class that Assignment3 should communicate with.

-It will also create a shopping list text file, "shoppinglist.txt", which contains
all the ingredients the baker will need to purchase based on their orders.
 */
public class RecipeManager {
    private List<Recipe> recipes = new ArrayList<>();
    
    public void loadRecipes() {
        try (Scanner scanner = new Scanner(new File("recipelist.txt"))) {
            
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine().trim();
                
                //grabs recipe title
                if (currentLine.startsWith("Recipe ")) {
                    String title = currentLine.substring(7).trim(); // Extract name after "Recipe "
                    Recipe recipe = new Recipe();
                    recipe.setTitle(title);
                    
                    //grabs ingredients from recipe line by line
                    readIngredients(scanner, recipe);
                    recipes.add(recipe);
                }
            }    
        } catch (FileNotFoundException e) {
            System.err.println("Error! recipelist.txt not found!");
        }
    }
    
    
    
    public List<String> getRecipeTitles() {
        List<String> titles = new ArrayList<>();
        for (Recipe recipe : recipes) {
            titles.add(recipe.getTitle());
        }
        return titles;
    }
    
    public void setRecipeQuantity(int recipeNumber, int quantity) {
        if (recipeNumber < 1 || recipeNumber > recipes.size()) {
            throw new IllegalArgumentException("Invalid recipe number");
        }
        Recipe recipe = recipes.get(recipeNumber - 1);
        recipe.setQuantity(Math.max(quantity, 0)); // Prevent negative values
    }
    
    public boolean hasOrder() {
        for (Recipe recipe : recipes) {
            if (recipe.getQuantity() > 0) return true;
        }
        return false;
    }
    
    
    public void readIngredients(Scanner scanner, Recipe recipe) {
        int grabbedIngredients = 0;
        //while loop to continue through the recipe line by line until all 5 ingredients have been grabbed. 
        while(grabbedIngredients < 5 && scanner.hasNextLine()) {
            String ingredient = scanner.nextLine();
            //will allow us to skip a blank line
            if (ingredient.isEmpty()) continue;
            //splits string into the item (flour, sugar, etc) and the amount, using either a space or a colon depending on recipe format
            String [] parts = ingredient.split(" |:");
            String item = parts[0];
            double amount = Double.parseDouble(parts[1]);
            //assigns value to each recipe item
            switch (item) {
                case "flour"  -> recipe.setFlour(amount);
                case "sugar"  -> recipe.setSugar(amount);
                case "eggs"   -> recipe.setEggs(amount);
                case "yeast"  -> recipe.setYeast(amount);
                case "butter" -> recipe.setButter(amount);
            }
            grabbedIngredients++;
        }
    }
    
    public void createShoppingList() {
        // setup variables for all ingredients
        double totalFlour = 0;
        double totalSugar = 0;
        double totalEggs = 0;
        double totalYeast = 0;
        double totalButter = 0;
        
        // calculate the total for each item 
        for (Recipe recipe : recipes) {
            int qty = recipe.getQuantity();
            if (qty > 0) {
                totalFlour += recipe.getFlour() * qty;
                totalSugar += recipe.getSugar() * qty;
                totalEggs += recipe.getEggs() * qty;
                totalYeast += recipe.getYeast() * qty;
                totalButter += recipe.getButter() * qty;
            }
        }
        
        // Write the shopping list to a file
            try {
                FileWriter myWriter = new FileWriter("shoppinglist.txt");
                myWriter.write("Shopping List:\n");
                myWriter.write("-------------\n");
                myWriter.write("flour: " + totalFlour + "\n");
                myWriter.write("sugar: " + totalSugar + "\n");
                myWriter.write("eggs: " + totalEggs + "\n");
                myWriter.write("yeast: " + totalYeast + "\n");
                myWriter.write("butter: " + totalButter + "\n");
                myWriter.close();
                System.out.println("Shopping list created successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
    }
}
