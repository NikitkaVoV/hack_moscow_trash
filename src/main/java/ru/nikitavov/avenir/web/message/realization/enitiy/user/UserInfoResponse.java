package ru.nikitavov.avenir.web.message.realization.enitiy.user;

public record UserInfoResponse(int id, String surname, String name, String patronymic, @javax.validation.constraints.Email String email) {
}
