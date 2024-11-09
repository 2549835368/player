<template>
	<el-scrollbar>
		<Card v-for="(item,index) in videoList" 
		v-model:title="item.title" 
		v-model:cover="item.coverUrl"
		v-model:vid="item.id"></Card>
	</el-scrollbar>
</template>

<script setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { useDataStore } from '../../stores/dataStore.js';
	import { storeToRefs } from 'pinia'
	import { onMounted, ref } from 'vue';
	import Card from'./VCard.vue'
	
	const dataStore = useDataStore();
	
	const { videoList } = storeToRefs(dataStore);
	console.log(videoList.value)
	
</script>

<style scoped>
	.card-box{
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: space-around;
	}
</style>