import { defineStore } from 'pinia'
import { ref, computed, watch } from 'vue'
import request from '../axios/request.js'
import { ElMessage } from "element-plus"

export const useUserStore = defineStore('user',() =>{
	const id = ref()
	const username = ref()
	const token = ref()
	const nickname = ref()
	const loged = ref()
	loged.value = false;
	const avatar = ref()
	const userCollection = ref()
	
	token.value = localStorage.getItem("token")
	
	if(token.value!=null&&token.value!=undefined){
		login();
	}
	
	function login() {
		request.get('/user/autologin?token='+token.value)
		.then((res)=>{
			id.value = res.data.id;
			username.value = res.data.username;
			nickname.value = res.data.nickname;
			token.value = res.data.token;
			loged.value = true
		}).catch( res =>{
			loged.value = false;
			error("请重新登录")
			localStorage.removeItem("token")
		})
	}
	function logout(){
		id.value = 0;
		username.value = '';
		nickname.value = '';
		token.value = '';
		loged.value = false
	}
	
	watch(token,()=>{
		if(token.value == ''){
			localStorage.removeItem("token")
		}else{
			localStorage.setItem("token",token.value)
		}
	})
	
	function error(message){
		ElMessage({
			message:message,
			type:'error',
		})
	}
	return { id, username, login ,token, nickname, loged, avatar, logout}
})