<template>
	<el-checkbox border v-model="isCollection" style="background-color: white;">使用合集</el-checkbox>
	<div :class="{hide:!isCollection}" >
		<div style="display: flex;">
		<el-radio-group v-model="radioResult">
			<el-popover v-for="(item,index) in collection" key="index">
				
				<el-button @click="del(item.id,item.title)">删除该合集</el-button>
				<template #reference>
					 <el-radio :value="item.id" border style="margin: 0 10px;">{{item.title}}</el-radio>
				</template>
			</el-popover>
			 
		</el-radio-group>
		
		<el-button @click="dialogVisible = true" >+创建合集</el-button>
		</div>
	</div>
	<el-dialog v-model="dialogVisible" center width="500">
		<template #header="{ titleId, titleClass }">
		      <div class="my-header">
		        <h2 :id="titleId" :class="titleClass">创建合集</h2>
		      </div>
		</template>
		<div class="popForm">
			<el-form v-model="cForm">
				<el-form-item label="合集名">
					<el-input v-model="cForm.title"></el-input>
				</el-form-item>
				<el-form-item>
					<el-radio-group>
					</el-radio-group>
				</el-form-item>
				
				<el-form-item>
					<el-button style="margin: 0 auto; margin-top: 20px;" @click="createC">创建</el-button>
				</el-form-item>
			</el-form>
		</div>
		
	</el-dialog>
	
	
</template>

<script setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, reactive, ref, watch } from 'vue';
	import { useUserStore } from '../../stores/userStore.js';
	import { storeToRefs } from 'pinia';
	import { ElMessage, ElMessageBox } from "element-plus"
	
	const userStore = useUserStore();
	const { id } = storeToRefs(userStore);
	const collection = ref()
	const radioResult = defineModel()
	const dialogVisible = ref(false)
	const videoListOutCol = ref([])
	const cForm = reactive({
		cForm:0,
		title:'',
		
	})
	const isCollection=ref(false)
	const useString = ref("使用合集")
	
	getCollection()
	function getCollection(){
		request.get("/collection/getByUid?uid="+id.value)
		.then(res=>{
			collection.value = res.data
		})
	}

	
	function getVideoByCollection(item){
		request.get("/collection/get?cid="+item.id)
		.then(res=>{
			
		})
	}
	
	const createC = ()=>{
		request.post("/collection/createCollection",cForm)
		.then(res=>{
			console.log(res)
			success("创建成功")
			cForm.title = '';
			dialogVisible.value = false;
			getCollection();
		})
	}
	
	function del(id,title){
		ElMessageBox.confirm(
		    '是否要删除合集'+title,
		    '警告',
		    {
		      confirmButtonText: '确定',
		      cancelButtonText: '取消',
		      type: 'warning',
		    }
		  )
		    .then(() => {
		      request.get("/collection/delCollection?cid="+id)
		      .then(res=>{
		      	console.log(res)
		      	success("删除成功")
		      	getCollection()
		      	if(id == radioResult.value){
		      		radioResult.value = 0;
		      	}
		      })
		    })
		    .catch(() => {
		      ElMessage({
		        type: 'info',
		        message: '删除取消',
		      })
		    })

	}
	
	watch(isCollection,()=>{
		if(!isCollection){
			radioResult.value = 0;
		}
	})
	
	function success(message){
	  ElMessage({
	    message: message,
	    type: 'success',
	  })
	}
	function error(message){
		ElMessage({
			message:message,
			type:'error',
			showClose: true,
		})
	}
</script>

<style scoped>
</style>