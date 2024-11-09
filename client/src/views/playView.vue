<template>
	<el-row class="container">
			<div class="video-box box-background">
				<el-scrollbar class="scroll">
					<div class="left">
						<el-text truncated id="title" class="title">
							{{title}}
						</el-text>
						<div class="video-main">
							<VideoPlayer></VideoPlayer>
						</div>
					</div>
					<div class="right">
						<Author></Author>
						<RightTabs></RightTabs>
					</div>
				</el-scrollbar>
			</div>
	</el-row>

	
</template>

<script setup>
	import request from '../axios/request.js'
	import { useVideoStore } from '../stores/videoStore.js';
	import { storeToRefs } from 'pinia';
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref, nextTick, watch} from 'vue';
	import VideoPlayer from '../components/play/VideoPlayer.vue';
	import RightTabs from '../components/play/RightTabs.vue';
	import Author from '../components/play/Author.vue'
	
	const route = useRoute();
	const vid = route.params.vid;
	const videoStore = useVideoStore();
	var title = ref("");
	// console.log(videoStore.test)
	var {videoData} = storeToRefs(videoStore);
	videoStore.get(vid)
	
	watch(videoData,()=>{
		title.value = videoData.value?.title;
	})

	
	
</script>

<style scoped>
	.video-box{
		height: var(--box-height);
		border-radius: 10px;
		border: solid var(--bg-color) 1px;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		flex-direction: column;
		align-items: space-content;
		flex-wrap: wrap;
	}
	.scroll{
		width: 100%;
		height: var(--box-height);
		min-width: 500px;
	}
	.title{
		/* background-color: var(--bg-color); */
		border-radius: 10px 10px 0px 0px;
		height: 40px;
		line-height: 40px;
		font-size: 22px;
		width: 100%;
	}
	.video-main{
		height: calc(100% - 55px);
	}
	.left{
		height: var(--box-height);
		padding-left: 12px;
		width: 68%;
	}
	.right{
		width:calc(30% - 14px);
		height: var(--box-height);
	}
</style>