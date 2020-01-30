package magnojr.com.de.roomoccupancyservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        RoomsAvailableDTO roomsAvailableDTO = new RoomsAvailableDTO(3,3);

        mockMvc.perform(post("/room-revenue-calculator/simulate-revenue")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new RoomsAvailableDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomUsagePremium").value("0"))
                .andExpect(jsonPath("$.totalRevenueForPremiumRooms").value("0"))
                .andExpect(jsonPath("$.roomUsageEconomy").value("0"))
                .andExpect(jsonPath("$.totalRevenueForEconomyRooms").value("0"));

    }
}
