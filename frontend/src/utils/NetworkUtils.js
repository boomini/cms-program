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
    }
}