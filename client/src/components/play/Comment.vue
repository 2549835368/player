<template>
	<el-scrollbar class="scroll" @scroll="scroll" ref="myScroll">
		<div ref="innerRef" class="innerWraper">
			<div class="comment" v-for="(item,index) in comment">
				<div class="comment-header">
					<el-avatar>
					</el-avatar>
					<div class="username">
						{{idList[item.uid]}}
					</div>
					<div style="margin-left:auto; font-size: 14px;">{{"#"+(index + 1)}}</div>
				</div>
				<div class="comment-content">
					{{item.content}}
				</div>
				<div class="comment-state">
					<div class="date">
						{{toFullDate(item.createdTime)}}
					</div>
					<div class="small-box whiteToBlue" @click="open(index)">
						<el-icon class=" reply-icon" ><ChatLineRound /></el-icon>
						<div class="small">{{item.childComment.total}}</div>
					</div>
					<a class="reply-button whiteToBlue" @click="reply(item.uid,index,-1)">回复</a>
				</div>
				<div class="reply" v-show="item.childComment.total != 0 && openList[index] == 1">
					<div class="comment" v-for="(ite,ind) in item.childComment.records">
						<div class="comment-header">
							<el-avatar :size="35">
							</el-avatar>
							<div class="username">
								{{idList[ite.uid]}}
							</div>
						</div>
						<div class="comment-content" style="font-size: 14px;">
							{{ite.content}}
						</div>
						<div class="comment-state">
							<div class="date">
								{{toFullDate(ite.createdTime)}}
							</div>
							<a class="reply-button whiteToBlue" @click="reply(ite.uid,index,ind)">回复</a>
						</div>
						<div>
						</div>
					</div>
					<el-pagination layout="prev, pager, next" style="margin: 10px 40px; "
						size="small" 
						v-model:page-count="item.childComment.pages"
						v-model:current-page="item.childComment.current"
						hide-on-single-page
						@current-change="handleCurrentChange(index,item.id,item.childComment.current,3)"
					/>
				</div>
					<div :id="'input' +(index)" class="reply-box" v-show="replyBox.current==index">
						<el-input class="small-input" v-model="smallInput" :placeholder="smallPlace" ></el-input>
						<el-button class="small-button" type="primary" @click="submitChildComment(item.id)">发送</el-button>
					</div>
				<el-divider></el-divider>
			</div>
		</div>
	</el-scrollbar>
	
	<el-input class="input" v-model="input" :placeholder="inputPlace"></el-input>
	<el-button class="button" type="primary" @click="submitComment(0)" :disabled ='!loged'>发送</el-button>
</template>

