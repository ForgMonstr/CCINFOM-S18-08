package managers;

import javax.sql.DataSource'
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Forecasting {
    private final DataSource dataSource;

    public Forecasting(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<SalesRecord> getSalesForecast(LocalDate startDate, LocalDate endDate) throws SQLException{
        String sql = "SELECT date, product_id, qty, revenue FROM sales WHERE date BETWEEN ? and ? ORDER BY date";
        try (Connection c =  dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)){

                ps.setDate(1, Date.valueOf(startDate));
                ps.setDate(2, Date.valueOf(endDate));
                try(ResultSet rs = ps.executeQuery()){

                    List<SalesRecord> salesRecords = new ArrayList<>();
                    while(rs.next(0)){
                        
                        list.add(new SalesRecord(
                            rs.getDate("date").toLocalDate(),
                            rs.getString("product_id"),
                            rs.getInt("qty"),
                            rs.getDouble("revenue")
                        ));
                    }
                    return list;
                }
            }
    }

    public List<DemandRecord> getDemands(){

    }

    public List<Double> forecastAve(){

    }

    public static class SalesRecord{
    }

    public static class DemandRecord{
    }

    public static class TrendResult{
    }
}
