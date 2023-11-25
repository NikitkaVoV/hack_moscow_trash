package ru.nikitavov.avenir.web.message.realization.enitiy.route;

import ru.nikitavov.avenir.web.message.intefaces.IResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PointResponse;

import java.util.Date;

public record RouteInfoResponse(int id, String name, String status, int camerasAmount,
                                Date dateDeparture, Date dateArrival, String wasteType,

                                PointResponse[] points, int[] c) implements IResponse {
}
