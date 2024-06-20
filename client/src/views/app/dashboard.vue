<template>
    <Dialog v-model:visible="isEditRecordDialogOpened" :style="{ width: '450px' }" header="Edit Record" :modal="true">
        <div class="flex flex-col gap-6 p-fluid">
            <div>
                <label for="name" class="block font-bold mb-3">Name</label>
                <InputText id="name" v-model.trim="record.name" required="true" autofocus class="w-full"
                    :invalid="submittedRecord && !record" />
                <small v-if="submittedRecord && !record" class="text-red-500">Record Name is required.</small>
            </div>
        </div>

        <template #footer>
            <Button label="Cancel" text @click="closeEditRecordDialog" />
            <Button label="Save" @click="saveRecord" />
        </template>
    </Dialog>
    <Dialog v-model:visible="isDeleteRecordDialogOpened" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex items-center gap-4">
            <i class="pi pi-exclamation-triangle !text-3xl" />
            <span v-if="record">Are you sure you want to delete <b>{{ record.name }}</b>?</span>
        </div>
        <template #footer>
            <Button label="No" text @click="closeDeleteRecordDialog" />
            <Button label="Yes" @click="confirmDeleteRecord" />
        </template>
    </Dialog>

    <Dialog v-model:visible="isAddFolderDialogOpened" :style="{ width: '450px' }" header="Add Folder" :modal="true">
        <div class="flex flex-col gap-6 p-fluid">
            <div>
                <label for="name" class="block font-bold mb-3">Name</label>
                <InputText id="name" v-model.trim="folder.name" required="true" autofocus class="w-full"
                    :invalid="submittedFolder && !folder" />
                <small v-if="submittedFolder && !folder" class="text-red-500">Folder name is required.</small>
            </div>
        </div>

        <template #footer>
            <Button label="Cancel" text @click="closeAddFolderDialog" />
            <Button label="Save" @click="saveNewFolder" />
        </template>
    </Dialog>
    <Dialog v-model:visible="isDeleteFolderDialogOpened" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex items-center gap-4">
            <i class="pi pi-exclamation-triangle !text-3xl" />
            <span v-if="folder">Are you sure you want to delete <b>{{ folder.name }}</b>?</span>
        </div>
        <template #footer>
            <Button label="No" text @click="closeDeleteFolderDialog" />
            <Button label="Yes" @click="confirmDeleteFolder" />
        </template>
    </Dialog>


    <Dialog v-model:visible="isAddSubjectDialogOpened" :style="{ width: '450px' }" header="Add Subject" :modal="true">
        <div class="flex flex-col gap-6 p-fluid">
            <div>
                <label for="name" class="block font-bold mb-3">Name</label>
                <InputText id="name" v-model.trim="subject.name" required="true" autofocus class="w-full"
                    :invalid="submittedSubject && !subject" />
                <small v-if="submittedSubject && !subject" class="text-red-500">Subject name is required.</small>
            </div>
        </div>

        <template #footer>
            <Button label="Cancel" text @click="closeAddSubjectDialog" />
            <Button label="Save" @click="saveNewSubject" />
        </template>
    </Dialog>
    <Dialog v-model:visible="isEditSubjectDialogOpened" :style="{ width: '450px' }" header="Edit Subject" :modal="true">
        <div class="flex flex-col gap-6 p-fluid">
            <div>
                <label for="name" class="block font-bold mb-3">Name</label>
                <InputText id="name" v-model.trim="subject.name" required="true" autofocus class="w-full"
                    :invalid="submittedSubject && !subject" />
                <small v-if="submittedSubject && !subject" class="text-red-500">Subject name is required.</small>
            </div>
        </div>

        <template #footer>
            <Button label="Cancel" text @click="closeEditSubjectDialog" />
            <Button label="Save" @click="saveEditedSubject" />
        </template>
    </Dialog>
    <Dialog v-model:visible="isDeleteSubjectDialogOpened" :style="{ width: '450px' }" header="Confirm" :modal="true">
        <div class="flex items-center gap-4">
            <i class="pi pi-exclamation-triangle !text-3xl" />
            <span v-if="folder">Are you sure you want to delete <b>{{ subject.name }}</b>?</span>
        </div>
        <template #footer>
            <Button label="No" text @click="closeDeleteSubjectDialog" />
            <Button label="Yes" @click="confirmDeleteSubject" />
        </template>
    </Dialog>
    <div class="app-viewport inspect_">

        <!-- app-header -->
        <div class="app-header">
            <div class="app-branding">
                <h1 class="app-brand">Document Management System</h1>
            </div>
            <div class="app-nav">
                <Button type="button" icon="pi pi-power-off" @click="logout" aria-haspopup="true"
                    aria-controls="overlay_menu" />
            </div>
        </div>
        <!-- end app-header -->

        <!-- app-main -->
        <div class="app-main">
            <Message closable v-if="tableData.length === 0">No Record Available</Message>
            <DataTable v-else :value="tableData" showGridlines tableStyle="min-width: 50rem">
                <Column field="id" header="ID" />
                <Column field="name" header="Nama" />
                <Column :exportable="false" style="width: 8rem">
                    <template #body="slotProps">
                        <Button icon="pi pi-pencil" outlined rounded class="mr-2" @click="editRecord(slotProps.data)" />
                        <Button icon="pi pi-trash" outlined rounded severity="danger"
                            @click="deleteRecord(slotProps.data)" />
                    </template>
                </Column>
            </DataTable>

        </div>
        <!-- end app-main -->

        <!-- app-sidebar -->
        <div class="app-sidebar">
            <div class="flex justify-content-end pt-3 pr-3 gap-1">
                <template v-if="selectedEntity.type === null">
                    <Button type="button" label="Add Subject" @click="addSubject">
                        <i class="pi pi-plus" />
                        <i class="pi pi-box" />
                    </Button>
                </template>
                <template v-if="selectedEntity.type === 'subject'">
                    <Button type="button" label="Add Subject" @click="addSubject">
                        <i class="pi pi-plus" />
                        <i class="pi pi-box" />
                    </Button>
                    <Button type="button" label="Add Folder" @click="addFolder">
                        <i class="pi pi-plus" />
                        <i class="pi pi-folder" />
                    </Button>
                    <Button type="button" label="Edit Subject" @click="editSubject" severity="warn">
                        <i class="pi pi-pencil" />
                        <i class="pi pi-box" />
                    </Button>
                    <Button type="button" label="Delete Subject" severity="danger">
                        <i class="pi pi-trash" />
                        <i class="pi pi-box" />
                    </Button>
                </template>
                <template v-if="selectedEntity.type === 'folder'">
                    <Button type="button" label="Add Record">
                        <i class="pi pi-plus" />
                        <i class="pi pi-file" />
                    </Button>
                    <Button type="button" label="Edit Folder" severity="warn">
                        <i class="pi pi-pencil" />
                        <i class="pi pi-folder" />
                    </Button>
                    <Button type="button" label="Delete Folder" @click="deleteFolder" severity="danger">
                        <i class="pi pi-trash" />
                        <i class="pi pi-folder" />
                    </Button>
                </template>

            </div>
            <div class="card flex justify-center">
                <Tree @nodeUnselect="onNodeUnselect" @nodeSelect="onNodeSelect" v-model:selectionKeys="selectedKey"
                    selectionMode="single" :value="nodes" class="w-full md:w-[30rem]"></Tree>
            </div>

        </div>
        <!-- app-sidebar -->

    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllEagerSubjects, getFolderDetails, postNewFolder, postNewSubject, putEditRecord, putEditSubject, type GetAllEagerSubjectResponse, deleteRecord as APIDeleteRecord, deleteFolder as APIDeleteFolder } from '@/api/app';
