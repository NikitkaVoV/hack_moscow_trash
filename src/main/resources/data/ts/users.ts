import {ESort} from "./resources";

export interface IUsersPayload {
    filter?: string
    page?: number
    size?: number
    sort?: {
        [key: string]: `${ESort}`
    }
}

export interface IUserEntity {
    id: string
    surname: string
    name: string
    patronymic: string
    email: string
}

export interface IUserExpandedEntity extends IUserEntity {

}