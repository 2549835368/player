<template>
	<el-avatar @click="visible = true" v-if="!loged">
		登录
	</el-avatar>
	
	<el-popover 
		placement="bottom"
		trigger="hover"
		width="100"
		>
		<template #reference>
			<el-avatar v-if="loged" :src="avatar">
				{{avatar == null?nickname[0]:avatar}}
			</el-avatar>
		</template>
		
		<template #default>
			<div>
				<a>个人中心</a>
				<router-link to="/upload">上传视频</router-link>
				<a @click="userStore.logout()()">退出登录</a>
			</div>
		</template>
	</el-popover>
	
	
	<el-dialog v-model="visible" width="400" title="登录" ref="dialog" @close="clear()" destroy-on-close>
		<el-form label-position="left" label-width="auto" :model="form" :rules="rules" ref="formRef" class="blackform">
			<el-form-item label="用户名" prop="username">
				<el-input class="elInput" v-model="form.username"></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="password">
				<el-input class="elInput" type="password" v-model="form.password"></el-input>
			</el-form-item>
			<el-form-item>

				<el-button @click="onSubmit" type="primary" class="s-button">登录</el-button>
				<el-button class="s-button">注册</el-button>
			</el-form-item>
		</el-form>
	</el-dialog>
</template>

<script lang="ts" setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { onMounted, ref, reactive} from 'vue';
	import { useUserStore } from '../../stores/userStore.js';
	import { storeToRefs } from 'pinia';
	import { ElMessage } from "element-plus"
	
	const userStore = useUserStore();
	var {id, username, nickname, token, loged, avatar} = storeToRefs(userStore);
	
	const visible = ref(false);
	const formRef = ref();
	function clear(){
		form.username = '';
		form.password = ''
	}
	
	const form = reactive({
		username:'',
		password:''
	})

	const rules = reactive({
	  username: [
	    { required: true, message: '请输入用户名', trigger: 'blur' },
	    { min:3, max: 10, message: '长度不能小于3大于10', trigger: 'blur' },
	  ],
	  password:[
		{ required: true, message: '请输入密码', trigger:'blur'},
		{ min: 4, max: 15, message: '长度不能小于4或大于15', trigger: 'blur'}
	  ]})
	
	function success(){
	  ElMessage({
	    message: '登录成功',
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

	
	const onSubmit = () =>{
		var data = {username:"",password:""}
		data.username = form.username;
		data.password = form.password;
		formRef.value.validate((valid,fields) =>{
			if(valid){
				request.post("user/login",data,{headers:{'Content-Type': 'application/json'}})
				.then(res =>{
					if(res.code == 200){
						id.value = res.data.id;
						username.value = res.data.username;
						nickname.value = res.data.nickname;
						token.value = res.data.token;
						loged.value = true
						
						visible.value = false;
						success();
					}else{
						error(res.message)
					}
					
				})
			}else{
				error('信息校验不通过');
			}
		})

	}
</script>

<style scoped>
	.elInput{
		width: 300px;
	}
	.s-button{
		margin-left: 20px !important;
	}

</style>