package trainStore;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {
    public void addProduct(Connection con, trainStore.Product product) throws SQLException {
        try {
            String insertStatement = "INSERT INTO PRODUCTS (productCode, brandName, productName, retailPrice, stock," +
            "gauge, eraCode, dccCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepedStatement = con.prepareStatement(insertStatement);
            prepedStatement.setString(1, product.getProductCode());
            prepedStatement.setString(2, product.getBrandName());
            prepedStatement.setString(3, product.getProductName());
            prepedStatement.setBigDecimal(4, product.getRetailPrice());
            prepedStatement.setInt(5, product.getStock());
            prepedStatement.setString(6, product.getGauge());
            prepedStatement.setString(7, product.getEraCode());
            prepedStatement.setString(8, product.getDccCode());

            prepedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}