import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentManager {
    private DatabaseConnectionManager connectionManager;

    public PaymentManager(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void updatePaymentStatus(String username, String paymentStatus, String paymentMethod, boolean adminFeeBool, boolean tuitionFeeBool, boolean transportFeeBool) {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "UPDATE payment SET payment_status = ?, payment_method = ?, "+
                         "admin_fee = ?, tuition_fee = ?, transport_fee = ? "+
                         "WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, paymentStatus);
            statement.setString(2, paymentMethod);

            if (adminFeeBool){
                statement.setString(3, "1");
            }else{
                statement.setString(3, "0");
            }
            if (tuitionFeeBool){
                statement.setString(4, "1");
            }else{
                statement.setString(4, "0");
            }
            if (transportFeeBool){
                statement.setString(5, "1");
            }else{
                statement.setString(5, "0");
            }

            statement.setString(6, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
    }
}