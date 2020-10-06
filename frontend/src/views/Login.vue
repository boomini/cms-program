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
            <div class="my-2">
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
            </div>

            <div class="my-2">
              <v-btn
                depressed
                block
                large
                color="primary"
                router-link
                :to="{ name: 'signup' }"
                >회원가입</v-btn
              >
            </div>

            <div class="my-2">
              <v-btn
                depressed
                block
                large
                color="yellow"
                router-link
                @click="kakaoLogin"
                >카카오로그인</v-btn
              >
            </div>
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
      accessToken: null
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError"])
  },
  mounted() {
    //this.accessToken = this.$route.params.accessToken;
    //window.Kakao.Auth.setAccessToken(this.$route.params.accessToken);

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
  }
};
</script>