import { useToast } from 'primevue/usetoast';

const toast = useToast()

interface Subject {
    id: number;
    name: string;
    parentSubjectId: number | null;
    childSubjects: Subject[];
    folders: Folder[];
}

interface Folder {
    id: number;
    name: string;
}

interface TreeNode {
    key: string;
    label: string;
    data: number;
    icon: string;
    children: TreeNode[];
}

// record
const editRecord = (prod: { id: number, name: string }) => {
    record.value = prod;
    isEditRecordDialogOpened.value = true;
};
const deleteRecord = (prod: { id: number, name: string }) => {
    record.value = prod;
    isDeleteRecordDialogOpened.value = true;
};
const saveRecord = () => {
    submittedRecord.value = true;
    putEditRecord(record.value)
        .then(() => {
            toast.add({ severity: 'success', summary: 'Success', detail: 'Edit Record Success', life: 3000 });
            record.value = { id: 0, name: '' };
            isEditRecordDialogOpened.value = false;
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Edit Record Error', life: 3000 });
        });
};

const confirmDeleteRecord = () => {
    submittedRecord.value = true;
    APIDeleteRecord(record.value.id)
        .then(() => {
            if (selectedEntity.value.id === null) return

            toast.add({ severity: 'success', summary: 'Success', detail: 'Delete Record Success', life: 3000 });
            record.value = { id: 0, name: '' };

            isDeleteRecordDialogOpened.value = false;
            getFolderDetails(selectedEntity.value.id);
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Delete Record Error', life: 3000 });
        });
};

