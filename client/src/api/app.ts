import request from "@/utils/request";

export type GetAllSubjectResponse = {
    id: number;
    name: string;
    hasChildren: boolean;
    parentSubjectId: number | null;
    childSubjects: {
        id: number;
        name: string;
        hasChildren: boolean;
    }[];
    folders: {
        id: number;
        name: string;
        hasChildren: boolean;
    }[];
}

export function getAllSubject() {
    return request<GetAllSubjectResponse[]>({
        url: `api/subjects`,
        method: 'GET',
    })
}

export function getSubjectDetails(id: number) {
    return request<GetAllSubjectResponse>({
        url: `api/subjects/${id}`,
        method: 'GET',
    })
}


export type GetAllEagerSubjectResponse = {
    id: number;
    name: string;
    parentSubjectId: number | null;
    childSubjects: GetAllEagerSubjectResponse[];
    folders: {
        id: number;
        name: string;
    }[];
}

export function getAllEagerSubjects() {
    return request<GetAllEagerSubjectResponse[]>({
        url: `api/subjects/eager`,
        method: 'GET',
    })
}

export type GetFolderDetailsResponse = {
    id: number;
    name: string;
    subjectId: number | null;
    records: {
        id: number;
        name: string;
    }[];
}

export function getFolderDetails(id: number) {
    return request<GetFolderDetailsResponse>({
        url: `api/folders/${id}`,
        method: "GET",
    });
}

export type PutEditRecord = {
    id: number;
    name: string;
}

export function putEditRecord(data: PutEditRecord) {
    return request({
        url: `api/records/${data.id}`,
        method: 'PUT',
        data
    })
}
