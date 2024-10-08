<template>
	{{userStore.id}}
</template>

<script setup>
	import {useUserStroe} from '../stores/userStore.js'
	import axios from 'axios'
	const userStore = useUserStroe()
	console.log("axios")
	userStore.set();
	
</script>

<style>
</style>