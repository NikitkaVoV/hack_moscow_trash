package ru.nikitavov.avenir.web.message.realization.enitiy.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nikitavov.avenir.web.message.intefaces.IResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.Coordinate;

public record CameraShortInfoResponse(Integer id, String name, String address,
                                      @JsonProperty("violationsAmount") Integer amountOffense,
                                      Coordinate coordinates
                                      ) implements IResponse {

}
