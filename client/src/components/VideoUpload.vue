<template>
	
	<el-scrollbar>
		<div class="uploadWraper" @drop.prevent = "handleDrop" @dragover.prevent @click="fileClick">
			<el-icon color="white" class="el-icon--upload" size="70" :class="{hide:!progressVisible}">
				<upload-filled />
			</el-icon>
			<div class="tip" :class="{hide:!progressVisible}">拖拽或点击上传视频</div>
			<input type="file" name="file" @change="handleFileUpload" style="display: none;" ref="fileRef">
			<el-progress type="circle" :percentage="progress" :class="{hide:progressVisible}" />
		</div>
		<el-form label-position="left" label-width="auto" :model="form" ref="formRef" class="form">
			<el-form-item label="封面:">
				<div class="elInput">
					<CoverSelect :data = "imageData" v-model="form.cover"></CoverSelect>
				</div>
			</el-form-item>
			<el-form-item label="标题:" prop="title">
				<el-input class="elInput" v-model="form.title" show-word-limit maxlength="20">
				</el-input>
			</el-form-item>
			<el-form-item label="分区:" prop="part">
				<el-radio-group class="elInput" v-model="form.part">
					<el-radio-button class="radioButton" :value = item.code v-for="(item,index) in partCaption" key="index">
						{{item.caption}}
					</el-radio-button>
				</el-radio-group>
			</el-form-item>
			
			<el-form-item label="标签:" prop="tag">
				<el-input class="elInput" v-model="form.tag" disabled></el-input>
				<div class="elInput">			
					<el-tag
						v-for="tag in tags"
						:key="tag"
						closable
						:disable-transitions="false"
						@close="handleClose(tag)"
						>
						{{ tag }}
					</el-tag>
					
					<el-input
					      v-if="inputVisible"
					      ref="InputRef"
					      v-model="inputValue"
					      class="w-20"
					      size="small"
					      @keyup.enter="handleInputConfirm"
					      @blur="handleInputConfirm"
					    />
					<el-button v-else class="button-new-tag" size="small" @click="showInput">
					  +添加标签
					</el-button>
				</div>
				
			</el-form-item>
			
			<el-form-item label="合集:" prop="合集">
				<div class="elInput"></div><CollectionInfo v-model="form.collection"></CollectionInfo>
			</el-form-item>
			
			<el-form-item label="简介:" prop="profile">
				<el-input class="elInput" v-model="form.profile" 
				:autosize="{ minRows: 2, maxRows: 4 }" 
				show-word-limit maxlength="100"
				type="textarea">
				</el-input>
			</el-form-item>
			<el-form-item>
				<el-button style="margin: 0 auto;" @click="videoSubmit">上传</el-button>
			</el-form-item>
		</el-form>
	</el-scrollbar>
	
	
	
	
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { ref, useTemplateRef, reactive, watch,nextTick} from 'vue';
	import SparkMD5 from 'spark-md5';
	import { useUserStore } from '../stores/userStore.js';
	import { useDataStore } from '../stores/dataStore.js';
	import { storeToRefs } from 'pinia';
	import { ElMessage, ElMessageBox } from "element-plus"
	import CollectionInfo from '../components/upload/CollectionInfo.vue'
	import CoverSelect from '../components/upload/CoverSelect.vue';
	
	const userStore = useUserStore();
	const dataStore = useDataStore();
	const {id, loged} = storeToRefs(userStore);
	const {partCaption} = storeToRefs(dataStore);
	
	const tags = ref([])
	const isTag = ref(true)
	const inputVisible = ref(false)
	const inputValue = ref('')
	const InputRef = useTemplateRef("InputRef")
	const progressVisible = ref(true)
	const progress = ref(0)
	const imageData = ref()
	var file;
	var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
	var chunkSize = 1024*1024;//分片大小
	var fileArray = [];//存储分片的数组
	var spark = new SparkMD5.ArrayBuffer();
	var sparkArray = [];//存储分片MD5的数组
	const reader = new FileReader();
	var chunkCount = 0;	//分片数量
	var md5 = "";
	var suffix = "";
	var videoID;
	
	const form = reactive({
		title:"",
		part:"",
		collection:"",
		tag:"",
		profile:"",
		cover:""
	})
	
	
	const fileRef = useTemplateRef("fileRef");
	function init(){
		progress.value = 0;
		file = "";
		fileArray = [];
		sparkArray = [];
		chunkCount = 0;
		spark.end();
		md5="";
		suffix = "";
		imageData.value = null;
		clearReactive(form);
		videoID = 0
	}
	
	const clearReactive = (myReactive) => {
	      // 删除对象的每个属性
	      Object.keys(myReactive).forEach(key => {
	        delete myReactive[key];
	      });
	    };
	
	function fileClick(){
		fileRef.value.click();
	}
	
	async function handleDrop(event) {
		init()
		file = event.dataTransfer.files[0];
		createChunks(file);
		var fileName = file.name;
		suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	
	async function handleFileUpload(event){
		init();
		file = event.target.files[0];
		createChunks(file);
		var fileName = file.name;
		suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	
	async function createChunks(file){
		progressVisible.value = false;
		chunkCount = Math.ceil(file.size/chunkSize);
		form.title = file.name?.split(".")[0]
		console.log("count = " + chunkCount)
		var i = 0;
		const load = async()=>{
			var theChunk = blobSlice.call(file,i * chunkSize, (i + 1) * chunkSize)
			fileArray.push(theChunk);//分片存入分片数组
			reader.readAsArrayBuffer(theChunk);//分片通过FileReader获得该分片的md5值
		}
		load();

		reader.onload = async(e) =>{
			spark.append(e.target.result)
			var hash = SparkMD5.ArrayBuffer.hash(e.target.result);
			sparkArray.push(hash)
			i++
			console.log(i)
			
			progress.value = Math.round(i/chunkCount * 25);
			if(i<chunkCount)
			{
				load();
			}
			else{
				md5 = spark.end();
				preUpload();
				return;
			}
		}
	}
	
	// function getState(){
		
	// 	console.log(sparkArray)
	// 	console.log(fileArray);
	// 	// console.log(spark.getState());
	// 	console.log(spark.getState())
		
	// }
	// function end(){
	// 	console.log(md5);
	// }
	async function preUpload(){
		if(!file){
			alert('请选择文件');
			return;
		}
		console.log(md5);
		var formData = new FormData();
		formData.append('md5',md5);
		// formData.append('title',form.title);
		formData.append("authId",id.value);
		// formData.append("partId",form.part);
		// formData.append("collectionId",form.collection)
		// formData.append("tags",form.tag.toString());
		// formData.append("url","");
		// formData.append("coverUrl","");
		formData.append('max',chunkCount);
		formData.append('suffix',suffix);
		request.post('/video/preUpload',formData)
		.then((res)=>{
			videoID = res.data.id;
			if(res.data.index == -1){
				console.log("已存在");
				progress.value = 100;
			}
			else if(res.data.index > chunkCount){
				mergeChunk();
			}
			else{
				submitFile(res.data.index);
			}
		})
	}
	
	
	async function submitFile(index){
		var curChunk = index; 
		var chunk = fileArray[curChunk-1]
		var formData = new FormData();
		formData.append('file',chunk);
		formData.append('md5',md5)
		formData.append('index',curChunk);
		formData.append('max',chunkCount);
		await uploadFile(formData);
		var res = await getCurrentChunk();
		console.log("res = "+res.data)
		curChunk = res.data
		progress.value =25 + Math.round(curChunk/chunkCount * 75)
		if(curChunk <= chunkCount){
			submitFile(curChunk++)
		}
		else{
			mergeChunk()
			return
		}
	}
	
	function getCurrentChunk(){
		return new Promise((resolve) =>{
			var formData = new FormData();
			formData.append("md5",md5);
			request.post('/video/getCurrentChunk',formData)
			.then(res=>{
				if(res.code == 500){
					deleteVideo()
				}
				resolve(res);
			})

		})
		
	}
	
	function uploadFile(formData){
		return new Promise((resolve) =>{
		request.post('/video/upload',formData)
		.then(res=>{
			if(res.code == 500){
				deleteVideo()
			}
			resolve(res)
		})
	})
	}
	
	function mergeChunk(){
		var formData = new FormData();
		formData.append("md5",md5);
		formData.append('id',videoID)
		request.post('/video/mergeChunk',formData)
		.then(res=>{
			if(res.code == 500){
				deleteVideo()
			}else{
				progress.value = 100;
				imageData.value = res.data;
			}
			
			console.log(res.data)
		})

	}
	function deleteVideo(){
		console.log("执行catch")
		var formData = new FormData();
		formData.append('id',videoID)
		request.post("video/deleteVideo",formData)
		.then((res)=>{
			console.log("出错了"+res.data)
		})
	}
	
	const handleClose = (tag) => {
	  tags.value.splice(tags.value.indexOf(tag), 1)
	  form.tag = tags.value.toString()
	}
	
	const showInput = ()=>{
	  inputVisible.value = true
	  nextTick(() => {
	    InputRef.value.input.focus()
	  })
	}
	
	const handleInputConfirm = () => {
		console.log(inputValue.value)
		if (inputValue.value) {
			tags.value.push(inputValue.value)
			form.tag = tags.value.toString()
		}
		inputVisible.value = false
		inputValue.value = ''
	}
	watch(progress,()=>{
		if(progress.value >100){
			
		}
	})
	function videoSubmit(){
		if(progress.value < 100){
			return;
		}
		var formData = new FormData();
		// formData.append('md5',md5);
		formData.append('id',videoID);
		formData.append('title',form.title);
		// formData.append("authId",id.value);
		formData.append("partId",form.part);
		formData.append("collectionId",form.collection)
		formData.append("tags",form.tag.toString());
		// formData.append("url","");
		formData.append("coverUrl",form.cover);
		formData.append("profile",form.profile)
		formData.append('max',chunkCount);
		formData.append('suffix',suffix);
		request.post("videoInfo/submit",formData)
		.then(res =>{
			console.log("上传成功")
			console.log(res)
			success("上传成功")
			init();
		})
	}
	
	function success(message){
	  ElMessage({
	    message: message,
	    type: 'success',
	  })
	}
	function error(message){
		ElMessage({
			message:message,
			type:'error',
			showClose: true,
		})
	}
	
	
</script>

<style scoped>
	.tip{
		color: white;
		cursor: pointer;
	}

	.form{
		margin: 10px auto;
		width: 800px;
		color: black;
	}
	.elInput{
		margin-left: 50px;
	}
	.w-20{
		width:80px;
	}
</style>