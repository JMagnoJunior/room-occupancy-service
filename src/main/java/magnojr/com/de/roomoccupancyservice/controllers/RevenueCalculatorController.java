package magnojr.com.de.roomoccupancyservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magnojr.com.de.roomoccupancyservice.dtos.RevenueSimulationResult;
import magnojr.com.de.roomoccupancyservice.dtos.RoomsAvailableDTO;
import magnojr.com.de.roomoccupancyservice.services.RevenueCalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping({"", "/v1"})
@RequiredArgsConstructor
public class RevenueCalculatorController {

    private final RevenueCalculatorService revenueCalculatorService;

    @PostMapping(value = "/room-revenue-calculator/simulate-revenue")
    public RevenueSimulationResult simulateRevenue(@RequestBody @Valid RoomsAvailableDTO roomsAvailableDTO) {
        return revenueCalculatorService.simulateRevenue(roomsAvailableDTO);
    }


}
