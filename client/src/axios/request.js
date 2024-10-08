import axios from 'axios'

// axios.defaults.baseURL = "http://localhost:8080"

const request = axios.create({
	baseURL:'http://localhost:8080',
	timeout:30000
});

//请求拦截器
request.interceptors.request.use(config=>{
	// config.headers['Content-Type'] = 'application/json;charset=utf-8';
	// let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem(("user")))
	// config.headers['token'] = user.token;
	config.headers['token'] = 12345678;
	
	
	return config
},error => {
	console.error('request error:' + error)
	return Promise.reject(error)
});

//响应拦截器
request.interceptors.response.use(
	response => {
		return response.data;
	},
	error =>{
		console.error('response error:' + error)
		return Promise.reject(error)
	}
)


export default request