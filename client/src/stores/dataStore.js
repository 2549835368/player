import { defineStore } from 'pinia'
import { ref, computed} from 'vue'
import request from '../axios/request.js'

export const useDataStore = defineStore('data',() =>{
	var partCaption = ref([])
	var test = 1;
	function getPart() {
		request.get('/partCaption/getList')
		.then( res =>{
			partCaption.value = res.data;
		})
	}
	
	return {partCaption, getPart,test}
})