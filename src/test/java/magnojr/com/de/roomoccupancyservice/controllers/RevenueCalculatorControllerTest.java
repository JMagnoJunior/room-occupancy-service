package magnojr.com.de.roomoccupancyservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import magnojr.com.de.roomoccupancyservice.dtos.RevenueSimulationResult;
import magnojr.com.de.roomoccupancyservice.dtos.RoomsAvailableDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RevenueCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSuccessRevenueCalculation() throws Exception {

        // scenario 1
        RoomsAvailableDTO roomsAvailableDTO = new RoomsAvailableDTO(3, 3);
        RevenueSimulationResult expectedResult = new RevenueSimulationResult();
        expectedResult.setTotalRevenueForPremiumRooms(738);
        expectedResult.setRoomUsagePremium(3);
        expectedResult.setTotalRevenueForEconomyRooms(167);
        expectedResult.setRoomUsageEconomy(3);
        mockRequestToSimulateRevenueEndpoint(roomsAvailableDTO, expectedResult);

        // scenario 2
        roomsAvailableDTO = new RoomsAvailableDTO(7, 5);
        expectedResult = new RevenueSimulationResult();
        expectedResult.setTotalRevenueForPremiumRooms(1054);
        expectedResult.setRoomUsagePremium(6);
        expectedResult.setTotalRevenueForEconomyRooms(189);
        expectedResult.setRoomUsageEconomy(4);
        mockRequestToSimulateRevenueEndpoint(roomsAvailableDTO, expectedResult);

        // scenario 3
        roomsAvailableDTO = new RoomsAvailableDTO(2, 7);
        expectedResult = new RevenueSimulationResult();
        expectedResult.setTotalRevenueForPremiumRooms(583);
        expectedResult.setRoomUsagePremium(2);
        expectedResult.setTotalRevenueForEconomyRooms(189);
        expectedResult.setRoomUsageEconomy(4);
        mockRequestToSimulateRevenueEndpoint(roomsAvailableDTO, expectedResult);

        // scenario 4
        roomsAvailableDTO = new RoomsAvailableDTO(7, 1);
        expectedResult = new RevenueSimulationResult();
        expectedResult.setTotalRevenueForPremiumRooms(1054);
        expectedResult.setRoomUsagePremium(6);
        expectedResult.setTotalRevenueForEconomyRooms(144);
        expectedResult.setRoomUsageEconomy(2);
        mockRequestToSimulateRevenueEndpoint(roomsAvailableDTO, expectedResult);

    }

    @Test
    void testFailRevenueCalculationForNegativeValue() throws Exception {

        RoomsAvailableDTO roomsAvailableDTO = new RoomsAvailableDTO(-1, 1);
        mockMvc.perform(post("/room-revenue-calculator/simulate-revenue")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roomsAvailableDTO)))
                .andExpect(status().is4xxClientError());

        roomsAvailableDTO = new RoomsAvailableDTO(1, -1);
        mockMvc.perform(post("/room-revenue-calculator/simulate-revenue")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roomsAvailableDTO)))
                .andExpect(status().is4xxClientError());

    }

    private void mockRequestToSimulateRevenueEndpoint(RoomsAvailableDTO roomsAvailableDTO,
                                                      RevenueSimulationResult revenueSimulationResult) throws Exception {

        mockMvc.perform(post("/room-revenue-calculator/simulate-revenue")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roomsAvailableDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomUsagePremium").value(revenueSimulationResult.getRoomUsagePremium()))
                .andExpect(jsonPath("$.totalRevenueForPremiumRooms").value(revenueSimulationResult.getTotalRevenueForPremiumRooms()))
                .andExpect(jsonPath("$.roomUsageEconomy").value(revenueSimulationResult.getRoomUsageEconomy()))
                .andExpect(jsonPath("$.totalRevenueForEconomyRooms").value(revenueSimulationResult.getTotalRevenueForEconomyRooms()));

    }

}
