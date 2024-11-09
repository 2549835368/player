import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'

const routes = [
	{
		path:"/",
		redirect:"/home"
	},
	{
		path:"/home",
		component:() => import("../views/homeView.vue")
	},
	{
		path:"/play/:vid",
		component:() => import("../views/playView.vue")
	},
	{
		path:"/upload",
		component:() => import("../views/uploadView.vue")
	}
]

const router = createRouter({
	history:createWebHistory(),
	routes:routes
})

export default router