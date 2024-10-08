<template>
	<div class="side-box">
		<el-scrollbar>
			<ul>
				<router-link v-for="(item,index) in partCaption" :to='"/home/" + (item.code)'>
					<li>
						{{item.caption}}
					</li>
				</router-link>
			</ul>
		</el-scrollbar>
	</div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useDataStore } from '../stores/dataStore.js';
	import { storeToRefs } from 'pinia'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref, watch } from 'vue';
	

	const dataStore = useDataStore();
	var { partCaption } = storeToRefs(dataStore);
	onMounted(()=>{
		dataStore.getPart();
	})
	
	
	
	
</script>

<style scoped>
	.side-box{
		position: relative;
		width: 100%;
		border-radius: 10px 0px 0px 10px;
		margin-left: auto;	
		height: calc(100vh - 112px);
		border: solid var(--bg-color) 1px;
		/* transition: 0.5s ease; */
	}
	.side-box:hover{
		/* background-color: var(--bg-color);
		transition: 0.5s ease-in-out; */
	}
	ul{
		padding: 0px;
		text-align: center;
		list-style-type:none;
		font-weight: bold;
		color: rgb(0, 0, 0);
	}
	li{
		height: 35px;
		line-height: 35px;
		transition: 0.5s ease;
		color: #000000;
		text-decoration: none;

	}
	li:hover{
		background-color: var(--bg-color);
		transition: 0.5s ease;
		color: rgba(0, 170, 255, 1.0);

	}
</style>