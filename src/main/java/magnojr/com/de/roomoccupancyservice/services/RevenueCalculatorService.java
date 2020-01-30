package magnojr.com.de.roomoccupancyservice.services;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RevenueCalculatorService {

    public static void main(String[] args) {
        List<Integer> clients = Arrays.asList(23, 45, 155, 374, 22, 99, 100, 101, 115, 209);
        clients.sort(Comparator.reverseOrder());

        int premiumRooms = 2;
        int economicRooms = 7;
        // build list of premium
        PriorityQueue<Integer> premiumClients = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> economicClients = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < clients.size(); i++) {
            int client = clients.get(i);
            if (client >= 100) {
                premiumClients.add(client);
            } else {
                economicClients.add(client);
            }
        }

        int usagePremium = 0;
        int removedElements = 0;

        while(!premiumClients.isEmpty() && removedElements < premiumRooms ) {
            usagePremium += premiumClients.poll();
            removedElements++;
        }

        int vacantPremiumRooms = premiumRooms - removedElements;

        int usageEconomic = 0;
        removedElements = 0;
        while(!economicClients.isEmpty() && removedElements < economicRooms + vacantPremiumRooms) {
            usageEconomic += economicClients.poll();
            removedElements++;
        }

        System.out.println(usagePremium);
        System.out.println(usageEconomic);
    }
}
