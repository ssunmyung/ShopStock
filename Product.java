package project;

public class Product {
	private String name; // 상품명
    private int price; // 가격
    private int stock; // 재고량

    // 생성자
    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // getter, setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public boolean sell(int quantity) {
        if (quantity > stock) {
            return false;
        } else {
            stock -= quantity;
            return true;
        }
    }

    public void addStock(int quantity) {
        stock += quantity;
    }
}
