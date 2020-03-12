package Core.Product;

import Core.Common.SettingName;

public class ProductBuilder {

    private Product product;

    public ProductBuilder(){
        product = new Product();
    }

    public void addTitle(String title){
        product.add(SettingName.PRODUCTTITLE, title);
    }
    public void addDescription(String description){
        product.add(SettingName.PRODUCTDESCRIPTION, description);
    }
    public void addQuantity(int qta){
        product.add(SettingName.PRODUCTQUANTITY, String.valueOf(qta));
    }
    public void addSellingPrice(double price){
        product.add(SettingName.PRODUCTSELLINGPRICE, String.valueOf(price));
    }
    public void addPuchasePrice(double price){
        product.add(SettingName.PRODUCTPURCHUASEPRICE, String.valueOf(price));
    }
    public void addBrand(String brand){
        product.add(SettingName.PRODUCTBRAND, brand);
    }
    public void addModel(String model){
        product.add(SettingName.PRODUCTMODEL, model);
    }
    public Product getResult(){
        return product;
    }
}
