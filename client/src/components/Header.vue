<template>
	<div class="header-color ">
		<div class="common-margin header">
			<div>logo</div>


			<router-link to="/home">
				首页
			</router-link>

		
		
		

			<div>Search</div>


			<User></User>

		</div>
	</div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { ref } from 'vue';
	import User from './home/User.vue';
	
	let leftLinklist = [
		{title:"首页",icon:"",path:"/home"},
		// {title:"分类",icon:"",path:""},
		// {title:"排行榜",icon:"",path:""},
	]
	let rightLinklist = [
		{title:"个人",icon:"",path:""},
		// {title:"历史",icon:"",path:""},
	]
	
</script>

<style>

</style>