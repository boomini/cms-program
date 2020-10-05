<template>
  <v-container fill-height style="max-width:450px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-alert class="mb-3" :value="isLoginError" type="error"
          >아이디와 비밀번호를 확인해주세요.</v-alert
        >
        <v-alert class="mb-3" :value="isLogin" type="success"
          >로그인이 성공하였습니다.</v-alert
        >
        <v-card>
          <v-toolbar flat dense>
            <v-toolbar-title>로그인</v-toolbar-title>
          </v-toolbar>
          <div class="pa-3">
            <v-text-field
              v-model="uid"
              label="이메일을 입력하세요"
            ></v-text-field>
            <v-text-field
              v-model="password"
              type="password"
              label="패스워드를 입력하세요"
            ></v-text-field>
            <v-btn
              @click="
                signin({
                  uid,
                  password
                })
              "
              depressed
              block
              large
              color="primary"
              >로그인</v-btn
            >
            <v-btn
              depressed
              block
              large
              color="yellow"
              router
              @click="kakaoLogin"
              >카카오톡 로그인</v-btn
            >
            <v-btn router-link :to="{ name: 'signup' }">회원가입</v-btn>
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import axios from "axios";
export default {
  data() {
    return {
      uid: null,
      password: null,
      accessToken: null
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  mounted() {
    this.accessToken = this.$route.params.accessToken;
    //window.Kakao.Auth.setAccessToken(this.$route.params.accessToken);
    axios
      .post(
        "http://localhost:3500/v1/signin/kakao",
        { accessToken: this.accessToken },
        {
          headers: { "x-accept-type": "operator" }
        }
      )
      //post방식에는 두번째 인자로 파라미터가 오고
      .then(res => {
        //성공시 token(실제로는 token과 함께 user_id값을 받아올 것이다.)
        //토큰을 헤더에 포함시켜서 유저 정보를 요청
        console.log(res);
        // let token = res.data.data
        //     //토큰을 로컬스토리지에 저장
        // localStorage.setItem("x-auth-token", token)
        // dispatch("getMemberInfo")
        // alert("로그인이 완료되었습니다..")
      })
      .catch(err => {
        // alert('이메일과 비밀번호를 확인하세요')
        console.log(err);
      });
    window.Kakao.API.request({
      url: "/mypage",
      success(response) {
        console.log(response);
      },
      fail(error) {
        console.log(error);
      }
    });
  },
  methods: {
    ...mapActions(["signin", "signup"]),
    kakaoLogin() {
      window.Kakao.Auth.authorize({
        redirectUri: `${window.location.origin}/sociallogin`
      });
    }
    // test() {
    //   axios
    //     .get("http://localhost:3500/v1/userlist")
    //     .then(res => {
    //       // handle success
    //       console.log(res);
    //     })
    //     .catch(err => {
    //       // handle error
    //       console.log(err);
    //     })
    //     .then(() => {
    //       console.log("test");
    //       // always executed
    //     });
    // },
    // posttest() {
    //   axios
    //     .post("http://localhost:3500/v1/signin", {
    //       uid: "8"
    //     })
    //     .then(res => {
    //       console.log(res);
    //     })
    //     .catch(err => {
    //       console.log(err);
    //     });
    // },
    //arrow function을 써야지만 this를 통해 vue instance의 인자들에 손쉽게 접근할 수 있다.
  }
};
</script>