const closeEditRecordDialog = () => {
    isEditRecordDialogOpened.value = false;
    submittedRecord.value = false;
};

const closeDeleteRecordDialog = () => {
    isDeleteRecordDialogOpened.value = false;
    submittedRecord.value = false;
};

const isEditRecordDialogOpened = ref(false);
const isDeleteRecordDialogOpened = ref(false);

const record = ref<{ id: number, name: string }>({ id: 0, name: '' });
const submittedRecord = ref(false);

// end record


// folder
const addFolder = () => {
    isAddFolderDialogOpened.value = true;
    folder.value.subjectId = selectedEntity.value.id
};

const deleteFolder = () => {
    isDeleteFolderDialogOpened.value = true;
    folder.value.id = selectedEntity.value.id
    folder.value.name = selectedEntity.value.label
};

const submittedFolder = ref(false);

const isAddFolderDialogOpened = ref(false);
const isDeleteFolderDialogOpened = ref(false);

const folder = ref<{ subjectId: number | null, name: string | null, id: number | null }>({ subjectId: null, name: null, id: null })

const closeAddFolderDialog = () => {
    isAddFolderDialogOpened.value = false;
    submittedFolder.value = false;
};

const closeDeleteFolderDialog = () => {
    isDeleteFolderDialogOpened.value = false;
    submittedFolder.value = false;
};

const saveNewFolder = () => {
    submittedFolder.value = true;
    if (!folder.value.subjectId || !folder.value.name) return

    postNewFolder({
        subjectId: folder.value.subjectId,
        name: folder.value.name
    })
        .then(() => {
            fetchAndConvertSubjects();
            toast.add({ severity: 'success', summary: 'Success', detail: 'Add Folder Success', life: 3000 });
            isAddFolderDialogOpened.value = false;
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Add Folder Error', life: 3000 });
        });
};

const confirmDeleteFolder = () => {
    submittedFolder.value = true;
    if (!folder.value.id) return

    APIDeleteFolder(folder.value.id)
        .then(() => {
            if (selectedEntity.value.id === null) return

            toast.add({ severity: 'success', summary: 'Success', detail: 'Delete Folder Success', life: 3000 });
            record.value = { id: 0, name: '' };

            isDeleteFolderDialogOpened.value = false;
            fetchAndConvertSubjects();
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Delete Folder Error', life: 3000 });
        });
};
// end folder

// subject
const addSubject = () => {
    isAddSubjectDialogOpened.value = true;
    subject.value.subjectId = selectedEntity.value.id
};

