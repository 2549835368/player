import { defineStore } from 'pinia'
import { ref, computed, watch} from 'vue'
import request from '../axios/request.js'

export const useDataStore = defineStore('data',() =>{
	const partCaption = ref()
	const currentPart = ref(0);
	const videoList = ref();
	getPart()
	function getPart() {
		request.get('/partCaption/getList')
		.then( res =>{
			partCaption.value = res.data;
		}).catch( err =>{
			console.log("未获取到信息")
		})
	}
	
	return {partCaption, getPart, currentPart,videoList}
})