package magnojr.com.de.roomoccupancyservice.services;

import magnojr.com.de.roomoccupancyservice.dtos.RevenueSimulationResult;
import magnojr.com.de.roomoccupancyservice.dtos.RoomsAvailableDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class RevenueCalculatorService {

    public RevenueSimulationResult simulateRevenue(RoomsAvailableDTO roomsAvailableDTO) {
        List<Integer> defaultClients = Arrays.asList(23, 45, 155, 374, 22, 99, 100, 101, 115, 209);

        return process(roomsAvailableDTO.getFreePremiumRooms(),
                roomsAvailableDTO.getFreeEconomyRooms(),
                defaultClients);
    }

    private RevenueSimulationResult process(final int premiumRooms, final int economicRooms, final List<Integer> clients) {

        PriorityQueue<Integer> premiumClients = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> economicClients = new PriorityQueue<>(Collections.reverseOrder());

        for (Integer client : clients) {
            if (client >= 100) {
                premiumClients.add(client);
            } else {
                economicClients.add(client);
            }
        }

        int totalRevenueForPremiumRooms = 0;
        int occupiedPremiumRooms = 0;
        while (!premiumClients.isEmpty() && occupiedPremiumRooms < premiumRooms) {
            totalRevenueForPremiumRooms += premiumClients.poll();
            occupiedPremiumRooms++;
        }

        int vacantPremiumRooms = premiumRooms - occupiedPremiumRooms;

        int totalRevenueForEconomyRooms = 0;
        int occupiedEconomyRooms = 0;
        while (!economicClients.isEmpty() && occupiedEconomyRooms < economicRooms + vacantPremiumRooms) {
            totalRevenueForEconomyRooms += economicClients.poll();
            occupiedEconomyRooms++;
        }

        RevenueSimulationResult revenueSimulationResult = new RevenueSimulationResult();
        revenueSimulationResult.setRoomUsagePremium(occupiedPremiumRooms);
        revenueSimulationResult.setRoomUsageEconomy(occupiedEconomyRooms);
        revenueSimulationResult.setTotalRevenueForPremiumRooms(totalRevenueForPremiumRooms);
        revenueSimulationResult.setTotalRevenueForEconomyRooms(totalRevenueForEconomyRooms);
        return revenueSimulationResult;
    }

}