const submittedSubject = ref(false);

const isAddSubjectDialogOpened = ref(false);

const subject = ref<{ subjectId: number | null, name: string | null }>({ subjectId: null, name: null })

const closeAddSubjectDialog = () => {
    isAddSubjectDialogOpened.value = false;
    submittedSubject.value = false;
};

const saveNewSubject = () => {
    submittedSubject.value = true;
    if (!subject.value.name) return

    postNewSubject({
        parentSubjectId: subject.value.subjectId ?? undefined,
        name: subject.value.name
    })
        .then(() => {
            fetchAndConvertSubjects();
            toast.add({ severity: 'success', summary: 'Success', detail: 'Add Subject Success', life: 3000 });
            isAddSubjectDialogOpened.value = false;
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Add Subject Error', life: 3000 });
        });
};

const editSubject = () => {
    isEditSubjectDialogOpened.value = true;
    subject.value.subjectId = selectedEntity.value.id
    subject.value.name = selectedEntity.value.label
};

const isEditSubjectDialogOpened = ref(false);

const closeEditSubjectDialog = () => {
    isEditSubjectDialogOpened.value = false;
    submittedSubject.value = false;
};

const saveEditedSubject = () => {
    submittedSubject.value = true;
    if (!subject.value.name || !subject.value.subjectId) return

    putEditSubject({
        subjectId: subject.value.subjectId,
        name: subject.value.name
    })
        .then(() => {
            fetchAndConvertSubjects();
            toast.add({ severity: 'success', summary: 'Success', detail: 'Add Subject Success', life: 3000 });
            isAddSubjectDialogOpened.value = false;
        })
        .catch((error) => {
            toast.add({ severity: 'error', summary: 'Error', detail: 'Add Subject Error', life: 3000 });
        });
};
// TODO: CHANGE THE PAYLOAD

// end subject




const nodes = ref<TreeNode[]>([]);

const tableData = ref<{ id: number; name: string }[]>([]);
const selectedKey = ref(null);
const selectedEntity = ref<{ type: 'folder' | 'subject' | null, id: number | null, label: string | null }>({
    type: null,
    id: null,
    label: null
});

const onNodeUnselect = () => {
    selectedEntity.value.id = null
    selectedEntity.value.type = null
}


const isFolder = (str: string): boolean => {
    return str.trim().endsWith('pi-folder');
}

const isSubject = (str: string): boolean => {
    return str.trim().endsWith('pi-box');
}

const onNodeSelect = async (node: any) => {
    selectedEntity.value.id = node.data
    selectedEntity.value.label = node.label

    console.log('#debug selected en', node)

    if (isFolder(node.icon)) {
        selectedEntity.value.type = 'folder'

        fetchFolderDetails(parseInt(node.data))
    }
    else if (isSubject(node.icon)) {
        selectedEntity.value.type = 'subject'

        tableData.value = []
    }
};

const convertToTreeNodes = (subjects: Subject[]): TreeNode[] => {
    const buildTree = (subject: Subject, parentKey: string): TreeNode => {
        const key = `${parentKey}p${subject.id}`;
        const children: TreeNode[] = [];

        subject.childSubjects.forEach(child => {
            children.push(buildTree(child, key));
        });

        subject.folders.forEach(folder => {
            children.push({
                key: `${key}f${folder.id}`,
                label: folder.name,
                data: folder.id,
                icon: 'pi pi-fw pi-folder',
                children: []
            });
        });

        return {
            key,
            label: subject.name,
            data: subject.id,
            icon: 'pi pi-fw pi-box',
            children
        };
    };

    return subjects.map(subject => buildTree(subject, ''));
}

const fetchAndConvertSubjects = async () => {
    try {
        const response = await getAllEagerSubjects();
        nodes.value = convertToTreeNodes(response.data);
    } catch (error) {
        console.error('Failed to fetch subjects:', error);
    }
}