<script lang="ts" setup>
	import request from '../../axios/request.js'
	import { useRoute, useRouter } from 'vue-router';
	import { nextTick, onMounted, reactive, ref, watch, useTemplateRef, onUpdated } from 'vue';
	import { useUserStore } from '../../stores/userStore.js'
	import { storeToRefs } from 'pinia';
	import { ElMessage } from "element-plus"
	import { ElScrollbar } from 'element-plus'
	
	const route = useRoute();
	const vid = route.params.vid;
	const input = ref('')
	
	const smallInput = ref('')
	const smallPlace = ref('')
	
	const currentData = ref()
	var comment = ref([]);
	var idList = ref(JSON)
	
	const userStore = useUserStore();
	var {id, username, token, loged} = storeToRefs(userStore);
	
	const myWrapRef = useTemplateRef('innerRef')
	const myRef = useTemplateRef("myScroll");
	
	const replyBox = reactive({current:-1,child:-2});
	const openList = ref();
	const inputPlace = ref();
	init()
	function init(){
		getComment(1,5)
		
		if(!loged.value){
			inputPlace.value = "登录后才能发送评论哦"
		}else{
			inputPlace.value = ""
		}
		watch(loged,()=>{
			if(!loged.value){
				inputPlace.value = "登录后才能发送评论哦"
			}else{
				inputPlace.value = "欢迎评论"
			}
		})
	}
	
	
	function getComment(current,size){
		request.get("/comment/getCommentByVid?vid=" + vid+"&current="+current+"&size="+size)
		.then(res =>{
			if(res.data.current == 1){
				comment.value = res.data.records
			}else{
				for(var newCom of res.data.records){
					comment.value.push(newCom)
				}
			}
			
			currentData.value = res.data;
			openList.value = Array(comment.value.length).fill(1)
			getName()
			
		})
	}
	
	function getName(){
		var list = []
		for(let i of comment.value)
		{
			list.push(i.uid)
			if(i.childComment.total != 0){
				for(let j of i.childComment.records){
					list.push(j.uid)
				}
			}
		}
		var formData = new FormData();
		formData.append("idList",list);
		request.post("/userInfo/getNamesById",formData)
		.then(res =>{
			idList.value = JSON.parse(JSON.stringify(res.data))
		})
	}

	function toFullDate(date){
		var theDate = new Date(date);
		var res = theDate.getFullYear() +"-"+ fillZero((theDate.getMonth()+1)) +"-" 
		+ fillZero(theDate.getDate())+" " + fillZero(theDate.getHours())+":" 
		+ fillZero(theDate.getMinutes())+":" + fillZero(theDate.getSeconds());
		return res;
	}
	
	function fillZero(str) {
	    var realNum;
	    if (str < 10) {
	        realNum = '0' + str;
	    } else {
	        realNum = str;
	    }
	    return realNum;
	}
	 
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
	 
	function submitComment(pid){
		var data = {
			pid:"",
			content:"",
			uid:"",
			vid:"",
			createdTime:""
		}
		if(input.value == ''){
			error("还什么都没输入呢")
			return
		}
		if(!id.value){
			error("请登录")
		}
		data.pid=pid
		data.content=input.value
		data.uid=id.value
		data.vid=vid
		data.createdTime=Date.now() 
		request.post("/comment/save",data)
		.then(res=>{
			if(res.code == 200){
				 input.value = '';
				 success("发送成功")
				 getComment(1,5);
			}else{
				 error("发送失败")
			}
			 		 
		})
	}
	
	function submitChildComment(pid){
		var data = {
			pid:"",
			content:"",
			uid:"",
			vid:"",
			createdTime:""
		}
		if(smallInput.value == ''){
			error("还什么都没输入呢")
			return
		}
		data.pid=pid
		if(replyBox.child==-1){
			console.log(replyBox.child)
			data.content=smallInput.value
		}else{
			data.content=smallPlace.value+""+smallInput.value
		}
		data.uid=id.value
		data.vid=vid
		data.createdTime=Date.now() 
		request.post("/comment/save",data)
		.then(res=>{
			if(res.code == 200){
				 smallInput.value = '';
				 success("发送成功")
				 getComment(1,5);
			}else{
				 error("发送失败")
			}
			 		 
		})
	}
	
	 
	function open(index){
		openList.value[index] = openList.value[index]==0?1:0
	 }
	function reply(toid,index,ind){
		if(!loged.value){
			error("请登陆后发送评论")
			return
		}
		smallPlace.value ="回复" + idList.value[toid] + ": ";
		if(replyBox.current == index&&replyBox.child == ind){
			replyBox.current = -1;
		}else{
			replyBox.current = index;
		}
		replyBox.child = ind;
		// document.getElementById("input"+index).getElementsByTagName("input")[0].focus()
		
	}
	function handleCurrentChange(index,pid,current,size){
		request.get("/comment/getCommentByPid?pid=" + pid+"&current="+current+"&size="+size)
		.then(res =>{
			comment.value[index].childComment = res.data
		})
	}
	
	
	const scroll = ({scrollTop})=>{
		// console.log(scrollTop)
		// console.log(myRef.value.wrapRef.scrollHeight - 449)
		const scrollTopRef = ref(scrollTop)
		var scrollHight = myRef.value.wrapRef.scrollHeight - 449;
		if(scrollTop > scrollHight){
			console.log("底部")
			var current = currentData.value.current
			if(current<currentData.value.pages){
				getComment(current + 1,5)
			}
			
		}

	}




	

</script>

<style scoped>
	div{
		color: white;
	}
	.comment{
		width: calc(100% - 10px);
	}
	.reply{
		padding-left: 40px;
	}
	.comment-header{
		display: flex;
		flex-wrap: nowrap;
		align-items: center;
		padding-top: 5px;
	}
	.comment-content{
		padding-left: 46px;
	}
	.comment-state{
		margin-top: 10px;
		padding-left: 46px;
		display: flex;
		flex-direction: row;
		align-items: center;
		flex-wrap: nowrap;
	}
	.scroll{
		height: calc(var(--box-height) - 100vh*0.12 - 82px);
	}
	.username{
		padding-left: 6px;
		font-size: 16px;
	}
	.input{
		width: calc(100% - 80px);
	}
	.button{
		width: 60px;
		margin-left: 10px;
	}
	.reply-button{
		font-size: 14px;
		margin-left: 15px;
	}
	.whiteToBlue{
		color: white;
		transition: 0.5s ease;
	}
	.whiteToBlue:hover{
		color: rgba(0, 170, 255, 1.0);
		transition: 0.5s ease;
		cursor: pointer;
	}
	.date{
		font-size: 14px;
	}
	.small{
		font-size: 12px;
	}
	.reply-icon{
		margin-left: 15px;
	}
	.small-box{
		display: flex;
		flex-direction: row;
		align-items: center;
		flex-wrap: nowrap;
	}
	.small-input{
		width: calc(100% - 120px);
		margin-left: 35px;
	}
	.small-button{
		width: 60px;
		margin-left: 10px;
	}
	.reply-box{
		margin-top: 20px;
	}
	.innerWraper{
		width: 100%;
		height: 100%;
	}
</style>