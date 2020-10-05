<template>
  <v-container fill-height style="max-width:450px">
    <v-layout align-center row wrap>
      <v-flex xs12>
        <v-alert class="mb-3" :value="isLoginError" type="error"
          >빈 칸을 채워주세요.</v-alert
        >
        <v-alert class="mb-3" :value="isLogin" type="success"
          >회원가입이 성공하였습니다.</v-alert
        >
        <v-card>
          <v-toolbar flat dense>
            <v-toolbar-title>회원가입</v-toolbar-title>
          </v-toolbar>
          <div class="pa-3">
            <v-text-field
              v-model="password"
              type="password"
              label="패스워드를 입력하세요"
            ></v-text-field>
            <v-text-field
              v-model="name"
              label="이름을 입력하세요"
            ></v-text-field>
            <v-btn
              @click="
                kakaosignup({
                  token,
                  password,
                  name
                })
              "
              depressed
              block
              large
              color="primary"
              >회원가입</v-btn
            >
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
import { mapActions, mapState } from "vuex";
export default {
  data() {
    return {
      password: null,
      name: null,
      token: null
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  methods: {
    ...mapActions(["kakaosignin", "signin", "signup", "kakaosignup"])
  },
  mounted() {
    console.log(this.$route.query.code);
    const qs = require("qs");
    let accessToken = null;
    const parameter = {
      grant_type: "authorization_code",
      client_id: "9711a4064e369abb0bab51007aa18a4b",
      // redirect_uri: `${window.location.origin}/sociallogin`,
      code: this.$route.query.code
    };
    axios
      .post("https://kauth.kakao.com/oauth/token", qs.stringify(parameter), {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
        }
      })
      .then(res => {
        console.log(res);
        accessToken = res.data.access_token;
        this.token = accessToken;
        console.log(this.token);
        this.kakaosignin(accessToken);
      });
  }
};
</script>

// Request:
// curl -v -X POST https://kauth.kakao.com/oauth/token \
//  -d 'grant_type=authorization_code' \
//  -d 'client_id={REST_API_KEY}' \
//  -d 'redirect_uri={REDIRECT_URI}' \
//  -d 'code={AUTHORIZE_CODE}'

// Response:Success
//  HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
// {
//     "token_type":"bearer",
//     "access_token":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
//     "expires_in":43199,
//     "refresh_token":"yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
//     "refresh_token_expires_in":25184000,
//     "scope":"account_email profile"