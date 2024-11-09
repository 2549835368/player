<template>
	<div>
		<el-upload 
			action=""
			:http-request="uploadImage"
			:show-file-list="false"
			><el-button>上传封面</el-button>
		</el-upload>
		
	</div>
	<el-radio-group v-model="radioResult" style="left: -105px; position: relative;">
		<div style="display: flex;flex-direction: column;">
			<el-image v-if="imageVisible" :src="imageSrc" class="cover"></el-image>
			<el-radio v-if="imageVisible" :value="imageSrc">选择</el-radio>
		</div>
		<div class="imageBox">
			<div v-for="(item,index) in imageData.data" key="index" style="width: 50%;">
				<el-image :src="item"></el-image>
				<el-radio :value="item">选择</el-radio>
			</div>
			
		</div>
	</el-radio-group>
	
	
</template>

<script setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref ,watch} from 'vue';
	
	const imageData = defineProps(['data'])

	const radioResult = defineModel()
	
	const imageSrc = ref();
	
	const imageVisible = ref(false);
	
	function uploadImage(config){
		
		const formData = new FormData();
		formData.append("file",config.file)
		request.post("image/upload",formData)
		.then(res =>{
			imageSrc.value = res.data;
			imageVisible.value = true;
			console.log(imageSrc.value);
		})
	}
	
</script>

<style scoped>
	.imageBox{
		display:flex;
		width: 800px;
		flex-wrap: wrap;
	}
	.imageScroll{
		margin: 0 auto;
	}
	.cover{
		width: 400px;
	}
</style>