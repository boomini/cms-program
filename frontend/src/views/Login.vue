<template>
  <v-container fill-height style="max-width:450px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-alert class="mb-3" :value="isLoginError" type="error">아이디와 비밀번호를 확인해주세요.</v-alert>
        <v-alert class="mb-3" :value="isLogin" type="success">로그인이 성공하였습니다.</v-alert>
        <v-card>
          <v-toolbar flat dense>
            <v-toolbar-title>로그인</v-toolbar-title>
          </v-toolbar>
          <div class="pa-3">
            <v-text-field v-model="email" label="이메일을 입력하세요"></v-text-field>
            <v-text-field v-model="password" type="password" label="패스워드를 입력하세요"></v-text-field>
            <v-btn
              @click="login({
              email,
              password
            })"
              depressed
              block
              large
              color="primary"
            >로그인</v-btn>
            <!-- <v-btn @click="test">테스트</v-btn>
            <v-btn @click="posttest">포스트 테스트</v-btn>-->
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      email: null,
      password: null
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  methods: {
    ...mapActions(["login"])

    // test() {
    //   axios
    //     .get("https://reqres.in/api/users?page=2")
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
    //     .post("https://reqres.in/api/register", {
    //       email: "eve.holt@reqres.in",
    //       password: "pistol"
    //     })
    //     .then(res => {
    //       console.log(res);
    //     })
    //     .catch(err => {
    //       console.log(err);
    //     });
    // }
    //arrow function을 써야지만 this를 통해 vue instance의 인자들에 손쉽게 접근할 수 있다.
  }
};
</script>