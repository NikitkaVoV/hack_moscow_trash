import {ESort, ICoordinates} from "./resources";

export interface IGarbagesPayload {
    filter?: string
    page?: number
    size?: number
    sort?: {
        [key: string]: `${ESort}`
    }
}

export interface IGarbageEntity {
    id: string
    name: string
    dateCreation: string
    photo: string
    coordinates: ICoordinates
    garbageType: string
}

export interface IGarbageExpandedEntity extends IGarbageEntity {

}

export interface IGarbagePostPutPayload {
    id: string
    name: string
    dateCreation: string
    photo: string
    coordinates: ICoordinates
    garbageType: string
}
