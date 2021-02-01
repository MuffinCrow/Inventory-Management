package library;

public class Product {
	
	private int id;
	private String name;
	private String description;
	private double unitPrice;
	private int quantity;
	
	public Product(int id, String name, String description, double unitPrice, int quantity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getUnitPrice() {
		return unitPrice;
	}
    
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void rentMe() {
		if (quantity > 0) {
			quantity--;
		}
	}
	  
	public void returnMe() {
			quantity++;
	}
}