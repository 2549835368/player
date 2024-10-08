<template>
	<div class="collection-box">
		<router-link v-for="(item,index) in collectionData" :key="index" :to="'/play/'+item.vid">
		<div class="collection-item" >
			{{index}}
		</div>
		</router-link>
	</div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref, watch} from 'vue';
	import { useVideoStore } from '../stores/videoStore.js';
	import { storeToRefs } from 'pinia'
	
	const videoStore = useVideoStore();
	var {videoData, collectionData} = storeToRefs(videoStore);
	
	
</script>

<style scoped>
	.collection-box{
		width: 70%;
		height: 100px;
		background-color: var(blue);
		display:flex;
	}
	.collection-item{
		margin-top: 20px;
		margin-right: 20px;
		--height:40px;
		width: 80px;
		height: var(--height);
		line-height: var(--height);
		text-align: center;
		background-color: var(--bg-color);
		border: 1px solid --bg-color;
		border-radius: 10px;
	}
</style>