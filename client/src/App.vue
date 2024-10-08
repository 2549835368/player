<template>
		<video autoplay loop muted id="video">
			<source src="./assets/bg.mp4"></source>
		</video>
		<div class="common-layout">
			<el-container>
			  <el-header class="no-margin">
				  <Header></Header>
			  </el-header>
				<el-main class="no-margin">
					<router-view></router-view>
				</el-main>
			</el-container>
		</div>
	
</template>

<script setup>
	import Header from './components/Header.vue'
	import Up from './components/VideoUpload.vue'

	
</script>

<style>

</style>
