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
                  password,
                })
              "
              depressed
              block
              large
              color="primary"
              >로그인</v-btn
            >
            <v-btn @click="signup({ uid, password })">회원가입</v-btn>
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
      uid: null,
      password: null,
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(["signin", "signup"]),

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
  },
};
</script>
