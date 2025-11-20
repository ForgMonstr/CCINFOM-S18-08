package managers;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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
    public static class IngredientDemand {
        public final String ingredientId;
        public final double totalQty;

        public IngredientDemand(String ingredientId, double totalQty) {
            this.ingredientId = ingredientId;
            this.totalQty = totalQty;
        }

        @Override
        public String toString() {
            return "IngredientDemand{" +
                    "ingredientId='" + ingredientId + '\'' +
                    ", totalQty=" + totalQty +
                    '}';
        }
    }

    public static class SeasonalityReport {
        public final double trendSlope;
        public final double correlationWithTime;
        public final double[] seasonalIndices; // 12 elements, index 0 = January

        public SeasonalityReport(double trendSlope, double correlationWithTime, double[] seasonalIndices) {
            this.trendSlope = trendSlope;
            this.correlationWithTime = correlationWithTime;
            this.seasonalIndices = seasonalIndices;
        }

        @Override
        public String toString() {
            return "SeasonalityReport{" +
                    "trendSlope=" + trendSlope +
                    ", correlationWithTime=" + correlationWithTime +
                    ", seasonalIndices=" + java.util.Arrays.toString(seasonalIndices) +
                    '}';
        }
    }

    public static class ProfitabilityReport {
        public final java.util.List<Double> periodForecasts; // forecasted quantities
        public final double forecastRevenue;
        public final double forecastProfit;

        public ProfitabilityReport(java.util.List<Double> periodForecasts, double forecastRevenue, double forecastProfit) {
            this.periodForecasts = periodForecasts;
            this.forecastRevenue = forecastRevenue;
            this.forecastProfit = forecastProfit;
        }

        @Override
        public String toString() {
            return "ProfitabilityReport{" +
                    "periodForecasts=" + periodForecasts +
                    ", forecastRevenue=" + forecastRevenue +
                    ", forecastProfit=" + forecastProfit +
                    '}';
        }
    }

    public java.util.List<IngredientDemand> projectIngredientDemand(int year, int month) {
        java.time.YearMonth ym = java.time.YearMonth.of(year, month);
        java.sql.Date start = java.sql.Date.valueOf(ym.atDay(1));
        java.sql.Date end = java.sql.Date.valueOf(ym.atEndOfMonth());

        String sql = "SELECT pi.ingredient_id, SUM(od.qty * pi.qty_per_unit) AS total_qty " +
                     "FROM customerorder co " +
                     "JOIN orderdetails od ON co.order_id = od.order_id " +
                     "JOIN product_ingredients pi ON od.product_id = pi.product_id " +
                     "WHERE co.order_date BETWEEN ? AND ? " +
                     "GROUP BY pi.ingredient_id";

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, start);
            ps.setDate(2, end);

            try (ResultSet rs = ps.executeQuery()) {
                java.util.List<IngredientDemand> out = new java.util.ArrayList<>();
                while (rs.next()) {
                    out.add(new IngredientDemand(
                            rs.getString("ingredient_id"),
                            rs.getDouble("total_qty")
                    ));
                }
                return out;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error projecting ingredient demand", e);
        }
    }

    public SeasonalityReport analyzeSeasonalityAndTrend(String productId, int years) {
        if (years < 1) throw new IllegalArgumentException("years must be >= 1");

        java.time.YearMonth endYm = java.time.YearMonth.now();
        java.time.YearMonth startYm = endYm.minusMonths((long) years * 12 - 1);
        java.sql.Date startDate = java.sql.Date.valueOf(startYm.atDay(1));
        java.sql.Date endDate = java.sql.Date.valueOf(endYm.atEndOfMonth());

        String sql = "SELECT YEAR(co.order_date) AS y, MONTH(co.order_date) AS m, SUM(od.qty) AS total " +
                     "FROM customerorder co " +
                     "JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE co.order_date BETWEEN ? AND ? AND od.product_id = ? " +
                     "GROUP BY y, m ORDER BY y, m";

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setString(3, productId);

            try (ResultSet rs = ps.executeQuery()) {
                java.util.Map<java.time.YearMonth, Double> totals = new java.util.HashMap<>();
                while (rs.next()) {
                    int y = rs.getInt("y");
                    int m = rs.getInt("m");
                    double t = rs.getDouble("total");
                    totals.put(java.time.YearMonth.of(y, m), t);
                }

                java.util.List<Double> series = new java.util.ArrayList<>();
                java.util.List<Double> timeIndex = new java.util.ArrayList<>();
                java.time.YearMonth cur = startYm;
                int idx = 0;
                while (!cur.isAfter(endYm)) {
                    series.add(totals.getOrDefault(cur, 0.0));
                    timeIndex.add((double) idx);
                    idx++;
                    cur = cur.plusMonths(1);
                }

                if (series.isEmpty()) {
                    throw new IllegalArgumentException("No historical data found for product: " + productId);
                }

                int n = series.size();
                double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
                for (int i = 0; i < n; i++) {
                    double x = timeIndex.get(i);
                    double yv = series.get(i);
                    sumX += x;
                    sumY += yv;
                    sumXY += x * yv;
                    sumX2 += x * x;
                }
                double denom = n * sumX2 - sumX * sumX;
                double slope = denom == 0 ? 0.0 : (n * sumXY - sumX * sumY) / denom;

                double correlation = pearsonCorrelation(timeIndex, series);

                // seasonal indices (by calendar month) normalized relative to overall monthly average
                double[] monthSums = new double[12];
                int[] monthCounts = new int[12];
                double totalAll = 0;
                cur = startYm;
                for (int i = 0; i < series.size(); i++) {
                    int m = cur.getMonthValue();
                    monthSums[m - 1] += series.get(i);
                    monthCounts[m - 1] += 1;
                    totalAll += series.get(i);
                    cur = cur.plusMonths(1);
                }
                double overallMonthlyAvg = series.size() == 0 ? 0.0 : (totalAll / series.size());
                double[] seasonalIndices = new double[12];
                for (int i = 0; i < 12; i++) {
                    double avg = monthCounts[i] == 0 ? 0.0 : (monthSums[i] / monthCounts[i]);
                    seasonalIndices[i] = overallMonthlyAvg == 0 ? 0.0 : (avg / overallMonthlyAvg);
                }

                return new SeasonalityReport(slope, correlation, seasonalIndices);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error analyzing seasonality and trend", e);
        }
    }

    public ProfitabilityReport forecastProfitability(String productId, int periods, int windowSize) {
        if (periods < 1 || windowSize < 1) throw new IllegalArgumentException("periods and windowSize must be >= 1");

        String sql = "SELECT YEAR(co.order_date) AS y, MONTH(co.order_date) AS m, SUM(od.qty) AS total " +
                     "FROM customerorder co JOIN orderdetails od ON co.order_id = od.order_id " +
                     "WHERE od.product_id = ? " +
                     "GROUP BY y, m ORDER BY y, m";

        java.util.List<Double> series = new java.util.ArrayList<>();
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    series.add(rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching historical demand", e);
        }

        if (series.size() < windowSize) {
            throw new IllegalArgumentException("Not enough historical data for the chosen window size");
        }

        java.util.List<Double> forecasts = forecastAverage(series, periods, windowSize);

        double price = 0.0;
        double cost = 0.0;
        String psql = "SELECT price, cost FROM products WHERE product_id = ?";
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(psql)) {
            ps.setString(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("price");
                    cost = rs.getDouble("cost");
                } else {
                    throw new IllegalArgumentException("Product not found: " + productId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching product price/cost", e);
        }

        double profitPerUnit = price - cost;
        double totalRevenue = 0.0;
        double totalProfit = 0.0;
        for (Double q : forecasts) {
            totalRevenue += q * price;
            totalProfit += q * profitPerUnit;
        }

        return new ProfitabilityReport(forecasts, totalRevenue, totalProfit);
    }


}
