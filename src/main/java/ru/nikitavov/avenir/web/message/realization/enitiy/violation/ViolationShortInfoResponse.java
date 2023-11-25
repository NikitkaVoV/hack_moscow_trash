package ru.nikitavov.avenir.web.message.realization.enitiy.violation;

import ru.nikitavov.avenir.web.message.intefaces.IResponse;

import java.util.Date;

public record ViolationShortInfoResponse(Integer id, String violationType, String criminal, Date date, String address,
                                         Integer cameraId) implements IResponse {
}
