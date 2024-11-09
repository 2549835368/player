<template>
	<div class="author-box">
		<el-avatar :size="50"></el-avatar>
		<div class="username">{{authName}}</div>
	</div>
	
</template>

<script setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref, watch } from 'vue';
	import { useVideoStore } from '../../stores/videoStore.js';
	import { storeToRefs } from 'pinia';
	
	const videoStore = useVideoStore();
	var {videoData} = storeToRefs(videoStore);
	var authName = ref()
	
	watch(videoData,()=>{
		request.get("/userInfo/getById?id="+videoData.value?.authId)
		.then(res =>{
			authName.value = res.data.nickname;
		})
	})
	
</script>
	
<style scoped>
	.author-box{
		height: 12%;
		display: flex;
		padding-top: 12px;
		align-items: center;
	}
	.username{
		padding-left: 12px;
		font-weight: bold;
	}
</style>