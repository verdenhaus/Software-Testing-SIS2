package data;

public record Product (
    String title,
    float price,
    Integer length,
    Integer weight
){
    public Product(){
        this(null, 0, null, null);
    }
}
