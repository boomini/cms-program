import Vue from "vue";
import Vuex from "vuex";
import router from "../router"
import axios from "axios"
import NetworkUtils from "../utils/NetworkUtils";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        userInfo: null,
        isLogin: false,
        isLoginError: false,
        adminCheck: false,
        userAccessoken: null,
        userList: [],
        postList: []
    },
    mutations: {
        // 로그인이 성공했을 때,
        loginSuccess(state, payload) {
            state.isLogin = true
            state.isLoginError = false
            state.userInfo = payload
            if (state.userInfo.authority === "ROLE_ADMIN") {
                state.adminCheck = true
            }
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
            state.adminCheck = false
        },
        setUserList(state, payload) {
            state.userList = payload
        },
        setPostList(state, payload) {
            state.postList = payload
        }

    },
    actions: {
        //로그인 시도
        signin({ dispatch }, params) {
            return NetworkUtils.LOGIN(params).then((res) => {
                console.log(res)
                let token = res.data
                    //토큰을 로컬스토리지에 저장
                localStorage.setItem("x-auth-token", token)
                dispatch("getMemberInfo")
                alert("로그인이 되었습니다.")

            }).catch(res => {
                alert(res),
                    console.log(res)
            });
        }
        // axios
        //     .post("http://localhost:3500/v1/signin", params, {
        //         headers: { 'x-accept-type': 'operator' }
        //     })
        //     //post방식에는 두번째 인자로 파라미터가 오고
        //     .then(res => {


        //     //성공시 token(실제로는 token과 함께 user_id값을 받아올 것이다.)
        //     //토큰을 헤더에 포함시켜서 유저 정보를 요청
        //     let token = res.data.data
        //         //토큰을 로컬스토리지에 저장
        //     localStorage.setItem("x-auth-token", token)
        //     dispatch("getMemberInfo")
        //     alert("로그인이 완료되었습니다..")
        //     console.log(res)
        // })
        // .catch(err => {
        //     //alert('이메일과 비밀번호를 확인하세요')
        //     console.log(err);
        // });
        ,
        kakaosignin({ dispatch }, param) {
            return NetworkUtils.KAKAOLOGIN(param).then((res) => {
                console.log(res)
                let token = res.data
                localStorage.setItem("x-auth-token", token)
                dispatch("getMemberInfo")
                alert("로그인이 되었습니다.")
            }).catch(res => {
                alert(res),
                    console.log(res)
                this.$router.push({ name: "signin" });
            });

        },
        kakaosignup({ dispatch }, param) {

            return NetworkUtils.KAKAOSIGNUP(param).then((res) => {
                console.log(res)
                dispatch("kakaosignin", param.token)
                alert("회원가입이 되었습니다.")
            }).catch(res => {
                alert(res),
                    console.log(res)
            });

        },

        //로그아웃시
        logout({ commit }) {
            commit("logout")
            router.push({ name: "home" })
            localStorage.removeItem("x-auth-token")
        },
        getMemberInfo({ commit }) {
            // 토큰 -> 멤버 정보를 반환
            // 새로 고침 -> 토큰만 가지고 멤버정보를 요청 
            //로컬 스토리지에 저장되어 있는 토큰을 불러온다.
            let token = localStorage.getItem("x-auth-token")
            axios
                .get("http://localhost:3500/v1/user", {
                    headers: {
                        "x-auth-token": token
                    }
                })
                .then(response => {
                    let userInfo = {
                        uid: response.data.data.uid,
                        name: response.data.data.name,
                        authority: response.data.data.authority
                    }
                    commit('loginSuccess', userInfo)
                    router.push({ name: "mypage" })
                })
                .catch(error => {
                    //alert('이메일과 비밀번호를 확인하세요')
                    console.log(error)
                })
                //get방식에는 두번째 인자로 config가 온다.
        },
        // //회원가입시
        signup({ dispatch }, params) {

            return NetworkUtils.SIGNUP(params).then((res) => {
                console.log(res)
                let userInfo = {
                    uid: params.uid,
                    password: params.password,
                }
                dispatch("signin", userInfo)
                alert("회원가입이 되었습니다.")
            }).catch(res => {
                alert(res),
                    console.log(res)
            });

        },
        //리스트불러오기
        getUserList({ commit }) {
            let token = localStorage.getItem("x-auth-token")
            axios
                .get("http://localhost:3500/v1/users", {
                    headers: {
                        "x-auth-token": token
                    }
                })
                .then(response => {
                    commit('setUserList', response.data.list)


                })
                .catch(error => {
                    console.log(error)
                })
        },
        updateUsrInfo({ dispatch }, updateObj) {
            let token = localStorage.getItem("x-auth-token")
            console.log(updateObj)
            axios
                .put("http://localhost:3500/v1/user", updateObj, {
                    headers: {
                        "x-auth-token": token,
                        contentType: 'application/json'
                    },

                })
                .then(res => {
                    console.log(res)
                    dispatch("getMemberInfo")
                    alert("수정이 완료되었습니다.")

                })
                .catch(err => {
                    console.log(err)
                })
        },
        userDelete({ dispatch }, deleteUid) {
            let token = localStorage.getItem("x-auth-token")
            console.log(deleteUid)
            axios
                .delete("http://localhost:3500/v1/user/" + deleteUid, {
                    headers: {
                        "x-auth-token": token,
                        contentType: 'application/json'
                    },

                })
                .then(res => {
                    console.log(res)
                    dispatch("getUserList")
                    alert("회원이 삭제되었씁니다..")

                })
                .catch(err => {
                    console.log(err)
                })
        },
        //리스트불러오기
        getPostList({ commit }, boardName) {
            console.log(boardName.boardName)
            axios
                .get("http://localhost:3500/v1/board/" + boardName.boardName + "/posts")
                .then(res => {
                    commit('setPostList', res.data.list)
                    console.log(res)

                })
                .catch(err => {
                    console.log(err)
                })
        },
    },
    modules: {},
});