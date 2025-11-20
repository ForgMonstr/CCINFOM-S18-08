package dao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Forecasting {
    private final DataSource dataSource;

    public Forecasting(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static class SalesRecord{
        public final String productId;
        public final int qty;
        public final double revenue;

        public SalesRecord(String productId, int qty, double revenue) {
            this.productId = productId;
            this.qty = qty;
            this.revenue = revenue;
        }

        public String toString() {
            return "SalesRecord{" +
                    ", productId='" + productId + '\'' +
                    ", qty=" + qty +
                    ", revenue=" + revenue +
                    '}';
        }    
    }

    public static class DemandRecord{
        public final LocalDate date;
        public final String productId;
        public final int qty;

        public DemandRecord(String productId, int qty) {
            this.productId = productId;
            this.qty = qty;
        }

        public String toString() {
            return "DemandRecord{" +
                    ", productId='" + productId + '\'' +
                    ", qty=" + qty +
                    '}';
        }
    }

    public static class TrendResult {
        public final String productId;
        public final int totalSalesQty;
        public final int totalDemandQty;
        public final double totalRevenue;

        public TrendResult(String productId, int totalSalesQty, int totalDemandQty, double totalRevenue) {
            this.productId = productId;
            this.totalSalesQty = totalSalesQty;
            this.totalDemandQty = totalDemandQty;
            this.totalRevenue = totalRevenue;
        }

        public String toString() {
            return "TrendResult{" +
                    "productId='" + productId + '\'' +
                    ", totalSalesQty=" + totalSalesQty +
                    ", totalDemandQty=" + totalDemandQty +
                    ", totalRevenue=" + totalRevenue +
                    '}';
        }
    }

    public List<SalesRecord> getSalesForecast(){
        String sql = "SELECT date, product_id, qty, revenue FROM sales WHERE date BETWEEN ? and ? ORDER BY date";
        try (Connection c =  dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)){

            try (ResultSet rs = ps.executeQuery()){

                List<SalesRecord> salesRecords = new ArrayList<>();
                while(rs.next()){
                        
                    salesRecords.add(new SalesRecord(
                        rs.getDate("date").toLocalDate(),
                        rs.getString("product_id"),
                        rs.getInt("qty"),
                        rs.getDouble("revenue")
                    ));
                }
                return salesRecords;
            }
        } catch (SQLException e){
            throw new RuntimeException("Error fetching sales forecast", e);
        }
    }

    public List<DemandRecord> getDemands(){
        String sql = "SELECT date, product_id, qty FROM sales ORDER BY date";

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            List<DemandRecord> list = new ArrayList<>();

            while (rs.next()) {
                list.add(new DemandRecord(
                    rs.getDate("date").toLocalDate(),
                    rs.getString("product_id"),
                    rs.getInt("qty")
                ));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching demands", e);
        }
    }

    public TrendResult analyzeTrends(List<Double> salesSer, List<Double> demandSer){
        if(salesSer = null || demandSer == null ||  salesSer.size() !=  demandSer.size()){
            throw new IllegalArgumentException("Invalid Series.");
        }

        double correlation = pearsonCorrelation(salesSer, demandSer);
        return new TrendResult(correlation);
    }

    public List<Double> forecastAverage(List<Double> series, int periods, int windowSize){
        if (series == null || series.size() < windowSize){
            throw  new IllegalArgumentException("Invalid Series.")
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
            return nume/demo;
        }

    }



}
