import type { GetAllSubjectResponse } from "@/api/app"

export interface TreeNode {
    id: number
    name: string
    hasChildren: boolean
    parentSubjectId: number | null
    childSubjects?: TreeNode[]
    folders?: TreeNode[]
}

export interface Tree {
    label: string
    isFolder?: boolean
    hasChildren?: boolean
    children?: Tree[]
}

const mapNode = (node: TreeNode): Tree => {
    const children: Tree[] = []

    if (node.childSubjects) {
        children.push(...node.childSubjects.map(mapNode))
    }

    if (node.folders) {
        children.push(...node.folders.map(mapNode))
    }

    return {
        label: node.name,
        isFolder: node.hasChildren,
        hasChildren: node.hasChildren,
        children: children.length > 0 ? children : undefined
    }
}

const convertToTreeData = (data: GetAllSubjectResponse[]): Tree[] => {
    return data.map((subject) => mapNode({
        id: subject.id,
        name: subject.name,
        hasChildren: subject.hasChildren,
        parentSubjectId: subject.parentSubjectId,
        childSubjects: subject.childSubjects.map((child) => ({
            id: child.id,
            name: child.name,
            hasChildren: child.hasChildren,
            parentSubjectId: subject.id,
        })),
        folders: subject.folders.map((folder) => ({
            id: folder.id,
            name: folder.name,
            hasChildren: folder.hasChildren,
            parentSubjectId: subject.id,
        }))
    }))
}

export default convertToTreeData
