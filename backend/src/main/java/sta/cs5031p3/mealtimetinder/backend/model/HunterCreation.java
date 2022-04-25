package sta.cs5031p3.mealtimetinder.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Model for register a hunter account.
 * @author 200011181
 */
@Getter
public class HunterCreation implements Serializable  {
    @Schema(description="username", example = "hunger", required = true)
    @NotNull
    private String username;
    @Schema(description="password", example = "secret!", required = true)
    @NotNull
    private String password;

    @Schema(description="address", example = "St Andrews", required = false)
    private String address;
    @Schema(description="postcode", example = "KY16 121", required = false)
    private String postcode;
}
