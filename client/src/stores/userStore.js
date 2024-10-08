import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import request from '../axios/request.js'

export const useUserStroe = defineStore('user',() =>{
	const id = ref(0)
	const userName = ref("Li")
	
	function set() {
		request.get('/hello/query?username='+userName.value).then((res)=>{
			id.value = res.data.id;
			userName.value = res.data.name;
			console.log(res.data);
			console.log(id.value);
			console.log(userName.value);
		})
	}
	
	return { id, userName, set }
})