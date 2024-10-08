import { defineStore } from 'pinia'
import { ref, computed, resolveComponent } from 'vue'
import request from '../axios/request.js'

export const useVideoStore = defineStore('video',() =>{
	var videoData = ref()
	var collectionData = ref()
	var test = 2;
	function get(vid) {
		request.get("videoInfo/getInfo/"+vid)
		.then( res =>{
			videoData.value = res.data;
			getCollection(res.data.collectionId)
		})

	}
	function getCollection(cid){
		if(cid != 0){
			request.get("/collection/get/" + cid)
			.then(res =>{
				collectionData.value = res.data;
			})
		}
	}
	
	return {videoData, collectionData, get,test}
})