<template>
	<el-row class="container">
		<el-col :span="1"></el-col>
		<el-col :span="22">
			
			<div class="video-box">
				<el-scrollbar ref="scrollbarRef" class="scroll">
				<el-text truncated id="title" class="title">
					{{title}}
				</el-text>
				<div class="video-main">
					<VideoPlayer></VideoPlayer>
				</div>
				<Collection></Collection>
				</el-scrollbar>
			</div>
			
			
		</el-col>
		<el-col :span="1"></el-col>
	</el-row>

	
</template>

<script setup>
	import request from '../axios/request.js'
	import { useVideoStore } from '../stores/videoStore.js';
	import { storeToRefs } from 'pinia';
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref, nextTick, watch} from 'vue';
	import VideoPlayer from '../components/VideoPlayer.vue';
	import Collection from '../components/Collection.vue';
	import { ElScrollbar } from 'element-plus'
	
	const route = useRoute();
	const vid = route.params.vid;
	const videoStore = useVideoStore();
	var title = ref("");
	console.log(videoStore.test)
	var {videoData} = storeToRefs(videoStore);
	videoStore.get(vid)
	
	watch(videoData,()=>{
		title.value = videoData.value?.title;
	})

	
	
</script>

<style scoped>
	.video-box{
		/* height: calc(100vh - 112px); */
		border-radius: 10px;
		border: solid var(--bg-color) 1px;
		/* background-color: var(--bg-color); */
		display: flex;
		width: 100%;
		justify-content: flex-start;
		flex-direction: column;
		align-items: center;
		flex-wrap: wrap;
	}
	.scroll{
		width: 100%;
		height: calc(100vh - 112px);
		min-width: 500px;
	}
	.title{
		/* background-color: var(--bg-color); */
		border-radius: 10px 10px 0px 0px;
		height: 8%;
		line-height: calc(var(--box-height)*0.08);
		font-size: 22px;
		width: 70%;
	}
	.video-main{
		width: 70%;
	}
</style>