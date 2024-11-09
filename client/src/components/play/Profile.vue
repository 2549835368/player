<template>
	<div>
		{{videoData.profile}}
	</div>
	
</template>

<script setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref } from 'vue';
	import { useVideoStore } from '../../stores/videoStore.js'
	import { storeToRefs } from 'pinia';
	
	

	const videoStore = useVideoStore();
	const { videoData } = storeToRefs(videoStore);
	console.log(videoData)


	
	
</script>

<style scoped>
</style>