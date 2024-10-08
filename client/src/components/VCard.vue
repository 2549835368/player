<template>
	<div class="card-style">
		<router-link :to="url">
			<el-image :src = "url" class="card-image"></el-image>
			<el-tooltip effect="light" :content="title" placement="bottom">
				<el-text truncated line-clamp="2" class="card-text">
					{{title}}
				</el-text>
			</el-tooltip>
		</router-link>
	</div>
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref } from 'vue';
	
	var title = "视频标题";
	var url = "/play/4";
	
</script>

<style scoped>
	.card-style{
		margin: 26px;
		z-index: 10;
		width : 300px;
		height : 218.75px;
		border-radius: 10px;
		/* background-color: rgba(255,255,255,0); */
		transition: 0.5s ease;
	}
	.card-style:hover{
		/* background-color: rgba(255,255,255,1); */
		box-shadow: 0px 0px 12px #ffff00;
		transition: 0.5s ease-in-out;
	}
	.card-image{
		width: 300px;
		height: 168.75px;
		border-radius: 10px 10px 0px 0px;
	}
	.card-text{
		width: 290px;
		height: 40px;
		padding: 5px;
		transform: translateY(-5px);
		background-color: rgba(255,255,255,1);
		border-radius: 0px 0px 10px 10px;
		font-weight: bold;
		font-size: 16px;
		white-space: pre-wrap !important;
		transition: 0.5s ease;
	}
	.card-text:hover{
		color: rgba(0, 170, 255, 1.0);
		transition: 0.5s ease;
	}
</style>