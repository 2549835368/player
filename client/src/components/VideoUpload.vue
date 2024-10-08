<template>
	<input type="file" name="file" @change="handleFileUpload">
	<button @click="preUpload">按钮</button>
	<button @click="getState" >state</button>
	<button @click="end" >end</button>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { ref } from 'vue';
	import SparkMD5 from 'spark-md5';
	
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
	
	function init(){
		file = "";
		fileArray = [];
		sparkArray = [];
		chunkCount = 0;
		spark.end();
		md5="";
		suffix = "";
	}
	
	async function handleFileUpload(event){
		init();
		file = event.target.files[0];
		createChunks(file);
		var fileName = file.name;
		suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
	}
	
	async function createChunks(file){
		chunkCount = Math.ceil(file.size/chunkSize);
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
			if(i<chunkCount)
			{
				load();
			}
			else{
				md5 = spark.end();
				return;
			}
		}
	}
	
	function getState(){
		
		console.log(sparkArray)
		console.log(fileArray);
		// console.log(spark.getState());
		console.log(spark.getState())
		
	}
	function end(){
		console.log(md5);
	}
	async function preUpload(){
		if(!file){
			alert('请选择文件');
			return;
		}
		console.log(md5);
		var formData = new FormData();
		formData.append('md5',md5);
		formData.append('title',"123");
		formData.append("authId",1);
		formData.append("partId",1);
		formData.append("tags",2);
		formData.append("url","");
		formData.append("coverUrl","");
		formData.append('max',chunkCount);
		formData.append('suffix',suffix);
		request.post('/video/preUpload',formData)
		.then((res)=>{
			videoID = res.data.id;
			if(res.data.index == -1){
				console.log("已存在");
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
				resolve(res);
			})
			.catch(err =>{
				deleteVideo()
			})
		})
		
	}
	
	function uploadFile(formData){
		return new Promise((resolve) =>{
		request.post('/video/upload',formData)
		.then(res=>{
			console.log(res)
			resolve(res)
		})
		.catch(err =>{
			deleteVideo()
		})
	})
	}
	
	function mergeChunk(){
		var formData = new FormData();
		formData.append("md5",md5);
		formData.append('id',videoID)
		request.post('/video/mergeChunk',formData)
		.then(res=>{
			console.log(res.data)
		})
		.catch(err =>{
			deleteVideo()
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
</script>

<style>
</style>