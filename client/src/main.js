import { createApp } from 'vue'
import { createPinia} from 'pinia'
import App from './App.vue'
import axios from './axios/request.js'
import router from './routers/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/global.css'



const app = createApp(App);
app.config.globalProperties.$request = axios;
app.use(createPinia())
.use(router)
.use(ElementPlus)
.mount('#app');
