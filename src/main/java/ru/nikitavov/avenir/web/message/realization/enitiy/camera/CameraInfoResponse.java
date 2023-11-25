package ru.nikitavov.avenir.web.message.realization.enitiy.camera;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nikitavov.avenir.web.message.intefaces.IResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.Coordinate;

import java.util.Date;

public record CameraInfoResponse(Integer id, String name, String address,
                                 @JsonProperty("camera_url") String cameraUrl,
                                 @JsonProperty("amount_offense") Integer amountOffense,
                                 @JsonProperty("last_date_detection") Date lastDateDetect,
                                 String offender,
                                 @JsonProperty("last_detection_elements") CameraDetectionElementResponse[] lastDetectionElements,
                                 Coordinate coordinates
                                 ) implements IResponse {

    public record CameraDetectionElementResponse() implements IResponse {

    }
}
