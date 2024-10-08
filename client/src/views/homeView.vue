<template>
	<div class="container">
		<div id="main" class="main">
			<el-row class="main">
				<el-col :span="1"></el-col>
				<el-col :span="2">
					<Side></Side>
				</el-col>
				<el-col :span="20">
					<div class="scroll">
						<el-scrollbar>
							<Card></Card>
							<Card></Card>
							<Card></Card>
							<Card></Card>
							<Card></Card>
							<Card></Card>
							<Card></Card>
						</el-scrollbar>
					</div>
				</el-col>
				<el-col :span="1"></el-col>
			</el-row>
		</div>
	</div>

	
	
</template>

<script setup>
	import request from '../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { ref } from 'vue';
	import Card from '../components/VCard.vue'
	import Side from '../components/Side.vue'
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