const fetchFolderDetails = async (id: number) => {
    try {
        const response = await getFolderDetails(id);
        const val = response.data.records.map(record => ({
            id: record.id,
            name: record.name,
        }));

        tableData.value = val

    } catch (error) {
        console.error('Failed to fetch subjects:', error);
    }
}


onMounted(async () => {
    await fetchAndConvertSubjects();
});


const logout = () => {
    localStorage.removeItem('token');
};

</script>

<style scoped>
.app-viewport {
    height: 100%;
    margin: 0;
}

.app-viewport.inspect * {
    border: 1px solid red;
}

/* Grid Layout */

/* .app-viewport * {
    position: relative;
    margin: 0;
    padding: 0;
    box-sizing: border-box;
} */

.app-viewport {
    display: grid;
    grid-template-columns: var(--app-sidebar-width) 1fr;
    grid-template-rows: var(--app-header-height) 1fr;
    grid-template-areas:
        "AppSidebar AppHeader"
        "AppSidebar AppMain";
}

.app-header {
    grid-area: AppHeader;
    margin-left: calc(var(--app-sidebar-width) * -1);
    z-index: 2;
}

.app-nav,
.app-header,
.app-branding {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: center;
    justify-items: flex-start;
    color: #fff;
}

.app-nav {
    padding: 0 var(--app-padding);
    flex: auto;
    justify-content: flex-end;
}

.app-branding {
    width: var(--app-sidebar-width);
    padding: 0 var(--app-padding);
}

.app-main {
    grid-area: AppMain;
    overflow-y: auto;
    z-index: 1;
    height: 100vh;
    padding: var(--app-padding);
}

.app-sidebar {
    /* display: grid; */
    grid-area: AppSidebar;
    margin-top: var(--app-header-height);
    overflow: auto;
    z-index: 1;
}

.app-sidebar-menu {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
}

.app-sidebar-menu>li>a {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: center;
    justify-items: start;
    padding: 5px 0;
}

.app-sidebar-menu>li>a>.menu-icon {
    margin-left: 15px;
}

.app-sidebar-menu>li>a>span {
    margin-left: 10px;
}

/* Color / Presentation Only */

.placeholder {
    display: block;
    height: 1200px;
    background: rgba(0, 0, 0, 0.05);
}

.placeholder:after {
    content: " ";
}

.app-viewport {
    background: #e9ecef;
}

.app-header,
.app-sidebar-header {
    background: #202020;
}

.app-sidebar {
    background: var(--p-content-background);
    font-size: 15px;
}

.app-sidebar-menu>li>a {
    text-decoration: none;
    color: #fff;
    opacity: 0.5;
}

.app-sidebar-menu>li.active>a,
.app-sidebar-menu>li>a:hover {
    opacity: 1;
}

.app-icon {
    font-size: 28px;
    margin-right: 5px;
    font-weight: 500;
}

.app-brand {
    font-size: 130%;
    font-weight: 500;
    line-height: 1.2rem;
}

.app-nav-toggle,
.app-brand {
    color: #fff;
}

/* Responsive */

@media screen and (max-width: 768px) {
    .app-viewport {
        grid-template-columns: var(--app-sidebar-min-width) 1fr;
    }

    .app-header {
        margin-left: calc(var(--app-sidebar-min-width) * -1);
    }

    .app-icon {
        font-size: 36px;
        margin: 0;
    }

    .app-branding {
        width: var(--app-sidebar-min-width);
        padding: 0;
        justify-content: center;
    }

    .app-brand {
        display: none;
    }

    .app-sidebar-menu>li {
        width: var(--app-sidebar-min-width);
    }

    .app-sidebar-menu>li>a {
        display: block;
        text-align: center;
    }

    .app-sidebar-menu>li>a>span {
        display: none;
    }

    .app-sidebar-menu>li>a>.menu-icon {
        margin: 0;
        padding: 0;
        font-size: 30px;
    }
}
</style>