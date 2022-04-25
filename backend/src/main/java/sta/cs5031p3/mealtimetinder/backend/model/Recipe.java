package sta.cs5031p3.mealtimetinder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @Setter
    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    @JsonIgnore
    @Setter
    private Meal meal;
}
