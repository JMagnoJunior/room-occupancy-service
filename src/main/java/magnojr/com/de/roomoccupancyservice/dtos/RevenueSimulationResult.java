package magnojr.com.de.roomoccupancyservice.dtos;

import lombok.Data;

@Data
public class RevenueSimulationResult {

    private int roomUsagePremium;
    private int totalRevenueForPremiumRooms;
    private int roomUsageEconomy;
    private int totalRevenueForEconomyRooms;

}
