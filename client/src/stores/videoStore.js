import { defineStore } from 'pinia'
import { ref, computed, resolveComponent } from 'vue'
import request from '../axios/request.js'

export const useVideoStore = defineStore('video',() =>{
	var videoData = ref()
	var collectionData = ref()
	function get(vid) {
		request.get("videoInfo/getInfo?vid="+vid)
		.then( res =>{
			videoData.value = res.data;
			getCollection(res.data.id)
		})

	}

	function getCollection(vid){
		if(vid != 0){
			request.get("/collection/getCollectionVideoByVid?vid=" + vid)
			.then(res =>{
				collectionData.value = res.data;
			})
		}
	}
	
	return {videoData, collectionData, get}
})