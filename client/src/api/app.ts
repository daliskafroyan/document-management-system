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

export type PostNewRecord = {
    folderId: number;
    name: string;
}

export function postNewRecord(data: PostNewRecord) {
    return request({
        url: `api/records`,
        method: 'POST',
        data
    })
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

export function deleteRecord(id: number) {
    return request({
        url: `api/records/${id}`,
        method: 'DELETE',
    })
}


export type PostNewFolder = {
    subjectId: number;
    name: string;
}

export function postNewFolder(data: PostNewFolder) {
    return request({
        url: `api/folders`,
        method: 'POST',
        data
    })
}

export function deleteFolder(id: number) {
    return request({
        url: `api/folders/${id}`,
        method: 'DELETE',
    })
}

export type PutEditFolder = {
    id: number;
    name: string;
}

export function putEditFolder(data: PutEditFolder) {
    return request({
        url: `api/folders/${data.id}`,
        method: 'PUT',
        data
    })
}

export type PostNewSubject = {
    parentSubjectId?: number;
    name: string;
}

export function postNewSubject(data: PostNewSubject) {
    return request({
        url: `api/subjects`,
        method: 'POST',
        data
    })
}

export type PutEditSubject = {
    id: number;
    name: string;
}

export function putEditSubject(data: PutEditSubject) {
    return request({
        url: `api/subjects/${data.id}`,
        method: 'PUT',
        data
    })
}

export function deleteSubject(id: number) {
    return request({
        url: `api/subjects/${id}`,
        method: 'DELETE',
    })
}

