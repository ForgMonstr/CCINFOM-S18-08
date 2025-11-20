package managers;

import dao.ForecastingDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Forecasting {
   private final ForecastingDAO forecastingDAO;

    public Forecasting() {
        this.forecastingDAO = new ForecastingDAO();
   }

   public List<SalesRecord> getForecast(){
    return forecastingDAO.getSalesForecast();
   }

   public List<DemandRecord> getDemandForecast(){
    return forecastingDAO.getDemands();
   }

   public TrendResult getanalyzedResults(List<Double> salesSer, List<Double> demandSer){
    return forecastingDAO.analyzeTrends(salesSer, demandSer);
   }

   public List<Double> getForecastAverage(List<Double> series, int period, int size){
    return forecastingDAO.forecastAverage(series, period, size);
   }

}
