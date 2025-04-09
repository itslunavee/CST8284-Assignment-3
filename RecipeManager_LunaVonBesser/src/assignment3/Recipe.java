package assignment3;
/*
Recipe is a plain Java class. It has only getters and setters and has very little logic
beyond this.
 */
public class Recipe {
private String title;
private double sugar, eggs, flour, yeast, butter;
private int quantity;

//getters:
	public String getTitle() {
	return title;
	}

	public double getSugar() {
	    return sugar;
	}
	
	public double getEggs() {
	    return eggs;
	}
	
	public double getFlour() {
	    return flour;
	}
	
	public double getYeast() {
	    return yeast;
	}
	
	public double getButter() {
	    return butter;
	}
	
	public int getQuantity() {
	    return quantity;
	}
	
	//setters:
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSugar(double sugar) {
	    this.sugar = sugar;
	}
	
	public void setEggs(double eggs) {
	    this.eggs = eggs;
	}
	
	public void setFlour(double flour) {
	    this.flour = flour;
	}
	
	public void setYeast(double yeast) {
	    this.yeast = yeast;
	}
	
	public void setButter(double butter) {
	    this.butter = butter;
	}
	
	public void setQuantity(int quantity) {
	    this.quantity = quantity;
	}
}

