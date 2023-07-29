import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentManager {
    private DatabaseConnectionManager connectionManager;

    public PaymentManager(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public boolean insertPaymentStatus(int userID, String paymentStatus, String paymentMethod, boolean adminFeeBool, boolean tuitionFeeBool, String otherOption) {
        try (Connection connection = connectionManager.getConnection()) {
            String sql = "INSERT INTO payment (userID, payment_status, payment_method, admin_fee, tuition_fee, Other) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setString(2, paymentStatus);
            statement.setString(3, paymentMethod);
            statement.setBoolean(4, adminFeeBool);
            statement.setBoolean(5, tuitionFeeBool);
            statement.setString(6, otherOption);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
        return false;
    }

}
