package magnojr.com.de.roomoccupancyservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsAvailableDTO {

    @PositiveOrZero(message = "numbers of premium rooms should be positive")
    private int freePremiumRooms;
    @PositiveOrZero(message = "numbers of economy rooms should be positive")
    private int freeEconomyRooms;

}
