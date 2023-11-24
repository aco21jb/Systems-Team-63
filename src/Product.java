import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    private String productCode;
    private String productName;
    private  BigDecimal retailPrice;
    private Integer stock;
    private String gauge;
    private String eraCode;
    private String dccCode;

    public Product(String productCode, String productName, BigDecimal retailPrice, Integer stock, String gauge, String eraCode, String dccCode){
        this.setProductCode(productCode);
        this.setProductName(productName);
        this.setRetailPrice(retailPrice);
        this.setStock(stock);
        this.setGauge(gauge);
        this.setEraCode(eraCode);
        this.setDccCode(dccCode);
    }

    //get methods
    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public String getGauge() {
        return gauge;
    }

    public String getEraCode() {
        return eraCode;
    }

    public String getDccCode() {
        return dccCode;
    }

    //set methods
    public void setProductCode(String productCode) {
        if (isProductCodeValid(productCode)) {
            this.productCode = productCode;
        } else {
            throw new IllegalArgumentException("Invalid Product Code");
        }
    }

    public void setProductName(String productName){
        if (isProductNameValid(productName)) {
            this.productName = productName;
        } else {
            throw new IllegalArgumentException("Invalid Product Name");
        }
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        if (isRetailPriceValid(retailPrice)) {
            retailPrice = retailPrice.setScale(2, RoundingMode.HALF_UP);
            this.retailPrice = retailPrice;
        } else {
            throw new IllegalArgumentException("Invalid retail price");
        }
    }

    public void setStock(Integer stock) {
        if (isStockValid(stock)){
            this.stock = stock;
        } else {
            throw new IllegalArgumentException("Invalid Stock entry");
        }
    }

    public void setGauge(String gauge) {
        if (isGaugeValid(gauge)){
            this.gauge = gauge;
        } else {
            throw new IllegalArgumentException("Invalid gauge value");
        }
    }

    public void setEraCode(String eraCode) {
        if (isEraCodeValid(eraCode)) {
            this.eraCode = eraCode;
        } else {
            throw new IllegalArgumentException("Invalid Era Code");
        }
    }

    public void setDccCode(String dccCode) {
        if (isDccCodeValid(dccCode)){
            this.dccCode = dccCode;
        } else {
            throw new IllegalArgumentException("Invalid DCC Code");
        }
    }

    // validator methods
    private boolean isProductCodeValid(String productCode){
        return productCode != null && productCode.length() <= 10;
    }

    private boolean isProductNameValid(String productName){
        return productName != null && productName.length() <= 200;
    }

    private boolean isRetailPriceValid(BigDecimal retailPrice){
        return retailPrice != null && retailPrice.compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean isStockValid(Integer stock){
        return stock != null && stock >= 0;
    }

    private boolean isGaugeValid(String gauge){
        return gauge.length() <= 10;
    }

    private boolean isEraCodeValid(String eraCode){
        return eraCode.length() <= 20;
    }

    private boolean isDccCodeValid(String dccCode){
        return dccCode.length() <= 50;
    }

}
