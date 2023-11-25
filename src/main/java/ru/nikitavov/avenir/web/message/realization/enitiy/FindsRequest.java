package ru.nikitavov.avenir.web.message.realization.enitiy;

import ru.nikitavov.avenir.web.message.intefaces.IRequest;

public record FindsRequest(String filter, int size, int page, String[] sort) implements IRequest {

}
