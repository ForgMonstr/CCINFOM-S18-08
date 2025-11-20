package managers;

import javax.sql.DataSource;
import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;

public class Forecasting {
    private final DataSource dataSource;

    public Forecasting(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static class DemandRecord{
        public final LocalDate date;
        public final String productId;
        public final int qty;
        public final double revenue;

        public DemandRecord(String productId, int qty, LocalDate date, double revenue) {
            this.productId = productId;
            this.qty = qty;
            this.date = date;
            this.revenue = revenue;
        }

        public String toString() {
            return "DemandRecord{" +
                    "date=" + date +
                    ", productId='" + productId + '\'' +
                    ", qty=" + qty +
                    ", revenue=" + revenue +
                    '}';
        }
    }

    public static class TrendResult {
        public final double correlation;

        public TrendResult(double correlation){
            this.correlation = correlation;
        }

    }

    public List<DemandRecord> getRecords(java.sql.Date startDate, java.sql.Date endDate){
        String sql = "SELECT co.order_date, od.product_id, od.qty, od.subtotal " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE co.order_date BETWEEN ? AND ? " +
                     "ORDER BY co.order_date, od.product_id";

        try (Connection c =  dataSource.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){

            ps.setDate(1, startDate);
            ps.setDate(2, endDate);

            try (ResultSet rs = ps.executeQuery()){

                List<DemandRecord> records = new ArrayList<>();
                while(rs.next()){
                        
                    records.add(new DemandRecord(
                        rs.getString("product_id"),
                        rs.getInt("qty"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("subtotal")
                    ));
                }
                return records;
            }
        } catch (SQLException e){
            throw new RuntimeException("Error fetching records", e);
        }
    }

    public List<DemandRecord> getDemands(java.sql.Date startDate, java.sql.Date endDate){
        String sql = "SELECT co.order_date, od.product_id, od.qty " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE co.order_date BETWEEN ? AND ? " +
                     "ORDER BY co.order_date, od.product_id";

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)){
                ps.setDate(1, startDate);
                ps.setDate(2, endDate);

             try(ResultSet rs = ps.executeQuery()){

                List<DemandRecord> list = new ArrayList<>();

                while (rs.next()) {
                    list.add(new DemandRecord(
                        rs.getString("product_id"),
                        rs.getInt("qty"),
                        rs.getDate("order_date").toLocalDate(),
                        0.0
                ));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching demands", e);
        }
    }

    public TrendResult analyzeTrends(List<Double> salesSer, List<Double> demandSer){
        if(salesSer == null || demandSer == null ||  salesSer.size() !=  demandSer.size()){
            throw new IllegalArgumentException("Invalid Series.");
        }

        double correlation = pearsonCorrelation(salesSer, demandSer);
        return new TrendResult(correlation);
    }

    public List<Double> forecastAverage(List<Double> series, int periods, int windowSize){
        if (series == null || series.size() < windowSize){
            throw  new IllegalArgumentException("Invalid Series.");
        }

        List<Double> forecast = new ArrayList<>();
        List<Double> working = new ArrayList<>(series);

        for(int p=0; p < periods; p++){
            int start = working.size() - windowSize;
            double sum = 0.0;

            for(int i=start; i < working.size(); i++){
                    sum =  sum + working.get(i);
            }

            double avg = sum/windowSize;
            forecast.add(avg);
            working.add(avg);
        }
        return forecast;
    }

    private double pearsonCorrelation(List<Double> x, List<Double> y){
        int n = x.size();
        double sumX =  0.0;
        double sumY = 0.0;
        double sumXY = 0.0;
        double sumX2 = 0.0;
        double sumY2 = 0.0;

        for(int i=0; i < n; i++){
            double xi = x.get(i);
            double yi = y.get(i);

            sumX = sumX + xi;
            sumY = sumY + yi;
            sumXY = sumXY + xi * yi;
            sumX2 = sumX2 + xi * xi;
            sumY2 = sumY2 + yi * yi;
        }

        double nume = n * sumXY - sumX *  sumY;
        double deno = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        if(deno == 0){
            return 0.0;
        } else{
            return nume/deno;
        }

    }

}
