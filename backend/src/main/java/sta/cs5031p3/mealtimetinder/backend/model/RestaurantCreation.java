package sta.cs5031p3.mealtimetinder.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Model for storing restaurant account registration information.
 * @author 200011181
 */
@Getter
@Schema(name = "Restaurant register form")
public class RestaurantCreation implements Serializable {
    @Schema(description="username", example = "subway", required = true)
    @NotNull
    private String username;
    @NotNull
    @Schema(description="password", example = "1204578616", required = true)
    private String password;
    @Schema(description="description", example = "Subway - Fast food", required = true)
    @NotNull
    private String description;
    @NotNull
    @Schema(description="address", example = "St Andrews", required = true)
    private String address;
    @NotNull
    @Schema(description="postcode", example = "KY16 121", required = true)
    private String postcode;
}
