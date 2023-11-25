import {ESort} from "./resources";

export interface IViolationsPayload {
    filter?: string
    page?: number
    size?: number
    sort?: {
        [key: string]: `${ESort}`
    }
}

export interface IViolationEntity {
    id: string
    violationType: string
    criminal:string
    date:string
    address: string
    cameraId:string
}

export interface IViolationExpandedEntity extends IViolationEntity {

}