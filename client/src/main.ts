import { createApp } from 'vue';
import PrimeVue from 'primevue/config';
import Aura from '@primevue/themes/aura';
import { createPinia } from 'pinia';
import "./assets/main.css";
// import "./assets/styles.scss";
import 'primeicons/primeicons.css'
import "primeflex/primeflex.css";

import App from './App.vue'
import router from './router'
import Button from 'primevue/button';
import Checkbox from 'primevue/checkbox';
import InputText from 'primevue/inputtext';
import Toast from 'primevue/toast';
import Tree from 'primevue/tree';
import ToastService from 'primevue/toastservice';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';
import Row from 'primevue/row';
import Message from 'primevue/message';
import Dialog from 'primevue/dialog';

const app = createApp(App)

app.component('Button', Button);
app.component('Checkbox', Checkbox);
app.component('InputText', InputText);
app.component('Toast', Toast);
app.component('Tree', Tree);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('ColumnGroup', ColumnGroup);
app.component('Row', Row);
app.component('Message', Message);
app.component('Dialog', Dialog);


app.use(createPinia())
app.use(router)
app.use(ToastService)
app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            prefix: 'p',
            darkModeSelector: 'light',
            cssLayer: false
        }
    }
});


app.mount('#app')
