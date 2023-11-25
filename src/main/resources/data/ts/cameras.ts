import {ESort, ICoordinates} from "./resources";

export interface ICamerasPayload {
    filter?: string
    page?: number
    size?: number
    sort?: {
        [key: string]: `${ESort}`
    }
}

export interface ICameraEntity {
    id:string
    name:string
    address:string
    violationsAmount:number
    coordinates:ICoordinates
}

export interface ICameraExpandedEntity extends ICameraEntity {

}