import Vue from "vue";
import Vuex from "vuex";
import router from "../router"
import axios from "axios";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        userInfo: null,
        isLogin: false,
        isLoginError: false
    },
    mutations: {
        // 로그인이 성공했을 때,
        loginSuccess(state, payload) {
            state.isLogin = true
            state.isLoginError = false
            state.userInfo = payload
        },
        // 로그인이 실패했을 때,
        loginError(state) {
            state.isLogin = false
            state.isLoginError = true
        },
        logout(state) {
            state.isLogin = false
            state.isLoginError = false
            state.userInfo = null
        }
    },
    actions: {
        //로그인 시도
        signin({ dispatch }, params) {
            //로그인 -> 토큰 변환
            // console.log(loginObj)
            // let selectedUser = null;
            // state.allUsers.forEach(user => {
            //     if (user.email === loginObj.email) selectedUser = user;
            // });
            // if (selectedUser === null || selectedUser.password !== loginObj.password)
            //     commit("loginError")
            // else {
            //     commit("loginSuccess", selectedUser);
            //     router.push({ name: "mypage" })

            // }
            console.log(params);
            axios
                .post("http://localhost:3500/v1/signin", params, {
                    headers: { 'x-accept-type': 'operator' }
                })
                //post방식에는 두번째 인자로 파라미터가 오고
                .then(res => {
                    //성공시 token(실제로는 token과 함께 user_id값을 받아올 것이다.)
                    //토큰을 헤더에 포함시켜서 유저 정보를 요청
                    console.log(res);
                    let token = res.data.data
                        //토큰을 로컬스토리지에 저장
                    localStorage.setItem("x-auth-token", token)
                    dispatch("getMemberInfo")

                })
                .catch(err => {
                    //alert('이메일과 비밀번호를 확인하세요')
                    console.log(err);
                });

        },

        //로그아웃시
        logout({ commit }) {
            commit("logout")
            router.push({ name: "home" })
        },
        getMemberInfo({ commit }) {
            // 토큰 -> 멤버 정보를 반환
            // 새로 고침 -> 토큰만 가지고 멤버정보를 요청 
            //로컬 스토리지에 저장되어 있는 토큰을 불러온다.
            let token = localStorage.getItem("x-auth-token")
            console.log(token)
                // let config = {
                //     //config에서 헤더값을 설정해줄 수 있고 , 헤더내에 토큰을 포함시키기 위해 사용한다.
                //     headers: {
                //         "x-auth-token": token
                //     }
                // }
            axios
                .get("http://localhost:3500/v1/user", {
                    headers: {
                        "x-auth-token": token
                    }
                })
                .then(response => {
                    console.log(response)
                    let userInfo = {
                        uid: response.data.data.uid,
                        name: response.data.data.name,
                    }
                    commit('loginSuccess', userInfo)
                })
                .catch(error => {
                    // alert('이메일과 비밀번호를 확인하세요')
                    console.log(error)
                })
                //get방식에는 두번째 인자로 config가 온다.
        }
    },
    modules: {},
});