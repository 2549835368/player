<template>
	<div class="container">
		<div id="main" class="main">
			<el-row class="main">

				<el-col :span="2">
					<Side></Side>
				</el-col>
				<el-col :span="22">
					<div class="scroll">
						<CardBox></CardBox>
					</div>
				</el-col>

			</el-row>
		</div>
	</div>

	
	
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { ref } from 'vue';
	import CardBox from '../components/home/CardBox.vue'
	import Side from '../components/home/Side.vue'
</script>

<style scoped>

	.scroll{
		height: calc(100% - 2px);
		border: solid var(--bg-color) 1px;
		border-radius: 0px 10px 10px 0px;
		border-left: 0px;
	}
	.el-scrollbar{
		height: calc(100vh - 112px);
	}
	.main{
		height: calc(100vh - 110px);
	}

</style>