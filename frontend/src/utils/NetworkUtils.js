import axios from "axios";
export default {
    LOGIN: function(params) {
        return new Promise(function(resolve, reject) {
            axios
                .post("http://localhost:3500/v1/signin", params, {
                    headers: { 'x-accept-type': 'operator' }
                })
                //post방식에는 두번째 인자로 파라미터가 오고
                .then(res => {
                    console.log(res)
                    if (res.data.code === 0) {
                        resolve(res.data)
                    } else {
                        reject(res.data.msg)
                    }

                })
                .catch(err => {
                    //alert('이메일과 비밀번호를 확인하세요')
                    console.log(err)
                });
        })
    },
    KAKAOLOGIN: function(param) {
        return new Promise(function(resolve, reject) {
            axios
                .post("http://localhost:3500/v1/signin/kakao", param, {
                    headers: { "x-accept-type": "operator" }
                })
                //post방식에는 두번째 인자로 파라미터가 오고
                .then(res => {
                    //성공시 token(실제로는 token과 함께 user_id값을 받아올 것이다.)
                    //토큰을 헤더에 포함시켜서 유저 정보를 요청
                    console.log(res);
                    if (res.data.code == 0) {
                        resolve(res.data)
                    } else {
                        reject(res.data.msg)
                    }

                })
                .catch(err => {
                    // alert('이메일과 비밀번호를 확인하세요')
                    console.log("error" + err);
                });
        })
    },
    SIGNUP: function(params) {
        return new Promise(function(resolve, reject) {
            axios
                .post("http://localhost:3500/v1/signup", params, {
                    headers: { 'x-accept-type': 'operator' }
                })
                //post방식에는 두번째 인자로 파라미터가 오고
                .then(res => {
                    if (res.data.code === 0) {
                        resolve(res.data)
                    } else {
                        reject(res.data.msg)
                    }

                })
                .catch(err => {
                    //alert('이메일과 비밀번호를 확인하세요')
                    console.log(err)
                });
        })
    },
    KAKAOSIGNUP: function(params) {
        return new Promise(function(resolve, reject) {
            axios
                .post("http://localhost:3500/v1/signup/kakao", params, {
                    headers: { 'x-accept-type': 'operator' }
                })
                //post방식에는 두번째 인자로 파라미터가 오고
                .then(res => {
                    if (res.data.code === 0) {
                        resolve(res.data)
                    } else {
                        reject(res.data.msg)
                    }

                })
                .catch(err => {
                    //alert('이메일과 비밀번호를 확인하세요')
                    console.log(err)
                });
        })
    }
}