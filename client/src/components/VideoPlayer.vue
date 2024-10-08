<template>
	<div id="dplayer"></div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref} from 'vue';
	import DPlayer from 'dplayer';
	import 'vue-dplayer/dist/vue-dplayer.css'
	
	const route = useRoute();
	const vid = route.params.vid;
	
	onMounted(()=>{
		const dp = new DPlayer({
			container: document.getElementById("dplayer"),
			video: {
			        url: 'http://localhost:8080/videoPlay/play/'+vid,
			    },
			danmaku:{
				id:vid,
				api:"http://localhost:8080/danmu/",
				token: 'tokendemo',
				user: 'Morey',
				bottom: '15%',
				unlimited: true,
			}
		});
		
	})
	
	
</script>

<style scoped>
	#dplayer{
		width: 100%;
	}
</style>