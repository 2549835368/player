<template>
	<el-tabs v-model="activeName" class="tabs">
		<el-tab-pane label="简介" name="first">
			<theProfile></theProfile>
		</el-tab-pane>
		<el-tab-pane label="选集" name="second">
			<Collection></Collection>
		</el-tab-pane>
		<el-tab-pane label="评论" name="third">
			<Comment></Comment>
		</el-tab-pane>
	</el-tabs>
</template>

<script setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref } from 'vue';
	import Collection from './Collection.vue';
	import Comment from './Comment.vue';
	import theProfile from './Profile.vue'
	
	const activeName = ref('first')
	
</script>

<style>
	.el-tabs__item {
		/* 修改为您想要的文字大小 */
	    font-size: 24px !important;
	}
</style>