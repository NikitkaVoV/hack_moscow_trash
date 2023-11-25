import {ESort, ICoordinates} from "./resources";

export interface IRoutesPayload {
    filter?: string
    page?: number
    size?: number
    sort?: {
        [key: string]: `${ESort}`
    }
}

enum ERouteStatus {
    enRoute = 'enroute',
    error = 'error',
    checked = 'checked',
    readyTo = 'readyto'
}

export const parseERouteStatusToRusName = (str: `${ERouteStatus}`): string => {
    switch (str) {
        case ERouteStatus.error:
            return 'Ошибка'
        case ERouteStatus.enRoute:
            return 'В пути'
        case ERouteStatus.checked:
            return 'Проверено'
        case ERouteStatus.readyTo:
            return 'Предстоит'
        default:
            return 'Другой'
    }
}

export interface IRouteEntity {
    id: string
    name: string
    status: '${ERouteStatus}'
    camerasAmount: number
    dateDeparture: string
    dateArrival: string
    wasteType: string
    points: {
        coordinates: ICoordinates
        name: string
    }[]
    cameraIds: string[]
}

export interface IRouteExpandedEntity extends IRouteEntity {

}