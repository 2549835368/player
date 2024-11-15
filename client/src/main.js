import { createApp } from 'vue'
import { createPinia} from 'pinia'
import App from './App.vue'
import axios from './axios/request.js'
import router from './routers/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/global.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'



const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.config.globalProperties.$request = axios;
app.use(createPinia())
.use(router)
.use(ElementPlus)
.mount('#app');
