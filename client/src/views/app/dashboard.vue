<template>
    <el-container class="home-container">
        <el-aside style="width: 217px;">
            <el-row style="height: 100vh;">
                <el-tree style="width: 1200px" :data="treeData" :props="defaultProps" @node-click="handleNodeClick" />
            </el-row>
        </el-aside>
        <el-container class="main-container">
            <el-header>
                <div class="header_left">
                    <span style="font-weight: bold;">&nbsp;Document Management System</span>
                </div>
                <div class="header_right">
                    <a href="#" @click.prevent="logout">Logout</a>
                </div>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup lang="ts">
import type Node from 'element-plus/es/components/tree/src/model/node';
import { ref, onMounted, toRaw } from 'vue';
import { useRouter } from 'vue-router';
import { getAllEagerSubjects, type GetAllEagerSubjectResponse } from '@/api/app';

const router = useRouter();

interface Tree {
    label: string;
    children?: Tree[];
}

const treeData = ref<Tree[]>([]);
const defaultProps = {
    label: 'label',
    children: 'children',
};

const handleNodeClick = (data: Tree) => {
    console.log(data);
};

const mapNode = (node: GetAllEagerSubjectResponse): Tree => {
    const children: Tree[] = [];

    if (node.childSubjects) {
        children.push(...node.childSubjects.map(mapNode));
    }

    if (node.folders) {
        children.push(...node.folders.map(folder => ({
            label: folder.name,
        })));
    }

    return {
        label: node.name,
        children: children.length > 0 ? children : undefined,
    };
};

const convertToTreeData = (data: GetAllEagerSubjectResponse[]): Tree[] => {
    return data.map(mapNode);
};

onMounted(async () => {
    try {
        const response = await getAllEagerSubjects();
        treeData.value = convertToTreeData(response.data);
    } catch (error) {
        console.error('Failed to fetch subjects:', error);
    }
});

const currUser = ref({});

const logout = () => {
    // Implement logout logic
};
</script>

<style scoped>
.is-folder>.el-tree-node__content {
    color: #000;
}

.home-container {
    height: 100vh;
    display: flex;
}

.main-container {
    display: flex;
    flex-direction: column;
    flex: 1;
}

.el-header {
    line-height: 60px;
    display: flex;
    justify-content: space-between;
}

.el-header .header_left {
    font-size: 22px;
}

.header_left img {
    margin-top: -5px;
    vertical-align: middle;
}

.el-header .header_right {
    padding-right: 20px;
}

.header_right a {
    margin-left: 10px;
}

.el-menu {
    height: 100%;
}

.el-main {
    border-top: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
    flex: 1;
    display: flex;
    flex-direction: column;
}

.breadcrumb {
    margin-bottom: 20px;
}

.el-footer {
    text-align: center;
    font-size: 12px;
    height: 40px;
    line-height: 40px;
}
</style>