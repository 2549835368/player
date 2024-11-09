<template>
	<div class="container box-background">
		<VideoUpload></VideoUpload>
	</div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref } from 'vue';
	import VideoUpload from '../components/VideoUpload.vue'
	
</script>

<style scoped>
	.container{
		height: var(--box-height);
		border: solid white 1px;
		border-radius: 10px;
	}
</style>