package ru.nikitavov.avenir.web.message.realization.enitiy.violation;

import ru.nikitavov.avenir.web.message.intefaces.IRequest;

public record FindsViolationRequest(String filter, Integer cameraId, int size, int page, String[] sort) implements IRequest {

}
