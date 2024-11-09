<template>
		<div class="background">
			
		</div>
		<div class="common-layout">
			<el-container>
			  <el-header class="no-margin">
				  <Header></Header>
			  </el-header>
				<el-main class="no-margin">
					<router-view :key="route.path"></router-view>
				</el-main>
			</el-container>
		</div>
	
</template>

<script setup>
	import Header from './components/Header.vue'
	import Up from './components/VideoUpload.vue'
	import { useRoute, useRouter } from 'vue-router';
	
	const route = useRoute();
	
</script>

<style>

</style>
