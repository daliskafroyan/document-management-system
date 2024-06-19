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


export interface GetAllEagerSubjectResponse {
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
