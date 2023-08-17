package bg.softuni.mobilele.models.dtos;

import bg.softuni.mobilele.models.enums.EngineEnum;
import jakarta.validation.constraints.NotNull;

public class AddOfferDTO {

    @NotNull
    private EngineEnum engine;

    public EngineEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineEnum engine) {
        this.engine = engine;
    }
}
