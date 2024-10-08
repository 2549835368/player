<template>
	<div class="header-color header">
	<el-row class="common-margin">
		<el-col :span="6">
			<div>logo</div>
		</el-col>
		
		<el-col :span="3" v-for="(v,i) in leftLinklist" :key = "i">
			<router-link :to="v.path">
				{{v.title}}
			</router-link>
		</el-col>
		
		
		
		<el-col :span="5">
			<div>Search</div>
		</el-col>
		<el-col :span="3" v-for="(v,i) in rightLinklist" :key = "i">
			<router-link :to="v.path">
				{{v.title}}
			</router-link>
		</el-col>
		
	</el-row>
	</div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { ref } from 'vue';
	
	let leftLinklist = [
		{title:"首页",icon:"",path:"/home"},
		// {title:"分类",icon:"",path:""},
		{title:"排行榜",icon:"",path:""},
	]
	let rightLinklist = [
		{title:"个人",icon:"",path:""},
		{title:"历史",icon:"",path:""},
	]
	
</script>

<style scoped>
	.header{
		height: 40px;
		padding-top: 20px;
	}
</style>